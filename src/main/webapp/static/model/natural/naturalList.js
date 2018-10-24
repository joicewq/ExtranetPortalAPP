require.config(config);
require(["jquery", "leftMenu", "animation", "datepicker", "vue","pager","base"], function($, menu, animation, datepicker, Vue,pager,base) {
	var pageSize = 10;
	var companyUrl="/spi/query";
	var productUrl = "/ni/query";
	var naturalUrl="/ni/naturalQuery"
	var showDeafaultTable = pager.methods.showDeafaultTable = function(pageNo, pageSize,params) {
		var data={
			beginDate:$("#startDTime").val(),
			endDate:$("#endDTime").val(),
			issueTitle:$("#spec").val()
		};
		var url,temp,params;
		if($(".menu-a.active").text()=="企业信息公示"){
			url=companyUrl;
			temp="tmp1";
		}
		else if($(".menu-a.active").text()=="产品信息公示"){
			url=productUrl;
			temp="tmp3";
			data.status="1";
		}
		else if($(".menu-a.active").text()=="检测结果公示"){
			url=naturalUrl;
			temp="tmp4";
			data.columnName="检测公示";
		}
		else if($(".menu-a.active").text()=="检测不合格结果公示"){
			url=productUrl;
			temp="tmp2";
			data.status="12";
		}
		data=(params==undefined?data:$.extend({},data,params));
		pager.methods.showTemplateTable(pageNo, pageSize, url,"pagination","policies-list-items",temp,data);		
	}
	pager.pagerGlobal(pager.methods);
	var menuData={
			"title": "检测公示",
			"icon":"iconfont icon-shuzizhengwuyingjixuanchuan01",
			"menu":[

				{
					"name": "企业信息公示"
				},
				{
					"name": "检测结果公示"
				/*	"childs":[
						          {
						        	  "name":"检测合格结果公示",
						          },
						          {
						        	  "name":"检测不合格结果公示"
						          }
					          ]*/
				},
				{
					"name": "产品信息公示"
				}
			]
		};
	$(function() {
		animation.load({
			container:$("#container")
		});
		menu.setmenu("#menu", menuData,function(ele){
			/*if(ele.text()=="备案企业公示"){
				showCompanyTable({});
			}
			else if(ele.text()=="检测合格结果公示"){
				showProductTable({status:"11"});
			}
			else if(ele.text()=="检测不合格结果公示"){
				showProductTable({status:"12"});
			}*/
			$("#spec").val("");
			if(ele.next("ul").length==0){
				showDeafaultTable(1,pageSize);				
			}
		});
		showDeafaultTable(1,pageSize);
		//showDeafaultTable(1, pageSize,companyUrl,{});
		$("#queryBtn").on("click",function(){
			var params = {
				beginDate:$("#startDTime").val(),
				endDate:$("#endDTime").val(),
				issueTitle:$("#spec").val()
			};
			showDeafaultTable(1,pageSize,params);
		})
	})
})