package kirin.barcodescanner.repository;

import kirin.barcodescanner.Entity.LikedItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LikedItemRepository extends JpaRepository<LikedItem, Long> {
    List<LikedItem> findByUserId(Long userId);
    LikedItem findByUserIdAndBarcodeNumber(Long userId, String barcodeNumber);
}