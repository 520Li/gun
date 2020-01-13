package cn.lac.wechat.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 文章类 <br/>
 *
 * @author lac
 * @version 1.0
 * @date 2020/1/4 0004 - 16:57
 */
@TableName("zly_article")
@Data
public class Article {

    @TableId(type = IdType.UUID)
    private String arId;
    private String arTitle;
    private String arText;
    private String arType;
    private String arPath;
    private String arIspath;
    private String arFirstpath;
    private String arUser;
    private String arOrg;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy年MM月dd日", timezone = "GMT+8")
    private Date createTime;
    private Date updateTime;

    private String arMajorType;
    private String arLowType;

    private String arTextJson;

}
