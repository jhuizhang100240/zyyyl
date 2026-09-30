package com.zyyyl.nursing.controller.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zyyyl.common.core.domain.AjaxResult;
import com.zyyyl.common.core.domain.R;
import com.zyyyl.common.core.page.TableDataInfo;
import com.zyyyl.nursing.domain.Reservation;
import com.zyyyl.nursing.dto.ReservationDto;
import com.zyyyl.nursing.service.IReservationService;
import com.zyyyl.nursing.service.MemberLoginService;
import com.zyyyl.nursing.vo.TimeCountVo;

@RestController
@RequestMapping("/member/reservation")
public class MemberReservationController {

    @Autowired
    private IReservationService reservationService;

    @Autowired
    private MemberLoginService memberLoginService;

    @GetMapping("/cancelled-count")
    public AjaxResult cancelledCount() {
        return AjaxResult.success(reservationService.cancelledCount(memberLoginService.currentMemberId()));
    }

    @GetMapping("/countByTime")
    public R<List<TimeCountVo>> countByTime(Long time) {
        return R.ok(reservationService.getCountByTime(time));
    }

    @PostMapping
    public AjaxResult insertReservation(@RequestBody ReservationDto dto) {
        return AjaxResult.success(reservationService.insertReservation(dto, memberLoginService.currentMemberId()));
    }

    @GetMapping("/page")
    public AjaxResult page(@RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            Integer status) {
        TableDataInfo<Reservation> page = reservationService.selectByPage(
                pageNum, pageSize, status, memberLoginService.currentMemberId());
        return AjaxResult.success(page);
    }

    @PutMapping("/{id}/cancel")
    public AjaxResult cancel(@PathVariable Long id) {
        reservationService.cancelReservation(id, memberLoginService.currentMemberId());
        return AjaxResult.success();
    }
}
