package kirin.barcodescanner.repository;

import kirin.barcodescanner.domain.Product;

import java.util.List;


public interface CuRepository {

    List<Product> findAll();
    List<Product> findByCategory(String category);
    List<Product> findProductByBarcodeNum(String barcodeNum);
    List<Product> findCuEvent();
    void updateProductWithReview(String barcodeNumber, int rating);
    void incrementProductLike(String barcodeNumber);
    void decrementProductLike(String barcodeNumber);
}
