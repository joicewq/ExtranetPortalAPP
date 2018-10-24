package cn.gov.tjp.app.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Configuration;
import freemarker.template.Template;
/**
 * 利用freemarker实现页面静态化
  * Description
  * @author Lovercy
  * @version 1.0
  * @date 2016年4月20日
  * Copyright 青海粮食云项目组
 */
@Service("freeMarkerService")
public class FreeMarkerService {

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    
    public void processTemplate(String templateName, Map<String,Object> root, String filePath) throws Exception{
        Configuration config = freeMarkerConfigurer.getConfiguration();
        //获得模板  
        Template template=config.getTemplate(templateName);
        //输出对象 具体输出文件的路径
        File file=new File(filePath);
        Writer out = new OutputStreamWriter(new FileOutputStream(file),"UTF-8");
        //生成文件（这里是我们是生成html）  
        template.process(root, out); 
        
        IOUtils.closeQuietly(out);
    }
}
