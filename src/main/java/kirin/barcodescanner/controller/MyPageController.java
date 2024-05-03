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
            long id= user.getId();
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
            return "login";
        }
        return "/myPageMenu/likeList";
    }
    
    


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