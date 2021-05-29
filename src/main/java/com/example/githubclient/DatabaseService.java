package com.example.githubclient;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatabaseService {
    private final JdbcTemplate jdbcTemplate;

    public DatabaseService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<String> getUser() {
        // можно написать любой селект
        return jdbcTemplate.queryForList("select name from UserInfo", String.class);
    }
}
