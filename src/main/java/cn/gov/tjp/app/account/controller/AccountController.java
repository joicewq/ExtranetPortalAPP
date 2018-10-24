package cn.gov.tjp.app.account.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pubframework.common.http.HttpUtil;
import com.qhgrain.common.util.EnvUtil;

import cn.gov.tjp.app.common.utils.CookieUtils;
import cn.gov.tjp.app.common.utils.ResultMessage;
import cn.gov.tjp.app.natural.model.UserVo;
import cn.gov.tjp.app.utils.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

/**
 * Created by lovercy on 22/11/2016.
 */
@Controller
@RequestMapping("/account")
public class AccountController {
	private String url = EnvUtil.getVal("PAASOS_DEPEND_APIGDSALTACCOUNT01") ;
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 跳转到账户注册jsp页面
     * @return
     */
    @RequestMapping("/register")
    public String register(Model model){
    	model.addAttribute("post_id", EnvUtil.getVal("post_id"));
        return "/account/register";
    }
    
    /**
     * 忘记密码
     * */
    @RequestMapping("/forgetPassword")
    public String forgetPassword(){
        return "/account/forgetPassword";
    }
    
    /**
     * 更改手机号码
     * */
    @RequestMapping("/changePhoneNumber")
    public String changePhoneNumber(){
        return "/account/changePhoneNumber";
    }
    
    /**
     * 忘记密码发送验证码
     * @param params
     * @return
     */
    @RequestMapping("/pwdSendMsg")
    @ResponseBody
    public Map<String, Object> pwdSendMsg(HttpServletRequest request){
    	Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String loginName = request.getParameter("loginName");
			//根据用户名查到登录ID和手机号码
			String result = HttpUtil.get(url+ "/accountService/query/byName/"+loginName, new HashMap<String, String>());
			JSONObject dataObject = JSONObject.parseObject(result).getJSONObject("data");
			if (dataObject==null) {
				resultMap.put("status", 0);
				resultMap.put("message", "该用户不存在");
				return resultMap;
			}
			String loginId = dataObject.getString("loginId");
			String phone = dataObject.getString("phone");
			resultMap.put("loginId", loginId);
			//根据手机号码发送验证码并返回
			String random = sendMessage(phone, EnvUtil.getVal("forget_pwd"));
			if ("400".equals(random)) {
				resultMap.put("status", 0);
				resultMap.put("message", "验证码发送失败，请检查是否一天内操作多次");
				return resultMap;
			}
			resultMap.put("random", random);	//验证码
			resultMap.put("status", 1);
			resultMap.put("message", "验证码发送成功，可以输入认证");
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("status", 0);
			resultMap.put("message", "未知错误");
		}
		return resultMap;
    }
    
    /**
     * 忘记密码通过验证后修改密码
     * @param params
     * @return
     */
    @RequestMapping("/pwdUpdate")
    @ResponseBody
    public Map<String, Object> pwdUpdate(HttpServletRequest request){
    	Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String loginId = request.getParameter("loginId");
			String newPassword = request.getParameter("newPassword");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("loginId", loginId);
			params.put("loginPassword", newPassword);
			
			String result = HttpUtil.postJsonParams(url+ "/accountService", "/modify", 
					JSONObject.parseObject(JSONObject.toJSONString(params)));
			if (!"1".equals(JSONObject.parseObject(result).getString("result"))) {
				resultMap.put("status", 0);
				resultMap.put("message", "未知错误");
			}else{
				resultMap.put("status", 1);
				resultMap.put("message", "修改成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("status", 0);
			resultMap.put("message", "未知错误");
		}
		return resultMap;
    }
    
    /**
     * 更改手机号发送验证码
     * @param request
     * @return
     */
    @RequestMapping("/cagPhoneSendMsg")
    @ResponseBody
    public Map<String, Object> cagPhoneSendMsg(HttpServletRequest request, String phone, String type){
    	Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String result = "";
			if (StringUtil.isNotEmpty(type)&&"old".equals(type)) {
				HttpSession session = request.getSession();
				String loginId = (String)session.getAttribute("loginId");
				result = HttpUtil.get(url+ "/accountService/query/byId/"+loginId, new HashMap<String, String>());
				if (!phone.equals(JSONObject.parseObject(result).getJSONObject("data").getString("phone"))) {
					resultMap.put("status", 0);
					resultMap.put("message", "输入的手机号码与原号码不一致");
					return resultMap;
				}
			}
			//根据手机号码发送验证码并返回
			String random = sendMessage(phone, EnvUtil.getVal("edit_phone"));
			if ("400".equals(random)) {
				resultMap.put("status", 0);
				resultMap.put("message", "验证码发送失败，请检查是否一天内操作多次");
			}else {
				resultMap.put("random", random);	//验证码
				resultMap.put("status", 1);
				resultMap.put("message", "验证码发送成功，可以输入认证");
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("status", 0);
			resultMap.put("message", "未知错误");
		}
		return resultMap;
    }
    
    /**
     * 发送验证码短信方法
     * @param mobile	手机号码
     * @param tplId		模板ID
     * @return 验证码
     */
    public String sendMessage(String mobile, String tplId){
    	String random = "";
        try {
        	String smsveriUrl = EnvUtil.getVal("PAASOS_DEPEND_APISMS01")+"/sms/pushVerificationCode";
        	Map<String, String> paramMap = new HashMap<String, String>();
        	paramMap.put("mobile", mobile);
        	paramMap.put("tplId", tplId);	
			String result = HttpUtil.post(smsveriUrl,JSON.parseObject(JSON.toJSONString(paramMap)));
			JSONObject object = JSONObject.parseObject(result);
			if ("400".equals(object.getString("http_status_code"))) {
				random = "400";
			}else {
				random = JSONObject.parseObject(result).getString("random");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return random;
    }

    /**
     * 添加账户信息(账户注册)
     * @param params
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public ResultMessage addAccount(HttpServletRequest request,String params){
    	ResultMessage result=new ResultMessage();
    	//注册验证手机验证码
/*    	String salt_verifyCode=CookieUtils.getValueNotDecrypt(request, "salt_verifyCode");
    	JSONObject data=JSONObject.parseObject(params);
    	String code=data.getString("phone-verifyCode");
    	if(!code.equals(salt_verifyCode)){
            result.setResult(ResultMessage.Fail);
            result.setMessage("操作失败,验证码错误!");
            return result;
    	}*/
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<String> formEntity = new HttpEntity<String>(params,headers);
        String url= EnvUtil.getVal("PAASOS_DEPEND_APIGDSALTACCOUNT01");
        url+="/accountService/add";
        String message = restTemplate.postForObject(url, formEntity, String.class);
        
        if(StringUtils.isEmpty(message)){
            result.setResult(ResultMessage.Fail);
            result.setMessage("操作失败,后台未返回结果!");
        }else{
            result= JSON.parseObject(message,ResultMessage.class);
        }
        return result;
    }

    /**
     * 判断用户名是否已被注册
     * @param loginName
     * @return
     */
    @RequestMapping("/exist")
    @ResponseBody
    public boolean loginNameIsExit(String loginName){
        String url=EnvUtil.getVal("PAASOS_DEPEND_APIGDSALTACCOUNT01");
        url+="/accountService/exist";
        if(StringUtils.isEmpty(loginName)){
            return false;
        }else{
            url+="/"+loginName;
            String message=restTemplate.getForObject(url,String.class);
            if(StringUtils.isEmpty(message)){
                return false;
            }else {
                ResultMessage result = JSON.parseObject(message, ResultMessage.class);
                if(result.getResult()==ResultMessage.Success){
                    return true;
                }else{
                    return false;
                }
            }
        }
    }
    
    /**
     * 判断手机号是否已被注册
     * @param phone
     * @return 	true	表示没有注册	可用
     * 			false	表示已注册	不可用
     */
    @RequestMapping("/phoneIsExit")
    @ResponseBody
    public boolean phoneIsExit(String phone){
    	boolean flag = false;
    	try {
    		String url = EnvUtil.getVal("PAASOS_DEPEND_APIGDSALTACCOUNT01") + "/accountService";
    		if(StringUtils.isEmpty(phone)){
    			flag = false;
    		}else{
    			Map<String, Object> paramMap = new HashMap<String, Object>();
    			paramMap.put("phone", phone);
    			String result = HttpUtil.postFormParams(url, "/queryByPhone", paramMap);
    			JSONObject resultObject = JSON.parseObject(result);
    			if ("1".equals(resultObject.getString("status"))) {
					flag = resultObject.getBoolean("data");
				}
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return flag;
    }

    
    /**
     * 修改用户手机号码
     * @param params
     * @return
     */
    @RequestMapping("/updateUserphone")
    @ResponseBody
    public Map<String, Object> updateUserPhone(HttpServletRequest request){
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	
    	String loginId = request.getSession().getAttribute("loginId").toString();
    	if(loginId!=null){
    		try {
    			String phone = request.getParameter("phone");
    			JSONObject params = new JSONObject();
    			params.put("phone", phone);
    			params.put("loginId",loginId);
    			String result = HttpUtil.post(url+ "/accountService/modify",params);
    			if (!"1".equals(JSONObject.parseObject(result).getString("result"))) {
    				resultMap.put("status", 0);
    				resultMap.put("message", "未知错误");
    			}else{
    				resultMap.put("status", 1);
    				resultMap.put("message", "修改成功");
    			}
    		} catch (Exception e) {
    			e.printStackTrace();
    			resultMap.put("status", 0);
    			resultMap.put("message", "未知错误");
    		}
    	}else{
    		resultMap.put("status", 0);
			resultMap.put("message", "登录超时,请重新登录");
    	}
		return resultMap;
    }
}
