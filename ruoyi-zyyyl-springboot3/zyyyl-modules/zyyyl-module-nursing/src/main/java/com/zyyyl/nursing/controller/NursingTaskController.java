package com.zyyyl.nursing.controller;

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
import com.zyyyl.common.core.domain.R;
import com.zyyyl.common.core.page.TableDataInfo;
import com.zyyyl.common.enums.BusinessType;
import com.zyyyl.nursing.domain.NursingTask;
import com.zyyyl.nursing.dto.NursingTaskDto;
import com.zyyyl.nursing.dto.TaskDto;
import com.zyyyl.nursing.service.INursingTaskService;
import com.zyyyl.nursing.vo.NursingTaskVo;

@RestController
@RequestMapping("/nursing/nursingTask")
public class NursingTaskController extends BaseController {

    @Autowired
    private INursingTaskService nursingTaskService;

    @GetMapping("/list")
    public TableDataInfo list(NursingTaskDto dto) {
        return nursingTaskService.selectNursingTaskList(dto);
    }

    @GetMapping("/{id}")
    public R<NursingTaskVo> getInfo(@PathVariable Long id) {
        return R.ok(nursingTaskService.selectNursingTaskById(id));
    }

    @PutMapping("/cancel")
    public AjaxResult cancel(@RequestBody TaskDto dto) {
        return toAjax(nursingTaskService.cancelTask(dto));
    }

    @PutMapping("/updateTime")
    public AjaxResult updateTime(@RequestBody TaskDto dto) {
        return toAjax(nursingTaskService.rescheduleTask(dto));
    }

    @PutMapping("/do")
    public AjaxResult doTask(@RequestBody TaskDto dto) {
        return toAjax(nursingTaskService.executeTask(dto));
    }

    @PreAuthorize("@ss.hasPermi('nursing:nursingTask:add')")
    @Log(title = "护理任务", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody NursingTask nursingTask) {
        return toAjax(nursingTaskService.insertNursingTask(nursingTask));
    }

    @PreAuthorize("@ss.hasPermi('nursing:nursingTask:edit')")
    @Log(title = "护理任务", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody NursingTask nursingTask) {
        return toAjax(nursingTaskService.updateNursingTask(nursingTask));
    }

    @PreAuthorize("@ss.hasPermi('nursing:nursingTask:remove')")
    @Log(title = "护理任务", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(nursingTaskService.deleteNursingTaskByIds(ids));
    }
}
