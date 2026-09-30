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
import com.zyyyl.nursing.domain.HealthAssessment;
import com.zyyyl.nursing.dto.HealthAssessmentDto;
import com.zyyyl.nursing.service.IHealthAssessmentService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/nursing/healthAssessment")
public class HealthAssessmentController extends BaseController {

    @Autowired
    private IHealthAssessmentService healthAssessmentService;

    @PreAuthorize("@ss.hasPermi('nursing:healthAssessment:list')")
    @GetMapping("/list")
    public TableDataInfo list(HealthAssessment healthAssessment) {
        startPage();
        List<HealthAssessment> list = healthAssessmentService.selectHealthAssessmentList(healthAssessment);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('nursing:healthAssessment:export')")
    @Log(title = "健康评估", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HealthAssessment healthAssessment) {
        List<HealthAssessment> list = healthAssessmentService.selectHealthAssessmentList(healthAssessment);
        new ExcelUtil<HealthAssessment>(HealthAssessment.class).exportExcel(response, list, "健康评估数据");
    }

    @PreAuthorize("@ss.hasPermi('nursing:healthAssessment:query')")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable Long id) {
        return success(healthAssessmentService.selectHealthAssessmentById(id));
    }

    @PreAuthorize("@ss.hasPermi('nursing:healthAssessment:add')")
    @Log(title = "健康评估", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HealthAssessmentDto dto) {
        return success(healthAssessmentService.insertHealthAssessment(dto));
    }

    @PreAuthorize("@ss.hasPermi('nursing:healthAssessment:edit')")
    @Log(title = "健康评估", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HealthAssessment healthAssessment) {
        return toAjax(healthAssessmentService.updateHealthAssessment(healthAssessment));
    }

    @PreAuthorize("@ss.hasPermi('nursing:healthAssessment:remove')")
    @Log(title = "健康评估", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(healthAssessmentService.deleteHealthAssessmentByIds(ids));
    }
}
