package town.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import town.util.Transfer;

public class Client{
	private int port = 9999;
	private String host = "localhost";
	private Socket client;
	private Object obj;
	
	public Client(int message) {
		try{
			client = new Socket(InetAddress.getByName(host), port);
			ObjectInputStream in = new ObjectInputStream(client.getInputStream());
			ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
			Transfer trans = new Transfer();
			trans.setMessage(message);
			out.writeObject(trans);
			try {
				obj = in.readObject();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			client.close();
		}
		catch (IOException ex){
			ex.printStackTrace();
		}
	}
	
	public Client(Transfer trans) {
		try{
			client = new Socket(InetAddress.getByName(host), port);
			ObjectInputStream in = new ObjectInputStream(client.getInputStream());
			ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
			out.writeObject(trans);
			try {
				obj = in.readObject();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			client.close();
		}
		catch (IOException ex){
			ex.printStackTrace();
		}
	}
	
	public Object getObject() {
		return obj;
	}
}