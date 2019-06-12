package town.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import town.model.Stuff;
import town.mysql.DBConn;

public class StuffDao {
	public Stuff Login(String name, String password) {
		Stuff stuff = null;
		DBConn connect = new DBConn();
		String sql = "select * from `stuff` where name = ? and password = ?";
		ResultSet rs = connect.execQuery(sql, new Object[]{name, password});
		try {
			while(rs.next()) {
				stuff = new Stuff();
				stuff.setId(rs.getInt(1));
				stuff.setNumber(rs.getString(2));
				stuff.setName(rs.getString(3));
				stuff.setPassword(rs.getString(4));
				stuff.setLevel(rs.getInt(5));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stuff;
	}
}
