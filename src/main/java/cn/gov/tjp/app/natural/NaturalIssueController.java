package cn.gov.tjp.app.natural;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

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
 * 产品检验公示controller
 * @author hm
 *
 */
@Controller
@RequestMapping("/ni")
public class NaturalIssueController {

	@RequestMapping("/download")
	public String dmDownload(Model model){
		return "/document/list";
	}
	
	private String NI_URL = EnvUtil.getVal("PAASOS_DEPEND_APIAPIGDSALTNATURAL01")+"/ni";
	private String natural_url = EnvUtil.getVal("PAASOS_DEPEND_APIAPIGDSALTNATURAL01")+"/naturalService";
	private String prodlib_url = EnvUtil.getVal("PAASOS_DEPEND_APIGDSALTPRODLIB01")+"/categoryService";
	private String article_url = EnvUtil.getVal("PAASOS_DEPEND_APIGDSALTPUBLISH")+ "/publishContent";
	
	@RequestMapping("/query")
	@ResponseBody
	public ModelAndView query(HttpServletRequest request,Model model){
		    StringView view=new StringView();
		try {
			String issueTitle=request.getParameter("issueTitle");
			String beginDate=request.getParameter("beginDate");
			String endDate=request.getParameter("endDate");
			//审核状态：11 :审核同意  12：审核驳回
			String status=request.getParameter("status");
			String page=request.getParameter("page");
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
				params.put("issueContent", title);
			}
			if(status !=null && !status.equals("")){
				params.put("status", status);
			}
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
			
			String result = HttpUtil.postFormParams(NI_URL,"/query", params);
			RestVo vo = JSONObject.toJavaObject(JSON.parseObject(result),RestVo.class);
			Object object=vo.getData();
			List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
			List<NaturalIssueVo> svList=new ArrayList<NaturalIssueVo>();
			if(object!=null && !object.toString().equals("") && !object.toString().equals("") && !object.toString().equals("null")){
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
				json.put("totalRow", vo.getExceptionMsg());
				Double totalPages=Math.ceil(Double.parseDouble(vo.getExceptionMsg())/Double.parseDouble(pageSize));
				//总页数
				String sss =  (totalPages.toString()).substring(0,(totalPages.toString()).indexOf("."));
				json.put("totalPage",Integer.valueOf(sss));
			}else{
				json.put("totalRow", "0");
				json.put("totalPage", "0");
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
	 * 根据id获取食盐资质详情记录
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
			String result = HttpUtil.get(NI_URL+"/getById/", map);
			RestVo vo=JSONObject.toJavaObject(JSON.parseObject(result), RestVo.class);
			Object object=vo.getData();
			if (object != null && object instanceof Map) {
				Map<String,Object> dmMap=(Map<String,Object>) object;
				//食盐资质id集合
				Object nIds=dmMap.get("nIds");
				if(nIds!=null && !nIds.toString().equals("") && !nIds.toString().equals("null")){
					Object issueTitle=dmMap.get("issueTitle");
					Object issueContent=dmMap.get("issueContent");
					String [] ids=nIds.toString().split(",");
					for(String nId:ids){
						//通过id查询食盐资质记录
						result = HttpUtil.get(natural_url+"/query/"+nId, "");
						JSONObject json = JSON.parseObject(result);
						String noticeString = json.get("data").toString();
						net.sf.json.JSONObject jsonObj=net.sf.json.JSONObject.fromObject(noticeString);
						jsonObj.put("issueTitle", issueTitle.toString());
						jsonObj.put("issueContent", issueContent.toString());
						jsonArr.add(jsonObj);
					}
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
		return "/natural/detail";
	}
	@RequestMapping("/naturaldetail")
	public String naturaldetail(HttpServletRequest request){
		return "/natural/naturaldetail";
	}
	/**
	 * 根据id获取食盐资质详情记录
	 * @param request
	 * @param column
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getNiByIdNatual")
    @ResponseBody
    public JSONArray getNiByIdNatual(HttpServletRequest request,String id){
		JSONArray jsonArr=new JSONArray();
		try {
			Map<String,String> map=new HashMap<String,String>();
			map.put("id", id);
			String result = HttpUtil.get(NI_URL+"/getById/", map);
			RestVo vo=JSONObject.toJavaObject(JSON.parseObject(result), RestVo.class);
			Object object=vo.getData();
			if (object != null && object instanceof Map) {
				Map<String,Object> dmMap=(Map<String,Object>) object;
				//食盐资质id集合
				Object nIds=dmMap.get("nIds");
				Object createDate = dmMap.get("createDate");
				if(nIds!=null && !nIds.toString().equals("") && !nIds.toString().equals("null")){
					Object issueTitle=dmMap.get("issueTitle");
					Object issueContent=dmMap.get("issueContent");
					Object province="";
					Object kindes="";
					String [] ids=nIds.toString().split(",");
					for(String nId:ids){
						//通过id查询食盐资质记录
						/*Map<String,String> par=new HashMap<String, String>();
						par.put("id", nId);*/
						result = HttpUtil.get(natural_url+"/query/"+nId, "");
						JSONObject json = JSON.parseObject(result);
						String noticeString = json.get("data").toString();
						net.sf.json.JSONObject jsonObj=net.sf.json.JSONObject.fromObject(noticeString);
						String preId = jsonObj.getString("remark2");
						if(!StringUtils.isEmpty(preId)){

							//String url = EnvUtil.getVal("PAASOS_DEPEND_APIGDSALTPRODLIB01");
							Map<String, Object> params = new HashMap<String, Object>();
							Map<String, Object> mapC = new HashMap<String, Object>();
							mapC.put("id", preId);
							params.put("queryInfo", JSON.toJSONString(mapC));
							String resultC  = HttpUtil.postFormParams(prodlib_url, "/query", params);
							String caregary = JSONObject.parseObject(resultC).get("message").toString();
							JSONArray caregaryArray = JSONArray.fromObject(caregary);
							if(caregaryArray.size()>0){
								//net.sf.json.JSONObject o = caregaryArray.getJSONObject(caregaryArray.get(0).toString());
								JSONObject careJson = JSONObject.parseObject(caregaryArray.get(0).toString());
								String suppplier = careJson.getString("supplier");
								if(!StringUtils.isEmpty(suppplier)){
									province=suppplier;
								}
								String parentId = careJson.getString("preId");				
						
								if(!StringUtils.isEmpty(preId)){
									params = new HashMap<String, Object>();
									mapC = new HashMap<String, Object>();
									mapC.put("id", parentId);
									params.put("queryInfo", JSON.toJSONString(mapC));
									String resultCa  = HttpUtil.postFormParams(prodlib_url, "/query", params);
									String cary = JSONObject.parseObject(resultCa).get("message").toString();
									JSONArray careArray = JSONArray.fromObject(cary);
									if(careArray.size()>0){
										//careArray.sf.json.JSONObject o = caregaryArray.getJSONObject(caregaryArray.get(0).toString());
										JSONObject cJson = JSONObject.parseObject(careArray.get(0).toString());
										String typeName = cJson.getString("name");
										if(!StringUtils.isEmpty(typeName)){
											kindes =typeName;
										}
									}
								}
							
							}
							
						
						}
						
						
						
						
						
						
						jsonObj.put("issueTitle", issueTitle.toString());
						jsonObj.put("issueContent", issueContent.toString());
						jsonObj.put("createDateIssue", createDate);
						if(!StringUtils.isEmpty(province)){
							jsonObj.put("province", province);
						}
						if(!StringUtils.isEmpty(kindes)){
							jsonObj.put("kindes", kindes);
						}	
						jsonArr.add(jsonObj);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonArr;
	}
	
	/**
	 * 查询产品检测公示列表
	 * @param request
	 * @param column
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/naturalQuery")
	@ResponseBody
	public Map<String, Object> query(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String issueTitle=request.getParameter("issueTitle");
			String beginDate=request.getParameter("beginDate");
			String endDate=request.getParameter("endDate");
			String pageNo=request.getParameter("page");
			String pageSize=request.getParameter("pageSize");
			
			// String id = request.getParameter("id");//审核历史
			String columnName = request.getParameter("columnName");// 栏目名称
			String comeSource = "监管平台";// 来源
			if (StringUtils.isEmpty(pageNo)) {
				pageNo = "1";
			}
			if (StringUtils.isEmpty(pageSize)) {
				pageSize = "10";
			}
			if (StringUtils.isEmpty(comeSource)) {
				comeSource = "";
			}
			if (StringUtils.isEmpty(columnName)) {
				columnName = "";
			}
			if (StringUtils.isEmpty(issueTitle)) {
				issueTitle = "";
			}
			if (StringUtils.isEmpty(beginDate)) {
				beginDate = "";
			}
			if (StringUtils.isEmpty(endDate)) {
				endDate = "";
			}
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("pageNo", pageNo);
			params.put("pageSize", pageSize);
			params.put("comeSource", comeSource);
			params.put("columnName", columnName);
			params.put("title", issueTitle);
			params.put("startDate", beginDate);
			params.put("endDate", endDate);
		
			String result = "";
			result = HttpUtil.postFormParams(article_url, "/queryPublish", params);
			JSONObject json = JSON.parseObject(result);
			List<JSONObject> arrayResult = JSON.toJavaObject(JSON.parseArray(String.valueOf(json.get("data"))),	List.class);
			resultMap.put("data", arrayResult);
			// 总记录数
			resultMap.put("totalRow", json.get("totalRow"));
			// 总页数
			resultMap.put("totalPage", json.get("totalPage"));
			// 当前页数
			resultMap.put("curPage", json.get("curPage"));
			// 每页记录数
			resultMap.put("pageLine", json.get("pageLine"));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}
	
	
	/**
	 * 查詢產品信息發佈詳細信息
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/getPublishById")
    @ResponseBody
    public JSONArray getPublishById(HttpServletRequest request){
		JSONArray jsonArr=new JSONArray();
		try {
			String id=request.getParameter("id");
			if(!StringUtils.isEmpty(id)){
				
				String result = HttpUtil.get(article_url+"/query/"+id, "");
				JSONObject objResult = JSON.parseObject(result);
				String obj = objResult.get("data").toString();
				net.sf.json.JSONObject jsonObj=net.sf.json.JSONObject.fromObject(obj);
				jsonArr.add(jsonObj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonArr;
	}
}
