package com.zyyyl.nursing.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zyyyl.common.annotation.Log;
import com.zyyyl.common.core.controller.BaseController;
import com.zyyyl.common.core.domain.AjaxResult;
import com.zyyyl.common.core.page.TableDataInfo;
import com.zyyyl.common.enums.BusinessType;
import com.zyyyl.nursing.domain.Reservation;
import com.zyyyl.nursing.dto.ReservationQueryDto;
import com.zyyyl.nursing.service.IReservationService;

@RestController
@RequestMapping("/nursing/reservation")
public class ReservationController extends BaseController {

    @Autowired
    private IReservationService reservationService;

    @PreAuthorize("@ss.hasPermi('nursing:reservation:list')")
    @GetMapping("/list")
    public TableDataInfo list(ReservationQueryDto query) {
        startPage();
        List<Reservation> list = reservationService.selectReservationList(query);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('nursing:reservation:query')")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable Long id) {
        return success(reservationService.selectReservationById(id));
    }

    @PreAuthorize("@ss.hasPermi('nursing:reservation:edit')")
    @Log(title = "预约信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Reservation reservation) {
        return toAjax(reservationService.updateReservation(reservation));
    }

    @PreAuthorize("@ss.hasPermi('nursing:reservation:remove')")
    @Log(title = "预约信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(reservationService.deleteReservationByIds(ids));
    }
}
