package com.springbootIntegration.demo.util;

import com.springbootIntegration.demo.support.exception.BaseException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author liukun
 * @description 对图片操作的工具类
 * @date 2020/5/26
 */
public class PicUtil {

    /**
     * 图片任意格式转换
     *
     * @param picSrc       原格式文件绝对路径
     * @param outputFormat 目标格式
     */
    public static void conversion(String picSrc, String outputFormat) {
        try {
            File input = new File(picSrc);
            if (!input.exists()) {
                System.out.println("图片不存在");
                return ;
            }
            BufferedImage bim = ImageIO.read(input);
            String targetFile = picSrc.substring(0, picSrc.lastIndexOf(".") + 1) + outputFormat;
            File output = new File(targetFile);
            ImageIO.write(bim, outputFormat, output);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
