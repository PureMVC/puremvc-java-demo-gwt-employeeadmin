/*
 PureMVC Java MultiCore Demo - GWT Employee Admin by Anthony Quinault <anthony.quinault@puremvc.org>
 Based upon PureMVC AS3 Demo - Flex Employee Admin - Copyright(c) 2007-08 Cliff Hall <clifford.hall@puremvc.org>
 Your reuse is governed by the Creative Commons Attribution 3.0 License
 */

package org.puremvc.java.multicore.demos.gwt.employeeadmin.controller;

import org.puremvc.java.multicore.demos.gwt.employeeadmin.ApplicationFacade;
import org.puremvc.java.multicore.demos.gwt.employeeadmin.model.RoleProxy;
import org.puremvc.java.multicore.demos.gwt.employeeadmin.model.UserProxy;
import org.puremvc.java.multicore.demos.gwt.employeeadmin.model.vo.UserVO;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;
	
public class DeleteUserCommand extends SimpleCommand {

	/**
	 * Retrieve the user and role proxies and delete the user
	 * and his roles. then send the USER_DELETED notification
	 * @param notification notification
	 */
	public void execute(final INotification notification) {
		UserVO user = (UserVO)notification.getBody();
		UserProxy userProxy = (UserProxy)this.getFacade().retrieveProxy(UserProxy.NAME);
		RoleProxy roleProxy = (RoleProxy)this.getFacade().retrieveProxy(RoleProxy.NAME);
		userProxy.deleteItem(user);
		roleProxy.deleteItem(user);
		sendNotification(ApplicationFacade.USER_DELETED);
	}
}