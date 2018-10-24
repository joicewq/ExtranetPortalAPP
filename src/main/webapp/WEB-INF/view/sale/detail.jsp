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
					<ul class="form-list">
					<input type="hidden" id="id" name="id"  value="${saleInfo.id }">
					<input type="hidden" id="companyName" name="companyName"  value="${saleInfo.companyName }">
					<input type="hidden" id="companyId" name="companyId"  value="${saleInfo.companyId }">
						<li>
							<div class="label">
								<span class="sm-title" >食盐销售商：</span>
								<input type="text" class="required"   id="saltRetailers" name="saltRetailers" value="${saleInfo.saltRetailers }" readonly="readonly">
							</div>
						</li>
						<li>
							<div class="label">
								<span class="sm-title" style="white-space: nowrap;">用户名称：</span>
								<input type="text" class="required"   id="userSaleName" name="userSaleName" value="${saleInfo.userName }" readonly="readonly">
							</div>
						</li>
						<li>
							<div class="label">
								<span class="sm-title" style="white-space: nowrap;">标题：</span>
								<input type="text" class="required"   id="title" name="title" value="${saleInfo.title }" readonly="readonly">
							</div>
						</li>
						<li>
							<div class="label">
								<span class="sm-title"  >用户地址：</span>
								<input type="text" class="required"   id="userAddress" name="userAddress" value="${saleInfo.userAddress }" readonly="readonly">
							</div>
						</li>
						<li>
							<div class="label">
								<span class="sm-title"  >联系人：</span>
								<input type="text" class="required"   id="contactPerson" name="contactPerson" value="${saleInfo.contactPerson }" readonly="readonly">
							</div>
						</li>
						<li>
							<div class="label">
								<span class="sm-title"  >联系方式：</span>
								<input type="text" class="required"   id="contactPhone" name="contactPhone" value="${saleInfo.contactPhone }" readonly="readonly">
							</div>
						</li>
						<li>
							<div class="label">
								<span class="sm-title"  >月用盐量：</span>
								<input type="text" class="required"   id="monthSalt" name="monthSalt" value="${saleInfo.monthSalt }" readonly="readonly">
							</div>
						</li>
					</ul>
				</form>
				<div class="text-center mt10">
					<button class="btn btn-default" onclick='jumbto("/natural/naturalindex")' type="button" style="font-size:16px" >返回</button>
				</div>
</body>
</html>