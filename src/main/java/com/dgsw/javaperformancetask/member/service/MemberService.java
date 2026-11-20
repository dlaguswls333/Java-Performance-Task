package com.dgsw.javaperformancetask.member.service;

import com.dgsw.javaperformancetask.member.dto.MemberDto;

public interface MemberService {
    MemberDto.SignupResponse regist(MemberDto.SignupRequest req);
    MemberDto.LoginResponse login(MemberDto.LoginRequest req);
    MemberDto.RefreshResponse refresh(MemberDto.RefreshRequest req);
    MemberDto.PutResponse put(Long id, String username, MemberDto.PutRequest req);
    MemberDto.DeleteResponse delete(Long id, String username, MemberDto.DeleteRequest req);
}
