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

	<link rel="stylesheet" href="${APP_PATH}/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${APP_PATH}/css/font-awesome.min.css">
	<link rel="stylesheet" href="${APP_PATH}/css/main.css">
	<style>
	.tree li {
        list-style-type: none;
		cursor:pointer;
	}
	table tbody tr:nth-child(odd){background:#F4F4F4;}
	table tbody td:nth-child(even){color:#C00;}
	</style>
  </head>

  <body>

    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container-fluid">
        <div class="navbar-header">
          <div><a class="navbar-brand" style="font-size:32px;" href="#">众筹平台 - 角色维护</a></div>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav navbar-right">
            <li style="padding-top:8px;">
				<div class="btn-group">
				  <button type="button" class="btn btn-default btn-success dropdown-toggle" data-toggle="dropdown">
					<i class="glyphicon glyphicon-user"></i> ${user.name } <span class="caret"></span>
				  </button>
					  <ul class="dropdown-menu" role="menu">
						<li><a href="#"><i class="glyphicon glyphicon-cog"></i> 个人设置</a></li>
						<li><a href="#"><i class="glyphicon glyphicon-comment"></i> 消息</a></li>
						<li class="divider"></li>
						<li><a href="${APP_PATH }/logout"><i class="glyphicon glyphicon-off"></i> 退出系统</a></li>
					  </ul>
			    </div>
			</li>
            <li style="margin-left:10px;padding-top:8px;">
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
				<h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
			  </div>
			  <div class="panel-body">
<form class="form-inline" role="form" style="float:left;">
  <div class="form-group has-feedback">
    <div class="input-group">
      <div class="input-group-addon">查询条件</div>
      <input class="form-control has-success" type="text" id="likeSel" placeholder="请输入查询条件">
    </div>
  </div>
  <button type="button" id="likeBtn" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
</form>
<button type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
<button type="button" id="insertBtn" class="btn btn-primary" style="float:right;"><i class="glyphicon glyphicon-plus"></i> 新增</button>
<br>
 <hr style="clear:both;">
          <div class="table-responsive">
            <table class="table  table-bordered">
              <thead>
                <tr >
                  <th width="30">#</th>
				  <th width="30"><input type="checkbox" id="allSelBox"></th>
                  <th>名称</th>
                  <th width="100">操作</th>
                </tr>
              </thead>
              <tbody id="roleItem">
                
              </tbody>
			  <tfoot>
			     <tr >
				     <td colspan="6" align="center">
						<ul class="pagination" id="pageItem">
						</ul>
					 </td>
				 </tr>

			  </tfoot>
            </table>
          </div>
			  </div>
			</div>
        </div>
      </div>
    </div>

    <script src="${APP_PATH}/jquery/jquery-2.1.1.min.js"></script>
    <script src="${APP_PATH}/bootstrap/js/bootstrap.min.js"></script>
	<script src="${APP_PATH}/script/docs.min.js"></script>
	<script src="${APP_PATH}/layer/layer.js"></script>
        <script type="text/javascript">
        
	     	//是否进行模糊查询的条件
			var likeflg = false;
        
            $(function () {
			    $(".list-group-item").click(function(){
				    if ( $(this).find("ul") ) {
						$(this).toggleClass("tree-closed");
						if ( $(this).hasClass("tree-closed") ) {
							$("ul", this).hide("fast");
						} else {
							$("ul", this).show("fast");
						}
					}
				});
			    pageQuery(1);
			    
			    //是否切换模糊查询
			   $("#likeBtn").click(function(){
				   var likeSel = $("#likeSel").val();
					if(likeSel == ""){
						likeflg = false;
					}else{
						likeflg = true;
					}
					pageQuery(1);
			   });
            });
            
            //全选与全不选
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
            
            function pageQuery(pageNo){
            	
            	var jsonData = {"pageNo":pageNo, "size":5};
            	if(likeflg){
            		jsonData.likeSel = $("#likeSel").val();
            	}
            	
            	$.ajax({
            		type:"POST",
            		url:"${APP_PATH}/role/list",
            		data:jsonData,
            		success:function(result){
            			if(result.success){
                			var RoleContext = "";
                			var pageContext = "";
                			
                			$.each(result.data.datas,function(i,role){
                				RoleContext += '<tr>';
                				RoleContext += '<td>'+ (i+1) +'</td>';
                				RoleContext += '<td><input type="checkbox" class="checkItem"></td>';
                				RoleContext += '<td>'+ role.roleName +'</td>';
                				RoleContext += '<td>';
                				RoleContext += '<button type="button" onclick="goAssignPage('+role.id+')" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>';
                				RoleContext += '<button type="button" onclick="toEdit('+ role.id +')" class="btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></button>';
                				RoleContext += '<button type="button" onclick="deleteRole('+ role.id +', \''+ role.roleName +'\')" class="btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></button>';
                				RoleContext += '</td>';
                				RoleContext += '</tr>';
                			});
                			$("#roleItem").html(RoleContext);
                			
                			
                			if(result.data.pageNo > 1){
                				pageContext += '<li><a href="#" onclick="pageQuery('+(pageNo-1)+')">上一页</a></li>';
                			}
                			console.log(result.data.totalNo);
                			for(var i = 1; i <= result.data.totalNo; i++){
                				if(i==pageNo){
    								pageContext += '<li class="active"><a href="#">'+i+'</a></li>';
    							}else{
    								pageContext += '<li><a href="#" onclick="pageQuery('+i+')">'+i+'</a></li>';
    							}
                			}
                			if(result.data.pageNo < result.data.totalNo){
                				pageContext += '<li><a href="#" onclick="pageQuery('+(pageNo+1)+')">下一页</a></li>';
                			}
                			$("#pageItem").html(pageContext);
            			}else{
            				layer.msg("角色信息分页查询失败", {time : 1000,icon : 5,shift : 6}, function() {});
            			}
            			
            		}
            	});
            }
            
            $("#insertBtn").click(function(){
            	window.location.href = "${APP_PATH}/role/add";
            });
            
            $("tbody .btn-success").click(function(){
                window.location.href = "assignPermission.html";
            });
            
            function toEdit(id){
            	window.location.href = "${APP_PATH}/role/edit?id="+id;
            }
            
            function deleteRole(id,roleName){
            	layer.confirm("确认删除【"+ roleName +"】角色吗？", {icon:3,title:'提示'}
            			,function(cindex){
            				layer.close(cindex);
            				
            				$.ajax({
            					url:"${APP_PATH}/role/delete",
            					data:{"id":id},
            					success: function(result){
            						if(result.success){
            							layer.msg("角色删除成功", {time : 800,icon : 6}, function() {
            								window.location.href="${APP_PATH}/role/index";
            							});
            						}else{
            							layer.msg("角色删除失败", {time : 800,icon : 5,shift : 6}, function() {});
            						}
            					}
            				});
            				
            			},function(cindex){
							layer.close(cindex);
            			});
            }
            
            function goAssignPage(id) {
            	window.location.href = "${APP_PATH}/role/assign?id="+id;
            }
        </script>
  </body>
</html>
