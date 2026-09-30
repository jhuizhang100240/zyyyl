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
import com.zyyyl.common.core.page.TableDataInfo;
import com.zyyyl.common.enums.BusinessType;
import com.zyyyl.nursing.domain.Room;
import com.zyyyl.nursing.service.IRoomService;
import com.zyyyl.nursing.vo.RoomVo;

@RestController
@RequestMapping({ "/nursing/room", "/elder/room" })
public class RoomController extends BaseController {

    @Autowired
    private IRoomService roomService;

    @GetMapping("/getRoomsWithNurByFloorId/{floorId}")
    public R<List<RoomVo>> getRoomsWithNurByFloorId(@PathVariable Long floorId) {
        return R.ok(roomService.getRoomsWithNurByFloorId(floorId));
    }

    @GetMapping("/one/{id}")
    public AjaxResult getById(@PathVariable Long id) {
        return success(roomService.getRoomById(id));
    }

    @GetMapping("/getRoomsByFloorId/{floorId}")
    public R<List<RoomVo>> getRoomsByFloorId(@PathVariable Long floorId) {
        return R.ok(roomService.getRoomsByFloorId(floorId));
    }

    @PreAuthorize("@ss.hasPermi('elder:room:list')")
    @GetMapping("/list")
    public TableDataInfo list(Room room) {
        startPage();
        List<Room> list = roomService.selectRoomList(room);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('elder:room:query')")
    @GetMapping("/{id}")
    public R<Room> getInfo(@PathVariable Long id) {
        return R.ok(roomService.selectRoomById(id));
    }

    @PreAuthorize("@ss.hasPermi('elder:room:add')")
    @Log(title = "房间", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Room room) {
        return toAjax(roomService.insertRoom(room));
    }

    @PreAuthorize("@ss.hasPermi('elder:room:edit')")
    @Log(title = "房间", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Room room) {
        return toAjax(roomService.updateRoom(room));
    }

    @PreAuthorize("@ss.hasPermi('elder:room:remove')")
    @Log(title = "房间", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(roomService.deleteRoomByIds(ids));
    }
}
