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
import com.zyyyl.nursing.domain.Bed;
import com.zyyyl.nursing.service.IBedService;

@RestController
@RequestMapping({ "/nursing/bed", "/elder/bed" })
public class BedController extends BaseController {

    @Autowired
    private IBedService bedService;

    @PreAuthorize("@ss.hasPermi('elder:bed:list')")
    @GetMapping("/list")
    public TableDataInfo list(Bed bed) {
        startPage();
        List<Bed> list = bedService.selectBedList(bed);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('elder:bed:query')")
    @GetMapping("/{id}")
    public R<Bed> getInfo(@PathVariable Long id) {
        return R.ok(bedService.selectBedById(id));
    }

    @PreAuthorize("@ss.hasPermi('elder:bed:add')")
    @Log(title = "床位", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Bed bed) {
        return toAjax(bedService.insertBed(bed));
    }

    @PreAuthorize("@ss.hasPermi('elder:bed:edit')")
    @Log(title = "床位", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Bed bed) {
        return toAjax(bedService.updateBed(bed));
    }

    @PreAuthorize("@ss.hasPermi('elder:bed:remove')")
    @Log(title = "床位", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(bedService.deleteBedByIds(ids));
    }
}
