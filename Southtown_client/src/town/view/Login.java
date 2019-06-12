package town.view;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;

import town.main.Man_client;
import town.model.Stuff;
import town.socket.Client;
import town.util.Msg;
import town.util.Transfer;

public class Login {
	
	public static Stuff stuff;

	protected Shell shell;
	private Text text;
	private Text text_1;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Login window = new Login();
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
		Image image = new Image(display, "resources/main.jpg");
		shell.setImage(image);
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
		shell.setSize(600, 400);
		shell.setLocation(360, 200);
		shell.setText("ÇëµÇÂ¼");
		
		Label label = new Label(shell, SWT.SEPARATOR | SWT.VERTICAL);
		label.setBounds(287, 41, 2, 274);
		
		Label label_1 = new Label(shell, SWT.NONE);
		label_1.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
		label_1.setBounds(330, 83, 70, 30);
		label_1.setText("\u5E10\u53F7:");
		
		Label label_2 = new Label(shell, SWT.NONE);
		label_2.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
		label_2.setBounds(330, 140, 70, 30);
		label_2.setText("\u5BC6\u7801:");
		
		text = new Text(shell, SWT.BORDER);
		text.setBounds(400, 83, 150, 27);
		
		text_1 = new Text(shell, SWT.BORDER | SWT.PASSWORD);
		text_1.setBounds(400, 140, 150, 27);
		
		Button button = new Button(shell, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//login
				String name = text.getText();
				String password = text_1.getText();
				Transfer trans = new Transfer();
				Object[] params = new Object[2];
				params[0] = name;
				params[1] = password;
				trans.setMessage(Msg.USER_LOGIN_IN);
				trans.setParams(params);
				stuff = (Stuff) new Client(trans).getObject();
				
				if(stuff == null) {
					MessageBox mb = new MessageBox(shell, SWT.ICON_ERROR | SWT.YES);
					mb.setText("ÖØÐÂµÇÂ½");
					mb.setMessage("ÕÊºÅ»òÃÜÂë´íÎó£¬ÇëÖØÐÂµÇÂ¼£¡");
					mb.open();
				} else {
					Man_client.flag = 1;
					shell.dispose();
				}
			}
		});
		button.setBounds(330, 212, 91, 45);
		button.setText("\u767B\u5F55");
		
		Button button_1 = new Button(shell, SWT.NONE);
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//cancel
				shell.dispose();
			}
		});
		button_1.setBounds(459, 212, 91, 45);
		button_1.setText("\u53D6\u6D88");
		
		Label label_3 = new Label(shell, SWT.CENTER);
		label_3.setFont(SWTResourceManager.getFont("Ubuntu", 30, SWT.NORMAL));
		label_3.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_NORMAL_SHADOW));
		label_3.setBounds(57, 63, 170, 50);
		label_3.setText("\u6C5F\u5357\u53E4\u9547");
		
		Label label_4 = new Label(shell, SWT.NONE);
		label_4.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
		label_4.setBounds(80, 150, 100, 27);
		label_4.setText("\u6C5F\u5357\u98CE\u60C5");
		
		Label label_5 = new Label(shell, SWT.NONE);
		label_5.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
		label_5.setBounds(80, 190, 100, 27);
		label_5.setText("\u53E4\u9547\u97F5\u5473");
		
		Label label_6 = new Label(shell, SWT.NONE);
		label_6.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
		label_6.setText("\u4E3A\u4F0A\uFF0C\u6D88\u5F97\u4EBA\u6194\u60B4");
		label_6.setBounds(80, 230, 160, 27);

	}
}
