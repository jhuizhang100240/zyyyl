package com.zyyyl.nursing.controller.member;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zyyyl.common.core.controller.BaseController;
import com.zyyyl.common.core.domain.R;
import com.zyyyl.common.core.page.TableDataInfo;
import com.zyyyl.nursing.domain.NursingProject;
import com.zyyyl.nursing.service.INursingProjectService;
import com.zyyyl.nursing.service.MemberLoginService;

/**
 * 家属端护理项目查询。
 */
@RestController
@RequestMapping("/member/orders")
public class MemberNursingProjectController extends BaseController {

    private final INursingProjectService nursingProjectService;
    private final MemberLoginService memberLoginService;

    public MemberNursingProjectController(INursingProjectService nursingProjectService,
            MemberLoginService memberLoginService) {
        this.nursingProjectService = nursingProjectService;
        this.memberLoginService = memberLoginService;
    }

    @GetMapping("/project/{id}")
    public R<NursingProject> getById(@PathVariable("id") Long id) {
        memberLoginService.requireMemberLogin();
        return R.ok(nursingProjectService.selectNursingProjectById(id));
    }

    @GetMapping
    public TableDataInfo getByPage(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            String name) {
        memberLoginService.requireMemberLogin();
        return nursingProjectService.selectMemberPage(pageNum, pageSize, name);
    }
}
