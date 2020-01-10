package cn.lac.wechat.enums;

/**
 * 微信消息类型
 */
public enum MessageType {

    /**
     * 文本
     */
    TEXT {
        @Override
        public String toString() {
            return "text";
        }
    },
    /**
     * 图片
     */
    IMAGE {
        @Override
        public String toString() {
            return "image";
        }
    },
    /**
     * 语音
     */
    VOICE {
        @Override
        public String toString() {
            return "voice";
        }
    },
    /**
     * 视频
     */
    VIDEO {
        @Override
        public String toString() {
            return "video";
        }
    },
    /**
     * 短视频
     */
    SHORTVIDEO {
        @Override
        public String toString() {
            return "shortvideo";
        }
    },
    /**
     * 地址
     */
    LOCATION {
        @Override
        public String toString() {
            return "location";
        }
    },
    /**
     * 链接
     */
    LINK {
        @Override
        public String toString() {
            return "link";
        }
    },
    /**
     * 音乐
     */
    MUSIC {
        @Override
        public String toString() {
            return "music";
        }
    },
    /**
     * 图文信息
     */
    NEWS {
        @Override
        public String toString() {
            return "news";
        }
    },
    /**
     * 事件
     */
    EVENT {
        @Override
        public String toString() {
            return "event";
        }
    }

}
