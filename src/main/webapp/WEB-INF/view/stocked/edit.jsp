<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/static/common/jstl.jsp"%>
<%@ include file="/static/common/import.jsp"%>
<!DOCTYPE html>
<html lang="en-us">
<head>
<meta charset="utf-8">
<title>储备管理</title>
<link rel="Shortcut Icon" href="/static/images/jgqt.ico">
<meta name="description" content="">
<meta name="author" content="">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link rel="stylesheet" href="${ctx}/static/js/layer/skin/default/layer.css"/>
<style type="text/css">
		#appearIds #loaded1,#checkIds #loaded2{
			width: 649px;
		    margin-left: 100px;
		}
		#appearIds #loaded1 div:nth-of-type(3),#checkIds #loaded2 div:nth-of-type(3){
			margin-top: -30px;
		}
	</style>
<script type="text/javascript">
	var  companyId = "${companyId}";
</script>
<%-- <script type="text/javascript" src="${ctx}/static/js/require.js" data-main="${ctx}/static/model/prodlib/edit.js"></script> --%>
<script type="text/javascript" src="${ctx}/static/model/stocked/edit.js"></script>
</head>
<body>
<!-- 	<div class="ds-main">
		<div class="user-main mt10 clearfix">
			
			<div class="user-right"> -->
				<form id="supply-form">
					<ul class="form-list">
					<input type="hidden" id="id" name="id"  value="${stockedInfo.id }">
					<input type="hidden" id="stocekOrder" name="stocekOrder"  value="${stockedInfo.stocekOrder }">
					<input type="hidden" id="stocekCode" name="stocekCode"  value="${stockedInfo.stocekCode }">
						<li>
							<div class="label">
								<span class="sm-title"><span class="text-red mr5">*</span>企业名称：</span>
								<select name="companyName" id="companyName">
									<option value="">请选择</option>
									<c:forEach var="supplier" items="${supplierList}">
										<option value="${supplier.companyId}" <c:if test="${supplier.companyName eq stockedInfo.companyName}">selected</c:if>>${stockedInfo.companyName}</option>
									</c:forEach>
								</select>
							</div>
						</li>
						<li>
							<div class="label">
								<span class="sm-title" >标题：</span>
								<input type="text" class="required"   id="title" name="title" value="${stockedInfo.title }">
							</div>
						</li>
						<li>
							<div class="label">
								<span class="sm-title" style="white-space: nowrap;">食盐月均销售量 /预销量：</span>
								<input type="text" class="required"   id="saleNumber" name="saleNumber" value="${stockedInfo.saleNumber }">
							</div>
						</li>
						<li>
							<div class="label">
								<span class="sm-title"  >联系人：</span>
								<input type="text" class="required"   id="contactPerson" name="contactPerson" value="${stockedInfo.contactPerson }">
							</div>
						</li>
						<li>
							<div class="label">
								<span class="sm-title"  >食盐储备任务量：</span>
								<input type="text" class="required"   id="saleTaskNumber" name="saleTaskNumber" value="${stockedInfo.saleTaskNumber }">
							</div>
						</li>
						<li>
							<div class="label">
								<span class="sm-title"  >联系电话：</span>
								<input type="text" class="required"   id="contactPhone" name="contactPhone" value="${stockedInfo.contactPhone }">
							</div>
						</li>
						<li>
							<div class="label">
								<span class="sm-title"  >储备仓库地址：</span>
								<input type="text" class="required"   id="stockedAddress" name="stockedAddress" value="${stockedInfo.stockedAddress }">
							</div>
						</li>
						<li>
							<div class="label">
								<span class="sm-title" >类型：</span>
								<select name="type" id="type" value="${stockedInfo.type }">
									<option value="">请选择</option>
									<option value="10" <c:if test="${stockedInfo.type eq '原料仓'}">selected</c:if>>原料仓</option>
									<option value="20" <c:if test="${stockedInfo.type eq '成品仓'}">selected</c:if>>成品仓</option>
									<option value="30" <c:if test="${stockedInfo.type eq '辅料仓'}">selected</c:if>>辅料仓</option>
									<option value="41" <c:if test="${stockedInfo.type eq '废品仓'}">selected</c:if>>废品仓</option>
									<option value="51" <c:if test="${stockedInfo.type eq '代管仓'}">selected</c:if>>代管仓</option>
									<option value="60" <c:if test="${stockedInfo.type eq '外租仓'}">selected</c:if>>销售仓</option>
									<option value="90" <c:if test="${stockedInfo.type eq '外租仓'}">selected</c:if>>外租仓</option>
									<option value="81" <c:if test="${stockedInfo.type eq '储备仓'}">selected</c:if>>储备仓</option>
									<option value="*2" <c:if test="${stockedInfo.type eq '公用仓'}">selected</c:if>>公用仓</option>
								</select>
							</div>
						</li>
						<li>
							<div class="label">
								<span class="sm-title"  >仓库容量：</span>
								<input type="text" class="required"   id="stockedCapacity" name="stockedCapacity" value="${stockedInfo.stockedCapacity }">
							</div>
						</li>
						<li>
							<div class="label">
								<span class="sm-title"  >邮政编码：</span>
								<input type="text" class="required"   id="postalCode" name="postalCode" value="${stockedInfo.postalCode }">
							</div>
						</li>
						<li>
							<div class="label">
								<span class="sm-title"  >仓库最大库存量：</span>
								<input type="text" class="required"   id="stockedMaxCapacity" name="stockedMaxCapacity" value="${stockedInfo.stockedMaxCapacity }">
							</div>
						</li>
						<li id="isBizStatusShow">
							<div class="label">
								<span class="sm-title" >来源：</span>
								<input type="hidden" id="comeResource"  value="${stockedInfo.comeResource }"/>
								<input type="radio" checked="true" name="comeResource" id="comeResource1" value="1"/>&nbsp;广东省&nbsp;&nbsp;&nbsp;
								<input type="radio" name="comeResource" id="comeResource2" value="0"/>&nbsp;省内
							</div>
						</li>
						<li id="isBizStatusShow">
								<div class="label">
								<span class="sm-title" >仓库名称：</span>
								<input type="text" class="required"   id="stockedName" name="stockedName" value="${stockedInfo.stockedName }">
							</div>
						</li>
					</ul>
					<div class="text-center mt10">
						<button class="btn btn-primary" type="button" style="font-size:16px" id="save">保&nbsp;&nbsp;存</button>
						<button class="btn btn-default" id="process_btn" disabled="disabled" style="display:none;" onclick="return false;">
							<i class="fa fa-default"></i> 正在处理
						</button>
						<button class="btn btn-default" type="button" style="font-size:16px" onclick='jumbto("/stocked/index")' >返回</button>
					</div>
				</form>
</body>
</html>