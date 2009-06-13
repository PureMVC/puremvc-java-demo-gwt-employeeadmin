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

/**
 * initialization of the view.
 * Register mediator and proxy
 */
public class StartupCommand extends SimpleCommand {
	
	/**
	 * Register mediator and proxy.
	 * @param notification notification
	 */
	public void execute(final INotification notification) {
		this.getFacade().registerProxy(new UserProxy());
		this.getFacade().registerProxy(new RoleProxy());

		this.getFacade().registerMediator(new UserListMediator());
		this.getFacade().registerMediator(new RolePanelMediator());
		this.getFacade().registerMediator(new UserFormMediator());
		
		// Initialization of the users list
		this.getFacade().sendNotification(ApplicationFacade.INIT_USERS);  
	}
}
