package com.zyyyl.nursing.vo;

import java.util.List;

import com.zyyyl.nursing.domain.NursingTask;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class NursingTaskVo extends NursingTask {

    private String nursingLevelName;

    private Integer age;

    private List<String> nursingName;

    private String sex;

    private String updater;
}
