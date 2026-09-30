package com.zyyyl.nursing.controller.member;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zyyyl.common.core.controller.BaseController;
import com.zyyyl.common.core.domain.AjaxResult;
import com.zyyyl.nursing.domain.RoomType;
import com.zyyyl.nursing.service.IRoomTypeService;
import com.zyyyl.nursing.service.MemberLoginService;

/**
 * 家属端房型查询。
 */
@RestController
@RequestMapping("/member/roomTypes")
public class MemberRoomTypeController extends BaseController {

    private final IRoomTypeService roomTypeService;
    private final MemberLoginService memberLoginService;

    public MemberRoomTypeController(IRoomTypeService roomTypeService, MemberLoginService memberLoginService) {
        this.roomTypeService = roomTypeService;
        this.memberLoginService = memberLoginService;
    }

    @GetMapping
    public AjaxResult findRoomTypeListByStatus(Integer status) {
        memberLoginService.requireMemberLogin();
        List<RoomType> list = roomTypeService.findRoomTypeListByStatus(status);
        return success(list);
    }
}
