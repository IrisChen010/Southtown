package town.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

public class Reservation implements Serializable {
	private int id;
	private int covers;
	private Date date;
	private Time time;
	private int tid;
	private int cid;
	private int status;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCovers() {
		return covers;
	}
	public void setCovers(int covers) {
		this.covers = covers;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Time getTime() {
		return time;
	}
	public void setTime(Time time) {
		this.time = time;
	}
	public int getTid() {
		return tid;
	}
	public void setTid(int tid) {
		this.tid = tid;
	}
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
