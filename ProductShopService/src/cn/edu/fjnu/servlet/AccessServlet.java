package cn.edu.fjnu.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.minidev.json.JSONObject;
import cn.edu.fjnu.utils.DBUtils;

/**
 * @author GaoFei
 * ÆÀ¼Û·þÎñ
 */
public class AccessServlet extends HttpServlet {

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
		response.setContentType("text/json");
		response.setCharacterEncoding("utf-8");
		String command=request.getParameter("command");
		JSONObject data=new JSONObject();
		if("1".equals(command)){
			try {
				String userID=request.getParameter("userID");
				String orderID=request.getParameter("orderID");
				String accessContent=request.getParameter("accessContent");
				if(DBUtils.addRowToAccess(orderID,Integer.parseInt(userID),  accessContent)!=-1){
					data.put("accessState", "success");
				}else{
					data.put("accessState", "failed");
				}
				//data.put("products",products);
				response.getWriter().write(data.toJSONString());
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
		}
	}
	
	
}
