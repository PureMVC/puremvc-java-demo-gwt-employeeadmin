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

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Role panel mediator.
 */
public class RolePanelMediator extends Mediator {

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
	 * Reference to the current user rsoles.
	 */
	private List<RoleEnum> userRoles;
		
	/**
	 * The panels.
	 */
	private AbsolutePanel aPanel = new AbsolutePanel();			
	private CaptionPanel cpanel = new CaptionPanel("User Roles");
	private VerticalPanel vpanel = new VerticalPanel();
	private HorizontalPanel hpanel = new HorizontalPanel();

	/**
	 * The widgets.
	 */
	private ListBox lb = new ListBox();
	private Button bAdd = new Button("Add");
	private Button bRemove = new Button("Remove");
	private ListBox lbRoleCombo = new ListBox();

	private static final int SPACING = 3;
	private static final int VISIBLE_ITEM = 5;

	/**
	 * Mediator name.
	 */
	public static final String NAME = "RolePanelMediator";

	/**
	 * Constructor.
	 */
	public RolePanelMediator()	{
		super(NAME, null);
		initView();
	}
	
	/**
	 * Set the parent panel view.
	 * @param panel the parent panel
	 */
	public final void setParentPanel(final Panel panel) {
		panel.add(aPanel);
	}

	/**
	 * Initialize the role panel view.
	 */
	private void initView() {
		// Init the panels and widgets
		aPanel.setStyleName("pmvc-absolutePanelUserRoles");
		lb.setStyleName("pmvc-listBox1UserRoles");
		cpanel.setStyleName("pmvc-absoluteCaptionPanelUserRoles");
		lb.setVisibleItemCount(VISIBLE_ITEM);
		vpanel.add(lb);
		cpanel.add(vpanel);
		hpanel.add(lbRoleCombo);
		hpanel.add(bAdd);
		hpanel.add(bRemove);
		vpanel.add(hpanel);
		hpanel.setSpacing(SPACING);
		aPanel.add(cpanel);

		clearForm();

		// Fill the combo
		List<RoleEnum> comboList = RoleEnum.comboList();
		for (int i = 0; i < comboList.size(); i++) {
			lbRoleCombo.addItem(comboList.get(i).value);	
		}

		// Add click listener
		bAdd.addClickHandler(new ClickHandler() {
			public void onClick(final ClickEvent event) {
				onAddRole();
				clearForm();
				initForm();
				}
		});

		// Add click listener
		bRemove.addClickHandler(new ClickHandler() {
			public void onClick(final ClickEvent event) {
				onRemoveRole();
				clearForm();
				initForm();
			}
		});		
		
		// Add click listener
		lb.addClickHandler(new ClickHandler() {
			public void onClick(final ClickEvent event) {
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
		});
		
		// Add click listener
		lbRoleCombo.addClickHandler(new ClickHandler() {
			public void onClick(final ClickEvent event) {
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
		});
		
		setViewComponent(aPanel);
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
				ApplicationFacade.ADD_ROLE_RESULT,
				};	
	}

	/**
	 * {@inheritDoc}
	 */	
	@Override
	public final void handleNotification(final INotification notification) {
		roleProxy = (RoleProxy) getFacade().retrieveProxy(RoleProxy.NAME);
		
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