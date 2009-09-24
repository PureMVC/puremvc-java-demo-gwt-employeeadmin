/*
 PureMVC Java MultiCore Demo - GWT Employee Admin by Anthony Quinault <anthony.quinault@puremvc.org>
 Based upon PureMVC AS3 Demo - Flex Employee Admin - Copyright(c) 2007-08 Cliff Hall <clifford.hall@puremvc.org>
 Your reuse is governed by the Creative Commons Attribution 3.0 License
 */

package org.puremvc.java.multicore.demos.gwt.employeeadmin.view;

import org.puremvc.java.multicore.demos.gwt.employeeadmin.ApplicationFacade;
import org.puremvc.java.multicore.demos.gwt.employeeadmin.model.UserProxy;
import org.puremvc.java.multicore.demos.gwt.employeeadmin.model.enumerator.DeptEnum;
import org.puremvc.java.multicore.demos.gwt.employeeadmin.model.vo.UserVO;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.mediator.Mediator;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

/**
 * User form mediator.
 */
public class UserFormMediator extends Mediator {
	/**
	 * Mediator name.
	 */
	public static final String NAME = "UserFormMediator";

	/**
	 * Mode.
	 */
	public static final int MODE_ADD = 0;
	public static final int MODE_EDIT = 1;
	private int mode = MODE_ADD;
	
	/**
	 * Reference to the user proxy.
	 */
	private UserProxy userProxy = null;

	/**
	 * Reference to the user vo.
	 */
	private UserVO user;
	
	/**
	 * Widgets.
	 */
	private Button addUser = new Button("Add User");
	private Button update = new Button("Update User");
	private Button cancel = new Button("Cancel");
	private Label lfname = new Label("First Name");
	private TextBox tbfname = new TextBox();
	private Label llname = new Label("Last Name");
	private TextBox tblname = new TextBox();
	private Label lemail = new Label("Email");
	private TextBox tbemail = new TextBox();
	private Label lusername = new Label("Username *");
	private TextBox tbusername = new TextBox();
	private Label lpassword = new Label("Password *");
	private PasswordTextBox tbpassword = new PasswordTextBox();
	private Label lconfirmpassword = new Label("Confirm Password *");
	private PasswordTextBox tbconfirmpassword = new PasswordTextBox();
	private Label ldepartment = new Label("Department *");
	private ListBox lbdepartment = new ListBox();
	private FlexTable ft = new FlexTable();

	/**
	 * Panels.
	 */
	private AbsolutePanel aPanel = new AbsolutePanel();
	private CaptionPanel cpanel = new CaptionPanel("User Profile");

	private static final int SPACING = 3;

	/**
	 * Constructor.
	 */
	public UserFormMediator() {
		super(NAME, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void onRegister() {
		userProxy = (UserProxy) getFacade().retrieveProxy(UserProxy.NAME);
		initView();
		super.onRegister();
	}

	/**
	 * Initialize the user form view.
	 */
	private void initView() {
		aPanel.setStyleName("pmvc-absolutePanelUserProfile");
		cpanel.setStyleName("pmvc-absoluteCaptionPanelUserProfile");
		cpanel.add(ft);
		aPanel.add(cpanel);
		
		ft.setWidget(0, 0, lfname);
		ft.setWidget(0, 1, tbfname);
		ft.setWidget(1, 0, llname);
		ft.setWidget(1, 1, tblname);
		ft.setWidget(2, 0, lemail);
		ft.setWidget(2, 1, tbemail);
		ft.setWidget(3, 0, lusername);
		ft.setWidget(3, 1, tbusername);
		ft.setWidget(4, 0, lpassword);
		ft.setWidget(4, 1, tbpassword);
		ft.setWidget(5, 0, lconfirmpassword);
		ft.setWidget(5, 1, tbconfirmpassword);
		ft.setWidget(6, 0, ldepartment);
		ft.setWidget(6, 1, lbdepartment);
		ft.setWidget(7, 0, update);
		ft.setWidget(7, 1, cancel);
		
		ft.setCellSpacing(SPACING);

		setEnabledForm(false);

		// Add click listener
		addUser.addClickHandler(new ClickHandler() {
			public void onClick(final ClickEvent event) {
				onAdd();
			}
		});
		
		update.addClickHandler(new ClickHandler() {
			public void onClick(final ClickEvent event) {
				onUpdate();
			}
		});

		cancel.addClickHandler(new ClickHandler() {
			public void onClick(final ClickEvent event) {
				onCancel();
			}
		});
		
		tbusername.addKeyUpHandler(new KeyUpHandler() {
			public void onKeyUp(final KeyUpEvent event) {
				updateForm();
			}
		});
		
		tbpassword.addKeyUpHandler(new KeyUpHandler() {
			public void onKeyUp(final KeyUpEvent event) {
				updateForm();
			}
		});
		
		tbconfirmpassword.addKeyUpHandler(new KeyUpHandler() {
			public void onKeyUp(final KeyUpEvent event) {
				updateForm();
			}
		});
		
		lbdepartment.addClickHandler(new ClickHandler() {
			public void onClick(final ClickEvent event) {
				updateForm();
			}
		});

		lbdepartment.addKeyUpHandler(new KeyUpHandler() {
			public void onKeyUp(final KeyUpEvent event) {
				updateForm();
			}
		});
		
		setViewComponent(aPanel);
		RootPanel.get("userFormContainer").add(aPanel);
	}

	/**
	 * Add a user and send a notification.
	 */
	private void onAdd() {
		populateUserVO();
		userProxy.addItem(user);
		sendNotification(ApplicationFacade.USER_ADDED, user);
		clearForm();
		setEnabledForm(false);
	}

	/**
	 * Update the user and send a notification.
	 */
	private void onUpdate()	{
		populateUserVO();
		userProxy.updateItem(user);
		sendNotification(ApplicationFacade.USER_UPDATED, user);
		clearForm();
		setEnabledForm(false);
	}
	
	/**
	 * Cancel the action.
	 */
	private void onCancel()	{
		sendNotification(ApplicationFacade.CANCEL_SELECTED);			
		clearForm();
		setEnabledForm(false);
	}

	/**
	 * Populate the user vo from the form.
	 */
	private void populateUserVO() {
		String value = lbdepartment.getItemText(lbdepartment.getSelectedIndex());
		for (int i = 0; i < DeptEnum.list().size(); i++) {
			if (value.equals(DeptEnum.list().get(i).value)) {
				user.department = DeptEnum.list().get(i);
				break;
			}
		}
		
		user.fname = tbfname.getText();
		user.lname = tblname.getText(); 
		user.email = tbemail.getText(); 
		user.username = tbusername.getText();
		user.password = tbpassword.getText();
	}
		
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final String[] listNotificationInterests() {
		return new String[] { ApplicationFacade.NEW_USER, 
				ApplicationFacade.USER_DELETED,
				ApplicationFacade.USER_SELECTED
				};	
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void handleNotification(final INotification notification) {
		if (notification.getName().equals(ApplicationFacade.NEW_USER)) {
			user = (UserVO) notification.getBody();
			clearForm();
			tbfname.setFocus(true);
			setEnabledForm(true);
			setMode(MODE_ADD);
			updateForm();
		} else if (notification.getName().equals(ApplicationFacade.USER_DELETED)) {
			user = null;
			clearForm();
			setEnabledForm(false);
			tbusername.setEnabled(false);
		} else if (notification.getName().equals(ApplicationFacade.USER_SELECTED)) {
			user = (UserVO) notification.getBody();
			clearForm();
			initForm();
			setEnabledForm(true);
			setMode(MODE_EDIT);
			updateForm();
		}
		super.handleNotification(notification);
	}

	/**
	 * Set mode.
	 * @param pmode the mode
	 */
	private void setMode(final int pmode) {
		mode = pmode;
	}

	/**
	 * Sets whether this form is enabled.
	 * @param arg the argument
	 */
	private void setEnabledForm(final boolean arg) {
		tbfname.setEnabled(arg);
		tblname.setEnabled(arg); 
		tbemail.setEnabled(arg); 
		tbusername.setEnabled(arg);
		tbpassword.setEnabled(arg);
		tbconfirmpassword.setEnabled(arg);
		lbdepartment.setEnabled(arg);
		addUser.setEnabled(arg);
		update.setEnabled(arg);
		cancel.setEnabled(arg);
	}
	
	/**
	 * Clear this form.
	 */
	private void clearForm() {
		tbfname.setText("");
		tblname.setText(""); 
		tbemail.setText(""); 
		tbusername.setText("");
		tbpassword.setText("");
		tbconfirmpassword.setText("");
		lbdepartment.clear();
		int indexSelected = 0;
		for (int i = 0; i < DeptEnum.list().size(); i++) {
			lbdepartment.addItem(DeptEnum.list().get(i).value);
		}
		lbdepartment.setSelectedIndex(indexSelected);
	}

	/**
	 * Initialize this form.
	 */
	private void initForm() {
		tbfname.setText(user.fname);
		tblname.setText(user.lname); 
		tbemail.setText(user.email); 
		tbusername.setText(user.username);
		tbpassword.setText(user.password);
		tbconfirmpassword.setText(user.password);
		for (int i = 0; i < lbdepartment.getItemCount(); i++) {
			if (user.department.value.equals(lbdepartment.getItemText(i))) {
				lbdepartment.setSelectedIndex(i);
				break;
			}
		}
	}
	
	/**
	 * Update this form.
	 */
	private void updateForm() {
		if (mode == MODE_ADD) {
			tbusername.setEnabled(true);
			addUser.setEnabled(false);
			update.setEnabled(false);
			ft.setWidget(7, 0, addUser);
			
			if ((tbusername.getText().length() > 0)
				&& (tbpassword.getText().length() > 0)
				&& (tbpassword.getText().equals(tbconfirmpassword.getText()))
				&& (lbdepartment.getSelectedIndex() > 0)) {
					addUser.setEnabled(true);
					update.setEnabled(true);
			}
		} else if (mode == MODE_EDIT) {
			tbusername.setEnabled(false);
			addUser.setEnabled(false);
			update.setEnabled(false);
			ft.setWidget(7, 0, update);
			
			if ((tbusername.getText().length() > 0)
				&& (tbpassword.getText().length() > 0)
				&& (tbpassword.getText().equals(tbconfirmpassword.getText()))
				&& (lbdepartment.getSelectedIndex() > 0)) {
					addUser.setEnabled(true);
					update.setEnabled(true);
			}
		}
	}
}