package cn.lac.wechat.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 活动类 <br/>
 *
 * @author lac
 * @version 1.0
 * @date 2020/1/4 0004 - 18:56
 */
@TableName("zly_event")
@Data
public class Event {
    @TableId(type = IdType.UUID)
    private String eventId;
    private String eventName;
    private String eventType;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy年MM月dd日 HH:mm", timezone = "GMT+8")
    private Date eventStart;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy年MM月dd日 HH:mm", timezone = "GMT+8")
    private Date eventEnd;
    private String eventLocal;
    private Integer eventLimit;
    private Integer eventReal;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy年MM月dd日 HH:mm", timezone = "GMT+8")
    private Date eventRestart;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy年MM月dd日 HH:mm", timezone = "GMT+8")
    private Date eventReend;
    private String eventText;
    private String eventStatus;
    private String eventState;
    private Date createTime;
    private Date updateTime;
}
