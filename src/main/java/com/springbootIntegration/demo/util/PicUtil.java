package com.springbootIntegration.demo.util;

import com.springbootIntegration.demo.support.exception.BaseException;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

/**
 * @author liukun
 * @description 对图片操作的工具类
 * @date 2020/5/26
 */
@Slf4j
public class PicUtil {

    /**
     * 图片格式之间相互转化
     * 目前仅测试过jpg,jpeg,gif,png,bmp之间的转换,其余格式需要先测试再使用
     *
     * @param picSrc       原图片文件绝对路径
     * @param outputFormat 要转换的格式,
     * @return 转换后的图片绝对路径
     * 存在bug，在win上可以运行，在linux非桌面端没测试过，在28所麒麟操作系统测试可以
     */
    public static File conversion(String picSrc, String outputFormat) {
        // 图片http://192.168.3.92:10000/CAMP/Img/capture/b61483e602724a4f96a2adfffc98d055.png不存在
        File file = new File(picSrc);
        if (!file.exists()) {
            log.error("图片" + picSrc + "不存在");
            return null;
        }

        BufferedImage input = null;
        // input = ImageIO.read(file); 直接使用java自带的方法读取是有bug的，后期出来的图片会失真
        Image image = Toolkit.getDefaultToolkit().getImage(file.getPath());
        input = toBufferedImage(image);

        String targetFile = picSrc.substring(0, picSrc.lastIndexOf(".") + 1) + outputFormat;
        // 要输出的文件
        File output = new File(targetFile);
        // 这么写是为了防止使用ImageIO.write后失真
        Iterator<ImageWriter> iterator = ImageIO.getImageWritersByFormatName(outputFormat);
        if (iterator.hasNext()) {
            ImageWriter writer = iterator.next();
            ImageWriteParam param = writer.getDefaultWriteParam();

            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(0.92f);
            try {
                FileImageOutputStream out = new FileImageOutputStream(output);
                writer.setOutput(out);
                writer.write(null, new IIOImage(input, null, null), param);
                out.close();
                writer.dispose();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return output;
    }

    private static BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }

        // 下面代码确保所有像素被加载
        image = new ImageIcon(image).getImage();
        BufferedImage bimage = null;
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

        try {
            int transparency = Transparency.OPAQUE;
            GraphicsDevice gs = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gs.getDefaultConfiguration();
            bimage = gc.createCompatibleImage(image.getWidth(null), image.getHeight(null), transparency);
        }
        catch (HeadlessException e) {
            log.error("系统没有屏幕");
        }
        if (bimage == null) {
            int type = BufferedImage.TYPE_INT_RGB;
            bimage = new BufferedImage(image.getWidth(null),image.getHeight(null) ,type );
        }
        Graphics graphics = bimage.createGraphics();
        graphics.drawImage(image,0 ,0 ,null );
        graphics.dispose();

        return bimage;
    }

    /**
     * 不按比例缩放,注意会失真
     *
     * @param file   图片文件
     * @param width  宽
     * @param height 高
     * @return 缩放后
     */
    private static File resize(File file, int width, int height) throws IOException {
        if (!file.exists()) {
            System.out.println("图片不存在");
            return null;
        }

        BufferedImage image = ImageIO.read(file);
        Image tmp = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        String parentPath = file.getParent();
        String name = file.getName();
        name = name.substring(0, name.lastIndexOf("."))
                + "_" + width + "x" + height + name.substring(name.lastIndexOf("."));
        File output = new File(parentPath + File.separator + name);
        ImageIO.write(resized, name.substring(name.lastIndexOf(".")+1), output);
        return output;
    }

    /**
     * 按比例缩放，注意也会失真
     * @param file
     * @param width
     * @param height
     * @return
     */
    private static File resizebyaspect(File file, int width, int height) throws IOException {
        if (!file.exists()) {
            System.out.println("图片不存在");
            return null;
        }

        BufferedImage image = ImageIO.read(file);
        int ori_width = image.getWidth();
        int ori_height = image.getHeight();
        float ratio_w = (float) width / ori_width;
        float ratio_h = (float) height / ori_height;
        int new_width = (ratio_w < ratio_h) ? width : (int) (ratio_h * ori_width);
        int new_height = (ratio_h < ratio_w) ? height : (int) (ratio_w * ori_height);
        System.out.println(new_height);
        System.out.println(new_width);
        Image tmp = image.getScaledInstance(new_width, new_height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(new_width, new_height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        String parentPath = file.getParent();
        String name = file.getName();
        name = name.substring(0, name.lastIndexOf("."))
                + "_" + new_width + "x" + new_height + name.substring(name.lastIndexOf("."));
        File output = new File(parentPath + File.separator + name);
        ImageIO.write(resized, name.substring(name.lastIndexOf(".")+1), output);
        return output;
    }

    public static void main(String[] args) throws IOException {
//        PicUtil.conversion("C:/Users/27567/Desktop/123.png", "bmp");
        File resizebyaspect = PicUtil.resizebyaspect(new File("C:\\Users\\27567\\Desktop\\123.jpg"), 256, 128);
        System.out.println(resizebyaspect.getName());
    }
}
