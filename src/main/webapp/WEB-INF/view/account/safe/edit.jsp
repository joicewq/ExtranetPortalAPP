<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
<link rel="Shortcut Icon" href="/static/images/jgqt.ico">
<script type="text/javascript" src="${ctx}/static/model/account/safe/edit.js"></script>
 <%-- src="${ctx}/static/js/require.js" --%>
 <style type="text/css">
 .variety-passw {
    width: 190px !important;
 }
 </style>
</head>
<body>
<div class="edit-passw">
	<ul class="form-list">
		<li class="li-per">
			<div class="label">
				<span class="sm-title"><span class="mr5" style="color: red">*</span>输入原密码：</span>
				<div class="ds-from-group">
					<input type="password" class="ds-inp-control required" minDataLength="6" maxDataLength="20" id="password" name="password" />
					<span class="ds-text-red ml10"></span><!-- 登录密码校验:必填,6-12位,没有空格 -->
				</div>
			</div>
		</li>
		<li class="li-per">
			<div class="label">
				<span class="sm-title"><span class="mr5" style="color: red">*</span>输入新密码：</span>
				<div class="ds-from-group">
					<input type="password" class="ds-inp-control required" minDataLength="6" maxDataLength="20" id="newPassword" name="newPassword">
					<span class="ds-text-red ml10" style="position: absolute;top:0;left:290px;"></span><!-- 登录密码校验:必填,6-12位,没有空格 -->
				</div>
			</div>
		</li>
		<li class="li-per">
			<div class="label">
				<span class="sm-title"><span class="mr5" style="color: red">*</span>确认新密码：</span>
				<div class="ds-from-group">
					<input type="password" equalTo="#newPassword" class="ds-inp-control required checkPasswordIsSame" id="checkNewPassword" name="checkNewPassword">
					<span class="ds-text-red ml10" id="theTips"></span>
				</div>
			</div>
		</li>
		<li class="li-per">
			<div class="label">
				<span class="sm-title"><span class="mr5" style="color: red">*</span>输入验证码：</span>
				<input type="text" class="pull-left variety-passw required matchVerify" id="verifyCode" name="verifyCode" style="width:190px;">
				<span>
                    <img id="verifyImg" class="verifyImg" src="/verify/code" />
                    <a id="showCode" style="color:#134ea5;font-size:12px;" href="javascript:void(0)">看不清？换一张</a>
                </span>
			</div>
		</li>
		<li class="li-per">
			<div class="label">
				<span class="sm-title">&nbsp;</span>
				<button class="btn btn-primary" type="button" id="submit">提交</button>
				<button class="btn btn-default" id="process_btn" disabled="disabled" style="display:none;" onclick="return false;">
					<i class="fa fa-default"></i> 正在处理
				</button>
				<button class="btn btn-default" type="button" style="font-size:16px" id="back">返回</button>
			</div>
		</li>
	</ul>

</div>
</body>

</html>