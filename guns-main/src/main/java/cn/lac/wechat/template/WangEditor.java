package cn.lac.wechat.template;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class WangEditor {


    private String tag;
    private List<Map<String, String>> attrs;
    private List<WangEditor> children;

}
