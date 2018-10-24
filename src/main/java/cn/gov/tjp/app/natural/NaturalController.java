package cn.gov.tjp.app.natural; 


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pubframework.common.http.HttpUtil;
import com.qhgrain.common.util.EnvUtil;

import cn.gov.tjp.app.account.service.AccountService;
import cn.gov.tjp.app.common.utils.ResultMessage;
import cn.gov.tjp.app.utils.StringView;
import net.sf.json.JSONArray;



@Controller
@RequestMapping("/natural")
public class NaturalController {
	private String natural_url = EnvUtil.getVal("PAASOS_DEPEND_APIAPIGDSALTNATURAL01")+"/naturalService";
	private String prodlib_url = EnvUtil.getVal("PAASOS_DEPEND_APIGDSALTPRODLIB01")+"/categoryService";
	private static Logger logger = LoggerFactory.getLogger(NaturalController.class);
	//会员认证状态-认证成功
	public static final Integer APPROVAL_SUCCESS = 11;
	//会员认证状态-认证失败
	public static final Integer APPROVAL_FAIL = 12;
	
	@Autowired
	private AccountService accountService;
	@Autowired
	private RestTemplate restTemplate;
	
	
	
	@RequestMapping("/naturalindex")
	public String naturalindex(HttpServletRequest request,Model model,HttpServletResponse response){
		  String  loginId = (String) request.getSession().getAttribute("loginId");
		  if(StringUtils.isEmpty(loginId)){
			  return "/home/login/index";
		  }else{
			  //取得用户是否认证
			  JSONObject member= accountService.getCompany(loginId,"S");
			  if(member!=null){
				  Object approvalStatus=member.getString("approvalStatus");
				  //企业备案认证状态11：同意 12：驳回
				  if(approvalStatus!=null && !approvalStatus.toString().equals("null")){
					 model.addAttribute("approvalStatus", approvalStatus.toString());
				  }  
			  }
		  }
		  return "/natural/index";
	}
	
//	
//	@RequestMapping("/addsave")
//	public String addsave(Model model){
//		String url = EnvUtil.getVal("PAASOS_DEPEND_APIGDSALTPRODLIB01");
//		Map<String, Object> params = new HashMap<String, Object>();
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("level", 1);
//		params.put("queryInfo", JSON.toJSONString(map));
//		String result = "";
//		try {
//			result = HttpUtil.postFormParams(url, "/query", params);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		JSONObject json = JSON.parseObject(result);
//		String noticeString = json.get("message").toString();
//		if (!noticeString.isEmpty()) {
//			JSONArray noticeArray = JSONArray.fromObject(noticeString);
//			List<Map> noticeMap = new ArrayList<Map>();
//
//			for (int i = 0; i < noticeArray.size(); i++) {
//				net.sf.json.JSONObject o = noticeArray.getJSONObject(i);
//				noticeMap.add(o);
//			}
//			model.addAttribute("naturalList", noticeMap);
//		}
//		return "/natural/edit";
//	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("/add")
	public ModelAndView add(HttpServletRequest request,Model model){
		ModelAndView view = new ModelAndView("/natural/edit");
		try {
			String url = EnvUtil.getVal("PAASOS_DEPEND_APIGDSALTPRODLIB01");
			Map<String, Object> params = new HashMap<String, Object>();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("level", 1);
			params.put("queryInfo", JSON.toJSONString(map));
			String result = HttpUtil.postFormParams(url, "/categoryService/query", params);
			JSONObject json = JSON.parseObject(result);
			String noticeString = json.get("message").toString();
			if (!noticeString.isEmpty()) {
				JSONArray noticeArray = JSONArray.fromObject(noticeString);
				List<Map> noticeMap = new ArrayList<Map>();

				for (int i = 0; i < noticeArray.size(); i++) {
					net.sf.json.JSONObject o = noticeArray.getJSONObject(i);
					noticeMap.add(o);
				}
				model.addAttribute("naturalList", noticeMap);
				view.addObject("naturalList", noticeMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}
	@SuppressWarnings("rawtypes")
	@RequestMapping("/caregary")
	@ResponseBody
	public String caregaryChange(HttpServletRequest request,Model model,String id,String idName) throws IOException{
		JSONObject resul = new JSONObject();
		String url = EnvUtil.getVal("PAASOS_DEPEND_APIGDSALTPRODLIB01");
		String  nameNo = request.getParameter("nameNo").equals("")?id:request.getParameter("nameNo");
		
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nameNo", nameNo);
		params.put("queryInfo", JSON.toJSONString(map));
		String result = "";
		result = HttpUtil.postFormParams(url, "/categoryService/query", params);
		JSONObject json = JSON.parseObject(result);
		String noticeString = json.get("message").toString();
		//List<JSONObject> arrayResult = JSON.toJavaObject(JSON.parseArray(String.valueOf(json.get("message"))),List.class);
		if (!noticeString.isEmpty()) {
			JSONArray noticeArray = JSONArray.fromObject(noticeString);
			List<Map> noticeMap = new ArrayList<Map>();

			for (int i = 0; i < noticeArray.size(); i++) {
				net.sf.json.JSONObject o = noticeArray.getJSONObject(i);
				noticeMap.add(o);
			}
			model.addAttribute("naturalList", noticeMap);
			
			resul.put("naturalchild", noticeMap);
			resul.put("status", 0);
			
		}else{
			resul.put("status", 1);
		}
		return resul.toString();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/query")
	@ResponseBody
	public Map<String, Object> query(HttpServletRequest request, Integer pageNo, Integer pageSize){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			//String id = request.getParameter("id");//审核历史
			
			  //后期完善获取当前登录用户所在企业ID,通过企业ID查找
			  String  loginId = (String) request.getSession().getAttribute("loginId");
			  String companyId="";
			  String companyName="";
				if(!StringUtils.isEmpty(loginId)){
					String resultStr = HttpUtil.get(EnvUtil.getVal("PAASOS_DEPEND_APIGDSALTSUPPLIER01")+"/supplierService/query/byAccount/"+loginId, "");
					JSONObject objResult = JSON.parseObject(resultStr);
					if(objResult.get("data")!=null){
						Object obj = objResult.get("data");
						Map result1Map = JSON.parseObject(obj.toString(), Map.class);
						companyId = (String)result1Map.get("companyId");
						companyName = (String)result1Map.get("companyName");
					}
				}
			
			
			String startDate = request.getParameter("startDate");//企业名称
			String endDate = request.getParameter("endDate");//企业名称
			String saltName = request.getParameter("saltName");//企业名称
			if (StringUtils.isEmpty(pageNo)) {
				pageNo = 1;
			}
			if (StringUtils.isEmpty(pageSize)) {
				pageSize = 10;
			}
		  if(!companyName.equals("") && !companyId.equals("")){
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("pageNo", pageNo);
			params.put("pageSize", pageSize);
			params.put("startDate", startDate);
			params.put("endDate", endDate);
			params.put("saltName", saltName);
			params.put("companyName", companyName);
			params.put("companyId", companyId);
			String result = "";
			result = HttpUtil.postFormParams(natural_url, "/query", params);
			JSONObject json = JSON.parseObject(result);
			List<JSONObject> arrayResult = JSON.toJavaObject(JSON.parseArray(String.valueOf(json.get("data"))),List.class);
			resultMap.put("data", arrayResult);
			// 总记录数
			resultMap.put("totalRow", json.get("totalRow"));
			// 总页数
			resultMap.put("totalPage",json.get("totalPage"));
			// 当前页数
			resultMap.put("curPage", json.get("curPage"));
			// 每页记录数
			resultMap.put("pageLine", json.get("pageLine"));
		  }else{
			List<JSONObject> arrayResult = new ArrayList<JSONObject>();
			resultMap.put("data", arrayResult);
			// 总记录数
			resultMap.put("totalRow", 0);
			// 总页数
			resultMap.put("totalPage",0);
			// 当前页数
			resultMap.put("curPage", 0);
			// 每页记录数
			resultMap.put("pageLine",0);
		  }
		} catch (Exception e) {
			logger.error("查询出现异常...." + e.getMessage());
		}
		return resultMap;
	}
	
	
	 /**
     * 保存供货商注册信息(到数据库表salt_suppliers_temp中)
     * @param supplierTemp
     * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/save")
    @ResponseBody
    public synchronized ResultMessage save(HttpServletRequest request,String natural) throws ClientProtocolException, IOException{
    	ResultMessage result=new ResultMessage();
    	JSONObject json =   JSONObject.parseObject(natural);
    	String saltNameChild  = json.getString("saltNameChild");
    	
    	if(!StringUtils.isEmpty(saltNameChild)){
        	String province  = json.getString("province");
        	String preId  = json.getString("categoryId");
        	String nameNO  = json.getString("nameNO");
        	String supplier =json.getString("provinceName");
    		String level = "2";
    		Map<String, Object> map = new HashMap<String,Object>();

    		if (!StringUtils.isEmpty(saltNameChild)) {
    			map.put("name", saltNameChild);
    		}
    		if (!StringUtils.isEmpty(nameNO)&& !StringUtils.isEmpty(province)) {
    			map.put("nameNo", province+nameNO);
    		}
    		if (!StringUtils.isEmpty(preId)) {
    			map.put("preId", preId);
    		}
    		if (!StringUtils.isEmpty(level)) {
    			map.put("level", Integer.parseInt(level));
    		}
    		if (!StringUtils.isEmpty(supplier)) {
    			map.put("supplier", supplier);
    		}
    		String categoryJson = JSON.toJSONString(map);
    		Map<String, Object> paramMap = new HashMap<>();
    		paramMap.put("categoryJson", categoryJson);
    		String category = HttpUtil.postFormParams(prodlib_url, "/add", paramMap);
    		JSONObject cate = JSONObject.parseObject(category);
    		if(cate.getString("status").equals("1")){
    			JSONObject cateGory =	cate.getJSONObject("message");
    			String id = cateGory.getString("id");
    			String nameNo = cateGory.getString("nameNo");
    			
    			json.put("remark", nameNo);//产品编号
    			json.put("remark2", id);
    			String message="";
    			try {
    		    	  //后期完善获取当前登录用户所在企业ID,通过企业ID查找
    		    	  Map  newStocke=JSON.parseObject(json.toJSONString(),Map.class);
    		    	  //后期完善获取当前登录用户所在企业ID,通过企业ID查找
    		   		  String  loginId = (String) request.getSession().getAttribute("loginId");
    		   		  String companyName="";
    		   		  String companyId="";
    		   			if(!StringUtils.isEmpty(loginId)){
    		   				String resultStr = HttpUtil.get(EnvUtil.getVal("PAASOS_DEPEND_APIGDSALTSUPPLIER01")+"/supplierService/query/byAccount/"+loginId, "");
    		   				JSONObject objResult = JSON.parseObject(resultStr);
    		   				Object obj = objResult.get("data");
    		   				Map resultMap = JSON.parseObject(obj.toString(), Map.class);
    		   				companyName = (String) resultMap.get("companyName");
    		   				companyId = (String)resultMap.get("companyId");
    		   				newStocke.put("companyId", companyId);
    		   				newStocke.put("companyName",companyName);
    		   			
    		   			 newStocke.put("saltName", saltNameChild);
    		   		     String url=natural_url+"/add";
    		   		     HttpHeaders headers = new HttpHeaders();
    		   		     MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
    					 headers.setContentType(type);
    					 headers.add("Accept", MediaType.APPLICATION_JSON.toString());
    					 HttpEntity<String> formEntity = new HttpEntity<String>(JSON.toJSONString(newStocke), headers);
    					 message = restTemplate.postForObject(url, formEntity, String.class);
//    		       	     }String message = HttpUtil.post(natural_url+"/add", JSONObject.parseObject(JSONObject.toJSONString(newStocke)));
    		   			}
    		   			 if(StringUtils.isEmpty(message)&&StringUtils.isEmpty(loginId)){
    		   				 result.setResult(ResultMessage.Login);
    		                 result.setMessage("登录超时!");
    		   			 }else if(StringUtils.isEmpty(message)){
    		                 result.setResult(ResultMessage.Fail);
    		                 result.setMessage("操作失败!");
    		             }else{
    		                 result = JSON.parseObject(message,ResultMessage.class);
    		             }
    		             
    				} catch (Exception e) {
    					result.setResult(ResultMessage.Fail);
    		            result.setMessage("操作失败!");
    					e.printStackTrace();
    				}
    		}
    	}else{
    		try {
    			 String message="";
		    	  //后期完善获取当前登录用户所在企业ID,通过企业ID查找
		    	  Map  newStocke=JSON.parseObject(json.toJSONString(),Map.class);
		    	  //后期完善获取当前登录用户所在企业ID,通过企业ID查找
		   		  String  loginId = (String) request.getSession().getAttribute("loginId");
		   		  String companyName="";
		   		  String companyId="";
		   			if(!StringUtils.isEmpty(loginId)){
		   				String resultStr = HttpUtil.get(EnvUtil.getVal("PAASOS_DEPEND_APIGDSALTSUPPLIER01")+"/supplierService/query/byAccount/"+loginId, "");
		   				JSONObject objResult = JSON.parseObject(resultStr);
		   				Object obj = objResult.get("data");
		   				Map resultMap = JSON.parseObject(obj.toString(), Map.class);
		   				companyName = (String) resultMap.get("companyName");
		   				companyId = (String)resultMap.get("companyId");
		   				newStocke.put("companyId", companyId);
		   				newStocke.put("companyName",companyName);
		   			//}
		   		     String url=natural_url+"/add";
		   		     HttpHeaders headers = new HttpHeaders();
		   		     MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
					 headers.setContentType(type);
					 headers.add("Accept", MediaType.APPLICATION_JSON.toString());
					 HttpEntity<String> formEntity = new HttpEntity<String>(JSON.toJSONString(newStocke), headers);
					  message = restTemplate.postForObject(url, formEntity, String.class);
//		       	     String message = HttpUtil.post(natural_url+"/add", JSONObject.parseObject(JSONObject.toJSONString(newStocke)));
		   			}
		   		 if(StringUtils.isEmpty(message)&&StringUtils.isEmpty(loginId)){
	   				 result.setResult(ResultMessage.Login);
	                 result.setMessage("登录超时!");
	   			 }else if(StringUtils.isEmpty(message)){
		                 result.setResult(ResultMessage.Fail);
		                 result.setMessage("操作失败!");
		             }else{
		                 result = JSON.parseObject(message,ResultMessage.class);
		             }
		             
				} catch (Exception e) {
					result.setResult(ResultMessage.Fail);
		            result.setMessage("操作失败!");
					e.printStackTrace();
				}
    	}
    	
    	
    
		
		
		
    	
    	
    	
    	
    	
    	
    	
    	
    	 /* ResultMessage result=new ResultMessage();
    		 //后期完善获取当前登录用户所在企业ID,通过企业ID查找
    	  Map  newStocke=JSON.parseObject(natural,Map.class);
    	  //后期完善获取当前登录用户所在企业ID,通过企业ID查找
		  String  loginId = (String) request.getSession().getAttribute("loginId");
		  String companyName="";
		  String companyId="32c611c1-f918-42fc-bc82-e216dc54ae75";
			if(!StringUtils.isEmpty(loginId)){
				String resultStr = HttpUtil.get(EnvUtil.getVal("PAASOS_DEPEND_APIGDSALTSUPPLIER01")+"/supplierService/query/byAccount/"+loginId, "");
				JSONObject objResult = JSON.parseObject(resultStr);
				Object obj = objResult.get("data");
				Map resultMap = JSON.parseObject(obj.toString(), Map.class);
				companyName = (String) resultMap.get("companyName");
				companyId = (String)resultMap.get("companyId");
				newStocke.put("companyId", companyId);
				newStocke.put("companyName",companyName);
			}
			
			if(!StringUtils.isEmpty(companyId)){
				String product = "";
				Map<String, Object> params = new HashMap<String, Object>();
				//String productId = (String) newStocke.get("remark2");
				params.put("productId", "32c611c1-f918-42fc-bc82-e216dc54ae73");
				params.put("companyId", companyId);
				
				//product = HttpUtil.postFormParams(natural_url, "/queryByProductId", params);
				product =HttpUtil.post(natural_url+"/queryByProductId", params);
				Map  json = (Map) JSON.parseObject(product).get("data");
				Integer number = (Integer) json.get("code");
				if(number>0){
					
				}
				
			}*/
    	
    	
          return result;
    }
    
	@RequestMapping("/delete")
	@ResponseBody
	public JSONObject UpdateById(Model model,String ids){
		JSONObject result = new JSONObject();
		try {
			//Map<String, Object> supplier = new HashMap<String, Object>();
			String vo = HttpUtil.get(natural_url+"/deleteById/"+ids,"");
			JSONObject voJson = JSONObject.parseObject(vo);
			result.put("code", voJson.get("result"));
			result.put("message", voJson.get("message"));
		} catch (Exception e) {
			result.put("code", 0);
			logger.error("删除失败!", e);
		}
		return result;
	}

	
	
	@RequestMapping("/updateSatus")
	@ResponseBody
	public JSONObject updateAgree(Model model,String ids){
		JSONObject result = new JSONObject();
		try {
			Map<String, Object> supplier = new HashMap<String, Object>();
			supplier.put("companyId", ids);
			supplier.put("approvalStatus", APPROVAL_SUCCESS);
			supplier.put("auditor", "admin");
			String vo = HttpUtil.postJsonParams(natural_url, "/modify", JSONObject.parseObject(JSONObject.toJSONString(supplier)));
			JSONObject voJson = JSONObject.parseObject(vo);
			result.put("code", voJson.get("result"));
			result.put("message", voJson.get("message"));
		} catch (Exception e) {
			result.put("code", 0);
			logger.error("认证失败!", e);
		}
		return result;
	}
	/**
	 * 供应商详情页面
	 * @param request
	 * @param model
	 * @return
	 * @author qwang
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/showDetail")
	public String showDetail(HttpServletRequest request, Model model){
		String id = request.getParameter("id");

		JSONObject json = new JSONObject();
		try {
			String resultStr = HttpUtil.get(natural_url +"/query/" + id,"");
			JSONObject objResult = JSON.parseObject(resultStr);
			/*List<JSONObject> arrayResult = JSON.toJavaObject(JSON.parseArray(String.valueOf(json.get("data"))),List.class);
			model.addAllAttributes(resultMap);*/
			Object obj = objResult.get("data");
			Map resultMap = JSON.parseObject(obj.toString(), Map.class);
			model.addAttribute("AssociatorInfo", resultMap);
			if (objResult.get("result").equals("1")) {
				json.put("status", 0);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "/associator/detail";
		
	}
	
	/**
	 * 产品信息详情页面
	 * @param request
	 * @param model
	 * @return
	 * @author qwang
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/detail")
	public String detail(HttpServletRequest request, Model model){
		String id = request.getParameter("id");
        model.addAttribute("id", id);
		JSONObject json = new JSONObject();
		try {
			String resultStr = HttpUtil.get(natural_url +"/query/" + id,"");
			JSONObject objResult = JSON.parseObject(resultStr);
			/*List<JSONObject> arrayResult = JSON.toJavaObject(JSON.parseArray(String.valueOf(json.get("data"))),List.class);
			model.addAllAttributes(resultMap);*/
			Object obj = objResult.get("data");
			Map resultMap = JSON.parseObject(obj.toString(), Map.class);
			String nameNo = resultMap.get("remark").toString();
			if(!StringUtils.isEmpty(nameNo)){
				String url = EnvUtil.getVal("PAASOS_DEPEND_APIGDSALTPRODLIB01");
				Map<String, Object> params = new HashMap<String, Object>();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("nameNumber", nameNo);
				params.put("queryInfo", JSON.toJSONString(map));
				String resultC  = HttpUtil.postFormParams(url, "/categoryService/query", params);
				String caregary = JSONObject.parseObject(resultC).get("message").toString();
				JSONArray caregaryArray = JSONArray.fromObject(caregary);
				if(caregaryArray.size()>0){
					//net.sf.json.JSONObject o = caregaryArray.getJSONObject(caregaryArray.get(0).toString());
					JSONObject careJson = JSONObject.parseObject(caregaryArray.get(0).toString());
					String suppplier = careJson.getString("supplier");
					if(!StringUtils.isEmpty(suppplier)){
						resultMap.put("supplier", suppplier);
					}
					String preId = careJson.getString("preId");				
			
					if(!StringUtils.isEmpty(preId)){
						params = new HashMap<String, Object>();
						map = new HashMap<String, Object>();
						map.put("id", preId);
						params.put("queryInfo", JSON.toJSONString(map));
						String resultCa  = HttpUtil.postFormParams(url, "/categoryService/query", params);
						String cary = JSONObject.parseObject(resultCa).get("message").toString();
						JSONArray careArray = JSONArray.fromObject(cary);
						if(careArray.size()>0 && careArray.size()==1){
							//careArray.sf.json.JSONObject o = caregaryArray.getJSONObject(caregaryArray.get(0).toString());
							JSONObject cJson = JSONObject.parseObject(careArray.get(0).toString());
							String typeName = cJson.getString("name");
							if(!StringUtils.isEmpty(typeName)){
								resultMap.put("typeName", typeName);
							}
						}
					}
				
				}
				
			}
			model.addAttribute("NaturalInfo", resultMap);
			if (objResult.get("message").equals("success")) {
				json.put("status", 0);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "/natural/detailn";
		
	}
	
	
	/**
	 * 供应商会员禁用
	 * @param model
	 * @param id
	 * @return
	 * @author qwang
	 */
	@RequestMapping("/disabled")
	@ResponseBody
	public JSONObject disabled(Model model,String ids){
		JSONObject result = new JSONObject();
		try {
			//Map<String, Object> supplier = new HashMap<String, Object>();
			String vo = HttpUtil.get(natural_url+"/disabled/"+ids,"");
			JSONObject voJson = JSONObject.parseObject(vo);
			result.put("code", voJson.get("result"));
			result.put("message", voJson.get("message"));
		} catch (Exception e) {
			result.put("code", 0);
			logger.error("禁用失败!", e);
		}
		return result;
	}
	
	/**
	 * 供应商会员解禁
	 * @param model
	 * @param id
	 * @return
	 * @author qwang
	 */
	@RequestMapping("/enabled")
	@ResponseBody
	public JSONObject enabled(Model model,String ids){
		JSONObject result = new JSONObject();
		try {
			//Map<String, Object> supplier = new HashMap<String, Object>();
			String vo = HttpUtil.get(natural_url+"/enabled/"+ids,"");
			JSONObject voJson = JSONObject.parseObject(vo);
			result.put("code", voJson.get("result"));
			result.put("message", voJson.get("message"));
		} catch (Exception e) {
			result.put("code", 0);
			logger.error("禁用失败!", e);
		}
		return result;
	}
	
	@RequestMapping("/updateRebut")
	public String updateRebut(Model model, HttpServletRequest request){
		String id = request.getParameter("id");
		model.addAttribute("companyId", id);
		return "/associator/rebutsugges";
	}
	
	@RequestMapping("/updateRebutChange")
	@ResponseBody
	public JSONObject updateRebutChange(Model model, HttpServletRequest request){
		JSONObject result = new JSONObject();
		String companyId = request.getParameter("companyId");
		String auditorSuggestion = request.getParameter("auditorSuggestion");
		Map<String, Object> supplier = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(companyId)) {
			supplier.put("companyId", companyId);
		}
		if (!StringUtils.isEmpty(auditorSuggestion)) {
			supplier.put("auditorSuggestion", auditorSuggestion);
		}
		supplier.put("approvalStatus", APPROVAL_FAIL);
		supplier.put("auditor", "admin");
		String vo;
		try {
			vo = HttpUtil.postJsonParams(natural_url, "/modify", JSONObject.parseObject(JSONObject.toJSONString(supplier)));
			JSONObject voJson = JSONObject.parseObject(vo);
			result.put("code", voJson.get("result"));
			result.put("message", voJson.get("message"));
			
		} catch (IOException e) {
			result.put("code", 0);
			logger.error("禁用失败!", e);
		}
		return result;
	}
	
	@RequestMapping("/showDetailHis")
	public String showDetailHis(HttpServletRequest request, Model model) throws UnsupportedEncodingException{
		String approvalSuggestion = request.getParameter("approvalSuggestion");
		approvalSuggestion =new String(URLDecoder.decode(approvalSuggestion, "UTF-8")); 
		String approvalPerson = request.getParameter("approvalPerson");
		approvalPerson =new String(URLDecoder.decode(approvalPerson, "UTF-8")); 
		String modifyDate = request.getParameter("modifyDate");
	/*	String approvalSuggestion = request.getParameter("approvalSuggestion");
		approvalSuggestion =new String(approvalSuggestion.getBytes("iso8859-1"),"utf-8"); 
		String approvalPerson = request.getParameter("approvalPerson");
		approvalPerson =new String(approvalPerson.getBytes("iso8859-1"),"utf-8");
		String modifyDate = request.getParameter("modifyDate"); */
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("approvalPerson", approvalPerson);
		resultMap.put("approvalSuggestion", approvalSuggestion);
		resultMap.put("modifyDate", modifyDate);
		try {
			model.addAttribute("natrualInfo", resultMap);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "/natural/suggestDetail";
		
	}
	//<---------------------------------------------------------------审核历史--------------------------------------------------------------------------------->
	
	//审核历史查询
	@SuppressWarnings("unchecked")
	@RequestMapping("/queryHistory")
	@ResponseBody
	public Map<String, Object> queryHistory(HttpServletRequest request, Integer pageNo, Integer pageSize){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			//String id = request.getParameter("id");//审核历史
			String companyName = request.getParameter("companyName");//企业名称
			String contactPerson = request.getParameter("legalRepresentative");//联系人
			if (StringUtils.isEmpty(pageNo)) {
				pageNo = 1;
			}
			if (StringUtils.isEmpty(pageSize)) {
				pageSize = 10;
			}
			/*Map<String, Object> map = new HashMap<String, Object>();
			if (!StringUtils.isEmpty(id)) {
				map.put("id", id);
			}
			if (!StringUtils.isEmpty(companyName)) {
				map.put("companyName", URLEncoder.encode(companyName, "utf-8"));
			}
			if (!StringUtils.isEmpty(contactPerson)) {
				map.put("contactPerson", );//URLEncoder.encode(contactPerson, "utf-8")
			}
			String queryInfo = JSON.toJSONString(map);*/
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("companyName", companyName);
			params.put("contactPerson", contactPerson);
			params.put("pageNo", pageNo);
			params.put("pageSize", pageSize);
			String result = "";
			result = HttpUtil.postFormParams(natural_url, "/queryHistory", params);
			JSONObject json = JSON.parseObject(result);
			//Map<String, Object> dataMap = (Map<String, Object>) json;
			List<JSONObject> arrayResult = JSON.toJavaObject(JSON.parseArray(String.valueOf(json.get("data"))),List.class);
			resultMap.put("data", arrayResult);
			// 总记录数
			//Integer count = (Integer) json.get("totalCount");
			resultMap.put("totalRow", json.get("totalRow"));
			// 总页数
			resultMap.put("totalPage",json.get("totalPage"));
			// 当前页数
			resultMap.put("curPage", json.get("curPage"));
			// 每页记录数
			resultMap.put("pageLine", json.get("pageLine"));
			
			
		} catch (Exception e) {
			logger.error("查询出现异常...." + e.getMessage());
		}
		return resultMap;
	}
	@RequestMapping("/assochistory")
	public String assochistory(Model model){
		return "/associator/assochistory";
	}


	//<---------------------------------------------------------------采购商--------------------------------------------------------------------------------->
	

	@RequestMapping("/providerindex")
	public String providerindex(Model model){
		return "/associator/providerindex";
	}
	@SuppressWarnings("unchecked")
	@RequestMapping("/queryProvider")
	@ResponseBody
	public Map<String, Object> queryProvider(HttpServletRequest request, Integer pageNo, Integer pageSize){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			//String id = request.getParameter("id");//审核历史
			String companyName = request.getParameter("companyName");//企业名称
			String contactPerson = request.getParameter("legalRepresentative");//联系人
			if (StringUtils.isEmpty(pageNo)) {
				pageNo = 1;			}
			if (StringUtils.isEmpty(pageSize)) {
				pageSize = 10;
			}
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("companyName", companyName);
			params.put("contactPerson", contactPerson);
			params.put("pageNo", pageNo);
			params.put("pageSize", pageSize);
			String result = "";
			result = HttpUtil.postFormParams(natural_url, "/query", params);
			JSONObject json = JSON.parseObject(result);
			List<JSONObject> arrayResult = JSON.toJavaObject(JSON.parseArray(String.valueOf(json.get("data"))),List.class);
			resultMap.put("data", arrayResult);
			// 总记录数
			resultMap.put("totalRow", json.get("totalRow"));
			// 总页数
			resultMap.put("totalPage",json.get("totalPage"));
			// 当前页数
			resultMap.put("curPage", json.get("curPage"));
			// 每页记录数
			resultMap.put("pageLine", json.get("pageLine"));
			
			
		} catch (Exception e) {
			logger.error("查询出现异常...." + e.getMessage());
		}
		return resultMap;
	}
	/**
	 * 采购商会员禁用
	 * @param model
	 * @param id
	 * @return
	 * @author qwang
	 */
	@RequestMapping("/disabledProvider")
	@ResponseBody
	public JSONObject disabledProvider(Model model,String ids){
		JSONObject result = new JSONObject();
		try {
			//Map<String, Object> supplier = new HashMap<String, Object>();
			String vo = HttpUtil.get(natural_url+"/disabled/"+ids,"");
			JSONObject voJson = JSONObject.parseObject(vo);
			result.put("code", voJson.get("result"));
			result.put("message", voJson.get("message"));
		} catch (Exception e) {
			result.put("code", 0);
			logger.error("禁用失败!", e);
		}
		return result;
	}
	
	/**
	 * 采购商会员解禁
	 * @param model
	 * @param id
	 * @return
	 * @author qwang
	 */
	@RequestMapping("/enabledProvider")
	@ResponseBody
	public JSONObject enabledProvider(Model model,String ids){
		JSONObject result = new JSONObject();
		try {
			//Map<String, Object> supplier = new HashMap<String, Object>();
			String vo = HttpUtil.get(natural_url+"/enabled/"+ids,"");
			JSONObject voJson = JSONObject.parseObject(vo);
			result.put("code", voJson.get("result"));
			result.put("message", voJson.get("message"));
		} catch (Exception e) {
			result.put("code", 0);
			logger.error("禁用失败!", e);
		}
		return result;
	}
	@RequestMapping("/updateRebutPro")
	public String updateRebutPro(Model model, HttpServletRequest request){
		String id = request.getParameter("id");
		model.addAttribute("companyId", id);
		return "/associator/providsugges";
	}
	/**
	 * 采购商会员驳回
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateRebutChangePro")
	@ResponseBody
	public JSONObject updateRebutChangePro(Model model, HttpServletRequest request){
		JSONObject result = new JSONObject();
		String companyId = request.getParameter("companyId");
		String auditorSuggestion = request.getParameter("auditorSuggestion");
		Map<String, Object> supplier = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(companyId)) {
			supplier.put("companyId", companyId);
		}
		if (!StringUtils.isEmpty(auditorSuggestion)) {
			supplier.put("auditorSuggestion", auditorSuggestion);
		}
		supplier.put("approvalStatus", APPROVAL_FAIL);
		supplier.put("auditor", "admin");
		String vo;
		try {
			vo = HttpUtil.postJsonParams(natural_url, "/modify", JSONObject.parseObject(JSONObject.toJSONString(supplier)));
			JSONObject voJson = JSONObject.parseObject(vo);
			result.put("code", voJson.get("result"));
			result.put("message", voJson.get("message"));
			
		} catch (IOException e) {
			result.put("code", 0);
			logger.error("禁用失败!", e);
		}
		return result;
	}
	/**
	 * 采购商会员同意
	 * @param model
	 * @param ids
	 * @return
	 */
	@RequestMapping("/updateAgreePro")
	@ResponseBody
	public JSONObject updateAgreePro(Model model,String ids){
		JSONObject result = new JSONObject();
		try {
			Map<String, Object> supplier = new HashMap<String, Object>();
			supplier.put("companyId", ids);
			supplier.put("approvalStatus", APPROVAL_SUCCESS);
			supplier.put("auditor", "admin");
			String vo = HttpUtil.postJsonParams(natural_url, "/modify", JSONObject.parseObject(JSONObject.toJSONString(supplier)));
			JSONObject voJson = JSONObject.parseObject(vo);
			result.put("code", voJson.get("result"));
			result.put("message", voJson.get("message"));
		} catch (Exception e) {
			result.put("code", 0);
			logger.error("认证失败!", e);
		}
		return result;
	}
	/**
	 * 供应商详情页面
	 * @param request
	 * @param model
	 * @return
	 * @author qwang
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/showDetailPro")
	public String showDetailPro(HttpServletRequest request, Model model){
		String id = request.getParameter("id");

		JSONObject json = new JSONObject();
		try {
			String resultStr = HttpUtil.get(natural_url +"/query/" + id,"");
			JSONObject objResult = JSON.parseObject(resultStr);
			/*List<JSONObject> arrayResult = JSON.toJavaObject(JSON.parseArray(String.valueOf(json.get("data"))),List.class);
			model.addAllAttributes(resultMap);*/
			Object obj = objResult.get("data");
			Map resultMap = JSON.parseObject(obj.toString(), Map.class);
			model.addAttribute("AssociatorInfo", resultMap);
			if (objResult.get("result").equals("1")) {
				json.put("status", 0);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "/associator/detail";
		
	}
		@SuppressWarnings({ "unchecked" })
		@RequestMapping("/queryNaturalRecord")
		@ResponseBody
		public Map<String, Object> queryNaturalRecord(HttpServletRequest request, Integer pageNo, Integer pageSize){
			Map<String, Object> resultMap = new HashMap<String, Object>();
			try {
				String id = request.getParameter("id");//产品id
				String resultOfNatrual = HttpUtil.get(natural_url+"/query/"+id, "");
				JSONObject jsonOfNatural = JSON.parseObject(resultOfNatrual);
				String noticeString = jsonOfNatural.get("data").toString();
				net.sf.json.JSONObject jsonObj=net.sf.json.JSONObject.fromObject(noticeString);
				int status = jsonObj.getInt("approvalStatus");
				if(status != 11 && status != 12){
					resultMap.put("data", 0);
					// 总记录数
					resultMap.put("totalRow", 0);
					// 总页数
					resultMap.put("totalPage",0);
					// 当前页数
					resultMap.put("curPage", 0);
					// 每页记录数
					resultMap.put("pageLine", 10);
				}else{
					if (StringUtils.isEmpty(pageNo)) {
						pageNo = 1;
					}
					if (StringUtils.isEmpty(pageSize)) {
						pageSize = 10;
					}
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("pageNo", pageNo);
					params.put("pageSize", pageSize);
					params.put("naturalId", id);
					String result = "";
					result = HttpUtil.postFormParams(natural_url, "/queryHistory", params);
					JSONObject json = JSON.parseObject(result);
					List<JSONObject> arrayResult = JSON.toJavaObject(JSON.parseArray(String.valueOf(json.get("data"))),List.class);
					resultMap.put("data", arrayResult);
					// 总记录数
					resultMap.put("totalRow", json.get("totalRow"));
					// 总页数
					resultMap.put("totalPage",json.get("totalPage"));
					// 当前页数
					resultMap.put("curPage", json.get("curPage"));
					// 每页记录数
					resultMap.put("pageLine", json.get("pageLine"));
				}

			} catch (Exception e) {
				logger.error("查询出现异常...." + e.getMessage());
			}
			return resultMap;
		}
		
		/**
		 * 产品信息编辑
		 * @param request
		 * @param model
		 * @return
		 * @author qwang
		 */
		@SuppressWarnings({ "rawtypes", "unchecked" })
		@RequestMapping("/editById")
		public String editById(HttpServletRequest request, Model model){
			String id = request.getParameter("id");
	        model.addAttribute("id", id);
			JSONObject json = new JSONObject();
			try {
				String resultStr = HttpUtil.get(natural_url +"/query/" + id,"");
				JSONObject objResult = JSON.parseObject(resultStr);
				/*List<JSONObject> arrayResult = JSON.toJavaObject(JSON.parseArray(String.valueOf(json.get("data"))),List.class);
				model.addAllAttributes(resultMap);*/
				Object obj = objResult.get("data");
				Map resultMap = JSON.parseObject(obj.toString(), Map.class);
				String nameNo = resultMap.get("remark").toString();
				if(!StringUtils.isEmpty(nameNo)){
					String url = EnvUtil.getVal("PAASOS_DEPEND_APIGDSALTPRODLIB01");
					Map<String, Object> params = new HashMap<String, Object>();
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("nameNumber", nameNo);
					params.put("queryInfo", JSON.toJSONString(map));
					String resultC  = HttpUtil.postFormParams(url, "/categoryService/query", params);
					String caregary = JSONObject.parseObject(resultC).get("message").toString();
					JSONArray caregaryArray = JSONArray.fromObject(caregary);
					if(caregaryArray.size()>0){
						//net.sf.json.JSONObject o = caregaryArray.getJSONObject(caregaryArray.get(0).toString());
						JSONObject careJson = JSONObject.parseObject(caregaryArray.get(0).toString());
						String suppplier = careJson.getString("supplier");
						if(!StringUtils.isEmpty(suppplier)){
							resultMap.put("supplier", suppplier);
						}
						String preId = careJson.getString("preId");				
				
						if(!StringUtils.isEmpty(preId)){
							params = new HashMap<String, Object>();
							map = new HashMap<String, Object>();
							map.put("id", preId);
							params.put("queryInfo", JSON.toJSONString(map));
							String resultCa  = HttpUtil.postFormParams(url, "/categoryService/query", params);
							String cary = JSONObject.parseObject(resultCa).get("message").toString();
							JSONArray careArray = JSONArray.fromObject(cary);
							if(careArray.size()>0 && careArray.size()==1){
								//careArray.sf.json.JSONObject o = caregaryArray.getJSONObject(caregaryArray.get(0).toString());
								JSONObject cJson = JSONObject.parseObject(careArray.get(0).toString());
								String typeName = cJson.getString("name");
								if(!StringUtils.isEmpty(typeName)){
									resultMap.put("typeName", typeName);
								}
							}
						}
					
					}
					
				}
				model.addAttribute("NaturalInfo", resultMap);
				
				
				//查询所以食盐种类
				String url1 = EnvUtil.getVal("PAASOS_DEPEND_APIGDSALTPRODLIB01");
				Map<String, Object> params1 = new HashMap<String, Object>();
				Map<String, Object> map1 = new HashMap<String, Object>();
				map1.put("level", 1);
				params1.put("queryInfo", JSON.toJSONString(map1));
				String result = HttpUtil.postFormParams(url1, "/categoryService/query", params1);
				JSONObject json1 = JSON.parseObject(result);
				String noticeString = json1.get("message").toString();
				if (!noticeString.isEmpty()) {
					JSONArray noticeArray = JSONArray.fromObject(noticeString);
					List<Map> noticeMap = new ArrayList<Map>();

					for (int i = 0; i < noticeArray.size(); i++) {
						net.sf.json.JSONObject o = noticeArray.getJSONObject(i);
						noticeMap.add(o);
					}
					model.addAttribute("naturalList", noticeMap);
				}
				if (objResult.get("message").equals("success")) {
					json.put("status", 0);
				}
				
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
			return "/natural/edit";
			
		}
		
		@RequestMapping("/queryInfo")
		@ResponseBody
		public ModelAndView queryInfo(HttpServletRequest request,Model model){
			ModelAndView view = new ModelAndView("/natural/edit");
			try {
				//String url = EnvUtil.getVal("PAASOS_DEPEND_APIAPIGDSALTNATURAL01");
				Map<String, Object> params = new HashMap<String, Object>();
				Map<String, Object> map = new HashMap<String, Object>();
				//map.put("level", 1);
			/*	map.put("startDate", "2017-04-07");
				map.put("endDate", "2017-04-07 23:59:59");*/
				params.put("JsonInfo", JSON.toJSONString(map));
			/*	params.put("pageNo", 1);
				params.put("pageSize", 10);	*/			
				//String result =HttpUtil.postFormParams(url, "/categoryService/query", params);
				String result = HttpUtil.postFormParams(natural_url, "/queryInfo", params);
				JSONObject json = JSON.parseObject(result);
				String noticeString = json.get("message").toString();
				if (!noticeString.isEmpty()) {
					JSONArray noticeArray = JSONArray.fromObject(noticeString);
					List<Map> noticeMap = new ArrayList<Map>();

					for (int i = 0; i < noticeArray.size(); i++) {
						net.sf.json.JSONObject o = noticeArray.getJSONObject(i);
						noticeMap.add(o);
					}
					model.addAttribute("naturalList", noticeMap);
					view.addObject("naturalList", noticeMap);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return view;
		}
}
