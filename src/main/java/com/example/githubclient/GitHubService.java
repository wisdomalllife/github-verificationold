package com.example.githubclient;

import com.example.githubclient.model.CommitNode;
import com.example.githubclient.model.IssueComment;
import com.example.githubclient.model.Pull;
import com.example.githubclient.model.ReviewComment;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
//помечает для спринга, что это сервис
//Функции, которые мы будем использовать в приложении(фызов не через ссылки, а на прямую)
@Service
public class GitHubService {
    //экзампляр клиента для работы с клиентом( устанавливаем связь между приложением и сервисом гитхаба)
    private final GitHubClient client;

    public GitHubService(GitHubClient client) {
        this.client = client;
    }
    //получить данные -> что-то делаем -> обратно отправляет
    public void sendTitlesMessage(String owner, String repo) throws IOException {

        String titles = client.getUserRepoPulls(owner, repo).stream()
                .map(Pull::getTitle)
                .collect(Collectors.joining(", "));

        IssueComment comment = new IssueComment();

        comment.setBody(titles);
        client.createPullIssue(comment, owner, repo, 1);
    }
    //отправляет сообщение о том правильно ли написан заголовок
    public void sendVerificationMessage(String owner, String repo, int number) throws IOException {

        List<CommitNode> commitList = client.getCommitNodes(owner, repo, number).stream()
                .sorted(Comparator.comparing(CommitNode::getDate))
                .collect(Collectors.toList());

        List<IssueComment> issueCommentList = client.getPullIssue(owner, repo, number).stream()
                .sorted(Comparator.comparing(IssueComment::getCreationDate))
                .collect(Collectors.toList());

        List<ReviewComment> reviewCommentList = client.getPullReview(owner, repo, number).stream()
                .sorted(Comparator.comparing(ReviewComment::getCreationDate))
                .collect(Collectors.toList());

        String message = MessageTemplateVerifier.buildMessage(issueCommentList, reviewCommentList, commitList);

        if (!message.isEmpty()) {
            IssueComment comment = new IssueComment();
            comment.setBody(message);
            client.createPullIssue(comment, owner, repo, number);
        }
    }

    public List<Pull> getPulls(String owner, String repo) throws IOException {
        List<Pull> list = client.getUserRepoPulls(owner, repo);
        list.forEach(x -> x.setTitle("Hello " + x.getTitle()));
        return list;

    }

    public List<CommitNode> getCommitNode(String owner, String repo, int number) throws IOException {
        List<CommitNode> list = client.getCommitNodes(owner, repo, number);
        list.forEach(x -> x.setSha(new StringBuilder(x.getSha()).reverse().toString()));
        return list;
    }

    public List<IssueComment> getIssues(String owner, String repo, int number) throws IOException {
        List<IssueComment> list = client.getPullIssue(owner, repo, number);
        list.forEach(x -> x.setBody("NICE " + x.getBody()));
        return list;
    }

    public List<ReviewComment> getReviews(String owner, String repo, int number) throws IOException {
        List<ReviewComment> list = client.getPullReview(owner, repo, number);
        list.forEach(x -> x.setBody("NICE " + x.getBody()));
        return list;
    }
}
