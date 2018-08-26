<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-CN">
  <head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="keys" content="">
    <meta name="author" content="">
	<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="css/font-awesome.min.css">
	<link rel="stylesheet" href="css/login.css">
	<style>

	</style>
  </head>
  <body>
    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <div><a class="navbar-brand" href="index.html" style="font-size:32px;">尚筹网-创意产品众筹平台</a></div>
        </div>
      </div>
    </nav>

    <div class="container">

      <form id="loginFrom" action="dologin" method="post" class="form-signin" role="form">
        <h2 class="form-signin-heading"><i class="glyphicon glyphicon-user"></i> 用户登录</h2>
		  <div class="form-group has-success has-feedback">
			<input type="text" class="form-control" name="loginAccount" id="loginAccount" placeholder="请输入登录账号" autofocus>
			<span class="glyphicon glyphicon-user form-control-feedback"></span>
		  </div>
		  <div class="form-group has-success has-feedback">
			<input type="text" class="form-control" name="loginPwd" id="loginPwd" placeholder="请输入登录密码" style="margin-top:10px;">
			<span class="glyphicon glyphicon-lock form-control-feedback"></span>
		  </div>
		  <div class="form-group has-success has-feedback">
			<select class="form-control" >
                <option value="member">会员</option>
                <option value="user">管理</option>
            </select>
		  </div>
        <div class="checkbox">
          <label>
            <input type="checkbox" value="remember-me"> 记住我
          </label>
          <br>
          <label>
            忘记密码
          </label>
          <label style="float:right">
            <a href="reg.html">我要注册</a>
          </label>
        </div>
        <a class="btn btn-lg btn-success btn-block" onclick="dologin()" > 登录</a>
      </form>
    </div>
    <script src="${APP_PATH }/jquery/jquery-2.1.1.min.js"></script>
    <script src="${APP_PATH }/bootstrap/js/bootstrap.min.js"></script>
    <script src="${APP_PATH }/layer/layer.js"></script>
    <script>
    function dologin() {
    	//验证
        var loginAcc = $("#loginAccount").val();
        var loginPwd = $("#loginPwd").val();
        if(loginAcc == ""){
        	layer.msg("用户名不能为空", {time:1000, icon:5, shift:6}, function(){});
        	return;
        }
        if(loginPwd == ""){
        	layer.msg("密码不能为空", {time:1000, icon:5, shift:6}, function(){});
        	return;
        }
        //表单提交
        //$("#loginFrom").submit();
        
        //采用AJAX表单提交
        var loadimg = null;
        $.ajax({
        	type : "POST",
        	url : "doAJAXLogin",
        	data : {
        		"Account" : loginAcc,
        		"password" : loginPwd
        	},
        	//提交请求时，小圆圆等待提示
        	beforeSend : function(){
        		loadimg = layer.msg('处理中...',{icon:16});
        	},
        	success : function(result){
        		//关掉小圆圈
        		layer.close(loadimg);
        		if(result.success){
        			window.location.href = "main";
        		}else{
        			layer.msg("用户名和密码不匹配或不存在，请重新登录", {time:1000, icon:5, shift:6}, function(){});
        		}
        	}
        });
        
    }
    </script>
  </body>
</html>