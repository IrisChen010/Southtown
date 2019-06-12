package town.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import town.model.Menu;
import town.mysql.DBConn;

public class MenuDao {
	public List<Menu> getAllMenus() {
		List<Menu> list = new ArrayList<Menu>();
		DBConn connect = new DBConn();
		String sql = "select * from `menu`";
		ResultSet rs = connect.execQuery(sql, new Object[]{});
		try {
			while(rs.next()) {
				Menu menu = new Menu();
				menu.setId(rs.getInt(1));
				menu.setNumber(rs.getString(2));
				menu.setName(rs.getString(3));
				menu.setPrice(rs.getInt(4));
				menu.setKind(rs.getString(5));
				list.add(menu);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public int addMenuItem(Menu menu) {
		DBConn connect = new DBConn();
		String sql = "insert into `menu`(number, name, price, kind) values(?, ?, ?, ?)";
		int affectedRows = connect.execOther(sql, new Object[]{menu.getNumber(), menu.getName(),
				menu.getPrice(), menu.getKind()});
		return affectedRows;
	}
	
	public int removeMenuItemById(int id) {
		DBConn connect = new DBConn();
		String sql = "delete from `menu` where id = ?";
		int affectedRows = connect.execOther(sql, new Object[]{id});
		return affectedRows;
	}
	
	public int removeMenuItemByNumber(String number) {
		DBConn connect = new DBConn();
		String sql = "delete from `menu` where number = ?";
		int affectedRows = connect.execOther(sql, new Object[]{number});
		return affectedRows;
	}
	
	public List<String> getAllKinds() {
		List<String> list = new ArrayList<String>();
		DBConn connect = new DBConn();
		String sql = "select distinct kind from `menu`";
		ResultSet rs = connect.execQuery(sql, new Object[]{});
		try {
			while(rs.next()) {
				String kind = rs.getString(1);
				list.add(kind);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public List<Menu> getMenusByKind(String kind) {
		List<Menu> list = new ArrayList<Menu>();
		DBConn connect = new DBConn();
		String sql = "select * from `menu` where kind = ?";
		ResultSet rs = connect.execQuery(sql, new Object[]{kind});
		try {
			while(rs.next()) {
				Menu menu = new Menu();
				menu.setId(rs.getInt(1));
				menu.setNumber(rs.getString(2));
				menu.setName(rs.getString(3));
				menu.setPrice(rs.getInt(4));
				menu.setKind(rs.getString(5));
				list.add(menu);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public Menu getMenuByNumber(String number) {
		Menu menu = null;
		DBConn connect = new DBConn();
		String sql = "select * from `menu` where number = ?";
		ResultSet rs = connect.execQuery(sql, new Object[]{number});
		try {
			while(rs.next()) {
				menu = new Menu();
				menu.setId(rs.getInt(1));
				menu.setNumber(rs.getString(2));
				menu.setName(rs.getString(3));
				menu.setPrice(rs.getInt(4));
				menu.setKind(rs.getString(5));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return menu;
	}
	
	public Menu getMenuById(int id) {
		Menu menu = null;
		DBConn connect = new DBConn();
		String sql = "select * from `menu` where id = ?";
		ResultSet rs = connect.execQuery(sql, new Object[]{id});
		try {
			while(rs.next()) {
				menu = new Menu();
				menu.setId(rs.getInt(1));
				menu.setNumber(rs.getString(2));
				menu.setName(rs.getString(3));
				menu.setPrice(rs.getInt(4));
				menu.setKind(rs.getString(5));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return menu;
	}
	
	public int updateMenuByNumber(String number, Menu menu) {
		DBConn connect = new DBConn();
		String sql = "update `menu` set number = ?, name = ?, price = ?, kind = ? where number = ?";
		int affectedRows = connect.execOther(sql, new Object[]{menu.getNumber(),
																menu.getName(),
																menu.getPrice(),
																menu.getKind(),
																number});
		return affectedRows;
	}
}
