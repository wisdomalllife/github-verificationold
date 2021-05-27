package com.example.githubclient;

import com.example.githubclient.model.CommitNode;
import com.example.githubclient.model.IssueComment;
import com.example.githubclient.model.Pull;
import com.example.githubclient.model.ReviewComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class GitHubClientController extends AbstractRestController {
    //сосздает объект клиента и устанавливает связи
    @Autowired
    private GitHubClient githubService;
    //то, по какой ссылке работает клиент(вызов методов)
    @GetMapping("/repos/{owner}/{repo}/pulls")
    public List<Pull> getPulls(
            @PathVariable("owner") String owner,
            @PathVariable("repo") String repoName) throws IOException {
        return githubService.getUserRepoPulls(owner, repoName);
    }

    @GetMapping("/repos/{owner}/{repo}/pull/{number}/commits")
    public List<CommitNode> getCommit(
            @PathVariable("owner") String owner,
            @PathVariable("repo") String repoName,
            @PathVariable("number") int number) throws IOException {
        return githubService.getCommitNodes(owner, repoName, number);
    }

    @GetMapping("/repos/{owner}/{repo}/pull/{number}/review")

    public List<ReviewComment> getReviewComment(
            @PathVariable("owner") String owner,
            @PathVariable("repo") String repoName,
            @PathVariable("number") int number) throws IOException {
        return githubService.getPullReview(owner, repoName, number);
    }

    @GetMapping("/repos/{owner}/{repo}/pull/{number}/issue")
    public List<IssueComment> getIssueComment(
            @PathVariable("owner") String owner,
            @PathVariable("repo") String repoName,
            @PathVariable("number") int number) throws IOException {
        return githubService.getPullIssue(owner, repoName, number);
    }

    @PostMapping("/repos/{owner}/{repo}/pull/{number}/review/post")
    public ReviewComment leaveReviewComment(@RequestBody ReviewComment reviewComment,
                                            @PathVariable("owner") String owner,
                                            @PathVariable("repo") String repoName,
                                            @PathVariable("number") int number) throws IOException {

        return githubService.createPullReview(reviewComment, owner, repoName, number);
    }

    @PostMapping("/repos/{owner}/{repo}/pull/{number}/issue")
    public IssueComment leaveIssueComment(@RequestBody IssueComment issueComment,
                                          @PathVariable("owner") String owner,
                                          @PathVariable("repo") String repoName,
                                          @PathVariable("number") int number) throws IOException {

        Pull pull = getPulls(owner, repoName).stream()
                .filter(p -> p.getNumber() == number)
                .collect(Collectors.toList()).get(0);

        // List<CommitNode> commitNodeList = getCommit(owner, repoName, number);

        String messageBody = "Hello World!";
        issueComment.setBody(messageBody);

        return githubService.createPullIssue(issueComment, owner, repoName, number);
    }
}
