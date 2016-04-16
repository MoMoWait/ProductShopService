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
 *数据库数据生成器
 */
public class DataGetting {
	public static void main(String[] args) {
		String []productNames=new String[]{"苹果","香蕉","雪梨","荔枝",
				"白菜","青菜","西红柿","土豆","鸡蛋","豆腐","乌龟","鱼",
				"草莓","普通","西瓜","核桃"};
		String[]names=new String[]{"奥特曼","孙悟空","苏东坡","范冰冰","黄志强","刘落英","张全蛋","李连杰","刘德华","成龙"};
		String[]sex=new String[]{"男","女"};
		String[]address=new String[]{"江西赣州","福建福州","辽宁沈阳","中国香港","台湾高雄","河北合肥"};
		String[]phones=new String[]{"18720725761","18391689097","15170456251","15986324924"};
		String[]productTypes=new String[]{"水果","蔬菜","生鲜","其他"};
		CommonProduct commonProduct=new CommonProduct();
		User user=new User();
		HoteProduct hoteProduct=new HoteProduct();
		Random random=new Random();
		/*
		for(int i=0;i<2000;i++){
			commonProduct.setProductName(productNames[random.nextInt(16)]);
			commonProduct.setProductType(productTypes[random.nextInt(4)]);
			commonProduct.setProductDes("很好，非常好吃!");
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
		System.out.println("已完成");
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
		System.out.println("已完成");
	}
}
