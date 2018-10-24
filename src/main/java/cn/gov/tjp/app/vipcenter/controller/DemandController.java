/**
 * 2016年11月28日 下午2:50:07
 * @auto Jack.Hou
 * @Copyright 1999-2020 http://www.yihecloud.com/ Croporation Limited.
 */
package cn.gov.tjp.app.vipcenter.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pubframework.common.http.HttpUtil;
import com.pubframework.common.util.StringUtilExt;
import com.qhgrain.common.util.EnvUtil;

import cn.gov.tjp.app.utils.ControllerUtil;
import cn.gov.tjp.app.utils.StringUtil;
import cn.gov.tjp.app.vipcenter.info.Demand;
import cn.gov.tjp.app.vipcenter.service.DemandService;

/**
 * @author Administrator
 * @category 需求管理
 */
@Controller
@RequestMapping(value = "/demand")
public class DemandController {

	private static Logger logger = LoggerFactory.getLogger(DemandController.class);
	
	@Autowired
	private DemandService demandService;

	@RequestMapping(value = "/index")
	public String index() {

		return "/vipcenter/demand/demand_index";
	}

	/**
	 * 查询需求列表
	 * 
	 * @param request
	 * @param model
	 * @param pageNo
	 * @param pageSize
	 * @param demand
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/querydemandlist")
	public Map<String, Object> queryDemandList(HttpServletRequest request, Model model, String pageNo, String pageSize, Demand demand)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int pageNum = StringUtilExt.isNotBlank(pageNo) ? Integer.parseInt(pageNo) : 1;
		int size = StringUtilExt.isNotBlank(pageSize) ? Integer.parseInt(pageSize) : 10;
		ControllerUtil.getSetParameters(request);
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtil.isNotEmpty(demand.getProductName())) {
			map.put("productName", demand.getProductName());
		}
		if (StringUtil.isNotEmpty(demand.getProductSpec())) {
			map.put("productSpec", demand.getProductSpec()); // '产品规格',
		}
		if (StringUtil.isNotEmpty(demand.getProductNum())) {
			map.put("productNum", demand.getProductNum()); // '产品数量',
		}
		if (StringUtil.isNotEmpty(demand.getProductPrice())) {
			map.put("productPrice", demand.getProductPrice()); // '产品单价',
		}
		if (StringUtil.isNotEmpty(demand.getProductPlace())) {
			map.put("productPurity", demand.getProductPlace()); // '产地',
		}
		if (StringUtil.isNotEmpty(demand.getLogisticsMode())) {
			map.put("logisticsMode", demand.getLogisticsMode()); // '物流方式（0-京东1-顺丰。。。）',
		}
		if (StringUtil.isNotEmpty(demand.getTradeAdress())) {
			map.put("tradeAdress", demand.getTradeAdress()); // '交割地/交易地址'
		}
		if (StringUtil.isNotEmpty(demand.getContactPhone())) {
			map.put("contactPhone", demand.getContactPhone()); // '联系方式'
		}
		if (StringUtil.isNotEmpty(demand.getDeadtime())) {
			map.put("deadtime", demand.getDeadtime()); // '截止时间'
		}

		map.put("pageNo", size);
		map.put("pageNum", pageNum);
		String result = demandService.getDemandList(map);
		JSONObject json = JSON.parseObject(result);
		Map<String, Object> dataMap = (Map<String, Object>) json.get("data");
		List<Map<String, Object>> list = (List<Map<String, Object>>) dataMap.get("list");
		resultMap.put("data", list);
		Integer count = (Integer) dataMap.get("totalCount");
		resultMap.put("totalRow", count); // 总记录数
		resultMap.put("totalPage", (count % Integer.valueOf(pageSize) == 0) ? count / Integer.valueOf(pageSize) : count / Integer.valueOf(pageSize)
				+ 1); // 总页数
		resultMap.put("curPage", pageNo);// 当前页数
		resultMap.put("pageLine", pageSize); // 每页记录数
		return resultMap;
	}

	/**
	 * 跳转到新增/编辑角色页面
	 * 
	 * @param model
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value = "/edit")
	public String edit(Model model, String dId) {
		try {
			if (StringUtils.isNotBlank(dId)) {
				Demand demand = this.demandService.find(dId);
				model.addAttribute("demand", demand);
			}else{
				logger.error("跳转需求修改页面ID为："+dId);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "/vipcenter/demand/demand_edit";
	}

	/**
	 * 删除角色，支持批量删除
	 * 
	 * @param model
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public JSONObject delete(Model model, String ids) {
		JSONObject result = new JSONObject();
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("ids", ids);
			String vo = HttpUtil.postFormParams(EnvUtil.getVal("PAASOS_DEPEND_APIGDSALTDEMAND_URL"), "/delete", paramMap);
			JSONObject voJson = JSONObject.parseObject(vo);
			result.put("code", voJson.getString("state"));
		} catch (Exception e) {
			result.put("code", 0);
			logger.error("删除需求信息失败!", e);
		}
		return result;
	}
 
	/**
	 * 保存/更新需求信息
	 * 
	 * @param model
	 * @param roleId
	 * @param roleName
	 * @param roleType
	 * @param roleCode
	 * @return
	 */
	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject saveOrUpdate(Model model, Demand demand) {
		JSONObject result = new JSONObject();
		try {
			result = demandService.saveDemand(demand); 
		} catch (Exception e) {
			result.put("code", 0);
			logger.error("保存需求信息出错。。。", e);
		}
		return result;
	}

}
