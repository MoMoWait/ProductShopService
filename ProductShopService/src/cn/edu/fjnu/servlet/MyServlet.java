/**
 * 
 */
package cn.edu.fjnu.servlet;

import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.minidev.json.JSONObject;
import cn.edu.fjnu.utils.DBUtils;

/**
 * @author GaoFei
 *
 */
public class MyServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		JSONObject userInfo=new JSONObject();
		String command= request.getParameter("command");
		int userID = Integer.parseInt(request.getParameter("ID"));
		if("1".equals(command)){
			try {
				ResultSet resultSet=DBUtils.getUser(userID);
				if(resultSet.first()){
					userInfo.put("ID", resultSet.getInt(1));
					userInfo.put("name", resultSet.getString(2));
					userInfo.put("sex", resultSet.getString(3));
					userInfo.put("address", resultSet.getString(4));
					userInfo.put("headPhoto", resultSet.getString(5));
					userInfo.put("account", resultSet.getFloat(6));
					userInfo.put("phoneNumber", resultSet.getString(7));
					userInfo.put("password", resultSet.getString(8));
					DBUtils.closeConn();
					response.setContentType("text/json");
					response.setCharacterEncoding("utf-8");
					response.getWriter().write(userInfo.toString());
				}else{
					
					DBUtils.closeConn();
					response.setContentType("text/json;charset=utf-8");
					response.getWriter().write(userInfo.toJSONString());
					
				}
				DBUtils.closeResource(DBUtils.backPreparedStatement, resultSet, null);
				//resultSet.close();
				DBUtils.closeConn();
			} catch (Exception e) {
				// TODO: handle exception
				//DBUtils.closeResource(DBUtils.backPreparedStatement, resultSet, null);
				DBUtils.closeConn();
			}
		}else if("2".equals(command)){
			try {
				String type=request.getParameter("type");
				String value=request.getParameter("value");
				int id=Integer.parseInt(request.getParameter("userID"));
				if(DBUtils.updateUserInfo(userID, type, value)!=-1){
					userInfo.put("state", "success");
				}else{
					userInfo.put("state", "failed");
				}
				response.getWriter().write(userInfo.toJSONString());
			} catch (Exception e) {
				// TODO: handle exception
				DBUtils.closeConn();
			}
		}else if("3".equals(command)){
			int id=Integer.parseInt(request.getParameter("userID"));
			String orignPassword=request.getParameter("orignPassword");
			String newPassword=request.getParameter("newPassword");
			//String commitPassword=request.getParameter("commitPassword");
			String changeState =DBUtils.changePassword(id, orignPassword, newPassword);
			if(changeState==null){
				userInfo.put("changeState", "unknowError");
			}else if("orignPasswordError".equals(changeState)){
				userInfo.put("changeState", "orignPasswordError");
				
			}else if("changeSuccess".equals(changeState)){
				
				userInfo.put("changeState", "changeSuccess");
			}else{
				userInfo.put("changeState", "unknowError");
			}
			
			response.getWriter().write(userInfo.toJSONString());
			
			
		}
		
	}
}
