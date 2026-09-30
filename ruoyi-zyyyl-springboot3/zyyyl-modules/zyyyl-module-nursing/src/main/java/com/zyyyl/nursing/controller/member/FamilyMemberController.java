package com.zyyyl.nursing.controller.member;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import com.zyyyl.common.annotation.Anonymous;
import com.zyyyl.common.core.controller.BaseController;
import com.zyyyl.common.core.domain.AjaxResult;
import com.zyyyl.common.core.domain.R;
import com.zyyyl.nursing.dto.MemberBindRequestDto;
import com.zyyyl.nursing.dto.MemberElderDto;
import com.zyyyl.nursing.dto.MemberLoginRequestDto;
import com.zyyyl.nursing.exception.MemberBindRequiredException;
import com.zyyyl.nursing.service.IFamilyMemberElderService;
import com.zyyyl.nursing.service.MemberLoginService;
import com.zyyyl.nursing.vo.FamilyMemberElderVo;
import com.zyyyl.nursing.vo.MemberElderListVo;
import com.zyyyl.nursing.vo.MemberLoginVo;

/**
 * 家属端账号与老人绑定接口。路径保持遗留系统与接口文档一致。
 */
@RestController
@RequestMapping("/member/user")
public class FamilyMemberController extends BaseController {

    private final MemberLoginService memberLoginService;

    private final IFamilyMemberElderService familyMemberElderService;

    public FamilyMemberController(MemberLoginService memberLoginService,
            IFamilyMemberElderService familyMemberElderService) {
        this.memberLoginService = memberLoginService;
        this.familyMemberElderService = familyMemberElderService;
    }

    /**
     * 原小程序登录入口：code + phoneCode。
     */
    @Anonymous
    @PostMapping("/login")
    public AjaxResult login(@RequestBody MemberLoginRequestDto dto) {
        return success(memberLoginService.login(dto));
    }

    /**
     * 微信静默登录：已绑定返回登录态，未绑定返回 202 bindRequired 和一次性票据。
     */
    @Anonymous
    @PostMapping("/wx-login")
    public AjaxResult wxLogin(@RequestBody MemberLoginRequestDto dto) {
        return success(memberLoginService.wxLogin(dto == null ? null : dto.getCode()));
    }

    /**
     * 首次微信登录绑定原家属账号。
     */
    @Anonymous
    @PostMapping("/wx-bind")
    public AjaxResult wxBind(@RequestBody MemberBindRequestDto dto) {
        return success(memberLoginService.wxBind(dto));
    }

    @PostMapping("/add")
    public AjaxResult add(@RequestBody MemberElderDto dto) {
        return toAjax(familyMemberElderService.add(dto, memberLoginService.currentMemberId()));
    }

    @GetMapping("/my")
    public R<List<FamilyMemberElderVo>> my() {
        return R.ok(familyMemberElderService.my(memberLoginService.currentMemberId()));
    }

    @GetMapping("/list-by-page")
    public R<List<MemberElderListVo>> listByPage() {
        startPage();
        return R.ok(familyMemberElderService.listByPage(memberLoginService.currentMemberId()));
    }

    @DeleteMapping("/deleteById")
    public AjaxResult deleteById(@RequestParam Long id) {
        return toAjax(familyMemberElderService.deleteById(id, memberLoginService.currentMemberId()));
    }

    /**
     * 微信未绑定时返回 202 + bindRequired，并携带一次性绑定票据。
     */
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ExceptionHandler(MemberBindRequiredException.class)
    public AjaxResult handleBindRequired(MemberBindRequiredException e) {
        return AjaxResult.error(202, "bindRequired").put("data",
                java.util.Map.of("bindTicket", e.getBindTicket()));
    }
}
