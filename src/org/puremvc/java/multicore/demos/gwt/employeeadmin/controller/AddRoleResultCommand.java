/*
 PureMVC Java MultiCore Demo - GWT Employee Admin by Anthony Quinault <anthony.quinault@puremvc.org>
 Based upon PureMVC AS3 Demo - Flex Employee Admin - Copyright(c) 2007-08 Cliff Hall <clifford.hall@puremvc.org>
 Your reuse is governed by the Creative Commons Attribution 3.0 License
 */

package org.puremvc.java.multicore.demos.gwt.employeeadmin.controller;

import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

import com.google.gwt.user.client.Window;
	
/**
 * Add role result command.
 */
public class AddRoleResultCommand extends SimpleCommand {

	/**
	 * Check if the the role already exists.
	 * and if it exists send an alert message 
	 * @param notification notification
	 */
	public final void execute(final INotification notification) {
		Boolean result = (Boolean) notification.getBody();
		if (result == Boolean.FALSE) {
			Window.alert("Role already exists for this user!");
		}
	}
}