package com.fluxmall.controller;

import com.fluxmall.domain.vo.MemberVO;
import com.fluxmall.domain.vo.ProductVO;
import com.fluxmall.service.MemberService;
import com.fluxmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final MemberService memberService;

    @Autowired
    public ProductController(ProductService productService, MemberService memberService) {
        this.productService = productService;
        this.memberService = memberService;
    }

    @GetMapping("/list")
    public String list(@RequestParam(required = false) String keyword, Model model) {
        List<ProductVO> products;
        if (keyword != null && !keyword.trim().isEmpty()) {
            products = productService.searchProducts(keyword);
            model.addAttribute("keyword", keyword);
        } else {
            products = productService.getOnSaleProducts();
        }
        model.addAttribute("products", products);
        return "product/list";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model, HttpSession session) {
        ProductVO product = productService.getProductDetail(id);
        if (product == null) {
            return "redirect:/product/list";
        }

        model.addAttribute("product", product);

        MemberVO loginMember = memberService.getLoginMember(session);
        if (loginMember != null) {
            model.addAttribute("isLoggedIn", true);
            model.addAttribute("canPurchase", productService.checkStockAvailable(id, 1));
        } else {
            model.addAttribute("isLoggedIn", false);
        }

        return "product/detail";
    }
}
