package town.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import town.util.Transfer;

public class Server {
	private int port = 9999;
	private ServerSocket ss;
	private Object obj;
	
	public Server() {
		try {
			ss = new ServerSocket(port);
			System.out.println("服务器已成功启动！");
			while(true) {
				Socket client = ss.accept();
				ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(client.getInputStream());
				Transfer trans = new Transfer();
				try {
					trans = (Transfer)in.readObject();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				obj = Operation.handleMessage(trans);
				out.writeObject(obj);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
