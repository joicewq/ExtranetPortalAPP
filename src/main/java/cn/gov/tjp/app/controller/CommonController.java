package cn.gov.tjp.app.controller;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.qhgrain.common.util.EnvUtil;
import com.qhgrain.ecom.common.dto.cim.CompanyVO;
import com.qhgrain.ecom.common.dto.page.PageInfo;
import com.qhgrain.ecom.common.dto.sm.Advertisement;
import com.qhgrain.ecom.common.dto.sm.Area;
import com.qhgrain.ecom.common.dto.sm.Grain;
import com.qhgrain.ecom.common.util.CommonStatus;
import com.qhgrain.ecom.common.util.ResultMessage;

import cn.gov.tjp.app.service.CommonService;


@Controller
@RequestMapping("/commonController")
public class CommonController {	
	
	@Autowired
	private CommonService commonService;
	
  private String companyServiceUrl=EnvUtil.getVal("PAASOS_DEPEND_APIECOMCIM")+"/companyService";
  private String dicServiceUrl=EnvUtil.getVal("PAASOS_DEPEND_APIECOMSM")+"/dictionaryService";
  private String tradeServiceUrl=EnvUtil.getVal("PAASOS_DEPEND_APIECOMSDIM");
  private String advertService=EnvUtil.getVal("PAASOS_DEPEND_APIECOMSM")+"/advertService";
  private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    /**
     * 分页查询企业列表
     * @param 
     * @return 
     * @author liub
     * @date 2016-3-25
     */
    @RequestMapping(value="/companys")
    @ResponseBody
    public ResultMessage queryAllByPage (@RequestBody PageInfo<CompanyVO> pb){
        if(pb.getList()!=null&&pb.getList().size()>0){
            pb.getList().get(0).setIsDelete(CommonStatus.DELETE_STATUS_FALSE);
            pb.getList().get(0).setApprovalStatus(CommonStatus.APPROVAL_STATUS_AGREE);
        }else{
            List<CompanyVO> list=new ArrayList<CompanyVO>();
            CompanyVO vo=new CompanyVO();
            vo.setApprovalStatus(CommonStatus.APPROVAL_STATUS_AGREE);
            vo.setIsDelete(CommonStatus.DELETE_STATUS_FALSE);
            list.add(vo);
            pb.setList(list);
        }
        return JSON.parseObject(commonService.postForObject(pb,companyServiceUrl,"companyVo/queryAll",((pb.getPageNum()-1)*pb.getPageSize())+1,pb.getPageSize()),ResultMessage.class);
    }
    
    /**
     * 粮食类型
     * @param 
     * @return 
     * @author liub
     * @date 2016-3-25
     */
    @RequestMapping(value="/queryGrains")
    @ResponseBody
    public ResultMessage queryGrains (){
        Grain grain=new Grain();
        return JSON.parseObject(commonService.postForObject(grain,dicServiceUrl,"grain/queryAll"),ResultMessage.class);
    }
    
    /**
     * 地区列表
     * @param 
     * @return 
     * @author liub
     * @date 2016-3-25
     */
    @RequestMapping(value="/queryAreas")
    @ResponseBody
    public ResultMessage queryAreas (String parentId){
        Area area=new Area();
        area.setParentId(parentId);
        return JSON.parseObject(commonService.postForObject(area,dicServiceUrl,"area/queryAll"),ResultMessage.class);
    }
    
    /**
     *  供应 列表
     * @param 
     * @return 
     * @author liub
     * @date 2016-3-25
     */
    @RequestMapping(value="/querySells")
    @ResponseBody
    public PageInfo querySells (){
        Map<String,String> map=new HashMap<String,String>();
        map.put("endTime",sdf.format(new Date()));
        map.put("approvalStatus","500,300");
        return JSON.parseObject(commonService.postForObject(map,tradeServiceUrl,"sell/query","1","5"),PageInfo.class);
    }

    /**
     * 求购列表
     * @param 
     * @return 
     * @author liub
     * @date 2016-3-25
     */
    @RequestMapping(value="/queryBuys")
    @ResponseBody
    public PageInfo queryBuys (){
        Map<String,String> map=new HashMap<String,String>();
        map.put("endTime",sdf.format(new Date()));
        map.put("approvalStatus","500,300");
        return JSON.parseObject(commonService.postForObject(map,tradeServiceUrl,"purchase/query","1","5"),PageInfo.class);
    }
    
    
    /**
     * 查询广告
     * @param advert
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value="/getAdvertisementList/{pageNum}/{pageSize}",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getAdvertisementList (@RequestBody Advertisement advert,@PathVariable String pageNum,@PathVariable String pageSize ) {// companyService
        
        Map<String,Object> result=new HashMap<String,Object>();
        
        if(StringUtils.isEmpty(advertService)){
            result.put("result", "0");
            result.put("message", "服务地址不存在!");
        }else{
            if(advert == null){
                result.put("result", "0");
                result.put("message", "传递的站内信信息为空!");
                return result;
            }
           /* advert.setOrderNum(0);*/
            try{
                String resultStr=commonService.postForObject(advert, advertService,"advert/query/",pageNum,pageSize);
                result=JSON.parseObject(resultStr,Map.class);
                @SuppressWarnings("unchecked")
                String re = JSON.parseObject(result.get("result").toString(),String.class);
                if("1".equals(re)){
                    
                    PageInfo<Advertisement> info = JSON.parseObject(result.get("message").toString(),PageInfo.class);
                    if(info != null){
                        List<Advertisement>  advList = JSON.parseArray(info.getList().toString(), Advertisement.class);
                        // 可以把有标号的都查出来,排序 
                        if(advList != null && advList.size()>0){
                            //排序   
                            Collections.sort(advList, new Comparator<Advertisement>() {
                                public int compare(Advertisement arg0, Advertisement arg1) {
                                    if(arg0.getOrderNum() != null && arg1.getOrderNum() != null){
                                        return arg0.getOrderNum().compareTo(arg1.getOrderNum());
                                    }else{
                                        return 1;
                                    }
                                }
                            });
                            info.setList(advList);
                            result.put("result", "1");
                            result.put("message", info);
                        }else{
                            // 如果没有广告，就查询默认的广告 
                            advert.setOrderNum(0);
                            resultStr=commonService.postForObject(advert, advertService,"advert/default");
                            result=JSON.parseObject(resultStr,Map.class);
                        }
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
                result.put("result", "0");
                result.put("message", "查询失败!"+e);
            }
        }
        return result;
        
    }
}
