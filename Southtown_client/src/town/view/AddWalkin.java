package town.view;

import java.sql.Date;
import java.sql.Time;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Spinner;

import town.model.Reservation;
import town.socket.Client;
import town.util.Msg;
import town.util.Transfer;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class AddWalkin extends Dialog {

	protected Object result;
	protected Shell shell;
	private Combo combo;
	private Spinner spinner;
	private DateTime dateTime;
	private DateTime dateTime_1;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public AddWalkin(Shell parent, int style) {
		super(parent, style);
		setText("顾客开单");
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
		shell.setSize(450, 300);
		shell.setText(getText());
		
		Label label = new Label(shell, SWT.NONE);
		label.setBounds(49, 27, 35, 17);
		label.setText("\u684C  \u53F7");
		
		Label label_1 = new Label(shell, SWT.NONE);
		label_1.setBounds(49, 97, 35, 17);
		label_1.setText("\u4EBA  \u6570");
		
		Label label_2 = new Label(shell, SWT.NONE);
		label_2.setBounds(49, 163, 35, 17);
		label_2.setText("\u65E5  \u671F");
		
		Label label_3 = new Label(shell, SWT.NONE);
		label_3.setBounds(49, 228, 35, 17);
		label_3.setText("\u65F6  \u95F4");
		
		dateTime = new DateTime(shell, SWT.DATE | SWT.DROP_DOWN);
		dateTime.setBounds(101, 159, 109, 25);
		
		dateTime_1 = new DateTime(shell, SWT.TIME);
		dateTime_1.setBounds(101, 224, 109, 24);
		
		Button button = new Button(shell, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//ok
				String number = combo.getText();
				int covers = Integer.parseInt(spinner.getText());
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
							mb.setMessage("时间冲突！ 请重新填写！");
							mb.open();
							return;
						}
						System.out.println(date.toString() +" " +d.toString());
					}
				}
				Reservation r = new Reservation();
				r.setCovers(covers);
				r.setDate(date);
				r.setTime(time);
				r.setTid(index+1);
				r.setStatus(Msg.WK_STATUS_EATING);
				
				Transfer trans = new Transfer();
				trans.setMessage(Msg.SAVE_WALKIN);
				trans.setObj(r);
				
				String str = (String) new Client(trans).getObject();
				System.out.println(str);
				
				MessageBox mb = new MessageBox(shell, SWT.ICON_WORKING | SWT.YES);
				mb.setText("添加成功");
				mb.setMessage("就餐添加成功");
				mb.open();
				
				MainWindow.setTableStatus(Msg.TB_STATUS_EATING, index);
				MainWindow.setButtons();
				MainWindow.initLeftBar();
				
				shell.dispose();
			}
		});
		button.setBounds(303, 46, 102, 48);
		button.setText("\u786E\u5B9A");
		
		Button button_1 = new Button(shell, SWT.NONE);
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//cancel
				shell.dispose();
			}
		});
		button_1.setBounds(303, 174, 102, 48);
		button_1.setText("\u53D6\u6D88");
		
		combo = new Combo(shell, SWT.NONE);
		combo.setBounds(101, 23, 109, 25);
		initCombo();
		
		spinner = new Spinner(shell, SWT.BORDER);
		spinner.setBounds(101, 92, 109, 27);
		initSpinner();

	}
	
	private void initCombo() {
		for(int i = 0; i < Msg.TABLE_TOTAL_NUMBER; i++) {
			String str = "T00" + i;
			combo.add(str);
		}
		combo.setText("T000");
	}
	
	private void initSpinner() {
		spinner.setMinimum(1);
		spinner.setMaximum(Msg.TABLE_MAX_COVERS);
		spinner.setIncrement(1);
	}
}
