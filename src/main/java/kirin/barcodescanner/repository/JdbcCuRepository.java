package kirin.barcodescanner.repository;

import kirin.barcodescanner.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class jdbcCuRepository implements CuRepository {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public jdbcCuRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Product> findByCategory(String category) {
        String sql = "SELECT * FROM cu_table WHERE category = ?";
        return jdbcTemplate.query(sql, productRowMapper(), category);
    }

    private RowMapper<Product> productRowMapper() {
        return (rs, rowNum) -> {
            Product product = new Product();
            product.setBarcodeNumber(rs.getString("barcode_num"));
            product.setProductName(rs.getString("prod_name"));
            product.setProductPrice(rs.getString("price"));
            product.setProductCategory(rs.getString("category"));
            product.setProductDiscount(rs.getString("discount"));
            return product;
        };
    }
}