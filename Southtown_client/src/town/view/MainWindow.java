package town.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import town.model.Menu;
import town.socket.Client;
import town.util.Msg;
import town.util.Transfer;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class MainWindow {

	protected Shell shell;
	private static Table table;
	private static Display display = Display.getDefault();
	public static int[] status = new int[Msg.TABLE_TOTAL_NUMBER];
	
	public static Button btnNo;
	public static Button btnNo_1;
	public static Button btnNo_2;
	public static Button btnNo_3;
	public static Button btnNo_4;
	public static Button btnNo_5;
	public static Button btnNo_6;
	public static Button btnNo_7;
	public static Button btnNo_8;
	public static Button btnNo_9;
	
	
	private GoodsManage sGoodsManage;
	private boolean bGoodsManage;
	
	private static int currentTableCovers = 0;
	private static int currentTableMenusNum = 0;
	private static int currentTableMoney = 0;
	
	public static int currentTableId = -1;
	private static Label label_2;
	private static Label label_4;
	private static Label label_6;
	private static Label label_8;
	private static Label label_10;
	private static Label label_12;
	private static Label label_14;
	private static Label label_16;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MainWindow window = new MainWindow();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setLocation(0, 0);
		shell.setSize(1366, 768);
		shell.setText("江南古镇");
		Image image = new Image(display, "resources/main.jpg");
		shell.setImage(image);
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setBounds(0, 0, 1366, 730);
		
		Group group = new Group(composite, SWT.NONE);
		group.setBounds(0, -12, 1366, 96);
		
		Button button = new Button(group, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				AddWalkin sWalkin = new AddWalkin(shell, SWT.MIN | SWT.APPLICATION_MODAL);
				sWalkin.open();
			}
		});
		button.setBounds(31, 25, 105, 61);
		button.setText("\u987E\u5BA2\u5F00\u5355");
		
		Button button_1 = new Button(group, SWT.NONE);
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				AddReservation sReservation = new AddReservation(shell, SWT.MIN | SWT.APPLICATION_MODAL);
				sReservation.open();
			}
		});
		button_1.setBounds(179, 25, 105, 61);
		button_1.setText("\u6DFB\u52A0\u8BA2\u5355");
		
		Button button_2 = new Button(group, SWT.NONE);
		button_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//增加消费
				if(currentTableId != -1) {
					if(status[currentTableId-1] != Msg.TB_STATUS_EATING) {
						MessageBox mb = new MessageBox(shell, SWT.ICON_ERROR | SWT.YES);
						mb.setText("空闲餐桌");
						mb.setMessage("\n餐桌空闲，无法添加！\n\n请更换餐桌！");
						mb.open();
					} else {
						AddConsumption ac = new AddConsumption(shell, SWT.MIN | SWT.APPLICATION_MODAL);
						ac.open();
					}
					currentTableId = -1;
				}
			}
		});
		button_2.setBounds(323, 25, 105, 61);
		button_2.setText("\u589E\u52A0\u6D88\u8D39");
		
		Button button_3 = new Button(group, SWT.NONE);
		button_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//show reservation
				ShowReservation sr = new ShowReservation(shell, SWT.MIN | SWT.APPLICATION_MODAL);
				sr.open();
			}
		});
		button_3.setBounds(470, 25, 105, 61);
		button_3.setText("\u663E\u793A\u9884\u7EA6");
		
		Button button_4 = new Button(group, SWT.NONE);
		button_4.setBounds(613, 25, 105, 61);
		button_4.setText("\u9910\u53F0\u66F4\u6362");
		
		Button button_5 = new Button(group, SWT.NONE);
		//sGoodsManage = new GoodsManage();
		bGoodsManage = false;
		button_5.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(bGoodsManage == false) {
					sGoodsManage = new GoodsManage();
					bGoodsManage = true;
					sGoodsManage.open();
					bGoodsManage = false;
				} else {
					sGoodsManage.shell.setFocus();
				}
			}
		});
		button_5.setBounds(753, 25, 105, 61);
		button_5.setText("\u83DC\u54C1\u7BA1\u7406");
		
		Button button_6 = new Button(group, SWT.NONE);
		button_6.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(currentTableId != -1) {
					if(status[currentTableId-1] != Msg.TB_STATUS_EATING) {
						MessageBox mb = new MessageBox(shell, SWT.ICON_ERROR | SWT.YES);
						mb.setText("空闲餐桌");
						mb.setMessage("\n餐桌空闲，无法结帐！\n\n请更换餐桌！");
						mb.open();
					} else {
						PayOrder pay = new PayOrder(shell, SWT.MIN | SWT.APPLICATION_MODAL);
						pay.open();
					}
					switch(currentTableId) {
						case 1:
							MainWindow.showTableOrderMenus(MainWindow.btnNo, currentTableId);
							break;
						case 2:
							MainWindow.showTableOrderMenus(MainWindow.btnNo_1, currentTableId);
							break;
						case 3:
							MainWindow.showTableOrderMenus(MainWindow.btnNo_2, currentTableId);
							break;
						case 4:
							MainWindow.showTableOrderMenus(MainWindow.btnNo_3, currentTableId);
							break;
						case 5:
							MainWindow.showTableOrderMenus(MainWindow.btnNo_4, currentTableId);
							break;
						case 6:
							MainWindow.showTableOrderMenus(MainWindow.btnNo_5, currentTableId);
							break;
						case 7:
							MainWindow.showTableOrderMenus(MainWindow.btnNo_6, currentTableId);
							break;
						case 8:
							MainWindow.showTableOrderMenus(MainWindow.btnNo_7, currentTableId);
							break;
						case 9:
							MainWindow.showTableOrderMenus(MainWindow.btnNo_8, currentTableId);
							break;
						case 10:
							MainWindow.showTableOrderMenus(MainWindow.btnNo_9, currentTableId);
							break;
					}
					currentTableId = -1;
				}
			}
		});
		button_6.setBounds(898, 25, 105, 61);
		button_6.setText("\u987E\u5BA2\u7ED3\u8D26");
		
		Group group_1 = new Group(composite, SWT.NONE);	//左侧
		group_1.setBounds(0, 82, 300, 630);
		
		Label label = new Label(group_1, SWT.NONE);
		label.setBounds(22, 20, 90, 17);
		label.setText("\u64CD\u4F5C\u5458\u59D3\u540D\uFF1A");
		
		label_2 = new Label(group_1, SWT.NONE);
		label_2.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		label_2.setBounds(120, 20, 90, 17);
		
		Label label_3 = new Label(group_1, SWT.NONE);
		label_3.setBounds(22, 60, 90, 17);
		label_3.setText("\u64CD\u4F5C\u5458\u6743\u9650\uFF1A");
		
		label_4 = new Label(group_1, SWT.NONE);
		label_4.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		label_4.setBounds(120, 60, 90, 17);
		
		Label label_5 = new Label(group_1, SWT.NONE);
		label_5.setBounds(22, 130, 90, 17);
		label_5.setText("\u9910\u684C\u603B\u6570\u91CF\uFF1A");
		
		label_6 = new Label(group_1, SWT.NONE);
		label_6.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		label_6.setBounds(120, 130, 90, 17);
		
		Label label_7 = new Label(group_1, SWT.NONE);
		label_7.setBounds(22, 170, 90, 17);
		label_7.setText("\u5F53\u524D\u53EF\u7528\u6570\uFF1A");
		
		label_8 = new Label(group_1, SWT.NONE);
		label_8.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		label_8.setBounds(120, 170, 90, 17);
		
		Label label_9 = new Label(group_1, SWT.NONE);
		label_9.setBounds(22, 240, 90, 17);
		label_9.setText("\u5F53\u524D\u9910\u684C\u53F7\uFF1A");
		
		label_10 = new Label(group_1, SWT.NONE);
		label_10.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		label_10.setBounds(120, 240, 90, 17);
		
		Label label_11 = new Label(group_1, SWT.NONE);
		label_11.setBounds(22, 280, 90, 17);
		label_11.setText("\u9910\u684C\u7684\u4EBA\u6570\uFF1A");
		
		label_12 = new Label(group_1, SWT.NONE);
		label_12.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		label_12.setBounds(120, 280, 90, 17);
		
		Label label_13 = new Label(group_1, SWT.NONE);
		label_13.setBounds(22, 320, 90, 17);
		label_13.setText("\u70B9\u83DC\u7684\u6570\u91CF\uFF1A");
		
		label_14 = new Label(group_1, SWT.NONE);
		label_14.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		label_14.setBounds(120, 320, 90, 17);
		
		Label label_15 = new Label(group_1, SWT.NONE);
		label_15.setBounds(22, 360, 90, 17);
		label_15.setText("\u6D88\u8D39\u603B\u91D1\u989D\uFF1A");
		
		label_16 = new Label(group_1, SWT.NONE);
		label_16.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		label_16.setBounds(120, 360, 90, 17);
		
		Group group_2 = new Group(composite, SWT.NONE);	//状态栏组
		group_2.setBounds(0, 710, 1366, 730);
		
		Group group_3 = new Group(composite, SWT.NONE);
		group_3.setBounds(171, 75, 1366, 645);
		
		btnNo = new Button(group_3, SWT.NONE);
		btnNo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//table No.0
				currentTableId = 1;
				showTableOrderMenus(btnNo, 1);
			}
		});
		btnNo.setBounds(189, 72, 142, 91);
		btnNo.setText("No.0");
		
		table = new Table(group_3, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(128, 428, 1067, 210);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tableColumn_4 = new TableColumn(table, SWT.NONE);
		tableColumn_4.setWidth(100);
		tableColumn_4.setText("\u5E8F\u53F7");
		
		TableColumn tableColumn_5 = new TableColumn(table, SWT.NONE);
		tableColumn_5.setWidth(100);
		tableColumn_5.setText("\u7F16\u53F7");
		
		TableColumn tableColumn = new TableColumn(table, SWT.NONE);
		tableColumn.setWidth(100);
		tableColumn.setText("\u83DC\u54C1\u540D\u79F0");
		
		TableColumn tableColumn_6 = new TableColumn(table, SWT.NONE);
		tableColumn_6.setWidth(100);
		tableColumn_6.setText("\u6570\u91CF");
		
		TableColumn tableColumn_8 = new TableColumn(table, SWT.NONE);
		tableColumn_8.setWidth(100);
		tableColumn_8.setText("\u5355\u4F4D");
		
		TableColumn tableColumn_1 = new TableColumn(table, SWT.NONE);
		tableColumn_1.setWidth(100);
		tableColumn_1.setText("\u5355\u4EF7");
		
		TableColumn tableColumn_3 = new TableColumn(table, SWT.NONE);
		tableColumn_3.setWidth(100);
		tableColumn_3.setText("\u91D1\u989D");
		
		TableColumn tableColumn_2 = new TableColumn(table, SWT.NONE);
		tableColumn_2.setWidth(100);
		tableColumn_2.setText("\u6DFB\u52A0\u4EBA");
		
		TableColumn tableColumn_7 = new TableColumn(table, SWT.NONE);
		tableColumn_7.setWidth(200);
		tableColumn_7.setText("\u6D88\u8D39\u65F6\u95F4");
		
		btnNo_1 = new Button(group_3, SWT.NONE);
		btnNo_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//table No.1
				currentTableId = 2;
				showTableOrderMenus(btnNo_1, 2);
			}
		});
		btnNo_1.setBounds(382, 72, 142, 91);
		btnNo_1.setText("No.1");
		
		btnNo_2 = new Button(group_3, SWT.NONE);
		btnNo_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//table No.2
				currentTableId = 3;
				showTableOrderMenus(btnNo_2, 3);
			}
		});
		btnNo_2.setBounds(576, 72, 142, 91);
		btnNo_2.setText("No.2");
		
		btnNo_3 = new Button(group_3, SWT.NONE);
		btnNo_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//table No.3
				currentTableId = 4;
				showTableOrderMenus(btnNo_3, 4);
			}
		});
		btnNo_3.setBounds(769, 72, 142, 91);
		btnNo_3.setText("No.3");
		
		btnNo_4 = new Button(group_3, SWT.NONE);
		btnNo_4.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//table No.4
				currentTableId = 5;
				showTableOrderMenus(btnNo_4, 5);
			}
		});
		btnNo_4.setBounds(965, 72, 142, 91);
		btnNo_4.setText("No.4");
		
		btnNo_5 = new Button(group_3, SWT.NONE);
		btnNo_5.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//table No.5
				currentTableId = 6;
				showTableOrderMenus(btnNo_5, 6);
			}
		});
		btnNo_5.setBounds(189, 249, 142, 91);
		btnNo_5.setText("No.5");
		
		btnNo_6 = new Button(group_3, SWT.NONE);
		btnNo_6.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//table No.6
				currentTableId = 7;
				showTableOrderMenus(btnNo_6, 7);
			}
		});
		btnNo_6.setBounds(382, 249, 142, 91);
		btnNo_6.setText("No.6");
		
		btnNo_7 = new Button(group_3, SWT.NONE);
		btnNo_7.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//table No.7
				currentTableId = 8;
				showTableOrderMenus(btnNo_7, 8);
			}
		});
		btnNo_7.setBounds(576, 249, 142, 91);
		btnNo_7.setText("No.7");
		
		btnNo_8 = new Button(group_3, SWT.NONE);
		btnNo_8.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//table No.8
				currentTableId = 9;
				showTableOrderMenus(btnNo_8, 9);
			}
		});
		btnNo_8.setBounds(769, 249, 142, 91);
		btnNo_8.setText("No.8");
		
		btnNo_9 = new Button(group_3, SWT.NONE);
		btnNo_9.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//table No.9
				currentTableId = 10;
				showTableOrderMenus(btnNo_9, 10);
			}
		});
		btnNo_9.setBounds(965, 249, 142, 91);
		btnNo_9.setText("No.9");
		
		getTableStatuses();
		setButtons();
		initLeftBar();
	}
	
	public static void getTableStatuses() {
		status = (int[]) new Client(Msg.GET_TABLE_STATUSES).getObject();
	}
	
	public static void setTableStatus(int s, int index) {
		status[index] = s;
		Transfer trans = new Transfer();
		trans.setMessage(Msg.SET_TABLE_STATUSES);
		trans.setObj(status);
		String str = (String) new Client(trans).getObject();
		System.out.println(str);
	}
	
	public static void setButtons() {
		setButton(btnNo, 1);
		setButton(btnNo_1, 2);
		setButton(btnNo_2, 3);
		setButton(btnNo_3, 4);
		setButton(btnNo_4, 5);
		setButton(btnNo_5, 6);
		setButton(btnNo_6, 7);
		setButton(btnNo_7, 8);
		setButton(btnNo_8, 9);
		setButton(btnNo_9, 10);
	}
	
	public static int setButton(Button button, int tid) {
		Transfer trans = new Transfer();
		trans.setMessage(Msg.GET_TABLE_BY_ID);
		trans.setObj(tid);
		town.model.Table t = (town.model.Table) new Client(trans).getObject();
		Image image = null;
		switch(t.getStatus()) {
			case Msg.TB_STATUS_FREE:
				button.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
				button.setText("No." +(tid-1) +"\n\n闲置");
				break;
			case Msg.TB_STATUS_EATING:
				button.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
				button.setText("No." +(tid-1) +"\n\n就餐中");
				break;
			case Msg.TB_STATUS_HAS_RV:
				button.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_CYAN));
				button.setText("No." +(tid-1) +"\n\n有预约");
				break;
		}
		return t.getStatus();
	}
	
	@SuppressWarnings("unchecked")
	public static List<Menu> getTableOrderMenus(int tid) {
		List<Menu> list = new ArrayList<Menu>();
		Transfer trans = new Transfer();
		trans.setMessage(Msg.GET_TABLE_ORDER_MENUS);
		trans.setObj(tid);
		list = (List<Menu>) new Client(trans).getObject();
		return list;
	}
	
	public static void showTableOrderMenus(Button button, int tid) {
		table.removeAll();
		int status = setButton(button, tid);
		switch(status) {
			case Msg.TB_STATUS_EATING:
				List<Menu> list = getTableOrderMenus(tid);
				Iterator it = list.iterator();
				SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");     
				String date = sDateFormat.format(new java.util.Date());
				int count = 0, price = 0;
				while(it.hasNext()) {
					count++;
					Menu menu = (Menu) it.next();
					price += menu.getPrice();
					new TableItem(table, SWT.LEFT).setText(new String[] {Integer.toString(table.getItemCount()),
							menu.getNumber(),
							menu.getName(),
							"1",
							"份",
							Integer.toString(menu.getPrice()),
							Integer.toString(menu.getPrice()),
							"付建宇",
							date});
				}
				currentTableMenusNum = count;
				currentTableMoney = price;
				break;
			default:
				break;
		}
		initLeftBar();
	}
	
	public static void initLeftBar() {
		label_2.setText(Login.stuff.getName());
		if(Login.stuff.getLevel() == 0) {
			label_4.setText("管理员");
		} else if(Login.stuff.getLevel() == 1){
			label_4.setText("普通职员");
		}
		label_6.setText(Integer.toString(Msg.TABLE_TOTAL_NUMBER) +"  张");
		int count = Msg.TABLE_TOTAL_NUMBER;
		for(int i = 0; i < Msg.TABLE_TOTAL_NUMBER; i++) {
			if(status[i] == Msg.TB_STATUS_EATING) {
				count--;
			}
		}
		label_8.setText(Integer.toString(count) +"  张");
		if(currentTableId != -1) {
			if(status[currentTableId-1] == Msg.TB_STATUS_EATING) {
				label_10.setText("T00" +Integer.toString(currentTableId-1));
				currentTableCovers = PayOrder.getTableEatingReservation(currentTableId).getCovers();
				label_12.setText(Integer.toString(currentTableCovers) +"  个");
				label_14.setText(Integer.toString(currentTableMenusNum) +"  份");
				label_16.setText("￥ " +Integer.toString(currentTableMoney) +"  元");
			} else {
				label_10.setText("T00" +Integer.toString(currentTableId-1));
				label_12.setText("空");
				label_14.setText("空");
				label_16.setText("空");
			}
		} else {
			label_10.setText("空");
			label_12.setText("空");
			label_14.setText("空");
			label_16.setText("空");
		}
		
		
	}
}
