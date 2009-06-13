/*
 PureMVC Java MultiCore Demo - GWT Employee Admin by Anthony Quinault <anthony.quinault@puremvc.org>
 Based upon PureMVC AS3 Demo - Flex Employee Admin - Copyright(c) 2007-08 Cliff Hall <clifford.hall@puremvc.org>
 Your reuse is governed by the Creative Commons Attribution 3.0 License
 */

package org.puremvc.java.multicore.demos.gwt.employeeadmin.model;

import java.util.ArrayList;
import java.util.List;

import org.puremvc.java.multicore.demos.gwt.employeeadmin.ApplicationFacade;
import org.puremvc.java.multicore.demos.gwt.employeeadmin.model.enumerator.RoleEnum;
import org.puremvc.java.multicore.demos.gwt.employeeadmin.model.vo.RoleVO;
import org.puremvc.java.multicore.demos.gwt.employeeadmin.model.vo.UserVO;
import org.puremvc.java.multicore.patterns.proxy.Proxy;

/**
 * Role Proxy.
 */
public class RoleProxy extends Proxy {
	
	/**
	 * Proxy name.
	 */
	public static final String NAME = "RoleProxy";

	/**
	 * Constructor.
	 */
	public RoleProxy() {
		super(NAME, new ArrayList<RoleVO>());
		
		List<RoleEnum> rolesEnum1 = new ArrayList<RoleEnum>();
		rolesEnum1.add(RoleEnum.PAYROLL);
		rolesEnum1.add(RoleEnum.EMP_BENEFITS);
		addItem(new RoleVO("lstooge", rolesEnum1));
										       
		List<RoleEnum> rolesEnum2 = new ArrayList<RoleEnum>();
		rolesEnum2.add(RoleEnum.ACCT_PAY);
		rolesEnum2.add(RoleEnum.ACCT_RCV);
		rolesEnum2.add(RoleEnum.GEN_LEDGER);
		addItem(new RoleVO("cstooge", rolesEnum2));
										       
		List<RoleEnum> rolesEnum3 = new ArrayList<RoleEnum>();
		rolesEnum3.add(RoleEnum.INVENTORY);
		rolesEnum3.add(RoleEnum.PRODUCTION);
		rolesEnum3.add(RoleEnum.SALES);
		rolesEnum3.add(RoleEnum.SHIPPING);
		addItem(new RoleVO("mstooge", rolesEnum3));
	}
	
	/**
	 * The roles.
	 * @return data property cast to proper type
	 */
	@SuppressWarnings("unchecked")
	public List<RoleVO> roles() {
		return (List<RoleVO>) this.getData();
	}

	/**
	 * Add an item to the data.
	 * @param item the item
	 */
	public void addItem(RoleVO item) {
		roles().add(item);
	}
	
	/**
	 * Delete an item in the data.
	 * @param item the item
	 */
	public void deleteItem(UserVO item) {
		for (int i = 0; i < roles().size(); i++) {
			if (roles().get(i).username == item.username) {
				roles().remove(i);
			}
		}
	}

	/**
	 * Determine if the user has a given role.
	 * @param user the user
	 * @param role the role
	 */
	public boolean doesUserHaveRole(UserVO user, RoleEnum role) {
		boolean hasRole = false;
		for(int i=0; i<roles().size(); i++) { 
			if(roles().get(i).username.equals(user.username)) {
				List<RoleEnum> userRoles = roles().get(i).roles;
				for(int j=0; j<userRoles.size(); j++) {
					if(userRoles.get(j).equals(role)) {
						hasRole = true;
						break;
					}
				}
				break;
			}
		}
		return hasRole;
	}

	/**
	 * Add a role to this user.
	 * @param user the user
	 * @param role the role
	 */
	public void addRoleToUser(UserVO user, RoleEnum role) {
		boolean result = false;
		if(!doesUserHaveRole(user, role)) {
			for(int i=0; i<roles().size(); i++) { 
				if(roles().get(i).username.equals(user.username)) {
					List<RoleEnum> userRoles = roles().get(i).roles;
					userRoles.add(role);
					result = true;
					break;
				}
			}
		}
		sendNotification(ApplicationFacade.ADD_ROLE_RESULT, result);
	}

	/**
	 * Remove a role from the user.
	 * @param user the user
	 * @param role the role
	 */
	public void removeRoleFromUser(UserVO user, RoleEnum role) {
		if(doesUserHaveRole(user, role)) {
			for(int i=0; i<roles().size(); i++) {
				if(roles().get(i).username.equals(user.username)) {
					List<RoleEnum> userRoles = roles().get(i).roles;
					for(int j=0; j<userRoles.size(); j++) { 
						if(userRoles.get(j).equals(role)) {
							userRoles.remove(j);
							break;
						}
					}
					break;
				}
			}
		}
	}
		
	/**
	 * Get a user's roles.
	 * @param username the user name
	 */
	public List<RoleEnum> getUserRoles( String username ) {
		List<RoleEnum> userRoles = new ArrayList<RoleEnum>();
		for ( int i=0; i<roles().size(); i++) { 
			if ( roles().get(i).username.equals(username)) {
				List<RoleEnum> roles = roles().get(i).roles;
				userRoles = roles;
				break;
			}
		}	
		return userRoles;
	}
}