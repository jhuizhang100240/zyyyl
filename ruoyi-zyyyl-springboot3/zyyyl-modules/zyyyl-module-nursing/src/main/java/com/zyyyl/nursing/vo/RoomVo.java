package com.zyyyl.nursing.vo;

import java.util.List;

import lombok.Data;

/**
 * 房间及其床位视图。
 */
@Data
public class RoomVo {

    private Long id;

    private String floorName;

    private String floorId;

    private String roomId;

    private String code;

    private String price;

    private List<BedVo> bedVoList;
}
