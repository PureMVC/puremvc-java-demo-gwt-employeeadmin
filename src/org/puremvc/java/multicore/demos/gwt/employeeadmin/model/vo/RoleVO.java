/*
 PureMVC Java MultiCore Demo - GWT Employee Admin by Anthony Quinault <anthony.quinault@puremvc.org>
 Based upon PureMVC AS3 Demo - Flex Employee Admin - Copyright(c) 2007-08 Cliff Hall <clifford.hall@puremvc.org>
 Your reuse is governed by the Creative Commons Attribution 3.0 License
 */

package org.puremvc.java.multicore.demos.gwt.employeeadmin.model.vo;

import java.util.ArrayList;
import java.util.List;

import org.puremvc.java.multicore.demos.gwt.employeeadmin.model.enumerator.RoleEnum;

/**
 * RoleVO.
 */
public class RoleVO {

	/**
	 * Username.
	 */
	public String username = new String();

	/**
	 * Roles enumeration.
	 */
	public List<RoleEnum> roles = new ArrayList<RoleEnum>();

	/**
	 * Constructor.
	 */
	public RoleVO(String username, List<RoleEnum> roles) {
		if (username != null)
			this.username = username;
		if (roles != null)
			this.roles = new ArrayList<RoleEnum>(roles);
	}

}
