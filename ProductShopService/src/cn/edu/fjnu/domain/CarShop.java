/**
 * 
 */
package cn.edu.fjnu.domain;

/**
 * @author GaoFei
 * 购物车实体类
 */
public class CarShop {

	private int userID;
	private int productID;
	private int productNumber;
	private String unit;
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public int getProductID() {
		return productID;
	}
	public void setProductID(int productID) {
		this.productID = productID;
	}
	public int getProductNumber() {
		return productNumber;
	}
	public void setProductNumber(int productNumber) {
		this.productNumber = productNumber;
	}
	
	public String getUnit() {
		return unit;
	}
	
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
}
