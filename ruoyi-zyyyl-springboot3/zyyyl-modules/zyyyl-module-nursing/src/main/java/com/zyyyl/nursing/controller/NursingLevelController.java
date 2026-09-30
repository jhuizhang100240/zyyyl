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
import com.zyyyl.nursing.domain.NursingLevel;
import com.zyyyl.nursing.service.INursingLevelService;
import com.zyyyl.nursing.vo.NursingLevelVo;

@RestController
@RequestMapping("/nursing/nursingLevel")
public class NursingLevelController extends BaseController {

    @Autowired
    private INursingLevelService nursingLevelService;

    @GetMapping("/listAll")
    public AjaxResult listAll() {
        return success(nursingLevelService.listAll());
    }

    @PreAuthorize("@ss.hasPermi('nursing:nursingLevel:list')")
    @GetMapping("/list")
    public TableDataInfo list(NursingLevel nursingLevel) {
        startPage();
        List<NursingLevelVo> list = nursingLevelService.selectNursingLevelList(nursingLevel);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('nursing:nursingLevel:query')")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable Long id) {
        return success(nursingLevelService.selectNursingLevelById(id));
    }

    @PreAuthorize("@ss.hasPermi('nursing:nursingLevel:add')")
    @Log(title = "护理等级", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody NursingLevel nursingLevel) {
        return toAjax(nursingLevelService.insertNursingLevel(nursingLevel));
    }

    @PreAuthorize("@ss.hasPermi('nursing:nursingLevel:edit')")
    @Log(title = "护理等级", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody NursingLevel nursingLevel) {
        return toAjax(nursingLevelService.updateNursingLevel(nursingLevel));
    }

    @PreAuthorize("@ss.hasPermi('nursing:nursingLevel:remove')")
    @Log(title = "护理等级", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(nursingLevelService.deleteNursingLevelByIds(ids));
    }
}
