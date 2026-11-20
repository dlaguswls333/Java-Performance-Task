package com.dgsw.javaperformancetask.member.service;

import com.dgsw.javaperformancetask.common.exception.CustomException;
import com.dgsw.javaperformancetask.common.exception.ErrorCode;
import com.dgsw.javaperformancetask.common.jwt.JwtProvider;
import com.dgsw.javaperformancetask.member.dto.MemberDto;
import com.dgsw.javaperformancetask.member.entity.Member;
import com.dgsw.javaperformancetask.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Override
    public MemberDto.SignupResponse regist(MemberDto.SignupRequest req) {
        if (memberRepository.existsByUsername(req.username())) {
            throw new CustomException(ErrorCode.DUPLICATE_USERNAME);
        }
        Member member = memberRepository.save(Member.builder()
                .username(req.username())
                .password(passwordEncoder.encode(req.password()))
                .build());
        return new MemberDto.SignupResponse(member.getId(), member.getUsername());
    }

    @Override
    @Transactional(readOnly = true)
    public MemberDto.LoginResponse login(MemberDto.LoginRequest req) {
        Member member = memberRepository.findByUsername(req.username())
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_CREDENTIALS));
        if (!passwordEncoder.matches(req.password(), member.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_CREDENTIALS);
        }
        return new MemberDto.LoginResponse(
                jwtProvider.generateAccessToken(member.getUsername()),
                jwtProvider.generateRefreshToken(member.getUsername()));
    }

    @Override
    @Transactional(readOnly = true)
    public MemberDto.RefreshResponse refresh(MemberDto.RefreshRequest req) {
        String username = jwtProvider.parseRefreshToken(req.refreshToken()).getSubject();
        if (!memberRepository.existsByUsername(username)) {
            throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
        }
        return new MemberDto.RefreshResponse(jwtProvider.generateAccessToken(username));
    }

    @Override
    public MemberDto.PutResponse put(Long id, String username, MemberDto.PutRequest req) {
        Member member = findMember(id, username);
        member.setPassword(passwordEncoder.encode(req.password()));
        return new MemberDto.PutResponse(member.getId(), member.getUsername());
    }

    @Override
    public MemberDto.DeleteResponse delete(Long id, String username, MemberDto.DeleteRequest req) {
        Member member = findMember(id, username);
        if (!passwordEncoder.matches(req.password(), member.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_CREDENTIALS);
        }
        memberRepository.delete(member);
        return new MemberDto.DeleteResponse("회원 탈퇴가 완료되었습니다.");
    }

    private Member findMember(Long id, String username) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        if (!member.getUsername().equals(username)) {
            throw new CustomException(ErrorCode.ACCESS_DENIED);
        }
        return member;
    }
}
