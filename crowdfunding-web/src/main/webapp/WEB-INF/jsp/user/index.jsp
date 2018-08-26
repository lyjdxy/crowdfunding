<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<link rel="stylesheet"
	href="${APP_PATH }/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="${APP_PATH }/css/font-awesome.min.css">
<link rel="stylesheet" href="${APP_PATH }/css/main.css">
<style>
.tree li {
	list-style-type: none;
	cursor: pointer;
}

table tbody tr:nth-child(odd) {
	background: #F4F4F4;
}

table tbody td:nth-child(even) {
	color: #C00;
}
</style>
</head>

<body>

	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="container-fluid">
			<div class="navbar-header">
				<div>
					<a class="navbar-brand" style="font-size: 32px;" href="#">众筹平台
						- 用户维护</a>
				</div>
			</div>
			<div id="navbar" class="navbar-collapse collapse">
				<ul class="nav navbar-nav navbar-right">
					<li style="padding-top: 8px;">
						<div class="btn-group">
							<button type="button"
								class="btn btn-default btn-success dropdown-toggle"
								data-toggle="dropdown">
								<i class="glyphicon glyphicon-user"></i> ${user.name } <span
									class="caret"></span>
							</button>
							<ul class="dropdown-menu" role="menu">
								<li><a href="#"><i class="glyphicon glyphicon-cog"></i>
										个人设置</a></li>
								<li><a href="#"><i class="glyphicon glyphicon-comment"></i>
										消息</a></li>
								<li class="divider"></li>
								<li><a href="${APP_PATH }/logout"><i
										class="glyphicon glyphicon-off"></i> 退出系统</a></li>
							</ul>
						</div>
					</li>
					<li style="margin-left: 10px; padding-top: 8px;">
						<button type="button" class="btn btn-default btn-danger">
							<span class="glyphicon glyphicon-question-sign"></span> 帮助
						</button>
					</li>
				</ul>
				<form class="navbar-form navbar-right">
					<input type="text" class="form-control" placeholder="Search...">
				</form>
			</div>
		</div>
	</nav>

	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-3 col-md-2 sidebar">
				<div class="tree">
					<%@include file="/WEB-INF/jsp/common/menu.jsp" %>
				</div>
			</div>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">
							<i class="glyphicon glyphicon-th"></i> 数据列表
						</h3>
					</div>
					<div class="panel-body">
						<form class="form-inline" role="form" style="float: left;">
							<div class="form-group has-feedback">
								<div class="input-group">
									<div class="input-group-addon">查询条件</div>
									<input id="queryText" class="form-control has-success" type="text"
										placeholder="请输入查询条件">
								</div>
							</div>
							<button id="queryBtn" type="button" class="btn btn-warning">
								<i class="glyphicon glyphicon-search"></i> 查询
							</button>
						</form>
						<button type="button" class="btn btn-danger" onclick="deleteUsers()"
							style="float: right; margin-left: 10px;">
							<i class=" glyphicon glyphicon-remove"></i> 删除
						</button>
						<button type="button" class="btn btn-primary"
							style="float: right;" onclick="window.location.href='${APP_PATH}/user/add'">
							<i class="glyphicon glyphicon-plus"></i> 新增
						</button>
						<br>
						<hr style="clear: both;">
						<div class="table-responsive">
							<form id="userForm">
							<table class="table  table-bordered">
								<thead>
									<tr>
										<th width="30">#</th>
										<th width="30"><input type="checkbox" id="allSelBox"></th>
										<th>账号</th>
										<th>名称</th>
										<th>邮箱地址</th>
										<th width="100">操作</th>
									</tr>
								</thead>
								
								<tbody id="userData">

								</tbody>
								
								<tfoot>
									<tr>
										<td colspan="6" align="center">
											<ul class="pagination">

											</ul>
										</td>
									</tr>

								</tfoot>
							</table>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script src="${APP_PATH }/jquery/jquery-2.1.1.min.js"></script>
	<script src="${APP_PATH }/bootstrap/js/bootstrap.min.js"></script>
	<script src="${APP_PATH }/script/docs.min.js"></script>
	<script src="${APP_PATH }/layer/layer.js"></script>
	<script type="text/javascript">
	
		//是否进行模糊查询的条件
		var likeflg = false;
		
		$(function() {
			$(".list-group-item").click(function() {
				if ($(this).find("ul")) {
					$(this).toggleClass("tree-closed");
					if ($(this).hasClass("tree-closed")) {
						$("ul", this).hide("fast");
					} else {
						$("ul", this).show("fast");
					}
				}
			});

			//页面异步加载动态数据
			pageQuery(1);

			//是否切换模糊查询
			$("#queryBtn").click(function(){
				var queryText = $("#queryText").val();
				if(queryText == ""){
					likeflg = false;
				}else{
					likeflg = true;
				}
				
				pageQuery(1);
			});
			
			//全选
			/*方法一：
			$("#allSelBox").click(function(){
				var flg = this.checked;
				$("#userData :checkbox").each(function(){
					this.checked = flg;
				});
			}); */
			//方法二
			$("#allSelBox").click(function(){
				//attr获取checked是undefined
				//这些DOM的原生属性，应该用prop()方法来获取就不会undefined
				//attr用于自定义的属性
				$(".checkItem").prop("checked",$(this).prop("checked"));
			});
			
			//单个选择框都被选中时，全选框也要选中，反之亦然
			$(document).on("click",".checkItem",function(){
				//判断 		当前被选中的框数                       是否等于  页面中所显示的框数
				var flag = $(".checkItem:checked").length==$(".checkItem").length;
				$("#allSelBox").prop("checked",flag);
			});
			
		});
		$("tbody .btn-success").click(function() {
			window.location.href = "assignRole.html";
		});
		$("tbody .btn-primary").click(function() {
			window.location.href = "edit.html";
		});
		
		

		function pageQuery(pageNo) {
			var loadimg = null;
			
			var jsonData = {"pageNo" : pageNo, "pageSize" : 5 };
			if(likeflg == true){
				jsonData.queryText = $("#queryText").val();
			}
			
			$.ajax({
				type : "POST",
				url : "${APP_PATH}/user/pageQuery",
				data : jsonData	,
				beforeSend : function() {
					loadimg = layer.msg('处理中...', { icon : 16 });
				},
				success : function(result) {
					layer.close(loadimg);
					if (result.success) {
						//局部刷新数据	
						var tableContext = "";
						var pageContext = "";

						var userPage = result.data;
						var users = userPage.datas;

						//拼凑动态的用户数据
						$.each(users, function(i, user) {
							tableContext += '<tr>';
							tableContext += '<td>'+(i+1)+'</td>';
							tableContext += '<td><input type="checkbox" class="checkItem" name="userid" value="'+user.id+'"></td>';
							tableContext += '<td>'+ user.account +'</td>';
							tableContext += '<td>'+ user.name +'</td>';
							tableContext += '<td>'+ user.email +'</td>';
							tableContext += '<td>';
							tableContext += '<button type="button" onclick="goAssignPage('+ user.id +')" class="btn btn-success btn-xs">';
							tableContext += '<i class=" glyphicon glyphicon-check"></i>';
							tableContext += '</button>';
							tableContext += '<button type="button" onclick="goUpdatePage('+ user.id +')" class="btn btn-primary btn-xs">';
							tableContext += '<i class=" glyphicon glyphicon-pencil"></i>';
							tableContext += '</button>';
							tableContext += '<button type="button" onclick="deleteUser('+ user.id +',&apos;'+ user.account +'&apos;)" class="btn btn-danger btn-xs">';
							tableContext += '<i class=" glyphicon glyphicon-remove"></i>';
							tableContext += '</button>';
							tableContext += '</td>';
							tableContext += '</tr>';
						});
						
						//拼凑动态的页码条
						if(pageNo > 1){
							pageContext += '<li><a href="#" onclick="pageQuery('+(pageNo-1)+')">上一页</a></li>';
						}
						
						for(var i = 1; i<=userPage.totalNo; i++){
							if(i==pageNo){
								pageContext += '<li class="active"><a href="#">'+i+'</a></li>';
							}else{
								pageContext += '<li><a href="#" onclick="pageQuery('+i+')">'+i+'</a></li>';
							}
						}
						
						if(pageNo < userPage.totalNo){
							pageContext += '<li><a href="#" onclick="pageQuery('+(pageNo+1)+')">下一页</a></li>';
						}

						$("#userData").html(tableContext);
						$(".pagination").html(pageContext);
					} else {
						layer.msg("用户信息分页查询失败", {time : 1000,icon : 5,shift : 6}, function() {});
					}
				}

			});
		}
		
		//跳转修改页
		function goUpdatePage(id){
			window.location.href="${APP_PATH}/user/edit?id="+id;
		}
		
		//批量删除
		function deleteUsers(){
			var boxes = $("#userForm :checked");
			if(boxes.length == 0){
				layer.msg("请选中要删除的用户信息",{time : 1000,icon : 5,shift : 6},function(){});
			}else{
				layer.confirm("确认删除选中的所有用户吗？",{icon:3,title:'提示'}, function(cindex) {
				console.log($("#userForm").serialize());
					$.ajax({
						type : "POST",
						url : "${APP_PATH}/user/deleteBatch",
						data : $("#userForm").serialize(),	//serialize表单序列化
						success : function(result) {
							if (result.success) {
								layer.msg("批量删除成功", {time : 1000, icon : 6}, function() {
									pageQuery(1);
									});
							}else{
								layer.msg("用户批量删除失败", {time : 1000,icon : 5,shift : 6}, function() {});
							}
						}
					});
					layer.close(cindex);
				},function(cindex){
					layer.close(cindex);
				});
			}
		}
		
		//删除
		function deleteUser(id, account){
			layer.confirm("确认删除【"+ account +"】用户吗？",{icon:3,title:'提示'}, function(cindex) {

				var loadimg = null;
				$.ajax({
					type : "POST",
					url : "${APP_PATH}/user/delete",
					data : {"id":id},
					beforeSend : function() {
						loadimg = layer.msg('处理中...', { icon : 16 });
					},
					success : function(result) {
						layer.close(loadimg);
						if (result.success) {
							pageQuery(1);
						}else{
							layer.msg("用户删除失败", {time : 1000,icon : 5,shift : 6}, function() {});
						}
					}
				});
				layer.close(cindex);
			},function(cindex){
				layer.close(cindex);
			});
		}
		
		function goAssignPage(id){
			window.location.href="${APP_PATH}/user/toAssign?id="+id;
		}
		
	</script>
</body>
</html>
