package cn.lac.wechat.domain;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;


@Data
@TableName("zly_appeal_log")
public class AppealLog {


    @TableId(type = IdType.UUID)
    private String zalId;
    private String appealId;
    private String acceptorUser;
    private String acceptorTel;
    private String acceptorText;
    private String acceptorStatus;
    private Date createTime;
}
