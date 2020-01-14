package cn.lac.wechat.service.impl;

import cn.lac.wechat.dao.EventMapper;
import cn.lac.wechat.domain.Event;
import cn.lac.wechat.service.EventService;
import cn.lac.wechat.vo.LayerVo;
import cn.lac.wechat.vo.QueryVo;
import cn.stylefeng.roses.core.util.ToolUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class EventServiceImpl implements EventService {

    @Autowired
    private EventMapper eventMapper;


    /**
     * 查询活动列表
     */
    @Override
    public LayerVo findEvent(QueryVo vo) {
        if (!ToolUtil.isAllEmpty(vo.getPage(), vo.getLimit())) {
            vo.setPage((vo.getPage() - 1) * vo.getLimit());
        }
        List<Event> list = eventMapper.selectByVo(vo);
        int count = eventMapper.countByVo(vo);
        return new LayerVo(count, list);
    }

    /**
     * 活动详细
     *
     * @param eventId
     * @return
     */
    @Override
    public Event findEventById(String eventId) {
        return eventMapper.selectById(eventId);
    }

    /**
     * 新增活动
     *
     * @param event
     */
    @Override
    public void insert(Event event) {
        event.setCreateTime(new Date());
        eventMapper.insert(event);
    }

    /**
     * 编辑活动
     *
     * @param event
     */
    @Override
    public void edit(Event event) {
        event.setUpdateTime(new Date());
        event.setEventText(event.getEventText().trim());
        eventMapper.updateById(event);
    }
}
