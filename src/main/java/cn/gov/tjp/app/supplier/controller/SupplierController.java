package cn.gov.tjp.app.supplier.controller;

import com.alibaba.fastjson.JSON;
import com.pubframework.common.http.HttpUtil;
import com.qhgrain.common.util.EnvUtil;

import cn.gov.tjp.app.common.utils.ResultMessage;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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

/**
 * Created by lovercy on 22/11/2016.
 */
@Controller
@RequestMapping("/supplier")
public class SupplierController {
    @Autowired
    private RestTemplate restTemplate;
    private static Logger logger = LoggerFactory.getLogger(SupplierController.class);

    /**
     * 跳转到供货商注册页面,并将账户Id带入此页面
     * @param model
     * @param loginId
     * @return
     */
    @RequestMapping("/register")
    public String register(Model model, String loginId){
        model.addAttribute("loginId",loginId);
        return "/member/supplierRegister";
    }
    @RequestMapping("/registerDetail")
    public String registerDetail(Model model, String loginId){
        model.addAttribute("loginId",loginId);
        return "/member/detail";
    }

    /**
     * 提交供货商注册信息(到数据库表salt_suppliers中)
     * @param supplier
     * @return
     */
    @RequestMapping("/submit")
    @ResponseBody
    public ResultMessage submit(String supplier){
        ResultMessage result=new ResultMessage();
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<String> formEntity = new HttpEntity<String>(supplier,headers);
        String url= EnvUtil.getVal("PAASOS_DEPEND_APIGDSALTSUPPLIER01");
        url+="/supplierService/add";
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
     * 保存供货商注册信息(到数据库表salt_suppliers_temp中)
     * @param supplierTemp
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public ResultMessage save(String supplierTemp){
        ResultMessage result=new ResultMessage();
        try {
//        HttpHeaders headers = new HttpHeaders();
//        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
//        headers.setContentType(type);
//        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
//        HttpEntity<String> formEntity = new HttpEntity<String>(supplierTemp,headers);
        String url= EnvUtil.getVal("PAASOS_DEPEND_APIGDSALTSUPPLIER01");
        url+="/supplierService/add/temp";
        String message = HttpUtil.post(url, JSON.parseObject(supplierTemp));
//        String message = restTemplate.postForObject(url, formEntity, String.class);
        if(StringUtils.isEmpty(message)){
            result.setResult(ResultMessage.Fail);
            result.setMessage("操作失败,后台未返回结果!");
        }else{
            result = JSON.parseObject(message,ResultMessage.class);
        }
        } catch (Exception e) {
			e.printStackTrace();
		}
        return result;
    }
    
	/**
	 * 查询审核记录
	 * @param request
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/queryHistoryrecord")
	@ResponseBody
	public Map<String, Object> queryHistoryrecord(HttpServletRequest request ,Model model,Integer pageNo, Integer pageSize){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String id = request.getParameter("supplierId");//供应商ID
			Map<String, Object> params = new HashMap<String, Object>();
				if(!StringUtils.isEmpty(id)){
					params.put("supplierId", id);
					
					params.put("pageNo", pageNo);
					params.put("pageSize", pageSize);
					if (StringUtils.isEmpty(pageNo)) {
						pageNo = 1;
					}
					if (StringUtils.isEmpty(pageSize)) {
						pageSize = 10;
					}
				
				
					String result = "";
					result = HttpUtil.postFormParams(EnvUtil.getVal("PAASOS_DEPEND_APIGDSALTSUPPLIER01"), "/supplierService/queryBySupplierId", params);
					com.alibaba.fastjson.JSONObject json = JSON.parseObject(result);
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
					resultMap.put("data", 0);
					// 总记录数
					resultMap.put("totalRow", 0);
					// 总页数
					resultMap.put("totalPage",0);
					// 当前页数
					resultMap.put("curPage", 0);
					// 每页记录数
					resultMap.put("pageLine", 10);
			}
			
		} catch (Exception e) {
			logger.error("查询出现异常...." + e.getMessage());
		}
		return resultMap;
	}
	
	 /**
     * 判断企业名称或工商营业执照编号否已被注册
     * @param phone
     * @return 	true	表示没有注册	可用
     * 			false	表示已注册	不可用
	 * @throws IOException 
     */
    @RequestMapping("/checkIsExit")
    @ResponseBody
    public boolean checkIsExit(String companyName,String charterCode,String companyId) throws IOException{
    	
    			String url = EnvUtil.getVal("PAASOS_DEPEND_APIGDSALTSUPPLIER01") + "/supplierService";
    			Map<String, Object> paramMap = new HashMap<String, Object>();
    			if(StringUtils.isEmpty(companyName) && StringUtils.isEmpty(charterCode)){
    				return false;
    			}else{
    				if(!StringUtils.isEmpty(companyName)){
    					paramMap.put("companyName", companyName);
    				}
    				if(!StringUtils.isEmpty(charterCode)){
    					paramMap.put("charterCode", charterCode);
    				}
    				if(!StringUtils.isEmpty(companyId)){
    					paramMap.put("companyId", companyId);
    				}
    				
    				String message = HttpUtil.postFormParams(url, "/checkIsExist", paramMap);
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
}
