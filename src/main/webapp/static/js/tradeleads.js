//最新供应信息模块数据渲染
service.querySells (null,function(data_){
	if(data_&&data_.list){
		var sells="";
		for(var i=0;i<data_.list.length;i++){
			var item = data_.list[i];
			sells+=  '<tr>'
					+'<td>'+item.type+'</td>'
					+'<td>'+item.type2+'</td>'
					+'<td>'+item.type3+'</td>'
					+'<td>'+item.location+'</td>'
					+'<td>'+item.location2+'</td>'
					+'<td>'+item.brand+'</td>'
					+'<td>'+item.purity+'</td>'
					+'</tr>';
		}
		$("#sells").find("tbody").html(sells);
	}
					
});

//最新销售信息模块数据渲染
service.queryBuys (null,function(data_){
	if(data_&&data_.list){
		var buys="";
		for(var i=0;i<data_.list.length;i++){
			var defaultFile="../images/pic-160-01.jpg";
			if(data_.list[i].fileUrl){
				defaultFile=picPath + data_.list[i].fileUrl;
			}
			buys+=  '<li class="ds-commodity-li pull-left" onclick="javascript:window.open(\''+sdimPath+'/html/purchaseDetails.html?purchaseId='+data_.list[i].purchaseId+'\')">  '  
					+'	<img src="'+defaultFile+'" width="160px" height="160px"/>        ' 
					+'	<p class="ds-small-title font12" style="overflow: hidden; text-overflow:ellipsis;white-space:nowrap;width: 160px;" title="'+data_.list[i].purchaseTitle+'">'+data_.list[i].purchaseTitle+'</p>  ' 
					+'	<span class="ds-msg ds-msg-orange"></span>                 '  
					+'	<span class="ds-msg-text">采购</span>                       '  
					+'</li>';
		}
		$("#buys").html(buys);
	}
});