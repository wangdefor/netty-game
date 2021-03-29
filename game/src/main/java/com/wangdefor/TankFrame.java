package com.wangdefor;

import cn.hutool.core.io.resource.ClassPathResource;
import com.sun.org.apache.bcel.internal.util.ClassPath;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author wangyong
 * @Classname TankFrame
 * @Description JFrame
 * @Date 2021/3/27 17:11
 */
public class TankFrame extends Frame {


    static final int GAME_WIDTH = 960, GAME_HEIGHT = 540;

    public TankFrame(){
        setSize(GAME_WIDTH, GAME_HEIGHT);
        setResizable(false);
        setTitle("tank war");
        //setVisible(true);

        this.addKeyListener(new MyKeyListener());

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) { // bjmashibing/tank
                System.exit(0);
            }

        });
    }

    @Override
    public void paint(Graphics g) {
//        Color c = g.getColor();
//        g.setColor(Color.WHITE);
//        g.drawString("bullets:" + 2, 10, 60);
//        g.drawString("tanks:" + 2, 10, 80);
//        g.drawString("explodes" + 2, 10, 100);
//        g.setColor(c);
        //开始画一个坦克
        try {
            this.paintTank(g);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void paintTank(Graphics g) throws IOException {
        //读取图片
        ClassPathResource resource = new ClassPathResource("image/zj_001.jpg") ;
        BufferedImage read = ImageIO.read(resource.getStream());
        g.drawImage(modifyImageScale(read,0.05),20,30,null);
    }

    Image offScreenImage = null;

    public static BufferedImage rotateImage(final BufferedImage bufferedimage,
                                            final int degree) {
        int w = bufferedimage.getWidth();
        int h = bufferedimage.getHeight();
        int type = bufferedimage.getColorModel().getTransparency();
        BufferedImage img;
        Graphics2D graphics2d;
        (graphics2d = (img = new BufferedImage(w, h, type))
                .createGraphics()).setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2d.rotate(Math.toRadians(degree), w / 2, h / 2);
        graphics2d.drawImage(bufferedimage, 0, 0, null);
        graphics2d.dispose();
        return img;
    }

    public BufferedImage modifyImageScale(BufferedImage mini,double Scale) {
        int w = (int)Math.round(mini.getWidth()*Scale);
        int h = (int)Math.round(mini.getHeight()*Scale);
        //设置生成图片宽*高，色彩
        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        //创建画布
        Graphics2D g2 = bi.createGraphics();
        //设置图片透明  注********：只有png格式的图片才能设置背景透明，jpg设置图片颜色变的乱七八糟
        bi = g2.getDeviceConfiguration().createCompatibleImage(w, h, Transparency.TRANSLUCENT);
        //重新创建画布
        g2 = bi.createGraphics();
        //画图
        g2.drawImage(mini, 0,0,w,h, null);
        //关闭资源
        g2.dispose();
        return bi;
    }

    @Override
    public void update(Graphics g) {
        if (offScreenImage == null) {
            //读取图片
            ClassPathResource resource = new ClassPathResource("image/test.jpeg") ;
            BufferedImage read = null;
            try {
                read = ImageIO.read(resource.getStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            g.drawImage(modifyImageScale(read,1),0,0,null);
            offScreenImage = read;
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        //gOffScreen.setColor(Color.BLACK);
       // gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
//        gOffScreen.setColor(c);
        paint(gOffScreen);

        g.drawImage(offScreenImage, 0, 0, null);

        g.setColor(Color.BLUE);
        Font font = new Font("宋体",Font.BOLD,40);
        g.setFont(font);
        g.drawString("欢迎来到我的世界",GAME_WIDTH / 3 ,GAME_HEIGHT / 2);
    }

    /**
     * 键盘事件监听器
     */
    private class MyKeyListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }
}
