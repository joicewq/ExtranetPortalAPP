package cn.gov.tjp.app.provider.controller;

import com.alibaba.fastjson.JSON;
import com.qhgrain.common.util.EnvUtil;

import cn.gov.tjp.app.common.utils.ResultMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

/**
 * Created by lovercy on 25/11/2016.
 */

@Controller
@RequestMapping("/provider")
public class ProviderController {
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 跳转到采购商信息注册的jsp页面,并将loginId带入页面
     * @param model
     * @param loginId
     * @return
     */
    @RequestMapping("/register")
    public String register(Model model, String loginId){
        model.addAttribute("loginId",loginId);
        return "/member/providerRegister";
    }

    /**
     * 提交采购商注册信息(到数据库表salt_providers中)
     * @param provider
     * @return
     */
    @RequestMapping("/submit")
    @ResponseBody
    public ResultMessage submit(String provider){
        ResultMessage result=new ResultMessage();
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<String> formEntity = new HttpEntity<String>(provider,headers);
        String url= EnvUtil.getVal("PAASOS_DEPEND_APIGDSALTPROVIDER01_URL");
        url+="/add";
        String message = restTemplate.postForObject(url, formEntity, String.class);
        if(StringUtils.isEmpty(message)){
            result.setResult(ResultMessage.Fail);
            result.setMessage("操作失败,后台未返回结果!");
        }else{
            result= JSON.parseObject(message,ResultMessage.class);
        }
        return result;
    }

    /**
     * 保存采购商注册信息(到数据库表salt_providers_temp中)
     * @param providerTemp
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public ResultMessage save(String providerTemp){
        ResultMessage result=new ResultMessage();
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<String> formEntity = new HttpEntity<String>(providerTemp,headers);
        String url= EnvUtil.getVal("PAASOS_DEPEND_APIGDSALTPROVIDER01_URL");
        url+="/add/temp";
        String message = restTemplate.postForObject(url, formEntity, String.class);
        if(StringUtils.isEmpty(message)){
            result.setResult(ResultMessage.Fail);
            result.setMessage("操作失败,后台未返回结果!");
        }else{
            result = JSON.parseObject(message,ResultMessage.class);
        }
        return result;
    }
}
