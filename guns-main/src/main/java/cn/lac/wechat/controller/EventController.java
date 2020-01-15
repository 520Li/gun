package cn.lac.wechat.controller;

import cn.lac.wechat.domain.Appoint;
import cn.lac.wechat.domain.Event;
import cn.lac.wechat.service.EventService;
import cn.lac.wechat.service.UserService;
import cn.lac.wechat.vo.LayerVo;
import cn.lac.wechat.vo.QueryVo;
import cn.lac.wechat.wx.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
@RequestMapping("/admin/event")
public class EventController {

    @Autowired
    private EventService eventService;


    /**
     * 跳转活动管理页面
     */
    @GetMapping("")
    public String admin() {
        return "/admin/event/event.html";
    }

    /**
     * 获取活动列表
     */
    @GetMapping("/list")
    @ResponseBody
    public LayerVo list(QueryVo vo) {
        return eventService.findEvent(vo);
    }

    /**
     * 跳转发布活动页面
     */
    @GetMapping("/event_add")
    public String event_add() {
        return "/admin/event/event_add.html";
    }

    /**
     * 跳转发布编辑页面
     */
    @GetMapping("/event_edit")
    public String event_edit(String eventId, ModelMap map) {
        map.put("event", eventService.findEventById(eventId));
        return "/admin/event/event_edit.html";
    }

    /**
     * 跳转人员名单页面
     */
    @GetMapping("/event_join")
    public String event_join(String eventId, ModelMap map) {
        map.put("eventId", eventId);
        return "/admin/event/event_join.html";
    }

    /**
     * 发布活动
     */
    @PostMapping("/add")
    @ResponseBody
    public Result add(Event event) {
        eventService.insert(event);
        return new Result(true, "发布成功！");
    }

    /**
     * 编辑活动
     */
    @PostMapping("/edit")
    @ResponseBody
    public Result edit(Event event) {
        eventService.edit(event);
        return new Result(true, "编辑成功！");
    }

    /**
     * 人员名单页面
     */
    @GetMapping("/person_list")
    @ResponseBody
    public LayerVo personList(String eventId, QueryVo vo) {
        return eventService.findPersonByEvent(eventId, vo);
    }


}
