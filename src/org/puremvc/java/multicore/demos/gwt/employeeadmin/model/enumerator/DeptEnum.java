/*
 PureMVC Java MultiCore Demo - GWT Employee Admin by Anthony Quinault <anthony.quinault@puremvc.org>
 Based upon PureMVC AS3 Demo - Flex Employee Admin - Copyright(c) 2007-08 Cliff Hall <clifford.hall@puremvc.org>
 Your reuse is governed by the Creative Commons Attribution 3.0 License
 */

package org.puremvc.java.multicore.demos.gwt.employeeadmin.model.enumerator;

import java.util.ArrayList;
import java.util.List;

/**
 * DeptEnum.
 */
public class DeptEnum {

	/**
	 * Department enumeration.
	 */
	public static final DeptEnum NONE_SELECTED = new DeptEnum(
			"--None Selected--", -1);

	public static final DeptEnum ACCT = new DeptEnum("Accounting", 0);

	public static final DeptEnum SALES = new DeptEnum("Sales", 1);

	public static final DeptEnum PLANT = new DeptEnum("Plant", 2);

	public static final DeptEnum SHIPPING = new DeptEnum("Shipping", 3);

	public static final DeptEnum QC = new DeptEnum("Quality Control", 4);

	public int ordinal;

	public String value;

	public List<String> roles = new ArrayList<String>();

	/**
	 * Constructor.
	 * @param value the value
	 * @param ordinal the ordinal
	 */
	protected DeptEnum(final String value, final int ordinal) {
		this.value = value;
		this.ordinal = ordinal;
	}
	
	/**
	 * The list of Department enumeration.
	 * @return the instance of the List
	 */
	public static ArrayList<DeptEnum> list() {
		ArrayList<DeptEnum> arraylist = new ArrayList<DeptEnum>();
		arraylist.add(NONE_SELECTED);
		arraylist.add(ACCT);
		arraylist.add(SALES);
		arraylist.add(PLANT);
		return arraylist;
	}

	/**
	 * The comboList of Department NONE_SELECTED.
	 * @return the instance of the List
	 */
	public static List<DeptEnum> comboList() {
		ArrayList<DeptEnum> arraylist = list();
		arraylist.add(0, NONE_SELECTED);
		return arraylist;
	}

	/**
	 * Check if the deptEnum is equal.
	 * @param deptEnum the depltEnum
	 * @return whether it is currently equal
	 */
	public final boolean equals(final DeptEnum deptEnum) {
		return (this.ordinal == deptEnum.ordinal && this.value.equals(deptEnum.value));
	}
}
