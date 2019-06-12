package town.view;

import java.sql.Date;
import java.sql.Time;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

import town.model.Reservation;
import town.socket.Client;
import town.util.Msg;
import town.util.Transfer;

public class AddReservation extends Dialog {

	protected Object result;
	protected Shell shell;
	private Combo combo;
	private DateTime dateTime;
	private DateTime dateTime_1;
	private Spinner spinner;
	private Text text;
	private Text text_1;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public AddReservation(Shell parent, int style) {
		super(parent, style);
		setText("添加订单");
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
		shell.setSize(568, 377);
		shell.setText(getText());
		
		Label label = new Label(shell, SWT.CENTER);
		label.setBounds(58, 39, 35, 17);
		label.setText("\u684C  \u53F7");
		
		combo = new Combo(shell, SWT.NONE);
		combo.setBounds(110, 33, 109, 27);
		initCombo();
		
		Button button = new Button(shell, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//ok
				String number = combo.getText();
				int covers = Integer.parseInt(spinner.getText());
				String name = text.getText();
				String phone = text_1.getText();
				String dtstr = dateTime.toString();
				String dateStr_d = dtstr.substring(10, dtstr.length()-1);
				String[] s = dateStr_d.split("/");
				dateStr_d = s[2] +"-" +s[0] +"-" +s[1];
				dtstr = dateTime_1.toString();
				String dateStr_t = dtstr.substring(10, dtstr.length()-1);
				Date date = Date.valueOf(dateStr_d);
				Time time = Time.valueOf(dateStr_t);
				
				int index = Integer.parseInt(number.substring(3));
				if(MainWindow.status[index] == Msg.TB_STATUS_EATING) {
					MessageBox mb = new MessageBox(shell, SWT.ICON_ERROR | SWT.YES);
					mb.setText("请重新填写");
					mb.setMessage("餐桌冲突！ 请重新填写！");
					mb.open();
					return;
				} else if(MainWindow.status[index] == Msg.TB_STATUS_HAS_RV) {
					Transfer trans = new Transfer();
					trans.setMessage(Msg.GET_NEW_RESERVATIONS_BY_TID);
					trans.setObj(index+1);
					List<Reservation> list = (List<Reservation>) new Client(trans).getObject();
					Iterator it = list.iterator();
					while(it.hasNext()) {
						Reservation r = (Reservation) it.next();
						Date d = r.getDate();
						long m = Math.abs((date.getTime() -d.getTime()));
						if(m < 43200000) {
							MessageBox mb = new MessageBox(shell, SWT.ICON_ERROR | SWT.YES);
							mb.setText("请重新填写");
							mb.setMessage("与已有预约时间冲突！ 请重新填写！");
							mb.open();
							return;
						}
					}
				}
				
				Reservation r = new Reservation();
				r.setCovers(covers);
				r.setDate(date);
				r.setTime(time);
				r.setStatus(Msg.RV_STATUS_NEW);
				
				Object[] params = new Object[3];
				params[0] = number;
				params[1] = name;
				params[2] = phone;
				
				Transfer trans = new Transfer();
				trans.setMessage(Msg.SAVE_RESERVATION);
				trans.setObj(r);
				trans.setParams(params);
				
				String str = (String) new Client(trans).getObject();
				System.out.println(str);
				
				MessageBox mb = new MessageBox(shell, SWT.ICON_WORKING | SWT.YES);
				mb.setText("添加成功");
				mb.setMessage("新的预约添加成功");
				mb.open();
				
				MainWindow.setTableStatus(Msg.TB_STATUS_HAS_RV, index);
				MainWindow.setButtons();
				MainWindow.initLeftBar();
				shell.dispose();
			}
		});
		button.setBounds(82, 269, 109, 57);
		button.setText("\u786E\u5B9A");
		
		Button button_1 = new Button(shell, SWT.NONE);
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//cancel
				shell.dispose();
			}
		});
		button_1.setBounds(346, 269, 116, 57);
		button_1.setText("\u53D6\u6D88");
		
		Label label_1 = new Label(shell, SWT.NONE);
		label_1.setBounds(310, 39, 35, 17);
		label_1.setText("\u4EBA  \u6570");
		
		spinner = new Spinner(shell, SWT.BORDER);
		spinner.setBounds(370, 34, 109, 27);
		initSpinner();
		
		Label label_2 = new Label(shell, SWT.NONE);
		label_2.setBounds(58, 104, 35, 17);
		label_2.setText("\u59D3  \u540D");
		
		text = new Text(shell, SWT.BORDER);
		text.setBounds(110, 100, 109, 27);
		
		Label label_3 = new Label(shell, SWT.NONE);
		label_3.setBounds(310, 104, 35, 17);
		label_3.setText("\u7535  \u8BDD");
		
		text_1 = new Text(shell, SWT.BORDER);
		text_1.setBounds(371, 100, 109, 27);
		
		Label label_4 = new Label(shell, SWT.NONE);
		label_4.setBounds(58, 185, 35, 17);
		label_4.setText("\u65E5  \u671F");
		
		dateTime = new DateTime(shell, SWT.DATE | SWT.DROP_DOWN);
		dateTime.setBounds(110, 181, 109, 27);
		
		dateTime_1 = new DateTime(shell, SWT.TIME);
		dateTime_1.setBounds(369, 181, 109, 27);
		
		Label label_5 = new Label(shell, SWT.NONE);
		label_5.setBounds(310, 185, 35, 17);
		label_5.setText("\u65F6  \u95F4");
	}
	
	private void initCombo() {
		for(int i = 0; i < Msg.TABLE_TOTAL_NUMBER; i++) {
			String str = "T00" + i;
			combo.add(str);
		}
	}
	
	private void initSpinner() {
		spinner.setMinimum(1);
		spinner.setMaximum(Msg.TABLE_MAX_COVERS);
		spinner.setIncrement(1);
	}
}
