<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<jsp:include page="${ctx}/static/common/jstl.jsp" />
<jsp:include page="${ctx}/static/common/import.jsp" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>广东省盐业监管服务平台</title>
<link rel="Shortcut Icon" href="/static/images/jgqt.ico">
<!-- 未读消息图标css -->
<style>
	.info-badge{
		display: inline-block;
		min-width: 10px;
		padding: 3px 7px;
		font-size: 12px;
		font-weight: 700;
		line-height: 1;
		color: #fff;
		text-align: ce	nter;
		white-space: nowrap;
		vertical-align: middle;
		background-color: #ff7800;
		border-radius: 10px;
		margin-left:-8px;
	}
	.entry-box>a{
		height: 57px;
	}
	.ds-footer {
		margin-top: 0;
	}
</style> 
</head>
<body>
	<script data-main="${ctx}/static/model/home/home.js" src="${ctx}/static/js/require.js"></script>
	<jsp:include page="/static/common/header.jsp" />
	<div class="ds-main container" id="homeBox">
		<div class="row clearfix">
			<div class="index-left">
				<div class="entry-box">
				<c:choose>
				<c:when test="${empty loginId}">
				<c:set var="url_1" value="javascript:window.location.href='/login/index'"></c:set>
				<c:set var="url_2" value="javascript:window.location.href='/login/index'"></c:set>
				</c:when>
				<c:otherwise>
				<c:set var="url_1" value="/vipcenter/index?showPage=0_0"></c:set>
				<c:set var="url_2" value="/vipcenter/index?showPage=1"></c:set>
				</c:otherwise>
				</c:choose>
					<a href="${url_1}">
						<div class="entry-icon">
							<i class="iconfont icon-company"></i>
						</div>
						<div class="entry-title-wrap">
							<span class="entry-title">企业信息告知</span>
							<span class="entry-intro">企业管理备案</span>
						</div>
					</a>
					<a href="${url_2}">
						<div class="entry-icon">
							<i class="iconfont icon-szzw-danganguanli"></i>
						</div>
						<div class="entry-title-wrap">
							<span class="entry-title">产品信息告知</span>
							<span class="entry-intro">产品资质备案</span>
						</div>
					</a>
					<a href="/trace/search">
						<div class="entry-icon">
							<i class="iconfont icon-search"></i></i>
						</div>
						<div class="entry-title-wrap">
							<span class="entry-title">防伪追溯</span>
							<span class="entry-intro">一键查伪追溯</span>
						</div>
					</a>
					<a href="${trade_url}" target="_blank">
						<div class="entry-icon">
							<i class="iconfont icon-yytb-sheliangqiyexinxisvg"></i>
						</div>
						<div class="entry-title-wrap">
							<span class="entry-title">交易平台</span>
							<span class="entry-intro">食盐网上交易平台</span>
						</div>
					</a>
					<a href="/complaints/report" class="">
						<div class="entry-icon">
							<i class="iconfont icon-shuzizhengwujuchangxinxiang01"></i>
						</div>
						<div class="entry-title-wrap">
							<span class="entry-title">举报投诉</span>
							<span class="entry-intro">您的投诉，我的服务</span>
						</div>
					</a>
					<a href="http://ywj.gdsalt.com/xingzheng.php" class="no-border" target="_blank">
						<div class="entry-icon">
							<i class="iconfont icon-yytb-sheliangqiyexinxisvg"></i>
						</div>
						<div class="entry-title-wrap">
							<span class="entry-title">信用信息双公示</span>
							<span class="entry-intro">行政许可、行政处罚</span>
						</div>
					</a>
				</div>
				<div id="picplayer" class="slider">
					<ul class="slides clearfix">
						<li class="" v-for="item in items">
							<a v-bind:href="'/broadcast/detail?id='+item.id" target="_blank" class="">
								<img v-bind:alt="item.advTitle" v-bind:src="'${upload_url}/doc/download/'+item.fileIds"/>
							</a>
						</li>
					</ul>
				</div>
			</div>
			<div class="index-right">
                <a href="/news/list" class="ds-more pull-right" style="margin-top: 10px;margin-right: 10px;">更多>></a>
				<ul class="tabs clearfix">
                    <li class="active"><a href="#dynamic-list">盐业动态</a></li>
                    <li><a href="#law-enforcement">盐政执法</a></li>
                    <li><a href="#policies">政策法规</a></li>
                </ul>
                <div class="tab-contentmian">
                    <div class="tab-content nopadding" id="dynamic-list">
                        <ul class="news-list">
                        	<li v-for="item in items">
                        		<a v-bind:href="'/news/detail?id='+item.id" target="_blank" v-bind:title="item.title" class="news-title" v-html="item.title"></a>
                        		<span class="news-hot-icon" v-if="item.isNewest==true"></span>
								<span class="news-date" v-html="item.date"></span>                       		
                        	</li>
                        </ul>
                    </div>
                    <div class="tab-content nopadding" id="law-enforcement">
                    	<ul class="news-list">
                        	<li v-for="item in items">
                        		<a v-bind:href="'/news/detail?id='+item.id" target="_blank" v-bind:title="item.title" class="news-title" v-html="item.title"></a>
                        		<span class="news-hot-icon" v-if="item.isNewest==true"></span>
								<span class="news-date" v-html="item.date"></span>                       		
                        	</li>
                        </ul>
                    </div>
                    <div class="tab-content nopadding" id="policies">
                    	<ul class="news-list">
                        	<li v-for="item in items">
                        		<a v-bind:href="'/policies/detail?id='+item.id" target="_blank" v-bind:title="item.title" class="news-title" v-html="item.title"></a>
                        		<span class="news-hot-icon" v-if="item.isNewest==true"></span>
								<span class="news-date" v-html="item.date"></span>                       		
                        	</li>
                        </ul>
                    </div>
                </div>
			</div>
		</div>

		<div class="row clearfix">
			<div class="module module-min module-border">
				<div class="module-title-wrap">
					<span class="module-title-primary"></span>
					<div class="module-title inline-block">
						企业信息公示
					</div>
					<a href="/ni/list" class="ds-more pull-right">更多>></a>
				</div>
				<div class="module-content-wrap" id="company-publicity">
					<ul class="news-list">
						<li v-for="item in items" >
							<a v-bind:href="'/spi/detail?id='+item.id" target="_blank" v-bind:title="item.issueTitle" class="news-title" v-html="item.issueTitle"></a>
							<span class="news-hot-icon" v-if="item.isNewest==true"></span>
							<span class="news-date" v-html="item.date"></span>
						</li>
					</ul>
				</div>
			</div>
			<div class="module module-min module-border pull-right">
				<div class="module-title-wrap">
					<span class="module-title-primary"></span>
					<div class="module-title inline-block">
						通知公告
					</div>
					<a href="/notice/list" class="ds-more pull-right">更多>></a>
				</div>
				<div class="module-content-wrap" id="notices">
					<ul class="news-list">
						<li v-for="item in items" >
							<a v-bind:href="'/notice/detail?id='+item.id" target="_blank" v-bind:title="item.title" class="news-title" v-html="item.title"></a>
							<span class="news-hot-icon" v-if="item.isNewest==true"></span>
							<span class="news-date" v-html="item.date"></span>
						</li>
					</ul>
				</div>
			</div>
		</div>
		
		<div id="picplayer2" style="position:relative;overflow:hidden;width:1200px;height:92px;clear:none;border:solid 1px #ccc;margin:0 auto;margin-top: 30px" >
			<img src="/static/images/banner3.jpg" alt="" />
		</div>
		
		<div class="row modules-banner">
			<img src="${ctx}/static/images/indexModulesBannerLeft.png" class="modules-banner-left">
			<div class="modules-banner-line"></div>
			<img src="${ctx}/static/images/indexModulesBannerRight.png" class="modules-banner-right">
		</div>
		<div class="row clearfix" id="publicity-list">
			<div class="module module-normal">
				<div class="module-title-wrap">
					<span class="module-title-primary"></span>
					<div class="module-title inline-block">
						产品信息公示
					</div>
					<a href="/ni/list" class="ds-more pull-right">更多>></a>
				</div>
				<div class="module-content-wrap" id="publicity-pass-list">
					<ul class="news-list">
						<li v-for="item in items" >
							<a v-bind:href="'/ni/naturaldetail?id='+item.id" target="_blank" v-bind:title="item.issueTitle" class="news-title" v-html="item.issueTitle"></a>
							<span class="news-hot-icon" v-if="item.isNewest==true"></span>
							<span class="news-date" v-html="item.date"></span>
						</li>
					</ul>
				</div>
			</div>
			<div class="module-divider"></div>
			<div class="module module-normal pull-right">
				<div class="module-title-wrap">
					<span class="module-title-danger"></span>
					<div class="module-title inline-block">
						检测结果公示
					</div>
					<a href="/ni/list" class="ds-more pull-right">更多>></a>
				</div>
				<div class="module-content-wrap" id="publicity-failed-list">
					<ul class="news-list">
						<li v-for="item in items" >
							<a v-bind:href="'/ni/detail?id='+item.id" target="_blank" v-bind:title="item.title" class="news-title" v-html="item.title"></a>
							<span class="news-hot-icon" v-if="item.isNewest"></span>
							<span class="news-date" v-html="item.date"></span>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<div class="row related-links">
			<div class="related-links-left">
				<span class="related-links-icon"><i class="fa fa-link"></i></span>
				<span class="related-links-text">相关链接<br /><small>RELATED LINKS</small></span>
			</div>
			<div class="related-links-right">
				<a href="http://www.gov.cn" target="_blank"><img src="/static/images/bottom-zhengfuwang.jpg" /></a>
				<a href="http://www.sdpc.gov.cn" target="_blank"><img src="/static/images/bottom-fagaiwei.jpg" /></a>
				<a href="http://www.miit.gov.cn" target="_blank"><img src="/static/images/bottom-gongxinbu.jpg" /></a>
				<a href="http://www.gd.gov.cn" target="_blank"><img src="/static/images/bottom-renminzhengfu.jpg" /></a>
				<a href="http://www.cnsalt.cn" target="_blank"><img src="/static/images/bottom-chinayanye.jpg" /></a>
			</div>
		</div>
		
	</div>

	<jsp:include page="${ctx}/static/common/footer.jsp" />
</body>
</html>