package cn.lac.wechat.controller;

import cn.lac.wechat.vo.Result;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

/**
 * AbstractErrorController 提供了多个方法可以从request里中获取错误信息，包含如下信息
 * timestamp 错误发生的时间
 * status:对应于HTTP Status，如404
 * error: 错误消息，如Bad Request,Not Found
 * message:详细错误信息
 * exception:如果应用抛出有异常，exception是字符串，代表异常的类名,如org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
 * path:请求的uri
 * errors:@Validated校验错误的时候，校验结果信息放到这里
 *
 * @author lac
 * @version 1.0
 * @date 2019/8/25 0025 - 20:28
 */
@Controller
@Slf4j
public class ErrorController extends AbstractErrorController {


    /*@Autowired
    private ObjectMapper objectMapper;*/

    public ErrorController() {
        super(new DefaultErrorAttributes());
    }


    /**
     * get请求报错处理
     *
     * @param request
     * @param response
     * @param map
     * @return
     */
    @GetMapping("/error")
    public String getErrorPath2(HttpServletRequest request, HttpServletResponse response, ModelMap map) {
        //getErrorAttributes 方法是AbstractErrorController 提供的用于获取错误信息的方法，返回一个Map，包含的数据如掐面所述。
        System.out.println(request.getRequestURL());
        Map<String, Object> model = Collections.unmodifiableMap(getErrorAttributes(request, false));
        //获取异常，有可能为空
        Throwable cause = getCause(request);
        int status = (Integer) model.get("status");
        //错误信息
        String message = (String) model.get("message");
        //友好提示
        String errorMessage = getErrorMessage(cause);
        //后台打印日志信息方方便查错
        log.info(status + "," + message, cause);
        response.setStatus(status);
        // error 模板显示错误详细信息
        map.put("errorMessage", errorMessage);
        map.put("status", status);
        map.put("cause", cause);
        return "/common/error.html";
    }


    /**
     * post请求报错处理
     *
     * @param request
     * @param response
     */
    @PostMapping("/error")
    public void getErrorPath(HttpServletRequest request, HttpServletResponse response) {
        //getErrorAttributes 方法是AbstractErrorController 提供的用于获取错误信息的方法，返回一个Map，包含的数据如掐面所述。
        Map<String, Object> model = Collections.unmodifiableMap(getErrorAttributes(request, false));
        //获取异常，有可能为空
        Throwable cause = getCause(request);
        int status = (Integer) model.get("status");
        //错误信息
        String message = (String) model.get("message");
        //友好提示
        String errorMessage = getErrorMessage(cause);
        //后台打印日志信息方方便查错
        log.info(status + "," + message, cause);
        Result result = new Result(false, errorMessage);
        response.setStatus(200);
        writeJson(response, result);
    }


    //getCause 方法用于获取应用系统的异常
    protected Throwable getCause(HttpServletRequest request) {
        Throwable error = (Throwable) request.getAttribute("javax.servlet.error.exception");
        if (error != null) {
            //MVC 有可能会封装异常成ServletException，需要调用getCause获取真正的异常
            while (error instanceof ServletException && error.getCause() != null) {
                error = ((ServletException) error).getCause();
            }
        }
        return error;
    }


    //getErrorMessage方法返回一个友好的异常信息，而不是SpringBoot提供的message包含的信息
    protected String getErrorMessage(Throwable ex) {
        return "服务器错误,请联系管理员";
    }

    /*protected String getErrorMessage(Throwable ex) {
        if (ex instanceof YourApplicationException) {
            // 如果 YourApplicationException的信息是可以显示给用户的
            return ((YourApplicationException) ex).getMessage();
        }
        return "服务器错误,请联系管理员";
    }*/

    //isJsonRequest 方法用来区分客户端发起的是页面渲染请求还是JSON请求
    protected boolean isJsonRequest(HttpServletRequest request) {
        String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
        if (requestUri != null && requestUri.endsWith(".do")) {
            return true;
        } else {
            //也可以通过获取HTTP头，根据Accept字段是否包含JSON来进一步判断，比如
            //request.getHeader("Accept").contains("application/json")
            return false;
        }
    }

    //如果是json请求，则调用writeJson方法，像客户端输出一个封装的Error信息
    protected void writeJson(HttpServletResponse response, Object error) {
        response.setContentType("application/json;charset=utf-8");
        try {
            response.getWriter().write(JSONObject.toJSONString(error));
        } catch (IOException e) {
            // ignore
        }
    }


    @Override
    public String getErrorPath() {
        return null;
    }
}
