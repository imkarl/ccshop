package cn.jeesoft.core.utils;

import javax.imageio.ImageIO;

import cn.jeesoft.core.exception.FileIOException;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * JDiy图片操作工具类.
 * 此类封装简化了图片大小获取/缩放调整，水印等常用功能.
 * @author 子秋(ziquee)  http://www.jdiy.org
 */
@SuppressWarnings({"unused"})
public final class Pic{
    private int width = 0;
    private int height = 0;
    private File file;
    private BufferedImage image;
    private int opacityType =1;

    private Pic() {
    }

    /**
     * 以给定的文件物理路径构造图片处理对象.
     * @param file 要处理的图片文件.
     */
    public Pic(File file){
        this.file = file;
        if (!this.file.exists()) throw new FileIOException("要操作的文件不存在或不是一个文件."+file.getPath());

        if (this.file.isDirectory()) throw new FileIOException("无法创建实例化,构造方法传入的参数需要是一个文件而非文件夹。");

        String fn = file.getName().toLowerCase();
        opacityType = fn.endsWith(".png") || fn.endsWith("gif") ?
                BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB;
        Image src;
        try {
            src = ImageIO.read(this.file);
        } catch (IllegalArgumentException e) {
            throw new FileIOException("无法使用嵌入的ICC配置文件,因为ICC文件头己损坏。这是由于您指定的图片文件格式不正确而造成的错误。");
        } catch (IOException e) {
            throw new FileIOException(e.fillInStackTrace());
        }
        width = src.getWidth(null);
        height = src.getHeight(null);
        image = new BufferedImage(width, height, opacityType);
        Graphics g = image.createGraphics();
        g.drawImage(src, 0, 0, width, height, null);
        g.dispose();
    }

    /**
     * 将指定的图片加入到当前图片中的指定位置. 即向图片中打水印.
     * @param file 要添加的图片文件。
     * @param position 要添加的图片在当前图片中的位置. <br />
     * 其取值范围为1-9之间的整数(默认为9)。分别代表：左上、上中、右上、左中、正中、右中、左下、中下、右下
     * @return Pic 当前的Pic对象本身.
     */
    public Pic add(File file, int position) {
        Image src;
        try {
            src = ImageIO.read(file);
        } catch (IllegalArgumentException e) {
            throw new FileIOException("无法使用嵌入的ICC配置文件,因为ICC文件头己损坏。这是由于您指定的图片文件格式不正确而造成的错误。");
        } catch (IOException e) {
            throw new FileIOException(e.fillInStackTrace());
        }
        int ww = src.getWidth(null);
        int hh = src.getHeight(null);
        int WW, HH;
        switch (position) {
            case 1:
                WW = 0;
                HH = 0;
                break;
            case 2:
                WW = (width - ww) / 2;
                HH = 0;
                break;
            case 3:
                WW = width - ww;
                HH = 0;
                break;
            case 4:
                WW = 0;
                HH = (height - hh) / 2;
                break;
            case 5:
                WW = (width - ww) / 2;
                HH = (height - hh) / 2;
                break;
            case 6:
                WW = width - ww;
                HH = (height - hh) / 2;
                break;
            case 7:
                WW = 0;
                HH = height - hh;
                break;
            case 8:
                WW = (width - ww) / 2;
                HH = height - hh;
                break;
            default:
                WW = width - ww;
                HH = height - hh;
        }
        Graphics g = image.createGraphics();
        g.drawImage(src, WW, HH, ww, hh, null);
        g.dispose();
        return this;
    }

    /**
     * 返回当前图片的高度.
     * @return int 当前图片的高度值
     */
    public int getHeight() {
        return height;
    }

    /**
     * 返回当前图片的宽度.
     * @return int 当前图片的宽度值
     */
    public int getWidth() {
        return width;
    }

    /**
     * 将图片等比缩放. (缩放后的图片与原图成等比.)    <br />
     * <strong>说明：</strong>
     * <ol><li>如果参数w和h的值都不为0, 程序会将图片等比缩放，使图片处于最大宽为w,或者最大高为h的限定范围之内.
     * 也就是说，由于图片自身的长宽比，和w/h参数的比例可能并不一致，缩放后的图片的宽高并不一定都与w/h参数值相等.</li>
     * <li>如果参数w和h中有且只有一个值为0,程序将只以非0的那个边(宽或高)作为参照，将图片等比缩放至指定大小.</li>
     * <li>如果两个参数都为0,或者w/h参数与当前图片的尺寸相等，程序将不作任何处理.</li></ol>
     * @param w 缩放后的图片宽度(或最大宽度).
     * @param h 缩放后的图片高度(或最大高度).
     * @return Pic 当前的Pic对象本身.
     * @see #resizeBy(int, int, java.awt.Color)
     * @see #resizeTo(int, int)
     */

    public Pic resizeBy(int w, int h){
        if (w == 0 && h == 0 || w==width && h==height) {
            return this;
        }
        int w1 = w, h1 = h;
        if (w1 > h1) {
            h1 = ((height * w1) / width);
        } else {
            w1 =  ((width * h1) / height);
        }
        if (w != 0 && h != 0) {
            if (w1 > w) {
                h1 = (w * h1 / w1);
                w1 = w;
            } else if (h1 > h) {
                w1 = (h * w1 / h1);
                h1 = h;
            }
        }
        return resizeTo(w1, h1);
    }

    /**
     * 将图片等比缩放至w,h限定的范围之内,此范围之内空的区域以指定的颜色进行填充.
     * 与{@link #resizeBy(int, int)}不同的是，该方法缩小后的图片(为了自尺寸与原图等比)长宽并不一定会等于w,h的值,
     * 而此方法缩放后的图片，其长度一定等于w, 宽度一定等于h,但是图片的显示画面仍然与原图保持等比，中间空出来的地方，将会用指定的颜色填充.
     * @param w 缩小后的图片宽度
     * @param h 缩小后的图片高度
     * @param c 空的区域所要填充的颜色.
     * @return Pic 当前的Pic对象本身.
     * @see #resizeBy(int, int)
     * @see #resizeTo(int, int)
     */
    public Pic resizeBy(int w, int h, Color c) {
        if(w==width && h==height)return this;
        resizeBy(w, h);
        int L = ((w - width) / 2);
        int T = ((h - height) / 2);
        BufferedImage image1 = new BufferedImage(w, h, opacityType);
        Graphics g = image1.createGraphics();
        g.setColor(c);
        g.fillRect(0, 0, w, h);
        try {
            g.drawImage(image, L, T, width, height, null);
            image=image1;
            width=w;
            height=h;
        } catch (IllegalArgumentException e) {
            throw new FileIOException("无法使用嵌入的ICC配置文件,因为ICC文件头己损坏。这是由于您指定的图片文件格式不正确而造成的错误。");
        }
        return this;
    }

    /**
     * 将图片收缩或放大至指定尺寸.
     * 此方法不保证等比，如果图片原尺寸的比例和传入的参数w/h不成比例，图片将被拉伸变形.
     * @param w 缩放后的图片宽度
     * @param h 缩放后的图片高度
     * @return Pic 当前的Pic对象本身.
     * @see #resizeBy(int, int)
     * @see #resizeBy(int, int, java.awt.Color)
     */
    public Pic resizeTo(int w, int h) {
        if(w==width && h==height)return this;
        if (w == 0 || h == 0) return resizeBy(w, h);
        BufferedImage image1 = new BufferedImage(w, h, opacityType);
        Graphics g = image1.createGraphics();
        try {
            g.drawImage(image, 0, 0, w, h, null);
            image=image1;
        } catch (IllegalArgumentException e) {
            throw new FileIOException("无法使用嵌入的ICC配置文件,因为ICC文件头己损坏。这是由于您指定的图片文件格式不正确而造成的错误。");
        }
        width = w;
        height = h;
        return this;
    }

    /**
     * 将处理过的图片另存为文件.
     * @param file 要另存成的文件.
     */
    public void saveAs(File file){
        String path = file.getAbsolutePath();
        String ext = path.substring(path.lastIndexOf(".") + 1).toUpperCase();
        if (!ext.equals("PNG") && !ext.equals("GIF")) {
            ext = "JPEG";
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            ImageIO.write(image, ext, out);
            out.close();
        } catch (IOException e) {
            throw new FileIOException(e.fillInStackTrace());
        }
    }

    /**
     * 将处理过的图片保存.
     */
    public void save(){
        saveAs(file);
    }

}
