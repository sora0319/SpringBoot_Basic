package org.example.firstproject.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.firstproject.dto.MemberForm;
import org.example.firstproject.entity.Member;
import org.example.firstproject.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Slf4j
@Controller
public class MemberController {

    @Autowired
    private MemberRepository memberRepository;

    // 회원 가입 페이지
    @GetMapping("/signup")
    public String signUp() {
        return "members/new";
    }

    // 폼 데이터 저장
    @PostMapping("/join")
    public String join(MemberForm memberForm) {
        Member member = memberForm.toEntity();
        Member saved = memberRepository.save(member);
        log.info(saved.toString());

        return "redirect:/members/" + saved.getId();
    }

    // 상세 페이지
    @GetMapping("/members/{id}")
    public String showDetail(@PathVariable("id") Long id, Model model) {
        Member member = memberRepository.findById(id).orElse(null);
        model.addAttribute("member", member);
        return "members/show";
    }

    // 목록 페이지
    @GetMapping("/members")
    public String showIndex(Model model) {
        List<Member> members = (List<Member>) memberRepository.findAll();
        model.addAttribute("members", members);

        return "members/index";
    }

    @GetMapping("/members/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        Member member = memberRepository.findById(id).orElse(null);
        model.addAttribute("member", member);

        return "members/edit";
    }

    @PostMapping("/members/update")
    public String updateMember(MemberForm memberForm) {
        Member revisedMember = memberForm.toEntity();
        Member original = memberRepository.findById(revisedMember.getId()).orElse(null);

        if (original != null) {
            memberRepository.save(revisedMember);
        }

        return "redirect:/members/" + revisedMember.getId();
    }
}
