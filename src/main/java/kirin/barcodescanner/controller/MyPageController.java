package kirin.barcodescanner.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kirin.barcodescanner.Entity.LikedItem;
import kirin.barcodescanner.Entity.Review;
import kirin.barcodescanner.Entity.User;
import kirin.barcodescanner.domain.Product;
import kirin.barcodescanner.service.CuService;
import kirin.barcodescanner.service.LikedItemService;
import kirin.barcodescanner.service.ReviewService;
import kirin.barcodescanner.service.UserService;

@Controller
public class MyPageController {

    @Autowired
    private UserService userService;

    @Autowired
    private LikedItemService likeditemservice;

    @Autowired
    private CuService cuService;

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/myPageMenu/likeList")
    public String viewMyLikes(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
            String email = oauthUser.getAttribute("email");

            User user = userService.findOrCreateUserByEmail(email);
            long id = user.getId();
            List<LikedItem> likedItems = likeditemservice.findLikedItemsByUserId(id);

            // 각 찜 목록에 대한 상품 정보를 가져와 모델에 추가
            List<Product> products = new ArrayList<>();
            for (LikedItem likedItem : likedItems) {
                Product product = cuService.findProductByBarcodeNum(likedItem.getBarcodeNumber());
                if (product != null) {
                    products.add(product);
                }
            }

            model.addAttribute("likedItems", likedItems);
            model.addAttribute("products", products); // 상품 정보를 모델에 추가

        } else {
            model.addAttribute("errorMessage", "인증되지 않은 접근입니다.");
            return "custom-login";
        }
        return "myPageMenu/likeList";
    }

    @GetMapping("/myPageMenu/viewMyReview")
    public String viewMyReviewPage(Model model, RedirectAttributes redirectAttributes) {
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

        long id = user.getId();
        List<Review> reviews = reviewService.findReviewsByUserId(id);
        List<Product> products = new ArrayList<>();
        for (Review review : reviews) {
            Product product = cuService.findProductByBarcodeNum(review.getBarcodeNumber());
            if (product != null) {
                products.add(product);
            }
        }

        model.addAttribute("reviews", reviews);
        model.addAttribute("products", products);

        return "myPageMenu/viewMyReview";
    }
}