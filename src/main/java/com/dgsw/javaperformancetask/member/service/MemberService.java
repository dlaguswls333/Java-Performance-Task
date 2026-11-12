package com.dgsw.javaperformancetask.member.service;

import com.dgsw.javaperformancetask.member.dto.MemberDto;

public interface MemberService {
    MemberDto.SignupResponse regist(MemberDto.SignupRequest req);
    MemberDto.LoginResponse login(MemberDto.LoginRequest req);
    MemberDto.RefreshResponse refresh(MemberDto.RefreshRequest req);
    MemberDto.PutResponse put(String username, MemberDto.PutRequest req);
    MemberDto.DeleteResponse delete(String username, MemberDto.DeleteRequest req);
}
