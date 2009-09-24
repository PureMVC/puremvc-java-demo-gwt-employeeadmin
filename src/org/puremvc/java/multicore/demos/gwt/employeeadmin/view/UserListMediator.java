/*
 PureMVC Java MultiCore Demo - GWT Employee Admin by Anthony Quinault <anthony.quinault@puremvc.org>
 Based upon PureMVC AS3 Demo - Flex Employee Admin - Copyright(c) 2007-08 Cliff Hall <clifford.hall@puremvc.org>
 Your reuse is governed by the Creative Commons Attribution 3.0 License
 */

package org.puremvc.java.multicore.demos.gwt.employeeadmin.view;

import org.puremvc.java.multicore.demos.gwt.employeeadmin.ApplicationFacade;
import org.puremvc.java.multicore.demos.gwt.employeeadmin.model.UserProxy;
import org.puremvc.java.multicore.demos.gwt.employeeadmin.model.vo.UserVO;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.mediator.Mediator;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HTMLTable.Cell;

/**
 * User list mediator.
 */
public class UserListMediator extends Mediator {

	/**
	 * Mediator name.
	 */
	public static final String NAME = "UserListMediator";

	/**
	 * Reference to the user proxy.
	 */
	private UserProxy userProxy = null;

	/**
	 * Panels.
	 */
	private AbsolutePanel aPanel = new AbsolutePanel();
	private CaptionPanel cpanel = new CaptionPanel("Users");
	private VerticalPanel vpanel = new VerticalPanel();
	
	/**
	 * Widgets.
	 */
	private Button bDelete = new Button("Delete");
	private Button bNew = new Button("New");
	private Button bYes = new Button("Yes");
	private Button bNo = new Button("No");
	private Label lText = new Label("Are you sure?");
	private FlexTable table = new FlexTable();
	private FlowPanel listFlowPanel = new FlowPanel();
	private ScrollPanel listPanel = new ScrollPanel(table);

	private int lastTableRow = 0;
	private int selectedRow = -1;

	private static final int SPACING = 3;
	
	/**
	 * Constructor.
	 */
	public UserListMediator() {
		super(NAME, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void onRegister() {
		userProxy = (UserProxy) getFacade().retrieveProxy(UserProxy.NAME);
		initView();
		super.onRegister();
	}
	
	/**
	 * Initialize the user list view.
	 */
	private void initView() {
		aPanel.setStyleName("pmvc-absolutePanelUsers");
		cpanel.setStyleName("pmvc-absoluteCaptionUsers");
		cpanel.add(vpanel);
		aPanel.add(cpanel);
		
		// Setup the table.
		table.setCellSpacing(0);
		table.setCellPadding(0);
		table.setWidth("100%");
		table.addStyleName("pmvc-watchListUsers");
		table.getRowFormatter().addStyleName(0, "pmvc-watchListHeaderUsers");

		insertInList("Username", "First Name", "Last Name", "Email", "Department");
		
		listPanel.addStyleName("pmvc-listePanelUsers");
		listPanel.setSize("100%", "100%");
		
		listFlowPanel.setStyleName("pmvc-listeFlowPanelUsers");
		listFlowPanel.add(listPanel);

		vpanel.add(listFlowPanel);
		HorizontalPanel hp = new HorizontalPanel();
		hp.add(bDelete);
		hp.add(bNew);
		hp.add(lText);
		hp.add(bYes);
		hp.add(bNo);
		
		lText.setVisible(false);
		bYes.setVisible(false);
		bNo.setVisible(false);
		
		vpanel.add(hp);
		hp.setSpacing(SPACING);
		vpanel.setSpacing(SPACING);
		
		table.addClickHandler(new ClickHandler() {
			public void onClick(final ClickEvent event) {
				Cell cell = table.getCellForEvent(event);
				if (cell != null) {
					int row = cell.getRowIndex();
					if (row > 0) {
						selectRow(row - 1);
					}
				}
			}
		});
		
		bNew.addClickHandler(new ClickHandler() {
			public void onClick(final ClickEvent event) {
				onNew();
			} 
		});

		bDelete.addClickHandler(new ClickHandler() {
			public void onClick(final ClickEvent event) {
				if (selectedRow != -1) {
					lText.setVisible(true);
					bYes.setVisible(true);
					bNo.setVisible(true);
				}
			} 
		});

		bYes.addClickHandler(new ClickHandler() {
			public void onClick(final ClickEvent event) {
				lText.setVisible(false);
				bYes.setVisible(false);
				bNo.setVisible(false);
				onDelete();
			} 
		});

		bNo.addClickHandler(new ClickHandler() {
			public void onClick(final ClickEvent event) {
				lText.setVisible(false);
				bYes.setVisible(false);
				bNo.setVisible(false);
			} 
		});

		setViewComponent(aPanel);
		RootPanel.get("userListContainer").add(aPanel);
	}

	/**
	 * Clear list.
	 */
	private void clearList() {
		int count = table.getRowCount();
		while (count > 0) {
		   table.removeRow(0);
		   count = table.getRowCount();
		}

		lastTableRow = 0;

		table.addStyleName("pmvc-watchListUsers");
		table.getRowFormatter().addStyleName(0, "pmvc-watchListHeaderUsers");
		insertInList("Username", "First Name", "Last Name", "Email", "Department");
	}
	
	/**
	 * Insert in list.
	 * @param col1 param1
	 * @param col2 param2
	 * @param col3 param3
	 * @param col4 param4
	 * @param col5 param5
	 */
	private void insertInList(final String col1, final String col2, final String col3, final String col4, final String col5) {
		int i = lastTableRow++;
		table.setText(i, 0, col1);
		table.setText(i, 1, col2);
		table.setText(i, 2, col3);
		table.setText(i, 3, col4);
		table.setText(i, 4, col5);
	}
	
	/**
	 * Select the row.
	 * @param row the row
	 */
	private void selectRow(final int row) {
		styleRow(selectedRow, false);
		styleRow(row, true);
		selectedRow = row;
		onSelect();
	}

	/**
	 * Set the style of the row.
	 * @param row the row
	 * @param selected indicate if row should be selected
	 */
	private void styleRow(final int row, final boolean selected) {
		if (row != -1) {
			if (selected) {
				table.getRowFormatter().addStyleName(row + 1,
						"pmvc-SelectedRowUsers");
			} else {
				table.getRowFormatter().removeStyleName(row + 1,
						"pmvc-SelectedRowUsers");
			}
		}
	}

	/**
	 * Create a new userVO and send a notification.
	 */
	private void onNew() {
		UserVO user = new UserVO();
		sendNotification(ApplicationFacade.NEW_USER, user);
	}

	/**
	 * Remove the user and send a notification.
	 */
	private void onDelete() {
		lastTableRow--;
		sendNotification(ApplicationFacade.DELETE_USER, userProxy.users().get(selectedRow));
	}

	/**
	 * Send USER_SELECTED notification.
	 */
	private void onSelect() {
		sendNotification(ApplicationFacade.USER_SELECTED, userProxy.users().get(selectedRow));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final String[] listNotificationInterests() {
		return new String[] { ApplicationFacade.CANCEL_SELECTED, 
				ApplicationFacade.USER_UPDATED,
				ApplicationFacade.USER_DELETED,
				ApplicationFacade.USER_ADDED,
				ApplicationFacade.INIT_USERS
				};	
	}
	
	/**
	 * {@inheritDoc}
	 */	
	@Override
	public final void handleNotification(final INotification notification) {
		if (notification.getName().equals(ApplicationFacade.CANCEL_SELECTED)) {
		} else if (notification.getName().equals(ApplicationFacade.USER_UPDATED)) {
			clearList();
			for (int i = 0; i < userProxy.users().size(); i++) {
				insertInList(userProxy.users().get(i).username, userProxy.users().get(i).fname, 
					userProxy.users().get(i).lname, userProxy.users().get(i).email, 
					userProxy.users().get(i).department.value);
			}  
		} else if (notification.getName().equals(ApplicationFacade.USER_DELETED)) {
			clearList();
			selectedRow = -1;
			for (int i = 0; i < userProxy.users().size(); i++) { 
				insertInList(userProxy.users().get(i).username, userProxy.users().get(i).fname, 
					userProxy.users().get(i).lname, userProxy.users().get(i).email, 
					userProxy.users().get(i).department.value);
			}  
		} else if (notification.getName().equals(ApplicationFacade.INIT_USERS)) {
			clearList();
			for (int i = 0; i < userProxy.users().size(); i++) {
				insertInList(userProxy.users().get(i).username, userProxy.users().get(i).fname, 
					userProxy.users().get(i).lname, userProxy.users().get(i).email, 
					userProxy.users().get(i).department.value);
			}
		} else if (notification.getName().equals(ApplicationFacade.USER_ADDED)) {
			UserVO user = (UserVO) notification.getBody();
			insertInList(user.username, user.fname, user.lname, user.email, user.department.value);
		}
		super.handleNotification(notification);
	}
}
