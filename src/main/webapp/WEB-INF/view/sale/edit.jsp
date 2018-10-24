<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/static/common/jstl.jsp"%>
<%@ include file="/static/common/import.jsp"%>
<!DOCTYPE html>
<html lang="en-us">
<head>
<meta charset="utf-8">
<title></title>
<link rel="icon" href="/static/images/jyht.ico" type="image/x-icon"/>
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
<script type="text/javascript" src="${ctx}/static/model/sale/edit.js"></script>
</head>
<body>
<!-- 	<div class="ds-main">
		<div class="user-main mt10 clearfix">
			
			<div class="user-right"> -->
				<form id="supply-form">
					<input type="hidden" id="id" name="id"  value="${saleInfo.id }">
					<input type="hidden" id="companyName" name="companyName"  value="${saleInfo.companyName }">
					<input type="hidden" id="companyId" name="companyId"  value="${saleInfo.companyId }">
					<ul class="ds-user-from member-form normal-form clearfix">
						<li>
				            <label class="ds-from-left"><span class="ds-text-red mr5">*</span>食盐销售商:</label>
				            <div class="ds-from-group">
								<input type="text" class="required ds-inp-control"   id="saltRetailers" name="saltRetailers" value="${saleInfo.saltRetailers }">
				                <span class="ds-from-tips ds-text-red ml10"></span>
				            </div>
				        </li>
				        <li>
				            <label class="ds-from-left"><span class="ds-text-red mr5">*</span>用户名称:</label>
				            <div class="ds-from-group">
								<input type="text" class="required ds-inp-control"   id="userSaleName" name="userSaleName" value="${saleInfo.userName }">
				                <span class="ds-from-tips ds-text-red ml10"></span>
				            </div>
				        </li>
				        <li>
				            <label class="ds-from-left"><span class="ds-text-red mr5">*</span>标题:</label>
				            <div class="ds-from-group">
								<input type="text" class="required ds-inp-control"   id="title" name="title" value="${saleInfo.title }">
				                <span class="ds-from-tips ds-text-red ml10"></span>
				            </div>
				        </li>
				        <li>
				            <label class="ds-from-left"><span class="ds-text-red mr5">*</span>用户地址:</label>
				            <div class="ds-from-group">
								<input type="text" class="required ds-inp-control"   id="userAddress" name="userAddress" value="${saleInfo.userAddress }">
				                <span class="ds-from-tips ds-text-red ml10"></span>
				            </div>
				        </li>
				        <li>
				            <label class="ds-from-left"><span class="ds-text-red mr5">*</span>联系人:</label>
				            <div class="ds-from-group">
								<input type="text" class="required ds-inp-control"   id="contactPerson" name="contactPerson" value="${saleInfo.contactPerson }">
				                <span class="ds-from-tips ds-text-red ml10"></span>
				            </div>
				        </li>
				        <li>
				            <label class="ds-from-left"><span class="ds-text-red mr5">*</span>联系方式:</label>
				            <div class="ds-from-group">
								<input type="text" class="required ds-inp-control"   id="contactPhone" name="contactPhone" value="${saleInfo.contactPhone }">
				                <span class="ds-from-tips ds-text-red ml10"></span>
				            </div>
				        </li>
				        <li>
				            <label class="ds-from-left"><span class="ds-text-red mr5">*</span>月用盐量:</label>
				            <div class="ds-from-group">
								<input type="text" class="required ds-inp-control"   id="monthSalt" name="monthSalt" value="${saleInfo.monthSalt }">
				                <span class="ds-from-tips ds-text-red ml10"></span>
				            </div>
				        </li>
					</ul>
					<div class="text-center mt10">
						<button class="btn btn-primary" type="button" style="font-size:16px" id="save">保存</button>
						<button class="btn btn-default" id="process_btn" disabled="disabled" style="display:none;" onclick="return false;">
							<i class="fa fa-default"></i> 正在处理
						</button>
						<button class="btn btn-default" onclick='jumbto("/natural/naturalindex")' type="button" style="font-size:16px" >返回</button>
					</div>
				</form>
</body>
</html>