/**
 * ====================================================================
 * <p>版　权: Travelsky 版权所有 (c) 2006</p>
 * <p>文　件: com.ttl.dit.common.ValidationImageUtil</p>
 * <p>所含类: ValidationImageUtil</p>
 *
 * <p>修改记录:</p>
 * 日期       作者                            内容
 * =====================================================================
 * 2011-4-28 zhdwang                        建立,完成基本内容．
 * =====================================================================
 */
package com.pss.common;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

public class ValidationImageUtil {
    /**
     * 获得一张图片
     * @param width  图片的宽度
     * @param height 图片的高度
     * @param result 将返回的结果写在result中
     */
	public static ImageResult createImage(int width,int height){
		//得到一张宽为width，长度为height的图片
		BufferedImage img = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
		Graphics graphics = img.getGraphics();
		//使用随机数生成数字和字母
		Random r = new Random();
        //图片颜色
		Color color = new Color(230, 200, 80);
        //设置颜色
		graphics.setColor(color);
		// 填充整个图片的颜色
		graphics.fillRect(0, 0, width, height);
		// 向图片中输出数字和字母
		StringBuffer sb = new StringBuffer();
		char[] ch = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
		int index, len = ch.length;
		for (int i = 0; i < 4; i++) {
			index = r.nextInt(len);
			graphics.setColor(new Color(r.nextInt(88), r.nextInt(188), r.nextInt(255)));					
			graphics.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 22));// 输出的字体和大小
			graphics.drawString("" + ch[index], (i * (width/5)) + width/20, height);// 写什么数字，在图片的什么位置画
			sb.append(ch[index]);
		}
		ImageResult imageResult = new ImageResult();
		imageResult.setImage(img);
		imageResult.setResult(sb.toString());
		return imageResult;
	}
	/**
	 * 定义返回结果
	 */
	public static class ImageResult{
		private BufferedImage image;
		private String result;
		public BufferedImage getImage() {
			return image;
		}
		public void setImage(BufferedImage image) {
			this.image = image;
		}
		public String getResult() {
			return result;
		}
		public void setResult(String result) {
			this.result = result;
		}
		
	}
}


