package kirin.barcodescanner.repository;

import kirin.barcodescanner.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class JdbcCuRepository implements CuRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcCuRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Product> findAll() {
        String sql = "SELECT * FROM cu_table";
        return jdbcTemplate.query(sql, productRowMapper());
    }

    @Override
    public List<Product> findByCategory(String category) {
        String sql = "SELECT * FROM cu_table WHERE category = ?";
        return jdbcTemplate.query(sql, productRowMapper(), category);
    }

    @Override
    public List<Product> findProductByBarcodeNum(String barcodeNum) {
        return jdbcTemplate.query("SELECT * FROM cu_table WHERE barcode_num = ?", productRowMapper(), barcodeNum);
    }

    @Override
    public List<Product> findCuEvent() {
        String sql = "SELECT * FROM cu_table WHERE discount <> 0";
        return jdbcTemplate.query(sql, productRowMapper());
    }

    @Override
    public void updateProductWithReview(String barcodeNumber, int rating) {
        String avgRatingQuery = "SELECT avg_rating FROM cu_table WHERE barcode_num = ?";
        Double currentAvgRating = jdbcTemplate.queryForObject(avgRatingQuery, Double.class, barcodeNumber);

        Double newAvgRating = (currentAvgRating != null) ? currentAvgRating : (double) rating;
        String totalReviewsQuery = "SELECT COUNT(*) FROM review WHERE barcode_number = ?";
        int totalReviews = jdbcTemplate.queryForObject(totalReviewsQuery, Integer.class, barcodeNumber);

        newAvgRating = ((newAvgRating * (totalReviews - 1)) + rating) / totalReviews;

        String updateQuery = "UPDATE cu_table SET count_reviews = count_reviews + 1, avg_rating = ? WHERE barcode_num = ?";
        jdbcTemplate.update(updateQuery, newAvgRating, barcodeNumber);
    }

    @Override
    public void incrementProductLike(String barcodeNumber) {
        String updateQuery = "UPDATE cu_table SET count_likes = count_likes + 1 WHERE barcode_num = ?";
        jdbcTemplate.update(updateQuery, barcodeNumber);
    }

    @Override
    public void decrementProductLike(String barcodeNumber) {
        String updateQuery = "UPDATE cu_table SET count_likes = count_likes - 1 WHERE barcode_num = ?";
        jdbcTemplate.update(updateQuery, barcodeNumber);
    }

    private RowMapper<Product> productRowMapper() {
        return (rs, rowNum) -> {
            Product product = new Product();
            product.setBarcodeNumber(rs.getString("barcode_num"));
            product.setProductName(rs.getString("prod_name"));
            product.setProductPrice(rs.getString("price"));
            product.setProductCategory(rs.getString("category"));
            product.setProductDiscount(rs.getString("discount"));
            product.setCountReviews(rs.getInt("count_reviews"));
            product.setAvgRating(rs.getDouble("avg_rating"));
            product.setCountLikes(rs.getInt("count_likes"));
            return product;
        };
    }
}
