<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<meta http-equiv="X-UA-Compatible" content="IE=9" />
<title>青海粮食局-供应信息列表</title>
<link rel="stylesheet" href="/css/layout.css"/>
<link rel="stylesheet" href="/css/main.css"/>
<script type="text/javascript" src="/common/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="/common/jquery-jtemplates.js"></script>
<script type="text/javascript" src="/common/My97DatePicker/WdatePicker.js"></script>
<script>
	var himPath="${himPath}";
	var umPath="${umPath}";
	var cimPath="${cimPath}";
	var sdimPath="${sdimPath}";
	var picPath="${picPath}";
	var areaStr=${areaStr};
</script>
<script src="/js/ftl/header.js"></script>
<script src="/js/ftl/sell.js"></script>
<script src="/js/ftl/cityLeft_sd.js"></script>
</head>
<body>
	<#include "header.ftl" parse=true encoding="utf-8">
	<div class="ds-main ds-width-1000" id="sellListLoad">
        <!--left  begin-->
        <div class="ds-left-menu">
            <div class="ds-food-list text-center">
                <dl class="ds-menu-con" id="grainSellType">
                    <dt style="cursor:pointer;" onclick="showGrain()">粮食类型</dt>
                    <dd><a href="javascript:submitData.saleLabel='';forEachList();" class="ds-area-a">全部</a></dd>
                    <#list sellGrainList as sellGrain>
                    <dd><a href="javascript:submitData.saleLabel='${sellGrain.grainCode}';forEachList();" class="ds-area-a">${sellGrain.typeName}</a></dd>
                    </#list>
                </dl>
            </div>
            <div class="ds-food-list text-center mt20">
                <dl class="ds-menu-con" id="cityType">
                    <dt onclick="getAreaList()" style="cursor:pointer;">省市列表</dt>
                </dl>
            </div>
        </div>
        <div class="ds-right-con" >
            <div id="table" class="ds-food-list">
              <#list sellPb.list as sell>
              <div class="ds-search-con">
              	<div class="ds-qy-logo"><a href="javascript:forDetails('${sell.saleId}')">
              		<#if sell.fileUrl?exists && sell.fileUrl?length gt 0>
              		<img src="${picPath}${sell.fileUrl}" width="100%" />
              		<#else>
              		<img src="../images/pic-2.jpg" width="100%" />
              		</#if>
              		</a></div>
              	<dl class="ds-search-intro">
                	<dt onclick="forDetails('${sell.saleId}')" style="cursor:pointer;" class="font16 ds-font-orange"><span class="ds-font-green">[供应]</span>${sell.saleTitle}</dt>
                 	<dd style="margin-right: 2%;">
                 	<#if sell.saleIntroduce?exists>
                 		<#if sell.saleIntroduce?length gt 100>
                 			${sell.saleIntroduce?substring(0,100)}......
                 		<#else>
                 			${sell.saleIntroduce}
                 		</#if>
                 	<#else>
                 	</#if>
                 	</dd>
                 	<p class="ds-fc-c0 font12 font-arial">
                    	<span class="mr30">更新：${sell.modifyDate?substring(0,10)}</span>
                        <span class="mr30">电话：
                        <#if sell.telephone?exists>
                        ${sell.telephone}
                        <#else>
                        </#if>
                        </span>
                        <span class="mr30">地址：
                        <#if sell.address?exists>
                        ${sell.address}
                        <#else>
                        </#if>
                        </span>
					</p>
				</dl>
              </div>
              </#list>
            </div>
            <!--pages begin-->
            <div id="page" class="ds-page">
            	<ul>
				 	<#if sellPb.pageNum==1>
						<li><a>首页</a></li>
					<#else>
				 		<li><a href="javascript:forEachList(1,${sellPb.pageSize});">首页</a></li>
                    </#if>
				 	
				  	<#if sellPb.pageNum gt 1>
						<li><a href="javascript:forEachList(${sellPb.prePage},${sellPb.pageSize});">上一页</a></li>
					<#else>
						<li><a href="javascript:void(0);">上一页</a></li>
					</#if>
					
                    <#if sellPb.pageNum==sellPb.pages && sellPb.pages gt 2>
						<li><a href="javascript:forEachList(${sellPb.prePage-1},${sellPb.pageSize});">${sellPb.prePage-1}</a></li>
					</#if>
                    <#if sellPb.pageNum gt 1>
						<li><a href="javascript:forEachList(${sellPb.prePage},${sellPb.pageSize});">${sellPb.prePage}</a></li>
					</#if>
					<#if sellPb.pageNum lt 1>
						<li><a class="active">1</a></li>
					<#else>
						<li><a class="active">${sellPb.pageNum}</a></li>
					</#if>
					<#if sellPb.pageNum lt sellPb.pages>
						<li><a href="javascript:forEachList(${sellPb.nextPage},${sellPb.pageSize});">${sellPb.nextPage}</a></li>
					</#if>
				 	<#if sellPb.pageNum==1 && sellPb.pages gt 2>
						<li><a href="javascript:forEachList(${sellPb.nextPage+1},${sellPb.pageSize});">${sellPb.nextPage+1}</a></li>
					</#if>
					
					<#if sellPb.pageNum lt sellPb.pages>
						<li><a href="javascript:forEachList(${sellPb.nextPage},${sellPb.pageSize});">下一页</a></li>
					<#else>
						<li><a href="javascript:void(0);">下一页</a></li>
					</#if>
					
					<#if sellPb.pageNum==sellPb.pages>
						<li><a>尾页</a></li>
					<#else>
                    	<li><a href="javascript:forEachList(${sellPb.pages},${sellPb.pageSize});">尾页</a></li>
                    </#if>
                    <#if sellPb.pages==0>
                    	<li>共1页,到第</li>
                    <#else>
                    	<li>共${sellPb.pages}页，到第</li>
                    </#if>
                    <li><input type="text"  id="pageNum" onchange="pageSetting(${sellPb.pages},false)" onkeyup="pageSetting(${sellPb.pages},true)" class="input-page"/></li>
                    <li>页</li>
                    <li><a href="javascript:forEachList($('#pageNum').val(),${sellPb.pageSize})" class="active hover">确定</a></li>
                </ul>
            </div>
        </div>
        <!--right end-->
	</div>
	<!-- 在这里写你的表格模版 -->
	<textarea id="tableTemplateTxt" style="display: none">
	<![CDATA[
			{#template MAIN}
			{#foreach $T.list as row}
				<div class='ds-search-con'>
         			<div class='ds-qy-logo'><a href='javascript:forDetails('{$T.row.saleId}')'><img src='{$T.row.fileUrl}' width='100%' /></a></div>
         			<dl class='ds-search-intro'>
         				<dt onclick="forDetails('{$T.row.saleId}')" style='cursor:pointer;' class='font16 ds-font-orange'><span class='ds-font-green'>[供应]</span>{$T.row.saleTitle}</dt>
         				<dd style=' margin-right: 2%;'>{$T.row.saleIntroduce}</dd>
         				<p class='ds-fc-c0 font12 font-arial'>
                 			<span class='mr30'>更新：{$T.row.modifyDate}</span>
                 			<span class='mr30'>电话：{$T.row.telephone}</span>
                 			<span class='mr30'>地址：{$T.row.address}</span>
						</p>
					</dl>
				</div>
			{#/for}
			{#/template MAIN}
	]]>
	</textarea>
	<!-- 表分页信息模版 -->
	<textarea id="pageMsgTemplate" style="display:none">
		<![CDATA[
				 <ul>
			 
			 	{#if $T.pageNum==1}{*如果本页是最后一页不查询*}
					<li><a>首页</a></li>
				{#else}
			 		<li><a href="javascript:forEachList(1,{$T.pageSize});">首页</a></li>
                {#/if}
			 	
			  	{#if $T.pageNum>1}
					<li><a href="javascript:forEachList({$T.prePage},{$T.pageSize});">上一页</a></li>
				{#else}{* 没有上一页*}
					<li><a href="javascript:void(0);">上一页</a></li>
				{#/if}
				
				
                {#if $T.pageNum==$T.pages &&$T.pages>2}{*如果本页等于最后页,总数大于2,补上倒数第三页*}
					<li><a href="javascript:forEachList({$T.prePage-1},{$T.pageSize});">{$T.prePage-1}</a></li>
				{#/if}
                {#if $T.pageNum>1}{*如果本页大于1,那么显示前一页*}
					<li><a href="javascript:forEachList({$T.prePage},{$T.pageSize});">{$T.prePage}</a></li>
				{#/if}
				{#if $T.pageNum < 1}
					<li><a  class="active">1</a></li>
				{#else}
					<li><a class="active">{$T.pageNum}</a></li>
				{/#if}
				
				{#if $T.pageNum<$T.pages}{*如果本页小于最后页,显示最后一页*}
					<li><a href="javascript:forEachList({$T.nextPage},{$T.pageSize});">{$T.nextPage}</a></li>
				{#/if}
			 	{#if $T.pageNum==1 &&$T.pages>2}{*如果本页等于最后页,总数大于2,补上倒数第三页*}
					<li><a href="javascript:forEachList({$T.nextPage+1},{$T.pageSize});">{$T.nextPage+1}</a></li>
				{#/if}
				
				{#if $T.pageNum<$T.pages}
					<li><a href="javascript:forEachList({$T.nextPage},{$T.pageSize});">下一页</a></li>
				{#else}{* 没有下一页*}
					<li><a href="javascript:void(0);">下一页</a></li>
				{#/if}
				
				{#if $T.pageNum==$T.pages}{*如果本页是最后一页不查询*}
					<li><a>尾页</a></li>
				{#else}
                	<li><a href="javascript:forEachList({$T.pages},{$T.pageSize});">尾页</a></li>
                {#/if}
                {#if $T.pages==0}
                	<li>共1页，到第</li>
                {#else}
                	<li>共{$T.pages}页，到第</li>
                {#/if}
                	<li><input type="text"  id="pageNum" onchange="pageSetting({$T.pages},false)" onkeyup="pageSetting({$T.pages},true)"class="input-page" /></li>
                	<li>页</li>
                	<li><a href="javascript:forEachList($('#pageNum').val(),{$T.pageSize})" class="active hover">确定</a></li>
                </ul>
		]]>
	</textarea>
	<#include "footer.ftl" parse=true encoding="utf-8">
</body>
<script>
	$(document).ready(function(){
		$("#sell").attr("class","ds-nav-li-active");
		$("#serachType").append("<option value='sell' selected='selected'>搜供应</option>");
		$("#serachType").attr("disabled",true); 
		initHotSpot(hotSpotSys.sell);
		$("#hotSpot").html(cookieText);
	});
</script>
</html>