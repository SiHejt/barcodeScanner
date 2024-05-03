package kirin.barcodescanner.controller;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import kirin.barcodescanner.oauth.SessionUser;
import kirin.barcodescanner.repository.Login;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class LoginControler {
 


	 @GetMapping("/login")
	    public String home(@Login SessionUser user, Model model) {
	        // 세션에 저장된 값이 있을 때만 model에 userName 등록
	        if (user != null) {
	            model.addAttribute("userName", user.getName());
	        }
	        return "index";
    }
}