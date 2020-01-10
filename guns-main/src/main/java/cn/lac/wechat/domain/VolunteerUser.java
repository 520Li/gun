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
 * 志愿者 -- 用户 关联类
 */
@TableName("zly_volunteer_user")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VolunteerUser {

    @TableId(type = IdType.UUID)
    private String zvuId;
    private String volunteerId;
    private String userId;
    private Date createTime;
    private Date updateTime;

}
