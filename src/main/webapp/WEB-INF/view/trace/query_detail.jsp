<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<style type="text/css">
.table-bordered td{
	padding-left: 10px;
	text-align:left;
}
</style>

<!-- 二维码正常，获取到数据 -->

<h2 class="text-light-blue mt20 mb10 font16"><i class="iconfont icon-linecomputer"></i> 查询结果
	<c:if test="${'1' eq state }" >
		<span class="pull-right ds-text-gray font16">本产品已被查询 <span class="ds-text-red">${queryLogInfo.queryTotal }</span> 次，
		<span class="ds-text-red"> ${queryLogInfo.firstTime } </span>第一次查询</span>
	</c:if>
</h2>
<div class="bg-light-blue normal-module">
	<c:if test="${'1' eq state }" >
		<div class="ds-bg-white p25">
			<!-- 产品信息 -->
			<div>
				<h2 class="report-module-h2">产品信息</h2>
				<table class="table data-table table-bordered table-fixed">
					<tr>
						<td class="ds-bg-gray text-right">商品条形码</td>
						<td colspan="3">${produceInfo.productBarCode }</td>
					</tr>
					<tr>
						<td class="ds-bg-gray text-right" style="width:100px">商品名称</td>
						<td style="width:350px">${produceInfo.produceName }</td>
						<td class="ds-bg-gray text-right" style="width:100px">产品类别</td>
						<td style="width:350px">${produceInfo.category }</td>
					</tr>
					<tr>
						<td class="ds-bg-gray text-right">品牌</td>
						<td>${produceInfo.brand }</td>
						<td class="ds-bg-gray text-right">产品规格</td>
						<td>${produceInfo.spec }</td>
					</tr>
					<tr>
						<td class="ds-bg-gray text-right">生产日期</td>
						<td>${produceInfo.produceDate }</td>
						<td class="ds-bg-gray text-right">保质期</td>
						<td>${produceInfo.overdueVal }</td>
					</tr>
				</table>
			</div>
			
			<!-- 原料进购信息 -->
			<div class="mt30">
				<h2 class="report-module-h2">原料进购信息</h2>
				<c:if test="${empty materialList }">
				<table class="table data-table table-bordered table-fixed">
					<tr>
						<td class="ds-bg-gray text-right" style="width:100px">供应商名称</td>
						<td style="width:350px"></td>
						<td class="ds-bg-gray text-right" style="width:100px">原料名称</td>
						<td style="width:350px"></td>
					</tr>
					<tr>
						<td class="ds-bg-gray text-right">原料批次</td>
						<td></td>
						<td class="ds-bg-gray text-right">原料生产厂家</td>
						<td></td>
					</tr>
					<tr>
						<td class="ds-bg-gray text-right">进货日期</td>
						<td></td>
						<td class="ds-bg-gray text-right">进货数量</td>
						<td></td>
					</tr>
					<tr>
						<td class="ds-bg-gray text-right">包装单位</td>
						<td></td>
						<td class="ds-bg-gray text-right">食盐辅料</td>
						<td></td>
					</tr>
					<tr>
						<td class="ds-bg-gray text-right">
						原料检测报告文件
						</td>
						<td colspan="3"></td>
					</tr>
				</table>
				</c:if>
				
				<c:if test="${not empty materialList }">
				  <c:forEach items="${materialList }" var="material">
				  
				  <table class="table data-table table-bordered table-fixed">
					<tr>
						<td class="ds-bg-gray text-right" style="width:100px">供应商名称</td>
						<td style="width:350px">${material.supplier }</td>
						<td class="ds-bg-gray text-right" style="width:100px">原料名称</td>
						<td style="width:350px">${material.materialName }</td>
					</tr>
					<tr>
						<td class="ds-bg-gray text-right">原料批次</td>
						<td>${material.batch }</td>
						<td class="ds-bg-gray text-right">原料生产厂家</td>
						<td>${material.manufacturer }</td>
					</tr>
					<tr>
						<td class="ds-bg-gray text-right">进货日期</td>
						<td>${material.produceDate }</td>
						<td class="ds-bg-gray text-right">进货数量</td>
						<td>${material.total }</td>
					</tr>
					<tr>
						<td class="ds-bg-gray text-right">包装单位</td>
						<td>${material.unit }</td>
						<td class="ds-bg-gray text-right">食盐辅料</td>
						<td>${material.saltMaterials }</td>
					</tr>
					<tr>
						<td class="ds-bg-gray text-right">原料检测报告文件</td>
						<td colspan="3">
						<a href="/trace/downloadFile?dataType=ProductMaterialBuy&id=${material.id }">
						${material.checkReportFileName }
						</a>
						</td>
					</tr>
				</table>
				  </c:forEach>
				</c:if>
				
				
				
				
				
			</div>
			
			<!-- 生产追溯信息 -->
			<div class="mt30">
				<h2 class="report-module-h2">生产追溯信息</h2>
				<table class="table data-table table-bordered table-fixed">
					<tr>
						<td class="ds-bg-gray text-right" style="width:100px">生产条形码</td>
						<td style="width:350px">${trace.productBarCode }</td>
						<td class="ds-bg-gray text-right" style="width:100px">生产批次</td>
						<td style="width:350px">${trace.batch }</td>
					</tr>
					<tr>
						<td class="ds-bg-gray text-right">生产数量</td>
						<td>${trace.total }</td>
						<td class="ds-bg-gray text-right">追溯码</td>
						<td>${trace.traceCode }</td>
					</tr>
					<tr>
						<td class="ds-bg-gray text-right">包装</td>
						<td>${trace.packingVal }</td>
						<td class="ds-bg-gray text-right"></td>
						<td></td>
					</tr>
					<tr>
						<td class="ds-bg-gray text-right">成品检测扫描文件</td>
						<td colspan="3">
						<a href="/trace/downloadFile?dataType=ProductFinishedCheck&id=${trace.checkId }">
						${trace.fileName }
						</a>
						</td>
					</tr>
				</table>
			</div>
			
			<!-- 生产追溯信息 -->
			<div class="mt30">
				<h2 class="report-module-h2">销售信息</h2>
				<table class="table data-table table-bordered table-fixed">
					<tr>
						<td class="ds-bg-gray text-center" style="width:180px;">销售日期</td>
						<td>${sale.saleDate }</td>
					</tr>
					<tr>
						<td class="ds-bg-gray text-center">销售人</td>
						<td>${sale.managerName }</td>
					</tr>
					<tr>
						<td class="ds-bg-gray text-center">联系方式</td>
						<td>${sale.contact }</td>
					</tr>
					<tr>
						<td class="ds-bg-gray text-center">购销企业名称</td>
						<td>${sale.purchaseEnterpriseName }</td>
					</tr>
					<tr>
						<td class="ds-bg-gray text-center">购销企业许可证编码</td>
						<td>${sale.purchaseEnterprisePermitNumber }</td>
					</tr>
					<tr>
						<td class="ds-bg-gray text-center">购销企业所在地区</td>
						<td>${sale.purchaseEnterpriseProvince }</td>
					</tr>
					<tr>
						<td class="ds-bg-gray text-center">购销企业所在城市</td>
						<td>${sale.purchaseEnterpriseCity }</td>
					</tr>
					<tr>
						<td class="ds-bg-gray text-center">购销企业详细地址</td>
						<td>${sale.purchaseEnterpriseAdress }</td>
					</tr>
				</table>
			</div>
			
			<!-- 生产追溯信息 -->
			<div class="mt30">
				<h2 class="report-module-h2">物流信息</h2>
				<table class="table data-table table-bordered table-fixed">
					<tr>
						<td class="ds-bg-gray text-right" style="width:100px">物流厂家</td>
						<td style="width:350px"></td>
						<td class="ds-bg-gray text-right" style="width:100px">物流编号</td>
						<td style="width:350px"></td>
					</tr>
				</table>
			</div>
		</div>
	</c:if>
	<c:if  test="${'2' eq state }">
		<div class="text-center font16">该二维码获取不到数据，请检查二维码</div>
	</c:if>
</div>

