package cn.gov.tjp.app.supplier.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONArray;

import org.apache.http.client.ClientProtocolException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pubframework.common.http.HttpUtil;
import com.pubframework.common.vo.RestVo;
import com.qhgrain.common.util.EnvUtil;

import cn.gov.tjp.app.natural.model.NaturalIssueVo;
import cn.gov.tjp.app.utils.StringView;

/**
 * 企业备案审核历史公式controller
 * @author hm
 *
 */
@Controller
@RequestMapping("/spi")
public class SupplierIssueController {
	
	private String SPI_URL = EnvUtil.getVal("PAASOS_DEPEND_APIGDSALTSUPPLIER01")+"/spi";
	private String supplier_url = EnvUtil.getVal("PAASOS_DEPEND_APIGDSALTSUPPLIER01")+"/supplierService";
	
	@RequestMapping("/query")
	@ResponseBody
	public ModelAndView query(HttpServletRequest request,Model model){
		    StringView view=new StringView();
		try {
			String issueTitle=request.getParameter("issueTitle");
			String beginDate=request.getParameter("beginDate");
			String endDate=request.getParameter("endDate");
			//审核状态：11 :审核同意  12：审核驳回
			/*String status=request.getParameter("status");*/
			String page=request.getParameter("pageNo");
			String pageSize=request.getParameter("pageSize");
			
			Map<String, Object> params = new HashMap<String, Object>();
			if(StringUtils.isEmpty(page)){
				page="1";
			}
			if(StringUtils.isEmpty(pageSize)){
			    pageSize="10";
			}
			if (issueTitle != null && !issueTitle.equals("")) {
				String title=URLEncoder.encode(issueTitle, "UTF-8");
				params.put("issueTitle", title);
			}
		/*	if(status !=null && !status.equals("")){
				params.put("status", status);
			}*/
			if(beginDate !=null && !beginDate.equals("")){
				params.put("beginDate", beginDate);
			}
			if(endDate !=null && !endDate.equals("")){
				params.put("endDate", endDate);
			}
			Integer begin=(Integer.parseInt(page)-1)*Integer.parseInt(pageSize);
			params.put("start", begin);
			params.put("num", pageSize);
			
			JSONObject json=new JSONObject();
			
			String result = HttpUtil.postFormParams(SPI_URL,"/query", params);
			RestVo vo = JSONObject.toJavaObject(JSON.parseObject(result),RestVo.class);
			Object object=vo.getData();
			List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
			List<NaturalIssueVo> svList=new ArrayList<NaturalIssueVo>();
			if(object!=null && !object.toString().equals("") && !object.toString().equals("")&& !object.toString().equals("null")){
				list=(List<Map<String,Object>>)object;
				if(list!=null && list.size()>0){
					for(Map<String,Object> so:list){
						NaturalIssueVo svo=new NaturalIssueVo();
						Object id=so.get("id");
						if(id!=null && !id.toString().equals("null")){
							svo.setId(id.toString());
						}
						Object nIds=so.get("n_ids");
						if(nIds!=null && !nIds.toString().equals("null")){
							svo.setnIds(nIds.toString());
						}
						Object iTitle=so.get("issue_title");
						if(iTitle!=null && !iTitle.toString().equals("null")){
							svo.setIssueTitle(iTitle.toString());
						}
						Object issueContent=so.get("issue_content");
						if(issueContent!=null && !issueContent.toString().equals("null")){
							svo.setIssueContent(issueContent.toString());
						}
						Object createDate=so.get("create_date");
						Object publishDate=so.get("create_date");
						if(createDate!=null && !createDate.toString().equals("null")){
							svo.setCreateDate(createDate.toString());
						}
						svList.add(svo);
					}
				}
			}
			json.put("data", svList);
			if(!vo.getExceptionMsg().equals("") && !object.toString().equals("null")){
				//总条数
				json.put("exceptionMsg", vo.getExceptionMsg());
				Double totalPages=Math.ceil(Double.parseDouble(vo.getExceptionMsg())/Double.parseDouble(pageSize));
				//总页数
				String sss = (totalPages.toString()).substring(0,(totalPages.toString()).indexOf("."));
				json.put("totalPage", Integer.valueOf(sss));
			}else{
				json.put("exceptionMsg", "0");
				json.put("totalPages", "0");
			}
			//当前页数
			json.put("curPage", Integer.valueOf(page));
			//每页条数
			json.put("pageLine", Integer.valueOf(pageSize));
			view.setContent(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
			view.setContent("error");
		}
		return new ModelAndView(view);
	}
	/**
	 * 根据id获取企业备案详情记录
	 * @param request
	 * @param column
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@RequestMapping("/getNiById")
    @ResponseBody
    public JSONArray getNiById(HttpServletRequest request,String id){
		JSONArray jsonArr=new JSONArray();
		try {
			Map<String,String> map=new HashMap<String,String>();
			map.put("id", id);
			String result = HttpUtil.get(SPI_URL+"/getById/", map);
			RestVo vo=JSONObject.toJavaObject(JSON.parseObject(result), RestVo.class);
			Object object=vo.getData();
			if (object != null && object instanceof Map) {
				Map<String,Object> dmMap=(Map<String,Object>) object;
				//企业备案审核历史id集合
				Object nIds=dmMap.get("nIds");
				if(nIds!=null && !nIds.toString().equals("") && !nIds.toString().equals("null")){
					Object issueTitle=dmMap.get("issueTitle");
					Object issueContent=dmMap.get("issueContent");
					Object createDate=dmMap.get("createDate");
					//String [] ids=nIds.toString().split(",");
				/*	net.sf.json.JSONObject jsonObj= new net.sf.json.JSONObject();
					jsonObj.put("issueTitle", issueTitle.toString());
					jsonObj.put("issueContent", issueContent.toString());
					jsonObj.put("createDate", createDate.toString());*/
					
				/*	JSONArray issue = new JSONArray();
					issue.add(jsonObj);*/
					
					
					Map<String,Object> params = new HashMap<String,Object>();
					params.put("ids", nIds);
					result = HttpUtil.postFormParams(supplier_url, "/query/batch", params);
					JSONObject json = JSON.parseObject(result);
					String noticeString = json.get("data").toString();
					jsonArr = JSON.parseArray(noticeString);
					
					JSONObject jsonObj = (JSONObject) jsonArr.get(0);
					jsonObj.put("issueTitle", issueTitle.toString());
					jsonObj.put("issueContent", issueContent.toString());
					jsonObj.put("issueCreateDate", createDate.toString());
					
					
					//jsonArr.add(JSON.parseArray(noticeString));
					/*net.sf.json.JSONObject jsonObj=net.sf.json.JSONObject.fromObject(noticeString);
					jsonarr.put("issueTitle", issueTitle.toString());
					jsonObj.put("issueContent", issueContent.toString());
					jsonObj.put("createDate", createDate.toString());*/
					//jsonArr.add(jsonObj);
				/*	for(String nId:ids){
						//通过id查询企业备案审核历史记录
						result = HttpUtil.get(supplier_url+"/query/"+nId,"");
						JSONObject json = JSON.parseObject(result);
						String noticeString = json.get("data").toString();
						net.sf.json.JSONObject jsonObj=net.sf.json.JSONObject.fromObject(noticeString);
						jsonObj.put("issueTitle", issueTitle.toString());
						jsonObj.put("issueContent", issueContent.toString());
						jsonObj.put("createDate", createDate.toString());
						jsonArr.add(jsonObj);
					}*/
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonArr;
	}
	
	@RequestMapping("/list")
	public String list(HttpServletRequest request){
		return "/natural/list";
	}
	
	@RequestMapping("/detail")
	public String detail(HttpServletRequest request){
		return "/supplier/detail";
	}
}
