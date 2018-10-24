<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<link rel="stylesheet" href="/static/css/zTreeStyle/zTreeStyle.css"/>
<script type="text/javascript" src="/static/js/jquery.ztree.all-3.5.js"></script>
<style type="text/css">
		#appearIds #loaded1,#checkIds #loaded2{
			width: 649px;
		    margin-left: 100px;
		}
		#appearIds #loaded1 div:nth-of-type(3),#checkIds #loaded2 div:nth-of-type(3){
			margin-top: -30px;
		}
	</style>
	<script type="text/javascript" src="/static/model/vipcenter/demand_edit.js"></script>
	<script type="text/javascript">
// 		var  companyId = "${companyId}";
	</script>  
 
</head>
							
<body> 
	<form id="supply-form">
		<ul class="form-list">
			<li>
				<div class="label">
					<span class="sm-title"><span class="text-red">*</span>产品名称：</span>
					<input type="hidden" style="width:200px;" id="pid" name="pid">
					<input type="text" class="required" style="width:200px;" id="pIdName" name="pIdName" maxlength="200" readonly="readonly">
					<ul class="ztree" id="tree">
					
					</ul>
				</div> 
			</li>
			<li>
				<div class="label">
					<span class="sm-title"><span class="text-red">*</span>规格：</span>
					<input type="text" class="required" style="width:200px;" id="productSpec" name="productSpec" maxlength="20">
				</div>
			</li>   
			<li>
				<div class="label">
					<span class="sm-title"><span class="text-red">*</span>产地：</span>
					<input type="text" class="required" style="width:200px;" id="producePlace" name="productPlace" maxlength="200">
				</div>
			</li>
			<li>
				<div class="label">
					<span class="sm-title"><span class="text-red">*</span>物流方式：</span>
					<label><input style="width: 20px;position: relative;" type="radio"  id="logisticsMode" name="logisticsMode" value="1_3_1" checked="checked">顺丰快递</label>
					<label><input style="width: 20px;position: relative;" type="radio"  id="logisticsMode" name="logisticsMode" value="1_3_2">汽运物流</label>
				</div>
			</li>
			<li class="li-per">
				<div class="label">
					<span class="sm-title"><span class="text-red">*</span>截止日期：</span>
					<input type="text" class="date-inp Wdate inputText required" onClick="WdatePicker()" style="width:200px;" id="deadtime" name="deadtime">
				</div>
			</li>
			<li>
				<div class="label">
					<span class="sm-title"><span class="text-red">*</span>数量：</span>
					<input type="text" class="required numerical" style="width:200px;" id="productNum" name="productNum" maxlength="10">
				</div>
			</li>
			<li>
				<div class="label">
					<span class="sm-title"><span class="text-red">*</span>单价（元/kg）：</span>
					<input type="text" class="required numerical_decimal" style="width:200px;" id="productPrice" name="productPrice" maxlength="10">
				</div>
			</li>
			<li>
				<div class="label">
					<span class="sm-title"><span class="text-red">*</span>交割地：</span>
					<input type="text" class="required" style="width:200px;" id="tradeAddress" name="tradeAddress"  maxlength="200">
				</div>
			</li>
			<li>
				<div class="label">
					<span class="sm-title"><span class="text-red">*</span>联系人：</span>
					<input type="text" class="required" style="width:200px;" id="linkman" name="linkman" maxlength="30">
				</div>
			</li>
			<li>
				<div class="label">
					<span class="sm-title"><span class="text-red">*</span>联系方式：</span>
					<input type="text" class="required mobilePhone" style="width:200px;" id="contactPhone" name="contactPhone">
				</div>
			</li>
			<li>
				<div class="label">
					<span class="sm-title">质量等级：</span> 
					<select style="width:200px;" id="qualityLevel" name="qualityLevel">
						<option value="">请选择</option>
						<option value="0">一级</option>
						<option value="1">二级</option>
						<option value="2">三级</option>
					</select>
				</div>
			</li>
			<li>
				<div class="label">
					<span class="sm-title">单价是否包含产品税费、包装费、运费、装卸费：</span>
					<label><input style="width: 20px;position: relative;" type="radio"  id="isInfreight" name="isInfreight" value="1">是</label>
					<label><input style="width: 20px;position: relative;" type="radio"  id="isInfreight" name="isInfreight" value="0">否</label> 
				</div>
			</li>
			<li  class="li-per">
				<div class="label">
					<span class="sm-title">食盐生产标准依据 ：</span>
					<textarea rows="2" cols="2" id="saltPsbasis" name="saltPsbasis" maxlength="2000" style="width:580px;"></textarea>
				</div>
			</li>
			<li class="li-per">
				<div class="label">
					<span class="sm-title">食盐质量要求 ：</span>
					<textarea rows="2" cols="2" id="saltQualrequire" name="saltQualrequire" maxlength="2000" style="width:580px;"></textarea>
				</div>
			</li>
			<li class="li-per">
				<div class="label">
					<span class="sm-title">食盐包装要求 ：</span>
					<textarea rows="2" cols="2" id="saltPackrequire" name="saltPackrequire" maxlength="2000" style="width:580px;"></textarea>
				</div>
			</li> 
		</ul>
		<div class="text-center mt10">
			<button class="btn btn-primary long-btn font16" type="button" id="save">发布</button>
			<button class="btn btn-default" id="process_btn" disabled="disabled" style="display:none;" onclick="return false;">
				<i class="fa fa-default"></i> 正在处理
			</button>
			<input type="reset" class="hide" id="js-reset-btn">
		</div>
		
	</form> 
<!-- 	<div id="treediv" -->
<!-- 		style='overflow: auto; display: none; position: absolute; width: 300px; height: 200px; background-color: #fff; border: 1px solid #ddd; padding: 5px; font-size: 12px;'> -->
<!-- 			<ul id="tree" class="ztree" style="top: 413px;"></ul> -->
<!-- 	</div> -->
</body>

</html>