package com.example.portfolio.util;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class AuthUtils {
    public void checkOwnership(Long tokenUserId, Long ownerId) {
        if (!tokenUserId.equals(ownerId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "他人のデータは操作できません");
        }
    }
}
