package town.main;

import town.view.Login;
import town.view.MainWindow;

public class Man_client {
	public static int flag = 0;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Login login = new Login();
			login.open();
			if(flag == 1) {
				MainWindow window = new MainWindow();
				window.open();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
