


	var currentDate = []; // 当前页面的数据 
	var checkedNum = 0;   // 选中的数量 
	
	var pb={
			pages:1,
			pageSize:5,
			pageNum:1,
			list:[]
	};
	
	
		
	
	/**
	 * 时间格式化
	 */
	function timeStamp2String(time){
	    var datetime = new Date();
	    datetime.setTime(time);
	    var year = datetime.getFullYear();
	    var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
	    var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
	    var hour = datetime.getHours()< 10 ? "0" + datetime.getHours() : datetime.getHours();
	    var minute = datetime.getMinutes()< 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
	    var second = datetime.getSeconds()< 10 ? "0" + datetime.getSeconds() : datetime.getSeconds();
	    return year + "-" + month + "-" + date;
	}

	
	/**
	 * 页面加载初始化
	 */
	showTemplateTable(pb.pageNum,pb.pageSize);
	

	/**
	 * 获取查询条件
	 * @returns
	 */
	function getParam(){
		var letter = {};
		var noticeTitle = $("#noticeTitle").val();
		if(noticeTitle && noticeTitle.length>56){
    		alert('标题长度需在56字以内');
    		noticeTitle="";
    	}
		if(noticeTitle && noticeTitle.indexOf("%")>-1){
			alert('查询条件不能包含特殊字符%');
			noticeTitle="";
		}
		letter.notTitle = noticeTitle;
		return letter;
	}

	/**
	 * 查询
	 */
	function search(){
		var pageNum=1;
		var pageSize=5;
		showTemplateTable(pageNum,pageSize);	
	}

	/**
	 * 改变每页显示的条数
	 */
	function selectPageSize(){
		var pageSize=5;
		var pageNum=$("#pageNum").val();
		showPage(pageNum,pageSize);
	}

	/**
	 * 跳转
	 */
	function jumpPage(){
		var pageSize=5;
		var pageNum=$("#pageNum").val();
		
		if(pageNum == undefined || pageNum == null || pageNum == ""){
			alert("请填写正整数");
			return;
		}
		if(pageNum<1){
			pageNum = 1;
		}
		
		if(pageNum > pb.pages || isNaN(pageNum)){
			alert("请填写正整数");
			return;
		}
		showTemplateTable(pageNum,pageSize);
	}

	/**
	 * 首页,上一页,下一页,尾页
	 * @param pageNum
	 */
	function pageTo(pageNum){
		var pageSize=$("#pageSize").val();
		showPage(pageNum,pageSize);
	}

	/**
	 * 实现分页
	 * @param url
	 * @param param
	 */
	function showPage(pageNum,pageSize){
		
//		var param=getParam();
		var url="/stationLetter/getLetterList/"+pageNum+"/"+pageSize+".do";
		var letterTitle = $("#letterTitle").val();
		var param={};
		param.receiveId = "userId3 ";
		param.letterTitle = letterTitle;
		
//		var param={receiveId:"userId1 "};
		showTemplateTable(url,param,"pageTemplate","tableTemplate","tableTemplateTxt");
	}

	/**
	* 实现分页查询
	* url 请求路径
	* param 参数
	* pageTemplateId 分页信息所放的div ID
	* tableTemplateId 生成的表放在哪div中(div ID)
	* tableTemplateTxtId 表的模版
	*/
	function showTemplateTable(pageNum,pageSize){
		
		pb.pageNum=pageNum;
		if(pageNum<1){
			pb.pageNum=$("#pageNum").val();
		}
		if(isNaN(pb.pageNum)){
			alert("请输入正整数");
			return;
		}
		pb.pageNum=((pb.pageNum>pb.pages)?pb.pages:pb.pageNum);
		
		if(pb.pageNum<1){
			pb.pageNum=1;
		}
		pb.pageSize=pageSize;
		
		var param = getParam();
		// param.receiveId = backResult;
		$.ajax({  
			url: "/notice/getNoticeList/"+pb.pageNum+"/"+pageSize+".do", //url  action是方法的名称
			type: 'POST',
			contentType:"application/json; charset=UTF-8",
			data: JSON.stringify(param),
			success : function(result){
				
				if(result.result == 0){
					alert(result.message);
				}else{
					pb.pages=result.message.pages;
					// 将 查询的数据放入 全局变量中  currentDate  
					currentDate = result.message.list;
					
					//支持多个分页div
					/*$('#page').setTemplateElement("pageMsgTemplate");
					$('#page').processTemplate(result.message);*/
					$("#page").html(pages(result.message));
					//数据填充
					$("#table").setTemplateElement("tableTemplateTxt");
					$("#table").processTemplate(result.message);
					// 查询的数据放在页面后在 赋值 点击事件     
					
					$("#letterListBody").find("input[name='letterListOne']").each(function(i,ele){
						if(ele){
							$(ele).click(function(){
								if(this.checked){
									checkedNum++;
								}else{
									checkedNum--;
								}
								if(currentDate.length == checkedNum){
									$("#selectAll")[0].checked = true;
								}else{
									$("#selectAll")[0].checked = false;
								}
							})
						}
					})
					
					// 全选  
					$("#selectAll").click(function(){
						if(this.checked){
							$("#letterListBody").find("input[name='letterListOne']").each(function(i,ele){
								// alert( $(ele).attr("checked") );
								if(ele){
									$(ele)[0].checked = true;
								}
							})
						}else{
							$("#letterListBody").find("input[name='letterListOne']").each(function(i,ele){
								// alert( $(ele).attr("checked") );
								if(ele){
									$(ele)[0].checked = false;
								}
							})
						}
						
					})
					
				}
			},
			error : function(XMLHttpRequest, textStatus) {
				alert("获取数据失败!");
			}
		});
	}
	
	function pageSetting (num,obj){
		if(obj){
			if( isNaN($('#pageNum').val()) || parseInt($('#pageNum').val()) < 1 ){
				$('#pageNum').val(1)
			}
			if( parseInt($('#pageNum').val()) > parseInt(num) ){
				$('#pageNum').val(num);
			}
		}
	}