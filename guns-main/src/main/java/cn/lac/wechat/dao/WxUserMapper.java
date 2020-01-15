package cn.lac.wechat.dao;

import cn.lac.wechat.domain.User;
import cn.lac.wechat.vo.QueryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ClassName: WxUserMapper <br/>
 *
 * @author lac
 * @version 1.0
 * @date 2020/1/3 0003 - 0:23
 */
public interface WxUserMapper extends BaseMapper<User> {
    List<User> selectByEvent(@Param("eventId") String eventId, @Param("vo") QueryVo vo);

    int countByEvent(@Param("eventId") String eventId, @Param("vo") QueryVo vo);
}
