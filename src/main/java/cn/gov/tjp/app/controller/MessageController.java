package cn.gov.tjp.app.controller;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONPObject;
import com.qhgrain.common.util.EnvUtil;
import com.qhgrain.ecom.common.dto.sm.LetterVO;
import com.qhgrain.ecom.common.dto.um.User;
import com.qhgrain.ecom.common.dto.um.UserVO;
import com.qhgrain.ecom.common.util.ResultMessage;

import cn.gov.tjp.app.service.CommonService;
import cn.gov.tjp.app.utils.DES;


@Controller
@RequestMapping("/messageController")
public class MessageController {	
	
	@Autowired
	private CommonService commonService;
    private String  ecomUmService=EnvUtil.getVal("PAASOS_DEPEND_APIECOMUM");
    private String  userAPI="/userService";
    private String getAccount="/account/query";
    private String getByLoginId="/user/query";
    private String  letterService=EnvUtil.getVal("PAASOS_DEPEND_APIECOMSM")+"/letterService";;
	
	
	/**
	 * 判断用户是否登录
	 * Description
	 * @param 
	 * @return 
	 * @author Lovercy
	 * @date 2016年4月15日
	 */
    @RequestMapping("/getUnreadNum")
    @ResponseBody
    public JSONPObject getUnreadNum(HttpServletRequest request,HttpServletResponse response){
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
            String userId = "";
            ResultMessage resultMessage = getByLoginId( loginId,request, response);
            
            if(resultMessage != null){
                if(resultMessage.getResult() == ResultMessage.Success){
                     userId = (String)resultMessage.getMessage();
                }else{
                    result.setResult(ResultMessage.Fail);
                    result.setMessage(resultMessage.getMessage());
                    
                }
            }
            LetterVO letterVO = new LetterVO();
            
            letterVO.setReceiveId(userId);
            // TODO 调用查询角色的方法  ，  再设置角色信息 
            
            try{
                String resultStr = commonService.postForObject(letterVO,letterService,"receiveLetter/unreadNum");
                result = JSON.parseObject(resultStr,ResultMessage.class);
            }catch(Exception e){
                e.printStackTrace();
                
                result.setResult(ResultMessage.Fail);
                result.setMessage("查询失败!"+e);
                
            }
        }
        JSONPObject jpo=new JSONPObject(callback);
        jpo.addParameter(result);
        System.out.println(JSON.toJSONString(jpo));
        return jpo;
    }
    
    public ResultMessage getByLoginId(String loginId,HttpServletRequest request,HttpServletResponse response){
        
        ResultMessage result=new ResultMessage();
       
        if(StringUtils.isEmpty(loginId)){
            result.setResult(ResultMessage.Fail);
            result.setMessage("无法获取用户信息!");
        }else{
            String url=ecomUmService+userAPI+getByLoginId;
            url+="/"+loginId;
            try{
                String voStr=commonService.getForObject(url);
                if(StringUtils.isEmpty(voStr) || voStr.trim().equals("{}")){
                    result.setResult(ResultMessage.Fail);
                    result.setMessage("未能找到您的用户信息!");
                }else{
                    UserVO userVo=JSON.parseObject(voStr, UserVO.class);
                    
                    User user = new User();
                    String userId = "";
                    if(userVo != null){
                         user = userVo.getUser();
                    }
                    if(user!= null){
                         userId = user.getUserId();
                    }
                    result.setResult(ResultMessage.Success);
                    result.setMessage(userId);
                }
            }catch(Exception e){
                e.printStackTrace();
                result.setResult(ResultMessage.Fail);
                result.setMessage("获取用户信息失败!");
            }
        }
        return result;
    }
}
