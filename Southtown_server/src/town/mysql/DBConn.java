package town.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DBConn {
	private Connection connect = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	private void getConntion(){		
		try {
			Class.forName(Config.CLASS_NAME);
			String url = Config.DATABASE_URL +"://" +Config.SERVER_IP +":" +Config.SERVER_PORT +"/" +Config.DATABASE_SID;
			connect = DriverManager.getConnection(url,Config.USERNAME,Config.PASSWORD);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public void closeConn(){
		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(pstmt!=null){
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(connect!=null){
			try {
				connect.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public int execOther(final String strSQL,final Object[] params ){
		getConntion();
		System.out.println("SQL:> "+strSQL);
		try {
			pstmt = connect.prepareStatement(strSQL);
			for(int i=0; i< params.length ;i++){
				pstmt.setObject(i+1, params[i]);
			}
			int affectedRows = pstmt.executeUpdate();
			return affectedRows;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}

	public ResultSet execQuery(final String strSQL,final Object[] params){
		getConntion();
		System.out.println("SQL:> "+strSQL);
		try {
			pstmt = connect.prepareStatement(strSQL);
			for(int i=0; i< params.length ;i++){
				pstmt.setObject(i+1, params[i]);
			}		
			rs = pstmt.executeQuery();
			return rs;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}