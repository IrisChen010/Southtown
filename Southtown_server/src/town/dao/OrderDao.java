package town.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import town.model.Order;
import town.mysql.DBConn;

public class OrderDao {
	public List<Order> getOrdersByRid(int rid) {
		List<Order> list = new ArrayList<Order>();
		DBConn connect = new DBConn();
		String sql = "select * from `order` where rid = ?";
		ResultSet rs = connect.execQuery(sql, new Object[]{rid});
		try {
			while(rs.next()) {
				Order order = new Order();
				order.setId(rs.getInt(1));
				order.setRid(rs.getInt(2));
				order.setMid(rs.getInt(3));
				list.add(order);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public int addOrderByInfo(int rid, int mid) {
		DBConn connect = new DBConn();
		String sql = "insert into `order`(rid, mid) values(?, ?)";
		int affectedRows = connect.execOther(sql, new Object[]{rid, mid});
		return affectedRows;
	}
}
