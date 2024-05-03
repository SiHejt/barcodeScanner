package kirin.barcodescanner.controller;

import kirin.barcodescanner.Entity.LikedItem;
import kirin.barcodescanner.Entity.User;
import kirin.barcodescanner.service.LikedItemService;
import kirin.barcodescanner.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Map;

@Controller
public class LikedItemController {
    private final LikedItemService likedItemService;
    private final UserService userService;

    @Autowired
    public LikedItemController(LikedItemService likedItemService, UserService userService) {
        this.likedItemService = likedItemService;
        this.userService = userService;
    }

    @PostMapping("/addToLikelist")
    public String addToLikelist(@RequestBody Map<String, String> body, RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            redirectAttributes.addFlashAttribute("error", "로그인이 필요합니다.");
            return "redirect:/login";
        }

        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
        String email = oauthUser.getAttribute("email");
        if (email == null) {
            redirectAttributes.addFlashAttribute("error", "유효하지 않은 사용자입니다.");
            return "redirect:/login";
        }

        User user = userService.findOrCreateUserByEmail(email);
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "사용자 정보를 찾을 수 없습니다.");
            return "redirect:/home";
        }

        String barcodeNumber = body.get("barcodeNumber");
        if (barcodeNumber == null || barcodeNumber.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "바코드 번호가 필요합니다.");
            return "redirect:/";  // 적절한 리디렉션 경로 설정
        }

        LikedItem likedItem = new LikedItem(user, barcodeNumber);
        likedItemService.saveLikedItem(likedItem);
        redirectAttributes.addFlashAttribute("success", "찜 목록에 추가되었습니다.");
        return "redirect:/";
    }
}