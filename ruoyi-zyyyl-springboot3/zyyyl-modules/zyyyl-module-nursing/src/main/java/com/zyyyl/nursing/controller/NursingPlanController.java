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
import com.zyyyl.nursing.domain.NursingPlan;
import com.zyyyl.nursing.dto.NursingPlanDto;
import com.zyyyl.nursing.service.INursingPlanService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/nursing/nursingPlan")
public class NursingPlanController extends BaseController {

    @Autowired
    private INursingPlanService nursingPlanService;

    @GetMapping("/all")
    public AjaxResult listAll() {
        return success(nursingPlanService.listAll());
    }

    @PreAuthorize("@ss.hasPermi('nursing:nursingPlan:list')")
    @GetMapping("/list")
    public TableDataInfo list(NursingPlan nursingPlan) {
        startPage();
        List<NursingPlan> list = nursingPlanService.selectNursingPlanList(nursingPlan);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('nursing:nursingPlan:export')")
    @Log(title = "护理计划", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, NursingPlan nursingPlan) {
        List<NursingPlan> list = nursingPlanService.selectNursingPlanList(nursingPlan);
        new ExcelUtil<NursingPlan>(NursingPlan.class).exportExcel(response, list, "护理计划数据");
    }

    @PreAuthorize("@ss.hasPermi('nursing:nursingPlan:query')")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable Long id) {
        return success(nursingPlanService.selectNursingPlanById(id));
    }

    @PreAuthorize("@ss.hasPermi('nursing:nursingPlan:add')")
    @Log(title = "护理计划", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody NursingPlanDto dto) {
        return toAjax(nursingPlanService.insertNursingPlan(dto));
    }

    @PreAuthorize("@ss.hasPermi('nursing:nursingPlan:edit')")
    @Log(title = "护理计划", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody NursingPlanDto dto) {
        return toAjax(nursingPlanService.updateNursingPlan(dto));
    }

    @PreAuthorize("@ss.hasPermi('nursing:nursingPlan:remove')")
    @Log(title = "护理计划", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(nursingPlanService.deleteNursingPlanByIds(ids));
    }
}
