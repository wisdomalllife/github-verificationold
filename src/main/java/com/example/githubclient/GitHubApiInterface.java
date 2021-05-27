package com.example.githubclient;

import com.example.githubclient.model.CommitNode;
import com.example.githubclient.model.IssueComment;
import com.example.githubclient.model.Pull;
import com.example.githubclient.model.ReviewComment;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface GitHubApiInterface {

    @GET("repos/{owner}/{repo}/pulls")
    Call<List<Pull>> listUserRepoPulls(@Header("Authorization") String accessToken, @Header("Accept") String apiVersionSpec,
                                       @Path("owner") String owner, @Path("repo") String repo);

    @GET("repos/{owner}/{repo}/pulls/{number}/commits")
    Call<List<CommitNode>> listCommits(@Header("Authorization") String accessToken, @Header("Accept") String apiVersionSpec,
                                       @Path("owner") String owner, @Path("repo") String repo, @Path("number") int number);

    @GET("repos/{owner}/{repo}/pulls/{number}/comments")
    Call<List<ReviewComment>> listReviewComments(@Header("Authorization") String accessToken, @Header("Accept") String apiVersionSpec,
                                                 @Path("owner") String owner, @Path("repo") String repo, @Path("number") int number);

    @GET("repos/{owner}/{repo}/issues/{number}/comments")
    Call<List<IssueComment>> listIssueComments(@Header("Authorization") String accessToken, @Header("Accept") String apiVersionSpec,
                                               @Path("owner") String owner, @Path("repo") String repo, @Path("number") int number);

    @POST("/repos/{owner}/{repo}/pulls/{number}/comments")
    Call<ReviewComment> createReviewComment(@Body ReviewComment comm,
                             //body - то, что передаем в запросе
                             //Хэдер - дополнительная информация о запросе
                             @Header("Authorization") String accessToken,//токен авторизация
                             @Header("Accept") String apiVersionSpec, //версия api
                             @Header("Content-Type") String contentType,//то, что ожидается в запросе
                             @Path("owner") String owner,
                             @Path("repo") String repo,
                             @Path("number") int number);

    @POST("repos/{owner}/{repo}/issues/{number}/comments")
    Call<IssueComment> createIssueComment(@Body IssueComment comm,
                                      @Header("Authorization") String accessToken,
                                      @Header("Accept") String apiVersionSpec,
                                      @Header("Content-Type") String contentType,
                                      @Path("owner") String owner,
                                      @Path("repo") String repo,
                                      @Path("number") int number);
}