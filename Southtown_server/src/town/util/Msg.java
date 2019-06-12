package town.util;

public class Msg {
	//jdbc message
	public static final int GET_ALL_MENU = 0;
	public static final int SAVE_MENU_ITEM = 1;
	public static final int REMOVE_MENU_ITEM = 2;
	public static final int MODIFY_MENU_ITEM = 3;
	public static final int SAVE_RESERVATION = 4;
	public static final int SAVE_CUSTOMER = 5;
	public static final int GET_MENU_KINDS = 6;
	public static final int GET_MENUS_BY_KIND = 7;
	public static final int GET_MENU_BY_NUMBER = 8;
	public static final int SAVE_WALKIN = 9;
	public static final int GET_ALL_RESERVATIONS = 10;
	public static final int GET_CUSTOMER_BY_ID = 11;
	public static final int GET_TABLE_BY_ID = 12;
	public static final int GET_TABLE_STATUSES = 13;
	public static final int SET_TABLE_STATUSES = 14;
	public static final int GET_TABLE_ORDER_MENUS = 15;
	public static final int GET_TABLE_EATING_RESERVATION = 16;
	public static final int PAY_ORDER_SUCCESS = 17;
	public static final int GET_NEW_RESERVATIONS_BY_TID = 18;
	public static final int CANCEL_NEW_RESERVATION = 19;
	public static final int ADD_ORDER_TO_TABLE = 20;
	public static final int USER_LOGIN_IN = 21;
	
	//window choice flag
	public static final int WINDOW_GOODS_ITEM_MODIFY = 0;
	public static final int WINDOW_GOODS_ITEM_ADD = 1;
	
	//reservation status
	public static final int RV_STATUS_NEW = 0;
	public static final int RV_STATUS_CANCEL = 1;
	public static final int RV_STATUS_EATING = 2;
	public static final int RV_STATUS_HISTORY = 3;
	public static final int WK_STATUS_EATING = 4;
	public static final int WK_STATUS_HISTORY = 5;
	public static final int RV_WK_STATUS_ALL = 6;
	
	//table status
	public static final int TB_STATUS_FREE = 0;
	public static final int TB_STATUS_EATING = 1;
	public static final int TB_STATUS_HAS_RV = 2;
	
	//some global numbers
	public static final int TABLE_TOTAL_NUMBER = 10;
	public static final int TABLE_MAX_COVERS = 8;
}
