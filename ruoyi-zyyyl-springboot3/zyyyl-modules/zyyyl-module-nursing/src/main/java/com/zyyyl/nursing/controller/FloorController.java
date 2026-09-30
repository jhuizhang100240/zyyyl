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
import com.zyyyl.common.core.domain.R;
import com.zyyyl.common.enums.BusinessType;
import com.zyyyl.nursing.domain.Floor;
import com.zyyyl.nursing.service.IFloorService;
import com.zyyyl.nursing.vo.TreeVo;

@RestController
@RequestMapping({ "/nursing/floor", "/elder/floor" })
public class FloorController extends BaseController {

    @Autowired
    private IFloorService floorService;

    @GetMapping("/getAllFloorsWithNur")
    public R<List<Floor>> getAllFloorsWithNur() {
        return R.ok(floorService.selectAllByNur());
    }

    @GetMapping("/getRoomAndBedByBedStatus/{status}")
    public AjaxResult getRoomAndBedByBedStatus(@PathVariable Integer status) {
        List<TreeVo> list = floorService.getRoomAndBedByBedStatus(status);
        return success(list);
    }

    @PreAuthorize("@ss.hasPermi('elder:floor:list')")
    @GetMapping("/list")
    public R<List<Floor>> list() {
        return R.ok(floorService.list());
    }

    @PreAuthorize("@ss.hasPermi('elder:floor:query')")
    @GetMapping("/{id}")
    public R<Floor> getInfo(@PathVariable Long id) {
        return R.ok(floorService.selectFloorById(id));
    }

    @PreAuthorize("@ss.hasPermi('elder:floor:add')")
    @Log(title = "楼层", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Floor floor) {
        return toAjax(floorService.insertFloor(floor));
    }

    @PreAuthorize("@ss.hasPermi('elder:floor:edit')")
    @Log(title = "楼层", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Floor floor) {
        return toAjax(floorService.updateFloor(floor));
    }

    @PreAuthorize("@ss.hasPermi('elder:floor:remove')")
    @Log(title = "楼层", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(floorService.deleteFloorByIds(ids));
    }
}
