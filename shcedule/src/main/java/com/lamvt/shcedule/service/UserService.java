package com.lamvt.shcedule.service;

import com.lamvt.shcedule.entity.User;
import jakarta.mail.MessagingException;

public interface UserService {
     void sendVerificationEmail(User user, String token) throws MessagingException;
}
