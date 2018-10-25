<html>
<script type="text/javascript" src="/static/js/jquery-1.11.0.min.js" ></script>
<head>
<script type="text/javascript">
/* $(function(){
	if (window["context"] == undefined) {
	    if (!window.location.origin) {
	      window.location.origin = window.location.protocol + "//" + window.location.hostname + (window.location.port ? ':' + window.location.port : '');
	    }
	 	 window["context"] = location.origin + "/V6.0";
	  }
}); */
function getEnv(){
	$.post("/portal/getEnv",{},
	function(data,status){	
		docUrl=data.docAppUrl+docUrl;
		console.log("data",data);
		localStorage.setItem('EVN_RUL_DATA', data);
	});	 
}
$(function(){
	getEnv();
	location.href = "/salt/index";
})
	
</script>
</head>
</html>
