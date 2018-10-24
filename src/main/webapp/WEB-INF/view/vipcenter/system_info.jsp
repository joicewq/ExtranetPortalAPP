<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en-us">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统通知详情</title>
<link rel="Shortcut Icon" href="/static/images/jyqt.ico">
</head>
<body>
<div class="div-center mt10">
	<h2 class="fb mb10">${data.title }</h2>
	<p>${data.content }</p>
</div>
<div class="text-center mt15">
	<button class="btn btn-default" onclick="jumbto('/vipcenter/systemIndex')" type="button">返回</button>
</div>
</body>
</html>