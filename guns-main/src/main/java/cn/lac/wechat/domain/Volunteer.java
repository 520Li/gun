package cn.lac.wechat.domain;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 志愿者活动类
 */
@TableName("zly_volunteer")
@Data
public class Volunteer {

    @TableId(type = IdType.UUID)
    private String voId;
    private String voName;
    private String voTel;
    private String voTime;
    private String voLocal;
    private String voState;
    private String voDuration;
    private String voPath;
    private String voText;
    private Date createTime;
    private Date updateTime;


    private String isJoin;


}
