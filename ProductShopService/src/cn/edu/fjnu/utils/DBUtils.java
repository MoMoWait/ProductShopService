package cn.edu.fjnu.utils;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import cn.edu.fjnu.domain.CarShop;
import cn.edu.fjnu.domain.CommonProduct;
import cn.edu.fjnu.domain.FeedBack;
import cn.edu.fjnu.domain.HoteProduct;
import cn.edu.fjnu.domain.Order;
import cn.edu.fjnu.domain.OrderDetail;
import cn.edu.fjnu.domain.OrderItemInfo;
import cn.edu.fjnu.domain.User;

/**
 * @author GaoFei
 *数据库操作类
 */
public class DBUtils {
	
	private static Connection connection;
	private static final String ADDPRODUCT="insert into commonproduct(productName,productType,productDes,productPrice,productPhoto)"
			+ " values(?,?,?,?,?)" ;
	private static final String ADDHOTEPRODUCT="insert into hoteproduct(position) values(?)";
	private static final String ADDUSER="insert into user(name,sex,address,headphoto,account,phoneNumber,password) values(?,?,?,?,?,?,?)";
	private static final String ADDCARSHOP="insert into carshop(userID,productID,productNumber,unit) values(?,?,?,?)";
	private static final String UPDATECARSHOP="update carshop set productNumber=productNumber+? where userID=? and productID=? and unit=?";
	private static final String ADDFEADBACK="insert into feedback(userID,content,way,time,ip) values(?,?,?,?,?)";
	private static final String ADDORDER="insert into myorder values(?,?,?,?,?,?,?)";
	private static final String ADDORDERDETAIL="insert into orderdetail(detailID,orderID,productID,productNumber,unit) values(?,?,?,?,?)";
	private static final String DELETECARSHOP="delete from carshop where userID=? and productID=? and unit=?";
	private static final String DESUSERMONEY="update user set account=account-? where ID=?";
	private static final String ADDACCESS="insert into access(orderID,userID,accessContent) values(?,?,?)";
	private static final String DELETEWATCH="delete from mywatch where userID=? and productID=?";
	private static final String ADDWATCH="insert into mywatch(userID,productID) values(?,?) ";
	public static PreparedStatement backPreparedStatement=null;
	//private static final String FETCHHOTEPRODUCT="select "
	public static Connection getConn(){
		try {
			//Class.forName("com.mysql.jdbc.Driver");
		      //建立到MySQL的连接
			//closeConn();
			connection= DataSource.getInstance().getConnection();
		     return connection;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
		 //加载驱动，这一句也可写为：Class.forName("com.mysql.jdbc.Driver");
	     
		//return null;
	}
	
	public static Connection getNormalConn(){
		
	
		try {
			String url = "jdbc:mysql://112.74.77.24/farmshop";
			Class.forName("com.mysql.jdbc.Driver");
			String userName = "root";
			String password = "gf6548914";
			Connection con = DriverManager.getConnection(url,userName,password);
		
			return con;
		} catch (Exception e) {
			
			return null;
		}
		
	}
	
	public static void closeConn() {
		if(connection!=null)
			try {
				connection.close();
				connection = null;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public static int addRowToCommonProduct(CommonProduct commonProduct){
		int rows=-1;
		getConn();
		if(connection!=null){
			try {
				PreparedStatement preparedStatement=connection.prepareStatement(ADDPRODUCT);
				preparedStatement.setString(1, commonProduct.getProductName());
				preparedStatement.setString(2, commonProduct.getProductType());
				preparedStatement.setString(3, commonProduct.getProductDes());
				preparedStatement.setFloat(4, commonProduct.getProductPrice());
				preparedStatement.setString(5, commonProduct.getProductPhoto());
				rows= preparedStatement.executeUpdate();
				//connection.commit();
				preparedStatement.close();
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				try {
					connection.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
			
		}
		
		return rows;
	}
	
	public static int addRowToHotePorduct(HoteProduct hoteProduct){
		int rows=-1;
		getConn();
		if(connection!=null){
			try {
				PreparedStatement preparedStatement=connection.prepareStatement(ADDHOTEPRODUCT);
				preparedStatement.setInt(1, hoteProduct.getPosition());
				rows= preparedStatement.executeUpdate();
				//connection.commit();
				preparedStatement.close();
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				try {
					connection.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
			
		}
		
		return rows;
	}
	
	public static int addRowToUser(User user){
		int result=-1;
		getConn();
		if(connection!=null){
			try {
				PreparedStatement preparedStatement=connection.prepareStatement(ADDUSER);
				preparedStatement.setString(1, user.getName());
				preparedStatement.setString(2, user.getSex());
				preparedStatement.setString(3, user.getAddress());
				preparedStatement.setString(4, user.getHeadPhoto());
				preparedStatement.setFloat(5, user.getAccount());
				preparedStatement.setString(6, user.getPhoneNumber());
				preparedStatement.setString(7, user.getPassword());
				preparedStatement.executeUpdate();
				preparedStatement.close();
				PreparedStatement maxPreparedStatement=connection.prepareStatement("select LAST_INSERT_ID()");
				ResultSet resultSet=maxPreparedStatement.executeQuery();
				if(resultSet.first()){
					result=resultSet.getInt(1);
				}
				maxPreparedStatement.close();
				resultSet.close();
				//connection.commit();
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return result;
	}
	
	public static int addRowFeedBack(FeedBack feedBack){
		
		int rows=-1;
		getConn();
		if(connection!=null){
			try {
				PreparedStatement preparedStatement=connection.prepareStatement(ADDFEADBACK);
				preparedStatement.setInt(1,feedBack.getUsrID());
				preparedStatement.setString(2, feedBack.getContent());
				preparedStatement.setString(3, feedBack.getWay());
				preparedStatement.setDate(4, feedBack.getTime());
				preparedStatement.setString(5, feedBack.getIp());
				rows= preparedStatement.executeUpdate();
				preparedStatement.close();
				closeConn();
			} catch (Exception e) {
				// TODO: handle exception
				closeConn();
			}
		}
		
		return rows;
	}
	
	public static int addRowToAccess(String orderID,int userID,String accessContent){
		
		int rows=-1;
		getConn();
		if(connection!=null){
			try {
				PreparedStatement preparedStatement=connection.prepareStatement(ADDACCESS);
				preparedStatement.setString(1,orderID);
				preparedStatement.setInt(2, userID);
				preparedStatement.setString(3,accessContent);
				rows= preparedStatement.executeUpdate();
				preparedStatement.close();
				connection.close();
				
			} catch (Exception e) {
				// TODO: handle exception
				closeConn();
				e.printStackTrace();
			}
		}
		
		return rows;
	}
	
	public static int addRowToCarShop(CarShop carShop){
		int rows=-1;
		if(isExistCarShop(carShop.getUserID(), carShop.getProductID(),carShop.getUnit())){
			getConn();
			if(connection!=null){
				try {
					PreparedStatement preparedStatement=connection.prepareStatement(UPDATECARSHOP);
					preparedStatement.setInt(1,carShop.getProductNumber());
					preparedStatement.setInt(2, carShop.getUserID());
					preparedStatement.setInt(3, carShop.getProductID());
					preparedStatement.setString(4, carShop.getUnit());
					rows= preparedStatement.executeUpdate();
					//connection.commit();
					preparedStatement.close();
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					try {
						connection.close();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					//closeConn();
					e.printStackTrace();
				}
				
			}
		}else{
			getConn();
			if(connection!=null){
				try {
					PreparedStatement preparedStatement=connection.prepareStatement(ADDCARSHOP);
					preparedStatement.setInt(1,carShop.getUserID());
					preparedStatement.setInt(2, carShop.getProductID());
					preparedStatement.setInt(3, carShop.getProductNumber());
					preparedStatement.setString(4,carShop.getUnit());
					rows= preparedStatement.executeUpdate();
					//connection.commit();
					preparedStatement.close();
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					closeConn();
					e.printStackTrace();
				}
				
			}
		}		
		return rows;
	}
	
	
	
	public static boolean isExistCarShop(int userID,int productID,String unit){
		try {
			getConn();
			String sql="SELECT * from carshop where userID=? and productID=? and unit=?";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setInt(1, userID);
			preparedStatement.setInt(2, productID);
			preparedStatement.setString(3, unit);
			ResultSet rSet=preparedStatement.executeQuery();
			if(rSet.first()){
				preparedStatement.close();
				rSet.close();
				connection.close();
				return true;
			}
			preparedStatement.close();
			rSet.close();
			connection.close();
			return false;
			//ResultSet  connection.createStatement().executeQuery(sql);
			//closeConn();
			//return rSet;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			closeConn();
			return false;
		}
	}
	/**
	 * 从数据库中获取最热商品信息
	 */
	public static List<CommonProduct> getHotelProducts(){
		try {
			//这里不使用c3p0连接
			
			Connection normalConnection = getNormalConn();
			if(normalConnection == null)
				return new ArrayList<CommonProduct>();
			
			List<CommonProduct> hoteProducts=new ArrayList<CommonProduct>();
			String sql="SELECT * FROM commonproduct WHERE productType='花果山' LIMIT 0,4;";
			PreparedStatement preparedStatement=normalConnection.prepareStatement(sql);
			//preparedStatement.setString(1, productType);
			ResultSet rSet=preparedStatement.executeQuery();
			//ResultSet  connection.createStatement().executeQuery(sql);
			//closeConn();
			while(rSet.next()){
				CommonProduct commonProduct=new CommonProduct();
				commonProduct.setProductID(rSet.getInt(1));
				commonProduct.setProductName(rSet.getString(2));
				commonProduct.setProductType(rSet.getString(3));
				commonProduct.setProductDes(rSet.getString(4));
				commonProduct.setProductPrice(rSet.getFloat(5));
				commonProduct.setProductPhoto(rSet.getString(6));
				commonProduct.setProductBagPrice(rSet.getFloat(7));
				//commonProduct.setpr
				//commonProduct.setpr
				hoteProducts.add(commonProduct);
				//commonProduct.setProductName(rSet.getString(2));
			}
			if(rSet!=null)
				rSet.close();
			if(preparedStatement!=null)
				preparedStatement.close();
		//	closeConn();
			
		//	getConn();
			String vegatableSql="SELECT * FROM commonproduct WHERE productType='朋香阁' LIMIT 0,4;";
			PreparedStatement vegatablePreparedStatement=normalConnection.prepareStatement(vegatableSql);
			ResultSet vegatableResultSet=vegatablePreparedStatement.executeQuery();
			while(vegatableResultSet.next()){
				CommonProduct commonProduct=new CommonProduct();
				commonProduct.setProductID(vegatableResultSet.getInt(1));
				commonProduct.setProductName(vegatableResultSet.getString(2));
				commonProduct.setProductType(vegatableResultSet.getString(3));
				commonProduct.setProductDes(vegatableResultSet.getString(4));
				commonProduct.setProductPrice(vegatableResultSet.getFloat(5));
				commonProduct.setProductPhoto(vegatableResultSet.getString(6));
				commonProduct.setProductBagPrice(vegatableResultSet.getFloat(7));
				hoteProducts.add(commonProduct);
				//commonProduct.setProductName(rSet.getString(2));
			}
			if(vegatableResultSet!=null)
				vegatableResultSet.close();
			if(vegatablePreparedStatement!=null)
				vegatablePreparedStatement.close();
		//	closeConn();
			
			
		//	getConn();
			String otherSql="SELECT * FROM commonproduct WHERE productType='其他' LIMIT 0,4;";
			PreparedStatement ohterPreparedStatement=normalConnection.prepareStatement(otherSql);
			ResultSet ohterResultSet=ohterPreparedStatement.executeQuery();
			while(ohterResultSet.next()){
				CommonProduct commonProduct=new CommonProduct();
				commonProduct.setProductID(ohterResultSet.getInt(1));
				commonProduct.setProductName(ohterResultSet.getString(2));
				commonProduct.setProductType(ohterResultSet.getString(3));
				commonProduct.setProductDes(ohterResultSet.getString(4));
				commonProduct.setProductPrice(ohterResultSet.getFloat(5));
				commonProduct.setProductPhoto(ohterResultSet.getString(6));
				commonProduct.setProductBagPrice(ohterResultSet.getFloat(7));
				hoteProducts.add(commonProduct);
				//commonProduct.setProductName(rSet.getString(2));
			}
			if(ohterResultSet!=null)
				ohterResultSet.close();
			if(ohterPreparedStatement!=null)
				ohterPreparedStatement.close();
			if(normalConnection!=null)
				normalConnection.close();
			//closeConn();
			return hoteProducts;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//closeConn();
			return null;
		}
	}
	
	
	/**
	 * 从数据库中获取不同分类的商品
	 */
	public static ResultSet getCategroyProduct(String type){
		try {
			getConn();
			String sql="SELECT * from commonproduct WHERE productType=?";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			backPreparedStatement=preparedStatement;
			preparedStatement.setString(1, type);
			ResultSet rSet=preparedStatement.executeQuery();
		//	preparedStatement.close();
			//ResultSet  connection.createStatement().executeQuery(sql);
			//closeConn();
			return rSet;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			closeConn();
			return null;
		}
	}
	
	/**
	 * 从数据库中获取所有用户信息
	 */
	public static ResultSet getUserInfos(){
		try {
			getConn();
			String sql="SELECT * from user";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			backPreparedStatement=preparedStatement;
			ResultSet rSet=preparedStatement.executeQuery();
			//preparedStatement.close();
			//ResultSet  connection.createStatement().executeQuery(sql);
			//closeConn();
			//preparedStatement.close();
			return rSet;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			closeConn();
			return null;
		}
	}
	
	/***
	 * 获取指定用户ID的购物车数据
	 * @return
	 */
	public static ResultSet getCarShops(int userID){
		try {
			getConn();
			String sql="SELECT userID,carshop.productID,productNumber,productName,productType"+
 " productDes,productPrice,productPhoto,unit,productBagPrice FROM carshop,commonproduct WHERE userID=? AND carshop.productID=commonproduct.productID";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			backPreparedStatement=preparedStatement;
			preparedStatement.setInt(1, userID);
			ResultSet rSet=preparedStatement.executeQuery();
			//ResultSet  connection.createStatement().executeQuery(sql);
			//closeConn();
		//	preparedStatement.close();
			return rSet;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			closeConn();
			return null;
		}
	}
	
	public static List<OrderItemInfo> getOrderItemInfos(int userID){
		try {
			getConn();
			//System.out.println(connection==null);
			String sql="SELECT orderID,orderTime,expireTime,orderState,orderMoney FROM myorder WHERE userID=? "
					+ "ORDER BY orderTime DESC limit 0,100";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			backPreparedStatement=preparedStatement;
			preparedStatement.setInt(1, userID);
			ResultSet rSet=preparedStatement.executeQuery();
			List<OrderItemInfo> infos=new ArrayList<OrderItemInfo>();
			while(rSet.next()){
				OrderItemInfo info=new OrderItemInfo();
				info.setOrderID(rSet.getString(1));
				info.setOrderDate(rSet.getString(2));
				info.setExpireDate(rSet.getString(3));
				info.setOrderState(rSet.getString(4));
				info.setOrderMoney(rSet.getString(5));
				info.setOrderProduct(DBUtils.getDetailOrder(rSet.getString(1)));
				infos.add(info);
			//	System.out.println(info);
			}
			closeResource(preparedStatement, rSet, connection);
			return infos;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			closeConn();
		}
		return null;
	}
	
	public static List<OrderItemInfo> getFinishOrder(int userID){
		try {
			getConn();
			String sql="SELECT myorder.orderID,orderTime,expireTime,orderState,orderMoney FROM myorder WHERE userID=? and orderState='已完成' and orderID NOT in (SELECT orderID FROM access WHERE myorder.userID=access.userID) ORDER BY orderTime DESC";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			backPreparedStatement=preparedStatement;
			preparedStatement.setInt(1, userID);
			ResultSet rSet=preparedStatement.executeQuery();
			List<OrderItemInfo> infos=new ArrayList<OrderItemInfo>();
			while(rSet.next()){
				OrderItemInfo info=new OrderItemInfo();
				info.setOrderID(rSet.getString(1));
				info.setOrderDate(rSet.getString(2));
				info.setExpireDate(rSet.getString(3));
				info.setOrderState(rSet.getString(4));
				info.setOrderMoney(rSet.getString(5));
				info.setOrderProduct(DBUtils.getDetailOrder(rSet.getString(1)));
				infos.add(info);
			}
			closeResource(preparedStatement, rSet, connection);
			return infos;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			closeConn();
		}
		return null;
	}
	
	public static List<CommonProduct> getSearchProducts(String searchName){
		
		try {
			getConn();
			String sql="SELECT * FROM commonproduct WHERE productName LIKE ?";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			backPreparedStatement=preparedStatement;
			preparedStatement.setString(1, "%"+searchName+"%");
			ResultSet rSet=preparedStatement.executeQuery();
			List<CommonProduct> products=new ArrayList<CommonProduct>();
			while(rSet.next()){
				CommonProduct product=new CommonProduct();
				product.setProductID(rSet.getInt(1));
				product.setProductName(rSet.getString(2));
				product.setProductType(rSet.getString(3));
				product.setProductDes(rSet.getString(4));
				product.setProductPrice(rSet.getFloat(5));
				product.setProductPhoto(rSet.getString(6));
				product.setProductBagPrice(rSet.getFloat(7));
				
				products.add(product);
			}
			preparedStatement.close();
			rSet.close();
			closeConn();
			return products;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			closeConn();
		}
		return null;
	}
	
	public static String getDetailOrder(String orderID){
		
		try {
			//getConn();
			String sql="SELECT productNumber,productName,productPrice,productBagPrice,unit "
					+ "from orderdetail,commonproduct where orderID=?"
					+ " AND orderdetail.productID=commonproduct.productID";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setString(1, orderID);
			ResultSet rSet=preparedStatement.executeQuery();
			StringBuffer sBuffer=new StringBuffer();
			while(rSet.next()){
				sBuffer.append(rSet.getString(2)).append("(")
				.append(rSet.getString(3)).append(rSet.getString(5).equals("斤")?rSet.getString(3)+"/斤":rSet.getString(4)+"/袋").append("*").append(rSet.getString(1))
				.append(")\n");
			}
			preparedStatement.close();
			rSet.close();
		//	closeConn();
			return sBuffer.toString();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		//	closeConn();
		}
		return null;
	}
	
	public static int updateUserInfo(int userID,String type,String value){
		int rows=-1;
		getConn();
		try {
			
			String sql=null;
			if(type.equals("name")){
				
				 sql="UPDATE user SET name=? WHERE ID=?";
			
			}else if(type.equals("address")){
				 sql="update user set address=? where ID=?";
				
			}else if(type.equals("sex")){
					
				sql="update user set sex=? where ID=?";
			}else if(type.equals("phone")){
				sql="update user set phoneNumber=? where ID=?";
			}else if(type.equals("headPhoto")){
				sql="update user set headPhoto=? where ID=?";
			}
			
			PreparedStatement preparedStatement= connection.prepareStatement(sql);
			preparedStatement.setString(1, value);
			preparedStatement.setInt(2, userID);
			rows=preparedStatement.executeUpdate();
			preparedStatement.close();
			closeConn();
		} catch (Exception e) {
			// TODO: handle exception
			closeConn();
			e.printStackTrace();
		}
		
		
		return rows;
	}
	
	/**
	 * 从数据库中获取所有指定ID用户信息
	 */
	public static ResultSet getUser(int id){
		try {
			getConn();
			String sql="SELECT * from user where ID=?";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			backPreparedStatement=preparedStatement;
			preparedStatement.setInt(1, id);
			ResultSet rSet=preparedStatement.executeQuery();
			//preparedStatement.close();
			//ResultSet  connection.createStatement().executeQuery(sql);
			//closeConn();
			return rSet;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			closeConn();
			return null;
		}
	}
	
	
	
	public static int addRoWToOrder(Order order){
		int rows=-1;
		try {
			getConn();
			PreparedStatement preparedStatement=connection.prepareStatement(ADDORDER);
			System.out.println("生成的订单ID"+order.getOrderID());
			preparedStatement.setString(1, order.getOrderID());
			preparedStatement.setInt(2, order.getUserID());
			preparedStatement.setString(3, order.getOrderTime());
			preparedStatement.setString(4, order.getExpireTime());
			preparedStatement.setString(5, order.getOrderState());
			preparedStatement.setFloat(6, order.getOrderMoney());
			preparedStatement.setString(7, order.getCity());
			rows=preparedStatement.executeUpdate();
			preparedStatement.close();
			closeConn();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			closeConn();
		}
		return rows;
	}
	
	public static int addRoWToOrderDetail(OrderDetail detail){
		int rows=-1;
		try {
			getConn();
			PreparedStatement preparedStatement=connection.prepareStatement(ADDORDERDETAIL);
			preparedStatement.setInt(1, detail.getDetailID());
			preparedStatement.setString(2, detail.getOrderID());
			preparedStatement.setInt(3, detail.getProductID());
			preparedStatement.setInt(4, detail.getProductNumber());
			preparedStatement.setString(5, detail.getUnit());
			rows=preparedStatement.executeUpdate();
			preparedStatement.close();
			closeConn();
		} catch (Exception e) {
			// TODO: handle exception
			closeConn();
		}
		return rows;
	}
	
	public static int deleteRowFromCarShop(int userID,int productID,String unit){
		int rows=-1;
		try {
			getConn();
			PreparedStatement preparedStatement=connection.prepareStatement(DELETECARSHOP);
			preparedStatement.setInt(1, userID);
			preparedStatement.setInt(2, productID);
			preparedStatement.setString(3, unit);
			rows=preparedStatement.executeUpdate();
			preparedStatement.close();
			closeConn();
			return rows;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			closeConn();
		}
		
		return rows;
	}
	/**删减账户余额*/
	public static int desAccount(int userID,float money){
		int rows=-1;
		try {
			getConn();
			PreparedStatement preparedStatement=connection.prepareStatement(DESUSERMONEY);
			preparedStatement.setFloat(1, money);
			preparedStatement.setInt(2, userID);
			rows=preparedStatement.executeUpdate();
			preparedStatement.close();
			closeConn();
			return rows;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			closeConn();
		}
		
		return rows;
	}
	
	/**获取我的关注信息*/
	public static List<CommonProduct> getMyWatchs(int userID){
		try {
			getConn();
			String sql="SELECT commonproduct.* FROM mywatch,commonproduct  WHERE "
					+ "userID=? and mywatch.productID=commonproduct.productID";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setInt(1, userID);
			ResultSet rSet=preparedStatement.executeQuery();
			List<CommonProduct> products=new ArrayList<CommonProduct>();
			while(rSet.next()){
				CommonProduct product=new CommonProduct();
				product.setProductID(rSet.getInt(1));
				product.setProductName(rSet.getString(2));
				product.setProductType(rSet.getString(3));
				product.setProductDes(rSet.getString(4));
				product.setProductPrice(rSet.getFloat(5));
				product.setProductPhoto(rSet.getString(6));
				product.setProductBagPrice(rSet.getFloat(7));
				products.add(product);
			}
			preparedStatement.close();
			rSet.close();
			closeConn();
			return products;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			closeConn();
		}
		return null;
	}
	
	/**新增或取消关注*/
	public static String addOrDeleteWatch(int userID,int productID){
		try {
			getConn();
			String sql="SELECT * FROM mywatch WHERE userID=? AND productID=?";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setInt(1, userID);
			preparedStatement.setInt(2, productID);
			ResultSet rSet=preparedStatement.executeQuery();
			//preparedStatement.close();
			String state;
			/**数据库中存在关注的记录*/
			if(rSet.first()){
				PreparedStatement deleteStatement=connection.prepareStatement(DELETEWATCH);
				deleteStatement.setInt(1, userID);
				deleteStatement.setInt(2, productID);
				int rows=deleteStatement.executeUpdate();
				closeConn();
				if(rows>0)
					state="deleteSuccess";
				else
					state="deleteFailed";
				deleteStatement.close();
				rSet.close();
				return state;
			}else{
				
				PreparedStatement addStatement=connection.prepareStatement(ADDWATCH);
				addStatement.setInt(1, userID);
				addStatement.setInt(2, productID);
				int rows= addStatement.executeUpdate();
				closeConn();
				if(rows>0)
					state="addSuccess";
				else
					state="addFailed";
				addStatement.close();
				rSet.close();
				return state;
			}
			//rSet.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			closeConn();
		}
		
		return null;
	}
	
	
	/***
	 * 验证用户名和密码是否正确
	 */
	public static boolean isUserValidate(String userName,String password){
		try {
			getConn();
			String sql="SELECT * from user where ID=? and password=?";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setString(1, userName);
			preparedStatement.setString(2, password);
			ResultSet rSet=preparedStatement.executeQuery();
			if(rSet.first()){
				closeConn();
				return true;
			}
			preparedStatement.close();
			rSet.close();
			closeConn();
			return false;
			//ResultSet  connection.createStatement().executeQuery(sql);
			//closeConn();
			//return rSet;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			closeConn();
			return false;
		}
	}
	
	/**判断用户是否关注某个商品*/
	public static boolean isWatchProduct(int userID,int productID){
		
		try {
			getConn();
			String sql="SELECT * FROM mywatch WHERE userID=? and productID=?";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setInt(1, userID);
			preparedStatement.setInt(2, productID);
			ResultSet rSet=preparedStatement.executeQuery();
			
			if(rSet.first()){
				preparedStatement.close();
				rSet.close();
				closeConn();
				return true;
			}else{
				preparedStatement.close();
				rSet.close();
				closeConn();
				return false;
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			closeConn();
			e.printStackTrace();
		}
		return false;
	}
	
	
	public static String changePassword(int userID,String orignPassword,String newPassword){
		try {
			getConn();
			String sql="select * FROM user WHERE ID=? and password=?";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setInt(1, userID);
			preparedStatement.setString(2, orignPassword);
			//if()
			ResultSet resultSet=preparedStatement.executeQuery();
			if(!resultSet.first()){
				closeConn();
				return "orignPasswordError";
			}
			preparedStatement.close();
			resultSet.close();
			sql="UPDATE user SET password=? WHERE ID=?";
			PreparedStatement idPreparedStatement=connection.prepareStatement(sql);
			idPreparedStatement.setString(1, newPassword);
			idPreparedStatement.setInt(2, userID);
			int rows= preparedStatement.executeUpdate();
			idPreparedStatement.close();
			if(rows>0){
				/**密码修改成功*/
				closeConn();
				return "changeSuccess";
			}
		} catch (Exception e) {
			// TODO: handle exception
			closeConn();
			e.printStackTrace();
		}
		return null;
	}
	
	public static void closeResource(PreparedStatement preparedStatement,ResultSet set,Connection ex_connection){
		try {
			if(preparedStatement!=null)
				preparedStatement.close();
			if(set!=null)
				set.close();
			if(ex_connection!=null){
				ex_connection.close();
				
			}
				
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public static boolean isExistPhoneNumber(String phoneNumber){
		try {
			getConn();
			String sql="SELECT * from user where phoneNumber=?";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			backPreparedStatement=preparedStatement;
			preparedStatement.setString(1, phoneNumber);
			ResultSet rSet=preparedStatement.executeQuery();
			if(rSet.first()){
				preparedStatement.close();
				rSet.close();
				closeConn();
				return true;
			}else{
				preparedStatement.close();
				rSet.close();
				closeConn();
				return false;
			}
			//preparedStatement.close();
			//ResultSet  connection.createStatement().executeQuery(sql);
			//closeConn();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			closeConn();
			return true;
		}
	}
	
	
}
