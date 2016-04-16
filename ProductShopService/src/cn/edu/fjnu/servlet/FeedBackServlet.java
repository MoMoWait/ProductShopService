/**
 * 
 */
package cn.edu.fjnu.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.minidev.json.JSONObject;
import cn.edu.fjnu.domain.FeedBack;
import cn.edu.fjnu.utils.DBUtils;

/**
 * @author GaoFei
 * 意见反馈服务
 *
 */
public class FeedBackServlet extends HttpServlet{

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
		JSONObject data=new JSONObject();
		int userID=Integer.parseInt(request.getParameter("userID"));
		String content=request.getParameter("content");
		String way=request.getParameter("way");
		String ip=request.getParameter("ip");
		System.out.println(userID);
		System.out.println(content);
		System.out.println(way);
		Date date=new Date(System.currentTimeMillis());
		FeedBack feedBack=new FeedBack();
		feedBack.setUsrID(userID);
		feedBack.setContent(content);
		feedBack.setWay(way);
		feedBack.setIp(ip);
		feedBack.setTime(date);
		
		if( DBUtils.addRowFeedBack(feedBack)!=-1){
			data.put("feedback","success");
		}else{
			data.put("feedback","failed");
		}
		
		response.setContentType("text/json");
		response.setCharacterEncoding("utf-8");
		PrintWriter printWriter=response.getWriter();
		printWriter.write(data.toJSONString());
	}
}
