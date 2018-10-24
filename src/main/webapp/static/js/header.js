;define(["jquery","path"],function($,path){
	//热点系统值
	var hotSpotSys={
			him:"ecom_him_hotSpot",
			cim:"ecom_cim_hotSpot",
			sell:"ecom_sell_hotSpot",
			buy:"ecom_buy_hotSpot"
	};
	var himPath=path.himPath;
	var umPath=path.umPath;
	var cimPath=path.cimPath;
	var sdimPath=path.sdimPath;
	var picPath=path.picPath;
	
	function getUnreadNum(callback){
		$.getJSON(himPath+"/messageController/getUnreadNum.do?callback=?",callback);
		//	$.getJSON("http://127.0.0.1:8188/messageController/getUnreadNum.do?callback=?",callback);
	}
	
	function isLogin(callback){
		$.getJSON(himPath+"/loginController/checkStatus.do?callback=?",callback);
	}
	
	function logout(){
		$.getJSON(himPath+"/cookie/delCookie.do?callback=?",function(msg){
			if(msg.result == 0){
				alert("失败!");
			}
			if(msg.result == 1){
				location.href=himPath+"/html/home.html";
			}
		});
	}
	
	function toPage(url){
		var localUrl=(location.origin+location.pathname);
		if(url.indexOf(localUrl)>-1){
			location.href=url;
		}else{
			window.open(url);
		}
	}
	
	function serach(){
		if($("#serachType").val()){
			if($("#keyId").val().length>10){
				alert("搜索内容过长,请简短关键词");
				return;
			}
			var url="";
			if($("#serachType").val()=='cim'){
				if($("#keyId").val()){
					url=cimPath+"/html/company.list.html?companyName="+$("#keyId").val();
				}else{
					url=cimPath+"/html/company.list.html";
				}
			}else if($("#serachType").val()=='sell'){
				if($("#keyId").val()){
					url=sdimPath+"/html/sellList.html?title="+$("#keyId").val();
				}else{
					url=sdimPath+"/html/sellList.html";
				}
			}else if($("#serachType").val()=='buy'){
				if($("#keyId").val()){
					url=sdimPath+"/html/purchaseList.html?title="+$("#keyId").val();
				}else{
					url=sdimPath+"/html/purchaseList.html";
				}
			}
			
			if(currentSysHotSpot){
				if($("#keyId").val()){
					setHotPort(url);
				}
				initHotSpot(currentSysHotSpot);
			}
			toPage(url);//载入url
		}
		
	}
	
	//热点对象
	var hotSpot={
			"times":1,
			"key":"",
			"value":""
	};
	//热点集合
	var hotSpots=[];
	//热点内容
	var cookieText="";
	//当前系统热点
	var currentSysHotSpot="";
	/**
	 * 设置热点
	 * @param value
	 */
	function setHotPort(value){
		var Days = 30;
		var exp = new Date();
		var key=$("#keyId").val();
		var isExist=false;
		exp.setTime(exp.getTime() + Days*24*60*60*1000);
		for(var i=0;i<hotSpots.length;i++){//遍历已经存在的记录
			var hisKey=hotSpots[i].key;
			if(key==hisKey){//如果存在,times+1;
				var times=Number(hotSpots[i].times)+1;
				hotSpots[i].times=times;
				isExist=true;
				//如果当前的times大于前面的times
				if(i>0){
					var beforeTimes=Number(hotSpots[i-1].times);
					if(times>beforeTimes){
						setSort(hotSpots,i,i-1);
					}
				}
			}
		}
		if(!isExist){//如果不存在,保存进去
			hotSpot.key=key;
			hotSpot.value=value;
			hotSpot.times=1;
			hotSpots.push(hotSpot);
		}
		
		var value=arrayToString(hotSpots);
		document.cookie = currentSysHotSpot+"="+  value + ";expires=" + exp.toGMTString();
	}
	
	/**
	 * 初始化热点
	 */
	function initHotSpot(sysHot){
		currentSysHotSpot=sysHot;
		var cookies=document.cookie;
		var cookieArray=cookies.split(";");
		//获取cookie
		for(var i=0;i<cookieArray.length;i++){
			if(cookieArray[i].indexOf(currentSysHotSpot)>-1){
				stringToArray(cookieArray[i]);
			}
		}
		//组成展示数据
		for(var i=0;(i<hotSpots.length&&i<5);i++){
			var key=hotSpots[i].key;
			var value=hotSpots[i].value;
			cookieText+='<a href="javascript:void(0)" onclick="toPage(\''+value+'\')">'+key+' </a> &nbsp;|&nbsp;';
		}
		cookieText=cookieText.substring(0, cookieText.length-13);
	}
	
	/**
	 * 对象转化json
	 * @param obj
	 * @returns
	 */
	function arrayToString(obj){
		var json="[";
		for(var i=0;i<obj.length;i++){
			json+='{"key":"'+obj[i].key+'","value":"'+obj[i].value+'","times":"'+obj[i].times+'"},';
		}
		if(obj.length>0){
			json=json.substring(0,json.length-1);
		}
		json+="]";
		return json.toString();
	}
	/**
	 * json转化对象
	 * @param str
	 */
	function stringToArray(str){
		str=str.substring(13);
		hotSpots=eval("("+str+")");
	}
	/**
	 * 排序
	 * @param obj cookie集合
	 * @param i 当前下标
	 */
	function setSort(obj,i,before){
		if(obj[i].times>obj[before].times&&before>-1){//当前大于之前的,并且前面不是-1
			hotSpot=obj[i];
			obj[i]=obj[before];
			obj[before]=hotSpot;
			setSort(obj,before,before-1);
		}
	}
	
	$(function(){
		isLogin(function(msg) {
			if(msg.result == 0){
				$("#notLogin").show();
				$("#isLogin").hide();
			}
			if(msg.result == 1){
				$("#notLogin").hide();
				$("#isLogin").show();
				$("#userName").attr("href","javascript:toPage('"+umPath+"/index.html')");
				$("#userName").text(msg.message.loginName);
				
				// 查询未读消息数量 
				getUnreadNum(function(msg) {
					if(msg.result == 0){
						// console.log("用户未登录");
					}
					if(msg.result == 1){
						// 调用查询数量的方法   style="display:none"
						if(msg.message >0){
							var numA = $("#letterNum").parent();
							numA.attr("href",umPath+"/index.html?tab=letter");
							//numA.attr("href","http://127.0.0.1:8181/index.html?tab=letter");
							numA.attr("title","未读消息");
							$("#letterNum").html(msg.message);
						}else{
							$("#letterNum").css("display","none");
						}
					}
				});
			}
		});
	})
	
	//抬头
	/*var header='<div class="ds-top">                                                                                           '
		+'<ul class="ds-top-text clearfix ds-width-1000">                                                                '
		+'<li class="pull-left">你好！欢迎来到青海电子商务平台。</li>                                                                    '
		+'<li class="pull-right" id="notLogin" style="display:none;">                                                                          '
		+'<a href="javascript:toPage(\''+himPath+'/login.html\')" class="ds-top-login">登录</a>                                         '
		+'<span class="ds-fc-c0">|</span>                                                                                '
		+'<a href="javascript:toPage(\''+umPath+'/html/register.html\')" class="ds-top-login">免费注册</a>     '
		+'</li>                                                                                                          '
		+'<li class="pull-right" id="isLogin" style="display:none;">                                                                           '
		+'<a href="#" class="ds-top-login" id="userName"></a>                                                            '
		+'<a  href="#"><span id="letterNum" class="info-badge">12</span></a>                                             '
		+'<span class="ds-fc-c0">|</span>                                                                                '
		+'<a href="javascript:logout()" class="ds-top-login"><i class="fa fa-sign-out"></i> 退出</a>                       '
		+'</li>                                                                                                          '
		+'</ul>                                                                                                          '
		+'</div>                                                                                                         '
		+'<div class="ds-logo ds-width-1000">                                                                            '
		+'<img src="../images/logo.png" alt="" onclick="location.href=\''+himPath+'/html/home.html\'" class="ds-logo-img"/>                                                     '
		+'<div class="ds-header-search pull-right">                                                                      '
		+'<div class="ds-search-w clearfix">                                                                             '
		+'                                                                                                               '
		+'<div class="ds-search-input-div pull-right ml10 mb10">                                                          '
		+'<span class="ds-ico-search"></span>                                                                            '
		+'<input class="ds-search-input" type="text" id="keyId" placeholder="请输入关键字"/>                                   '
		+'<button class="ds-search-btn" onclick="serach()">搜 索</button>                                                         '
		+'</div>  																			'
		+'<select class="ds-search-select pull-right" name="serachType" id="serachType">                                  '
		+'<option value="cim">搜企业</option>                                                                               '
		+'<option value="sell">搜供应</option>                                                                              '
		+'<option value="buy">搜采购</option>                                                                               '
		+'</select>  '
		+'</div>                                                                                                         '
		+'<p class="ds-quick-seacrh mt5" id="hotSpot">                                                                                '
		+'</p>                                                                                                           '
		+'</div>                                                                                                         '
		+'</div>                                                                                                         '
		+'<div class="ds-nav">                                                                                           '
		+'<ul class="ds-nav-ul ds-width-1000">                                                                           '
		+'<li name="tab" id="him" onclick="javascript:toPage(\''+himPath+'/html/home.html\')">首页</li>                                                        '
		+'<li name="tab" id="cim" onclick="javascript:toPage(\''+cimPath+'/html/company.list.html\')">企业名录</li>                    '
		+'<li name="tab" id="sell"onclick="javascript:toPage(\''+sdimPath+'/html/sellList.html\')">供应信息</li>                       '
		+'<li name="tab" id="buy" onclick="javascript:toPage(\''+sdimPath+'/html/purchaseList.html\')">采购信息</li>                    '
		+'</ul>                                                                                                          '
		+'</div>                                                                                                         ';*/
	
	//document.write("<script src='"+himPath+"/js/page.js'></script>");
	
	//return header;
})