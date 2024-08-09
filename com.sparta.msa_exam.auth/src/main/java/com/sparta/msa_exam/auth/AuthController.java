package com.sparta.msa_exam.auth;


import com.sparta.msa_exam.auth.dto.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/signIn")
    public ResponseEntity<AuthResponse> signIn(
            @RequestParam(name = "user_id") String userId
    ) {
        return ResponseEntity.ok(authService.signIn(userId));
    }
}
