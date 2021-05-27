package com.example.githubclient;

import com.example.githubclient.model.CommitNode;
import com.example.githubclient.model.IssueComment;
import com.example.githubclient.model.Pull;
import com.example.githubclient.model.ReviewComment;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.when;

public class GitHubServiceTest extends AbstractGithubClientTest {

    String owner = "OWNER";
    String repo = "REPO";
    int number = 8800;
    //установка связи с гитом
    GitHubService service;
    //моккирование гитхаб
    GitHubClient mock;

    //перед каждым тестом
    @Before
    public void init() {
        //моккируем гитхаб клиент(заменяем интернет на экземпляр класса)
        mock = Mockito.mock(GitHubClient.class, RETURNS_DEEP_STUBS);
        //Устанавливаем связь с подмененным гитхабом
        service = new GitHubService(mock);
    }

    @Test
    public void testGetPulls() throws IOException {
        Pull pull = new Pull(); //создание нового экземпляра пула
        pull.setTitle("Test Pull"); //приделали заголовок
        when(mock.getUserRepoPulls(owner, repo)).thenReturn(Collections.singletonList(pull)); //список из одного объекта, который создали строчкой выше
        List<Pull> pulls = service.getPulls(owner, repo);
        assertEquals("Hello Test Pull", pulls.get(0).getTitle());
    }

    @Test
    public void testGetCommits() throws IOException {
        String testString = "STRING TEST";
        CommitNode node = new CommitNode();
        node.setSha(testString);
        when(mock.getCommitNodes(owner, repo, number)).thenReturn(Collections.singletonList(node));
        List<CommitNode> commitNodes = service.getCommitNode(owner, repo, number);
        assertEquals(new StringBuilder(testString).reverse().toString(), commitNodes.get(0).getSha());
    }

    @Test
    public void testGetIssue() throws IOException {
        IssueComment comment = new IssueComment();
        comment.setBody("BODY BODY BODY");
        when(mock.getPullIssue(owner, repo, number)).thenReturn(Collections.singletonList(comment));
        List<IssueComment> issues = service.getIssues(owner, repo, number);
        assertEquals("NICE BODY BODY BODY", issues.get(0).getBody());
    }

    @Test
    public void testGetReview() throws IOException {
        ReviewComment comment = new ReviewComment();
        comment.setBody("BODY BODY BODY");
        when(mock.getPullReview(owner, repo, number)).thenReturn(Collections.singletonList(comment));
        List<ReviewComment> reviews = service.getReviews(owner, repo, number);
        assertEquals("NICE BODY GOOD ASS", reviews.get(0).getBody());
    }



}
