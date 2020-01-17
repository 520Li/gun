package cn.lac.wechat.service.impl;

import cn.lac.wechat.dao.WxUserMapper;
import cn.lac.wechat.dao.VolunteerMapper;
import cn.lac.wechat.dao.VolunteerUserMapper;
import cn.lac.wechat.domain.Article;
import cn.lac.wechat.domain.User;
import cn.lac.wechat.domain.Volunteer;
import cn.lac.wechat.domain.VolunteerUser;
import cn.lac.wechat.service.VolunteerService;
import cn.lac.wechat.vo.LayerVo;
import cn.lac.wechat.vo.QueryVo;
import cn.lac.wechat.wx.Result;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class VolunteerServiceImpl implements VolunteerService {

    @Autowired
    private VolunteerMapper volunteerMapper;
    @Autowired
    private VolunteerUserMapper volunteerUserMapper;
    @Autowired
    private WxUserMapper wxUserMapper;
    @Autowired
    private HttpSession session;

    @Override
    public List<Volunteer> getVoList() {
        QueryWrapper<Volunteer> wrapper = new QueryWrapper<>();
        return volunteerMapper.selectList(wrapper);
    }

    @Override
    public Volunteer details(String voId) {
        User user = (User) session.getAttribute("login_user");
        return volunteerMapper.selectByVoId(voId, user.getUserId());
    }

    @Override
    public Result joinVo(String voId) {
        User user = (User) session.getAttribute("login_user");
        if (user.getUserVolunteer().equals("02")) {
            return new Result(false, "你还不是志愿者,请先申请成为志愿者！");
        }
        VolunteerUser entity = VolunteerUser.builder()
                .volunteerId(voId)
                .userId(user.getUserId())
                .createTime(new Date())
                .build();
        volunteerUserMapper.insert(entity);
        user.setUserVolunteer("01");
        session.setAttribute("login_user", user);
        return new Result(true, "报名成功！");
    }


    @Override
    public LayerVo getList(QueryVo vo) {
        if (!ToolUtil.isAllEmpty(vo.getPage(), vo.getLimit())) {
            vo.setPage((vo.getPage() - 1) * vo.getLimit());
        }
        List<Volunteer> list = volunteerMapper.selectByVo(vo);
        int count = volunteerMapper.countByVo(vo);
        return new LayerVo(count, list);
    }

    @Override
    public Volunteer getById(String voId) {

        return volunteerMapper.selectById(voId);
    }

    @Override
    public void insert(Volunteer volunteer) {
        volunteer.setCreateTime(new Date());
        volunteerMapper.insert(volunteer);
    }

    @Override
    public void edit(Volunteer volunteer) {
        volunteer.setUpdateTime(new Date());
        volunteerMapper.updateById(volunteer);
    }

    @Override
    public void deleteById(String voId) {
        volunteerMapper.deleteById(voId);
    }

    @Override
    public LayerVo userList(QueryVo vo, String voId) {
        if (!ToolUtil.isAllEmpty(vo.getPage(), vo.getLimit())) {
            vo.setPage((vo.getPage() - 1) * vo.getLimit());
        }
        List<User> list = wxUserMapper.selectByVolunteer(voId, vo);
        int count = wxUserMapper.countByVolunteer(voId, vo);

        return new LayerVo(count, list);
    }
}
