package cn.gov.tjp.app.controller;
/*package com.gdsalt.app.tradeplatform.controller;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONPObject;
import com.gdsalt.app.tradeplatform.service.CommonService;
import com.gdsalt.app.tradeplatform.utils.DES;
import com.qhgrain.common.util.EnvUtil;
import com.qhgrain.ecom.common.dto.um.Account;
import com.qhgrain.ecom.common.util.CommonStatus;
import com.qhgrain.ecom.common.util.ResultMessage;


@Controller
@RequestMapping("/loginController")
public class LoginsController {	
	
	@Autowired
	private CommonService commonService;
	
	private String  ecomUmService=EnvUtil.getVal("PAASOS_DEPEND_APIECOMUM");
    private String  userAPI="/userService";
    private String getAccount="/account/query";
	
	
	*//**
	 * 用户登录
	 * @param account
	 * @param request
	 * @param response
	 * @return
	 *//*
	@RequestMapping("/login")
	@ResponseBody
	public ResultMessage userLogin(Account account,HttpServletRequest request,HttpServletResponse response){
		ResultMessage result= new ResultMessage();
		if(account==null){
			result.setResult(ResultMessage.Fail);
			result.setMessage("登录信息为空!");
		}else{
			String loginName = account.getLoginName();
			String password = account.getLoginPassword();
			if(StringUtils.isEmpty(loginName)){
			    result.setResult(ResultMessage.Fail);
	            result.setMessage("登录名为空!");
			}else if(StringUtils.isEmpty(password)){
			    result.setResult(ResultMessage.Fail);
                result.setMessage("密码为空!");
			}else{
				String url=ecomUmService+userAPI+getAccount;
				url+="/loginName/"+loginName;
				try{
				    String accountStr = commonService.getForObject(url);
				    if(StringUtils.isEmpty(accountStr)){
				        result.setResult(ResultMessage.Fail);
		                result.setMessage("该用户未注册!");
				    }else{
				        Account ac= JSON.parseObject(accountStr,Account.class);
				        if(ac!=null){
				           if(ac.getBizStatus()==CommonStatus.BIZ_STATUS_CANCEL || ac.getIsDelete()==CommonStatus.DELETE_STATUS_TRUE){//判断用户状态是否为注销状态
				               result.setResult(ResultMessage.Fail);
	                           result.setMessage("该用户已被注销!"); 
				           }else{
				               if(password.equals(ac.getLoginPassword())){
				                   String loginId=ac.getLoginId();
				                   //系统内用session,系统间用cookie
				                   HttpSession session=request.getSession();
		                           session.setAttribute("loginId",loginId);
		                           DES des=new DES(DES.SECRET_KEY);
		                           String encrypt=des.encrypt(loginId);
		                           Cookie cookie = new Cookie("loginId", encrypt);   
		                           cookie.setMaxAge(1800);//设置cookie的声明周期为半小时,28800=0.5*60*60(秒)
		                           cookie.setPath("/"); 
		                           response.addCookie(cookie);
		                           result.setResult(ResultMessage.Success);
		                           result.setMessage("登录成功!");
		                       }else{
		                           result.setResult(ResultMessage.Fail);
		                           result.setMessage("用户名或密码错误!");
		                       }
				           } 
				        }else{
				            result.setResult(ResultMessage.Fail);
	                        result.setMessage("该用户未注册!");  
				        }
				    }
				}catch(Exception e){
				    e.printStackTrace();
				    result.setResult(ResultMessage.Fail);
				    result.setMessage("登录失败!");
				}
			}
		}
		return result;
	}
	
	
	
	*//**
	 * 判断用户是否登录
	 * Description
	 * @param 
	 * @return 
	 * @author Lovercy
	 * @date 2016年4月15日
	 *//*
    @RequestMapping("/checkStatus")
    @ResponseBody
    public JSONPObject isLogin(HttpServletRequest request,HttpServletResponse response){
        String callback=request.getParameter("callback");
        ResultMessage result=new ResultMessage();
	    String  loginId="";
	    Cookie[] cookie = request.getCookies();
	    if(cookie!=null && cookie.length>0){
	        for (int i = 0; i < cookie.length; i++) {
	            Cookie cook = cookie[i];
	            if(cook.getName().equalsIgnoreCase("loginId")){ //获取键 
	                DES des=new DES(DES.SECRET_KEY);
	                loginId=des.decrypt(cook.getValue());    //获取值 
	            }
	        }
	    }
        if(StringUtils.isEmpty(loginId) || StringUtils.isEmpty(loginId.trim())){
            result.setResult(ResultMessage.Fail);
            result.setMessage("用户未登录!");
        }else{
            String url=ecomUmService+userAPI+getAccount;
            url+="/loginId/"+loginId;
            try{
                String accountStr = commonService.getForObject(url);
                if(StringUtils.isEmpty(accountStr) || accountStr.trim().equals("{}")){
                    result.setResult(ResultMessage.Fail);
                    result.setMessage("用户未注册!");
                }else{
                    Account ac= JSON.parseObject(accountStr,Account.class);
                    Map<String,Object> resultMsg=new HashMap<String,Object>();
                    resultMsg.put("loginName",ac.getLoginName());
                    //获取此用户关联的供需信息,站内信等信息的数量
                    result.setResult(ResultMessage.Success);
                    result.setMessage(resultMsg);
                }
            }catch(Exception e){
                e.printStackTrace();
                result.setResult(ResultMessage.Fail);
                result.setMessage("获取用户信息失败!");
            }
        }
        JSONPObject jpo=new JSONPObject(callback);
        jpo.addParameter(result);
        System.out.println(JSON.toJSONString(jpo));
        return jpo;
    }
	
}
*/