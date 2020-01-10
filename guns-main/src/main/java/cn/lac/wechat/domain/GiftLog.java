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
 * 兑换记录类
 */
@TableName("zly_gift_log")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GiftLog {

    @TableId(type = IdType.UUID)
    private String zglId;
    private String zglBatchId;
    private String zglGiftId;
    private String zglUserId;
    private Integer zglNum;
    private Integer zglCore;
    private Date createTime;
    private Date updateTime;
}
