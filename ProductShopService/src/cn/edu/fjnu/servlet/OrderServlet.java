/**
 * 
 */
package cn.edu.fjnu.servlet;

import java.util.List;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.minidev.json.JSONObject;
import cn.edu.fjnu.domain.Order;
import cn.edu.fjnu.domain.OrderDetail;
import cn.edu.fjnu.domain.OrderItemInfo;
import cn.edu.fjnu.utils.DBUtils;

/**
 * @author GaoFei
 * 用户下单服务
 */
public class OrderServlet extends HttpServlet{

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
		JSONObject data=new JSONObject();
		String command=request.getParameter("command");
		if("1".equals(command)){
			try {
				/**生成订单时间*/
				Date date=new Date();
				SimpleDateFormat sFormat=new SimpleDateFormat("yyyyMMddhhmmssSSS");
				String userID=request.getParameter("ID");
				String orderID=sFormat.format(date)+userID;
				sFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String orderTime= sFormat.format(new Date());
				String expireTime=request.getParameter("expireTime");
				String orderState="未发货";
				String orderMoney=request.getParameter("orderMoney");
				String city = request.getParameter("city");
				String unit = request.getParameter("unit");
				String fromType = request.getParameter("fromType");
				Order order=new Order();
				order.setOrderID(orderID);
				order.setUserID(Integer.parseInt(userID));
				order.setOrderTime(orderTime);
				order.setExpireTime(expireTime);
				order.setOrderState(orderState);
				order.setOrderMoney(Float.parseFloat(orderMoney));
				order.setCity(city);
				DBUtils.addRoWToOrder(order);
				
				
				String productId=request.getParameter("productID");
				String productNum=request.getParameter("productNumber");
				String []productIDs= productId.split(";");
				String []productNumbs=productNum.split(";");
				String []units = unit.split(";");
				for(int i=0;i<productIDs.length;i++){
					//DBUtils.addRoWToOrderDetail(o)
					OrderDetail detail=new OrderDetail();
					detail.setOrderID(orderID);
					detail.setProductID(Integer.parseInt(productIDs[i]));
					detail.setProductNumber(Integer.parseInt(productNumbs[i]));
					detail.setUnit(units[i]);
					DBUtils.addRoWToOrderDetail(detail);
				}
				System.out.println("fromType:"+fromType);
				if("2".equals(fromType)){
					
					for(int i=0;i<productIDs.length;i++){
						DBUtils.deleteRowFromCarShop(Integer.parseInt(userID), Integer.parseInt(productIDs[i]),units[i]);
					}
					
					
				}
				
				
				
				/**更改用户账户余额*/
				DBUtils.desAccount(Integer.parseInt(userID), Float.parseFloat(orderMoney));
				//DBUtils.deleteRowFromCarShop(Integer.parseInt(userID), Integer.parseInt(productIDs[0]));
				
				data.put("orderState", "success");
				response.getWriter().write(data.toString());
				
			} catch (Exception e) {
				// TODO: handle exception
				data.put("orderState", "failed");
				response.getWriter().write(data.toJSONString());
				e.printStackTrace();
			}
		}else if("2".equals(command)){
			
			try {
				String userID=request.getParameter("userID");
				List<OrderItemInfo> infos=DBUtils.getOrderItemInfos(Integer.parseInt(userID));
				//JSONArray infoArrays=new JSONArray();
				data.put("orderInfos", infos);
				response.getWriter().write(data.toJSONString());
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
		}else if("3".equals(command)){
			try {
				String userID=request.getParameter("userID");
				List<OrderItemInfo> infos=DBUtils.getFinishOrder(Integer.parseInt(userID));
				//JSONArray infoArrays=new JSONArray();
				data.put("orderInfos", infos);
				response.getWriter().write(data.toJSONString());
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		
	}
}
