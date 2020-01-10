package cn.lac.wechat.domain;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 服务商类
 */
@TableName("zly_home")
@Data
public class Home {


    @TableId(type = IdType.UUID)
    private String homeId;
    private String homeName;
    private String homeLocal;
    private String homeType;
    private String homeTel;
    private String homeDetail;
    private String homeText;
    private String homePath;
    private String homeTime;
    private String homeState;
    private BigDecimal homeLat;
    private BigDecimal homeLng;
    private Date createTime;
    private Date updateTime;
}
