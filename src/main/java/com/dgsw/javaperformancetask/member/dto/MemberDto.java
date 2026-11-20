package com.dgsw.javaperformancetask.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

public class MemberDto {

    public record SignupRequest(
            @NotBlank(message = "아이디를 입력해주세요.")
            @Size(min = 4, max = 20, message = "아이디는 4~20자여야 합니다.")
            @Pattern(
                    regexp = "^[a-zA-Z0-9_]+$",
                    message = "아이디는 영문, 숫자, 밑줄만 사용할 수 있습니다."
            )
            String username,

            @NotBlank(message = "비밀번호를 입력해주세요.")
            @Size(min = 8, max = 72, message = "비밀번호는 8~72자여야 합니다.")
            String password
    ) {}

    public record LoginRequest(
            @NotBlank(message = "아이디를 입력해주세요.")
            String username,

            @NotBlank(message = "비밀번호를 입력해주세요.")
            String password
    ) {}

    public record PutRequest(
            @NotBlank(message = "새 비밀번호를 입력해주세요.")
            @Size(min = 8, max = 72, message = "비밀번호는 8~72자여야 합니다.")
            String password
    ) {}

    public record RefreshRequest(
            @NotBlank(message = "Refresh Token이 필요합니다.")
            String refreshToken
    ) {}

    public record DeleteRequest(
            @NotBlank(message = "비밀번호를 입력해주세요.")
            String password
    ) {}

    public record SignupResponse(
            Long id,
            String username
    ) {}

    public record LoginResponse(
            String accessToken,
            String refreshToken
    ) {}

    public record PutResponse(
            Long id,
            String username
    ) {}

    public record RefreshResponse(
            String accessToken
    ) {}

    public record DeleteResponse(
            String message
    ) {}
}