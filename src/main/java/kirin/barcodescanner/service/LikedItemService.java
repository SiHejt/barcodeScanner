package kirin.barcodescanner.service;

import kirin.barcodescanner.Entity.LikedItem;
import kirin.barcodescanner.repository.LikedItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;



@Service
public class LikedItemService {
    private final LikedItemRepository likedItemRepository;

    @Autowired
    public LikedItemService(LikedItemRepository likedItemRepository) {
        this.likedItemRepository = likedItemRepository;
    }

    @Transactional
    public void saveLikedItem(LikedItem likedItem) {
        likedItemRepository.save(likedItem);
    }

    @Transactional
    public void deleteLikedItem(LikedItem likedItem) {
        likedItemRepository.delete(likedItem);
    }

    public LikedItem findLikedItemByUserIdAndBarcodeNumber(Long userId, String barcodeNumber) {
        return likedItemRepository.findByUserIdAndBarcodeNumber(userId, barcodeNumber);
    }

    public List<LikedItem> findLikedItemsByUserId(Long userId) {
        return likedItemRepository.findByUserId(userId);
    }
}