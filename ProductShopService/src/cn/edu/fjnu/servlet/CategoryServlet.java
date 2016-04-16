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
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import cn.edu.fjnu.utils.DBUtils;

/**
 * @author GaoFei
 * 为APP分类页面提供服务
 */
public class CategoryServlet extends HttpServlet {

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
		try {
			//System.out.println("hp1");
			JSONObject hoteProduct;
			JSONArray hoteProducts=new JSONArray();
			String param=request.getParameter("command");
			int type= Integer.parseInt(param);
			ResultSet reSet;
			if(type==1){
				reSet = DBUtils.getCategroyProduct("花果山");
			}else if(type==2){
				reSet = DBUtils.getCategroyProduct("朋香阁");
			}else if(type==3){
				reSet = DBUtils.getCategroyProduct("生鲜");
			}else{
				reSet = DBUtils.getCategroyProduct("其他");
			}
			
			while (reSet.next()) {
				// System.out.println("1");
				hoteProduct = new JSONObject();
				hoteProduct.put("productID", reSet.getString(1));
				hoteProduct.put("productName", reSet.getString(2));
				hoteProduct.put("productType", reSet.getString(3));
				hoteProduct.put("productDes", reSet.getString(4));
				hoteProduct.put("productPrice", reSet.getString(5));
				hoteProduct.put("productPhoto", reSet.getString(6));
				hoteProduct.put("producttBagPrice", reSet.getString(7));
				hoteProducts.add(hoteProduct);
				//hoteProducts.put(hoteProduct);

			}
			
			response.setContentType("text/json");
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(hoteProducts.toJSONString());
			DBUtils.closeResource(DBUtils.backPreparedStatement, reSet, null);
			DBUtils.closeConn();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
}
