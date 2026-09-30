package com.zyyyl.nursing.vo;

import java.util.List;

import com.zyyyl.common.core.domain.entity.SysUser;

import lombok.Data;

/**
 * 房间下的床位视图。
 */
@Data
public class BedVo {

    private Long id;

    private String bedNumber;

    private Integer bedStatus;

    private Long roomId;

    private String ename;

    private Long elderId;

    private List<SysUser> userVos;
}
