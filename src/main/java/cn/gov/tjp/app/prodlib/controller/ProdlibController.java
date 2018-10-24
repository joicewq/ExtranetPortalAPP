package cn.gov.tjp.app.prodlib.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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

@Controller
@RequestMapping("/prodlib")
public class ProdlibController {
	private String prodlib_url = EnvUtil.getVal("PRODLIB_URL")+"/prodlibService";
	
	private static Logger LOG = LoggerFactory.getLogger(ProdlibController.class);
	
	@RequestMapping("/moreIndex")
	public String moreIndex(Model model){
		return "/prodlib/moreIndex";
	}
	
	@RequestMapping("/index")
	public String index(Model model){
		return "/prodlib/index";
	}
	
	@RequestMapping("/edit")
	public String edit(String companyId, Model model){
		model.addAttribute("companyId", companyId);
		return "/prodlib/edit";
	}
	
	@RequestMapping("/show")
	public String show(String companyId, Model model){
		model.addAttribute("companyId", companyId);
		return "/prodlib/show";
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public String save(HttpServletRequest request){
		String res = "success";
		try {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String id = request.getParameter("id");
			String companyId = request.getParameter("companyId");
			String name = request.getParameter("name");
			String spec = request.getParameter("spec");
			String grade = request.getParameter("grade");
			String stdNum = request.getParameter("stdNum");
			String licenseNum = request.getParameter("licenseNum");
			String producePlace = request.getParameter("producePlace");
			String valtDateFrom = request.getParameter("valtDateFrom");
			String valtDateTo = request.getParameter("valtDateTo");
			String mixIngt = request.getParameter("mixIngt");
			String keep = request.getParameter("keep");
			String nutrComp = request.getParameter("nutrComp");
			String antiFake = request.getParameter("antiFake");
			String introduce = request.getParameter("introduce");
			String number = request.getParameter("number");
			String price = request.getParameter("price");
			String deliveryPlace = request.getParameter("deliveryPlace");
			String appearIds = request.getParameter("appearIds");
			String checkIds = request.getParameter("checkIds");
//			String sta = request.getParameter("sta");
			
			Map<String, Object> map = new HashMap<String,Object>();
			if (!StringUtils.isEmpty(id)) {
				map.put("id", id);
			}
			if (!StringUtils.isEmpty(companyId)) {
				map.put("companyId", companyId);
			}
			if (!StringUtils.isEmpty(name)) {
				map.put("name", name);
			}
			if (!StringUtils.isEmpty(spec)) {
				map.put("spec", spec);
			}
			if (!StringUtils.isEmpty(grade)) {
				map.put("grade", grade);
			}
			if (!StringUtils.isEmpty(stdNum)) {
				map.put("stdNum", stdNum);
			}
			if (!StringUtils.isEmpty(licenseNum)) {
				map.put("licenseNum", licenseNum);
			}
			if (!StringUtils.isEmpty(producePlace)) {
				map.put("producePlace", producePlace);
			}
			if (!StringUtils.isEmpty(valtDateFrom)) {
//				map.put("valtDateFrom", valtDateFrom);
				map.put("valtDateFrom", format.format(new Date()));
			}
			if (!StringUtils.isEmpty(valtDateTo)) {
//				map.put("valtDateTo", valtDateTo);
				map.put("valtDateTo", format.format(new Date()));
			}
			if (!StringUtils.isEmpty(mixIngt)) {
				map.put("mixIngt", mixIngt);
			}
			if (!StringUtils.isEmpty(keep)) {
				map.put("keep", keep);
			}
			if (!StringUtils.isEmpty(nutrComp)) {
				map.put("nutrComp", nutrComp);
			}
			if (!StringUtils.isEmpty(antiFake)) {
				map.put("antiFake", antiFake);
			}
			if (!StringUtils.isEmpty(introduce)) {
				map.put("introduce", introduce);
			}
			if (!StringUtils.isEmpty(number)) {
				map.put("number", number);
			}
			if (!StringUtils.isEmpty(price)) {
				map.put("price", price);
			}
			if (!StringUtils.isEmpty(deliveryPlace)) {
				map.put("deliveryPlace", deliveryPlace);
			}
			if (!StringUtils.isEmpty(appearIds)) {
				map.put("appearIds", appearIds);
			}
			if (!StringUtils.isEmpty(checkIds)) {
				map.put("checkIds", checkIds);
			}
			map.put("sta", 1);
			String prodlibJson = JSON.toJSONString(map);
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("prodlibJson", prodlibJson);
			String result = "";
			if (StringUtils.isEmpty(id)) {	//如果ID为空则是新增
				result = HttpUtil.postFormParams(prodlib_url, "/add", paramMap);
			}else{	//否则是更新
				result = HttpUtil.postFormParams(prodlib_url, "/update", paramMap);
			}
			String status = JSON.parseObject(result).getString("status");
			if ("0".equals(status)) {
				res = "error";
			}
		} catch (Exception e) {
			LOG.error("保存出现异常...." + e.getMessage());
			res="error";
		}
		return res;
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public String update(HttpServletRequest request){
		String res = "success";
		try {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String id = request.getParameter("id");
			String sta = request.getParameter("sta");
			
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("id", id);
			map.put("sta", sta);
			if ("4".equals(sta)) {	//如果传的状态值为4：挂牌，则设置相关挂牌属性
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.MONTH, 1);
				map.put("refreshDate", format.format(new Date()));	//挂牌时间
				map.put("listedDate", format.format(calendar.getTime()));	//挂牌有效期
			}
			String prodlibJson = JSON.toJSONString(map);
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("prodlibJson", prodlibJson);
			String result = "";
			result = HttpUtil.postFormParams(prodlib_url, "/update", paramMap);
			String status = JSON.parseObject(result).getString("status");
			if ("0".equals(status)) {
				res = "error";
			}
		} catch (Exception e) {
			LOG.error("保存出现异常...." + e.getMessage());
			res="error";
		}
		return res;
	}
	
	@RequestMapping("/query")
	@ResponseBody
	public Map<String, Object> query(HttpServletRequest request, Integer pageNo, Integer pageSize){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String id = request.getParameter("id");
			String companyId = request.getParameter("companyId");
			String name = request.getParameter("name");
			String spec = request.getParameter("spec");
			String start = null;
			if (StringUtils.isEmpty(pageNo)) {
				pageNo = 1;
			}
			if (StringUtils.isEmpty(pageSize)) {
				pageSize = 10;
			}
			start = String.valueOf((pageNo - 1) * pageSize);
			Map<String, Object> map = new HashMap<String, Object>();
			if (!StringUtils.isEmpty(id)) {
				map.put("id", id);
			}
			if (!StringUtils.isEmpty(companyId)) {
				map.put("companyId", companyId);
			}
			if (!StringUtils.isEmpty(name)) {
				map.put("name", name);
			}
			if (!StringUtils.isEmpty(spec)) {
				map.put("spec", spec);
			}
			String queryInfo = JSON.toJSONString(map);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("queryInfo", queryInfo);
			params.put("start", start);
			params.put("size", pageSize);
			String result = "";
			result = HttpUtil.postFormParams(prodlib_url, "/query", params);
			JSONObject json = JSON.parseObject(result);
			Map<String, Object> dataMap = (Map<String, Object>) json;
			// 总记录数
			Integer count = (Integer) dataMap.get("total");
			resultMap.put("totalRow", count);
			// 总页数
			resultMap.put("totalPage", (count % pageSize == 0) ? count / pageSize : count / pageSize + 1);
			// 当前页数
			resultMap.put("curPage", pageNo);
			// 每页记录数
			resultMap.put("pageLine", pageSize);
			resultMap.put("data", dataMap.get("message"));
			
		} catch (Exception e) {
			LOG.error("查询出现异常...." + e.getMessage());
		}
		return resultMap;
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public String delete(HttpServletRequest request){
		String res = "success";
		try {
			String id = request.getParameter("id");
			
			Map<String, Object> map = new HashMap<String, Object>();
			if (!StringUtils.isEmpty(id)) {
				map.put("id", id);
			}
			String result = HttpUtil.postFormParams(prodlib_url, "/delete", map);
			String status = JSON.parseObject(result).getString("status");
			if ("0".equals(status)) {
				res = "error";
			}
		} catch (Exception e) {
			LOG.error("查询出现异常...." + e.getMessage());
			res = "error";
		}
		return res;
	}
	
	
}
