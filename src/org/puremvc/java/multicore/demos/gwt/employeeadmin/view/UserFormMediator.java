package org.puremvc.java.multicore.demos.gwt.employeeadmin.view;

import org.puremvc.java.multicore.demos.gwt.employeeadmin.ApplicationFacade;
import org.puremvc.java.multicore.demos.gwt.employeeadmin.model.UserProxy;
import org.puremvc.java.multicore.demos.gwt.employeeadmin.model.enumerator.DeptEnum;
import org.puremvc.java.multicore.demos.gwt.employeeadmin.model.vo.UserVO;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.mediator.Mediator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * Message mediator.
 */
public class UserFormMediator extends Mediator {

	/**
	 * The declarative UI.
	 */
	@UiTemplate("UserForm.ui.xml")
	interface MyUiBinder extends UiBinder<Widget, UserFormMediator> {
	}

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

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
	@UiField CaptionPanel cpanel;
	@UiField TextBox tbfname;
	@UiField TextBox tblname;
	@UiField TextBox tbemail;
	@UiField TextBox tbusername;
	@UiField PasswordTextBox tbpassword;
	@UiField PasswordTextBox tbconfirmpassword;
	@UiField ListBox lbdepartment;
	@UiField Button update;
	@UiField Button add;
	@UiField Button cancel;
	
	/**
	 * The handlers.
	 */
	@UiHandler({"tbfname","tblname", "tbemail", "tbusername", "tbpassword", "tbconfirmpassword"})
	void doChangeValue(ValueChangeEvent<String> event) {
		updateForm();
	}

	@UiHandler({"lbdepartment", "add", "update", "cancel"})
	void doClick(ClickEvent event) {
		if(event.getSource().equals(add))
			onAdd();
		if(event.getSource().equals(update))
			onUpdate();
		if(event.getSource().equals(cancel))
			onCancel();
		else if (event.getSource().equals(lbdepartment))
			updateForm();
	}
		
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
	 * Initialize the message view.
	 */
	private void initView() {
		uiBinder.createAndBindUi(this);
		RootPanel.get("userFormContainer").add(cpanel);
		setEnabledForm(false);
	}

	/* (non-Javadoc)
	 * @see org.puremvc.java.multicore.patterns.mediator.Mediator#onRemove()
	 */
	@Override
	public void onRemove() {
		super.onRemove();
		RootPanel.get().remove(cpanel);
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
		add.setEnabled(arg);
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
			update.setEnabled(false);
			update.setVisible(false);
			add.setEnabled(false);
			add.setVisible(true);
			
			if ((tbusername.getText().length() > 0)
				&& (tbpassword.getText().length() > 0)
				&& (tbpassword.getText().equals(tbconfirmpassword.getText()))
				&& (lbdepartment.getSelectedIndex() > 0)) {
					add.setEnabled(true);
			}
		} else if (mode == MODE_EDIT) {
			tbusername.setEnabled(false);
			add.setEnabled(false);
			add.setVisible(false);
			update.setEnabled(false);
			update.setVisible(true);
			
			if ((tbusername.getText().length() > 0)
				&& (tbpassword.getText().length() > 0)
				&& (tbpassword.getText().equals(tbconfirmpassword.getText()))
				&& (lbdepartment.getSelectedIndex() > 0)) {
					add.setEnabled(true);
					update.setEnabled(true);
			}
		}
	}
}