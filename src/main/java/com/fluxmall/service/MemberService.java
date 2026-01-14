package com.fluxmall.service;

import com.fluxmall.dao.MemberDao;
import com.fluxmall.domain.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

@Service
public class MemberService {

    private final MemberDao memberDao;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public MemberService(MemberDao memberDao, BCryptPasswordEncoder passwordEncoder) {
        this.memberDao = memberDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public boolean register(String email, String password, String name) {
        if (memberDao.findByEmail(email) != null) {
            return false;
        }

        String encodedPassword = passwordEncoder.encode(password);
        MemberVO member = new MemberVO(email, encodedPassword, name);
        return memberDao.insert(member) > 0;
    }

    public MemberVO login(String email, String rawPassword, HttpSession session) {
        MemberVO member = memberDao.findByEmail(email);

        if (member != null && passwordEncoder.matches(rawPassword, member.getPassword())) {
            session.setAttribute("loginMember", member);
            return member;
        }

        return null;
    }

    public void logout(HttpSession session) {
        session.removeAttribute("loginMember");
        session.invalidate();
    }

    public MemberVO getLoginMember(HttpSession session) {
        return (MemberVO) session.getAttribute("loginMember");
    }
}
