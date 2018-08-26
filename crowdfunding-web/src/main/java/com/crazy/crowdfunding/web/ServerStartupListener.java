package com.crazy.crowdfunding.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ServerStartupListener implements ServletContextListener {

	public ServerStartupListener() {
	}

	public void contextDestroyed(ServletContextEvent sce) {
	}

	public void contextInitialized(ServletContextEvent sce) {
		// 在web应用启动后，获取web应用的路径名，保存在application域中
		ServletContext application = sce.getServletContext();
		String path = application.getContextPath();
		System.out.println(path);
		application.setAttribute("APP_PATH", path);
	}

}
