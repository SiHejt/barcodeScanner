package kirin.barcodescanner.controller;

import kirin.barcodescanner.Entity.Review;
import kirin.barcodescanner.Entity.User;
import kirin.barcodescanner.service.CuService;
import kirin.barcodescanner.service.ReviewService;
import kirin.barcodescanner.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ReviewController {

    @Autowired
    private ReviewService reviewService;
    @Autowired
    private UserService userService;
    @Autowired
    private CuService cuService;

    @PostMapping("/submitReview")
    public String submitReview(@ModelAttribute Review review, RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            redirectAttributes.addFlashAttribute("error", "로그인이 필요합니다.");
            return "redirect:/custom-login";
        }

        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
        String email = oauthUser.getAttribute("email");
        if (email == null) {
            redirectAttributes.addFlashAttribute("error", "유효하지 않은 사용자입니다.");
            return "redirect:/custom-login";
        }

        User user = userService.findOrCreateUserByEmail(email);
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "사용자 정보를 찾을 수 없습니다.");
            return "redirect:/home";
        }

        review.setUser(user);
        reviewService.saveReview(review);

        cuService.updateProductWithReview(review.getBarcodeNumber(), review.getRating());

        redirectAttributes.addFlashAttribute("success", "리뷰가 성공적으로 등록되었습니다.");

        return "redirect:/home/viewProduct?barcodeNumber=" + review.getBarcodeNumber();
    }
}