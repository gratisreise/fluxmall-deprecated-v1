package com.fluxmall.controller;

import com.fluxmall.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;


@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;



}