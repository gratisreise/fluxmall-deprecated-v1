package com.fluxmall.controller;

import com.fluxmall.dto.MemberJoinDto;
import com.fluxmall.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    // 1. 회원가입 폼 페이지 (GET /user/join)
    @GetMapping("/join")
    public String joinForm() {
        return "member/join";  // join.jsp
    }

    // 2. 회원가입 처리 (POST /user/join)
    @PostMapping("/join")
    public String join(MemberJoinDto userJoinDto, Model model) {
        // 간단한 유효성 체크 (실무에서는 @Valid + BindingResult 사용)
        if (memberService.existsByUsername(userJoinDto.getUsername())) {
            model.addAttribute("error", "이미 사용 중인 아이디입니다.");
            return "user/join";
        }

        memberService.join(userJoinDto);

        // 가입 성공 후 로그인 페이지로 리다이렉트 또는 자동 로그인 처리
        return "redirect:/user/login?success=join";
    }

    //로그인 페이지

    //내 정보 페이지

    //회원가입

    //회원정보조회

    //회원정보 수정

    //회원정보 삭제

}
