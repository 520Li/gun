package cn.lac.wechat.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 活动 -- 用户关联 类 <br/>
 *
 * @author lac
 * @version 1.0
 * @date 2020/1/5 0005 - 22:28
 */
@TableName("zly_event_user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventUser {

    @TableId(type = IdType.UUID)
    private String id;
    private String eventId;
    private String userId;
    private String sign;
    private Integer signNum;
    private Date time;

}
