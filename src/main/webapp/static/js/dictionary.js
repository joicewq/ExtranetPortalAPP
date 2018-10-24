//	初始化粮食类型
	service.queryGrains(null,function(data_){
		var option='';
		var num=0;
		if(data_.result==1){
			if(data_.message){
				for(var i=0;i<data_.message.length;i++){
					var msg=data_.message[i];
					if(i%4==0){
						option+='<li>                                                    ';
					}
						if(i%2==0){
							option+='	<ul class="ds-food clearfix">                       ';
						}
							option+='		<li><a  target="_blank" href="url'+msg.grainCode+'" class="ds-food-a">'+msg.typeName+'</a></li>    ';
						if((i-1)%2==0){
							option+='	</ul>                                               ';
						}
					if((i-3)%4==0){
						num++;
						option+='</li>                                                   ';
					}
				}
			}
		}else{
			console.error(data_.message);
		}
		var sell=option	;
		var reg=new RegExp("url","g");
		sell=sell.replace(reg, sdimPath+"/html/sellList.html?garinType=");
		$("#sellGrains").html(sell);
		var buy=option	;
			buy=buy.replace(reg, sdimPath+"/html/purchaseList.html?garinType=");
		$("#buyGrains").html(buy);
	});
	//地区
	service.queryAreas({parentId:'000000'},function(data_){
		var option="<dl>";
		if(data_.result==1){
			if(data_.message){
				for(var i=0;i<data_.message.length;i++){
					var msg=data_.message[i];
					option+='<dd style="float:left;width:168px;border:1px solid #fff;"><a href="'+cimPath+'/html/company.list.html?area='+msg.areaCode+'" class="ds-food-a" target="_blank">'+msg.areaName+'</a></dd>';
				}
				option+="</dl>";
			}
		}else{
			console.error(data_.message);
		}
		$("#areas").html(option);
	});