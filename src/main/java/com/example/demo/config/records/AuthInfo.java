package com.example.demo.config.records;

import com.example.demo.db.entities.JsonWebToken;
import com.example.demo.db.entities.Users;

public record AuthInfo(Users user, JsonWebToken token) {
}
