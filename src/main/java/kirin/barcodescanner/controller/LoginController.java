package kirin.barcodescanner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LoginController {

    @GetMapping("/custom-login")
    public String login() {
        return "customlogin"; // 로그인 페이지로 리다이렉트
    }
}