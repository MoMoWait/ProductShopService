/**
 * 
 */
package cn.edu.fjnu.domain;

/**
 * @author GaoFei
 * 用户实体类
 */
public class User {

	private int id;
	private String name;
	private String sex;
	private String address;
	private String headPhoto;
	private float account;
	private String phoneNumber;
	private String password;
	public User(){
		
	}
	
	
	
	public User(String name, String sex, String address, String headPhoto,
			float account, String phoneNumber, String password) {
		super();
		this.name = name;
		this.sex = sex;
		this.address = address;
		this.headPhoto = headPhoto;
		this.account = account;
		this.phoneNumber = phoneNumber;
		this.password = password;
	}



	public User(String name, String sex, String address, String headPhoto,
			float account, String phoneNumber) {
		super();
		this.name = name;
		this.sex = sex;
		this.address = address;
		this.headPhoto = headPhoto;
		this.account = account;
		this.phoneNumber = phoneNumber;
	}



	public User(int id, String name, String sex, String address,
			String headPhoto, float account, String phoneNumber) {
		super();
		this.id = id;
		this.name = name;
		this.sex = sex;
		this.address = address;
		this.headPhoto = headPhoto;
		this.account = account;
		this.phoneNumber = phoneNumber;
	}

	

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getHeadPhoto() {
		return headPhoto;
	}
	public void setHeadPhoto(String headPhoto) {
		this.headPhoto = headPhoto;
	}
	public float getAccount() {
		return account;
	}
	public void setAccount(float account) {
		this.account = account;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
