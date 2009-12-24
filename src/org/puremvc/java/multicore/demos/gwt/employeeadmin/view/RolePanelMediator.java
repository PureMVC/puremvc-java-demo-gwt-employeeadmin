/*
 PureMVC Java MultiCore Demo - GWT Employee Admin by Anthony Quinault <anthony.quinault@puremvc.org>
 Based upon PureMVC AS3 Demo - Flex Employee Admin - Copyright(c) 2007-08 Cliff Hall <clifford.hall@puremvc.org>
 Your reuse is governed by the Creative Commons Attribution 3.0 License
 */

package org.puremvc.java.multicore.demos.gwt.employeeadmin.view;

import java.util.List;

import org.puremvc.java.multicore.demos.gwt.employeeadmin.ApplicationFacade;
import org.puremvc.java.multicore.demos.gwt.employeeadmin.model.RoleProxy;
import org.puremvc.java.multicore.demos.gwt.employeeadmin.model.enumerator.RoleEnum;
import org.puremvc.java.multicore.demos.gwt.employeeadmin.model.vo.RoleVO;
import org.puremvc.java.multicore.demos.gwt.employeeadmin.model.vo.UserVO;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.mediator.Mediator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Role panel mediator.
 */
public class RolePanelMediator extends Mediator {

	/**
	 * The declarative UI.
	 */
	@UiTemplate("RolePanel.ui.xml")
	interface MyUiBinder extends UiBinder<Widget, RolePanelMediator> {
	}

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	private static final int VISIBLE_ITEM = 10;

	/**
	 * Reference to the Controller.
	 */
	private RoleProxy roleProxy = null;		

	/**
	 * The selected role.
	 */
	private RoleEnum selectedRole = RoleEnum.NONE_SELECTED;
	
	/**
	 * Reference to the current user.
	 */
	private UserVO user;
	
	/**
	 * Reference to the current user roles.
	 */
	private List<RoleEnum> userRoles;
		
	/**
	 * The widgets.
	 */
	@UiField CaptionPanel cpanel;
	@UiField  ListBox lb = new ListBox();
	@UiField  Button bAdd = new Button("Add");
	@UiField  Button bRemove = new Button("Remove");
	@UiField  ListBox lbRoleCombo = new ListBox();

	/**
	 * The handler.
	 */
	@UiHandler({"lb", "bAdd", "bRemove", "lbRoleCombo"})
	void doClick(ClickEvent event) {
		if(event.getSource().equals(lb)) {
			if (lb.getSelectedIndex() >= 0) {
				List<RoleEnum> list = RoleEnum.comboList();
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).value.equals(lb.getItemText(lb.getSelectedIndex()))) {
						selectedRole = list.get(i);
						bAdd.setEnabled(false);
						bRemove.setEnabled(true);
						lbRoleCombo.setSelectedIndex(0);
						break;
					}
				}					 
			}
		}
		else if (event.getSource().equals(bAdd)) {
			onAddRole();
			clearForm();
			initForm();
		}
		else if (event.getSource().equals(bRemove)) {
			onRemoveRole();
			clearForm();
			initForm();
		}
		else if (event.getSource().equals(lbRoleCombo)) {
			List<RoleEnum> list = RoleEnum.comboList();
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).value.equals(lbRoleCombo.getItemText(lbRoleCombo.getSelectedIndex()))) {
					selectedRole = list.get(i);
					bAdd.setEnabled(true);
					bRemove.setEnabled(false);
					break;
				}
			}					 
		}
	}	
	
	/**
	 * Mediator name.
	 */
	public static final String NAME = "RolePanelMediator";

	/**
	 * Constructor.
	 */
	public RolePanelMediator() {
		super(NAME, null);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void onRegister() {
		roleProxy = (RoleProxy) getFacade().retrieveProxy(RoleProxy.NAME);
		initView();
		super.onRegister();
	}

	/**
	 * Initialize the role panel view.
	 */
	private void initView() {
		uiBinder.createAndBindUi(this);
		RootPanel.get("rolePanelContainer").add(cpanel);
		
		lb.setVisibleItemCount(VISIBLE_ITEM);
		
		// Fill the combo
		List<RoleEnum> comboList = RoleEnum.comboList();
		for (int i = 0; i < comboList.size(); i++) {
			lbRoleCombo.addItem(comboList.get(i).value);	
		}
	}
	
	/**
	 * Add the selected role to the user.
	 */
	private void onAddRole() {
		roleProxy.addRoleToUser(user, selectedRole);
	}
	
	/**
	 * Remove the selected role to the user.
	 */
	private void onRemoveRole() {
		roleProxy.removeRoleFromUser(user, selectedRole);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final String[] listNotificationInterests() {
		return new String[] { 					
				ApplicationFacade.NEW_USER,
				ApplicationFacade.USER_ADDED,
				ApplicationFacade.USER_UPDATED,
				ApplicationFacade.USER_DELETED,
				ApplicationFacade.CANCEL_SELECTED,
				ApplicationFacade.USER_SELECTED,
				ApplicationFacade.ADD_ROLE_RESULT
				};	
	}

	/**
	 * {@inheritDoc}
	 */	
	@Override
	public final void handleNotification(final INotification notification) {
		if (notification.getName().equals(ApplicationFacade.NEW_USER)) {
			clearForm();
		} else if (notification.getName().equals(ApplicationFacade.USER_ADDED)) {
			user = (UserVO) notification.getBody();
			RoleVO roleVO = new RoleVO(user.username, null);
			roleProxy.addItem(roleVO);
			clearForm();
			bAdd.setEnabled(false);
			bRemove.setEnabled(false);
			lb.setEnabled(false);
			lbRoleCombo.setEnabled(false);
		} else if (notification.getName().equals(ApplicationFacade.USER_UPDATED)) {
			clearForm();
		} else if (notification.getName().equals(ApplicationFacade.USER_DELETED)) {
			clearForm();
		} else if (notification.getName().equals(ApplicationFacade.CANCEL_SELECTED)) {
			clearForm();
		} else if (notification.getName().equals(ApplicationFacade.USER_SELECTED)) {
			user = (UserVO) notification.getBody();
			userRoles = roleProxy.getUserRoles(user.username);
			bAdd.setEnabled(false);
			bRemove.setEnabled(false);
			lb.setEnabled(true);
			lbRoleCombo.setEnabled(true);
			lbRoleCombo.setSelectedIndex(0);
			clearForm();
			initForm();
		} else if (notification.getName().equals(ApplicationFacade.ADD_ROLE_RESULT)) {
		}		
		super.handleNotification(notification);
	}

	/**
	 * Clear the user's roles list box.
	 */
	public final void clearForm() {
		lb.addItem("");
		lb.clear();
	}

	/**
	 * Initialize the user's roles list box.
	 */
	private void initForm() {
		for (int i = 0; i < userRoles.size(); i++) {
			lb.addItem(userRoles.get(i).value);
		}
	}
}