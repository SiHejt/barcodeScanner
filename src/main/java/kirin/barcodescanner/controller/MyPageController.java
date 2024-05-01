package kirin.barcodescanner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyPageController {

    @GetMapping("/myPageMenu/likeList")
    public String likeListPage() {
        return "/myPageMenu/likeList";
    }

    @GetMapping("/myPageMenu/viewMyReview")
    public String viewMyReviewPage() {
        return "/myPageMenu/viewMyReview";
    }
}
