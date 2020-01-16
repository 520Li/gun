package cn.lac.wechat.service;

import cn.lac.wechat.domain.Home;
import cn.lac.wechat.vo.LayerVo;
import cn.lac.wechat.vo.QueryVo;

import java.util.List;

/**
 * 服务商service
 */

public interface HomeService {

    List<Home> getHomeList();

    LayerVo findList(QueryVo vo);

    Home findById(String homeId);

    void insert(Home home);

    void edit(Home home);

    void deleteById(String homeId);

}
