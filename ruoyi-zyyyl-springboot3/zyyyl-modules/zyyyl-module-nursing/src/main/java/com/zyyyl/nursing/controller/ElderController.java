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
import com.zyyyl.nursing.domain.Elder;
import com.zyyyl.nursing.service.IElderService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/nursing/elder")
public class ElderController extends BaseController {

    @Autowired
    private IElderService elderService;

    @PreAuthorize("@ss.hasPermi('nursing:elder:list')")
    @GetMapping("/list")
    public TableDataInfo list(Elder elder) {
        startPage();
        List<Elder> list = elderService.selectElderList(elder);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('nursing:elder:export')")
    @Log(title = "老人", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Elder elder) {
        List<Elder> list = elderService.selectElderList(elder);
        new ExcelUtil<Elder>(Elder.class).exportExcel(response, list, "老人数据");
    }

    @PreAuthorize("@ss.hasPermi('nursing:elder:query')")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable Long id) {
        return success(elderService.selectElderById(id));
    }

    @PreAuthorize("@ss.hasPermi('nursing:elder:add')")
    @Log(title = "老人", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Elder elder) {
        return toAjax(elderService.insertElder(elder));
    }

    @PreAuthorize("@ss.hasPermi('nursing:elder:edit')")
    @Log(title = "老人", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Elder elder) {
        return toAjax(elderService.updateElder(elder));
    }

    @PreAuthorize("@ss.hasPermi('nursing:elder:remove')")
    @Log(title = "老人", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(elderService.deleteElderByIds(ids));
    }
}
