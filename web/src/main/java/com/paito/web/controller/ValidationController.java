package com.paito.web.controller;

import com.paito.biz.auction.dao.IAccountInfoDAO;
import com.paito.biz.auction.dao.IVerifyMessageDAO;
import com.paito.biz.auction.dto.AccountDO;
import com.paito.biz.auction.dto.VerifyMessage;
import com.paito.biz.frame.util.Constants;
import com.paito.web.common.AjaxResult;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Random;

@SuppressWarnings("serial")
@Controller
@RequestMapping("/validate")
public class ValidationController{
	
	private Log logger = LogFactory.getLog(ValidationController.class);
	
	private ByteArrayInputStream inputStream;

	@Resource
	private IAccountInfoDAO accountInfoDAO;

	@Resource
	private IVerifyMessageDAO verifyMessageDAO;

	@ResponseBody
	@RequestMapping(value = "/checkAccount.action", method = {RequestMethod.GET, RequestMethod.POST})
	public AjaxResult checkAccount(HttpServletRequest request){
		String nickName = request.getParameter("usAccount");
		if(!StringUtils.isEmpty(nickName)) {
			AccountDO accountDO = accountInfoDAO.getAccountByUserName(nickName);
			if(null == accountDO){
				return AjaxResult.succResult();
			}
		}
		return AjaxResult.errorResult();
	}

	@ResponseBody
	@RequestMapping(value = "/checkVerifyMsg.action", method = {RequestMethod.GET, RequestMethod.POST})
	public AjaxResult checkVerifyMsg(String mobileno, String verifyMsg){
		VerifyMessage verifyMessage = verifyMessageDAO.findTop1ByMobileno(mobileno);

		if(null != verifyMessage && verifyMessage.getUsedStatus().equals(0)){
			if(verifyMsg.equals(verifyMessage.getVerifycode())){
				return AjaxResult.succResult();
			}
		}
		return AjaxResult.errorResult();
	}



	@ResponseBody
	@RequestMapping(value = "/logincode.json", method = {RequestMethod.GET, RequestMethod.POST})
	public void login(HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.info("11111___________");
		//在内存中创建图象   
		int width=65, height=20;   
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		//获取图形上下文   
		Graphics g = image.getGraphics();   
		//生成随机类   
		Random random = new Random();   
		//设定背景色   
		g.setColor(getRandColor(200,250));   
		g.fillRect(0, 0, width, height);   
		//设定字体   
		g.setFont(new Font("Times New Roman",Font.PLAIN,18));   
		//随机产生155条干扰线，使图象中的认证码不易被其它程序探测到   
		g.setColor(getRandColor(160,200));   
		for (int i=0; i<155; i++){   
			int x = random.nextInt(width);   
		    int y = random.nextInt(height);   
		    int xl = random.nextInt(12);   
	        int yl = random.nextInt(12);   
		    g.drawLine(x,y,x+xl,y+yl);   
		}   
		//取随机产生的认证码(6位数字)   
		String sRand = "";   
		for (int i=0; i<4; i++){   
			String rand = String.valueOf(random.nextInt(10));   
			sRand += rand;   
			// 将认证码显示到图象中   
			g.setColor(new Color(20 + random.nextInt(110),20 + random.nextInt(110),20 + random.nextInt(110)));   
			//调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成   
			g.drawString(rand,13*i+6,16);   
		}   
		//将认证码存入SESSION
		request.getSession().setAttribute(Constants.VALIDATE_CODE, sRand);
		//图象生效   
		g.dispose();   
		ByteArrayOutputStream output = new ByteArrayOutputStream();   
		ImageOutputStream imageOut = ImageIO.createImageOutputStream(output);   
		ImageIO.write(image, "JPEG", imageOut);   
		imageOut.close();
		ByteArrayInputStream input = new ByteArrayInputStream(output.toByteArray());   
		this.setInputStream(input);
		response.getWriter().write(output.toString());
		return;
	}

	@ResponseBody
	@RequestMapping(value = "/registcode.json", method = {RequestMethod.GET, RequestMethod.POST})
	public void register(HttpServletRequest request, HttpServletResponse response) throws Exception{logger.info("11111___________");
		//在内存中创建图象   
		int width=65, height=20;   
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		//获取图形上下文   
		Graphics g = image.getGraphics();   
		//生成随机类   
		Random random = new Random();   
		//设定背景色   
		g.setColor(getRandColor(200,250));   
		g.fillRect(0, 0, width, height);   
		//设定字体   
		g.setFont(new Font("Times New Roman",Font.PLAIN,18));   
		//随机产生155条干扰线，使图象中的认证码不易被其它程序探测到   
		g.setColor(getRandColor(160,200));   
		for (int i=0; i<155; i++){   
			int x = random.nextInt(width);   
		    int y = random.nextInt(height);   
		    int xl = random.nextInt(12);   
	        int yl = random.nextInt(12);   
		    g.drawLine(x,y,x+xl,y+yl);   
		}   
		//取随机产生的认证码(6位数字)   
		String sRand = "";   
		for (int i=0; i<4; i++){   
			String rand = String.valueOf(random.nextInt(10));   
			sRand += rand;   
			// 将认证码显示到图象中   
			g.setColor(new Color(20 + random.nextInt(110),20 + random.nextInt(110),20 + random.nextInt(110)));   
			//调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成   
			g.drawString(rand,13*i+6,16);   
		}   
		//将认证码存入SESSION   
		request.getSession().setAttribute(Constants.VALIDATE_REGISTER_CODE, sRand);
		//图象生效   
		g.dispose();   
		ByteArrayOutputStream output = new ByteArrayOutputStream();   
		ImageOutputStream imageOut = ImageIO.createImageOutputStream(output);   
		ImageIO.write(image, "JPEG", imageOut);   
		imageOut.close();   
		ByteArrayInputStream input = new ByteArrayInputStream(output.toByteArray());   
		this.setInputStream(input);
		response.getWriter().write(output.toString());
		return;
	}

	/*   
	* 给定范围获得随机颜色   
	*/   
	private Color getRandColor(int fc,int bc){   
		Random random = new Random();   
		if(fc>255) fc=255;   
		if(bc>255) bc=255;   
		int r=fc+random.nextInt(bc-fc);   
		int g=fc+random.nextInt(bc-fc);   
		int b=fc+random.nextInt(bc-fc);   
		return new Color(r,g,b);   
	}

	public ByteArrayInputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(ByteArrayInputStream inputStream) {
		this.inputStream = inputStream;
	}

	
}
