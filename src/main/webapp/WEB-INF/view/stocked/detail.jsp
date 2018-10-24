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
	
	<form id="supply-form">
		<ul class="form-list">
		<input type="hidden" id="id" name="id"  value="${stockedInfo.id }">
		<input type="hidden" id="stocekOrder" name="stocekOrder"  value="${stockedInfo.stocekOrder }">
		<input type="hidden" id="stocekCode" name="stocekCode"  value="${stockedInfo.stocekCode }">
			<li>
				<div class="label">
					<span class="sm-title"><span class="text-red mr5">*</span>企业名称：</span>
					<input type="text"  name="companyName" id="companyName"  class="required" value="${stockedInfo.companyName }" readonly="readonly">
				</div>
			</li>
			<li>
				<div class="label">
					<span class="sm-title" >标题：</span>
					<input type="text" class="required"   id="title" name="title" value="${stockedInfo.title }" readonly="readonly">
				</div>
			</li>
			<li>
				<div class="label">
					<span class="sm-title" style="white-space: nowrap;">食盐月均销售量 /预销量：</span>
					<input type="text" class="required"   id="saleNumber" name="saleNumber" value="${stockedInfo.saleNumber }" readonly="readonly">(公斤)
				</div>
			</li>
			<li>
				<div class="label">
					<span class="sm-title"  >联系人：</span>
					<input type="text" class="required"   id="contactPerson" name="contactPerson" value="${stockedInfo.contactPerson }" readonly="readonly">
				</div>
			</li>
			<li>
				<div class="label">
					<span class="sm-title"  >食盐储备任务量：</span>
					<input type="text" class="required"   id="saleTaskNumber" name="saleTaskNumber" value="${stockedInfo.saleTaskNumber }" readonly="readonly">(公斤)
				</div>
			</li>
			<li>
				<div class="label">
					<span class="sm-title"  >联系电话：</span>
					<input type="text" class="required"   id="contactPhone" name="contactPhone" value="${stockedInfo.contactPhone }" readonly="readonly">
				</div>
			</li>
			<li>
				<div class="label">
					<span class="sm-title"  >储备仓库地址：</span>
					<input type="text" class="required"   id="stockedAddress" name="stockedAddress" value="${stockedInfo.stockedAddress }" readonly="readonly">
				</div>
			</li>
			<li>
				<div class="label">
					<span class="sm-title" >类型：</span>
					<input type="text"  name="type" id="type" value="${stockedInfo.type }" readonly="readonly">
				</div>
			</li>
			<li>
				<div class="label">
					<span class="sm-title"  >仓库容量：</span>
					<input type="text" class="required"   id="stockedCapacity" name="stockedCapacity" value="${stockedInfo.stockedCapacity }" readonly="readonly">(公斤)
				</div>
			</li>
			<li>
				<div class="label">
					<span class="sm-title"  >邮政编码：</span>
					<input type="text" class="required"   id="postalCode" name="postalCode" value="${stockedInfo.postalCode }" readonly="readonly">
				</div>
			</li>
			<li>
				<div class="label">
					<span class="sm-title"  >仓库最大库存量：</span>
					<input type="text" class="required"   id="stockedMaxCapacity" name="stockedMaxCapacity" value="${stockedInfo.stockedMaxCapacity }" readonly="readonly">(公斤)
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
			<li>
				<div class="label">
					<span class="sm-title" >仓储备案食盐名称：</span>
					<input type="text"  readonly value="${stockedInfo.remark }" />
				</div>
			</li>
			<li class="li-per">
				<div class="label text-center">
					<button class="btn btn-default" type="button" style="font-size:16px" onclick='jumbto("/stocked/index")' >返回</button>
				</div>
			</li>
		</ul>
	</form>
</body>
</html>