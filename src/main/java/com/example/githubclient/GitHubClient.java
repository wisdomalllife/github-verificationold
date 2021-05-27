package com.example.githubclient;

import com.example.githubclient.model.CommitNode;
import com.example.githubclient.model.IssueComment;
import com.example.githubclient.model.Pull;
import com.example.githubclient.model.ReviewComment;
import org.springframework.stereotype.Service;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Service
public class GitHubClient {
    //ссылка на api
    static final String API_BASE_URL = "https://api.github.com/";
    //версия api
    static final String API_VERSION_SPEC = "application/vnd.github.v3+json";
    //то, что ожидаем на получение
    static final String JSON_CONTENT_TYPE = "application/json";

    private final String accessToken;
    //интерфейс сервиса(класс, который выше)
    private final GitHubApiInterface service;
    //конструктор клиента
    //ретрофит готовит запрос из интерфейса к вызову
    public GitHubClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(GitHubApiInterface.class);
        this.accessToken = "token " + System.getenv("GITHUB_ACCESS_TOKEN");
    }

    public List<Pull> getUserRepoPulls(String owner, String repo) throws IOException {
        //забивает параметры хэдера, указыват путь отправления
        Call<List<Pull>> retrofitCall = service.listUserRepoPulls(accessToken, API_VERSION_SPEC, owner, repo);
        //отправка запроса и получения ответа
        Response<List<Pull>> response = retrofitCall.execute();
        //проверка, что результаты получены
        if (!response.isSuccessful()) {
            throw new IOException(response.errorBody() != null
                    ? response.errorBody().string() : "Unknown error");
        }
        //возвращение тела ответа
        return response.body();
    }

    public List<CommitNode> getCommitNodes(String owner, String repo, int number) throws IOException {
        Call<List<CommitNode>> retrofitCall = service.listCommits(accessToken, API_VERSION_SPEC, owner, repo, number);

        Response<List<CommitNode>> response = retrofitCall.execute();

        if (!response.isSuccessful()) {
            throw new IOException(response.errorBody() != null
                    ? response.errorBody().string() : "Unknown error");
        }


        return response.body();
    }

    public List<ReviewComment> getPullReview(String owner, String repo, int number) throws IOException {
        Call<List<ReviewComment>> retrofitCall = service.listReviewComments(accessToken, API_VERSION_SPEC, owner, repo, number);

        Response<List<ReviewComment>> response = retrofitCall.execute();

        if (!response.isSuccessful()) {
            throw new IOException(response.errorBody() != null
                    ? response.errorBody().string() : "Unknown error");
        }


        return response.body();
    }

    public List<IssueComment> getPullIssue(String owner, String repo, int number) throws IOException {
        Call<List<IssueComment>> retrofitCall = service.listIssueComments(accessToken, API_VERSION_SPEC, owner, repo, number);

        Response<List<IssueComment>> response = retrofitCall.execute();

        if (!response.isSuccessful()) {
            throw new IOException(response.errorBody() != null
                    ? response.errorBody().string() : "Unknown error");
        }


        return response.body();
    }

    public ReviewComment createPullReview(ReviewComment reviewComment, String owner, String repo, int number) throws IOException {
        //здесь сразу передаем тело
        Call<ReviewComment> retrofitCall = service.createReviewComment(reviewComment, accessToken, API_VERSION_SPEC, JSON_CONTENT_TYPE, owner, repo, number);

        Response<ReviewComment> response = retrofitCall.execute();

        if (!response.isSuccessful()) {
            throw new IOException(response.errorBody() != null
                    ? response.errorBody().string() : "Unknown error");
        }

        return response.body();
    }

    public IssueComment createPullIssue(IssueComment comm, String owner, String repo, int number) throws IOException {
        //Создаем тело ответа
        Call<IssueComment> retrofitCall = service.createIssueComment(comm, accessToken, API_VERSION_SPEC, JSON_CONTENT_TYPE, owner, repo, number);

        Response<IssueComment> response = retrofitCall.execute();

        if (!response.isSuccessful()) {
            throw new IOException(response.errorBody() != null
                    ? response.errorBody().string() : "Unknown error");
        }

        return response.body();
    }

}


