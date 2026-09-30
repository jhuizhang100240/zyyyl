package com.zyyyl.nursing.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zyyyl.common.annotation.Log;
import com.zyyyl.common.core.controller.BaseController;
import com.zyyyl.common.core.domain.AjaxResult;
import com.zyyyl.common.core.page.TableDataInfo;
import com.zyyyl.common.enums.BusinessType;
import com.zyyyl.common.utils.poi.ExcelUtil;
import com.zyyyl.nursing.domain.CheckIn;
import com.zyyyl.nursing.dto.CheckInApplyDto;
import com.zyyyl.nursing.service.ICheckInService;
import com.zyyyl.nursing.vo.CheckInDetailVo;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/nursing/checkIn")
public class CheckInController extends BaseController {

    @Autowired
    private ICheckInService checkInService;

    @GetMapping("/detail/{id}")
    public AjaxResult detail(@PathVariable Long id) {
        CheckInDetailVo vo = checkInService.detail(id);
        return success(vo);
    }

    @PostMapping("/apply")
    public AjaxResult apply(@RequestBody CheckInApplyDto dto) {
        checkInService.apply(dto);
        return success();
    }

    @PreAuthorize("@ss.hasPermi('nursing:checkIn:list')")
    @GetMapping("/list")
    public TableDataInfo list(CheckIn checkIn) {
        startPage();
        List<CheckIn> list = checkInService.selectCheckInList(checkIn);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('nursing:checkIn:export')")
    @Log(title = "入住", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CheckIn checkIn) {
        List<CheckIn> list = checkInService.selectCheckInList(checkIn);
        new ExcelUtil<CheckIn>(CheckIn.class).exportExcel(response, list, "入住数据");
    }

    @PreAuthorize("@ss.hasPermi('nursing:checkIn:query')")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable Long id) {
        return success(checkInService.selectCheckInById(id));
    }

    @PreAuthorize("@ss.hasPermi('nursing:checkIn:add')")
    @Log(title = "入住", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CheckIn checkIn) {
        return toAjax(checkInService.insertCheckIn(checkIn));
    }

    @PreAuthorize("@ss.hasPermi('nursing:checkIn:edit')")
    @Log(title = "入住", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CheckIn checkIn) {
        return toAjax(checkInService.updateCheckIn(checkIn));
    }

    @PreAuthorize("@ss.hasPermi('nursing:checkIn:remove')")
    @Log(title = "入住", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(checkInService.deleteCheckInByIds(ids));
    }
}
