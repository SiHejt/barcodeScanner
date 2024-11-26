package kirin.barcodescanner.controller;

import kirin.barcodescanner.Entity.LikedItem;
import kirin.barcodescanner.Entity.User;
import kirin.barcodescanner.service.CuService;
import kirin.barcodescanner.service.LikedItemService;
import kirin.barcodescanner.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LikedItemController {
    private final LikedItemService likedItemService;
    private final UserService userService;
    private final CuService cuService;

    @Autowired
    public LikedItemController(LikedItemService likedItemService, UserService userService, CuService cuService) {
        this.likedItemService = likedItemService;
        this.userService = userService;
        this.cuService = cuService;
    }

    @GetMapping("/isLiked")
    public ResponseEntity<Map<String, Boolean>> isLiked(@RequestParam String barcodeNumber) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Boolean> response = new HashMap<>();
        if (authentication == null || !authentication.isAuthenticated()) {
            response.put("liked", false);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
        String email = oauthUser.getAttribute("email");
        if (email == null) {
            response.put("liked", false);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        User user = userService.findOrCreateUserByEmail(email);
        if (user == null) {
            response.put("liked", false);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        LikedItem likedItem = likedItemService.findLikedItemByUserIdAndBarcodeNumber(user.getId(), barcodeNumber);
        response.put("liked", likedItem != null);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/toggleLikelist")
    public ResponseEntity<Map<String, String>> toggleLikelist(@RequestBody Map<String, String> request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<String, String> response = new HashMap<>();
        if (authentication == null || !authentication.isAuthenticated()) {
            response.put("message", "로그인이 필요합니다.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
        String email = oauthUser.getAttribute("email");
        if (email == null) {
            response.put("message", "이메일 정보를 찾을 수 없습니다.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        User user = userService.findOrCreateUserByEmail(email);
        if (user == null) {
            response.put("message", "사용자를 찾을 수 없습니다.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        String barcodeNumber = request.get("barcodeNumber");
        if (barcodeNumber == null || barcodeNumber.isEmpty()) {
            response.put("message", "유효하지 않은 바코드 번호입니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        LikedItem likedItem = likedItemService.findLikedItemByUserIdAndBarcodeNumber(user.getId(), barcodeNumber);
        if (likedItem == null) {
          
            LikedItem newLikedItem = new LikedItem();
            newLikedItem.setUser(user);
            newLikedItem.setBarcodeNumber(barcodeNumber);
            likedItemService.saveLikedItem(newLikedItem);
            response.put("status", "added");
            response.put("message", "찜 목록에 추가되었습니다.");

            cuService.incrementProductLike(barcodeNumber);
        } else {
         
            likedItemService.deleteLikedItem(likedItem);
            response.put("status", "removed");
            response.put("message", "찜 목록에서 제거되었습니다.");

            cuService.decrementProductLike(barcodeNumber);
        }

        return ResponseEntity.ok(response);
    }
}
