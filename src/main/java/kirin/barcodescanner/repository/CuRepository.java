package kirin.barcodescanner.repository;

import kirin.barcodescanner.domain.Product;

import java.util.List;


public interface CuRepository {
    List<Product> findByCategory(String category);

    List<Product> findProductByBarcodeNum(String barcodeNum);
}
