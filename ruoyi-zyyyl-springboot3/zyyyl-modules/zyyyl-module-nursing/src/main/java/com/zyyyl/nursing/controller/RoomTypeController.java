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
import com.zyyyl.nursing.domain.RoomType;
import com.zyyyl.nursing.service.IRoomTypeService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping({ "/nursing/roomType", "/elder/roomType" })
public class RoomTypeController extends BaseController {

    @Autowired
    private IRoomTypeService roomTypeService;

    @PreAuthorize("@ss.hasPermi('elder:roomType:list')")
    @GetMapping("/list")
    public TableDataInfo list(RoomType roomType) {
        startPage();
        List<RoomType> list = roomTypeService.selectRoomTypeList(roomType);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('elder:roomType:list')")
    @GetMapping("/listAll")
    public AjaxResult listAll() {
        return success(roomTypeService.findRoomTypeListByStatus(1));
    }

    @PreAuthorize("@ss.hasPermi('elder:roomType:export')")
    @Log(title = "房型", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RoomType roomType) {
        List<RoomType> list = roomTypeService.selectRoomTypeList(roomType);
        new ExcelUtil<RoomType>(RoomType.class).exportExcel(response, list, "房型数据");
    }

    @PreAuthorize("@ss.hasPermi('elder:roomType:query')")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable Long id) {
        return success(roomTypeService.selectRoomTypeById(id));
    }

    @PreAuthorize("@ss.hasPermi('elder:roomType:add')")
    @Log(title = "房型", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RoomType roomType) {
        return toAjax(roomTypeService.insertRoomType(roomType));
    }

    @PreAuthorize("@ss.hasPermi('elder:roomType:edit')")
    @Log(title = "房型", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RoomType roomType) {
        return toAjax(roomTypeService.updateRoomType(roomType));
    }

    @PreAuthorize("@ss.hasPermi('elder:roomType:remove')")
    @Log(title = "房型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(roomTypeService.deleteRoomTypeByIds(ids));
    }
}
