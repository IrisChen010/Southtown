package town.view;

import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import town.model.Menu;
import town.socket.Client;
import town.util.Msg;
import town.util.Transfer;

public class GoodsManage {

	protected Shell shell;
	private Table table;
	private Display display = Display.getDefault();
	private GoodsItemManage sGoodsItemManage;
	public static String currentMenuItemNumber = null;
	private Combo combo;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			GoodsManage window = new GoodsManage();
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
		shell = new Shell(SWT.CLOSE | SWT.MIN);
		shell.setSize(800, 600);
		shell.setText("≤À∆∑π‹¿Ì");
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setBounds(0, 0, 800, 562);
		
		Group group = new Group(composite, SWT.NONE);
		group.setBounds(0, -11, 800, 84);
		
		Button button = new Button(group, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//modify
				if(table.getSelectionIndex() != -1) {
					currentMenuItemNumber = table.getItem(table.getSelectionIndex()).getText(1);
					sGoodsItemManage.flag = Msg.WINDOW_GOODS_ITEM_MODIFY;
					sGoodsItemManage = new GoodsItemManage(shell, SWT.MIN | SWT.APPLICATION_MODAL);
					sGoodsItemManage.open();
					showMenusByKind();
					String kind = combo.getText();
					initCombo();
					combo.setText(kind);
				}
			}
		});
		button.setBounds(22, 20, 80, 54);
		button.setText("\u4FEE\u6539");
		
		Button button_1 = new Button(group, SWT.NONE);
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//add
				sGoodsItemManage.flag = Msg.WINDOW_GOODS_ITEM_ADD;
				sGoodsItemManage = new GoodsItemManage(shell, SWT.MIN | SWT.APPLICATION_MODAL);
				sGoodsItemManage.open();
				showMenusByKind();
				String kind = combo.getText();
				initCombo();
				combo.setText(kind);
			}
		});
		button_1.setBounds(134, 20, 80, 54);
		button_1.setText("\u6DFB\u52A0");
		
		Button button_2 = new Button(group, SWT.NONE);
		button_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//delete
				if(table.getSelectionIndex() != -1) {
					String number = table.getItem(table.getSelectionIndex()).getText(1);
					Transfer trans = new Transfer();
					trans.setMessage(Msg.REMOVE_MENU_ITEM);
					trans.setObj(number);
					String str = (String) new Client(trans).getObject();
					System.out.println(str);
					showMenusByKind();
				}
			}
		});
		button_2.setBounds(247, 20, 80, 54);
		button_2.setText("\u5220\u9664");
		
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
