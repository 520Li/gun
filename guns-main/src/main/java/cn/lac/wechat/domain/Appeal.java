package cn.lac.wechat.domain;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 事件举报类
 */
@TableName("zly_appeal")
@Data
public class Appeal {
    @TableId(type = IdType.UUID)
    private String appealId;
    private String appealType;
    private String appealUser;
    private String appealTel;
    private String appealLocal;
    private String appealDegree;
    private String appealText;
    private String appealPath;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy年MM月dd日 HH:mm", timezone = "GMT+8")
    private Date createTime;
    private Date updateTime;
    private String appealStatus;
    private String acceptorUser;
    private String acceptorTel;
    private String appealCore;
}
