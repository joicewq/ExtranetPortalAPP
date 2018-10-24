$(document).ready(function() {
	function cycleTop (pics1) {
            var p = $('#picplayer1'); 
            // 此数据应该是请求后台返回的数据       -- 数据怎么返回那？？？？  
            /*var pics1 = [
                         {url:'http://114.242.237.81:10037/images/banner.jpg',link:'http://www.jb51.net/#'},
                         {url:'http://114.242.237.81:10037/images/banner.jpg',link:'http://www.baidu.com'},
                         {url:'http://114.242.237.81:10037/images/banner.jpg',link:'http://www.baidu.com'},
                         {url:'http://114.242.237.81:10037/images/banner.jpg',link:'http://www.so.com'}
                        ]; */
            initPicPlayer1(pics1,p.css('width').split('px')[0],p.css('height').split('px')[0]); 
            // 
            // 
            function initPicPlayer1(pics,w,h){ 
            	 //选中的图片 
                var selectedItem; 
              //选中的按钮 
                var selectedBtn; 
              //自动播放的id 
                var playID; 
              //选中图片的索引
                var selectedIndex; 
              //容器 
                var p = $('#picplayer1'); 
                p.text(''); 
                p.append('<div id="piccontentT"></div>'); 
                var c = $('#piccontentT'); 
                for(var i=0;i<pics.length;i++){ 
                	//添加图片到容器中
                    c.append('<a href="'+pics[i].link+'" target="_blank"><img id="picitemT'+i+
                    		'" style="display: none;z-index:'+i+'" src="'+pics[i].url+'" /></a>'); 
                } 
                // 如果图片数量大于1才滚动播放
                if(pics.length>1){
                	
                	//按钮容器，绝对定位在右下角
                	p.append('<div id="picbtnHolder1" '+
                			'style="position:absolute;top:'+(h-25)+'px;width:'+w+'px;height:20px;z-index:10000;"></div>'); 
                	// 
                	var btnHolder = $('#picbtnHolder1'); 
                	btnHolder.append('<div id="picbtns1" style="text-align:center; padding-right:1px;"></div>'); 
                	var btns = $('#picbtns1'); 
                	// 
                	for(var i=0;i<pics.length;i++) { 
                		//增加图片对应的按钮   class="ds-banner-btn active"
                		btns.append('<span id="picbtnT'+i+'"  class="ds-banner-btn" '+
                		'style="cursor:pointer; border:solid 1px #ccc;background-color:#eee; display:inline-block;"></span> '); 
                		$('#picbtnT'+i).data('index',i); 
                		$('#picbtnT'+i).click( function(){ 
                			if(selectedIndex == $(this).data('index')){ 
                				return; 
                			} 
                			setSelectedItem($(this).data('index')); 
                		} 
                		); 
                	} 
                	btns.append(' '); 
                	/// 
                	setSelectedItem(0); 
                	//显示指定的图片index 
                }else{
                	// 图片数量等于1，则只显示第一张图片 
                	selectedItem = $('#picitemT'+0); 
                	//淡入淡出
                	selectedItem.fadeIn('slow');
                }
                
            	
                function setSelectedItem(index) { 
                	selectedIndex = index; 
                	clearInterval(playID); 
                	//alert(index); 
                	if(selectedItem){
                		//淡入淡出
                		selectedItem.fadeOut('fast');
                	} 
                	selectedItem = $('#picitemT'+index); 
                	//淡入淡出
                	selectedItem.fadeIn('slow'); 
                	// 
                	if(selectedBtn){ 
                		selectedBtn.css('backgroundColor','#eee'); 
                		selectedBtn.css('color','#000'); 
                	} 
                	selectedBtn = $('#picbtnT'+index); 
                	selectedBtn.css('backgroundColor','#000'); 
                	selectedBtn.css('color','#fff'); 
                	//自动播放
                	playID = setInterval(function() { 
                		var index = selectedIndex+1; 
                		if(index > pics.length-1){
                			
                			index=0; 
                		}
                		setSelectedItem(index); 
                	},5000); 
                } 
           }
	}
	
	function cycleBottom (pics1) {
		var p = $('#picplayer2'); 
		// 此数据应该是请求后台返回的数据       -- 数据怎么返回那？？？？  
		/*var pics1 = [
                         {url:'http://114.242.237.81:10037/images/banner.jpg',link:'http://www.jb51.net/#'},
                         {url:'http://114.242.237.81:10037/images/banner.jpg',link:'http://www.baidu.com'},
                         {url:'http://114.242.237.81:10037/images/banner.jpg',link:'http://www.baidu.com'},
                         {url:'http://114.242.237.81:10037/images/banner.jpg',link:'http://www.so.com'}
                        ]; */
		initPicPlayer2(pics1,p.css('width').split('px')[0],p.css('height').split('px')[0]); 
		// 
		// 
		function initPicPlayer2(pics,w,h){ 
			//选中的图片 
			var selectedItem; 
			//选中的按钮 
			var selectedBtn; 
			//自动播放的id 
			var playID; 
			//选中图片的索引
			var selectedIndex; 
			//容器 
			var p = $('#picplayer2'); 
			p.text(''); 
			p.append('<div id="piccontent2"></div>'); 
			var c = $('#piccontent2'); 
			for(var i=0;i<pics.length;i++){ 
				//添加图片到容器中
				c.append('<a href="'+pics[i].link+'" target="_blank"><img id="picitemB'+i+
						'" style="display: none;z-index:'+i+'" src="'+pics[i].url+'" /></a>'); 
			} 
			
			// 如果图片数量大于1才滚动播放
            if(pics.length>1){
				//按钮容器，绝对定位在右下角
				p.append('<div id="picbtnHolder2" '+
						'style="position:absolute;top:'+(h-25)+'px;width:'+w+'px;height:20px;z-index:10000;"></div>'); 
				// 
				var btnHolder = $('#picbtnHolder2'); 
				btnHolder.append('<div id="picbtns2" class="ds-control-xm-img" style="text-align:center; padding-right:1px;"></div>'); 
				var btns = $('#picbtns2'); 
				// 
				for(var i=0;i<pics.length;i++) { 
					//增加图片对应的按钮   class="ds-banner-btn active"
					btns.append('<i id="picbtnB'+i+'"  class="fa fa-circle ds-control-icon"'+
					'"></i> '); 
					$('#picbtnB'+i).data('index',i); 
					$('#picbtnB'+i).click( function(){ 
						if(selectedIndex == $(this).data('index')){ 
							return; 
						} 
						setSelectedItem($(this).data('index')); 
					} 
					); 
				} 
				btns.append(' '); 
				/// 
				setSelectedItem(0); 
            }else{
            	// 图片数量等于1，则只显示第一张图片 
            	selectedItem = $('#picitemB'+0); 
            	//淡入淡出
            	selectedItem.fadeIn('slow');
            }
			//显示指定的图片index 
			function setSelectedItem(index) { 
				selectedIndex = index; 
				clearInterval(playID); 
				//alert(index); 
				if(selectedItem){
					//淡入淡出
					selectedItem.fadeOut('fast');
				} 
				selectedItem = $('#picitemB'+index); 
				//淡入淡出
				selectedItem.fadeIn('slow'); 
				// 
				if(selectedBtn){ 
					//selectedBtn.css('backgroundColor','#fff'); 
					selectedBtn.css('color','#fff'); 
				} 
				selectedBtn = $('#picbtnB'+index); 
				//selectedBtn.css('backgroundColor','#fff'); 
				selectedBtn.css('color','#69aaec'); 
				//自动播放
				playID = setInterval(function() { 
					var index = selectedIndex+1; 
					if(index > pics.length-1){
						
						index=0; 
					}
					setSelectedItem(index); 
				},5000); 
			} 
		}
	}


	/**
	 * 查询首页广告
	 * @param location
	 */
	function getTopAdvList(location){
		var advert = {};
		advert.advLocation = location;
		/*advert.orderNum = "0";*/
		$.ajax({
			url: "/commonController/getAdvertisementList/1/20.do", //url  action是方法的名称
			type: 'POST',
			contentType:"application/json; charset=UTF-8",
			data: JSON.stringify(advert),
			success :function(result){
				if(result && result.message){
					// 查询出来后再执行页面的轮播代码
					if(result.result == 1){
						var pics=[];
						var list = result.message.list;
						if(list){
							for(var i=0;i<list.length;i++){
								var advertObj = {};
								advertObj.url =picPath+list[i].advFile ;
								advertObj.link = list[i].advUrl;
								pics.push(advertObj);
							}
							// 执行轮播
							if(location == 1){
								cycleTop (pics);
							}else if(location == 2){
								cycleBottom (pics);
							}
						}
					}else{
						alert(result.message);
					}
				}
			},
			error : function(msg) {
				alert("操作失败，"+msg.statusText);
			}
		})
	};
	
	getTopAdvList("1");
	getTopAdvList("2");
})