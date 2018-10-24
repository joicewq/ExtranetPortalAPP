/**
 * 2016年11月28日 上午10:16:54
 * @auto Jack.Hou
 * @Copyright 1999-2020 http://www.yihecloud.com/ Croporation Limited.
 */
package cn.gov.tjp.app.vipcenter.controller;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pubframework.common.http.HttpUtil;
import com.qhgrain.common.util.EnvUtil;

import cn.gov.tjp.app.account.service.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Administrator 会员中心
 */
@Controller
@RequestMapping(value = "/vipcenter")
public class VipCenterController {

	@Autowired
	private AccountService accountService;

	/**
	 * 会员中心页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request, Model model) {
		String loginId = (String) request.getSession().getAttribute("loginId");
		if (StringUtils.isEmpty(loginId)) {
			return "/home/login/index";
		} else {
			model.addAttribute("loginId", loginId);
			/* model.addAttribute("loginType",loginType); */
			return "/vipcenter/personalIndex";
		}
	}

	/**
	 * 系统通知
	 * 
	 * @return
	 */
	@RequestMapping(value = "/systemIndex")
	public String systemIndex(HttpServletRequest request, Model model) {
		String loginId = (String) request.getSession().getAttribute("loginId");
		if (StringUtils.isEmpty(loginId)) {
			return "/home/login/index";
		}
		return "/vipcenter/system_index";
	}
	
	@RequestMapping(value = "/toSystemInfo")
	public String toSystemInfo(HttpServletRequest request) {
		request.setAttribute("data", findSystemById(request.getParameter("id")));
		return "/vipcenter/system_info";
	}

	/**
	 * 系统通知
	 * 
	 * @return
	 */
	@RequestMapping(value = "/systemView")
	public String systemView(HttpServletRequest request, Model model, String id) {
		Map<String, String> map = new HashMap<String, String>();
		String result = HttpUtil
				.get(EnvUtil.getVal("PAASOS_DEPEND_APIPLATFORMMSG") + "/platformmsg/getById/" + id, map);
		model.addAttribute("result", JSON.parseObject(result).getJSONObject("data"));
		return "/vipcenter/system_view";
	}

	/**
	 * 分页查询系统消息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/systemIndexQuery")
	@ResponseBody
	public JSONObject systemIndexQuery(HttpServletRequest request) {
		JSONObject resultJson = new JSONObject();
		try {
			// 获取登录人UUid
			String loginId = (String) request.getSession().getAttribute("loginId");
			if (loginId.equals("")) {
				resultJson.put("code", "1");
				resultJson.put("msg", "获取登录名失败");
				return resultJson;
			}
			String url = EnvUtil.getVal("PAASOS_DEPEND_APILETTERTPL") + "/letterService/query";
			JSONObject parameter = new JSONObject();
			parameter.put("pageNo", request.getParameter("pageNo") + "");
			parameter.put("pageSize", request.getParameter("pageSize") + "");
			parameter.put("toUserId", loginId);
			String jsonStr = HttpUtil.post(url, parameter);
			resultJson = JSON.parseObject(jsonStr);
		} catch (Exception e) {
			resultJson.put("code", "1");
			resultJson.put("msg", e.toString());
		}
		return resultJson;
	}
	
	/**
	 * 根据ID查询系统消息
	 * 
	 * @param String
	 *            id 系统消息ID
	 * @return
	 */
	private JSONObject findSystemById(String id) {
		JSONObject resultJson = new JSONObject();
		try {
			String url = EnvUtil.getVal("PAASOS_DEPEND_APILETTERTPL") + "/letterService/findById";
			Map<String, String> parameter = new HashMap<String, String>();
			parameter.put("id", id);
			String jsonStr = HttpUtil.get(url, parameter);
			JSONObject json = JSON.parseObject(jsonStr);
			if (json.get("data") != null) {
				resultJson = (JSONObject) json.get("data");
				if(!resultJson.getString("msgSta").equals("2")){
					// 查看后修改状态为已读
					url = EnvUtil.getVal("PAASOS_DEPEND_APILETTERTPL") + "/letterService/updateMsgSta";
					JSONObject parameter2 = new JSONObject();
					parameter2.put("id", id);
					parameter2.put("msgSta", "2");
					jsonStr = HttpUtil.post(url, parameter2);					
				}
			}
		} catch (Exception e) {
			resultJson.put("code", "1");
			resultJson.put("msg", "根据ID查询系统消息时发生异常");
		}
		return resultJson;
	}

	/**
	 * 我的合同
	 * 
	 * @return
	 */
	@RequestMapping(value = "/mycontract")
	public String myContract() {

		return "/vipcenter/personal";
	}

	/**
	 * 跳转到账户信息页
	 * 
	 * @return
	 */
	@RequestMapping("/mycenter")
	public String myCenter(String loginId, String loginType, Model model, HttpServletRequest request) {
		String lId = (String) request.getSession().getAttribute("loginId");
		if (StringUtils.isEmpty(lId)) {
			return "/home/login/index";
		}
		JSONObject member = accountService.getCompany(loginId, loginType);
		if (!StringUtils.isEmpty(loginType) && loginType.equals("P")) {// 采购商
			if (member != null) {
				member.put("loginType", "P");
				model.addAttribute("member", member);
				return "/member/memberDetail";// 采购商详情页
			} else {
				model.addAttribute("loginId", loginId);
				return "/member/providerRegister";
			}
		} else {// 供应商
			if (member != null) {
				member.put("loginType", "S");
				model.addAttribute("member", member);
				return "/member/supplierRegister";// 供应商详情页
			} else {
				member = new JSONObject();
				member.put("loginType", "S");
				return "/member/supplierRegister";// 供应商详情页
			}
		}
	}
	/**
	 * 跳转到审批记录信息页
	 * 
	 * @return
	 */
	@RequestMapping("/registerDetail")
	public String applayRecode(String loginId, String loginType, Model model, HttpServletRequest request) {
		
		String lId = (String) request.getSession().getAttribute("loginId");
		if (StringUtils.isEmpty(lId)) {
			return "/home/login/index";
		}
		JSONObject member = accountService.getCompany(loginId, loginType);
		if (!StringUtils.isEmpty(loginType) && loginType.equals("P")) {// 采购商
			if (member != null) {
				member.put("loginType", "P");
				model.addAttribute("member", member);
				return "/member/memberDetail";// 采购商详情页
			} else {
				model.addAttribute("loginId", loginId);
				return "/member/providerRegister";
			}
		} else {// 供应商
			if (member != null) {
				if(member.get("approvalStatus").toString().equals("11")||member.get("approvalStatus").toString().equals("12")){
					member.put("loginType", "S");
					model.addAttribute("member", member);
				}else{
					member = new JSONObject();
					
				}
				
				return "/member/detail";// 供应商详情页
			} else {
				member = new JSONObject();
				member.put("loginType", "S");
				return "/member/detail";// 供应商详情页
			}
		}
	}
}
