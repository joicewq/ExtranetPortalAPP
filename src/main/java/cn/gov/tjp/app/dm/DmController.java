package cn.gov.tjp.app.dm;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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

import cn.gov.tjp.app.utils.StringView;

/**
 * 资料下载controller
 * @author hm
 *
 */
@Controller
@RequestMapping("/dm")
public class DmController {
	
	private String upload_url = EnvUtil.getVal("base_upload_url");

	@RequestMapping("/download")
	public String dmDownload(Model model){
		model.addAttribute("upload_url",upload_url);
		return "/document/list";
	}
	
	private String Dm_URL = EnvUtil.getVal("PAASOS_DEPEND_APISALTDM")+"/dm";
	
	@RequestMapping("/query")
	@ResponseBody
	public ModelAndView query(HttpServletRequest request,Model model){
		    StringView view=new StringView();
		try {
			String dTitle=request.getParameter("dTitle");
			String page=request.getParameter("page");
			String pageSize=request.getParameter("pageSize");
			
			Map<String, Object> params = new HashMap<String, Object>();
			if(StringUtils.isEmpty(page)){
				page="1";
			}
			if(StringUtils.isEmpty(pageSize)){
			    pageSize="10";
			}
			if (dTitle != null && !dTitle.equals("")) {
				String title=URLEncoder.encode(dTitle, "UTF-8");
				params.put("dTitle", title);
			}
			params.put("ifSupplyDown", 0);
			Integer begin=(Integer.parseInt(page)-1)*Integer.parseInt(pageSize);
			params.put("start", begin);
			params.put("num", pageSize);
			
			JSONObject json=new JSONObject();
			
			String result = HttpUtil.postFormParams(Dm_URL,"/query", params);
			RestVo vo = JSONObject.toJavaObject(JSON.parseObject(result),RestVo.class);
			Object object=vo.getData();
			List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
			List<DmVo> svList=new ArrayList<DmVo>();
			if(object!=null && !object.toString().equals("") && !object.toString().equals("")){
				list=(List<Map<String,Object>>)object;
				if(list!=null && list.size()>0){
					for(Map<String,Object> so:list){
						DmVo svo=new DmVo();
						Object id=so.get("id");
						if(id!=null && !id.toString().equals("null")){
							svo.setId(id.toString());
						}
						Object title=so.get("d_title");
						if(title!=null && !title.toString().equals("null")){
							svo.setdTitle(title.toString());
						}
						Object describe=so.get("d_describe");
						if(describe!=null && !describe.toString().equals("null")){
							svo.setDescribe(describe.toString());
						}
						Object docId=so.get("doc_id");
						if(docId!=null && !docId.toString().equals("null")){
							svo.setDocId(docId.toString());
						}
						Object ifSupplyDown=so.get("if_supply_down");
						if(ifSupplyDown!=null && !ifSupplyDown.toString().equals("null")){
							if(ifSupplyDown.toString().equals("0")){
								svo.setIfSupplyDown("是");
							}else{
								svo.setIfSupplyDown("否");
							}
						}else{
							svo.setIfSupplyDown("否");
						}
						Object degree=so.get("degree");
						if(degree!=null && !degree.toString().equals("null") && !degree.toString().equals("")){
							svo.setDegree(degree.toString());
						}else{
							svo.setDegree("0");
						}
						svList.add(svo);
					}
				}
			}
			json.put("data", svList);
			if(!vo.getExceptionMsg().equals("")){
				//总条数
				json.put("exceptionMsg", vo.getExceptionMsg());
				Double totalPages=Math.ceil(Double.parseDouble(vo.getExceptionMsg())/Double.parseDouble(pageSize));
				//总页数
				json.put("totalPage", (totalPages.toString()).substring(0,(totalPages.toString()).indexOf(".")));
			}else{
				json.put("exceptionMsg", "0");
				json.put("totalPages", "0");
			}
			//当前页数
			json.put("curPage", page);
			//每页条数
			json.put("pageLine", pageSize);
			view.setContent(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
			view.setContent("error");
		}
		return new ModelAndView(view);
	}
	/**
	 * 根据id更新下载次数
	 * @param request
	 * @param column
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@RequestMapping("/updateDegree")
    @ResponseBody
    public String updateDegree(HttpServletRequest request,String id){
		try {
			String degree="";
			Map<String,String> map=new HashMap<String,String>();
			map.put("id", id);
			String result = HttpUtil.get(Dm_URL+"/getById/", map);
			RestVo vo=JSONObject.toJavaObject(JSON.parseObject(result), RestVo.class);
			Object object=vo.getData();
			if (object != null && object instanceof Map) {
				Map<String,Object> dmMap=(Map<String,Object>) object;
				//下载次数
				Object de=dmMap.get("degree");
				degree=de.toString();
			}
			//更新下载次数
			Map<String,Object> params=new HashMap<String, Object>();
			int de = 0;
			if(!StringUtils.isEmpty(degree) && !degree.equals("null")){
				de=Integer.parseInt(degree)+1;
			}else{
				de=1;
			}
			params.put("degree", de);
			params.put("id", id);
			HttpUtil.postFormParams(Dm_URL,"/updateDegree", params);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "success";
	}
}
