package cn.lac.wechat.service.impl;

import cn.lac.wechat.dao.*;
import cn.lac.wechat.domain.*;
import cn.lac.wechat.service.UserService;
import cn.lac.wechat.vo.LayerVo;
import cn.lac.wechat.vo.QueryVo;
import cn.lac.wechat.wx.Result;
import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.auth.model.LoginUser;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * ClassName: UserServiceImpl <br/>
 *
 * @author lac
 * @version 1.0
 * @date 2020/1/3 0003 - 0:22
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    @Autowired
    private WxUserMapper wxUserMapper;
    @Autowired
    private HttpSession session;
    @Autowired
    private AppointMapper appointMapper;
    @Autowired
    private EventMapper eventMapper;
    @Autowired
    private EventUserMapper eventUserMapper;
    @Autowired
    private AppealMapper appealMapper;
    @Autowired
    private VolunteerUserMapper volunteerUserMapper;
    @Autowired
    private AppealLogMapper appealLogMapper;

    /**
     * 根据id查询用户
     */
    @Override
    public User getUserById(String id) {
        return wxUserMapper.selectById(id);
    }

    /**
     * 实名注册用户
     */
    @Override
    public void insertUser(User user) {
        wxUserMapper.insert(user);
    }

    /**
     * 校验手机号是否重复
     */
    @Override
    public boolean checkIphone(User user) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(User::getUserIphone, user.getUserIphone());
        List<User> list = wxUserMapper.selectList(wrapper);
        return list.size() == 0;
    }

    /**
     * 保存新预约
     */
    @Override
    public void saveAppoint(Appoint appoint) {
        User user = (User) session.getAttribute("login_user");
        appoint.setAppointUser(user.getUserId());
        appoint.setCreateTime(new Date());
        appoint.setApplyStatus("01");
        appointMapper.insert(appoint);
    }


    /**
     * 根据用户查询预约
     *
     * @return
     */
    @Override
    public List<Appoint> findAppointByUser() {
        User user = (User) session.getAttribute("login_user");
        /*QueryWrapper<Appoint> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Appoint::getAppointUser, user.getUserId());
        List<Appoint> appoints = appointMapper.selectList(wrapper);*/
        return appointMapper.findAppointByUser(user.getUserId());
    }


    /**
     * 审批预约
     *
     * @param appoint
     */
    @Override
    public void applyAppoint(Appoint appoint) {
        appoint.setUpdateTime(new Date());
        appoint.setApplyTime(new Date());
        appointMapper.updateById(appoint);
    }

    /**
     * 根据类别查询活动
     *
     * @param query
     * @return
     */
    @Override
    public List<Event> getEventList(String query) {
        User user = (User) session.getAttribute("login_user");
        return eventMapper.getEventByQuery(query, user.getUserId());
    }

    /**
     * 查看活动详情
     */
    @Override
    public Event getEventDis(String eventId) {
        /*
            线上报名   报名已截止  活动已结束   名额已满
        event_reend > now  线上报名
        event_reend > now  && event_limit = event_real 名额已满
        event_reend < now  报名已截止
        event_end < now  活动已结束
        */
       /* Event event = eventMapper.selectById(eventId);
        Date now = new Date();
        if (compareDate(now, event.getEventReend()) && event.getEventLimit() == event.getEventReal()) {
            event.setEventState("名额已满");
        } else if (compareDate(now, event.getEventReend())) {
            event.setEventState("线上报名");
        } else if (!compareDate(now, event.getEventReend())) {
            event.setEventState("报名已截止");
        } else if (!compareDate(now, event.getEventEnd())) {
            event.setEventState("活动已结束");
        }*/
        User user = (User) session.getAttribute("login_user");

        return eventMapper.selectByEventId(eventId, null == user ? "" : user.getUserId());
    }


    /**
     * 用户报名活动
     *
     * @return
     */
    @Override
    public Result reportEvent(String eventId) {
        if (StringUtils.isBlank(eventId)) {
            log.error("{}", "活动id为空！报名活动失败！");
            return new Result(false, "报名失败！");
        }


        User user = (User) session.getAttribute("login_user");
        QueryWrapper<EventUser> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(EventUser::getEventId, eventId).eq(EventUser::getUserId, user.getUserId());
        List<EventUser> users = eventUserMapper.selectList(wrapper);
        if (users.size() > 0) {
            return new Result(false, "您已经报名！");
        }


        Event event = eventMapper.selectById(eventId);
        if (compareDate(event.getEventReend(), new Date()) || compareDate(event.getEventEnd(), new Date())) {
            return new Result(false, "活动已结束！");
        }

        EventUser eventUser = EventUser.builder().eventId(eventId).userId(user.getUserId()).sign("01").build();
        eventUserMapper.insert(eventUser);
        return new Result(true, "报名成功！");
    }

    /**
     * 新增事件举报
     */
    @Override
    public void appealByUser(Appeal appeal) {
        User user = (User) session.getAttribute("login_user");
        appeal.setCreateTime(new Date());
        appeal.setAppealUser(user.getUserId());
        appeal.setAppealTel(user.getUserIphone());
        appealMapper.insert(appeal);
    }

    /**
     * 根据用户查询举报列表
     *
     * @return
     */
    @Override
    public List<Appeal> getAppealByUser() {
        User user = (User) session.getAttribute("login_user");
       /* QueryWrapper<Appeal> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Appeal::getAppealUser, user.getUserId()).orderByDesc(Appeal::getCreateTime);
        List<Appeal> appeals = appealMapper.selectList(wrapper);*/
        List<Appeal> appeals = appealMapper.getAppealByUser(user.getUserId());
       //List<Appeal> appeals = appealMapper.getAppealByUser("omRjy1OC0-3_iFigWCNsKwsxAcAg");

        return appeals;
    }

    /**
     * 申请/撤销 志愿者
     *
     * @param state
     */
    @Override
    public Result packVo(String state) {
        String detail = "";
        User user = (User) session.getAttribute("login_user");
        if (state.equals("02")) {
            detail = "撤销成功！";
            QueryWrapper<VolunteerUser> wrapper = new QueryWrapper<>();
            wrapper.lambda().eq(VolunteerUser::getUserId, user.getUserId());
            List<VolunteerUser> volunteerUsers = volunteerUserMapper.selectList(wrapper);
            if (volunteerUsers.size() > 0) {
                return new Result(false, "你有参与的志愿者活动，无法撤销！");
            }
        } else {
            detail = "申请成功！";
        }
        User pojo = wxUserMapper.selectById(user.getUserId());
        pojo.setUserVolunteer(state);
        wxUserMapper.updateById(pojo);
        return new Result(true, detail);

    }


    /**
     * 查询所有预约（后台）
     *
     * @param vo
     * @return
     */
    @Override
    public LayerVo findAppoint(QueryVo vo) {
        if (!ToolUtil.isAllEmpty(vo.getPage(), vo.getLimit())) {
            vo.setPage((vo.getPage() - 1) * vo.getLimit());
        }
        LoginUser shiroUser = LoginContextHolder.getContext().getUser();
        if (!shiroUser.getRoleList().contains(1L)) {
            vo.setDeptId(shiroUser.getDeptId());
        }
        List<Appoint> list = appointMapper.selectByVo(vo);
        int count = appointMapper.countByVo(vo);
        return new LayerVo(count, list);
    }

    /**
     * 查看预约详细
     *
     * @param appointId
     * @return
     */
    @Override
    public Appoint selectAppoint(String appointId) {
        return appointMapper.findById(appointId);
    }


    /**
     * 查看事件举报记录
     */
    @Override
    public List<AppealLog> getAppealDis(String appealId) {
        List<AppealLog> appealLogs = appealLogMapper.selectLog(appealId);
        return appealLogs;
    }

    /**
     * 查询事件状态（appealStatus不转中文）
     * @param appealId
     * @return
     */
    @Override
    public Appeal findAppealById(String appealId) {
        return appealMapper.selectById(appealId);
    }


    /**
     * 比较日期
     */
    private boolean compareDate(Date less, Date more) {
        long le = less.getTime();
        long mo = more.getTime();
        return le <= mo;
    }
}
