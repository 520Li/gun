package cn.lac.wechat.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@TableName("bus_building_info")
@Data
public class BusBuildingInfo {

    private String bbiId;
    private String bbiBuName;
    private String bbiEnergyTag;

}
