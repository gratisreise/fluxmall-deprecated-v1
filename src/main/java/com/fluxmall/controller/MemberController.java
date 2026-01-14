package com.fluxmall.controller;

import com.fluxmall.domain.vo.MemberVO;
import com.fluxmall.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/login")
    public String loginForm(@RequestParam(required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "이메일 또는 비밀번호가 올바르지 않습니다.");
        }
        return "member/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                      @RequestParam String password,
                      HttpSession session,
                      Model model) {
        MemberVO member = memberService.login(email, password, session);
        if (member != null) {
            return "redirect:/";
        } else {
            model.addAttribute("error", "이메일 또는 비밀번호가 올바르지 않습니다.");
            return "member/login";
        }
    }

    @GetMapping("/register")
    public String registerForm() {
        return "member/register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String email,
                         @RequestParam String password,
                         @RequestParam String name,
                         Model model) {
        boolean success = memberService.register(email, password, name);
        if (success) {
            return "redirect:/member/login";
        } else {
            model.addAttribute("error", "이미 사용 중인 이메일입니다.");
            return "member/register";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        memberService.logout(session);
        return "redirect:/";
    }
}
