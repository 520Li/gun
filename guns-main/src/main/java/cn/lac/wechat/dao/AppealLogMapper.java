package cn.lac.wechat.dao;

import cn.lac.wechat.domain.AppealLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AppealLogMapper extends BaseMapper<AppealLog> {

    List<AppealLog> selectLog(@Param("appealId") String appealId);
}
