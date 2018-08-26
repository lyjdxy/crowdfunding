package com.crazy.crowdfunding.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.crazy.crowdfunding.bean.User;

/**
 * 登录拦截器
 */
public class LoginInterceptor implements HandlerInterceptor {

	/**
	 * 在控制器执行之前调用该拦截器方法
	 * 返回true表示继续往下执行，false表示不往下执行
	 */
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String path = request.getSession().getServletContext().getContextPath();

		//判断用户是否登录，从session中看看能不能获取到
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("user");
		if(loginUser == null){
			//如果不存在已经登录的用户
			response.sendRedirect(path + "/login");
			return false;
		}else{
			//如果存在，则通过并往下执行控制器
			return true;
		}
		
	}

	/**
	 * 在控制器执行完成之后，渲染视图之前，调用该拦截器方法
	 */
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
	}

	/**
	 * 在渲染视图之后
	 */
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
	}

}
