package com.example.githubclient;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class VerifierExecutor {

    private final GitHubService service;

    String owner = "wisdomalllife";
    String repo = "java_au_previous";
    int number = 15;

    public VerifierExecutor(GitHubService service) {
        this.service = service;
    }

    @Scheduled(cron = "*/5 * * ? * *")
    public void verify() {
        System.out.println("Влад Котов, где ТЗ? Ты же обещал");
    }
    //каждые 10 секунд пытаемся отправить сообщение
    @Scheduled(cron = "*/10 * * ? * *")
    public void sendTestMessage() throws IOException {
        service.sendVerificationMessage(owner, repo, number);
    }
}
