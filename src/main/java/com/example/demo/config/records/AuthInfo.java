/**
 * This document olds the basic record for Authentication information.
 */
package com.example.demo.config.records;

import com.example.demo.db.entities.JsonWebToken;
import com.example.demo.db.entities.User;

/**
 * Basic record of the parsed Auth Header
 * @param user that been authed
 * @param token that was passed
 */
public record AuthInfo(User user, JsonWebToken token) {}
