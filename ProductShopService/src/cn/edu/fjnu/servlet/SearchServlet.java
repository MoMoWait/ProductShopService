package cn.edu.fjnu.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.minidev.json.JSONObject;
import cn.edu.fjnu.domain.CommonProduct;
import cn.edu.fjnu.utils.DBUtils;

/**
 * @author GaoFei
 * 
 *
 */
public class SearchServlet extends HttpServlet {

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
				String searchName=request.getParameter("searchName");
				List<CommonProduct> products= DBUtils.getSearchProducts(searchName);
				data.put("products",products);
				response.getWriter().write(data.toJSONString());
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
		}
	}
}
