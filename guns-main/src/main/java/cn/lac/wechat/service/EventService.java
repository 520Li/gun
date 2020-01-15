package cn.lac.wechat.service;

import cn.lac.wechat.domain.Event;
import cn.lac.wechat.vo.LayerVo;
import cn.lac.wechat.vo.QueryVo;

public interface EventService {

    LayerVo findEvent(QueryVo vo);

    Event findEventById(String eventId);

    void insert(Event event);

    void edit(Event event);

    LayerVo findPersonByEvent(String eventId, QueryVo vo);
}
