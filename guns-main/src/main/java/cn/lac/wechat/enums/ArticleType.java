package cn.lac.wechat.enums;

/**
 * 文章分类 <br/>
 *
 * @author lac
 * @version 1.0
 * @date 2020/1/4 0004 - 17:11
 */
public enum ArticleType {

    /**
     * 通知通告
     */
    TZTG {
        @Override
        public String toString() {
            return "TZTG";
        }
    },
    /**
     * 社区介绍
     */
    SQJS {
        @Override
        public String toString() {
            return "SQJS";
        }
    },
    /**
     * 社区风采
     */
    SQFC {
        @Override
        public String toString() {
            return "SQFC";
        }
    },
    /**
     * 一老一小
     */
    YLYX {
        @Override
        public String toString() {
            return "YLYX";
        }
    },
    /**
     * 老年人
     */
    LNR {
        @Override
        public String toString() {
            return "LNR";
        }
    },
    /**
     * 计生
     */
    JS {
        @Override
        public String toString() {
            return "JS";
        }
    },
    /**
     * 居住证
     */
    JZZ {
        @Override
        public String toString() {
            return "JZZ";
        }
    },
    /**
     * 残疾人
     */
    CJR {
        @Override
        public String toString() {
            return "CJR";
        }
    },
    /**
     * 电子阅览图书
     */
    DZYL {
        @Override
        public String toString() {
            return "DZYL";
        }
    }
}
