package kirin.barcodescanner.service;

import kirin.barcodescanner.domain.Product;
import kirin.barcodescanner.repository.CuRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CuService {
    private final CuRepository cuRepository;



    public CuService(CuRepository cuRepository) {
        this.cuRepository = cuRepository;
    }

    public Product findProductByBarcodeNum(String barcodeNum) {
        List<Product> products = cuRepository.findProductByBarcodeNum(barcodeNum);
        if (products == null || products.isEmpty()) {
        	throw new NoSuchElementException("바코드 번호에 해당하는 제품이 없습니다: " + barcodeNum);
        }
        return products.get(0);
    }

    public List<Product> viewCu(String category) {
        return cuRepository.findByCategory(category);
    }
}
