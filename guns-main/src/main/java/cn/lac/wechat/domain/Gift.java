package cn.lac.wechat.domain;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;


/**
 * 兑换品类
 */
@TableName("zly_gift")
@Data
public class Gift {

    @TableId(type = IdType.UUID)
    private String giftId;
    private String giftName;
    private Integer giftCore;
    private String giftText;
    private String giftPath;
    private Integer giftNum;
    private String giftState;
    private Date createTime;
    private Date updateTime;


}
