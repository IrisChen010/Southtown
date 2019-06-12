package town.view;

import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;

import town.model.Menu;
import town.model.Reservation;
import town.socket.Client;
import town.util.Msg;
import town.util.Transfer;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class PayOrder extends Dialog {

	protected Object result;
	protected Shell shell;
	private Table table;
	private Text text;
	private Text text_1;
	private Text text_2;
	private Reservation r;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public PayOrder(Shell parent, int style) {
		super(parent, style);
		setText("¹Ë¿Í½áÕË");
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
		
		Label label = new Label(shell, SWT.NONE);
		label.setBounds(73, 54, 35, 17);
		label.setText("\u684C  \u53F7");
		
		table = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(73, 136, 619, 258);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tableColumn = new TableColumn(table, SWT.NONE);
		tableColumn.setWidth(100);
		tableColumn.setText("\u5E8F\u53F7");
		
		TableColumn tableColumn_1 = new TableColumn(table, SWT.NONE);
		tableColumn_1.setWidth(100);
		tableColumn_1.setText("\u7F16\u53F7");
		
		TableColumn tableColumn_2 = new TableColumn(table, SWT.NONE);
		tableColumn_2.setWidth(100);
		tableColumn_2.setText("\u83DC\u54C1\u540D\u79F0");
		
		TableColumn tableColumn_3 = new TableColumn(table, SWT.NONE);
		tableColumn_3.setWidth(100);
		tableColumn_3.setText("\u5355\u4EF7");
		
		TableColumn tableColumn_4 = new TableColumn(table, SWT.NONE);
		tableColumn_4.setWidth(100);
		tableColumn_4.setText("\u6570\u91CF");
		
		TableColumn tableColumn_5 = new TableColumn(table, SWT.NONE);
		tableColumn_5.setWidth(100);
		tableColumn_5.setText("\u91D1\u989D");
		
		text = new Text(shell, SWT.BORDER | SWT.READ_ONLY);
		text.setBounds(126, 51, 73, 23);
		
		Label label_1 = new Label(shell, SWT.NONE);
		label_1.setBounds(548, 432, 35, 17);
		label_1.setText("\u603B  \u989D");
		
		text_1 = new Text(shell, SWT.BORDER | SWT.READ_ONLY);
		text_1.setBounds(600, 429, 73, 23);
		
		Label label_2 = new Label(shell, SWT.NONE);
		label_2.setBounds(679, 432, 15, 17);
		label_2.setText("\u5143");
		
		Label label_3 = new Label(shell, SWT.NONE);
		label_3.setBounds(289, 54, 35, 17);
		label_3.setText("\u4EBA  \u6570");
		
		text_2 = new Text(shell, SWT.BORDER | SWT.READ_ONLY);
		text_2.setBounds(341, 51, 73, 23);
		
		Label label_4 = new Label(shell, SWT.NONE);
		label_4.setBounds(73, 105, 97, 17);
		label_4.setText("\u6D88\u8D39\u83DC\u54C1\u8BE6\u7EC6");
		
		Button button = new Button(shell, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//pay
				Transfer trans = new Transfer();
				trans.setMessage(Msg.PAY_ORDER_SUCCESS);
				trans.setObj(r);
				
				String str = (String) new Client(trans).getObject();
				System.out.println(str);
				
				trans.setMessage(Msg.GET_NEW_RESERVATIONS_BY_TID);
				trans.setObj(r.getTid());
				List<Reservation> list = (List<Reservation>) new Client(trans).getObject();
				if(list.isEmpty()) {
					MainWindow.setTableStatus(Msg.TB_STATUS_FREE, r.getTid()-1);
				} else {
					MainWindow.setTableStatus(Msg.TB_STATUS_HAS_RV, r.getTid()-1);
				}
				MainWindow.setButtons();
				shell.dispose();
			}
		});
		button.setBounds(511, 487, 103, 49);
		button.setText("\u7ED3\u8D26");
		
		Button button_1 = new Button(shell, SWT.NONE);
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//cancel
				shell.dispose();
			}
		});
		button_1.setBounds(635, 487, 103, 49);
		button_1.setText("\u53D6\u6D88");
		
		initWindow();
	}
	
	private void initWindow() {
		int tid = MainWindow.currentTableId;
		int price = 0;
		text.setText("T00" +Integer.toString(tid-1));
		r = getTableEatingReservation(tid);
		text_2.setText(Integer.toString(r.getCovers()));
		List<Menu> list = MainWindow.getTableOrderMenus(tid);
		Iterator it = list.iterator();
		while(it.hasNext()) {
			Menu menu = (Menu) it.next();
			price += menu.getPrice();
			new TableItem(table, SWT.LEFT).setText(new String[] {Integer.toString(table.getItemCount()),
					menu.getNumber(),
					menu.getName(),
					Integer.toString(menu.getPrice()),
					"1",
					Integer.toString(menu.getPrice())});
		}
		text_1.setText(Integer.toString(price));
	}
	
	@SuppressWarnings("unchecked")
	public static Reservation getTableEatingReservation(int tid) {
		Transfer trans = new Transfer();
		trans.setMessage(Msg.GET_TABLE_EATING_RESERVATION);
		trans.setObj(tid);
		Reservation r = (Reservation) new Client(trans).getObject();
		return r;
	}
}
