package cn.gov.tjp.app.trace.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.pubframework.common.http.HttpUtil;
import com.qhgrain.common.util.EnvUtil;

import cn.gov.tjp.app.trace.controller.info.ProduceInfoVo;
import cn.gov.tjp.app.trace.controller.info.ProductMaterialVo;
import cn.gov.tjp.app.trace.controller.info.ProductTraceInfoVo;
import cn.gov.tjp.app.trace.controller.info.SaleDetailInfoVo;
import cn.gov.tjp.app.utils.DownloadUtil;
import cn.gov.tjp.app.utils.IpUtil;
import cn.gov.tjp.app.utils.StringUtil;

/**
 * Created by lovercy on 22/11/2016.
 */
@Controller
@RequestMapping("/trace")
public class TraceController {
    @Autowired
    private RestTemplate restTemplate;

    
    private String trace_url = EnvUtil.getVal("PAASOS_DEPEND_APIGDSALTREPORTSERVICE")+ "/inner";
	
    private static Logger LOG = LoggerFactory.getLogger(TraceController.class);
	
	
    /**
     * 防伪追溯查询页
     * @return
     */
    @RequestMapping("/search")
    public String register(Model model){
        return "/trace/search";
    }

    /**
     * 根据二维码获取产品相关信息
     * @param model
     * @return
     */
    @RequestMapping("/search/qrcode/{qrCode}")
    public String queryByQrcode(HttpServletRequest request,Model model,@PathVariable("qrCode")String qrCode){
    	
    	ProduceInfoVo produceInfo = null;
		List<ProductMaterialVo> materialList = null;
		ProductTraceInfoVo trace = null;
		SaleDetailInfoVo sale = null;
		JSONObject queryLogInfo = null;
    	try {
    		String ip = IpUtil.getRealIp(request);
    		Map<String,Object> param = new HashMap<String,Object>();
    		param.put("qrCode", qrCode.trim());
    		param.put("ip", ip);
			String jsonDataString = HttpUtil.post(trace_url + "/queryByQrcode",param );
			JSONObject jsonData = JSONObject.parseObject(jsonDataString);
			
			if("1".equals(jsonData.getString("state"))){
				model.addAttribute("state", "1");
				
				if(StringUtil.isEmpty(jsonData.getString("produceInfo"))){
					model.addAttribute("state", "2");//获取不到数据
				}
				
				if(StringUtil.isNotEmpty(jsonData.getString("produceInfo"))){
					produceInfo = JSONObject.parseObject(jsonData.getString("produceInfo"), ProduceInfoVo.class);
				}
				if(StringUtil.isNotEmpty(jsonData.getString("materialInfoList"))){
					materialList = JSONObject.parseArray(jsonData.getString("materialInfoList"), ProductMaterialVo.class);
				}
				if(StringUtil.isNotEmpty(jsonData.getString("produceTraceInfo"))){
					trace = JSONObject.parseObject(jsonData.getString("produceTraceInfo"), ProductTraceInfoVo.class);
				}
				if(StringUtil.isNotEmpty(jsonData.getString("saleInfo"))){
					sale = JSONObject.parseObject(jsonData.getString("saleInfo"), SaleDetailInfoVo.class);
				}
				
				queryLogInfo = jsonData.getJSONObject("queryLogInfo");
				
			}else{
				LOG.error("根据二维码="+qrCode+" 调用追溯服务接口，查询产品信息时，api系统错误..." + jsonDataString);
				model.addAttribute("state", "3");
			}
    	} catch (Exception e) {
			LOG.error("根据二维码="+qrCode+" 调用追溯服务接口，查询产品信息时，系统错误...",e);
			model.addAttribute("state", "4");
		}
		if(produceInfo == null){
			produceInfo = new ProduceInfoVo();
		}
		if(trace == null){
			trace = new ProductTraceInfoVo();
		}
		if(sale == null){
			sale = new SaleDetailInfoVo();
		}
		
		model.addAttribute("produceInfo", produceInfo);
		model.addAttribute("materialList", materialList);
		model.addAttribute("trace", trace);
		model.addAttribute("sale", sale);
		model.addAttribute("queryLogInfo", queryLogInfo);
		
        return "/trace/query_detail";
    }

    /**
	 * 下载文件
	 * @param id
	 */
	@RequestMapping("/downloadFile")
	public void downloadFile(HttpServletRequest request,HttpServletResponse response,String dataType,String id){
		try {
			Map<String,Object> paramMap = new HashMap<String, Object>();
			paramMap.put("dataType", dataType);
			paramMap.put("id", id);
			
			String result = HttpUtil.post(trace_url + "/findById", paramMap);
			JSONObject resultJson = JSONObject.parseObject(result);
			if("1".equals(resultJson.get("state"))){
				JSONObject data = resultJson.getJSONObject("data");
				String fileName = "";
				String base64FileString = "";
				//下载检验报告扫描件
				if("ProductFinishedCheck".equals(dataType)){
					fileName = data.getString("fileName");
					base64FileString = data.getString("fileContent");
					
				}else if("ProductMaterialBuy".equals(dataType)){
					//下载原料检测报告文件
					fileName = data.getString("checkReportFileName");
					base64FileString = data.getString("checkReportFile");
				}
				//base64解码
				sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
				byte[] file = decoder.decodeBuffer(base64FileString);
				DownloadUtil.download(request, response, fileName, file);
			}
		} catch (Exception e) {
			LOG.error("下载检验报告扫描件,系统出错...",e);
		}
	}
}
