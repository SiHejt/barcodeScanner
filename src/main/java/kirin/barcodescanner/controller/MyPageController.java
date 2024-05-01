package kirin.barcodescanner.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kirin.barcodescanner.Entity.Review;
import kirin.barcodescanner.Entity.User;
import kirin.barcodescanner.service.ReviewService;
import kirin.barcodescanner.service.UserService;

@Controller
public class MyPageController {

    @GetMapping("/myPageMenu/likeList")
    public String likeListPage() {
        return "/myPageMenu/likeList";
    }
    
    @Autowired
    private UserService userService;
    
    
    @Autowired
    private ReviewService reviewService;

    @GetMapping("/myPageMenu/viewMyReview")
    public String viewMyReviewPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
            
            // 사용자 정보 확인
            System.out.println("OAuth2 User Attributes:");
            oauthUser.getAttributes().forEach((key, value) -> System.out.println(key + ": " + value));

            // 사용자 이름 가져오기
            String userName = (String) oauthUser.getAttribute("email");
            if (userName == null) {
                model.addAttribute("errorMessage", "사용자 정보를 찾을 수 없습니다.");
                return "errorPage";
            }

            // 사용자 찾기
            User user = userService.findOrCreateUserByEmail(userName);
            if (user == null) {
                model.addAttribute("errorMessage", "사용자를 찾을 수 없습니다.");
                return "errorPage";
            }
            long id= user.getId();

            try {
                // 사용자의 리뷰 가져오기
                List<Review> reviews = reviewService.findReviewsByUserId(id);
                if (reviews.isEmpty()) {
                    model.addAttribute("message", "리뷰가 없습니다.");
                } else {
                    model.addAttribute("reviews", reviews);
                }
            } catch (Exception e) {
                model.addAttribute("errorMessage", "사용자 리뷰를 가져오는 중 오류가 발생했습니다.");
                return "errorPage";
            }
        } else {
            model.addAttribute("errorMessage", "인증되지 않은 접근입니다.");
            return "login";
        }
        return "/myPageMenu/viewMyReview";
    }
}