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
    private long page = 1;
    private long limit = 10;
    private String clazz;
    private Map<String, Object> map = new HashMap<>();


    /**
     * wechat
     */
    private String arType; //文章板块分类
    private String arTitle;//文章标题


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

    public long getPage() {
        return page;
    }

    public void setPage(long page) {
        this.page = page;
    }

    public long getLimit() {
        return limit;
    }

    public void setLimit(long limit) {
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
