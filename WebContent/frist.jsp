<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
       <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<script src="js/jquery-3.2.1.js"></script>
<script>
$(document).ready(function(){
	 $.ajax({
	 	 url:"select", //请求的路径
	 	 type:"get",//请求方式   
	 	 cache:false,
	 	 async:false,//是否异步请求,修改false就表示同步,true表示异步
	 	 dataType:"json",
		 success:function(result){
			for (var i = 0; i< result.length; i++) {
				var a=result[i];
				$("#table").append("<tr><td>"+a.id+"</td><td>"+a.name+"</td><td>"+a.sex+"</td><td>"+a.age+"</td><td><input type='button' value='删除' onclick='del(this)'><input type='button' value='修改' onclick='modify(this)'></tr>");
			}
  	 },//请求成功,进入该方法
	   	 error:function(XMLHttpRequest, textStatus, errorThrown)
	   	 {
	   		 alert("后台发生错误!!");
	   	 }
	}); 
	 
	$("#add").click(function(){
		$("#table").append("<tr><td><input type='text'/></td><td><input type='text'/></td><td><input type='text'/></td><td><input type='text'/></td><td><input type='button' value='保存' onclick='save(this)'/><input type='button' value='取消' onclick='cancel(this)'/></td></tr>");
	});
});
//取消
function cancel(a){
	if(confirm("是否确认要删除这一行？")){
		$(a).parent().parent().remove();
	}
}
//保存
function save(a)
{
	var tr = $(a).parent().parent();
	var id = tr.find("td").eq(0).find("input").val();
	var name = tr.find("td").eq(1).find("input").val();
	var sex = tr.find("td").eq(2).find("input").val();
	var age = tr.find("td").eq(3).find("input").val();
	//
	//调用ajax把数据进行保存到数据库,添加到数据时,判断ID是否存在,如果存在不添加
	var mark = true;
	$.ajax({
			 	 url:"add",//请求的路径
			 	 type:"get",//请求方式   
			 	 async:false,//是否异步请求,修改false就表示同步,true表示异步
			 	 data:{"id":id},//传递参数.json格式(这个明天再说)
				 success:function(result){
					 if(result == "1")
					 {
						 alert("添加ID已存在,请重新输入");
						 mark = false;
					 }
		    	 },//请求成功,进入该方法
		    	 error:function(XMLHttpRequest, textStatus, errorThrown)
		    	 {
		    		 alert("后台发生错误!!");
		    	 }
		 	});
	
	if(mark)
	{
		$.ajax({
		 	 url:"add",//请求的路径
		 	 type:"post",//请求方式   
		 	 async:false,//是否异步请求,修改false就表示同步,true表示异步
		 	 data:{"id":id,"name":name,"sex":sex,"age":age},//传递参数.json格式(这个明天再说)
			 success:function(result){
				 if(result == "0")
				 {
					 tr.html("<td>"+id+"</td><td>"+name+"</td><td>"+sex+"</td><td>"+age+"</td><td><input type='button' value='删除' onclick='del(this)'><input type='button' value='修改' onclick='modify(this)'></td>");
				 }
				 else
				 {
					alert("后台添加失败");		 
				 }
	    	 },//请求成功,进入该方法
	    	 error:function(XMLHttpRequest, textStatus, errorThrown)
	    	 {
	    		 alert("后台发生错误!!");
	    	 }
	 	});
		
	}
	
	
}


//修改
function modify(a){
	var tr = $(a).parent().parent();
	var id=tr.find("td").eq(0).text();
	tr.html("<td>"+id+"</td><td><input type='text' /></td><td><input type='text' /></td><td><input type='text' /></td><td><input type='button' value='修改后保存' onclick='preservation(this)' /><input type='button' value='取消修改'  onclick='quxiao(this)'/></td>");
}

//修改后保存
function  preservation(a){
	var tr=$(a).parent().parent();
	var id=tr.find("td").eq(0).text();
	var name=tr.find("td").eq(1).find("input").val();
	var sex=tr.find("td").eq(2).find("input").val();
	var age=tr.find("td").eq(3).find("input").val();
	$.ajax({
	 	 url:"update",//请求的路径 
	 	 async:true,//是否异步请求,修改false就表示同步,true表示异步
	 	 data:{
	 		 "id":id,
	 		 "name":name,
	 		 "sex":sex,
	 		 "age":age 
	 	 },//传递参数.json格式(这个明天再说)
		 success:function(data){
			tr.html("<td>"+id+"</td><td>"+name+"</td><td>"+sex+"</td><td>"+age+"</td><td><center><input type='button' onclick='del(this)'  value='删除' /><input type='button' onclick='modify(this)' value='修改' /></center></td>");		
			alert("修改成功");
		 }
	});
}

//删除
function del(a)
{
	if(confirm("你是否确认要进入删除操作")){
		var tr = $(a).parent().parent();
		var idTr = tr.find("td").eq(0).text();//把ID通过AJAX传入后台进行删除
		$.ajax({
		 	 url:"delete",//请求的路径 
		 	 async:false,//是否异步请求,修改false就表示同步,true表示异步
		 	 data:{"id":idTr},//传递参数.json格式(这个明天再说)
			 success:function(){
				 tr.find("td").remove();		
			},
	    	 error:function(XMLHttpRequest, textStatus, errorThrown)
	    	 {
	    		 alert("后台发生错误!!");
	    	 },
	 	});
	}
}
</script>
</head>
<body>
	<input type="button" id="add" value="添加" />
	<table id="table" border="1">
		<tr>
			<th>编号</th>
			<th>姓名</th>
			<th>性别</th>
			<th>年龄</th>
			<th >操作</th>
		</tr>
	</table>
		<form action="test">
			<input type="submit" value="导出" />
		</form>
		<form action="download" enctype="multipart/form-data">
		<input type="file" id="file" name="fiel"><br /> 
		<input	type="submit" value="导入">
	</form>
</body>
</html>