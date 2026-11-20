package com.dgsw.javaperformancetask.member.service;

import com.dgsw.javaperformancetask.common.exception.CustomException;
import com.dgsw.javaperformancetask.common.exception.ErrorCode;
import com.dgsw.javaperformancetask.common.jwt.JwtProvider;
import com.dgsw.javaperformancetask.member.dto.MemberDto;
import com.dgsw.javaperformancetask.member.entity.Member;
import com.dgsw.javaperformancetask.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MemberServiceImplTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtProvider jwtProvider;

    @InjectMocks
    private MemberServiceImpl memberService;

    @Test
    void 본인_회원정보를_수정하면_새_비밀번호를_암호화하고_응답을_반환한다() {
        Member member = member(7L, "member1", "old-hash");
        given(memberRepository.findById(7L)).willReturn(Optional.of(member));
        given(passwordEncoder.encode("new-password")).willReturn("new-hash");

        MemberDto.PutResponse response = memberService.put(7L, "member1", new MemberDto.PutRequest("new-password"));

        assertThat(response.id()).isEqualTo(7L);
        assertThat(response.username()).isEqualTo("member1");
        assertThat(member.getPassword()).isEqualTo("new-hash");
        verify(passwordEncoder).encode("new-password");
    }

    @Test
    void 다른_회원의_ID로_수정하면_접근_거부한다() {
        given(memberRepository.findById(7L)).willReturn(Optional.of(member(7L, "member1", "old-hash")));

        assertThatThrownBy(() -> memberService.put(7L, "member2", new MemberDto.PutRequest("new-password")))
                .isInstanceOf(CustomException.class)
                .extracting(exception -> ((CustomException) exception).getErrorCode())
                .isEqualTo(ErrorCode.ACCESS_DENIED);

        verify(passwordEncoder, never()).encode("new-password");
    }

    @Test
    void 회원이_존재하지_않으면_수정을_거부한다() {
        given(memberRepository.findById(404L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> memberService.put(404L, "member1", new MemberDto.PutRequest("new-password")))
                .isInstanceOf(CustomException.class)
                .extracting(exception -> ((CustomException) exception).getErrorCode())
                .isEqualTo(ErrorCode.MEMBER_NOT_FOUND);
    }

    @Test
    void 비밀번호가_일치하면_회원탈퇴한다() {
        Member member = member(7L, "member1", "password-hash");
        given(memberRepository.findById(7L)).willReturn(Optional.of(member));
        given(passwordEncoder.matches("password", "password-hash")).willReturn(true);

        MemberDto.DeleteResponse response = memberService.delete(7L, "member1", new MemberDto.DeleteRequest("password"));

        assertThat(response.message()).isEqualTo("회원 탈퇴가 완료되었습니다.");
        verify(memberRepository).delete(member);
    }

    @Test
    void 비밀번호가_틀리면_회원탈퇴하지_않는다() {
        Member member = member(7L, "member1", "password-hash");
        given(memberRepository.findById(7L)).willReturn(Optional.of(member));
        given(passwordEncoder.matches("wrong-password", "password-hash")).willReturn(false);

        assertThatThrownBy(() -> memberService.delete(7L, "member1", new MemberDto.DeleteRequest("wrong-password")))
                .isInstanceOf(CustomException.class)
                .extracting(exception -> ((CustomException) exception).getErrorCode())
                .isEqualTo(ErrorCode.INVALID_CREDENTIALS);

        verify(memberRepository, never()).delete(member);
    }

    @Test
    void 다른_회원의_ID로_탈퇴하면_접근_거부하고_삭제하지_않는다() {
        Member member = member(7L, "member1", "password-hash");
        given(memberRepository.findById(7L)).willReturn(Optional.of(member));

        assertThatThrownBy(() -> memberService.delete(7L, "member2", new MemberDto.DeleteRequest("password")))
                .isInstanceOf(CustomException.class)
                .extracting(exception -> ((CustomException) exception).getErrorCode())
                .isEqualTo(ErrorCode.ACCESS_DENIED);

        verify(memberRepository, never()).delete(member);
        verify(passwordEncoder, never()).matches("password", "password-hash");
    }

    private Member member(long id, String username, String password) {
        return Member.builder().id(id).username(username).password(password).build();
    }
}
