<#setting date_format="yyyy-MM-dd"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<meta http-equiv="X-UA-Compatible" content="IE=9" />
<title>青海粮食局-电子黄页列表</title>
<link rel="stylesheet" href="/css/layout.css"/>
<link rel="stylesheet" href="/css/main.css"/>
<link rel="stylesheet" href="/fontawesome/css/font-awesome.min.css"/>
<script type="text/javaScript" src="/common/jquery-1.8.3.min.js"></script>
<script type="text/javaScript" src="/common/jquery-jtemplates.js"></script>
<script>
	var himPath="${himPath}";
	var umPath="${umPath}";
	var cimPath="${cimPath}";
	var sdimPath="${sdimPath}";
	var picPath="${picPath}";
	var areaStr=${areaStr};
</script>
<script src="/js/ftl/header.js"></script>
<script src="/js/ftl/company.js"></script>
<script src="/js/ftl/cityLeft.js"></script>
</head>
<body>
	<#include "header.ftl" parse=true encoding="utf-8">
	<div class="ds-main ds-width-1000">
		<!--左侧筛选栏-->
		<div class="ds-left-menu">
			<div class="ds-food-list text-center">
                <dl class="ds-menu-con text-center" name="companyRelateGrains" id="grainList">
	                <dt style="cursor:pointer;" onclick="showGrain()">粮食类型</dt>
	                <dd><a href="javascript:selectByCondition('',null);" class="ds-area-a">全部</a></dd>
	                <#list companyGrainList as companyGrain>
	                <dd><a href="javascript:selectByCondition('${companyGrain.grainCode}',null);" class="ds-area-a">${companyGrain.typeName}</a></dd>
	                </#list>
                </dl>
            </div>
            <div class="ds-food-list text-center mt20">
                <dl class="ds-menu-con" id="cityType">
                    <dt id="areasShow" style="cursor:pointer;">省市列表</dt>
                    <dd style="display:none;" name=""><a href="javascript:selectByCondition(null,'');" class="ds-area-a">全部</a></dd>
                </dl>
            </div>
		</div>
		<!--右侧内容展示-->
		<div class="ds-right-con">
			<div id="table" class="ds-food-list">
				<#list companyPb.list as company>
				<div class="ds-search-con">
					<div class="ds-qy-logo">
						<a  target="_blank"  href="${cimPath}/html/company.info.html?companyId=${company.companyId}">
						<#if company.logoUrl?exists && company.logoUrl?length gt 0>
						<img src="${picPath}${company.logoUrl}" width="100%">
						<#else>
						<img src="../images/pic-1.jpg" width="100%">
						</#if></a>
					</div>
					<dl class="ds-search-intro">                                          
					    <dt class="text-overflow"><a class="font16 ds-font-orange" target="_blank" href="${cimPath}/html/company.info.html?companyId=${company.companyId}">${company.companyName}</a></dt>
					    <dd class="text-overflow">
					    <#if company.introduce?exists>
					    	${company.introduce}
					    </#if>
					    </dd>  
					    <p class="ds-fc-c0 font12 font-arial">                            
					        <span class="mr30">创建：
							<#if company.createDate?exists>
								${company.createDate?number_to_date}
							</#if>
							</span>                        
					        <span class="mr30">电话：
					        <#if company.areaCode?exists>
					        ${company.areaCode}-
					        </#if>
					        ${company.telephone}
					        </span>                     
					        <span class="mr30">地址：${company.companyAddress}</span>                   
					    </p>                                                                
					</dl>                                                                   
				</div>
				</#list>
			</div>
			<div id="page" class="ds-page">
				<ul>
				<#if companyPb.pageNum==1>
					<li><a>首页</a></li>
				<#else>
					<li><a href="javascript:showTemplateTable(1,${companyPb.pageSize});">首页</a></li>
				</#if>
				
				<#if companyPb.pageNum gt 1>
					<li><a href="javascript:showTemplateTable(${companyPb.prePage},${companyPb.pageSize});">上一页</a></li>
				<#else>
					<li><a href="javascript:void(0);">上一页</a></li>
				</#if>
				<#if companyPb.pageNum==companyPb.pages && companyPb.pages gt 2>
					<li><a href="javascript:showTemplateTable(${companyPb.prePage-1},${companyPb.pageSize});">${companyPb.prePage-1}</a></li>
				</#if> 
				<#if companyPb.pageNum gt 1>
					<li><a href="javascript:showTemplateTable(${companyPb.prePage},${companyPb.pageSize});">${companyPb.prePage}</a></li> 
				</#if>
				<#if companyPb.pageNum lt 1>
					<li><a class="active">1</a></li>
				<#else>
					<li><a class="active">${companyPb.pageNum}</a></li>
				</#if>
				<#if companyPb.pageNum lt companyPb.pages>
					<li><a href="javascript:showTemplateTable(${companyPb.nextPage},${companyPb.pageSize});">${companyPb.nextPage}</a></li> 
				</#if>
				<#if companyPb.pageNum==1 && companyPb.pages gt 2>
					<li><a href="javascript:showTemplateTable(${companyPb.nextPage+1},${companyPb.pageSize});">${companyPb.nextPage+1}</a></li> 
				</#if>
				<#if companyPb.pageNum lt companyPb.pages>
					<li><a href="javascript:showTemplateTable(${companyPb.nextPage},${companyPb.pageSize});">下一页</a></li>
				<#else>
					<li><a href="javascript:void(0);">下一页</a></li>
				</#if>
				<#if companyPb.pageNum==companyPb.pages>
					<li><a>尾页</a></li>
				<#else>
					<li><a href="javascript:showTemplateTable(${companyPb.pages},${companyPb.pageSize});">尾页</a></li>
				</#if>
				<#if companyPb.pages==0>
					<li>共1页，到第</li><input type="hidden" id="pages" value="1"/>
				<#else>
					<li>共${companyPb.pages}页，到第</li><input type="hidden" id="pages" value="${companyPb.pages}"/>
				</#if>
					<li><input type="text" id="pageNum" onchange="pageSetting(${companyPb.pages},false)" onkeyup="pageSetting(${companyPb.pages},true)" class="input-page"/></li>
					<li>页</li>
					<li><a href="javascript:showTemplateTable(-1,${companyPb.pageSize})" class="active hover">跳转</a></li>
				</ul>
			</div>
		</div>
	</div>
	<!-- 	在这里写你的表格模版 -->
	<textarea id="tableTemplateTxt" style="display: none">
	<![CDATA[
			{#template MAIN}
			{#foreach $T.list as row}
				<div class="ds-search-con">
					<div class="ds-qy-logo">
						<a href="javascript:forDetail('{$T.row.companyId}')">
						{#if $T.row.logoUrl}
					 		<img name="imgUrl" src="{$T.row.logoUrl}" width="100%">
						{#else}
							<img src="../images/pic-1.jpg" width="100%">
	                    {#/if}
						</a>
					</div>
					<dl class="ds-search-intro">                                          
					    <dt class=" text-overflow "><a  class="font16 ds-font-orange" href="javascript:forDetail('{$T.row.companyId}')">{$T.row.companyName}</a></dt>
					    <dd class=" text-overflow ">{$T.row.introduce}</dd>  
					    <p class="ds-fc-c0 font12 font-arial">                            
					        <span class="mr30">创建：
							{#if $T.row.createDate==null}
							{#else }{timeStamp2String($T.row.createDate)}
							{#/if}
							</span>                        
					        <span class="mr30">电话：{$T.row.areaCode}-{$T.row.telephone}</span>                     
					        <span class="mr30">地址：{$T.row.companyAddress}</span>                   
					    </p>                                                                
					</dl>                                                                   
				</div>
			{#/for}
			{#/template MAIN}
	]]>
	</textarea>
	<textarea id="pageMsgTemplate" style="display:none">
		<![CDATA[
				 <ul>
			 
			 	{#if $T.pageNum==1}{*如果本页是最后一页不查询*}
					<li><a>首页</a></li>
				{#else}
			 		<li><a href="javascript:javascript:showTemplateTable(1,{$T.pageSize});">首页</a></li>
                {#/if}
			 	
			  	{#if $T.pageNum>1}
					<li><a href="javascript:javascript:showTemplateTable({$T.prePage},{$T.pageSize});">上一页</a></li>
				{#else}{* 没有上一页*}
					<li><a href="javascript:void(0);">上一页</a></li>
				{#/if}
				
				
                {#if $T.pageNum==$T.pages &&$T.pages>2}{*如果本页等于最后页,总数大于2,补上倒数第三页*}
					<li><a href="javascript:javascript:showTemplateTable({$T.prePage-1},{$T.pageSize});">{$T.prePage-1}</a></li>
				{#/if}
                {#if $T.pageNum>1}{*如果本页大于1,那么显示前一页*}
					<li><a href="javascript:javascript:showTemplateTable({$T.prePage},{$T.pageSize});">{$T.prePage}</a></li>
				{#/if}
				{#if $T.pageNum < 1}
					<li><a  class="active">1</a></li>
				{#else}
					<li><a class="active">{$T.pageNum}</a></li>
				{/#if}
				
				{#if $T.pageNum<$T.pages}{*如果本页小于最后页,显示最后一页*}
					<li><a href="javascript:javascript:showTemplateTable({$T.nextPage},{$T.pageSize});">{$T.nextPage}</a></li>
				{#/if}
			 	{#if $T.pageNum==1 &&$T.pages>2}{*如果本页等于最后页,总数大于2,补上倒数第三页*}
					<li><a href="javascript:javascript:showTemplateTable({$T.nextPage+1},{$T.pageSize});">{$T.nextPage+1}</a></li>
				{#/if}
				
				{#if $T.pageNum<$T.pages}
					<li><a href="javascript:javascript:showTemplateTable({$T.nextPage},{$T.pageSize});">下一页</a></li>
				{#else}{* 没有下一页*}
					<li><a href="javascript:void(0);">下一页</a></li>
				{#/if}
				
				{#if $T.pageNum==$T.pages}{*如果本页是最后一页不查询*}
					<li><a>尾页</a></li>
				{#else}
                	<li><a href="javascript:javascript:showTemplateTable({$T.pages},{$T.pageSize});">尾页</a></li>
                {#/if}
                {#if $T.pages==0}
                	<li>共1页，到第</li><input type="hidden" id="pages" value="1"/>
                {#else}
                	<li>共{$T.pages}页，到第</li><input type="hidden" id="pages" value="{$T.pages}"/>
                {#/if}
                	<li><input type="text"  id="pageNum" onchange="pageSetting({$T.pages},false)" onkeyup="pageSetting({$T.pages},true)" class="input-page" /></li>
                	<li>页</li>
                	<li><a href="javascript:javascript:showTemplateTable($('#pageNum').val(),{$T.pageSize})" class="active hover">确定</a></li>
                </ul>
		]]>
	</textarea>
	<#include "footer.ftl" parse=true encoding="utf-8">
<body>
<script>
	$(document).ready(function(){
		$("#cim").attr("class","ds-nav-li-active");
		$("#serachType").attr("disabled",true);
		initHotSpot(hotSpotSys.cim);
		$("#hotSpot").html(cookieText);
	});
</script>
</html>