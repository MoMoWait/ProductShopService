/**
 * 
 */
package cn.edu.fjnu.servlet;

import java.util.List;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.minidev.json.JSONObject;
import cn.edu.fjnu.domain.CommonProduct;
import cn.edu.fjnu.utils.DBUtils;

/**
 * @author GaoFei
 * 我的关注服务
 */
public class WatchServlet extends HttpServlet {

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
		//super.doPost(req, resp);
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/json");
		response.setCharacterEncoding("utf-8");
		String command=request.getParameter("command");
		JSONObject data=new JSONObject();
		if("1".equals(command)){
			try {
				int userID=Integer.parseInt(request.getParameter("userID"));
				List<CommonProduct> products= DBUtils.getMyWatchs(userID);
				data.put("products",products);
				response.getWriter().write(data.toJSONString());
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
		}else if("2".equals(command)){
			try {
				int userID=Integer.parseInt(request.getParameter("userID"));
				int productID=Integer.parseInt(request.getParameter("productID"));
				String state =DBUtils.addOrDeleteWatch(userID, productID);
				System.out.println(state);
				if(state!=null){
					if("deleteSuccess".equals(state)){
						data.put("state", "deleteSuccess");
					}else if("deleteFailed".equals(state)){
						data.put("state", "deleteFailed");
					}else if("addSuccess".equals(state)){
						data.put("state", "addSuccess");
					}else if("addFailed".equals(state)){
						data.put("state", "addFailed");
					}
				}
				
				response.getWriter().write(data.toJSONString());
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}else if("3".equals(command)){
			try {

				int userID=Integer.parseInt(request.getParameter("userID"));
				int productID=Integer.parseInt(request.getParameter("productID"));
				boolean isExist=DBUtils.isWatchProduct(userID, productID);
				if(isExist){
					data.put("exist", "true");
				}else{
					data.put("exist", "false");
				}
				
				response.getWriter().write(data.toJSONString());
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
	}
	
	
}
