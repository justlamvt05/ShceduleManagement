package com.lamvt.shcedule.security.config.oauth;

import com.lamvt.shcedule.constraint.AuthProvider;
import com.lamvt.shcedule.entity.User;
import com.lamvt.shcedule.exception.UnauthorizedException;
import com.lamvt.shcedule.repository.UserRepository;
import com.lamvt.shcedule.security.jwt.JwtUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        assert email != null;
        String baseUsername = email.split("@")[0];
        // Create user case
//        User user = userRepository.findByEmail(email).orElseGet(() -> {
//            User newUser = new User();
//            newUser.setEmail(email);
//            newUser.setUsername(username);
//            newUser.setAuthProvider(AuthProvider.GOOGLE);
//            return userRepository.save(newUser);
//        });

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UnauthorizedException("User not found, Register"));

        String jwt = jwtUtils.generateToken(user);

        response.sendRedirect(
                "http://localhost:3000/oauth-success?token=" + jwt
        );

    }
}
