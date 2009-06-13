/*
 PureMVC Java MultiCore Demo - GWT Employee Admin by Anthony Quinault <anthony.quinault@puremvc.org>
 Based upon PureMVC AS3 Demo - Flex Employee Admin - Copyright(c) 2007-08 Cliff Hall <clifford.hall@puremvc.org>
 Your reuse is governed by the Creative Commons Attribution 3.0 License
 */

package org.puremvc.java.multicore.demos.gwt.employeeadmin.model.vo;

import org.puremvc.java.multicore.demos.gwt.employeeadmin.model.enumerator.DeptEnum;

/**
 * UserVO.
 */
public class UserVO {

	/**
	 * Username.
	 */
	public String username = new String();

	/**
	 * First name.
	 */
	public String fname = new String();

	/**
	 * Last name.
	 */
	public String lname = new String();

	/**
	 * Email.
	 */
	public String email = new String();

	/**
	 * Password.
	 */
	public String password = new String();

	/**
	 * Department.
	 */
	public DeptEnum department = DeptEnum.NONE_SELECTED;

	/**
	 * Constructor.
	 */
	public UserVO() {
	}
	
	/**
	 * Constructor.
	 * @param uname the user name
	 * @param fname the first name
	 * @param lname the last name
	 * @param email the email
	 * @param password the password
	 * @param department the department
	 */
	public UserVO(String uname, String fname, String lname, String email,
			String password, DeptEnum department) {
		if (uname != null)
			this.username = uname;
		if (fname != null)
			this.fname = fname;
		if (lname != null)
			this.lname = lname;
		if (email != null)
			this.email = email;
		if (password != null)
			this.password = password;
		if (department != null)
			this.department = department;
	}

	/**
	 * Check if is valid.
	 * @return whether it is valid
	 */
	public boolean isValid() {
		return username != "" && password != ""
				&& department != DeptEnum.NONE_SELECTED;
	}

	/**
	 * Concatenation of the last name with the first name.
	 * @return the given name
	 */
	public String givenName() {
		return this.lname + ", " + this.fname;
	}

}
