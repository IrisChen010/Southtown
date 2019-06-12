package town.socket;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import town.dao.CustomerDao;
import town.dao.MenuDao;
import town.dao.OrderDao;
import town.dao.ReservationDao;
import town.dao.StuffDao;
import town.dao.TableDao;
import town.model.Menu;
import town.model.Order;
import town.model.Reservation;
import town.model.Stuff;
import town.util.Msg;
import town.util.Transfer;

public class Operation {
	public static Object handleMessage(Transfer trans) {
		Object obj = new Object();
		Object[] params;
		int message = trans.getMessage();
		int flag = -1;
		String str = null;
		
		Menu menu;
		String number = null;
		
		Reservation r;
		int tid;
		int cid;
		
		switch(message) {
			//menu
			case Msg.GET_ALL_MENU:
				obj = new MenuDao().getAllMenus();
				break;
			case Msg.SAVE_MENU_ITEM:
				menu = (Menu) trans.getObj();
				flag = new MenuDao().addMenuItem(menu);
				if(flag > 0) {
					str = "save menu item success.";
				}
				obj = str;
				break;
			case Msg.REMOVE_MENU_ITEM:
				number = (String) trans.getObj();
				flag = new MenuDao().removeMenuItemByNumber(number);
				if(flag > 0) {
					str = "remove menu item success.";
				}
				obj = str;
				break;
			case Msg.MODIFY_MENU_ITEM:
				menu = (Menu) trans.getObj();
				params = trans.getParams();
				number = (String) params[0];
				flag = new MenuDao().updateMenuByNumber(number, menu);
				if(flag > 0) {
					str = "modify menu item success.";
				}
				obj = str;
				break;
			case Msg.GET_MENU_KINDS:
				obj = new MenuDao().getAllKinds();
				break;
			case Msg.GET_MENUS_BY_KIND:
				String kind = (String) trans.getObj();
				obj = new MenuDao().getMenusByKind(kind);
				break;
			case Msg.GET_MENU_BY_NUMBER:
				number = (String) trans.getObj();
				obj = new MenuDao().getMenuByNumber(number);
				break;
				
			//reservation
			case Msg.SAVE_RESERVATION:
				r = (Reservation) trans.getObj();
				params = trans.getParams();
				number = (String) params[0];
				String name = (String) params[1];
				String phone = (String) params[2];
				tid = new TableDao().getTableByNumber(number).getId();
				new TableDao().setTableStatusById(Msg.TB_STATUS_HAS_RV, tid);
				new CustomerDao().addCustomer(name, phone);
				cid = new CustomerDao().getCustomerByInfo(name, phone).getId();
				r.setTid(tid);
				r.setCid(cid);
				flag = new ReservationDao().addReservation(r);
				if(flag > 0) {
					str = "save new reservation success";
				}
				obj = str;
				break;
			case Msg.SAVE_WALKIN:
				r = (Reservation) trans.getObj();
				flag = new ReservationDao().addWalkin(r);
				if(flag > 0) {
					str = "save new walkin success";
				}
				obj = str;
				break;
			case Msg.GET_ALL_RESERVATIONS:
				obj = new ReservationDao().getAllReservations();
				break;
			case Msg.SAVE_CUSTOMER:
				
				break;
			case Msg.GET_CUSTOMER_BY_ID:
				cid = (int) trans.getObj();
				obj = new CustomerDao().getCustomerById(cid);
				break;
			case Msg.GET_NEW_RESERVATIONS_BY_TID:
				tid = (int) trans.getObj();
				List<Reservation> rlist = new ReservationDao().getNewReservationsByTid(tid);
				obj = rlist;
				break;
			case Msg.CANCEL_NEW_RESERVATION:
				params = trans.getParams();
				tid = (int) params[0];
				Date date = (Date) params[1];
				Time time = (Time) params[2];
				flag = new ReservationDao().cancelReservationByInfo(tid, date, time);
				if(flag > 0) {
					str = "cancel reservation success";
				}
				obj = str;
				break;
				
			//table
			case Msg.GET_TABLE_BY_ID:
				tid = (int) trans.getObj();
				obj = new TableDao().getTableById(tid);
				break;
			case Msg.GET_TABLE_STATUSES:
				obj = new TableDao().getTableStatuses();
				break;
			case Msg.SET_TABLE_STATUSES:
				int[] status = (int[]) trans.getObj();
				flag = new TableDao().setTableStatuses(status);
				if(flag > 0) {
					str = "save all table statuses success";
				}
				obj = str;
				break;
			case Msg.GET_TABLE_ORDER_MENUS:
				tid = (int) trans.getObj();
				r = new ReservationDao().getEatingReservationByTid(tid);
				List<Order> olist = new OrderDao().getOrdersByRid(r.getId());
				List<Menu> mlist = new ArrayList<Menu>();
				Iterator it = olist.iterator();
				while(it.hasNext()) {
					Order order = (Order) it.next();
					menu = new MenuDao().getMenuById(order.getMid());
					mlist.add(menu);
				}
				obj = mlist;
				break;
			case Msg.GET_TABLE_EATING_RESERVATION:
				tid = (int) trans.getObj();
				r = new ReservationDao().getEatingReservationByTid(tid);
				obj = r;
				break;
				
			//order
			case Msg.PAY_ORDER_SUCCESS:
				r = (Reservation) trans.getObj();
				flag = new ReservationDao().payReservation(r);
				if(flag > 0) {
					str = "modify reservation to RV_STATUS_HISTORY success";
				}
				obj = str;
				break;
			case Msg.ADD_ORDER_TO_TABLE:
				params = trans.getParams();
				int rid = (int) params[0];
				number = (String) params[1];
				int mid = new MenuDao().getMenuByNumber(number).getId();
				flag = new OrderDao().addOrderByInfo(rid, mid);
				if(flag > 0) {
					str = "add order success";
				}
				obj = str;
				break;
				
			//stuff login in
			case Msg.USER_LOGIN_IN:
				params = trans.getParams();
				String sName = (String) params[0];
				String password = (String) params[1];
				Stuff stuff = new StuffDao().Login(sName, password);
				obj = stuff;
				break;
			default:
				break;
			
		}
		return obj;
	}
}
