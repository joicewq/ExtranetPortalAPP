	<header>
		<div class="ds-top">
	 		<ul class="ds-top-text clearfix ds-width-1000">
				<li class="pull-left">你好！欢迎来到青海电子商务平台。</li>
				<li class="pull-right" id="notLogin" style="display:none;">
					<a href="${himPath}/login.html" target="_blank" class="ds-top-login">登录</a>
					<span class="ds-fc-c0">|</span>
					<a href="${umPath}/html/register.html" target="_blank" class="ds-top-login">免费注册</a>
				</li>
				<li class="pull-right" id="isLogin" style="display:none;">
					<a href="#" class="ds-top-login" id="userName"></a>
					<a href="#"><span id="letterNum" class="info-badge"></span></a> 
					<span class="ds-fc-c0">|</span>
					<a href="javascript:logout()" class="ds-top-login"><i class="fa fa-sign-out"></i> 退出</a>
				</li>
			</ul>
		</div>
		<div class="ds-logo ds-width-1000">
			<img src="../images/logo.png" alt="" onclick="location.href='${himPath}/index.html'" class="ds-logo-img"/>
			<div class="ds-header-search pull-right">
				<div class="ds-search-w clearfix">
					<div class="ds-search-input-div pull-right ml10 mb10">
						<span class="ds-ico-search"></span>
						<input class="ds-search-input" type="text" id="keyId" placeholder="请输入关键字"/>
						<button class="ds-search-btn" onclick="serach()">搜 索</button>
					</div>
					<select class="ds-search-select pull-right" name="serachType" id="serachType">
						<option value="cim">搜企业</option>
						<option value="sell">搜供应</option>
						<option value="buy">搜采购</option>
					</select>
				</div>
				<p class="ds-quick-seacrh mt5" id="hotSpot">
				</p>
			</div>
		</div>
		<div class="ds-nav">
			<ul class="ds-nav-ul ds-width-1000">
				<li name="tab" id="him" onclick="toPage('${himPath}/index.html')">首页</li>
				<li name="tab" id="cim" onclick="toPage('${himPath}/company.html')">企业名录</li>
				<li name="tab" id="sell" onclick="toPage('${himPath}/sell.html')">供应信息</li>
				<li name="tab" id="buy" onclick="toPage('${himPath}/purchase.html')">采购信息</li>
			</ul>
		</div>
	</header>