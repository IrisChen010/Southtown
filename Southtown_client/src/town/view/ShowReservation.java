package town.view;

import java.sql.Date;
import java.sql.Time;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;

import town.model.Customer;
import town.model.Reservation;
import town.socket.Client;
import town.util.Msg;
import town.util.Transfer;

public class ShowReservation extends Dialog {

	protected Object result;
	protected Shell shell;
	private Table table;
	private TableColumn tableColumn_1;
	private TableColumn tableColumn_2;
	private TableColumn tableColumn_3;
	private TableColumn tableColumn_4;
	private TableColumn tableColumn_5;
	private Button button;
	private Text text;
	private Text text_1;
	private Label label;
	private Label label_1;
	private Label label_2;
	private Button button_1;
	//private Button button_2;
	private Button button_3;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public ShowReservation(Shell parent, int style) {
		super(parent, style);
		setText("显示预约");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), getStyle());
		shell.setSize(800, 600);
		shell.setText(getText());
		
		table = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(32, 116, 736, 428);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tableColumn = new TableColumn(table, SWT.NONE);
		tableColumn.setWidth(59);
		tableColumn.setText("\u5E8F\u53F7");
		
		tableColumn_1 = new TableColumn(table, SWT.NONE);
		tableColumn_1.setWidth(100);
		tableColumn_1.setText("\u59D3\u540D");
		
		tableColumn_2 = new TableColumn(table, SWT.NONE);
		tableColumn_2.setWidth(100);
		tableColumn_2.setText("\u7535\u8BDD");
		
		tableColumn_3 = new TableColumn(table, SWT.NONE);
		tableColumn_3.setWidth(71);
		tableColumn_3.setText("\u684C\u53F7");
		
		tableColumn_4 = new TableColumn(table, SWT.NONE);
		tableColumn_4.setWidth(62);
		tableColumn_4.setText("\u4EBA\u6570");
		
		tableColumn_5 = new TableColumn(table, SWT.NONE);
		tableColumn_5.setWidth(206);
		tableColumn_5.setText("\u65E5\u671F");
		
		TableColumn tableColumn_6 = new TableColumn(table, SWT.NONE);
		tableColumn_6.setWidth(100);
		tableColumn_6.setText("\u5907\u6CE8");
		
		button = new Button(shell, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//顾客开台
			}
		});
		button.setBounds(32, 23, 80, 54);
		button.setText("\u987E\u5BA2\u5F00\u53F0");
		
		text = new Text(shell, SWT.BORDER);
		text.setBounds(583, 25, 98, 23);
		
		text_1 = new Text(shell, SWT.BORDER);
		text_1.setBounds(583, 54, 98, 23);
		
		label = new Label(shell, SWT.NONE);
		label.setBounds(526, 28, 35, 17);
		label.setText("\u59D3  \u540D");
		
		label_1 = new Label(shell, SWT.NONE);
		label_1.setBounds(526, 57, 35, 17);
		label_1.setText("\u7535  \u8BDD");
		
		button_1 = new Button(shell, SWT.NONE);
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//search
				String name = text.getText();
				String phone = text_1.getText();
				showReservationsByInfo(name, phone);
			}
		});
		button_1.setBounds(697, 23, 62, 54);
		button_1.setText("\u641C\u7D22");
		
//		button_2 = new Button(shell, SWT.NONE);
//		button_2.setBounds(148, 23, 80, 54);
//		button_2.setText("\u4FEE\u6539\u9884\u7EA6");
		
		button_3 = new Button(shell, SWT.NONE);
		button_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//cancel reservation
				int index = table.getSelectionIndex();
				if(index != -1) {
					if(table.getItem(index).getText(6).equalsIgnoreCase("新预约")) {
						String number = table.getItem(table.getSelectionIndex()).getText(3);
						int tid = Integer.parseInt(number.substring(3)) +1;
						String dt = table.getItem(table.getSelectionIndex()).getText(5);
						
						System.out.println(dt.substring(0, 10) +" " +dt.substring(12));
						
						Date date = Date.valueOf(dt.substring(0, 10));
						Time time = Time.valueOf(dt.substring(12));
						Transfer trans = new Transfer();
						trans.setMessage(Msg.CANCEL_NEW_RESERVATION);
						Object[] params = new Object[3];
						params[0] = tid;
						params[1] = date;
						params[2] = time;
						trans.setParams(params);
						String str = (String) new Client(trans).getObject();
						System.out.println(str);
						
						trans.setMessage(Msg.GET_NEW_RESERVATIONS_BY_TID);
						trans.setObj(tid);
						List<Reservation> list = (List<Reservation>) new Client(trans).getObject();
						if(list.isEmpty()) {
							MainWindow.setTableStatus(Msg.TB_STATUS_FREE, tid-1);
						} else {
							MainWindow.setTableStatus(Msg.TB_STATUS_HAS_RV, tid-1);
						}
						MainWindow.setButtons();
						
						MessageBox mb = new MessageBox(shell, SWT.ICON_WORKING | SWT.YES);
						mb.setText("取消成功");
						mb.setMessage("预约取消成功！");
						mb.open();
						showAllReservations();
						return ;
					}
					MessageBox mb = new MessageBox(shell, SWT.ICON_ERROR | SWT.YES);
					mb.setText("无法取消");
					mb.setMessage("无效预约，无法取消！");
					mb.open();
				}
			}
		});
		//button_3.setBounds(266, 23, 80, 54);
		button_3.setBounds(148, 23, 80, 54);
		button_3.setText("\u53D6\u6D88\u9884\u7EA6");

		showAllReservations();
	}
	
	private void showAllReservations() {
		table.removeAll();
		showReservationsByStatus(Msg.RV_STATUS_EATING);
		showReservationsByStatus(Msg.WK_STATUS_EATING);
		showReservationsByStatus(Msg.RV_STATUS_NEW);
		showReservationsByStatus(Msg.RV_STATUS_HISTORY);
		showReservationsByStatus(Msg.RV_STATUS_CANCEL);
		showReservationsByStatus(Msg.WK_STATUS_HISTORY);
	}
	
	@SuppressWarnings("unchecked")
	private void showReservationsByStatus(int status) {
		//table.removeAll();
		List<Reservation> list = (List<Reservation>) new Client(Msg.GET_ALL_RESERVATIONS).getObject();
		Iterator it = list.iterator();
		while(it.hasNext()) {
			Reservation r = (Reservation)it.next();
			if(r.getStatus() == status) {
				showReservationItem(r);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private void showReservationsByInfo(String name, String phone) {
		if(name.equalsIgnoreCase("")
				&& phone.equalsIgnoreCase("")) {
			showAllReservations();
			return;
		}
		table.removeAll();
		List<Reservation> list = (List<Reservation>) new Client(Msg.GET_ALL_RESERVATIONS).getObject();
		Iterator it = list.iterator();
		while(it.hasNext()) {
			Reservation r = (Reservation)it.next();
			int status = r.getStatus();
			Customer customer = new Customer();
			Transfer trans = new Transfer();
			if(status == Msg.RV_STATUS_NEW
					|| status == Msg.RV_STATUS_CANCEL
					|| status == Msg.RV_STATUS_EATING
					|| status == Msg.RV_STATUS_HISTORY) {
				int cid = r.getCid();
				trans.setMessage(Msg.GET_CUSTOMER_BY_ID);
				trans.setObj(cid);
				customer = (Customer) new Client(trans).getObject();
			}
			if(name.equalsIgnoreCase(customer.getName())
					|| phone.equalsIgnoreCase(customer.getPhone())) {
				showReservationItem(r);
			}
		}
	}
	
	private void showReservationItem(Reservation r) {
		Customer customer = new Customer();
		Transfer trans = new Transfer();
		int status = r.getStatus();
		
		if(status == Msg.RV_STATUS_NEW
				|| status == Msg.RV_STATUS_CANCEL
				|| status == Msg.RV_STATUS_EATING
				|| status == Msg.RV_STATUS_HISTORY) {
			int cid = r.getCid();
			trans.setMessage(Msg.GET_CUSTOMER_BY_ID);
			trans.setObj(cid);
			customer = (Customer) new Client(trans).getObject();
		} else {
			customer.setName("无");
			customer.setPhone("无");
		}
		
		int tid = r.getTid();
		trans.setMessage(Msg.GET_TABLE_BY_ID);
		trans.setObj(tid);
		town.model.Table t = (town.model.Table) new Client(trans).getObject();
		
		String extra = "";
		switch(status) {
			case Msg.RV_STATUS_NEW:
				extra = "新预约";
				break;
			case Msg.RV_STATUS_CANCEL:
				extra = "预约取消";
				break;
			case Msg.RV_STATUS_EATING:
				extra = "预约就餐中";
				break;
			case Msg.RV_STATUS_HISTORY:
				extra = "预约历史";
				break;
			case Msg.WK_STATUS_EATING:
				extra = "未预约/就餐中";
				break;
			case Msg.WK_STATUS_HISTORY:
				extra = "未预约/历史";
				break;
		}
		new TableItem(table, SWT.LEFT).setText(new String[] {Integer.toString(table.getItemCount()),
				customer.getName(),
				customer.getPhone(),
				t.getNumber(),
				Integer.toString(r.getCovers()),
				r.getDate().toString() +"  " +r.getTime().toString(),
				extra});
	}
}
