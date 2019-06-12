package town.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import town.model.Customer;
import town.mysql.DBConn;

public class CustomerDao {
	public Customer getCustomerById(int id) {
		Customer customer = new Customer();
		DBConn connect = new DBConn();
		String sql = "select * from `customer` where id = ?";
		ResultSet rs = connect.execQuery(sql, new Object[]{id});
		try {
			while(rs.next()) {
				customer.setId(rs.getInt(1));
				customer.setName(rs.getString(2));
				customer.setPhone(rs.getString(3));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return customer;
	}
	
	public Customer getCustomerByInfo(String name, String phone) {
		Customer customer = null;
		DBConn connect = new DBConn();
		String sql = "select * from `customer` where name = ? and phone = ?";
		ResultSet rs = connect.execQuery(sql, new Object[]{name, phone});
		try {
			while(rs.next()) {
				customer = new Customer();
				customer.setId(rs.getInt(1));
				customer.setName(rs.getString(2));
				customer.setPhone(rs.getString(3));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return customer;
	}
	
	public int addCustomer(Customer customer) {
		DBConn connect = new DBConn();
		Customer cus = getCustomerByInfo(customer.getName(), customer.getPhone());
		if(!customer.getName().equalsIgnoreCase(cus.getName())
				|| !customer.getPhone().equalsIgnoreCase(cus.getPhone())) {
			String sql = "insert into `customer`(name, phone) values(?, ?)";
			connect.execOther(sql, new Object[]{customer.getName(), customer.getPhone()});
			return 1;	//customer add success
		}
		return 0;	//customer already exists
	}
	
	public int addCustomer(String name, String phone) {
		DBConn connect = new DBConn();
		Customer cus = getCustomerByInfo(name, phone);
		if(cus == null) {
		} else if(name.equalsIgnoreCase(cus.getName())
				&& phone.equalsIgnoreCase(cus.getPhone())) {
			return 0;	//customer already exists
		}
		
		String sql = "insert into `customer`(name, phone) values(?, ?)";
		connect.execOther(sql, new Object[]{name, phone});
		return 1;	//customer add success
		
	}
}
