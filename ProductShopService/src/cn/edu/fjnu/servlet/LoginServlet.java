/**
 * 
 */
package cn.edu.fjnu.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.minidev.json.JSONObject;
import cn.edu.fjnu.domain.User;
import cn.edu.fjnu.utils.DBUtils;

/**
 * @author GaoFei
 * 登录服务
 */
public class LoginServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, resp);
	}
	
	
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String userName=request.getParameter("userName");
		String password=request.getParameter("password");
		JSONObject loginState=new JSONObject();
		/**
		 * 用户名和密码合法
		 */
		
		if(DBUtils.isUserValidate(userName, password)){
			
			loginState.put("loginState", "success");
			
			
		}else{
			loginState.put("loginState", "failed");
		}
		
		response.setContentType("text/json");
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(loginState.toJSONString());
		
	}
}
