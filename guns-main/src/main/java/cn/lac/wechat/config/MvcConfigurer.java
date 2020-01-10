package cn.lac.wechat.config;

import cn.lac.wechat.domain.User;
import cn.lac.wechat.service.UserService;
import cn.lac.wechat.utils.HttpClientUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * <p>
 * 配置应用的MVC全局特性
 *
 * @author lac
 * @version 1.0
 * @date 2019/8/25 0025 - 19:40
 */
@Configuration
public class MvcConfigurer implements WebMvcConfigurer {

    @Autowired
    private UserService userService;
    @Value("${getCode}")
    private String getCodeUrl;
    @Value("${getOpenId}")
    private String getOpenIdUrl;



    //格式化
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new DateFormatter("yyyy-MM-dd HH:mm"));
        //registry.addFormatter(new DateFormatter("yyyy-MM-dd"));
    }

    //拦截器
    /*
    通过 addInterceptors方法可以设置多个拦截器，
    比如对特定的URI设定拦截器检查用户是否登录，
    打印处理用户请求耗费的时间等。
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SessionHandlerInterceptor())
                .addPathPatterns("/wechat/second/report.do")
                .addPathPatterns("/wechat/second/appointment.do")
                .addPathPatterns("/wechat/third/appeal.do")
                .addPathPatterns("/wechat/third/read.do")
                .addPathPatterns("/wechat/third/home.do")
                .addPathPatterns("/wechat/third/benefit.do")
                .addPathPatterns("/wechat/third/volunteer.do");

        registry.addInterceptor(new OpenIdInterceptor())
                .addPathPatterns("/wechat/login");
    }

    //配置静态资源映射
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
        //registry.addResourceHandler("/images/**").addResourceLocations("classpath:/static/images/");
    }

    //跨域访问配置
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }

    //uri到视图的映射
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //系统登录页面
        /* registry.addViewController("/login").setViewName("/login.html");*/
    }


    /**
     * session拦截器
     */
    class SessionHandlerInterceptor implements HandlerInterceptor {

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            String url = request.getRequestURL().toString();
            User user = (User) request.getSession().getAttribute("login_user");
            if (null == user) {
                //授权 获取code
                String getCodeUrlCopy = String.format(getCodeUrl, url);
                //getCodeUrl = String.format(getCodeUrl, /*URLEncoder.encode(url, "GBK")*/url);
                response.sendRedirect(getCodeUrlCopy);
                return false;
            } else {
                return true;
            }

        }

        @Override
        public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
            //Controller方法处理完毕后，调用此方法
        }

        @Override
        public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
            // 页面渲染完毕后调用此方法，通常用来清除某些资源使用，类似Java语法的finally 作用。
        }
    }


    /**
     * 用户openId拦截器
     */
    class OpenIdInterceptor implements HandlerInterceptor {

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            String code = request.getParameter("code");
            String url = request.getParameter("state");
            String getOpenIdUrlCopy = String.format(getOpenIdUrl, code);
            String result = HttpClientUtil.doGet(getOpenIdUrlCopy, null);
            /*
            {
              "access_token":"ACCESS_TOKEN",
              "expires_in":7200,
              "refresh_token":"REFRESH_TOKEN",
              "openid":"OPENID",
              "scope":"SCOPE"
             */
            Map map = JSONObject.parseObject(result, Map.class);
            String openid = (String) map.get("openid");
            User user = userService.getUserById(openid);
            if (null == user) {
                System.out.println("openid:" + openid);
                /*request.setAttribute("openid", openid);*/
                request.getSession().setAttribute("openid", openid);
                request.getSession().setAttribute("url", url);
                return true;
            } else {
                request.getSession().setAttribute("login_user", user);
                response.sendRedirect(url);
                return false;
            }

        }

        @Override
        public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
            //Controller方法处理完毕后，调用此方法
        }

        @Override
        public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
            // 页面渲染完毕后调用此方法，通常用来清除某些资源使用，类似Java语法的finally 作用。
        }
    }

}


