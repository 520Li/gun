package cn.lac.wechat.controller;

import cn.lac.wechat.wx.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.UUID;

@Controller
@Slf4j
@RequestMapping("/admin/file")
public class FileController {


    /**
     * 图片上传
     *
     * @param request
     * @return
     */
    @PostMapping("/file.do")
    @ResponseBody
    public Result upload(String path, HttpSession session, HttpServletRequest request) {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("file");
        FileChannel outChannel = null;
        try {
            String oldName = file.getOriginalFilename();
            String suffix = oldName.substring(oldName.lastIndexOf("."));
            if (!suffix.equalsIgnoreCase(".pdf")) {
                return new Result(false, "只能上传 pdf 格式");
            }
            //后缀名
            String newName = UUID.randomUUID().toString().replace("-", "").toLowerCase() + suffix;
            outChannel = FileChannel.open(Paths.get(this.getClass().getClassLoader().getResource("").toString().replace("file:/", "") + "/static/images/" + path + "/" + newName),
                    StandardOpenOption.WRITE, StandardOpenOption.CREATE);
            ByteBuffer buf = ByteBuffer.allocate((int) file.getSize());
            buf.put(file.getBytes());
            buf.flip();
            outChannel.write(buf);
            buf.clear();

            // 数据库需要保存：相对路径
            String relativePath = "/images/" + path + "/" + newName;
            return new Result(true, "上传成功！", relativePath);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new Result(false, "上传图片失败");
        } finally {
            try {
                if (outChannel != null) {
                    outChannel.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
