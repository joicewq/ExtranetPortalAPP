package cn.gov.tjp.app.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.qhgrain.common.util.EnvUtil;
import com.qhgrain.ecom.common.dto.um.Resource;

import cn.gov.tjp.app.service.CommonService;

@Controller()
@RequestMapping("/resourceController")
public class ResourceController {
    public static final String HIM="ECOM_HIM";//首页管理
    
    public static final String UM="ECOM_UM";//用户管理
    
    public static final String CIM="ECOM_CIM";//企业管理
    
    public static final String SDIM="ECOM_SDIM";//供求管理
    
    public static final String SM="ECOM_SM";//后台管理
    
    public static final String PIC="ECOM_PIC";//图片管理
    
    @Autowired
    private CommonService commonService;
    
    private String  ecomUmService=EnvUtil.getVal("PAASOS_DEPEND_APIECOMUM");
    
    private String getByCode="/resourceService/resource/query/byCode";
    
    /**
     * 根据资源code获取资源url路径
     * Description
     * @param 
     * @return 
     * @author Lovercy
     * @date 2016年4月29日
     */
    @RequestMapping("/getPath")
    @ResponseBody
    public String getPath(String pathCode,HttpServletRequest request,HttpServletResponse response){
        String path="";
        if(!StringUtils.isEmpty(pathCode)){
            String url=ecomUmService+getByCode+"/"+pathCode;
            String str=commonService.getForObject(url);
            if(!StringUtils.isEmpty(str) && !str.trim().equals("{}")){
                Resource res=JSON.parseObject(str, Resource.class);
                path=res.getResourceUrl();
            }
        }
        return path;
    }
}
