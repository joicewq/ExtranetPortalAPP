<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统通知</title>
<link rel="Shortcut Icon" href="/static/images/jgqt.ico">
</head>
<body>

<div class="div-center mt10">

	<h2 class="fb mb10">${result.title}</h2>
	<div>${result.createDate}</div>
	<div>${result.fromUserName}</div>
	<p style="text-indent: 28px;">${result.content }</p>
	<div class="text-center mt10">
		<button class="btn btn-default" onclick='' type="button" style="font-size:16px" >返回</button>
	</div>
</div>
</body>

</html>