package cn.edu.fjnu.servlet;


import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import cn.edu.fjnu.domain.CommonProduct;
import cn.edu.fjnu.utils.DBUtils;

/**
 * Servlet implementation class HomeServlet
 */
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private String param="1";
    private static final String[] types=new String[]{"花果山","朋香阁","其他"};
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		param=request.getParameter("command");
		if(param.equals("1"))
			responseHoteProduct(request, response);
	}

	
	public void responseHoteProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		try {
			//System.out.println("hp1");
			request.setCharacterEncoding("utf-8");
			JSONObject hoteProduct=new JSONObject();
			JSONArray hoteArray = new JSONArray();
			List<CommonProduct> products= DBUtils.getHotelProducts();
			hoteArray.addAll(products);
			hoteProduct.put("hoteProducts", hoteArray);
			response.setContentType("text/json");
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(hoteProduct.toJSONString());
			//DBUtils.closeConn();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			//System.out.println(e);
		}
		
	}
	
}
