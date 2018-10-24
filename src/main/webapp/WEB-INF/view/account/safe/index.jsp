<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<title></title>
<link rel="Shortcut Icon" href="/static/images/jgqt.ico">
</head>
<body>
	<div class="safe-tips-div">
		<div class="safe-tips">
			<span class="safe-tips-text safe-text1 pull-left">登陆密码</span>
			<span class="safe-tips-text text-red">互联网账号存在被盗危险，建议你定期更改登陆密码以保护账号安全</span>
			<a class="safe-tips-text pull-right text-orange" style="cursor:pointer;" id="edit">修改密码</a>
		</div>
		<div class="safe-tips">
			<span class="safe-tips-text safe-text1 pull-left">手机验证</span>
			<span class="safe-tips-text"><span class="text-gray">你验证的手机是：</span>${fn:substring(saltUserInfo.phone,0,3)}****${fn:substring(saltUserInfo.phone,7,11)}<span class="text-gray">，若已丢失或更换，请立即更换，</span><span class="text-red">避免账号被盗</span></span>
			<a class="safe-tips-text pull-right text-orange" style="cursor:pointer;" href="/account/changePhoneNumber" target="_blank">修改</a>
		</div>
	</div>
</body>

<script type="text/javascript">
	function edit(){
		$("#rightContent").load('/account/safe/edit');
	}
	
	$(function(){
		$("#edit").click(edit);
	});
	
</script>

</html>