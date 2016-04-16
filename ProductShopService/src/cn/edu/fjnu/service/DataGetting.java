/**
 * 
 */
package cn.edu.fjnu.service;


import java.util.Random;

import cn.edu.fjnu.domain.CommonProduct;
import cn.edu.fjnu.domain.HoteProduct;
import cn.edu.fjnu.domain.User;
import cn.edu.fjnu.utils.DBUtils;

/**
 * @author GaoFei
 *���ݿ�����������
 */
public class DataGetting {
	public static void main(String[] args) {
		String []productNames=new String[]{"ƻ��","�㽶","ѩ��","��֦",
				"�ײ�","���","������","����","����","����","�ڹ�","��",
				"��ݮ","��ͨ","����","����"};
		String[]names=new String[]{"������","�����","�ն���","������","��־ǿ","����Ӣ","��ȫ��","������","���»�","����"};
		String[]sex=new String[]{"��","Ů"};
		String[]address=new String[]{"��������","��������","��������","�й����","̨�����","�ӱ��Ϸ�"};
		String[]phones=new String[]{"18720725761","18391689097","15170456251","15986324924"};
		String[]productTypes=new String[]{"ˮ��","�߲�","����","����"};
		CommonProduct commonProduct=new CommonProduct();
		User user=new User();
		HoteProduct hoteProduct=new HoteProduct();
		Random random=new Random();
		/*
		for(int i=0;i<2000;i++){
			commonProduct.setProductName(productNames[random.nextInt(16)]);
			commonProduct.setProductType(productTypes[random.nextInt(4)]);
			commonProduct.setProductDes("�ܺã��ǳ��ó�!");
			commonProduct.setProductPrice(random.nextFloat()*50);
			commonProduct.setProductPhoto("http://192.168.1.203:8080/FarmShopService/1.png");
			DBUtils.addRowToCommonProduct(commonProduct);
		}
		*/
		/*
		for(int i=0;i<16;i++){
			hoteProduct.setPosition(i%4+1);
			DBUtils.addRowToHotePorduct(hoteProduct);
		}
		System.out.println("�����");
		*/
		
		
		
		for(int i=0;i<200;i++){
			user.setName(names[random.nextInt(10)]);
			user.setSex(sex[random.nextInt(2)]);
			user.setAddress(address[random.nextInt(6)]);
			user.setHeadPhoto("http://192.168.1.203:8080/ProductShopService/UserHeadPhoto/1.jpg");
			user.setAccount(random.nextFloat()*2000);
			user.setPhoneNumber(phones[random.nextInt(4)]);
			DBUtils.addRowToUser(user);
		}
		System.out.println("�����");
	}
}
