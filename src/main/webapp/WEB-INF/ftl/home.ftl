<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<meta http-equiv="X-UA-Compatible" content="IE=9" />
<title>青海粮食局-电子商务首页</title>
<link rel="stylesheet" href="/css/layout.css"/>
<link rel="stylesheet" href="/css/main.css"/>
<link rel="stylesheet" href="/fontawesome/css/font-awesome.min.css"/>
<script src="/common/jquery-1.8.3.min.js"></script>
<script>
	var himPath="${himPath}";
	var umPath="${umPath}";
	var cimPath="${cimPath}";
	var sdimPath="${sdimPath}";
	var picPath="${picPath}";
	var companys=${companyStr};
	var firstPic=${firstPic};
	var secondPic=${secondPic};
</script>
<script src="/js/ftl/header.js"></script>
<script src="/js/ftl/home.js"></script>
<script src="/js/ftl/lunBo.js"></script>
</head>
<body>
	<#include "header.ftl" parse=true encoding="utf-8">
	<div class="ds-main ds-width-1000" id="homeBox">
		<div id="picplayer1" style="position:relative;overflow:hidden;width:1200px;height:380px;clear:none;border:solid 1px #ccc;margin:0 auto" >
		</div>
		<div class="ds-urlbar">
			<ul class="ds-urlbar-ul clearfix">
				<li class="ds-shopping ds-urlbar-li">
					<a href="${umPath}/html/purchaseEdit.html" target="_blank" class="ds-urlbar-a">
						<span class="ds-urlbar-span active ds-bg-shopping">我要采购</span>
					</a>
				</li>
				<li class="ds-sell ds-urlbar-li ml15 mr15">
					<a href="${umPath}/html/sellEdit.html" target="_blank" class="ds-urlbar-a">
						<span class="ds-urlbar-span ds-bg-sell">我要出售</span>
					</a>
				</li>
				<li class="ds-regsiter ds-urlbar-li" class="regsiter-a">
					<a href="${cimPath}/html/company.add.html" target="_blank" class="ds-urlbar-a">
						<span class="ds-urlbar-span ds-bg-reg">企业注册</span>
					</a>
				</li>
			</ul>
		</div>

		<div class="ds-commodity clearfix">
			<div class="ds-commodity-info pull-left">
				<h2 class="ds-info-title font18">
					最新供应信息
					<a href="${sdimPath}/html/sellList.html" target="_blank" class="ds-more pull-right font12">MORE>></a>
				</h2>
				<div class="ds-commodity-list mt10">
					<ul class="ds-commodity-ul clearfix">
						<#list sellList as sell>
							<li class="ds-commodity-li pull-left" onclick="window.open('${sdimPath}/html/sellDetails.html?saleId=${sell.saleId}')">
							<#if sell.fileUrl?exists && sell.fileUrl?length gt 0>
							<img src="${picPath}${sell.fileUrl}" width="160px" height="160px"/>
							<#else>
							<img src="../images/pic-160-01.jpg" width="100%" />
							</#if>
							<p class="ds-small-title font12" style="overflow: hidden; text-overflow:ellipsis;white-space:nowrap;width: 160px;" title="${sell.saleTitle}">${sell.saleTitle}</p>
							<span class="ds-msg ds-msg-green"></span>
							<span class="ds-msg-text">供应</span>
							</li>
						</#list>
					</ul>
				</div>
			</div>
			<div class="ds-notice pull-left">
				<h2 class="ds-info-title font18">
					公告
					<a href="/html/noticeList.html" target="_blank"  class="ds-more pull-right font12">MORE>></a>
				</h2>
				<ul id="noticeList" class="ds-notice-list font12" style="height: 222px;" >
					<#list noticeList as notice>
						<li class="ds-notice-li">
							<a href="/html/noticeInfo.html?id=${notice.notId}" target="_blank" class="ds-text-nowrap ds-font-simsun"><span class="text-red">[公告]</span>${notice.notTitle}</a>
						</li>
					</#list>
				</ul>
			</div>
			<div class="ds-commodity-info pull-left ds-commodity">
				<h2 class="ds-info-title font18">
					最新采购信息
					<a href="${sdimPath}/html/purchaseList.html" target="_blank" class="ds-more pull-right font12">MORE>></a>
				</h2>
				<div class="ds-commodity-list mt10">
					<ul class="ds-commodity-ul clearfix">
						<#list purchaseList as purchase>
							<li class="ds-commodity-li pull-left" onclick="window.open('${sdimPath}/html/purchaseDetails.html?purchaseId=${purchase.purchaseId}')">
							<#if purchase.fileUrl?exists && purchase.fileUrl?length gt 0>
							<img src="${picPath}${purchase.fileUrl}" width="160px" height="160px"/>
							<#else>
							<img src="../images/pic-160-01.jpg" width="100%" />
							</#if>
							<p class="ds-small-title font12" style="overflow: hidden; text-overflow:ellipsis;white-space:nowrap;width: 160px;" title="${purchase.purchaseTitle}">${purchase.purchaseTitle}</p>
							<span class="ds-msg ds-msg-green"></span>
							<span class="ds-msg-text">采购</span>
						    </li>
						</#list>
					</ul>
				</div>
			</div>
		</div>

		<div class="ds-company clearfix">
			<h2 class="ds-info-title font18 mb10 pull-left">
				最新企业
				<a href="${cimPath}/html/company.list.html" target="_blank" class="ds-more pull-right font12">MORE>></a>
			</h2>
			<#if companyList?size gt 0>
			<div class="ds-company-list pull-left">
				<ul id="companys">
					<#list companyList as company>
						<li class="ds-company-img" onclick="changeCompany('${company.companyId}')">
						<#if company.logoUrl?exists && company.logoUrl?length gt 0>
						<img src="${picPath}${company.logoUrl}" width="100%" />
						<#else>
						<img src="../images/bd.png" width="100%" />
						</#if>
						</li>
					</#list>
				</ul>
			</div>
			<div class="ds-product-img pull-left"><img name="logoUrl" width="260px" height="300px" src="<#if companyList[0].logoUrl?exists && companyList[0].logoUrl?length gt 0>${picPath}${companyList[0].logoUrl}<#else>../images/bd.png</#if>" onclick="window.open('${cimPath}/html/company.info.html?companyId=${companyList[0].companyId}')" /></div>
			<div class="ds-summary pull-left" id="companyInfo">
				<p class="text-orange font16 mb10" style="overflow:hidden;text-overflow:ellipsis;white-space:nowrap;">${companyList[0].companyName}</p>
				<p class="ds-text-ind" style="word-break:break-all;">${companyList[0].introduce}</p>
			</div>
			</#if>
		</div>

		<div id="picplayer2" style="position:relative;overflow:hidden;width:1200px;height:125px;clear:none;border:solid 1px #ccc;margin:0 auto;margin-top: 30px" >
		</div>

		<div class="ds-foodstuff">
			<h2 class="ds-info-title font18 mb10">
				粮食类型
			</h2>
			<div class="ds-food-list">
				<h2 class="font16 ml5" style="color:#ff7800;">
					供应
				</h2>
				<ul class="ds-food-list-con mb10 clearfix" id="sellGrains">
					<#list grainList as grain>
					<#if grain_index%4==0>
					<li>
					</#if>
					<#if grain_index%2==0>
					<ul class="ds-food clearfix">
					</#if>
					<li><a href="${sdimPath}/html/sellList.html?garinType=${grain.grainCode}" target="_blank" class="ds-food-a">${grain.typeName}</a></li>
					<#if (grain_index-1)%2==0>
					</ul>
					</#if>
					<#if (grain_index-3)%4==0>	
					</li>
					</#if>
					</#list>
					<#if (grainList?size)%2==1>
					</ul>
					</li>
					</#if>
				</ul>
				<h2 class="font16 ml5" style="color:#ff7800;">
					采购
				</h2>
				<ul class="ds-food-list-con clearfix" id="buyGrains">
					<#list grainList as grain>
					<#if grain_index%4==0>
					<li>
					</#if>
					<#if grain_index%2==0>
					<ul class="ds-food clearfix">
					</#if>
					<li><a href="${sdimPath}/html/purchaseList.html?garinType=${grain.grainCode}" target="_blank" class="ds-food-a">${grain.typeName}</a></li>
					<#if (grain_index-1)%2==0>
					</ul>
					</#if>
					<#if (grain_index-3)%4==0>	
					</li>
					</#if>
					</#list>
					<#if (grainList?size)%2==1>
					</ul>
					</li>
					</#if>
				</ul>
			</div>
		</div>

		<div class="ds-area">
			<h2 class="ds-info-title font18 mb10">
				地区列表
			</h2>
			<div class="ds-area-list clearfix" id="areas">
				<dl>
				<#list areaList as area>
					<dd style="float:left;width:168px;border:1px solid #fff;"><a href="${cimPath}/html/company.list.html?area=${area.areaCode}" target="_blank" class="ds-area-a">${area.areaName}</a></dd>
				</#list>
				</dl>
			</div>
		</div>
		<br />
	</div>
	<#include "footer.ftl" parse=true encoding="utf-8">
</body>
<script>
	$(document).ready(function(){
		$("#him").attr("class","ds-nav-li-active");
		initHotSpot(hotSpotSys.him);
		$("#hotSpot").html(cookieText);
	});
</script>
</html>