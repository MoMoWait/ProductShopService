package cn.edu.fjnu.utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class UploadUserHeadPhoto {
	
	private FileUplodThread fileUplodThread;
	//private List<Socket> clientSockets=new ArrayList<Socket>();
	private int port;
	public UploadUserHeadPhoto(int port) {
		// TODO Auto-generated constructor stub
		this.port=port;
	}
	
	
	/**开启服务监听*/
	public  void startListen(){
		
		fileUplodThread=new FileUplodThread(port);
		fileUplodThread.start();
		
	}
	
	
	/**关闭服务监听*/
	
	public void stopListen(){
		//fileUplodThread.
		if(fileUplodThread!=null){
			fileUplodThread.setRun(false);
			fileUplodThread.closeServerSocket();
		}
	}
	
	
	/**等待客户端连接线程*/
	public class FileUplodThread extends Thread{
		
		private int listenPort;
		private boolean isRun=true;
		private ServerSocket serverSocket;
		private Socket clientSocket;
		public FileUplodThread(int listenPort){
			this.listenPort=listenPort;
			try {
				serverSocket=new ServerSocket(listenPort);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void closeServerSocket(){
			if(serverSocket!=null)
				try {
					serverSocket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		public FileUplodThread(){
			
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(isRun){
				//等待客户端监听
				try {
					
					clientSocket= serverSocket.accept();
					SessionThread sessionThread=new SessionThread(clientSocket);
					sessionThread.start();
					
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			
		}

		public int getListenPort() {
			return listenPort;
		}

		public void setListenPort(int listenPort) {
			this.listenPort = listenPort;
		}

		public boolean isRun() {
			return isRun;
		}

		public void setRun(boolean isRun) {
			this.isRun = isRun;
		}
		
	}
	
	/**和客户端会话线程*/
	public class SessionThread extends Thread{
		
		private Socket socket;
		private DataInputStream dataInputStream;
		private DataOutputStream dataOutputStream;
		private boolean isRead=true;
		public SessionThread(Socket socket){
			this.socket=socket;
			try {
				
				dataInputStream= new DataInputStream(this.socket.getInputStream());
				//dataInputStream.read
				dataOutputStream=new DataOutputStream(this.socket.getOutputStream());
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			//读取客户端发送的消息
			try {
				
				String content=null;
				String userID=null;
				while(isRead){
					content=dataInputStream.readUTF();
					//读取命令
					if(content!=null){
						String command=content.split(";")[0];
						if(command.equals("userID")){
							userID=content.split(";")[1];
							
							//isRead=false;
							//socket.close();
						}else if(command.equals("fileLength")){
							long fileLength=Long.parseLong(content.split(";")[1]) ;
							byte[] readContents=new byte[1024];
							//File headFile=new File(userID+".jpg");
							FileOutputStream fileOutputStream=new FileOutputStream(new File(userID+".jpg"));
							int total=0;
							long length= (long)dataInputStream.read(readContents);
							total+=length;
							//System.out.println(length);
							while(total<fileLength){
								fileOutputStream.write(readContents, 0, (int)length);
								length=dataInputStream.read(readContents);
								total+=length;
								System.out.println(length);
							}
							dataOutputStream.writeUTF("finish");
							dataOutputStream.flush();
						//	dataOutputStream.close();
						//	dataInputStream.close();
							fileOutputStream.flush();
							fileOutputStream.close();
						}else if(command.equals("exit")){
							isRead=false;
							//尝试关闭输入流和输出流
							dataInputStream.close();
							dataOutputStream.close();
						}
					}
					Thread.sleep(100);
				}
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				if(socket!=null)
					try {
						if(!socket.isClosed())
							socket.close();
						dataInputStream.close();
						dataOutputStream.close();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			}
		}

		public Socket getSocket() {
			return socket;
		}

		public void setSocket(Socket socket) {
			this.socket = socket;
		}

		public DataInputStream getDataInputStream() {
			return dataInputStream;
		}

		public void setDataInputStream(DataInputStream dataInputStream) {
			this.dataInputStream = dataInputStream;
		}

		public DataOutputStream getDataOutputStream() {
			return dataOutputStream;
		}

		public void setDataOutputStream(DataOutputStream dataOutputStream) {
			this.dataOutputStream = dataOutputStream;
		}

		public boolean isRead() {
			return isRead;
		}

		public void setRead(boolean isRead) {
			this.isRead = isRead;
		}
		
		
	}
	
}
