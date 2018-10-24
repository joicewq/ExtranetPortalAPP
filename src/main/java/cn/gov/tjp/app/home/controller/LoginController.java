/**
 * 2016年11月25日 下午5:53:04
 * @auto Jack.Hou
 * @Copyright 1999-2020 http://www.yihecloud.com/ Croporation Limited.
 */
package cn.gov.tjp.app.home.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.qhgrain.common.util.EnvUtil;
import com.qhgrain.ecom.common.util.CommonStatus;
import com.qhgrain.ecom.common.util.ResultMessage;

import cn.gov.tjp.app.account.service.AccountService;
import cn.gov.tjp.app.common.utils.CookieUtils;
import cn.gov.tjp.app.common.utils.DES;
import cn.gov.tjp.app.home.util.Account;
import cn.gov.tjp.app.utils.StringView;

/**
 * @author Administrator 首页-登录
 */
@Controller
@RequestMapping("/login")
public class LoginController {

	private static Logger LOG = LoggerFactory.getLogger(LoginController.class);
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private AccountService accountService;

	@RequestMapping(value = "/index")
	public String index() {
		return "/home/login/login";
	}
	@RequestMapping(value = "/toIndex")
	public String toIndex() {
		return "/home/login/index";
	}
	@RequestMapping(value = "/logout")
	public String logout( HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookie = request.getCookies();
		if (cookie != null && cookie.length > 0) {
			for (int i = 0; i < cookie.length; i++) {
				cookie[i].setMaxAge(0);
				cookie[i].setPath("/");
				response.addCookie(cookie[i]);
			}
		}
		request.getSession().setAttribute("loginId", null);
		return "/home/login/login";
	}
	@RequestMapping(value = "/sendMsg")
	@ResponseBody
	public ModelAndView sendMsg( HttpServletRequest request, HttpServletResponse response) {
		StringView view=new StringView();
		try {
			String url=EnvUtil.getVal("PAASOS_DEPEND_APISMS01")+"/sms/pushVerificationCode";
			Map<String, String> map=new HashMap<String, String>();
			map.put("mobile", request.getParameter("phone"));
			map.put("tplId", EnvUtil.getVal("user_register"));//注册模板ID
			
			String jsonStr= null;//
//			jsonStr= HttpUtil.post(url, JSONObject.parseObject(JSON.toJSONString(map)));
			
			HttpHeaders headers = new HttpHeaders();
			MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
			headers.setContentType(type);
			headers.add("Accept", MediaType.APPLICATION_JSON.toString());
			HttpEntity<String> formEntity = new HttpEntity<String>(JSON.toJSONString(map), headers);
			jsonStr = restTemplate.postForObject(url , formEntity, String.class);
			 
			JSONObject data=JSONObject.parseObject(jsonStr);
			view.setContent("success");
			CookieUtils.setValue(response, "salt_verifyCode", data.getString("random"));
		} catch (Exception e) {
			view.setContent("error");
		}
		return new ModelAndView(view);
	}

	/**
	 * 用户登录
	 * 
	 * @param account
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/login")
	@ResponseBody
	public ResultMessage userLogin(Account account, HttpServletRequest request, HttpServletResponse response) {
		ResultMessage result = new ResultMessage();
		if (account == null) {
			result.setResult(0);
			result.setMessage("登录信息为空!");
		} else {
			String loginName = account.getLoginName();
			String password = account.getLoginPassword();
			if (StringUtils.isEmpty(loginName)) {
				result.setResult(0);
				result.setMessage("登录名为空!");
			} else if (StringUtils.isEmpty(password)) {
				result.setResult(1);
				result.setMessage("密码为空!");
			} else {
				try {
					Account ac = accountService.findByLoginInfo(loginName, null);
					if (StringUtils.isEmpty(ac)) {
						result.setResult(0);
						result.setMessage("该用户未注册!");
						return result;
					}
					if(StringUtils.isEmpty(ac.getLoginType())||!ac.getLoginType().equals("S")){
						result.setResult(0);
						result.setMessage("该用户未注册!");
						return result;
					}
					JSONObject supplier=accountService.getCompany(ac.getLoginId(), ac.getLoginType());
					if (supplier!=null&&supplier.getInteger("bizStatus")!=null&&supplier.getInteger("bizStatus") == 0) {// 判断用户状态是否为注销状态
						result.setResult(0);
						result.setMessage("该用户已被注销!");
					} else {
						if (password.equals(ac.getLoginPassword())) {
							String loginId = ac.getLoginId();
							// 系统内用session,系统间用cookie
							HttpSession session = request.getSession();
							session.setAttribute("loginId", loginId);
							session.setAttribute("loginType", ac.getLoginType());
							session.setAttribute("saltUserInfo", JSON.toJSON(ac));
							DES des = new DES(DES.SECRET_KEY);
							String encrypt = des.encrypt(loginId);
							Cookie cookie = new Cookie("loginId", encrypt);
/*									Cookie cookie1 = new Cookie("loginType", des.encrypt(ac.getLoginType()));*/
							cookie.setMaxAge(1800);// 设置cookie的声明周期为半小时,28800=0.5*60*60(秒)
							cookie.setPath("/");
							response.addCookie(cookie);
/*									cookie1.setMaxAge(1800);// 设置cookie的声明周期为半小时,28800=0.5*60*60(秒)
							cookie1.setPath("/");
							response.addCookie(cookie1);*/
							result.setResult(2);
							result.setMessage("登录成功!");
						} else {
							result.setResult(1);
							result.setMessage("用户名或密码错误!");
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					result.setResult(1);
					result.setMessage("登录失败!");
				}
			}
		}
		return result;
	}

	/**
	 * 判断用户是否登录 Description
	 * 
	 * @param
	 * @return
	 * @author
	 */
	@RequestMapping("/checkStatus")
	@ResponseBody
	public JSONPObject isLogin(HttpServletRequest request, HttpServletResponse response) {
		String callback = request.getParameter("callback");
		ResultMessage result = new ResultMessage();
		String loginId = (String) request.getSession().getAttribute("loginId");
/*		Cookie[] cookie = request.getCookies();
		if (cookie != null && cookie.length > 0) {
			for (int i = 0; i < cookie.length; i++) {
				Cookie cook = cookie[i];
				if (cook.getName().equalsIgnoreCase("loginId")) { // 获取键
					DES des = new DES(DES.SECRET_KEY);
					loginId = des.decrypt(cook.getValue()); // 获取值
				}
			}
		}*/
		if (StringUtils.isEmpty(loginId) || StringUtils.isEmpty(loginId.trim())) {
			result.setResult(ResultMessage.Fail);
			result.setMessage("用户未登录!");
		} else {

			try {
				Account islogin = accountService.findByLoginInfo(null, loginId);
				if (StringUtils.isEmpty(islogin) || islogin.equals("{}")) {
					result.setResult(ResultMessage.Fail);
					result.setMessage("用户未注册!");
				} else {
					Map<String, Object> resultMsg = new HashMap<String, Object>();
					resultMsg.put("loginName", islogin.getLoginName());
					// 获取此用户关联的供需信息,站内信等信息的数量
					result.setResult(ResultMessage.Success);
					result.setMessage(resultMsg);
				}
			} catch (Exception e) {
				e.printStackTrace();
				result.setResult(ResultMessage.Fail);
				result.setMessage("获取用户信息失败!");
			}
		}
		JSONPObject jpo = new JSONPObject(callback);
		jpo.addParameter(result);
		LOG.info("判断用户是否登录:"+JSON.toJSONString(jpo));
		return jpo;
	}
	
	/**
	 * 删除session里的信息,用于用户退出系统
	 *
	 */
	@RequestMapping("/delsession")
	@ResponseBody
	public Map<String, Object> delsession(HttpServletRequest request, HttpServletResponse response) {

		request.getSession().removeAttribute("loginId");
		Map<String, Object> result = new HashMap<String, Object>();
		Cookie cookie = new Cookie("loginId", null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
		Object userVO = request.getSession().getAttribute("saltUserInfo");
		if (userVO != null) {
			request.getSession().removeAttribute("saltUserInfo");
		}
		result.put("result", 1);
		result.put("message", "操作成功");
		return result;
	}
	
	/**
	 * 删除session里的信息,用于用户退出系统
	 *
	 */
	@RequestMapping("/disVerifyCode")
	@ResponseBody
	public String disVerifyCode(HttpServletRequest request, HttpServletResponse response) {
		try {
			CookieUtils.setValue(response, "salt_verifyCode", null);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		
		return "success";
	}
}
