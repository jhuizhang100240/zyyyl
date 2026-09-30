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
import com.zyyyl.nursing.domain.Contract;
import com.zyyyl.nursing.service.IContractService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/nursing/contract")
public class ContractController extends BaseController {

    @Autowired
    private IContractService contractService;

    @PreAuthorize("@ss.hasPermi('nursing:contract:list')")
    @GetMapping("/list")
    public TableDataInfo list(Contract contract) {
        startPage();
        List<Contract> list = contractService.selectContractList(contract);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('nursing:contract:export')")
    @Log(title = "合同", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Contract contract) {
        List<Contract> list = contractService.selectContractList(contract);
        new ExcelUtil<Contract>(Contract.class).exportExcel(response, list, "合同数据");
    }

    @PreAuthorize("@ss.hasPermi('nursing:contract:query')")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable Long id) {
        return success(contractService.selectContractById(id));
    }

    @PreAuthorize("@ss.hasPermi('nursing:contract:add')")
    @Log(title = "合同", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Contract contract) {
        return toAjax(contractService.insertContract(contract));
    }

    @PreAuthorize("@ss.hasPermi('nursing:contract:edit')")
    @Log(title = "合同", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Contract contract) {
        return toAjax(contractService.updateContract(contract));
    }

    @PreAuthorize("@ss.hasPermi('nursing:contract:remove')")
    @Log(title = "合同", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(contractService.deleteContractByIds(ids));
    }
}
