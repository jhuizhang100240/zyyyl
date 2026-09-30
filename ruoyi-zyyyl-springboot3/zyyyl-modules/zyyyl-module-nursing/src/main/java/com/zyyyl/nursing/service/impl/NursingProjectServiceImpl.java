package com.zyyyl.nursing.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mybatisflex.spring.service.impl.ServiceImpl;
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

    private void validate(NursingProject nursingProject) {
        if (nursingProject == null || nursingProject.getName() == null) {
            throw new ServiceException("护理项目名称不能为空");
        }
    }
}
