package cn.edu.fjnu.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.minidev.json.JSONObject;
import cn.edu.fjnu.domain.User;
import cn.edu.fjnu.utils.DBUtils;

public class RegisterServlet extends HttpServlet {

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
		request.setCharacterEncoding("utf-8");
		JSONObject registerState=new JSONObject();
		String command=request.getParameter("command");
		if("1".equals(command)){
			String userName=request.getParameter("userName");
			String sex=request.getParameter("sex");
		/*	params.put("address",address);
			params.put("phoneNumber",phoneNumber);
			params.put("password",password);*/
			String address=request.getParameter("address");
			String phoneNumber=request.getParameter("phoneNumber");
			String password=request.getParameter("password");
			User user=new User();
			user.setAccount(0);
			user.setAddress(address);
			user.setHeadPhoto("http://120.24.210.186:8080/ProductShopService/UserHeadPhoto/1.jpg");
			user.setName(userName);
			user.setPhoneNumber(phoneNumber);
			user.setPassword(password);
			user.setSex(sex);
			int userID= DBUtils.addRowToUser(user);
		//	System.out.println(userID);
			//select LAST_INSERT_ID()
			registerState.put("registerState", "success");
			registerState.put("userID", String.valueOf(userID));
			
		}
		
		response.setContentType("text/json");
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(registerState.toJSONString());
	}
}
