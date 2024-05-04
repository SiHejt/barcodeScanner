package kirin.barcodescanner.controller;

import kirin.barcodescanner.domain.Product;
import kirin.barcodescanner.service.CuService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
    private final CuService cuService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public HomeController(CuService cuService) {
        this.cuService = cuService;
    }

    @GetMapping("/")
    public String redirectFromIndex() {
        return "redirect:/home/viewAll";
    }

    @GetMapping("/home/viewAll")
    public String viewAllPage() {
        return "/home/viewAll";
    }

    @GetMapping("/home/viewProduct")
    public String viewProductPage(@RequestParam(required = false) String barcodeNumber, Model model) {
        Product product = cuService.findProductByBarcodeNum(barcodeNumber);
        model.addAttribute("product", product);

        return "/home/viewProduct";
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
                    return product;
                }
        );

        model.addAttribute("products", products);
        model.addAttribute("keyword", keyword);
        return "/home/search";
    }

    @GetMapping("/home/sevenEleven")
    public String sevenElevenPage() {

        return "/home/sevenEleven";
    }

    @GetMapping("/home/gs25")
    public String gs25Page() {

        return "/home/gs25";
    }

    @GetMapping("/home/event")
    public String eventPage() {

        return "/home/event";
    }

    @GetMapping("/home/myPage")
    public String myPage() {

        return "/home/myPage";
    }

}
