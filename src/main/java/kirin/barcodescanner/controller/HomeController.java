package kirin.barcodescanner.controller;


import kirin.barcodescanner.Entity.Review;
import kirin.barcodescanner.domain.Product;
import kirin.barcodescanner.service.CuService;
import kirin.barcodescanner.service.ReviewService;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
    private final CuService cuService;
    private final ReviewService reviewService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public HomeController(CuService cuService, ReviewService reviewService) {
        this.cuService = cuService;
        this.reviewService = reviewService;
    }

    @GetMapping("/")
    public String redirectFromIndex() {
        return "redirect:/home/viewAll";
    }

    @GetMapping("/home/viewAll")
    public String viewAllPage(Model model) {
        List<Product> products = cuService.viewAll();
        model.addAttribute("products", products);
        return "home/viewAll";
    }

    @GetMapping("/home/viewProduct")
    public String viewProductPage(@RequestParam(required = false) String barcodeNumber, Model model) {
        if (barcodeNumber == null || barcodeNumber.isEmpty()) {
            model.addAttribute("error", "바코드 번호가 제공되지 않았습니다.");
            return "error";
        }
        try {
            Product product = cuService.findProductByBarcodeNum(barcodeNumber);
            model.addAttribute("product", product);

            // Fetch reviews for the product
            List<Review> reviews = reviewService.findReviewsByBarcodeNumber(barcodeNumber);
            model.addAttribute("reviews", reviews);

            return "home/viewProduct";
        } catch (NoSuchElementException e) {
            model.addAttribute("error", "바코드 번호에 해당하는 제품이 없습니다: " + barcodeNumber);
            return "error";
        }
    }

    @GetMapping("/home/search")
    public String searchProducts(@RequestParam("keyword") String keyword, Model model) {
        String sql = "SELECT * FROM cu_table WHERE prod_name LIKE ?";
        List<Product> products = jdbcTemplate.query(
                sql,
                new Object[]{"%" + keyword + "%"},
                (rs, rowNum) -> {
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
                }
        );

        model.addAttribute("products", products);
        model.addAttribute("keyword", keyword);
        return "home/search";
    }

    @GetMapping("/home/sevenEleven")
    public String sevenElevenPage() {

        return "home/sevenEleven";
    }

    @GetMapping("/home/gs25")
    public String gs25Page() {

        return "home/gs25";
    }

    @GetMapping("/home/myPage")
    public String myPage() {

        return "redirect:/myPageMenu/viewMyReview";
    }

    @GetMapping("/home/event/sevenElevenEvent")
    public String sevenElevenEvent(Model model) {
        return "home/event/sevenElevenEvent";
    }

    @GetMapping("/home/event/gs25Event")
    public String gs25Event(Model model) {
        return "home/event/gs25Event";
    }
    
    @GetMapping("/barcodescan")
    public String showBarcodeScanPage() {
    	return "home/barcodescan";
    }
    @GetMapping("/home/barcodescan")
    public String barcodeScanPage() {
        return "home/barcodescan"; // barcodescan.html 파일을 반환합니다.
    }
}

