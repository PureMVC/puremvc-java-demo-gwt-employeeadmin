/*
 PureMVC Java MultiCore Demo - GWT Employee Admin by Anthony Quinault <anthony.quinault@puremvc.org>
 Based upon PureMVC AS3 Demo - Flex Employee Admin - Copyright(c) 2007-08 Cliff Hall <clifford.hall@puremvc.org>
 Your reuse is governed by the Creative Commons Attribution 3.0 License
 */

package org.puremvc.java.multicore.demos.gwt.employeeadmin.model.enumerator;

import java.util.ArrayList;
import java.util.List;

/**
 * RoleEnum.
 */
public class RoleEnum {

	/**
	 * Roles enumeration.
	 */
	public static final RoleEnum NONE_SELECTED = new RoleEnum(
			"--None Selected--", -1);

	public static final RoleEnum ADMIN = new RoleEnum("Administrator", 0);

	public static final RoleEnum ACCT_PAY = new RoleEnum("Accounts Payable", 1);

	public static final RoleEnum ACCT_RCV = new RoleEnum("Accounts Receivable",
			2);

	public static final RoleEnum EMP_BENEFITS = new RoleEnum(
			"Employee Benefits", 3);

	public static final RoleEnum GEN_LEDGER = new RoleEnum("General Ledger", 4);

	public static final RoleEnum PAYROLL = new RoleEnum("Payroll", 5);

	public static final RoleEnum INVENTORY = new RoleEnum("Inventory", 6);

	public static final RoleEnum PRODUCTION = new RoleEnum("Production", 7);

	public static final RoleEnum QUALITY_CTL = new RoleEnum("Quality Control",
			8);

	public static final RoleEnum SALES = new RoleEnum("Sales", 9);

	public static final RoleEnum ORDERS = new RoleEnum("Orders", 10);

	public static final RoleEnum CUSTOMERS = new RoleEnum("Customers", 11);

	public static final RoleEnum SHIPPING = new RoleEnum("Shipping", 12);

	public static final RoleEnum RETURNS = new RoleEnum("Returns", 13);

	public int ordinal;

	public String value;

	/**
	 * Constructor.
	 */
	protected RoleEnum(String value, int ordinal) {
		this.value = value;
		this.ordinal = ordinal;
	}

	/**
	 * The list of Role enumeration.
	 * @return the instance of the List
	 */
	public static List<RoleEnum> list() {
		ArrayList<RoleEnum> arraylist = new ArrayList<RoleEnum>();
		arraylist.add(ADMIN);
		arraylist.add(ACCT_PAY);
		arraylist.add(ACCT_RCV);
		arraylist.add(EMP_BENEFITS);
		arraylist.add(GEN_LEDGER);
		arraylist.add(PAYROLL);
		arraylist.add(INVENTORY);
		arraylist.add(PRODUCTION);
		arraylist.add(QUALITY_CTL);
		arraylist.add(SALES);
		arraylist.add(ORDERS);
		arraylist.add(CUSTOMERS);
		arraylist.add(SHIPPING);
		arraylist.add(RETURNS);
		return arraylist;
	}

	/**
	 * The comboList of Role NONE_SELECTED.
	 * @return the instance of the List
	 */
	public static List<RoleEnum> comboList() {
		List<RoleEnum> list = list();
		list.add(0, NONE_SELECTED);
		return list;
	}

	/**
	 * Check if the roleEnum is equal.
	 * @return whether it is currently equal
	 */
	public boolean equals(RoleEnum roleEnum) {
		return (this.ordinal == roleEnum.ordinal && this.value == roleEnum.value);
	}

}