package cn.gov.tjp.app.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.qhgrain.common.util.EnvUtil;
import com.qhgrain.ecom.common.dto.cim.CompanyVO;
import com.qhgrain.ecom.common.dto.page.PageInfo;
import com.qhgrain.ecom.common.dto.sdim.PurchaseEntity;
import com.qhgrain.ecom.common.dto.sdim.PurchaseEntityDTO;
import com.qhgrain.ecom.common.dto.sdim.SellEntity;
import com.qhgrain.ecom.common.dto.sdim.SellEntityDTO;
import com.qhgrain.ecom.common.dto.sm.Area;
import com.qhgrain.ecom.common.util.CommonStatus;
import com.qhgrain.ecom.common.util.ResultMessage;

import cn.gov.tjp.app.service.CommonService;

@Controller
@RequestMapping("/staticDataController")
public class StaticDataController {
    
    @Autowired
    private CommonService commonService;
    private String companyServiceUrl=EnvUtil.getVal("PAASOS_DEPEND_APIECOMCIM")+"/companyService";
    private String dicServiceUrl=EnvUtil.getVal("PAASOS_DEPEND_APIECOMSM")+"/dictionaryService";
    private String tradeServiceUrl=EnvUtil.getVal("PAASOS_DEPEND_APIECOMSDIM");
    /**
     * 获取地区
     * Description
     * @param 
     * @return 
     * @author Lovercy
     * @date 2016年4月21日
     */
    @RequestMapping("/getArea")
    @ResponseBody
    public List<Area> getArea(String parentId){
        Area area=new Area();
        area.setParentId(parentId);
        ResultMessage areaResult=JSON.parseObject(commonService.postForObject(area,dicServiceUrl,"area/queryAll"),ResultMessage.class);
        List<Area> areaList=(List<Area>) areaResult.getMessage();
        return areaList;
    }
    
    /**
     * 获取最新公司
     * Description
     * @param 
     * @return 
     * @author Lovercy
     * @date 2016年4月21日
     */
    @RequestMapping("/getCompany")
    @ResponseBody
    public PageInfo<CompanyVO> getCompany(@RequestBody PageInfo<CompanyVO> pb){
        List<CompanyVO> list=new ArrayList<CompanyVO>();
        if(pb.getList()!=null&&pb.getList().size()>0){
            list=JSON.parseArray( pb.getList().toString(),CompanyVO.class);
            CompanyVO vo=list.get(0);
            vo.setIsDelete(CommonStatus.DELETE_STATUS_FALSE);
            vo.setApprovalStatus(CommonStatus.APPROVAL_STATUS_AGREE);
        }else{
            CompanyVO vo=new CompanyVO();
            vo.setApprovalStatus(CommonStatus.APPROVAL_STATUS_AGREE);
            vo.setIsDelete(CommonStatus.DELETE_STATUS_FALSE);
            list.add(vo);
        }
        pb.setList(list);
        ResultMessage companyResult=JSON.parseObject(commonService.postForObject(pb,companyServiceUrl,"companyVo/queryAll",((pb.getPageNum()-1)*pb.getPageSize())+1,pb.getPageSize()),ResultMessage.class);
        PageInfo<CompanyVO> newPb=JSON.parseObject(companyResult.getMessage().toString(),PageInfo.class);
        return newPb;
    }
    
    /**
     * 获取供应信息
     * Description
     * @param 
     * @return 
     * @author Lovercy
     * @date 2016年4月21日
     */
    @RequestMapping("/getSellList")
    @ResponseBody
    public PageInfo<SellEntity> getSell(SellEntityDTO sell){
        sell.setEndTime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));//过滤掉过期的记录
        sell.setApprovalStatus("500,300");
        String pbStr=commonService.postForObject(sell,tradeServiceUrl,"sell/query",sell.getStart(),sell.getNum());
        PageInfo<SellEntity> sellPb=JSON.parseObject(pbStr,PageInfo.class);
        return sellPb;
    }
    
    /**
     * 获取采购信息
     * Description
     * @param 
     * @return 
     * @author Lovercy
     * @date 2016年4月21日
     */
    @RequestMapping("/getPurchaseList")
    @ResponseBody
    public PageInfo<PurchaseEntity> getPurchase(PurchaseEntityDTO purchase){
        purchase.setEndTime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));//过滤掉过期的记录
        purchase.setApprovalStatus("500,300");
        String pbStr=commonService.postForObject(purchase,tradeServiceUrl,"purchase/query",purchase.getStart(),purchase.getNum());
        PageInfo<PurchaseEntity> purchasePb=JSON.parseObject(pbStr,PageInfo.class);
        return purchasePb;
    }
}
