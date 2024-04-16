package kirin.barcodescanner.service;

import kirin.barcodescanner.domain.Product;
import kirin.barcodescanner.repository.CuRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CuService {
    private final CuRepository cuRepository;

    public CuService(CuRepository cuRepository) {
        this.cuRepository = cuRepository;
    }

    public List<Product> viewCu(String category) {
        return cuRepository.findByCategory(category);
    }
}