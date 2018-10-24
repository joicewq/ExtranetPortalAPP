package cn.gov.tjp.app.common.controller;

import com.qhgrain.ecom.common.util.VerifyCodeUtils;

import cn.gov.tjp.app.common.utils.DES;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/verify")
public class VerifyCodeController {
	/**
	 * 生成验证码
	 * Description
	 * @param 
	 * @return 
	 * @author Lovercy
	 * @date 2016年4月29日
	 */
	@RequestMapping("/code")
	public void getCode(HttpServletRequest request, HttpServletResponse response){
		response.setHeader("Pragma", "No-cache");  
        response.setHeader("Cache-Control", "no-cache");  
        response.setDateHeader("Expires", 0);  
        response.setContentType("image/jpeg");
        //生成随机字串  
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
		//将生成的字符串DES加密后添加到cookie中
		DES des=new DES(DES.SECRET_KEY);
		String encrypt=des.encrypt(verifyCode);
		Cookie cookie = new Cookie("rand", encrypt);
		cookie.setMaxAge(1800);//设置cookie的声明周期为半小时,28800=0.5*60*60(秒)
		cookie.setPath("/");
		response.addCookie(cookie);
        //生成图片  
        int w = 160, h = 70;  
        try {
			VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);
		} catch (IOException e) {
			e.printStackTrace();
		}  
    }
	
	/**
	 * 判断用户输入的验证码与生成的验证码是否一致
	 * Description
	 * @param 
	 * @return 
	 * @author Lovercy
	 * @date 2016年4月29日
	 */
	@RequestMapping("/compare")
	@ResponseBody
	public boolean compareCode(HttpServletRequest request, HttpServletResponse response){
		String newCode=request.getParameter("verifyCode");
		String oldCode=null;
		/**
		 * 从cookie中获取之前生成的验证码,解密后与用户输入的验证码对比
         */
		Cookie[] cookie = request.getCookies();
		if(cookie!=null && cookie.length>0){
			for (int i = 0; i < cookie.length; i++) {
				Cookie cook = cookie[i];
				if(cook.getName().equalsIgnoreCase("rand")){ //获取键
					DES des=new DES(DES.SECRET_KEY);
					oldCode=des.decrypt(cook.getValue());    //获取值
				}
			}
		}
		if(StringUtils.isEmpty(newCode)){
			return false;
		}else{
			if(StringUtils.isEmpty(oldCode)){
				return false;
			}else{
				if(newCode.toLowerCase().equals(oldCode.toLowerCase())){
					return true;
				}else{
					return false;
				}
			}
		}
	}
}
