package town.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import town.model.Reservation;
import town.mysql.DBConn;
import town.util.Msg;

public class ReservationDao {
	public int addReservation(Reservation r) {
		DBConn connect = new DBConn();
		String sql = "insert into `reservation`(covers, date, time, tid, cid, status)" +
				"values(?, ?, ?, ?, ?, ?)";
		int affectedRows = connect.execOther(sql, new Object[]{r.getCovers(),
																r.getDate(),
																r.getTime(),
																r.getTid(),
																r.getCid(),
																r.getStatus()});
		return affectedRows;
	}
	
	public int addWalkin(Reservation r) {
		DBConn connect = new DBConn();
		String sql = "insert into `reservation`(covers, date, time, tid, status)" +
				"values(?, ?, ?, ?, ?)";
		int affectedRows = connect.execOther(sql, new Object[]{r.getCovers(),
																r.getDate(),
																r.getTime(),
																r.getTid(),
																r.getStatus()});
		return affectedRows;
	}
	
	public List<Reservation> getAllReservations() {
		List<Reservation> list = new ArrayList<Reservation>();
		DBConn connect = new DBConn();
		String sql = "select * from `reservation`";
		ResultSet rs = connect.execQuery(sql, new Object[]{});
		try {
			while(rs.next()) {
				Reservation r = new Reservation();
				r.setId(rs.getInt(1));
				r.setCovers(rs.getInt(2));
				r.setDate(rs.getDate(3));
				r.setTime(rs.getTime(4));
				r.setTid(rs.getInt(5));
				r.setCid(rs.getInt(6));
				r.setStatus(rs.getInt(7));
				list.add(r);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public Reservation getEatingReservationByTid(int tid) {
		Reservation r = null;
		DBConn connect = new DBConn();
		String sql = "select * from `reservation` where tid = ? and (status = ? or status = ?)";
		ResultSet rs = connect.execQuery(sql, new Object[]{tid, Msg.RV_STATUS_EATING, Msg.WK_STATUS_EATING});
		try {
			while(rs.next()) {
				r = new Reservation();
				r.setId(rs.getInt(1));
				r.setCovers(rs.getInt(2));
				r.setDate(rs.getDate(3));
				r.setTime(rs.getTime(4));
				r.setTid(rs.getInt(5));
				r.setCid(rs.getInt(6));
				r.setStatus(rs.getInt(7));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return r;
	}
	
	public List<Reservation> getNewReservationsByTid(int tid) {
		List<Reservation> list = new ArrayList<Reservation>();
		DBConn connect = new DBConn();
		String sql = "select * from `reservation` where tid = ? and status = ?";
		ResultSet rs = connect.execQuery(sql, new Object[]{tid, Msg.RV_STATUS_NEW});
		try {
			while(rs.next()) {
				Reservation r = new Reservation();
				r.setId(rs.getInt(1));
				r.setCovers(rs.getInt(2));
				r.setDate(rs.getDate(3));
				r.setTime(rs.getTime(4));
				r.setTid(rs.getInt(5));
				r.setCid(rs.getInt(6));
				r.setStatus(rs.getInt(7));
				list.add(r);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public int payReservation(Reservation r) {
		DBConn connect = new DBConn();
		String sql = "update `reservation` set status = ? where id = ?";
		int affectedRows;
		if(r.getStatus() == Msg.RV_STATUS_EATING) {
			affectedRows = connect.execOther(sql, new Object[]{Msg.RV_STATUS_HISTORY, r.getId()});
		} else {
			affectedRows = connect.execOther(sql, new Object[]{Msg.WK_STATUS_HISTORY, r.getId()});
		}
		return affectedRows;
	}
	
	public int cancelReservationByInfo(int tid, Date date, Time time) {
		DBConn connect = new DBConn();
		String sql = "update `reservation` set status = ? where tid = ? and date = ? and time = ?";
		int affectedRows = connect.execOther(sql, new Object[]{Msg.RV_STATUS_CANCEL, tid, date, time});
		return affectedRows;
	}
}
