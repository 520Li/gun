package cn.lac.wechat.vo;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: QueryVo <br/>
 *
 * @author lac
 * @version 1.0
 * @date 2019/9/1 0001 - 19:16
 */
public class QueryVo {

    /**
     * @version 1.0
     */
    private Integer page = 1;
    private Integer limit = 10;
    private String clazz;
    private Map<String, Object> map = new HashMap<>();


    /**
     * index / office / read
     */
    private String type;//菜单分类
    private String arType; //文章板块分类
    private String arTitle;//文章标题

    /**
     * appoint
     */
    private String appointUser;//预约人
    private String serviceType;//预约业务

    /**
     * event
     */
    private String eventName; //活动名称


    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getAppointUser() {
        return appointUser;
    }

    public void setAppointUser(String appointUser) {
        this.appointUser = appointUser;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getArType() {
        return arType;
    }

    public void setArType(String arType) {
        this.arType = arType;
    }

    public String getArTitle() {
        return arTitle;
    }

    public void setArTitle(String arTitle) {
        this.arTitle = arTitle;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    @JsonAnyGetter
    public Map<String, Object> getMap() {
        return map;
    }

    @JsonAnySetter
    public void setMap(String key, Object value) {
        this.map.put(key, value);
    }
}
