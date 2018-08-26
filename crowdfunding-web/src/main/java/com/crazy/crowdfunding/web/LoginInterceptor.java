package com.crazy.crowdfunding.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.crazy.crowdfunding.bean.User;

/**
 * ��¼������
 */
public class LoginInterceptor implements HandlerInterceptor {

	/**
	 * �ڿ�����ִ��֮ǰ���ø�����������
	 * ����true��ʾ��������ִ�У�false��ʾ������ִ��
	 */
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String path = request.getSession().getServletContext().getContextPath();

		//�ж��û��Ƿ��¼����session�п����ܲ��ܻ�ȡ��
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("user");
		if(loginUser == null){
			//����������Ѿ���¼���û�
			response.sendRedirect(path + "/login");
			return false;
		}else{
			//������ڣ���ͨ��������ִ�п�����
			return true;
		}
		
	}

	/**
	 * �ڿ�����ִ�����֮����Ⱦ��ͼ֮ǰ�����ø�����������
	 */
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
	}

	/**
	 * ����Ⱦ��ͼ֮��
	 */
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
	}

}
