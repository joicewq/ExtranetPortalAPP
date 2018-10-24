var defaultLogo="../images/pic-1.jpg"; 

var pb={
		pageSize:8,
		pageNum:1,
		list:[]
};
var companys="";
var selectCompanyId="";
service.pageList(pb,function(data_){
	var list="";
	var msg="";
	if(data_.result==1){
		if(data_.message){
			for(var i=0;i<data_.message.list.length;i++){
				var logo=data_.message.list[i].logoUrl;
				if(logo){
					list+="<li val="+data_.message.list[i].companyId+" class=\"ds-company-img\" id=\"companyInfo_"+data_.message.list[i].companyId+"\"><img src=\""+picPath+data_.message.list[i].logoUrl+"\" width=\"100%\" height=\"100%\"></li>";
				}else{
					list+="<li val="+data_.message.list[i].companyId+" class=\"ds-company-img\" id=\"companyInfo_"+data_.message.list[i].companyId+"\"><img src=\""+defaultLogo+"\" width=\"100%\" height=\"100%\"></li>";
				}
				if(i==0){
					msg="<p class=\"text-orange font16 mb10\">"+data_.message.list[i].companyName+"</p>";
					msg+=data_.message.list[i].introduce;
					$("#companyInfo").html(msg);
					if(data_.message.list[i].logoUrl){
						$("[name=logoUrl]").attr("src",picPath+data_.message.list[i].logoUrl);
					}else{
						$("[name=logoUrl]").attr("src",defaultLogo);
					}
					selectCompanyId=data_.message.list[i].companyId;
					$("[name='logoUrl']").bind("click", function() {
						window.open(cimPath+"/html/company.info.html?companyId="+selectCompanyId);
					});
				}
			}
			companys=data_.message.list;
			$("#companys").html(list);
			$("[id^=companyInfo]").bind("click",function(){
				for(var i=0;i<companys.length;i++){
					if(companys[i].companyId==$(this).attr("val")){
						selectCompanyId=companys[i].companyId;
						msg="<p class=\"text-orange font16 mb10\">"+companys[i].companyName+"</p>";
						msg+=companys[i].introduce;
						$("#companyInfo").html(msg);
						if(companys[i].logoUrl){
							$("[name='logoUrl']").attr("src",picPath+companys[i].logoUrl);
						}else{
							$("[name='logoUrl']").attr("src",defaultLogo);
						}
					}
				}
			});
		}
	}else{
		console.error(data_.message);
	}
});


