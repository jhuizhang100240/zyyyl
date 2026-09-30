package com.zyyyl.nursing.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.zyyyl.common.core.domain.entity.SysUser;
import com.zyyyl.common.core.page.TableDataInfo;
import com.zyyyl.common.exception.ServiceException;
import com.zyyyl.common.utils.StringUtils;
import com.zyyyl.nursing.domain.CheckIn;
import com.zyyyl.nursing.domain.CheckInConfig;
import com.zyyyl.nursing.domain.Elder;
import com.zyyyl.nursing.domain.NursingLevel;
import com.zyyyl.nursing.domain.NursingPlan;
import com.zyyyl.nursing.domain.NursingProject;
import com.zyyyl.nursing.domain.NursingTask;
import com.zyyyl.nursing.dto.NursingTaskDto;
import com.zyyyl.nursing.dto.TaskDto;
import com.zyyyl.nursing.mapper.CheckInConfigMapper;
import com.zyyyl.nursing.mapper.CheckInMapper;
import com.zyyyl.nursing.mapper.ElderMapper;
import com.zyyyl.nursing.mapper.NursingElderMapper;
import com.zyyyl.nursing.mapper.NursingTaskAssigneeMapper;
import com.zyyyl.nursing.mapper.NursingTaskMapper;
import com.zyyyl.nursing.service.INursingLevelService;
import com.zyyyl.nursing.service.INursingPlanService;
import com.zyyyl.nursing.service.INursingProjectService;
import com.zyyyl.nursing.service.INursingTaskService;
import com.zyyyl.nursing.vo.NursingPlanVo;
import com.zyyyl.nursing.vo.NursingProjectPlanVo;
import com.zyyyl.nursing.vo.NursingTaskVo;
import com.zyyyl.system.service.ISysUserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NursingTaskServiceImpl extends ServiceImpl<NursingTaskMapper, NursingTask>
        implements INursingTaskService {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    @Autowired
    private ElderMapper elderMapper;

    @Autowired
    private CheckInMapper checkInMapper;

    @Autowired
    private CheckInConfigMapper checkInConfigMapper;

    @Autowired
    private NursingElderMapper nursingElderMapper;

    @Autowired
    private NursingTaskAssigneeMapper nursingTaskAssigneeMapper;

    @Autowired
    private INursingLevelService nursingLevelService;

    @Autowired
    private INursingPlanService nursingPlanService;

    @Autowired
    private INursingProjectService nursingProjectService;

    @Autowired
    private ISysUserService userService;

    @Override
    public NursingTaskVo selectNursingTaskById(Long id) {
        NursingTask task = getById(id);
        if (task == null) {
            throw new ServiceException("护理任务不存在");
        }
        NursingTaskVo vo = new NursingTaskVo();
        org.springframework.beans.BeanUtils.copyProperties(task, vo);
        vo.setNursingName(resolveNursingNames(task));

        CheckIn checkIn = checkInMapper.selectLatestByElderId(task.getElderId());
        if (checkIn != null) {
            vo.setNursingLevelName(checkIn.getNursingLevelName());
        }
        Elder elder = elderMapper.selectOneById(task.getElderId());
        if (elder != null) {
            vo.setAge(resolveAge(elder));
            vo.setSex(Integer.valueOf(0).equals(elder.getSex()) ? "女" : "男");
        }
        if (StringUtils.isNotEmpty(task.getUpdateBy()) && task.getUpdateBy().matches("\\d+")) {
            SysUser updater = userService.selectUserById(Long.valueOf(task.getUpdateBy()));
            vo.setUpdater(updater == null ? null : updater.getNickName());
        }
        return vo;
    }

    @Override
    public TableDataInfo selectNursingTaskList(NursingTaskDto dto) {
        int pageNum = dto.getPageNum() == null ? 1 : dto.getPageNum();
        int pageSize = dto.getPageSize() == null ? 10 : dto.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<NursingTask> tasks = mapper.selectByPage(dto);
        Page<NursingTask> page = (Page<NursingTask>) tasks;
        for (NursingTask task : page.getResult()) {
            task.setNursingName(resolveNursingNames(task));
        }
        TableDataInfo result = new TableDataInfo();
        result.setCode(200);
        result.setMsg("请求成功");
        result.setRows(page.getResult());
        result.setTotal(page.getTotal());
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertNursingTask(NursingTask nursingTask) {
        validateTask(nursingTask);
        save(nursingTask);
        syncAssignees(nursingTask.getId(), nursingTask.getNursingId());
        return 1;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateNursingTask(NursingTask nursingTask) {
        if (nursingTask == null || nursingTask.getId() == null) {
            throw new ServiceException("护理任务主键不能为空");
        }
        updateById(nursingTask);
        if (nursingTask.getNursingId() != null) {
            syncAssignees(nursingTask.getId(), nursingTask.getNursingId());
        }
        return 1;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteNursingTaskByIds(Long[] ids) {
        nursingTaskAssigneeMapper.deleteByTaskIds(Arrays.asList(ids));
        return removeByIds(Arrays.asList(ids)) ? 1 : 0;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createMonthTask(Elder elder) {
        if (elder == null || elder.getId() == null) {
            log.info("创建护理任务跳过：老人为空");
            return;
        }
        CheckIn checkIn = checkInMapper.selectLatestByElderId(elder.getId());
        if (checkIn == null) {
            log.info("创建护理任务跳过：老人 {} 无入住记录", elder.getId());
            return;
        }
        CheckInConfig config = checkInConfigMapper.selectByCheckInId(checkIn.getId());
        if (config == null || config.getNursingLevelId() == null) {
            log.info("创建护理任务跳过：入住 {} 无护理配置", checkIn.getId());
            return;
        }
        NursingLevel level = nursingLevelService.selectNursingLevelById(config.getNursingLevelId());
        if (level == null || level.getPlanId() == null) {
            log.info("创建护理任务跳过：护理等级 {} 未关联计划", config.getNursingLevelId());
            return;
        }
        NursingPlanVo plan = nursingPlanService.selectNursingPlanById(level.getPlanId());
        if (plan == null || plan.getProjectPlans() == null || plan.getProjectPlans().isEmpty()) {
            log.info("创建护理任务跳过：护理计划 {} 无项目", level.getPlanId());
            return;
        }

        List<Long> assigneeIds = nursingElderMapper.selectNursingIdsByElderId(elder.getId());
        Map<Long, String> projectNames = nursingProjectService.list().stream()
                .collect(Collectors.toMap(NursingProject::getId, NursingProject::getName, (a, b) -> a));
        List<NursingTask> tasks = buildTasks(elder, checkIn, config, plan, assigneeIds, projectNames);
        if (tasks.isEmpty()) {
            return;
        }

        Set<String> generatedKeys = new HashSet<>();
        for (NursingTask task : tasks) {
            String key = task.getElderId() + ":" + task.getProjectId() + ":" + task.getEstimatedServerTime();
            if (!generatedKeys.add(key)) {
                continue;
            }
            if (mapper.countByUniqueKey(task.getElderId(), task.getProjectId(), task.getEstimatedServerTime()) > 0) {
                continue;
            }
            save(task);
            if (!assigneeIds.isEmpty()) {
                nursingTaskAssigneeMapper.batchInsert(task.getId(), assigneeIds);
            }
        }
        log.info("护理任务生成完成，老人ID={}，候选任务数={}", elder.getId(), tasks.size());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int cancelTask(TaskDto dto) {
        NursingTask task = requirePendingTask(dto);
        task.setStatus(3);
        task.setCancelReason(dto.getReason());
        return updateById(task) ? 1 : 0;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int rescheduleTask(TaskDto dto) {
        if (dto == null || dto.getEstimatedServerTime() == null) {
            throw new ServiceException("改期时间不能为空");
        }
        NursingTask task = requirePendingTask(dto);
        task.setEstimatedServerTime(dto.getEstimatedServerTime());
        return updateById(task) ? 1 : 0;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int executeTask(TaskDto dto) {
        NursingTask task = requirePendingTask(dto);
        task.setStatus(2);
        task.setRealServerTime(dto.getEstimatedServerTime() == null
                ? LocalDateTime.now()
                : dto.getEstimatedServerTime());
        task.setTaskImage(dto.getTaskImage());
        task.setMark(dto.getMark());
        return updateById(task) ? 1 : 0;
    }

    private NursingTask requirePendingTask(TaskDto dto) {
        if (dto == null || dto.getTaskId() == null) {
            throw new ServiceException("护理任务ID不能为空");
        }
        NursingTask task = getById(dto.getTaskId());
        if (task == null) {
            throw new ServiceException("护理任务不存在");
        }
        if (!Integer.valueOf(1).equals(task.getStatus())) {
            throw new ServiceException("护理任务当前状态不允许操作");
        }
        return task;
    }

    private void validateTask(NursingTask task) {
        if (task == null || task.getElderId() == null || task.getProjectId() == null
                || task.getEstimatedServerTime() == null) {
            throw new ServiceException("老人、护理项目和预计服务时间不能为空");
        }
        if (task.getStatus() == null) {
            task.setStatus(1);
        }
    }

    private List<NursingTask> buildTasks(Elder elder, CheckIn checkIn, CheckInConfig config,
            NursingPlanVo plan, List<Long> assigneeIds, Map<Long, String> projectNames) {
        LocalDate today = LocalDate.now();
        LocalDate start = config.getFeeStartDate() == null
                ? today
                : max(today, config.getFeeStartDate().toLocalDate());
        LocalDate monthEnd = start.withDayOfMonth(start.lengthOfMonth());
        LocalDate end = config.getFeeEndDate() != null
                && config.getFeeEndDate().toLocalDate().isBefore(monthEnd)
                        ? config.getFeeEndDate().toLocalDate()
                        : monthEnd;
        if (end.isBefore(start)) {
            return Collections.emptyList();
        }

        String nursingIdText = assigneeIds == null || assigneeIds.isEmpty()
                ? ""
                : assigneeIds.stream().map(String::valueOf).collect(Collectors.joining(","));
        List<NursingTask> tasks = new ArrayList<>();
        for (NursingProjectPlanVo projectPlan : plan.getProjectPlans()) {
            LocalTime executeTime = parseTime(projectPlan.getExecuteTime());
            int frequency = projectPlan.getExecuteFrequency() == null
                    ? 1
                    : Math.max(1, projectPlan.getExecuteFrequency().intValue());
            long cycle = projectPlan.getExecuteCycle() == null ? 0L : projectPlan.getExecuteCycle();
            if (cycle == 0L) {
                addDailyTasks(tasks, elder, checkIn, projectPlan, projectNames, nursingIdText,
                        start, end, executeTime, frequency);
            } else if (cycle == 1L) {
                addWeeklyTasks(tasks, elder, checkIn, projectPlan, projectNames, nursingIdText,
                        start, end, executeTime, frequency);
            } else {
                addMonthlyTasks(tasks, elder, checkIn, projectPlan, projectNames, nursingIdText,
                        start, end, executeTime, frequency);
            }
        }
        return tasks;
    }

    private void addDailyTasks(List<NursingTask> tasks, Elder elder, CheckIn checkIn,
            NursingProjectPlanVo projectPlan, Map<Long, String> projectNames, String nursingIdText,
            LocalDate start, LocalDate end, LocalTime executeTime, int frequency) {
        for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
            LocalDateTime base = LocalDateTime.of(date, executeTime);
            for (int i = 0; i < frequency; i++) {
                LocalDateTime taskTime = base.plusMinutes((long) i * 24 * 60 / frequency);
                if (!taskTime.toLocalDate().isAfter(end)) {
                    tasks.add(buildTask(elder, checkIn, projectPlan, projectNames, nursingIdText, taskTime));
                }
            }
        }
    }

    private void addWeeklyTasks(List<NursingTask> tasks, Elder elder, CheckIn checkIn,
            NursingProjectPlanVo projectPlan, Map<Long, String> projectNames, String nursingIdText,
            LocalDate start, LocalDate end, LocalTime executeTime, int frequency) {
        for (LocalDate weekStart = start; !weekStart.isAfter(end); weekStart = weekStart.plusDays(7)) {
            for (int i = 0; i < frequency; i++) {
                LocalDate date = weekStart.plusDays((long) i * 7 / frequency);
                if (!date.isAfter(end)) {
                    tasks.add(buildTask(elder, checkIn, projectPlan, projectNames, nursingIdText,
                            LocalDateTime.of(date, executeTime)));
                }
            }
        }
    }

    private void addMonthlyTasks(List<NursingTask> tasks, Elder elder, CheckIn checkIn,
            NursingProjectPlanVo projectPlan, Map<Long, String> projectNames, String nursingIdText,
            LocalDate start, LocalDate end, LocalTime executeTime, int frequency) {
        LocalDate cursor = start.withDayOfMonth(1);
        while (!cursor.isAfter(end)) {
            int monthLength = cursor.lengthOfMonth();
            for (int i = 0; i < frequency; i++) {
                LocalDate date = cursor.plusDays((long) i * monthLength / frequency);
                if (!date.isBefore(start) && !date.isAfter(end)) {
                    tasks.add(buildTask(elder, checkIn, projectPlan, projectNames, nursingIdText,
                            LocalDateTime.of(date, executeTime)));
                }
            }
            cursor = cursor.plusMonths(1);
        }
    }

    private NursingTask buildTask(Elder elder, CheckIn checkIn, NursingProjectPlanVo projectPlan,
            Map<Long, String> projectNames, String nursingIdText, LocalDateTime taskTime) {
        NursingTask task = new NursingTask();
        task.setStatus(1);
        task.setNursingId(nursingIdText);
        task.setElderId(elder.getId());
        task.setElderName(elder.getName());
        task.setBedNumber(checkIn.getBedNumber());
        task.setProjectId(projectPlan.getProjectId());
        task.setProjectName(projectNames.get(projectPlan.getProjectId()));
        task.setEstimatedServerTime(taskTime);
        return task;
    }

    private LocalTime parseTime(String value) {
        if (StringUtils.isEmpty(value)) {
            throw new ServiceException("护理计划执行时间不能为空");
        }
        String normalized = value.length() > 5 ? value.substring(0, 5) : value;
        try {
            return LocalTime.parse(normalized, TIME_FORMATTER);
        } catch (Exception e) {
            throw new ServiceException("护理计划执行时间格式错误：" + value);
        }
    }

    private LocalDate max(LocalDate first, LocalDate second) {
        return first.isAfter(second) ? first : second;
    }

    private List<String> resolveNursingNames(NursingTask task) {
        List<Long> ids = nursingTaskAssigneeMapper.selectNursingIdsByTaskId(task.getId());
        if (ids.isEmpty() && StringUtils.isNotEmpty(task.getNursingId())) {
            ids = Arrays.stream(task.getNursingId().split(","))
                    .filter(StringUtils::isNotEmpty)
                    .map(Long::valueOf)
                    .collect(Collectors.toList());
        }
        if (ids.isEmpty()) {
            return Collections.emptyList();
        }
        List<String> names = new ArrayList<>();
        for (Long id : ids) {
            SysUser user = userService.selectUserById(id);
            if (user != null) {
                names.add(user.getNickName());
            }
        }
        return names;
    }

    private void syncAssignees(Long taskId, String nursingIdText) {
        nursingTaskAssigneeMapper.deleteByTaskIds(Collections.singletonList(taskId));
        if (StringUtils.isEmpty(nursingIdText)) {
            return;
        }
        List<Long> ids = Arrays.stream(nursingIdText.split(","))
                .filter(StringUtils::isNotEmpty)
                .map(Long::valueOf)
                .collect(Collectors.toList());
        if (!ids.isEmpty()) {
            nursingTaskAssigneeMapper.batchInsert(taskId, ids);
        }
    }

    private Integer resolveAge(Elder elder) {
        try {
            return com.zyyyl.common.utils.IdCardUtils.getAge(elder.getIdCardNo());
        } catch (Exception e) {
            return null;
        }
    }
}
