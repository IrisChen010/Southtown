package town.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import town.model.Menu;
import town.model.Table;
import town.mysql.DBConn;
import town.util.Msg;

public class TableDao {
	public Table getTableById(int id) {
		Table table = new Table();
		DBConn connect = new DBConn();
		String sql = "select * from `table` where id = ?";
		ResultSet rs = connect.execQuery(sql, new Object[]{id});
		try {
			while(rs.next()) {
				table.setId(id);
				table.setNumber(rs.getString(2));
				table.setPlaces(rs.getInt(3));
				table.setStatus(rs.getInt(4));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return table;
	}
	
	public Table getTableByNumber(String number) {
		Table table = null;
		DBConn connect = new DBConn();
		String sql = "select * from `table` where number = ?";
		ResultSet rs = connect.execQuery(sql, new Object[]{number});
		try {
			while(rs.next()) {
				table = new Table();
				table.setId(rs.getInt(1));
				table.setNumber(rs.getString(2));
				table.setPlaces(rs.getInt(3));
				table.setStatus(rs.getInt(4));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return table;
	}
	
	public int getPlacesByNumber(String number) {
		int places = 0;
		DBConn connect = new DBConn();
		String sql = "select places from `table` where number = ?";
		ResultSet rs = connect.execQuery(sql, new Object[]{number});
		try {
			while(rs.next()) {
				places = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return places;
	}
	
	public int setTableStatusById(int status, int id) {
		DBConn connect = new DBConn();
		String sql = "update `table` set status = ? where id = ?";
		int affectedRows = connect.execOther(sql, new Object[]{status, id});
		return affectedRows;
	}
	
	public int[] getTableStatuses() {
		int[] status = new int[Msg.TABLE_TOTAL_NUMBER];
		DBConn connect = new DBConn();
		String sql = "select status from `table`";
		ResultSet rs = connect.execQuery(sql, new Object[]{});
		int i = 0;
		try {
			while(rs.next()) {
				int s = rs.getInt(1);
				status[i++] = s;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}
	
	public int setTableStatuses(int[] status) {
		DBConn connect = new DBConn();
		String sql = "update `table` set status = ? where id = ?";
		int affectedRows = 0;
		for(int i = 0; i < Msg.TABLE_TOTAL_NUMBER; i++)
			affectedRows += connect.execOther(sql, new Object[]{status[i], i+1});
		return affectedRows;
	}
}
