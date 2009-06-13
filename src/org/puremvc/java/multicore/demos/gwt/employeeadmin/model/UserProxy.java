/*
 PureMVC Java MultiCore Demo - GWT Employee Admin by Anthony Quinault <anthony.quinault@puremvc.org>
 Based upon PureMVC AS3 Demo - Flex Employee Admin - Copyright(c) 2007-08 Cliff Hall <clifford.hall@puremvc.org>
 Your reuse is governed by the Creative Commons Attribution 3.0 License
 */

package org.puremvc.java.multicore.demos.gwt.employeeadmin.model;

import java.util.ArrayList;

import org.puremvc.java.multicore.demos.gwt.employeeadmin.model.enumerator.DeptEnum;
import org.puremvc.java.multicore.demos.gwt.employeeadmin.model.vo.UserVO;
import org.puremvc.java.multicore.patterns.proxy.Proxy;

/**
 * User Proxy.
 */
public class UserProxy extends Proxy {

	/**
	 * Proxy name.
	 */
	public static final String NAME = "UserProxy";

	/**
	 * Constructor.
	 */
	public UserProxy() {
		super(NAME, new ArrayList<UserVO>());

		// generate some test data
		addItem(new UserVO("lstooge", "Larry", "Stooge", "larry@stooges.com",
				"ijk456", DeptEnum.ACCT));
		addItem(new UserVO("cstooge", "Curly", "Stooge", "curly@stooges.com",
				"xyz987", DeptEnum.SALES));
		addItem(new UserVO("mstooge", "Moe", "Stooge", "moe@stooges.com",
				"abc123", DeptEnum.PLANT));
		
	}

	/**
	 * The users.
	 * @return data property cast to proper type
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<UserVO> users() {
		return(ArrayList<UserVO>)this.getData();
	}

	/**
	 * Add an item to the data.
	 * @param item the userVO
	 */
	public void addItem(UserVO item) {
		users().add(item);
	}

	/**
	 * Update an item in the data.
	 * @param item the userVO
	 */
	public void updateItem(UserVO item) {
		UserVO user = item;
		for(int i = 0; i < users().size(); i++) {
			if(users().get(i).username == user.username) {
				users().set(i, user);
			}
		}
	}

	/**
	 * Delete an item in the data.
	 * @param item the userVO
	 */
	public void deleteItem(UserVO item) {
		UserVO user = item;
		for(int i = 0; i < users().size(); i++) {
			if(users().get(i).username == user.username) {
				users().remove(i);
			}
		}
	}
}
