/**
 * 
 */
package cn.edu.fjnu.servlet;

import java.util.List;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.minidev.json.JSONObject;
import cn.edu.fjnu.domain.CarShop;
import cn.edu.fjnu.domain.DetailCarShop;
import cn.edu.fjnu.utils.DBUtils;

/**
 * @author GaoFei
 * 购物车服务
 */
public class CarServlet extends HttpServlet {

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
		String command= request.getParameter("command");
		response.setContentType("text/json");
		response.setCharacterEncoding("utf-8");
		if("1".equals(command)){
			int userID=Integer.parseInt(request.getParameter("userID"));
			int productID=Integer.parseInt(request.getParameter("productID"));
			int productNumber=Integer.parseInt(request.getParameter("productNumber"));
			String unit = request.getParameter("unit");
			CarShop carShop=new CarShop();
			carShop.setUserID(userID);
			carShop.setProductID(productID);
			carShop.setProductNumber(productNumber);
			carShop.setUnit(unit);
			JSONObject addToCarShop=new JSONObject();
			if(DBUtils.addRowToCarShop(carShop)!=-1){
				addToCarShop.put("addState", "success");
			}else{
				addToCarShop.put("addState", "failed");
			}
			response.getWriter().write(addToCarShop.toJSONString());
		}else if("2".equals(command)){
			List<DetailCarShop> shops=new ArrayList<DetailCarShop>();
		//	List<De> shops=new ArrayList<E>();
			int userID=Integer.parseInt(request.getParameter("userID"));
			ResultSet resultSet= DBUtils.getCarShops(userID);
			if(resultSet!=null){
				try {
					
					while(resultSet.next()){
						DetailCarShop detailCarShop=new DetailCarShop();
						detailCarShop.setUserID(resultSet.getInt(1));
						detailCarShop.setProductID(resultSet.getInt(2));
						detailCarShop.setProductNumber(resultSet.getInt(3));
						detailCarShop.setProductName(resultSet.getString(4));
						detailCarShop.setProductDes(resultSet.getString(5));
						detailCarShop.setProductPrice(resultSet.getFloat(6));
						detailCarShop.setProductPhoto(resultSet.getString(7));
						detailCarShop.setProductUnit(resultSet.getString(8));
						detailCarShop.setProductBagPrice(resultSet.getFloat(9));
						shops.add(detailCarShop);
						System.out.println(detailCarShop.getProductName());
						System.out.println(detailCarShop.getProductPhoto());
					}
					DBUtils.closeResource(DBUtils.backPreparedStatement, resultSet, null);
					DBUtils.closeConn();
					JSONObject data=new JSONObject();
					data.put("shops", shops);
					response.getWriter().write(data.toJSONString());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					DBUtils.closeConn();
					e.printStackTrace();
				}
			}
		}else if("3".equals(command)){
			//List<DetailCarShop> shops=new ArrayList<DetailCarShop>();
			//	List<De> shops=new ArrayList<E>();
				int userID=Integer.parseInt(request.getParameter("userID"));
				int productID=Integer.parseInt(request.getParameter("productID"));
				String unit = request.getParameter("unit");
				//int productNum = Integer.parseInt(request.getParameter("num"));
				//从数据库中删除
				JSONObject data=new JSONObject();
				if(DBUtils.deleteRowFromCarShop(userID, productID,unit)!=-1){
					
					data.put("deleteState", "success");
				}else{
					data.put("deleteState", "failed");
				}
				response.getWriter().write(data.toJSONString());
		}
	}
}
