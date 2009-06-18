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
	 * @param puname the user name
	 * @param pfname the first name
	 * @param plname the last name
	 * @param pemail the email
	 * @param ppassword the password
	 * @param pdepartment the department
	 */
	public UserVO(final String puname, final String pfname, final String plname, final String pemail,
			final String ppassword, final DeptEnum pdepartment) {
		if (puname != null) {
			this.username = puname;
		}
		if (pfname != null) {
			this.fname = pfname;
		}
		if (plname != null) {
			this.lname = plname;
		}
		if (pemail != null) {
			this.email = pemail;
		}
		if (ppassword != null) {
			this.password = ppassword;
		}
		if (pdepartment != null) {
			this.department = pdepartment;
		}
	}

	/**
	 * Check if is valid.
	 * @return whether it is valid
	 */
	public final boolean isValid() {
		return username != "" && password != ""
				&& department != DeptEnum.NONE_SELECTED;
	}

	/**
	 * Concatenation of the last name with the first name.
	 * @return the given name
	 */
	public final String givenName() {
		return this.lname + ", " + this.fname;
	}

}
