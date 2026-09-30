package com.zyyyl.nursing.vo;

import java.util.List;

import lombok.Data;

/**
 * 楼层-房间-床位树节点。
 */
@Data
public class TreeVo {

    private String value;

    private String label;

    private List<TreeVo> children;
}
