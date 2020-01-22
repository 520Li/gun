package cn.lac.wechat.utils;

import cn.lac.wechat.domain.Article;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.util.UUID;

public class PdfUtil {

    public static void pdf2Html(String pdfurl, String arId) {
        StringBuffer buffer = new StringBuffer();
        FileOutputStream fos;
        PDDocument document;
        File pdfFile;
        int size;
        BufferedImage image;
        FileOutputStream out;
        String randStr = arId;
        //PDF转换成HTML保存的文件夹
        String path = PdfUtil.class.getClassLoader().getResource("").toString().replace("file:/", "") + "/pages/pdf2html";
        String imagePath = PdfUtil.class.getClassLoader().getResource("").toString().replace("file:/", "") + "/static/images/pdf2html/" + arId;
        mkdirs(new File(path));
        mkdirs(new File(imagePath));
        mkdirs(new File(path + "/"));
        mkdirs(new File(imagePath + "/"));
        try {
            //遍历处理pdf附件
            buffer.append("<!doctype html>\r\n");
            buffer.append("<head>\r\n");
            buffer.append("<meta charset=\"UTF-8\">\r\n");
            buffer.append("</head>\r\n");
            buffer.append("<body style=\"background-color:gray;\">\r\n");
            buffer.append("<style>\r\n");
            buffer.append("img {background-color:#fff; text-align:center; width:100%; max-width:100%;margin-top:6px;}\r\n");
            buffer.append("</style>\r\n");
            document = new PDDocument();
            //pdf附件
            pdfFile = new File(pdfurl);
            document = PDDocument.load(pdfFile, (String) null);
            size = document.getNumberOfPages();
            Long start = System.currentTimeMillis(), end = null;
            System.out.println("===>pdf : " + pdfFile.getName() + " , size : " + size);
            PDFRenderer reader = new PDFRenderer(document);
            for (int i = 0; i < size; i++) {
                //image = newPDFRenderer(document).renderImageWithDPI(i,130,ImageType.RGB);
                image = reader.renderImage(i, 1.5f);
                //生成图片,保存位置
                out = new FileOutputStream(imagePath + "/" + "image" + "_" + i + ".jpg");
                ImageIO.write(image, "png", out); //使用png的清晰度
                //将图片路径追加到网页文件里
                buffer.append("<img src=\"/images/pdf2html/" + arId + "/" + "image" + "_" + i + ".jpg\"/>\r\n");
                image = null;
                out.flush();
                out.close();
            }
            reader = null;
            document.close();
            buffer.append("</body>\r\n");
            buffer.append("</html>");
            end = System.currentTimeMillis() - start;
            System.out.println("===> Reading pdf times: " + (end / 1000));
            start = end = null;
            //生成网页文件
            fos = new FileOutputStream(path + "/" + randStr + ".html");
            System.out.println(path + "/" + randStr + ".html");
            fos.write(buffer.toString().getBytes());
            fos.flush();
            fos.close();
            buffer.setLength(0);


        } catch (Exception e) {
            System.out.println("===>Reader parse pdf to jpg error : " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void mkdirs(File dir) {
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }


    /**
     * html 生成 pdf
     */
    public static void html2Pdf(Article article) {
        String arText = article.getArText();
        arText = arText.replaceAll("<br>", "").replaceAll("(<img((?!>).)*>)", "$1</img>");
        //pdf路径

        String arPath = article.getArPath();

        if (StringUtils.isBlank(arPath)) {
            arPath = "/images/pdf/" + UUID.randomUUID().toString().replace("-", "").toLowerCase() + ".pdf";
        }

        String filePath = PdfUtil.class.getClassLoader().getResource("").toString().replace("file:/", "") + "/static" + arPath;


        Document document = new Document();

        try {


            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }


            PdfWriter writer = PdfWriter.getInstance(document, new BufferedOutputStream(new FileOutputStream(file)));
            document.open();
            String content = "<html><body style=\"font-family: 宋体, SimHei;\">" + arText + "</body></html>";
            byte b[] = content.getBytes("utf-8");
            ByteArrayInputStream bais = new ByteArrayInputStream(b);


            XMLWorkerHelper.getInstance().parseXHtml(writer, document, bais, Charset.forName("UTF-8"));
            bais.close();


            article.setArPath(arPath);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (document.isOpen()) {
                document.close();
            }
        }
    }


    public static void main(String[] args) {

        String pdf = "D:\\123.pdf";

        //传入PDF地址
        //pdf2Html(pdf);
    }
}