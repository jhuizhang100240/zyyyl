package com.zyyyl.nursing.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.zyyyl.common.core.page.TableDataInfo;
import com.zyyyl.common.exception.ServiceException;
import com.zyyyl.nursing.domain.NursingProject;
import com.zyyyl.nursing.mapper.NursingProjectMapper;
import com.zyyyl.nursing.service.INursingProjectService;
import com.zyyyl.nursing.vo.NursingProjectVo;

@Service
public class NursingProjectServiceImpl extends ServiceImpl<NursingProjectMapper, NursingProject>
        implements INursingProjectService {

    @Override
    public NursingProject selectNursingProjectById(Long id) {
        return getById(id);
    }

    @Override
    public List<NursingProject> selectNursingProjectList(NursingProject nursingProject) {
        return mapper.selectNursingProjectList(nursingProject);
    }

    @Override
    public int insertNursingProject(NursingProject nursingProject) {
        validate(nursingProject);
        if (nursingProject.getStatus() == null) {
            nursingProject.setStatus(1);
        }
        return save(nursingProject) ? 1 : 0;
    }

    @Override
    public int updateNursingProject(NursingProject nursingProject) {
        validate(nursingProject);
        return updateById(nursingProject) ? 1 : 0;
    }

    @Override
    public int deleteNursingProjectByIds(Long[] ids) {
        return removeByIds(Arrays.asList(ids)) ? 1 : 0;
    }

    @Override
    public int deleteNursingProjectById(Long id) {
        return removeById(id) ? 1 : 0;
    }

    @Override
    public List<NursingProjectVo> listAll() {
        return mapper.listAll();
    }

    @Override
    public TableDataInfo selectMemberPage(Integer pageNum, Integer pageSize, String name) {
        int pageNo = pageNum == null || pageNum < 1 ? 1 : pageNum;
        int size = pageSize == null || pageSize < 1 ? 10 : Math.min(pageSize, 100);
        PageHelper.startPage(pageNo, size);
        NursingProject query = new NursingProject();
        query.setName(name);
        List<NursingProject> list = mapper.selectNursingProjectList(query);
        Page<NursingProject> page = (Page<NursingProject>) list;
        TableDataInfo result = new TableDataInfo();
        result.setCode(200);
        result.setMsg("请求成功");
        result.setRows(page.getResult());
        result.setTotal(page.getTotal());
        return result;
    }

    private void validate(NursingProject nursingProject) {
        if (nursingProject == null || nursingProject.getName() == null) {
            throw new ServiceException("护理项目名称不能为空");
        }
    }
}
