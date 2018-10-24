package cn.gov.tjp.app.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.http.client.ClientProtocolException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pubframework.common.http.HttpUtil;
import com.pubframework.common.vo.RestVo;
import com.qhgrain.common.util.EnvUtil;

import cn.gov.tjp.app.constant.AreaCodeEnum;
import cn.gov.tjp.app.constant.PairVo;

/**
 * 举报投诉controller
 * @author hm
 *
 */
@Controller
@RequestMapping("/claims")
public class ClaimsController {
	//举报案件URL
	private String Claims_URL = EnvUtil.getVal("PAASOS_DEPEND_APISALTCLAIMS")+"/claims";

	/**
	 * 提交举报投诉信息
	 * @param request
	 * @param column
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@RequestMapping("/submitClaims")
    @ResponseBody
    public String submitClaims(HttpServletRequest request,String caseName,String caseArea,String rcDate,String rcName,String mobile,
    		String rcaseContent,String docId){
		    String rcCode="";
		try {
			Map<String,Object> params=new HashMap<String,Object>();
			params.put("caseName", caseName);
			params.put("area", caseArea);
			params.put("rcDate", rcDate);
			params.put("rcName", rcName);
			params.put("mobile", mobile);
			params.put("rcaseContent", rcaseContent);
			params.put("docId", docId);
			//举报方式(0:电话、1:微信、2:门户、3:传真、4:函件、5:口头)
			params.put("claimsWay", 2);
		    String result = HttpUtil.postFormParams(Claims_URL,"/add", params);
		    RestVo vo=JSONObject.toJavaObject(JSON.parseObject(result), RestVo.class);
			Object object=vo.getData();
			rcCode=object.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return rcCode;
	}
	
	/**
	 * 根据用户输入的密码和举报编码获取投诉信息
	 * @param request
	 * @param column
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@RequestMapping("/getClaims")
    @ResponseBody
    public Map<String,Object> getClaims(HttpServletRequest request,Model model,String password,String rcCode) throws ClientProtocolException, IOException{
		    Map<String,Object> soMap=new HashMap<String, Object>();
		try {
			Map<String,Object> params=new HashMap<String,Object>();
			params.put("password", password);
			params.put("rcCode", rcCode);
			//kinds类别，0代表选择投诉监管平台的举报
			params.put("kinds", "0");
			String result=HttpUtil.postFormParams(Claims_URL,"/getPwAndCodeByClaims", params);
			RestVo vo=JSONObject.toJavaObject(JSON.parseObject(result), RestVo.class);
			Object object=vo.getData();
			if (object != null && object instanceof Map) {
				Map<String,Object> map=(Map<String,Object>) object;
				Object rcName=map.get("rcName");
				if(rcName!=null && !rcName.toString().equals("null")){
					soMap.put("rcName", rcName.toString());
				}else{
					soMap.put("rcName", "");
				}
//				Object idCard=map.get("idCard");
//				if(idCard!=null && !idCard.toString().equals("null")){
//					soMap.put("idCard", idCard.toString());
//				}else{
//					soMap.put("idCard", "");
//				}
//				Object ocupertino=map.get("ocupertino");
//				if(ocupertino!=null && !ocupertino.toString().equals("null")){
//					soMap.put("ocupertino", ocupertino.toString());
//				}else{
//					soMap.put("ocupertino", "");
//				}
				Object mAddress=map.get("mAddress");
				if(mAddress!=null && !mAddress.toString().equals("null")){
					soMap.put("mAddress", mAddress.toString());
				}else{
					soMap.put("mAddress", "");
				}
				Object mobile=map.get("mobile");
				if(mobile!=null && !mobile.toString().equals("null")){
					soMap.put("mobile", mobile.toString());
				}else{
					soMap.put("mobile", "");
				}
				Object brName=map.get("brName");
				if(brName!=null && !brName.toString().equals("null")){
					soMap.put("brName", brName.toString());
				}else{
					soMap.put("brName", "");
				}
				Object rcContent=map.get("rcContent");
				if(rcContent!=null && !rcContent.toString().equals("null")){
					soMap.put("rcContent", rcContent.toString());
				}else{
					soMap.put("rcContent", "");
				}
				Object rcDate=map.get("rcDate");
				if(rcDate!=null && !rcDate.toString().equals("null")){
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	                Date date=new Date(Long.parseLong(rcDate.toString()));
	                String str=sdf.format(date);
					soMap.put("rcDate", str);
				}else{
					soMap.put("rcDate", "");
				}
				Object pword=map.get("password");
				if(pword!=null && !pword.toString().equals("null")){
					soMap.put("password", pword.toString());
				}else{
					soMap.put("password", "");
				}
				Object rCode=map.get("rcCode");
				if(rCode!=null && !rCode.toString().equals("null")){
					soMap.put("rcCode", rCode.toString());
				}else{
					soMap.put("rcCode", "");
				}
				Object replyContent=map.get("replyContent");
				if(replyContent!=null && !replyContent.toString().equals("null")){
					soMap.put("replyContent", replyContent.toString());
				}else{
					soMap.put("replyContent", "");
				}
				Object status=map.get("status");
				soMap.put("status", status.toString());
				/*if(status!=null && !status.toString().equals("null")){
					if(status.toString().equals("0")){
						soMap.put("status", "未审核");
					}else{
						soMap.put("status", "已审核");
						}
				}else{
					soMap.put("status", "未审核");
				}*/
				Object email=map.get("email");
				if(email!=null && !email.toString().equals("null")){
					soMap.put("email", email.toString());
				}else{
					soMap.put("email", "");
				}
				Object telePhone=map.get("telePhone");
				if(telePhone!=null && !telePhone.toString().equals("null")){
					soMap.put("telePhone", telePhone.toString());
				}else{
					soMap.put("telePhone", "");
				}
				Object qq=map.get("qq");
				if(qq!=null && !qq.toString().equals("null")){
					soMap.put("qq", qq.toString());
				}else{
					soMap.put("qq", "");
				}
				Object weChat=map.get("weChat");
				if(weChat!=null && !weChat.toString().equals("null")){
					soMap.put("weChat", weChat.toString());
				}else{
					soMap.put("weChat", "");
				}
				Object docId=map.get("docId");
				if(docId!=null && !docId.toString().equals("null")){
					soMap.put("docId", docId.toString());
				}else{
					soMap.put("docId", "");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return soMap;
	}
	/**
	 * 查询所有区域
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getAllArea")
	@ResponseBody
	public List<Map<String,Object>> getAllArea() {
		List<Map<String,Object>> noticeMap = new ArrayList<Map<String,Object>>();
		try {
			List<PairVo> pVo=AreaCodeEnum.getAll();
			if(pVo!=null && pVo.size()>0){
				for(PairVo vo:pVo){
					Map<String,Object> map=new HashMap<String,Object>();
					map.put("areaName", vo.getName());
					map.put("areaCode", vo.getCode());
					noticeMap.add(map);
				}
			}
	 } catch (Exception e) {
	    e.printStackTrace();
	 }
	return noticeMap;
 }
}
