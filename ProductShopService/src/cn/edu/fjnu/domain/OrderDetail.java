/**
 * 
 */
package cn.edu.fjnu.domain;

/**
 * @author GaoFei
 * �������ʵ����
 */
public class OrderDetail {

	private int detailID;
	private String orderID;
	private int productID;
	private int productNumber;
	/**��Ʒ��λ*/
	private String unit;
	public int getDetailID() {
		return detailID;
	}
	public void setDetailID(int detailID) {
		this.detailID = detailID;
	}
	public String getOrderID() {
		return orderID;
	}
	public void setOrderID(String orderID) {
		this.orderID = orderID;
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
