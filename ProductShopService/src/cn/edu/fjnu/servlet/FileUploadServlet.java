/**
 * 
 */
package cn.edu.fjnu.servlet;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import cn.edu.fjnu.data.Const;
import cn.edu.fjnu.utils.DBUtils;

/**
 * @author GaoFei
 * 
 */
public class FileUploadServlet extends HttpServlet {

	private String userID;
	private String userHeadPhotoUrl;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		boolean isMultipart = ServletFileUpload.isMultipartContent(req);
		if (isMultipart) {
			String realpath = req.getSession().getServletContext()
					.getRealPath("/UserHeadPhoto");
			File dir = new File(realpath);
			if (!dir.exists())
				dir.mkdirs();
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setHeaderEncoding("UTF-8");
			try {
				List<FileItem> items = upload.parseRequest(req);
				//userID = req.getParameter("userID");
				//System.out.println("ÓÃ»§ID"+userID);
				for (FileItem item : items) {
					if (item.isFormField()) {
						String filedName = item.getFieldName();
						if(filedName.equals("userID")){
							userID=item.getString();
							//System.out.println(item.getString());
							//System.out.println(item.getString(filedName));
						}
							
						// System.out.println(name1+"="+username);
					} else {
						// item.write(new File(dir,System.currentTimeMillis()
						// +item.getName().substring(
						// item.getName().lastIndexOf("."))));
						File img = new File(dir, userID
								+ item.getName().substring(
										item.getName().lastIndexOf(".")));
						SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddhhmmssSSS");
						userHeadPhotoUrl = userID
								+ sdf.format(new Date())+item.getName().substring(
										item.getName().lastIndexOf("."));
						if (img.exists()) {
							System.out.println("--===========");
							img.delete();
							item.write(new File(dir,userHeadPhotoUrl));
							DBUtils.updateUserInfo(Integer.parseInt(userID),
									"headPhoto",
									Const.SERVER_BASIC+"UserHeadPhoto/"+ userHeadPhotoUrl);
						} else {
							item.write(new File(dir, userHeadPhotoUrl));
							DBUtils.updateUserInfo(Integer.parseInt(userID),
									"headPhoto",Const.SERVER_BASIC+"UserHeadPhoto/"+ userHeadPhotoUrl);
						}

					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			doGet(req, resp);
		}
	}

}
