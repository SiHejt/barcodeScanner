package kirin.barcodescanner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyPageController {

    @GetMapping("/myPageDir/likesList")
    public String likesList() {
        return "/myPageDir/likesList";
    }

    @GetMapping("/myPageDir/viewMyReview")
    public String viewMyReview() {
        return "/myPageDir/viewMyReview";
    }
}
