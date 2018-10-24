<%@ page language="java" pageEncoding="UTF-8"%>
<%-- <%@ include file="/static/common/jstl.jsp"%>
<%@ include file="/static/common/import.jsp"%> --%>
<!DOCTYPE html>
<html lang="en-us">
<head>
<meta charset="utf-8">
<title>产品发布管理</title>
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
<script type="text/javascript" src="${ctx}/static/model/prodlib/edit.js"></script>
</head>
<body>
<!-- 	<div class="ds-main">
		<div class="user-main mt10 clearfix">
			
			<div class="user-right"> -->
				<form id="supply-form">
					<ul class="form-list">
						<li>
							<div class="label">
								<span class="sm-title" style="min-width:100px;">名称：</span>
								<input type="text" class="required" style="width:200px;" id="name" name="name">
							</div>
						</li>
						<li>
							<div class="label">
								<span class="sm-title" style="min-width:100px;">规格：</span>
								<input type="text" class="required" style="width:200px;" id="spec" name="spec">
							</div>
						</li>
						<li>
							<div class="label">
								<span class="sm-title" style="min-width:100px;">品级：</span>
								<input type="text" class="required" style="width:200px;" id="grade" name="grade">
							</div>
						</li>
						<li>
							<div class="label">
								<span class="sm-title" style="min-width:100px;">产品标准号：</span>
								<input type="text" class="required" style="width:200px;" id="stdNum" name="stdNum">
							</div>
						</li>
						<li>
							<div class="label">
								<span class="sm-title" style="min-width:100px;">定点生产证号：</span>
								<input type="text" class="required" style="width:200px;" id="licenseNum" name="licenseNum">
							</div>
						</li>
						<li>
							<div class="label">
								<span class="sm-title" style="min-width:100px;">产地：</span>
								<input type="text" class="required" style="width:200px;" id="producePlace" name="producePlace">
							</div>
						</li>
						<li class="li-per">
							<div class="label">
								<span class="sm-title" style="min-width:100px;">产品有效期：</span>
								<input type="text" class="required" style="width:200px;" id="valtDateFrom" name="valtDateFrom">
								<span> 至 </span>  
								<input type="text" class="required" style="width:200px;" id="valtDateTo" name="valtDateTo">
							</div>
						</li>
						<li>
							<div class="label">
								<span class="sm-title" style="min-width:100px;">数量：</span>
								<input type="text" class="required" style="width:200px;" id="number" name="number">
							</div>
						</li>
						<li>
							<div class="label">
								<span class="sm-title" style="min-width:100px;">报价（元/kg）：</span>
								<input type="text" class="required" style="width:200px;" id="price" name="price">
							</div>
						</li>
						<li>
							<div class="label">
								<span class="sm-title" style="min-width:100px;">交割地：</span>
								<input type="text" class="required" style="width:200px;" id="deliveryPlace" name="deliveryPlace">
							</div>
						</li>
						<li class="li-per">
							<div class="label">
								<span class="sm-title" style="min-width:100px;">配料：</span>
								<textarea name="mixIngt" id="mixIngt" rows="2" id="mixIngt" name="mixIngt"></textarea>
							</div>
						</li>
						<li class="li-per">
							<div class="label">
								<span class="sm-title" style="min-width:100px;">贮存：</span>
								<textarea name="keep" id="keep" rows="2" id="keep" name="keep"></textarea>
							</div>
						</li>
						<li class="li-per">
							<div class="label">
								<span class="sm-title" style="min-width:100px;">营养成分：</span>
								<textarea name="nutrComp" id="nutrComp" rows="2" id="nutrComp" name="nutrComp"></textarea>
							</div>
						</li>
						<li class="li-per">
							<div class="label">
								<span class="sm-title" style="min-width:100px;">产品介绍：</span>
								<textarea name="introduce" id="introduce" rows="2" id="introduce" name="introduce"></textarea>
							</div>
						</li>
						<li class="li-per">
							<div class="label">
								<span class="sm-title mt5" style="min-width:100px;">产品外观：</span>
								<div id="appearIds"></div>
								<!-- <a href="javascript:;" class="file pull-left mr10">
									<input type="file" name="" id="">
									<input type="text" class="showFileName">
									浏览
								</a>
								<a href="javascript:;" class="a-upload">上传</a> -->
							</div>
						</li>
						<li class="li-per">
							<div class="label">
								<span class="sm-title" style="min-width:100px;">防伪识别方法：</span>
								<textarea name="antiFake" id="antiFake" rows="2" id="antiFake" name="antiFake"></textarea>
							</div>
						</li>
						<li class="li-per">
							<div class="label">
								<span class="sm-title mt5" style="min-width:100px;">质检报告：</span>
								<div id="checkIds"></div>
								<!-- <a href="javascript:;" class="file pull-left mr10">
									<input type="file" name="" id="">
									<input type="text" class="showFileName">
									浏览
								</a>
								<a href="javascript:;" class="a-upload">上传</a> -->
							</div>
						</li>
					</ul>
					<div class="text-center mt10">
						<button class="btn btn-primary" type="button" style="font-size:16px" id="save">发布</button>
						<button class="btn btn-default" id="process_btn" disabled="disabled" style="display:none;" onclick="return false;">
							<i class="fa fa-default"></i> 正在处理
						</button>
						<button class="btn btn-default" onclick='jumbto("/natural/naturalindex")'  type="button" style="font-size:16px" >返回</button>
					</div>
				</form>
<!-- 			</div>
		</div>
	</div> -->

</body>
</html>