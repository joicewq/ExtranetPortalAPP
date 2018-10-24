/**
 * 2016年11月28日 下午2:54:25
 * @auto Jack.Hou
 * @Copyright 1999-2020 http://www.yihecloud.com/ Croporation Limited.
 */
package cn.gov.tjp.app.vipcenter.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.pubframework.common.http.HttpUtil;
import com.pubframework.common.util.StringUtilExt;
import com.qhgrain.common.util.EnvUtil;

import cn.gov.tjp.app.utils.StringUtil;
import cn.gov.tjp.app.vipcenter.info.Demand;

/**
 * @author Administrator
 *
 */
@Service
public class DemandService {

	private static Logger LOG = LoggerFactory.getLogger(DemandService.class);

	/**
	 * 根据条件获取需求信息
	 * 
	 * @param map
	 * @return
	 */
	public String getDemandList(Map<String, Object> map) {

		String data = null;
		try {
			String vo = HttpUtil.post(EnvUtil.getVal("PAASOS_DEPEND_APIGDSALTDEMAND_URL") + "/query", map);
			JSONObject voJson = JSONObject.parseObject(vo);
			data = voJson.getString("data");
		} catch (IOException e) {
			LOG.error("根据条件获取需求数据失败..." + e.getMessage());
		}
		return data;

	}

	/**
	 * 根据id查询需求信息
	 * 
	 * @param dId
	 * @return
	 * @throws IOException
	 */
	public Demand find(String dId) throws IOException {
		String vo = HttpUtil.get(EnvUtil.getVal("PAASOS_DEPEND_APIGDSALTDEMAND_URL") + "/getId/" + dId, "");
		JSONObject voJson = JSONObject.parseObject(vo);
		if ("1".equals(voJson.getString("state"))) {
			return JSONObject.parseObject(voJson.getString("data"), Demand.class);
		} else {
			throw new RuntimeException("从api获取对象信息失败...vo=" + vo);
		}
	}

	/**
	 * 保存信息
	 * 
	 * @param demand
	 * @return
	 */
	public JSONObject saveDemand(Demand demand) {
		JSONObject result = new JSONObject();
		String vo = null;
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
			map.put("productPurity", demand.getProductPlace()); // 产地,
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
		try {
			if (StringUtilExt.isBlank(demand.getId())) {
				vo = HttpUtil.postFormParams(EnvUtil.getVal("PAASOS_DEPEND_APIGDSALTDEMAND_URL"), "/add", map);
			} else {
				vo = HttpUtil.postFormParams(EnvUtil.getVal("PAASOS_DEPEND_APIGDSALTDEMAND_URL"), "/edit", map);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		JSONObject voJson = JSONObject.parseObject(vo);
		result.put("code", voJson.getString("state"));
		return result;
	}

}
