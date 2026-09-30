package com.zyyyl.nursing.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.zyyyl.framework.web.service.TokenService;
import com.zyyyl.nursing.domain.FamilyMember;
import com.zyyyl.nursing.dto.MemberBindRequestDto;
import com.zyyyl.nursing.dto.MemberLoginRequestDto;
import com.zyyyl.nursing.exception.MemberBindRequiredException;
import com.zyyyl.nursing.service.impl.FamilyMemberServiceImpl;
import com.zyyyl.nursing.vo.MemberLoginVo;

@ExtendWith(MockitoExtension.class)
class MemberLoginServiceTest {

    @Mock
    private IWechatService wechatService;

    @Mock
    private FamilyMemberServiceImpl familyMemberService;

    @Mock
    private TokenService tokenService;

    @Mock
    private MemberBindTicketStore bindTicketStore;

    @InjectMocks
    private MemberLoginService memberLoginService;

    @Test
    void loginWithExistingOpenidReturnsToken() {
        FamilyMember member = member(7L, "13800000001", "大桔大利0001", "openid-a");
        when(wechatService.getOpenid("code-a")).thenReturn("openid-a");
        when(wechatService.getPhone("phone-a")).thenReturn("13800000001");
        when(familyMemberService.saveOrUpdateByWechat(eq("openid-a"), eq("13800000001"), any()))
                .thenReturn(member);
        when(tokenService.createToken(any(), anyString())).thenReturn("token-a");

        MemberLoginRequestDto dto = new MemberLoginRequestDto();
        dto.setCode("code-a");
        dto.setPhoneCode("phone-a");
        MemberLoginVo vo = memberLoginService.login(dto);

        assertThat(vo.getToken()).isEqualTo("token-a");
        assertThat(vo.getMemberId()).isEqualTo(7L);
        assertThat(vo.getPhone()).isEqualTo("138****0001");
    }

    @Test
    void wxLoginWithoutBindingThrowsBindRequired() {
        when(wechatService.getOpenid("code-b")).thenReturn("openid-b");
        when(familyMemberService.selectByOpenId("openid-b")).thenReturn(null);

        assertThatThrownBy(() -> memberLoginService.wxLogin("code-b"))
                .isInstanceOf(MemberBindRequiredException.class);
    }

    @Test
    void wxBindLinksOpenidToExistingPhoneAccount() {
        FamilyMember member = member(9L, "13800000002", "好柿开花0002", null);
        MemberBindRequestDto bind = new MemberBindRequestDto();
        bind.setBindTicket("ticket-c");
        bind.setPhone("13800000002");
        when(bindTicketStore.getOpenId("ticket-c")).thenReturn("openid-c");
        when(familyMemberService.selectByPhone("13800000002")).thenReturn(member);
        when(tokenService.createToken(any(), anyString())).thenReturn("token-c");

        MemberLoginVo vo = memberLoginService.wxBind(bind);

        verify(familyMemberService).updateById(member);
        verify(bindTicketStore).remove("ticket-c");
        assertThat(vo.getToken()).isEqualTo("token-c");
        assertThat(member.getOpenId()).isEqualTo("openid-c");
    }

    private FamilyMember member(Long id, String phone, String name, String openId) {
        FamilyMember member = new FamilyMember();
        member.setId(id);
        member.setPhone(phone);
        member.setName(name);
        member.setOpenId(openId);
        return member;
    }
}
