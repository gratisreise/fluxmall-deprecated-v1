package com.fluxmall.controller;

import com.fluxmall.domain.vo.MemberVO;
import com.fluxmall.domain.vo.OrderVO;
import com.fluxmall.domain.vo.ProductVO;
import com.fluxmall.service.MemberService;
import com.fluxmall.service.OrderService;
import com.fluxmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final ProductService productService;
    private final MemberService memberService;

    @Autowired
    public OrderController(OrderService orderService, ProductService productService, MemberService memberService) {
        this.orderService = orderService;
        this.productService = productService;
        this.memberService = memberService;
    }

    @GetMapping("/create/{productId}")
    public String createForm(@PathVariable Long productId, Model model, HttpSession session) {
        MemberVO loginMember = memberService.getLoginMember(session);
        if (loginMember == null) {
            return "redirect:/member/login";
        }

        ProductVO product = productService.getProductDetail(productId);
        if (product == null || !product.isOnSale()) {
            return "redirect:/product/list";
        }

        model.addAttribute("product", product);
        model.addAttribute("quantity", 1);
        return "order/create";
    }

    @PostMapping("/create")
    public String createOrder(@RequestParam Long productId,
                              @RequestParam int quantity,
                              HttpSession session,
                              RedirectAttributes redirectAttributes) {
        try {
            Long orderId = orderService.createOrder(productId, quantity, session);
            redirectAttributes.addFlashAttribute("message", "주문이 생성되었습니다.");
            return "redirect:/order/detail/" + orderId;
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/product/detail/" + productId;
        }
    }

    @GetMapping("/detail/{orderId}")
    public String orderDetail(@PathVariable Long orderId, Model model, HttpSession session) {
        OrderVO order = orderService.getOrderDetail(orderId, session);
        if (order == null) {
            return "redirect:/order/list";
        }

        model.addAttribute("order", order);
        return "order/detail";
    }

    @PostMapping("/payment/{orderId}")
    public String processPayment(@PathVariable Long orderId,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {
        try {
            boolean success = orderService.processPayment(orderId, session);
            if (success) {
                redirectAttributes.addFlashAttribute("message", "결제가 완료되었습니다.");
                return "redirect:/order/complete/" + orderId;
            } else {
                redirectAttributes.addFlashAttribute("error", "결제 처리에 실패했습니다.");
                return "redirect:/order/detail/" + orderId;
            }
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/order/detail/" + orderId;
        }
    }

    @GetMapping("/complete/{orderId}")
    public String orderComplete(@PathVariable Long orderId, Model model, HttpSession session) {
        OrderVO order = orderService.getOrderDetail(orderId, session);
        if (order == null || !order.isPaid()) {
            return "redirect:/order/list";
        }

        model.addAttribute("order", order);
        return "order/complete";
    }

    @PostMapping("/cancel/{orderId}")
    public String cancelOrder(@PathVariable Long orderId,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {
        try {
            boolean success = orderService.cancelOrder(orderId, session);
            if (success) {
                redirectAttributes.addFlashAttribute("message", "주문이 취소되었습니다.");
            } else {
                redirectAttributes.addFlashAttribute("error", "주문 취소에 실패했습니다.");
            }
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/order/list";
    }

    @GetMapping("/list")
    public String orderList(Model model, HttpSession session) {
        List<OrderVO> orders = orderService.getMyOrders(session);
        model.addAttribute("orders", orders);
        return "order/list";
    }
}
