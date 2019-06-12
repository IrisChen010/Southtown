package town.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import town.model.Menu;
import town.socket.Client;
import town.util.Msg;
import town.util.Transfer;

public class GoodsItemManage extends Dialog {

	protected Object result;
	protected Shell shell;
	private Text text;
	private Text text_1;
	private Text text_2;
	private Text text_3;
	public static int flag = -1;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public GoodsItemManage(Shell parent, int style) {
		super(parent, style);
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
		label.setBounds(49, 30, 35, 17);
		label.setText("\u7F16  \u53F7");
		
		text = new Text(shell, SWT.BORDER);
		text.setBounds(103, 23, 93, 31);
		
		Label label_1 = new Label(shell, SWT.NONE);
		label_1.setBounds(49, 96, 35, 17);
		label_1.setText("\u540D  \u79F0");
		
		text_1 = new Text(shell, SWT.BORDER);
		text_1.setBounds(103, 89, 93, 31);
		
		Label label_2 = new Label(shell, SWT.NONE);
		label_2.setBounds(49, 162, 35, 17);
		label_2.setText("\u5355  \u4EF7");
		
		text_2 = new Text(shell, SWT.BORDER);
		text_2.setBounds(103, 155, 93, 31);
		
		Label label_3 = new Label(shell, SWT.NONE);
		label_3.setBounds(49, 228, 35, 17);
		label_3.setText("\u7C7B  \u522B");
		
		text_3 = new Text(shell, SWT.BORDER);
		text_3.setBounds(103, 221, 93, 31);
		
		Button button = new Button(shell, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//ok
				String number = text.getText();
				String name = text_1.getText();
				int price = Integer.parseInt(text_2.getText());
				String kind = text_3.getText();
				
				Menu menu = new Menu();
				menu.setNumber(number);
				menu.setName(name);
				menu.setPrice(price);
				menu.setKind(kind);
				
				Transfer trans = new Transfer();
				
				if(flag == Msg.WINDOW_GOODS_ITEM_MODIFY) {
					String num = GoodsManage.currentMenuItemNumber;
					Object[] params = new Object[1];
					params[0] = num;
					trans.setMessage(Msg.MODIFY_MENU_ITEM);
					trans.setObj(menu);
					trans.setParams(params);
				} else if(flag == Msg.WINDOW_GOODS_ITEM_ADD) {
					trans.setMessage(Msg.SAVE_MENU_ITEM);
					trans.setObj(menu);
				}
				
				String str = (String) new Client(trans).getObject();
				System.out.println(str);
				shell.dispose();
			}
		});
		button.setBounds(300, 46, 102, 48);
		button.setText("\u786E\u5B9A");
		
		Button button_1 = new Button(shell, SWT.NONE);
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//cancel
				shell.dispose();
			}
		});
		button_1.setBounds(300, 162, 102, 48);
		button_1.setText("\u53D6\u6D88");
		
		initWindow();
	}
	
	private void initWindow() {
		if(flag == Msg.WINDOW_GOODS_ITEM_ADD) {
			setText("增加菜单条目");
		} else if(flag == Msg.WINDOW_GOODS_ITEM_MODIFY) {
			setText("修改菜单条目");
			String number = GoodsManage.currentMenuItemNumber;
			Transfer trans = new Transfer();
			trans.setMessage(Msg.GET_MENU_BY_NUMBER);
			trans.setObj(number);
			Menu menu = (Menu) new Client(trans).getObject();
			
			text.setText(menu.getNumber());
			text_1.setText(menu.getName());
			text_2.setText(Integer.toString(menu.getPrice()));
			text_3.setText(menu.getKind());
		}
	}

}
