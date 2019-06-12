package town.view;

import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import town.model.Menu;
import town.model.Reservation;
import town.socket.Client;
import town.util.Msg;
import town.util.Transfer;

public class AddConsumption extends Dialog {

	protected Object result;
	protected Shell shell;
	private Table table;
	private Combo combo;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public AddConsumption(Shell parent, int style) {
		super(parent, style);
		setText("增加消费");
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
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setBounds(0, 0, 800, 562);
		
		Group group = new Group(composite, SWT.NONE);
		group.setBounds(0, -11, 800, 84);
		
		Button button = new Button(group, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//add
				if(table.getSelectionIndex() != -1) {
					int tid = MainWindow.currentTableId;
					Transfer trans = new Transfer();
					trans.setMessage(Msg.GET_TABLE_EATING_RESERVATION);
					trans.setObj(tid);
					Reservation r = (Reservation) new Client(trans).getObject();
					int rid = r.getId();
					
					Object[] params = new Object[2];
					String number = table.getItem(table.getSelectionIndex()).getText(1);
					params[0] = rid;
					params[1] = number;
					trans.setMessage(Msg.ADD_ORDER_TO_TABLE);
					trans.setParams(params);
					String str = (String) new Client(trans).getObject();
					System.out.println(str);
					
					switch(tid) {
						case 1:
							MainWindow.showTableOrderMenus(MainWindow.btnNo, tid);
							break;
						case 2:
							MainWindow.showTableOrderMenus(MainWindow.btnNo_1, tid);
							break;
						case 3:
							MainWindow.showTableOrderMenus(MainWindow.btnNo_2, tid);
							break;
						case 4:
							MainWindow.showTableOrderMenus(MainWindow.btnNo_3, tid);
							break;
						case 5:
							MainWindow.showTableOrderMenus(MainWindow.btnNo_4, tid);
							break;
						case 6:
							MainWindow.showTableOrderMenus(MainWindow.btnNo_5, tid);
							break;
						case 7:
							MainWindow.showTableOrderMenus(MainWindow.btnNo_6, tid);
							break;
						case 8:
							MainWindow.showTableOrderMenus(MainWindow.btnNo_7, tid);
							break;
						case 9:
							MainWindow.showTableOrderMenus(MainWindow.btnNo_8, tid);
							break;
						case 10:
							MainWindow.showTableOrderMenus(MainWindow.btnNo_9, tid);
							break;
					}
					
					MessageBox mb = new MessageBox(shell, SWT.ICON_WORKING | SWT.YES);
					mb.setText("添加成功");
					mb.setMessage("菜品添加成功！");
					mb.open();
				} else {
					MessageBox mb = new MessageBox(shell, SWT.ICON_ERROR | SWT.YES);
					mb.setText("无效添加");
					mb.setMessage("无效菜品！ 请选择菜品！");
					mb.open();
					return;
				}
			}
		});
		button.setBounds(22, 20, 80, 54);
		button.setText("\u6DFB\u52A0");
		
		combo = new Combo(group, SWT.NONE);
		combo.setBounds(633, 35, 109, 25);
		initCombo();
		combo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//kind changes
				showMenusByKind();
			}
			
		});
		
		Label label = new Label(group, SWT.CENTER);
		label.setBounds(586, 39, 46, 25);
		label.setText("\u7C7B\u522B");
		
		table = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(0, 72, 798, 495);
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
		
		TableColumn tableColumn_4 = new TableColumn(table, SWT.NONE);
		tableColumn_4.setWidth(100);
		tableColumn_4.setText("\u5355\u4EF7");
		
		TableColumn tableColumn_3 = new TableColumn(table, SWT.NONE);
		tableColumn_3.setWidth(100);
		tableColumn_3.setText("\u6240\u5C5E\u7C7B\u522B");
		
		showAllMenus();

	}
	
	@SuppressWarnings("unchecked")
	void showAllMenus() {
		combo.setText("All");
		table.removeAll();
		List<Menu> list = (List<Menu>) new Client(Msg.GET_ALL_MENU).getObject();
		Iterator it = list.iterator();
		while(it.hasNext()) {
			Menu menu = (Menu)it.next();
			new TableItem(table, SWT.LEFT).setText(new String[] {Integer.toString(table.getItemCount()),
					menu.getNumber(),
					menu.getName(),
					Integer.toString(menu.getPrice()),
					menu.getKind()});
		}
	}
	
	@SuppressWarnings("unchecked")
	void showMenusByKind() {
		String kind = combo.getText();
		combo.setText(kind);
		if(kind.equalsIgnoreCase("All")) {
			showAllMenus();
		} else {
			Transfer trans = new Transfer();
			trans.setMessage(Msg.GET_MENUS_BY_KIND);
			trans.setObj(kind);
			List<Menu> list = (List<Menu>) new Client(trans).getObject();
			table.removeAll();
			Iterator it = list.iterator();
			while(it.hasNext()) {
				Menu menu = (Menu)it.next();
				new TableItem(table, SWT.LEFT).setText(new String[] {Integer.toString(table.getItemCount()),
						menu.getNumber(),
						menu.getName(),
						Integer.toString(menu.getPrice()),
						menu.getKind()});
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private void initCombo() {
		combo.removeAll();
		combo.add("All");
		List<String> list = (List<String>) new Client(Msg.GET_MENU_KINDS).getObject();
		Iterator it = list.iterator();
		while(it.hasNext()) {
			String kind = (String) it.next();
			combo.add(kind);
		}
	}

}
