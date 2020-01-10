package cn.lac.wechat.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 服务预约类 <br/>
 *
 * @author lac
 * @version 1.0
 * @date 2020/1/4 0004 - 9:32
 */
@TableName("zly_appoint")
@Data
public class Appoint {

    @TableId(type = IdType.UUID)
    private String appointId;
    private String serviceType;
    private Date appointTime;
    private String appointIphone;
    private String appointTitle;
    private String appointText;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy年MM月dd日 HH:mm", timezone = "GMT+8")
    private Date createTime;
    private Date updateTime;
    private String appointUser;
    private String applyStatus;
    private String applyUser;
    private String applyText;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy年MM月dd日 HH:mm", timezone = "GMT+8")
    private Date applyTime;
    private String applyIphone;


}
