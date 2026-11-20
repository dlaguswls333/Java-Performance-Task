package com.dgsw.javaperformancetask.member.controller;

import com.dgsw.javaperformancetask.member.dto.MemberDto;
import com.dgsw.javaperformancetask.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/regist")
    public ResponseEntity<MemberDto.SignupResponse> regist(@Valid @RequestBody MemberDto.SignupRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.regist(req));
    }

    @PostMapping("/login")
    public MemberDto.LoginResponse login(@Valid @RequestBody MemberDto.LoginRequest req) {
        return memberService.login(req);
    }

    @PostMapping("/refresh")
    public MemberDto.RefreshResponse refresh(@Valid @RequestBody MemberDto.RefreshRequest req) {
        return memberService.refresh(req);
    }

    @PutMapping
    public MemberDto.PutResponse put(Authentication authentication, @Valid @RequestBody MemberDto.PutRequest req) {
        return memberService.put(authentication.getName(), req);
    }

    @DeleteMapping
    public MemberDto.DeleteResponse delete(Authentication authentication, @Valid @RequestBody MemberDto.DeleteRequest req) {
        return memberService.delete(authentication.getName(), req);
    }
}
