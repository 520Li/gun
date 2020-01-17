package cn.lac.wechat.service;

import cn.lac.wechat.domain.Article;
import cn.lac.wechat.domain.Volunteer;
import cn.lac.wechat.vo.LayerVo;
import cn.lac.wechat.vo.QueryVo;
import cn.lac.wechat.wx.Result;

import java.util.List;

public interface VolunteerService {

    /**
     * 活动列表
     *
     * @return
     */
    List<Volunteer> getVoList();

    /**
     * 活动详情
     *
     * @param voId
     * @return
     */
    Volunteer details(String voId);

    /**
     * 参加活动报名
     *
     * @param voId
     */
    Result joinVo(String voId);


    LayerVo getList(QueryVo vo);

    Volunteer getById(String voId);

    void insert(Volunteer volunteer);

    void edit(Volunteer volunteer);

    void deleteById(String voId);

    LayerVo userList(QueryVo vo, String voId);

}
