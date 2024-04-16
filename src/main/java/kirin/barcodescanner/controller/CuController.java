package kirin.barcodescanner.controller;

import kirin.barcodescanner.domain.Product;
import kirin.barcodescanner.service.CuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.List;

@Controller
public class CuController {
    private final CuService cuService;

    @Autowired
    public CuController(CuService cuService) {
        this.cuService = cuService;
    }

    @GetMapping("/home/cu/cuQuickMeal")
    public String cuQuickMealPage(Model model) {
        List<Product> products = cuService.viewCu("간편식사");
        model.addAttribute("products", products);
        return "/home/cu/cuQuickMeal";
    }

    @GetMapping("/home/cu/cuInstant")
    public String cuInstantPage(Model model) {
        List<Product> products = cuService.viewCu("즉석조리");
        model.addAttribute("products", products);
        return "/home/cu/cuInstant";
    }

    @GetMapping("/home/cu/cuSnack")
    public String cuSnackPage(Model model) {
        List<Product> products = cuService.viewCu("과자류");
        model.addAttribute("products", products);
        return "/home/cu/cuSnack";
    }

    @GetMapping("/home/cu/cuIcecream")
    public String cuIcecreamPage(Model model) {
        List<Product> products = cuService.viewCu("아이스크림");
        model.addAttribute("products", products);
        return "/home/cu/cuIcecream";
    }

    @GetMapping("/home/cu/cuFood")
    public String cuFoodPage(Model model) {
        List<Product> products = cuService.viewCu("식품");
        model.addAttribute("products", products);
        return "/home/cu/cuFood";
    }

    @GetMapping("/home/cu/cuDrink")
    public String cuDrinkPage(Model model) {
        List<Product> products = cuService.viewCu("음료");
        model.addAttribute("products", products);
        return "/home/cu/cuDrink";
    }

    @GetMapping("/home/cu/cuSupplies")
    public String cuSuppliesPage(Model model) {
        List<Product> products = cuService.viewCu("생활용품");
        model.addAttribute("products", products);
        return "/home/cu/cuSupplies";
    }


}
