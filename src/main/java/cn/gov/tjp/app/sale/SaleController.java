package cn.gov.tjp.app.sale;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pubframework.common.http.HttpUtil;
import com.qhgrain.common.util.EnvUtil;

import cn.gov.tjp.app.common.utils.ResultMessage;


@Controller
@RequestMapping("/sale")
public class SaleController {

	private String SALE_URL = EnvUtil.getVal("PAASOS_DEPEND_APIGDSALTSALE01")+"/saleservice";
	private static Logger logger = LoggerFactory.getLogger(SaleController.class);
	
	
	
	@RequestMapping("/index")
	public String stockedindex(Model model){
		return "/sale/index";
	}
	
	
	@RequestMapping("/add")
	public String stockedAdd(Model model) throws IOException{
		return "/sale/edit";
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/query")
	@ResponseBody
	public Map<String, Object> query(HttpServletRequest request, Integer pageNo, Integer pageSize){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
			
			 //后期完善获取当前登录用户所在企业ID,通过企业ID查找
			  String  loginId = (String) request.getSession().getAttribute("loginId");
			  String companyName="";
			  String companyId="";
				if(!StringUtils.isEmpty(loginId)){
					String resultStr = HttpUtil.get(EnvUtil.getVal("PAASOS_DEPEND_APIGDSALTSUPPLIER01")+"/supplierService/query/byAccount/"+loginId, "");
					JSONObject objResult = JSON.parseObject(resultStr);
					Object obj = objResult.get("data");
					Map result1Map = JSON.parseObject(obj.toString(), Map.class);
					companyName = (String) result1Map.get("companyName");
					companyId = (String)result1Map.get("companyId");
				}
			String userName = request.getParameter("userName");//企业名称
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("pageNo", pageNo);
			params.put("pageSize", pageSize);
			if (StringUtils.isEmpty(pageNo)) {
				pageNo = 1;
			}
			if (StringUtils.isEmpty(pageSize)) {
				pageSize = 10;
			}
			if (!StringUtils.isEmpty(userName)) {
				params.put("userName", userName);
			}
			if (!StringUtils.isEmpty(companyId)) {
				params.put("companyId", companyId);
			}
			if (!StringUtils.isEmpty(companyName)) {
				params.put("companyName", companyName);
			}
			
			
			
	
			String result = "";
			result = HttpUtil.postFormParams(SALE_URL, "/query", params);
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
     * 保存供货商
     * @param supplierTemp
     * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
     */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/save")
    @ResponseBody
    public ResultMessage save(HttpServletRequest request,String sale) throws ClientProtocolException, IOException{
    	  ResultMessage result=new ResultMessage();
       	  Map  newStocke=JSON.parseObject(sale,Map.class);
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
			}
			  newStocke.put("companyId", companyId);
	    	  newStocke.put("companyName",companyName);
    	  String message = HttpUtil.post(SALE_URL+"/add", JSONObject.parseObject(JSONObject.toJSONString(newStocke)));
          if(StringUtils.isEmpty(message)){
              result.setResult(ResultMessage.Fail);
              result.setMessage("操作失败,后台未返回结果!");
          }else{
              result = JSON.parseObject(message,ResultMessage.class);
          }
          return result;
    }
    
	@RequestMapping("/delete")
	@ResponseBody
	public JSONObject deleteById(HttpServletRequest request,Model model,String ids){
		JSONObject result = new JSONObject();
		try {
			 //后期完善
    		JSONObject userVo= (JSONObject) request.getSession().getAttribute("saltUserInfo");
			if(userVo!=null){
				String userName = userVo.getString("loginName");
				ids=ids+","+userName;
			}
			String vo = HttpUtil.get(SALE_URL+"/delete/"+ids,"");
			JSONObject voJson = JSONObject.parseObject(vo);
			result.put("code", voJson.get("result"));
			result.put("message", voJson.get("message"));
		} catch (Exception e) {
			result.put("code", 0);
			logger.error("删除失败!", e);
		}
		return result;
	}

	/**
	 * 修改
	 * @param request
	 * @param model
	 * @return
	 * @author qwang
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/eidtById")
	public String eidtById(HttpServletRequest request, Model model){
		String id = request.getParameter("id");

		JSONObject json = new JSONObject();
		try {
			//查询主键对信息
			String resultStr = HttpUtil.get(SALE_URL +"/query/" + id,"");
			JSONObject objResult = JSON.parseObject(resultStr);
			Object obj = objResult.get("data");
			Map resultMap = JSON.parseObject(obj.toString(), Map.class);
			model.addAttribute("saleInfo", resultMap);
			if (objResult.get("result").equals("1")) {
				json.put("status", 0);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "/sale/edit";
		
	}
	
	

	/**
	 * 供应商详情页面
	 * @param request
	 * @param model
	 * @return
	 * @author qwang
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/show")
	public String showDetail(HttpServletRequest request, Model model){
		String id = request.getParameter("id");

		JSONObject json = new JSONObject();
		try {
			String resultStr = HttpUtil.get(SALE_URL +"/query/" + id,"");
			JSONObject objResult = JSON.parseObject(resultStr);
			Object obj = objResult.get("data");
			Map resultMap = JSON.parseObject(obj.toString(), Map.class);
			model.addAttribute("saleInfo", resultMap);
			if (objResult.get("result").equals("1")) {
				json.put("status", 0);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "/sale/detail";
		
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
		supplier.put("auditor", "admin");
		String vo;
		try {
			vo = HttpUtil.postJsonParams(SALE_URL, "/modify", JSONObject.parseObject(JSONObject.toJSONString(supplier)));
			JSONObject voJson = JSONObject.parseObject(vo);
			result.put("code", voJson.get("result"));
			result.put("message", voJson.get("message"));
			
		} catch (IOException e) {
			result.put("code", 0);
			logger.error("禁用失败!", e);
		}
		return result;
	}
	
	
	
	


}
