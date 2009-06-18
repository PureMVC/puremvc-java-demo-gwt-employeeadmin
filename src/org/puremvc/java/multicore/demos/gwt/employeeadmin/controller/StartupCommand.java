/*
 PureMVC Java MultiCore Demo - GWT Employee Admin by Anthony Quinault <anthony.quinault@puremvc.org>
 Based upon PureMVC AS3 Demo - Flex Employee Admin - Copyright(c) 2007-08 Cliff Hall <clifford.hall@puremvc.org>
 Your reuse is governed by the Creative Commons Attribution 3.0 License
 */

package org.puremvc.java.multicore.demos.gwt.employeeadmin.controller;

import org.puremvc.java.multicore.demos.gwt.employeeadmin.ApplicationFacade;
import org.puremvc.java.multicore.demos.gwt.employeeadmin.model.RoleProxy;
import org.puremvc.java.multicore.demos.gwt.employeeadmin.model.UserProxy;
import org.puremvc.java.multicore.demos.gwt.employeeadmin.view.RolePanelMediator;
import org.puremvc.java.multicore.demos.gwt.employeeadmin.view.UserFormMediator;
import org.puremvc.java.multicore.demos.gwt.employeeadmin.view.UserListMediator;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;

/**
 * initialization of the view.
 * Register mediator and proxy
 */
public class StartupCommand extends SimpleCommand {
	
	/**
	 * Register mediator and proxy.
	 * @param notification notification
	 */
	public final void execute(final INotification notification) {
		getFacade().registerProxy(new UserProxy());
		getFacade().registerProxy(new RoleProxy());

		getFacade().registerMediator(new UserListMediator());
		getFacade().registerMediator(new RolePanelMediator());
		getFacade().registerMediator(new UserFormMediator());

		Panel rootPanel = (Panel) notification.getBody();
		HorizontalPanel topPanel = new HorizontalPanel();
		HorizontalPanel bottomPanel = new HorizontalPanel();
		rootPanel.add(topPanel);
		rootPanel.add(bottomPanel);
		
		// Set the parent's layout
		((UserListMediator) getFacade().retrieveMediator(UserListMediator.NAME)).setParentPanel(topPanel);
		((UserFormMediator) getFacade().retrieveMediator(UserFormMediator.NAME)).setParentPanel(bottomPanel);
		((RolePanelMediator) getFacade().retrieveMediator(RolePanelMediator.NAME)).setParentPanel(bottomPanel);
		
		// Initialization of the users list
		getFacade().sendNotification(ApplicationFacade.INIT_USERS);  
	}
}
