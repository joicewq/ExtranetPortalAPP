package cn.gov.tjp.app.stocked;

import java.io.IOException;
import java.util.ArrayList;
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
import cn.gov.tjp.app.natural.NaturalController;
import cn.gov.tjp.app.natural.model.UserVo;
import net.sf.json.JSONArray;

@Controller
@RequestMapping("/stocked")
public class StockedController {

	private String STOCKED_URL = EnvUtil.getVal("PAASOS_DEPEND_APIAPIGDSALTSTOCKED01")+"/stockedService";
	private static Logger logger = LoggerFactory.getLogger(NaturalController.class);
	//会员认证状态-认证成功
	public static final Integer APPROVAL_SUCCESS = 11;
	//会员认证状态-认证失败
	public static final Integer APPROVAL_FAIL = 12;
	
	
	
	
	@RequestMapping("/index")
	public String stockedindex(Model model,HttpServletRequest request){
		 String  loginId = (String) request.getSession().getAttribute("loginId");
		  if(StringUtils.isEmpty(loginId)){
			  return "/home/login/index";
		  }
		return "/stocked/index";
	}
	
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("/add")
	public String stockedAdd(Model model) throws IOException{
		String url = EnvUtil.getVal("PAASOS_DEPEND_APIGDSALTSUPPLIER01")+"/supplierService";
		Integer pageNo=null;
		Integer pageSize=null;
		if (StringUtils.isEmpty(pageNo)) {
			pageNo = 1;
		}
		if (StringUtils.isEmpty(pageSize)) {
			pageSize = 10;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pageNo", pageNo);
		params.put("pageSize", pageSize);
		String result = "";
		result = HttpUtil.postFormParams(url, "/query", params);
		JSONObject json = JSON.parseObject(result);
		String noticeString = json.get("data").toString();
		if (!noticeString.isEmpty()) {
			JSONArray noticeArray = JSONArray.fromObject(noticeString);
			List<Map> noticeMap = new ArrayList<Map>();

			for (int i = 0; i < noticeArray.size(); i++) {
				net.sf.json.JSONObject o = noticeArray.getJSONObject(i);
				noticeMap.add(o);
			}
			model.addAttribute("supplierList", noticeMap);
		}
		return "/stocked/edit";
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/query")
	@ResponseBody
	public Map<String, Object> query(HttpServletRequest request, Integer pageNo, Integer pageSize){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			  //后期完善获取当前登录用户所在企业ID,通过企业ID查找
			  String  loginId = (String) request.getSession().getAttribute("loginId");
			  String companyId="";
				if(!StringUtils.isEmpty(loginId)){
					String resultStr = HttpUtil.get(EnvUtil.getVal("PAASOS_DEPEND_APIGDSALTSUPPLIER01")+"/supplierService/query/byAccount/"+loginId, "");
					JSONObject objResult = JSON.parseObject(resultStr);
					Object obj = objResult.get("data");
					Map result1Map = JSON.parseObject(obj.toString(), Map.class);
					companyId = (String)result1Map.get("companyId");
				}
			
			
			String title = request.getParameter("title");//企业名称
			String stockedName = request.getParameter("stockedName");//企业名称
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("pageNo", pageNo);
			params.put("pageSize", pageSize);
			if (StringUtils.isEmpty(pageNo)) {
				pageNo = 1;
			}
			if (StringUtils.isEmpty(pageSize)) {
				pageSize = 10;
			}
			if (!StringUtils.isEmpty(stockedName)) {
				params.put("storeName", stockedName);
			}
			if (!StringUtils.isEmpty(title)) {
				params.put("title", title);
			}
			if (!StringUtils.isEmpty(companyId)) {
				params.put("companyId", companyId);
			}
			
			
			
	
			String result = "";
			result = HttpUtil.postFormParams(STOCKED_URL, "/query", params);
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
    public ResultMessage save(HttpServletRequest request,String stocked) throws ClientProtocolException, IOException{
    	  ResultMessage result=new ResultMessage();
    	  //后期完善获取当前登录用户所在企业ID,通过企业ID查找
    	  	String companyId = request.getParameter("companyId");
    		Map<String, Object> JsonStirng = new HashMap<String, Object>();
    		if(!StringUtils.isEmpty(companyId)){
    			JsonStirng.put("id", companyId);
    		}
    	  Map  newStocke=JSON.parseObject(stocked,Map.class);
    	  String typeCode = (String) newStocke.get("typeCode");
    	  String postalCode = (String) newStocke.get("postalCode");
    	  String stocekCode = (String) newStocke.get("stocekCode");
    	  
    	  if(StringUtils.isEmpty(stocekCode)){
    	  //查询记录数必须查询当前企业记录数
    	  String resulNumber = HttpUtil.postJsonParams(STOCKED_URL,"/queryAll", JSONObject.parseObject(JSONObject.toJSONString(JsonStirng)));
    	  
    	  JSONObject  json = JSONObject.parseObject(resulNumber);
    	  Integer number = (Integer) json.get("result");
    	  if(number==0){
    		  number=1;
    		}else{
    			number++;
    		}

    	  newStocke.put("stocekOrder", number);
    	  newStocke.put("stocekCode",(typeCode+postalCode+number));
    	  }
    	  
    	  
    	
    	  
    	  String message = HttpUtil.post(STOCKED_URL+"/add", JSONObject.parseObject(JSONObject.toJSONString(newStocke)));
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
    		UserVo userVO = (UserVo) request.getSession().getAttribute("userVO");
			if(userVO!=null){
				String userName = userVO.getUserName();
				ids=ids+","+userName;
			}
			String vo = HttpUtil.get(STOCKED_URL+"/delete/"+ids,"");
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
			String resultStr = HttpUtil.get(STOCKED_URL +"/query/" + id,"");
			JSONObject objResult = JSON.parseObject(resultStr);
			Object obj = objResult.get("data");
			Map resultMap = JSON.parseObject(obj.toString(), Map.class);
			model.addAttribute("stockedInfo", resultMap);
			
			
			
			//查询供应商企业信息
			Map<String, Object> params = new HashMap<String, Object>();
			String url = EnvUtil.getVal("PAASOS_DEPEND_APIGDSALTSUPPLIER01")+"/supplierService";
			String result = HttpUtil.postFormParams(url, "/query", params);
			JSONObject supperCompanyName = JSON.parseObject(result);
			String noticeString = supperCompanyName.get("data").toString();
			if (!noticeString.isEmpty()) {
				JSONArray noticeArray = JSONArray.fromObject(noticeString);
				List<Map> noticeMap = new ArrayList<Map>();

				for (int i = 0; i < noticeArray.size(); i++) {
					net.sf.json.JSONObject o = noticeArray.getJSONObject(i);
					noticeMap.add(o);
				}
				model.addAttribute("supplierList", noticeMap);
			}
			
			
			
			
			if (objResult.get("result").equals("1")) {
				json.put("status", 0);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "/stocked/edit";
		
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
			String resultStr = HttpUtil.get(STOCKED_URL +"/query/" + id,"");
			JSONObject objResult = JSON.parseObject(resultStr);
			Object obj = objResult.get("data");
			Map resultMap = JSON.parseObject(obj.toString(), Map.class);
			model.addAttribute("stockedInfo", resultMap);
			if (objResult.get("result").equals("1")) {
				json.put("status", 0);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "/stocked/detail";
		
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
			vo = HttpUtil.postJsonParams(STOCKED_URL, "/modify", JSONObject.parseObject(JSONObject.toJSONString(supplier)));
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
