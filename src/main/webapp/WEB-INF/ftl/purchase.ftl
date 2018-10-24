<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<meta http-equiv="X-UA-Compatible" content="IE=9" />
<title>青海粮食局-采购信息列表</title>
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
<script src="/js/ftl/purchase.js"></script>
<script src="/js/ftl/cityLeft_sd.js"></script>
</head>
<body>
	<#include "header.ftl" parse=true encoding="utf-8">
	<div class="ds-main ds-width-1000" id="purchaseListLoad">
        <!--left  begin-->
        <div class="ds-left-menu">
            <div class="ds-food-list text-center">
                <dl class="ds-menu-con" id="grainPurchaseType">
                    <dt style="cursor:pointer;" onclick="showGrain()">粮食类型</dt>
                    <dd><a href="javascript:submitData.purchaseLabel='';forEachList();" class="ds-area-a">全部</a></dd>
                    <#list purchaseGrainList as purchaseGrain>
                    <dd><a href="javascript:submitData.purchaseLabel='${purchaseGrain.grainCode}';forEachList();" class="ds-area-a">${purchaseGrain.typeName}</a></dd>
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
              <#list purchasePb.list as purchase>
              <div class="ds-search-con">
              	<div class="ds-qy-logo"><a href="javascript:forDetails('${purchase.purchaseId}')">
              	<#if purchase.fileUrl?exists && purchase.fileUrl?length gt 0>
          		<img src="${picPath}${purchase.fileUrl}" width="100%" />
          		<#else>
          		<img src="../images/pic-2.jpg" width="100%" />
          		</#if>
              	</a></div>
              	<dl class="ds-search-intro">
                	<dt onclick="forDetails('${purchase.purchaseId}')" style="cursor:pointer;" class="font16 ds-font-orange"><span class="ds-font-green">[采购]</span>${purchase.purchaseTitle}</dt>
                 	<dd style="margin-right: 2%;">
                 	<#if purchase.purchaseIntro?exists>
                 		<#if purchase.purchaseIntro?length gt 100>
                 			${purchase.purchaseIntro?substring(0,100)}......
                 		<#else>
                 			${purchase.purchaseIntro}
                 		</#if>
                 	<#else>
                 	</#if>
                 	</dd>
                 	<p class="ds-fc-c0 font12 font-arial">
                    	<span class="mr30">更新：${purchase.modifyDate?substring(0,10)}</span>
                        <span class="mr30">电话：
                        <#if purchase.telephone?exists>
                        ${purchase.telephone}
                        <#else>
                        </#if>
                        </span>
                        <span class="mr30">地址：
                        <#if purchase.address?exists>
                        ${purchase.address}
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
				 	<#if purchasePb.pageNum==1>
						<li><a>首页</a></li>
					<#else>
				 		<li><a href="javascript:forEachList(1,${purchasePb.pageSize});">首页</a></li>
                    </#if>
				 	
				  	<#if purchasePb.pageNum gt 1>
						<li><a href="javascript:forEachList(${purchasePb.prePage},${purchasePb.pageSize});">上一页</a></li>
					<#else>
						<li><a href="javascript:void(0);">上一页</a></li>
					</#if>
					
                    <#if purchasePb.pageNum==purchasePb.pages && purchasePb.pages gt 2>
						<li><a href="javascript:forEachList(${purchasePb.prePage-1},${purchasePb.pageSize});">${purchasePb.prePage-1}</a></li>
					</#if>
                    <#if purchasePb.pageNum gt 1>
						<li><a href="javascript:forEachList(${purchasePb.prePage},${purchasePb.pageSize});">${purchasePb.prePage}</a></li>
					</#if>
					<#if purchasePb.pageNum lt 1>
						<li><a  class="active">1</a></li>
					<#else>
						<li><a class="active">${purchasePb.pageNum}</a></li>
					</#if>
					<#if purchasePb.pageNum lt purchasePb.pages>
						<li><a href="javascript:forEachList(${purchasePb.nextPage},${purchasePb.pageSize});">${purchasePb.nextPage}</a></li>
					</#if>
				 	<#if purchasePb.pageNum==1 && purchasePb.pages gt 2>
						<li><a href="javascript:forEachList(${purchasePb.nextPage+1},${purchasePb.pageSize});">${purchasePb.nextPage+1}</a></li>
					</#if>
					
					<#if purchasePb.pageNum lt purchasePb.pages>
						<li><a href="javascript:forEachList(${purchasePb.nextPage},${purchasePb.pageSize});">下一页</a></li>
					<#else>
						<li><a href="javascript:void(0);">下一页</a></li>
					</#if>
					
					<#if purchasePb.pageNum==purchasePb.pages>
						<li><a>尾页</a></li>
					<#else>
                    	<li><a href="javascript:forEachList(${purchasePb.pages},${purchasePb.pageSize});">尾页</a></li>
                    </#if>
                    <#if purchasePb.pages==0>
                    	<li>共1页,到第</li>
                    <#else>
                    	<li>共${purchasePb.pages}页，到第</li>
                    </#if>
                    	<li><input type="text"  id="pageNum" onchange="pageSetting(${purchasePb.pages},false)" onkeyup="pageSetting(${purchasePb.pages},true)" class="input-page"/></li>
                    	<li>页</li>
                    	<li><a href="javascript:forEachList($('#pageNum').val(),${purchasePb.pageSize})" class="active hover">确定</a></li>
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
         			<div class='ds-qy-logo'><a href='javascript:forDetails('{$T.row.purchaseId}')'><img src='{$T.row.fileUrl}' width='100%' /></a></div>
         			<dl class='ds-search-intro'>
         				<dt onclick="forDetails('{$T.row.purchaseId}')" style='cursor:pointer;' class='font16 ds-font-orange'><span class='ds-font-green'>[采购]</span>{$T.row.purchaseTitle}</dt>
         				<dd style=' margin-right: 2%;'>{$T.row.purchaseIntro}</dd>
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
                	<li><input type="text"  id="pageNum" onchange="pageSetting({$T.pages},false)" onkeyup="pageSetting({$T.pages},true)" class="input-page" /></li>
                	<li>页</li>
                	<li><a href="javascript:forEachList($('#pageNum').val(),{$T.pageSize})" class="active hover">确定</a></li>
                </ul>
		]]>
	</textarea>
	<#include "footer.ftl" parse=true encoding="utf-8">
</body>
<script>
	$(document).ready(function(){
		$("#buy").attr("class","ds-nav-li-active");
		$("#serachType").append("<option value='buy' selected='selected'>搜采购</option>");
		$("#serachType").attr("disabled",true);
		initHotSpot(hotSpotSys.buy);
		$("#hotSpot").html(cookieText);
	});
</script>
</html>