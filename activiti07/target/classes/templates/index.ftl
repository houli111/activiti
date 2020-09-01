<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"/>
<title>Activiti6流程设计器Demo</title>
<!-- 新 Bootstrap4 核心 CSS 文件 -->
<link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/4.3.1/css/bootstrap.min.css">
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>
<!-- bootstrap.bundle.min.js 用于弹窗、提示、下拉菜单，包含了 popper.min.js -->
<script src="https://cdn.staticfile.org/popper.js/1.15.0/umd/popper.min.js"></script>
<!-- 最新的 Bootstrap4 核心 JavaScript 文件 -->
<script src="https://cdn.staticfile.org/twitter-bootstrap/4.3.1/js/bootstrap.min.js"></script>

</head>
<body>
<h1>
	流程模型已保存
</h1>
<#--<div id="sucessbackinfo"  style="width:30%;float:right;right: 46%;display: none" class="alert alert-success alert-dismissible">
	<button type="button" class="close" data-dismiss="alert">&times;</button>
	<span id="sucessmessage"></span>
</div>
<div id="errorbackinfo"  style="width:30%;float:right;right: 46%;display: none" class="alert alert-danger alert-dismissible">
  <button type="button" class="close" data-dismiss="alert">&times;</button>
  <span id="errormessage"></span>
</div>
<div style="margin: 20px;">
	<br>
	  <h4>
		  <a href='/create?name=activiti&key=123456'>+绘制流程</a>
	  </h4>
	<div style="margin-top: 10px">
	<table  class="table table-striped table-bordered table-hover">
	    <tr>
	        <td width="10%">模型编号</td>
	        <td width="10%">版本</td>
	        <td width="20%">模型名称</td>
	        <td width="20%">模型key</td>
	        <td width="40%">操作</td>
	    </tr>
	        <#list modelList as model>
	        <tr>
	            <td width="10%">${model.id}</td>
	            <td width="10%">${model.version}</td>
	            <td width="20%"><#if (model.name)??>${model.name}<#else> </#if></td>
	            <td width="20%"><#if (model.key)??>${model.key}<#else> </#if></td>
	            <td width="40%">
	             <a href="/editor?modelId=${model.id}">编辑</a>
			     <button onclick="publish(${model.id})" type="button" class="btn btn-link">发布</button>
			     <button onclick="revokePublish(${model.id})" type="button" class="btn btn-link">撤销</button>
			     <button onclick="delete(${model.id})" type="button" class="btn btn-link">删除</button>
	            </td>
	        </tr>
	       </#list>
	</table>
	</div>
</div>-->
</body>
<script>
   // function publish(id){
   //     $.get("/publish?modelId="+id,
	// 	   function(data,status){
   //             if(data.code == "SUCCESS"){
   //                 $("#sucessmessage").html("成功")
   //                 $("#sucesbackinfo").show();
   //             }else{
   //                 $("#errormessage").html("失败")
   //                 $("#errorbackinfo").show();
   //             }
   //
	// 		setTimeout(function () {
   //              $("#sucesbackinfo").hide();
   //              $("sucesmessage").html("    ");
   //              $("#errorbackinfo").hide();
   //              $("errormessage").html("    ");
   //             }, 3000);
   //
   //     });
   // }
   // function revokePublish(id){
   //     $.get("/revokePublish?modelId="+id,
   //         function(data,status){
   //             if(data.code == "SUCCESS"){
   //                 $("#sucessmessage").html("成功")
   //                 $("#sucesbackinfo").show();
   //             }else{
   //                 $("#errormessage").html("失败")
   //                 $("#errorbackinfo").show();
   //             }
   //             setTimeout(function () {
   //                 $("#sucesbackinfo").hide();
   //                 $("sucesmessage").html("    ");
   //                 $("#errorbackinfo").hide();
   //                 $("errormessage").html("    ");
   //             }, 3000);
   //
   //         });
   // }
   // function deleteModel(id){
   //     $.get("/delete?modelId="+id,
   //         function(data,status){
   //             if(data.code == "SUCCESS"){
   //                 $("#sucessmessage").html("成功")
   //                 $("#sucesbackinfo").show();
   //             }else{
   //                 $("#errormessage").html("失败")
   //                 $("#errorbackinfo").show();
   //             }
   //             setTimeout(function () {
   //                 $("#sucesbackinfo").hide();
   //                 $("sucesmessage").html("    ");
   //                 $("#errorbackinfo").hide();
   //                 $("errormessage").html("    ");
   //             }, 3000);
   //
   //         });
   // }
</script>
</html>