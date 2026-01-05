package com.fluxmall.controller;

import com.fluxmall.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;


}