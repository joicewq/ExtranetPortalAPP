<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>需求发布</title>
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
	<script type="text/javascript" src="/static/model/vipcenter/demandedit.js"></script>
	<script type="text/javascript">
// 		var  companyId = "${companyId}";
	</script>  
 
</head>
							
<body> 
	<form id="supply-form">
		<ul class="form-list">
			<li>
				<div class="label">
					<span class="sm-title" style="min-width:100px;">产品名称：</span>
					<input type="text" class="required" style="width:200px;" id="pIdName" name="pIdName" value="${demand.pIdName}" readonly="readonly">
				</div>
			</li>
			<li>
				<div class="label">
					<span class="sm-title" style="min-width:100px;">规格：</span>
					<input type="text" class="required" style="width:200px;" id="productSpec" name="productSpec" value="${demand.productSpec}" readonly="readonly">
				</div>
			</li>   
			<li>
				<div class="label">
					<span class="sm-title" style="min-width:100px;">产地：</span>
					<input type="text" class="required" style="width:200px;" id="producePlace" name="productPlace" value="${demand.productPlace}" readonly="readonly">
				</div>
			</li>
			<li>
				<div class="label">
					<span class="sm-title" style="min-width:100px;">物流方式：</span> 
					<input style="width: 20px;position: relative;" <c:if test="${demand.logisticsMode eq '1_3_1' }"> checked='checked' </c:if> type="radio"  id="logisticsMode" name="logisticsMode" value="1_3_1" readonly="readonly">顺丰快递
					<input style="width: 20px;position: relative;" <c:if test="${demand.logisticsMode eq '1_3_2' }"> checked='checked' </c:if> type="radio"  id="logisticsMode" name="logisticsMode" value="1_3_2" readonly="readonly">汽运物流
				</div>
			</li>
			<li class="li-per">
				<div class="label">
					<span class="sm-title" style="min-width:100px;">截止日期：</span>
					<input type="text" style="width:200px;" id="deadtime" name="deadtime" value="${demand.deadtime}" readonly="readonly">
				</div>
			</li>
			<li>
				<div class="label">
					<span class="sm-title" style="min-width:100px;">数量：</span>
					<input type="text" class="required" style="width:200px;" id="productNum" name="productNum" value="${demand.productNum}" readonly="readonly">
				</div>
			</li>
			<li>
				<div class="label">
					<span class="sm-title" style="min-width:100px;">单价（元/kg）：</span>
					<input type="text" class="required" style="width:200px;" id="productPrice" name="productPrice" value="${demand.productPrice}" readonly="readonly">
				</div>
			</li>
			<li>
				<div class="label">
					<span class="sm-title" style="min-width:100px;">交割地：</span>
					<input type="text" class="required" style="width:200px;" id="tradeAddress" name="tradeAddress" value="${demand.tradeAddress}" readonly="readonly">
				</div>
			</li>
			<li>
				<div class="label">
					<span class="sm-title" style="min-width:100px;">联系人：</span>
					<input type="text" class="required" style="width:200px;" id="linkman" name="linkman" value="${demand.linkman}" readonly="readonly">
				</div>
			</li>
			<li>
				<div class="label">
					<span class="sm-title" style="min-width:100px;">联系方式：</span>
					<input type="text" class="required" style="width:200px;" id="contactPhone" name="contactPhone" value="${demand.contactPhone}" readonly="readonly">
				</div>
			</li>
			<li>
				<div class="label">
					<span class="sm-title">质量等级：</span> 
					<select style="width:200px;" id="qualityLevel" name="qualityLevel">
						<option value="">请选择</option>
						<option <c:if test="${demand.qualityLevel eq '0'}">selected='selected'</c:if> value="0">一级</option>
						<option <c:if test="${demand.qualityLevel eq '1'}">selected='selected'</c:if> value="1">二级</option>
						<option <c:if test="${demand.qualityLevel eq '2'}">selected='selected'</c:if> value="2">三级</option>
					</select>
				</div>
			</li>
			<li>
				<div class="label">
					<span class="sm-title">单价是否包含产品税费、包装费、运费、装卸费 ：</span> 
					<input style="width: 20px;position: relative;" type="radio"  id="isInfreight" name="isInfreight" value="1" <c:if test="${demand.isInfreight eq '1' }"> checked </c:if> readonly="readonly">是
					<input style="width: 20px;position: relative;" type="radio"  id="isInfreight" name="isInfreight" value="0" <c:if test="${demand.isInfreight eq '0' }"> checked </c:if> readonly="readonly">否	
				</div>
			</li>
			<li  class="li-per">
				<div class="label">
					<span class="sm-title">食盐生产标准依据 ：</span>
					<textarea rows="2" cols="2" id="saltPsbasis" name="saltPsbasis" maxlength="2000" style="width:580px;" readonly="readonly">${demand.saltPsbasis}</textarea>
				</div>
			</li>
			<li class="li-per">
				<div class="label">
					<span class="sm-title">食盐质量要求 ：</span>
					<textarea rows="2" cols="2" id="saltQualrequire" name="saltQualrequire" maxlength="2000" style="width:580px;" readonly="readonly">${demand.saltQualrequire}</textarea>
				</div>
			</li>
			<li class="li-per">
				<div class="label">
					<span class="sm-title">食盐包装要求 ：</span>
					<textarea rows="2" cols="2" id="saltPackrequire" name="saltPackrequire" maxlength="2000" style="width:580px;" readonly="readonly">${demand.saltPackrequire}</textarea>
				</div>
			</li> 
		</ul>
<!-- 		<div class="text-center mt10"> -->
<!-- 			<button class="btn btn-primary" type="button" style="font-size:16px" id="save">发&nbsp;&nbsp;布</button> -->
<!-- 			<button class="btn btn-default" id="process_btn" disabled="disabled" style="display:none;" onclick="return false;"> -->
<!-- 				<i class="fa fa-default"></i> 正在处理 -->
<!-- 			</button> -->
<!-- 			<input type="reset" class="hide" id="js-reset-btn"> -->
<!-- 		</div> -->
	</form> 

</body>

</html>