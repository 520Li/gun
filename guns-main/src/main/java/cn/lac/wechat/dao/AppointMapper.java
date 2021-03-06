package cn.lac.wechat.dao;

import cn.lac.wechat.domain.Appoint;
import cn.lac.wechat.vo.QueryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * ClassName: AppointMapper <br/>
 *
 * @author lac
 * @version 1.0
 * @date 2020/1/4 0004 - 9:50
 */
public interface AppointMapper extends BaseMapper<Appoint> {
    List<Appoint> findAppointByUser(String userId);

    List<Appoint> selectByVo(QueryVo vo);

    int countByVo(QueryVo vo);

    Appoint findById(String appointId);
}
