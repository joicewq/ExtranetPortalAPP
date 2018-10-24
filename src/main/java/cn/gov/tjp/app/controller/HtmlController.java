package cn.gov.tjp.app.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.qhgrain.common.util.EnvUtil;
import com.qhgrain.ecom.common.dto.cim.CompanyRelateGrain;
import com.qhgrain.ecom.common.dto.cim.CompanyVO;
import com.qhgrain.ecom.common.dto.page.PageInfo;
import com.qhgrain.ecom.common.dto.sdim.PurchaseEntity;
import com.qhgrain.ecom.common.dto.sdim.PurchaseEntityDTO;
import com.qhgrain.ecom.common.dto.sdim.SellEntity;
import com.qhgrain.ecom.common.dto.sdim.SellEntityDTO;
import com.qhgrain.ecom.common.dto.sm.Advertisement;
import com.qhgrain.ecom.common.dto.sm.Area;
import com.qhgrain.ecom.common.dto.sm.Grain;
import com.qhgrain.ecom.common.dto.sm.Notification;
import com.qhgrain.ecom.common.dto.um.Resource;
import com.qhgrain.ecom.common.util.CommonStatus;
import com.qhgrain.ecom.common.util.ResultMessage;

import cn.gov.tjp.app.service.CommonService;
import cn.gov.tjp.app.service.FreeMarkerService;

/**
 * freemarker生成静态化页面
  * Description
  * @author Lovercy
  * @version 1.0
  * @date 2016年4月20日
  * Copyright 青海粮食云项目组
 */
@Controller
@RequestMapping("/htmlController")
public class HtmlController {
    @Autowired
    private FreeMarkerService freeMarkerService;
    
    @Autowired
    private CommonService commonService;
    
    private String  ecomUmService=EnvUtil.getVal("PAASOS_DEPEND_APIECOMUM");
    
    private String getByCode= "/resourceService/resource/query/byCode";
    private String companyServiceUrl=EnvUtil.getVal("PAASOS_DEPEND_APIECOMCIM")+File.separator+"companyService";
    private String dicServiceUrl=EnvUtil.getVal("PAASOS_DEPEND_APIECOMSM")+File.separator+"dictionaryService";
    private String tradeServiceUrl=EnvUtil.getVal("PAASOS_DEPEND_APIECOMSDIM");
    private String advertService=EnvUtil.getVal("PAASOS_DEPEND_APIECOMSM")+File.separator+"advertService";
    private String noticeService=EnvUtil.getVal("PAASOS_DEPEND_APIECOMSM")+File.separator+"noticeService";
    
    
    /**
     * 系统启动时生成html文件
     * Description
     * @param 
     * @return 
     * @author Lovercy
     * @date 2016年4月27日
     */
    /*@PostConstruct
    public void createAll(){
//        createHome();
//        createCompany();
//        createSell();
//        createPurchase();
    }*/
    
    
    /**
     * 首页html页面生成
     * Description
     * @param 
     * @return 
     * @author Lovercy
     * @date 2016年4月20日
     */
    public void createHome(){
        System.out.println("createHomeStart:"+new Date());
        //模版名称
        String templateName="home.ftl";
        //模版输出结果集
        Map<String,Object> root=new HashMap<String,Object>();
        //①url路径
        String himPath=getPath(ResourceController.HIM);//首页
        String umPath=getPath(ResourceController.UM);//用户管理
        String cimPath=getPath(ResourceController.CIM);//企业管理
        String sdimPath=getPath(ResourceController.SDIM);//供求管理
        String picPath=getPath(ResourceController.PIC);//图片管理
        root.put("picPath", picPath);
        root.put("himPath",himPath);
        root.put("umPath",umPath);
        root.put("cimPath",cimPath);
        root.put("sdimPath",sdimPath);
        //②最新供应
        SellEntityDTO sell=new SellEntityDTO();
        sell.setStart("1");
        sell.setNum("5");
        PageInfo<SellEntity> sellPb=getSell(sell);
        List<SellEntity> sellList=sellPb.getList();
        root.put("sellList", sellList);
        //③最新采购
        PurchaseEntityDTO purchase=new PurchaseEntityDTO();
        purchase.setStart("1");
        purchase.setNum("5");
        PageInfo<PurchaseEntity> purPb=getPurchase(purchase);
        List<PurchaseEntity> purchaseList=purPb.getList();
        root.put("purchaseList",purchaseList);
        //④公告
        List<Notification> noticeList=getNotice();
        root.put("noticeList",noticeList);
        //⑤最新企业
        PageInfo<CompanyVO> pb=new PageInfo<CompanyVO>();
        List<CompanyVO> paramList=new ArrayList<CompanyVO>();
        CompanyVO vo=new CompanyVO();
        vo.setApprovalStatus(CommonStatus.APPROVAL_STATUS_AGREE);
        vo.setIsDelete(CommonStatus.DELETE_STATUS_FALSE);
        paramList.add(vo);
        pb.setList(paramList);
        pb.setPageNum(1);
        pb.setPageSize(8);
        PageInfo<CompanyVO> companyPb=getCompany(pb);
        List<CompanyVO> companyList=companyPb.getList();
        root.put("companyList",companyList);
        root.put("companyStr", JSON.toJSONString(companyList));
        //⑥粮食类型
        List<Grain> grainList=getGrain();
        root.put("grainList",grainList);
        //⑦地区列表
        List<Area> areaList=getArea("000000");
        root.put("areaList",areaList);
        
        //轮播广告
        List<Advertisement> firstPic=getAdvertisementList("1","1","20");
        List<Advertisement> secondPic=getAdvertisementList("2","1","20");
        root.put("firstPic",JSON.toJSONString(firstPic));
        root.put("secondPic",JSON.toJSONString(secondPic));
        /**
         * 生成的html文件的路径
         */
        String rootPath=getRootPath();
        String filePath=rootPath+File.separator+"index.html";
        System.out.println(filePath);
        try {
            freeMarkerService.processTemplate(templateName, root, filePath);
        } catch (Exception e) {
            e.printStackTrace();
        } 
        System.out.println("createHomeEnd:"+new Date());
    }
    /**
     * 供应信息列表页html页面生成
     * Description
     * @param 
     * @return 
     * @author Lovercy
     * @date 2016年4月22日
     */
    public void createSell(){
        System.out.println("createSellStart:"+new Date());
        String templateName="sell.ftl";
        Map<String,Object> root=new HashMap<String,Object>();
        //url路径
        String himPath=getPath(ResourceController.HIM);//首页
        String umPath=getPath(ResourceController.UM);//用户管理
        String cimPath=getPath(ResourceController.CIM);//企业管理
        String sdimPath=getPath(ResourceController.SDIM);//供求管理
        String picPath=getPath(ResourceController.PIC);//图片管理
        root.put("picPath", picPath);
        root.put("himPath",himPath);
        root.put("umPath",umPath);
        root.put("cimPath",cimPath);
        root.put("sdimPath",sdimPath);
        //筛选条件-粮食类型
        List<Grain> sellGrainList=getSellGrain();
        root.put("sellGrainList", sellGrainList);
        //筛选条件-地区
        List<Area> areaList=getArea(null);
        String areaStr=JSON.toJSONString(areaList);
        root.put("areaStr",areaStr);
        //列表展示
        SellEntityDTO sell=new SellEntityDTO();
        sell.setStart("1");
        sell.setNum("10");
        PageInfo<SellEntity> sellPb=getSell(sell);
        root.put("sellPb", sellPb);
        String rootPath=getRootPath();
        String filePath=rootPath+File.separator+"sell.html";
        try{
            freeMarkerService.processTemplate(templateName, root, filePath);
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("createSellEnd:"+new Date());
    }
    
    /**
     * 采购信息列表页html页面生成
     * Description
     * @param 
     * @return 
     * @author Lovercy
     * @date 2016年4月22日
     */
    public void createPurchase(){
        System.out.println("createPurchaseStart:"+new Date());
        String templateName="purchase.ftl";
        Map<String,Object> root=new HashMap<String,Object>();
        //url路径
        String himPath=getPath(ResourceController.HIM);//首页
        String umPath=getPath(ResourceController.UM);//用户管理
        String cimPath=getPath(ResourceController.CIM);//企业管理
        String sdimPath=getPath(ResourceController.SDIM);//供求管理
        String picPath=getPath(ResourceController.PIC);//图片管理
        root.put("picPath", picPath);
        root.put("himPath",himPath);
        root.put("umPath",umPath);
        root.put("cimPath",cimPath);
        root.put("sdimPath",sdimPath);
        //筛选条件-粮食类型
        List<Grain> purchaseGrainList=getPurGrain();
        root.put("purchaseGrainList", purchaseGrainList);
        //筛选条件-地区
        List<Area> areaList=getArea(null);
        String areaStr=JSON.toJSONString(areaList);
        root.put("areaStr",areaStr);
        //列表展示
        PurchaseEntityDTO purchase=new PurchaseEntityDTO();
        purchase.setStart("1");
        purchase.setNum("10");
        PageInfo<PurchaseEntity> purchasePb=getPurchase(purchase);
        root.put("purchasePb", purchasePb);
       // String rootPath=ServletContext.getRealPath("/");//webRoot绝对路径
        String rootPath=getRootPath();
        String filePath=rootPath+File.separator+"purchase.html";
        try{
            freeMarkerService.processTemplate(templateName, root, filePath);
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("createPurchaseEnd:"+new Date());
    }
    
    /**
     * 企业列表html页面生成
     * Description
     * @param 
     * @return 
     * @author Lovercy
     * @date 2016年4月22日
     */
    public void createCompany(){
        System.out.println("createCompanyStart:"+new Date());
        String templateName="company.ftl";
        Map<String,Object> root=new HashMap<String,Object>();
        //url路径
        String himPath=getPath(ResourceController.HIM);//首页
        String umPath=getPath(ResourceController.UM);//用户管理
        String cimPath=getPath(ResourceController.CIM);//企业管理
        String sdimPath=getPath(ResourceController.SDIM);//供求管理
        String picPath=getPath(ResourceController.PIC);//图片管理
        root.put("picPath", picPath);
        root.put("himPath",himPath);
        root.put("umPath",umPath);
        root.put("cimPath",cimPath);
        root.put("sdimPath",sdimPath);
        //筛选条件-粮食类型
        List<Grain> companyGrainList=getComGrain();
        root.put("companyGrainList", companyGrainList);
        //筛选条件-地区
        List<Area> areaList=getArea(null);
        String areaStr=JSON.toJSONString(areaList);
        root.put("areaStr",areaStr);
        //列表展示
        PageInfo<CompanyVO> pb=new PageInfo<CompanyVO>();
        pb.setPageNum(1);
        pb.setPageSize(10);
        PageInfo<CompanyVO> companyPb=getCompany(pb);
        root.put("companyPb", companyPb);
        //String rootPath=request.getSession().getServletContext().getRealPath("/");//webRoot绝对路径
        String rootPath=getRootPath();
        String filePath=rootPath+File.separator+"company.html";
        try{
            freeMarkerService.processTemplate(templateName, root, filePath);
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("createCompanyEnd:"+new Date());
    }
    
   /**
    * 获取路径
    * Description
    * @param 
    * @return 
    * @author Lovercy
    * @date 2016年4月21日
    */
    private String getPath(String pathCode){
        String path="";
        if(!StringUtils.isEmpty(pathCode)){
            String url=ecomUmService+getByCode+File.separator+pathCode;
            String str=commonService.getForObject(url);
            if(!StringUtils.isEmpty(str) && !str.trim().equals("{}")){
                Resource res=JSON.parseObject(str, Resource.class);
                path=res.getResourceUrl();
            }
        }
        return path;
    }
    /**
     * 获取粮食类型
     * Description
     * @param 
     * @return 
     * @author Lovercy
     * @date 2016年4月21日
     */
    private List<Grain> getGrain(){
        Grain grain=new Grain();
        ResultMessage grainResult=JSON.parseObject(commonService.postForObject(grain,dicServiceUrl,"grain/queryAll"),ResultMessage.class);
        List<Grain> grainList=JSON.parseArray(grainResult.getMessage().toString(), Grain.class) ;
        return grainList;
    }
    /**
     * 获取供应粮食类型
     * Description
     * @param 
     * @return 
     * @author Lovercy
     * @date 2016年4月22日
     */
    private List<Grain> getSellGrain(){
        List<Grain> all=getGrain();
        String url=tradeServiceUrl+File.separator+"sell"+File.separator+"label"+File.separator+"query";
        String str=commonService.getForObject(url);
        if(str.indexOf("{")>-1){
            return all;
        }else{
            List<String> grainCode=JSON.parseArray(str,String.class);
            List<Grain> sellLabel=new ArrayList<Grain>();
            for(Grain g:all){
                for(int i=0;i<grainCode.size();i++){
                    if(grainCode.get(i).equals(g.getGrainCode())){
                        sellLabel.add(g);
                    }
                }
            }
            return sellLabel;
        }
    }
        
    /**
     * 获取采购相关粮食类型
     * Description
     * @param 
     * @return 
     * @author Lovercy
     * @date 2016年4月29日
     */
    private List<Grain> getPurGrain(){
        List<Grain> all=getGrain();
        String url=tradeServiceUrl+File.separator+"purchase"+File.separator+"label"+File.separator+"query";
        String str=commonService.getForObject(url);
        if(str.indexOf("{")>-1){
            return all;
        }else{
            List<String> grainCode=JSON.parseArray(str,String.class);
            List<Grain> purLabel=new ArrayList<Grain>();
            for(Grain g:all){
                for(int i=0;i<grainCode.size();i++){
                    if(grainCode.get(i).equals(g.getGrainCode())){
                        purLabel.add(g);
                    }
                }
            }
            return purLabel;
        }
    }
    /**
     * 获取企业相关粮食类型
     * Description
     * @param 
     * @return 
     * @author Lovercy
     * @date 2016年4月29日
     */
    private List<Grain> getComGrain(){
        List<Grain> all=getGrain();
        String str=commonService.getForObject(companyServiceUrl,"grains"+File.separator+"queryAll");
        ResultMessage result=JSON.parseObject(str, ResultMessage.class);
        if(result.getResult()==ResultMessage.Success){
            List<CompanyRelateGrain> list=JSON.parseArray(result.getMessage().toString(), CompanyRelateGrain.class);
            List<Grain> comGrain=new ArrayList<Grain>();
            for(Grain g:all){
                for(CompanyRelateGrain crg:list){
                    if(crg.getGrainId().equals(g.getGrainCode())){
                        comGrain.add(g);
                    }
                }
            }
            return comGrain;
        }else{
            return all;
        }
    }
    
    /**
     * 获取地区
     * Description
     * @param 
     * @return 
     * @author Lovercy
     * @date 2016年4月21日
     */
    private List<Area> getArea(String parentId){
        Area area=new Area();
        area.setParentId(parentId);
        ResultMessage areaResult=JSON.parseObject(commonService.postForObject(area,dicServiceUrl,"area"+File.separator+"queryAll"),ResultMessage.class);
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
    private PageInfo<CompanyVO> getCompany(PageInfo<CompanyVO> pb){
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
        ResultMessage companyResult=JSON.parseObject(commonService.postForObject(pb,companyServiceUrl,"companyVo"+File.separator+"queryAll",((pb.getPageNum()-1)*pb.getPageSize())+1,pb.getPageSize()),ResultMessage.class);
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
    private PageInfo<SellEntity> getSell(SellEntityDTO sell){
        sell.setEndTime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));//过滤掉过期的记录
        sell.setApprovalStatus("500,300");
        String pbStr=commonService.postForObject(sell,tradeServiceUrl,"sell"+File.separator+"query",sell.getStart(),sell.getNum());
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
    private PageInfo<PurchaseEntity> getPurchase(PurchaseEntityDTO purchase){
        purchase.setEndTime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));//过滤掉过期的记录
        purchase.setApprovalStatus("500,300");
        String pbStr=commonService.postForObject(purchase,tradeServiceUrl,"purchase"+File.separator+"query",purchase.getStart(),purchase.getNum());
        PageInfo<PurchaseEntity> purchasePb=JSON.parseObject(pbStr,PageInfo.class);
        return purchasePb;
    }
    
    /**
     * 获取公告
     * Description
     * @param 
     * @return 
     * @author Lovercy
     * @date 2016年4月21日
     */
    private List<Notification> getNotice(){
        Notification notice=new Notification();
        String resultMap=commonService.postForObject(notice,noticeService,"notice"+File.separator+"query"+File.separator,1,6);
        ResultMessage result = JSON.parseObject(resultMap, ResultMessage.class);
        PageInfo<Notification> newPb=JSON.parseObject(result.getMessage().toString(),PageInfo.class);
        List<Notification> noticeList=newPb.getList();
        return noticeList;
    }
    
    /**
     * 获取webapp绝对路径
     * Description
     * @param 
     * @return 
     * @author Lovercy
     * @date 2016年4月23日
     */
    private String getRootPath(){
        String basePath=System.getProperty("himApp.root");
        System.out.println(basePath);
        return basePath;
    }
    
    /**
     * 获取首页广告
     * Description
     * @param advLocation:图片展示位置,orderNum:序号,pagNum:第几页
     * @return 
     * @author Lovercy
     * @date 2016年5月3日
     */
    private List<Advertisement> getAdvertisementList (String advLocation,String pageNum,String pageSize ){
        List<Advertisement> result=new ArrayList<Advertisement>();
        Advertisement advert=new Advertisement();
        advert.setAdvLocation(advLocation);
        advert.setOrderNum(0);
        try{
            String resultStr=commonService.postForObject(advert, advertService,"advert"+File.separator+"query"+File.separator,pageNum,pageSize);
            ResultMessage msg=JSON.parseObject(resultStr,ResultMessage.class);
            if(ResultMessage.Success==msg.getResult()){//获取数据成功
                PageInfo<Advertisement> info = JSON.parseObject(msg.getMessage().toString(),PageInfo.class);
                if(info != null){
                    result = JSON.parseArray(info.getList().toString(), Advertisement.class);
                    // 可以把有标号的都查出来,排序 
                    if(result != null && result.size()>0){
                        //排序   
                        Collections.sort(result, new Comparator<Advertisement>() {
                            public int compare(Advertisement arg0, Advertisement arg1) {
                                if(arg0.getOrderNum() != null && arg1.getOrderNum() != null){
                                    return arg0.getOrderNum().compareTo(arg1.getOrderNum());
                                }else{
                                    return 1;
                                }
                            }
                        });
                    }else{
                        // 如果没有广告，就查询默认的广告 
                        resultStr=commonService.postForObject(advert, advertService,"advert"+File.separator+"default");
                        ResultMessage res=JSON.parseObject(resultStr,ResultMessage.class);
                        PageInfo<Advertisement> infs = JSON.parseObject(res.getMessage().toString(),PageInfo.class);
                         result = JSON.parseArray(infs.getList().toString(), Advertisement.class);
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }

}
