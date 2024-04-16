package kirin.barcodescanner.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class jdbcSaveRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void saveProduct(List<String> barcodeNumbers, List<String> productNames, List<String> productPrices, String category, List<String> discounts) {
        String insertQuery = "INSERT INTO cu_table (barcode_num, prod_name, price, category, discount) VALUES (?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE prod_name = VALUES(prod_name), price = VALUES(price), category = VALUES(category), discount = VALUES(discount)";

        for (int i = 0; i < productNames.size(); i++) {
            jdbcTemplate.update(insertQuery, barcodeNumbers.get(i), productNames.get(i), productPrices.get(i), category, discounts.get(i));
        }
    }
}
