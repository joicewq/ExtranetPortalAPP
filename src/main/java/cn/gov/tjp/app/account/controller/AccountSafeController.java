package cn.gov.tjp.app.account.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.pubframework.common.http.HttpUtil;
import com.qhgrain.common.util.EnvUtil;

@Controller
@RequestMapping("/account/safe")
public class AccountSafeController {
	private String url = EnvUtil.getVal("PAASOS_DEPEND_APIGDSALTACCOUNT01") ;
	
	@RequestMapping("/index")
	public String index(HttpServletRequest request){
		 String  loginId = (String) request.getSession().getAttribute("loginId");
		  if(StringUtils.isEmpty(loginId)){
			  return "/home/login/index";
		  }
		return "/account/safe/index";
	}
	
	@RequestMapping("/edit")
	public String edit(){
		return "/account/safe/edit";
	}

	@RequestMapping("/forgetEdit")
	public String forgetEdit(){
		return "/account/safe/forgetEdit";
	}
	
	@RequestMapping("/phoneEdit")
	public String phoneEdit(){
		return "/account/safe/phoneEdit";
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public String update(HttpServletRequest request){
		String res = "success";
		String result = "";
		try {
			String password = request.getParameter("password");
			String newPassword = request.getParameter("newPassword");
			
			HttpSession session = request.getSession();
			String loginId = (String)session.getAttribute("loginId");
			result = HttpUtil.get(url+ "/accountService/query/byId/"+loginId, new HashMap<String, String>());
			
			if (!password.equals(JSONObject.parseObject(result).getJSONObject("data").getString("loginPassword"))) {
				res = "errorByPassword";
				return res;
			}
			/****如果通过密码验证***/
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("loginId", loginId);
			params.put("loginPassword", newPassword);
			
			result = HttpUtil.postJsonParams(url+ "/accountService", "/modify", 
					JSONObject.parseObject(JSONObject.toJSONString(params)));
			if (!"1".equals(JSONObject.parseObject(result).getString("result"))) {
				res = "error";
			}
		} catch (IOException e) {
			e.printStackTrace();
			res = "error";
		}
		
		return res;
	}
	
	
}
