package cn.lac.wechat.service;

import cn.lac.wechat.domain.*;
import cn.lac.wechat.vo.LayerVo;
import cn.lac.wechat.vo.QueryVo;
import cn.lac.wechat.wx.Result;

import java.util.List;

/**
 * ClassName: UserService <br/>
 *
 * @author lac
 * @version 1.0
 * @date 2020/1/3 0003 - 0:21
 */
public interface UserService {

    /**
     * 根据id查询用户
     *
     * @param id
     * @return
     */
    public User getUserById(String id);

    /**
     * 实名注册用户
     *
     * @param user
     */
    void insertUser(User user);

    /**
     * 校验手机号是否重复
     *
     * @param user
     * @return
     */
    boolean checkIphone(User user);

    /**
     * 保存新预约
     */
    void saveAppoint(Appoint appoint);

    /**
     * 查询预约
     */
    List<Appoint> findAppointByUser();

    /**
     * 审批新预约
     */
    void applyAppoint(Appoint appoint);

    /**
     * 查询活动
     */
    List<Event> getEventList(String query);

    /**
     * 查看活动详情
     */
    Event getEventDis(String eventId);

    /**
     * 报名活动
     */
    Result reportEvent(String eventId);

    /**
     * 用户事件举报
     */
    void appealByUser(Appeal appeal);

    /**
     * 根据用户查询事件举报列表
     */
    List<Appeal> getAppealByUser();

    /**
     * 申请/撤销 志愿者
     */
    Result packVo(String state);


    /**
     * 获取所有 预约
     */
    LayerVo findAppoint(QueryVo vo);

    /**
     * 查看详细
     */
    Appoint selectAppoint(String appointId);

    /**
     * 查询举报记录
     */
    List<AppealLog> getAppealDis(String appealId);

    Appeal findAppealById(String appealId);
}
