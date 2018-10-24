require.config(config);
require(["jquery","base","pager"],function($,base,pager){
	var pageSize = 10;

 	var url = "/supplier/queryHistoryrecord";
 	var showDeafaultTable=pager.methods.showDeafaultTable= function(pageNo, pageSize) {
 		pager.methods.showTemplateTable(pageNo, pageSize, url, "pagination", "view", "tmpl",
 				base.serializeObject($("#rf")),afterSuccess);
 	}
	//日期格式转换
	var afterSuccess=function(){
		$("#tableTemplate tr").each(function() {
			var tdEle = $(this).find("td").eq(0);
			if(tdEle.text().length > 0 && tdEle.text() !="" &&  tdEle.text() !="无符合条件数据" ) {
				var d = new Date(parseInt(tdEle.text()));
				tdEle.text(d.Format("yyyy-MM-dd"));
			}
		});
		$("#tableTemplate tr").each(function() {
			var tdEle = $(this).find("td").eq(1);
			if(tdEle.text().length > 0 && tdEle.text() !="" &&  tdEle.text() !="无符合条件数据") {
				var d = new Date(parseInt(tdEle.text()));
				tdEle.text(d.Format("yyyy-MM-dd"));
			}
		});
	}
	//列表状态转换
	pager.methods.stautsChange=function(approvalStatus){
		var stauts="";
		var intnumber = approvalStatus.toString();
		if(intnumber.length>=5){
			var stautsBH = intnumber.substr(intnumber.length-4);
			if(stautsBH=="0000"){
			  var status = intnumber.substr(0,intnumber.length-4);
			  if(status==1){
				  return stauts="局长驳回";
			  }else if(status==10){
				  return stauts="处长驳回";
			  }else if(status==12){
				  return stauts="科长驳回";
			  }
			}
		}else if(intnumber.length>=3 && intnumber.length<=4){
			var stautsBH = intnumber.substr(intnumber.length-2);
			if(stautsBH=="00"){
			  var status = intnumber.substr(0,intnumber.length-2);
			  if(status==1){
				  return stauts="处长撤销";
			  }else if(status==10){
				  return stauts="科长撤销";
			  }else if(status==2){
				  return stauts="局长撤销";
			  }
			}
			
		}
		if(approvalStatus==1){
			return stauts="科长审核同意";
		}else if(approvalStatus==2){
			return stauts="处长审核同意";
		}else if(approvalStatus==3){
			return stauts="局长审核同意";
		}else if(approvalStatus==11){
			return stauts="已告知";
		}
	}
	pager.pagerGlobal(pager.methods);
	$(function($) {
		pager.methods.showDeafaultTable(1, pageSize);
		
	});
});