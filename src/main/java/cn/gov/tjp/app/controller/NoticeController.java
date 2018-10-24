package cn.gov.tjp.app.controller;
/*package com.gdsalt.app.tradeplatform.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.gdsalt.app.tradeplatform.service.CommonService;
import com.qhgrain.common.util.EnvUtil;
import com.qhgrain.ecom.common.dto.sm.Notification;


*//**
 * 公告管理    ：发送、查询、删除  、修改
 * @author wangzhichao
 *
 *//*
@Controller
@RequestMapping("/notice")
public class NoticeController {	
	
    private String noticeService=EnvUtil.getVal("PAASOS_DEPEND_APIECOMSM")+"/noticeService";
	
	@Autowired
    private CommonService commonService;
	
	*//**
	 * 发送站内信
	 * @param letterVO
	 * @return
	 * @throws IOException
	 *//*
	@RequestMapping(value="/addNotice")
	@ResponseBody
	public Map<String,Object> addNotice(@RequestBody Notification notice) throws IOException{
		
		Map<String,Object> result=new HashMap<String,Object>();
		 
		if(StringUtils.isEmpty(noticeService)){
			result.put("result", "0");
			result.put("message", "服务地址不存在!");
		}else{
		    
			if(notice == null ){
				result.put("result", "0");
				result.put("message", "传递的站内信信息为空!");
				return result;
			}
			try{
				String resultStr=commonService.postForObject(notice, noticeService,"notice/add");
				result=JSON.parseObject(resultStr,Map.class);
			}catch(Exception e){
				e.printStackTrace();
				result.put("result", "0");
				result.put("message", "添加失败!"+e);
			}
		}
		return result;
	}
	
	
	*//**
	 * 查询站内信列表    支持管理员、用户查询  
	 * @param letterVO
	 * @return
	 * @throws IOException
	 *//*
	@RequestMapping(value="/getNoticeList/{pageNum}/{pageSize}",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getNoticeList(@RequestBody Notification notice,@PathVariable String pageNum,@PathVariable String pageSize) throws IOException{
		
		Map<String,Object> result=new HashMap<String,Object>();
		
		if(StringUtils.isEmpty(noticeService)){
			result.put("result", 0);
			result.put("message", "服务地址不存在!");
		}else{
			
			if(notice == null){
				result.put("result", 0);
				result.put("message", "传递的公告信息为空!");
				return result;
			}
			String rex = "^[1-9]\\d*$";
			Pattern p = Pattern.compile(rex);
			Matcher m = p.matcher(pageNum);
			if (!m.matches()){
			    result.put("result", 0);
                result.put("message", "请输入正整数");
                return result;
			}
			
			try{
				 String resultMap=commonService.postForObject(notice,noticeService,"notice/query/",pageNum,pageSize);
				 
				 result = JSON.parseObject(resultMap, Map.class);
				 
			}catch(Exception e){
				e.printStackTrace();
				result.put("result", 0);
				result.put("message", "查询失败!"+e);
			}
		}
		return result;
	}
	
	*//**
	 * 删除站内信  
	 * @param letterVO
	 * @return
	 * @throws IOException
	 *//*
	@RequestMapping(value="/delNotice",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> delNotice(@RequestBody List<String> noticeIdList) throws IOException{
		
		Map<String,Object> result=new HashMap<String,Object>();
		
		if(StringUtils.isEmpty(noticeService)){
			result.put("result", 0);
			result.put("message", "服务地址不存在!");
		}else{
			
			if(noticeIdList == null && noticeIdList.size()>0){
				result.put("result", 0);
				result.put("message", "传递的id数组为空");
				return result;
			}
			
			try{
				String resultStr = commonService.postForObject(noticeIdList,noticeService,"notice/batchDelete");
				result=JSON.parseObject(resultStr,Map.class);
			}catch(Exception e){
				e.printStackTrace();
				result.put("result", 0);
				result.put("message", "删除失败!"+e);
			}
		}
		return result;
	}
	
	*//**
	 * 删除站内信  
	 * @param letterVO
	 * @return
	 * @throws IOException
	 *//*
	@RequestMapping(value="/updNotice",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updNotice(@RequestBody Notification notice) throws IOException{
	    
	    Map<String,Object> result=new HashMap<String,Object>();
	    
	    if(StringUtils.isEmpty(noticeService)){
	        result.put("result", 0);
	        result.put("message", "服务地址不存在!");
	    }else{
	        
	        if(notice == null ){
	            result.put("result", 0);
	            result.put("message", "letterDeleteVO对象为空");
	            return result;
	        }
	        
	        try{
	            String resultStr = commonService.postForObject(notice,noticeService,"notice/update",notice.getNotId());
	            result=JSON.parseObject(resultStr,Map.class);
	        }catch(Exception e){
	            e.printStackTrace();
	            result.put("result", 0);
	            result.put("message", "更新失败!"+e);
	        }
	    }
	    return result;
	}
	
	*//**
	 * 查询站内信内容
	 * Description
	 * @param 
	 * @return 
	 * @author wangzhichao
	 * @date 2016年3月22日
	 *//*
	@RequestMapping(value="/getNoticeInfo",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getNoticeInfo(String noticeId) throws IOException{
	    
	    Map<String,Object> result=new HashMap<String,Object>();
	    
	    if(StringUtils.isEmpty(noticeService)){
	        result.put("result", 0);
	        result.put("message", "服务地址不存在!");
	    }else{
	        
	        if(StringUtils.isEmpty(noticeId)){
	            result.put("result", 0);
	            result.put("message", "letterId为空");
	            return result;
	        }
	        
	        try{
	            String resultStr = commonService.getForObject(noticeService,"notice/query",noticeId);
	            result=JSON.parseObject(resultStr,Map.class);
	        }catch(Exception e){
	            e.printStackTrace();
	            result.put("result", 0);
	            result.put("message", "删除失败!"+e);
	        }
	    }
	    return result;
	}

}
*/