/*******************************************************************************
 * Copyright (c) 2014-2015 University of Luxembourg.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Alfredo Capozucca - initial API and implementation
 *     Christophe Kamphaus - Remote implementation of Actors
 *     Thomas Mortimer - Updated client to MVC and added new design patterns
 ******************************************************************************/
package lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.admin;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import lu.uni.lassy.excalibur.examples.icrash.dev.controller.AdminController;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.SystemStateController;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.IncorrectActorException;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.IncorrectFormatException;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.ServerNotBoundException;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.ServerOfflineException;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActAdministrator;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.design.JIntIsActor;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCoordinatorID;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtPoliceID;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.Log4JUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.model.Message;
import lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.abstractgui.AbstractAuthGUIController;
import lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.coordinator.CreateICrashCoordGUI;
import lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.police.CreateICrashPoliGUI;
import javafx.scene.layout.GridPane;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
/*
 * This is the import section to be replaced by modifications in the ICrash.fxml document from the sample skeleton controller
 */
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
/*
 * This is the end of the import section to be replaced by modifications in the ICrash.fxml document from the sample skeleton controller
 */
/**
 * The Class ICrashGUIController, which deals with handling the GUI and it's functions for the Administrator.
 */
public class ICrashAdminGUIController extends AbstractAuthGUIController {
	
	/*
	* This section of controls and methods is to be replaced by modifications in the ICrash.fxml document from the sample skeleton controller
	* When replacing, remember to reassign the correct methods to the button event methods and set the correct types for the tableviews
	*/
	
    /** The pane containing the logon controls. */
	@FXML
    private Pane pnAdminLogon;
	
	
    /** The pane containing the logon controls. */
	@FXML
    private Pane pnAdminRestore;


    /** The textfield that allows input of a username for logon. */
    @FXML
    private TextField txtfldAdminUserName;
	
    /** The textfield that allows input of a phone number. */
    @FXML
    private TextField txtfldAdminPhoneNumber;

    /** The passwordfield that allows input of a password for logon. */
    @FXML
    private PasswordField psswrdfldAdminPassword;

    /** The button that initiates the login function. */
    @FXML
    private Button bttnAdminLogin;
    

    /** The button that initiates the restore psswrd function. */
    @FXML
    private Button bttnAdminRestore;
    

    /** The button that initiates the restore psswrd function. */
    @FXML
    private Button bttnAdminGetPsswrd;

    /** The borderpane that contains the normal controls the user will use. */
    @FXML
    private BorderPane brdpnAdmin;
    
    /** The borderpane that contains the normal controls the user will use. */
    @FXML
    private BorderPane brdpnAdmin1;
    

    /** The anchorpane that will have the add or delete coordinator controls added/removed from it */
    @FXML
    private AnchorPane anchrpnCoordinatorDetails;

    /** The button that shows the controls for adding a coordinator */
    @FXML
    private Button bttnBottomAdminCoordinatorAddACoordinator;

    /** The button that shows the controls for deleting a coordinator */
    @FXML
    private Button bttnBottomAdminCoordinatorDeleteACoordinator;
    
    ////
    /** The anchorpane that will have the add or delete Police controls added/removed from it */
    @FXML
    private AnchorPane anchrpnPoliceDetails;

    /** The button that shows the controls for adding a Police */
    @FXML
    private Button bttnBottomAdminPoliceAddAPolice;

    /** The button that shows the controls for deleting a Police */
    @FXML
    private Button bttnBottomAdminPoliceDeleteAPolice;

    ////
    /** The tableview of the recieved messages from the system */
    @FXML
    private TableView<Message> tblvwAdminMessages;

    /** The button that allows a user to logoff */
    @FXML
    private Button bttnAdminLogoff;
    

    /** The button that allows a user to close restore psswrd window */
    @FXML
    private Button bttnAdminBack;

    /**
     * The button event that will show the controls for adding a coordinator
     *
     * @param event The event type thrown, we do not need this, but it must be specified
     */
    @FXML
    void bttnBottomAdminCoordinatorAddACoordinator_OnClick(ActionEvent event) {
    	showCoordinatorScreen(TypeOfEdit.Add);
    }

    /**
     * The button event that will show the controls for deleting a coordinator
     *
     * @param event The event type thrown, we do not need this, but it must be specified
     */
    @FXML
    void bttnBottomAdminCoordinatorDeleteACoordinator_OnClick(ActionEvent event) {
    	showCoordinatorScreen(TypeOfEdit.Delete);
    }
    ///
    /**
     * The button event that will show the controls for adding a Police
     *
     * @param event The event type thrown, we do not need this, but it must be specified
     */
    @FXML
    void bttnBottomAdminPoliceAddAPolice_OnClick(ActionEvent event) {
    	showPoliceScreen(TypeOfEdit.Add);
    }

    /**
     * The button event that will show the controls for deleting a Police
     *
     * @param event The event type thrown, we do not need this, but it must be specified
     */
    @FXML
    void bttnBottomAdminPoliceDeleteAPolice_OnClick(ActionEvent event) {
    	showPoliceScreen(TypeOfEdit.Delete);
    }

    ///
    
    /**
     * The button event that will initiate the logging on of a user
     *
     * @param event The event type thrown, we do not need this, but it must be specified
     */
    @FXML
    void bttnBottomLoginPaneLogin_OnClick(ActionEvent event) {
    	logon();
    }
    
    /**
     * The button event that will initiate the restoring psswrd of a user
     *
     * @param event The event type thrown, we do not need this, but it must be specified
     */
    @FXML
    void bttnBottomGetPsswrdPaneGetPsswrd_OnClick(ActionEvent event) {
    	getPsswrd();
    	//System.out.println("7WXC1359");
    }
    
    private void getPsswrd() {
    	
    		if(txtfldAdminPhoneNumber.getText().length() > 0 ){
    				if ((txtfldAdminPhoneNumber.getText()).equals("123456"))
    					System.out.println("Your password is 7WXC1359");
        	}
        	else
        		showWarningNoDataEntered();
	}

    

	/**
     * The button event that will initiate the restoring psswrd of a user
     *
     * @param event The event type thrown, we do not need this, but it must be specified
     */
    @FXML
    void bttnBottomBackPaneBack_OnClick(ActionEvent event) {
    	backToMain();
    }

    private void backToMain() {
    	pnAdminRestore.setVisible(false);
	}
    
    
    /**
     * The button event that will initiate the restoring psswrd of a user
     *
     * @param event The event type thrown, we do not need this, but it must be specified
     */
    @FXML
    void bttnBottomRestorePaneRestore_OnClick(ActionEvent event) {
    	restoreP();
    }

    private void restoreP() {
    	pnAdminRestore.setVisible(true);
	}

	/**
     * The button event that will initiate the logging off of a user
     *
     * @param event The event type thrown, we do not need this, but it must be specified
     */
    @FXML
    void bttnTopLogoff_OnClick(ActionEvent event) {
    	logoff();
    }

    /*
     * These are other classes accessed by this controller
     */
    /** The user controller, for this GUI it's the admin controller and allows access to admin functions like adding a coordinator. */
	private AdminController userController;
	
	/** Used to get the actor coordinator that was created by the admin, for creating the new window with. */
	private SystemStateController systemstateController;
	
	/*
	 * Other things created for this controller
	 */
	/**
	 * The enumeration dictating the type of edit an admin is doing to a coordinator.
	 */
	private enum TypeOfEdit{
		
		/** Adding a coordinator/Police. */
		Add,
		
		/** Deleting a coordinator/Police. */
		Delete
	}
	
	/**
	 * The list of open windows in the system.
	 * We open a new window when a coordinator is created, so we also should close the window if the coordinator is deleted 
	 */
	private ArrayList<CreateICrashCoordGUI> listOfOpenWindows = new ArrayList<CreateICrashCoordGUI>();
	/**
	 * The list of open windows in the system.
	 * We open a new window when a Police is created, so we also should close the window if the Police is deleted 
	 */
	private ArrayList<CreateICrashPoliGUI> listOfOpenWindowsPoli = new ArrayList<CreateICrashPoliGUI>();
	/*
	 * Methods used within the GUI
	 */

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.abstractgui.AbstractAuthGUIController#logonShowPanes(boolean)
	 */
	protected void logonShowPanes(boolean loggedOn) {
		pnAdminLogon.setVisible(!loggedOn);
		brdpnAdmin.setVisible(loggedOn);
		brdpnAdmin1.setVisible(loggedOn);
		bttnAdminLogoff.setDisable(!loggedOn);
		bttnAdminLogin.setDefaultButton(!loggedOn);
		bttnAdminRestore.setDefaultButton(!loggedOn);
		pnAdminRestore.setVisible(false);
		if (!loggedOn){
			txtfldAdminUserName.setText("");
			psswrdfldAdminPassword.setText("");
			txtfldAdminUserName.requestFocus();
			for (int i = anchrpnCoordinatorDetails.getChildren().size() -1; i >= 0; i--)
				anchrpnCoordinatorDetails.getChildren().remove(i);
			for (int i = anchrpnPoliceDetails.getChildren().size() -1; i >= 0; i--)
				anchrpnPoliceDetails.getChildren().remove(i);
		}
		
	}	
	
	/**
	 * Server has gone down.
	 */
	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.AbstractGUIController#serverHasGoneDown()
	 */
	protected void serverHasGoneDown(){
		logoff();
	}
	
	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.abstractgui.HasTables#setUpTables()
	 */
	public void setUpTables(){
		setUpMessageTables(tblvwAdminMessages);
	}

	/**
	 * Shows the modify coordinator screen.
	 *
	 * @param type The type of edit to be done, this could be add or delete
	 */
	private void showCoordinatorScreen(TypeOfEdit type){
		for(int i = anchrpnCoordinatorDetails.getChildren().size() -1; i >= 0; i--)
			anchrpnCoordinatorDetails.getChildren().remove(i);
		TextField txtfldUserID = new TextField();
		TextField txtfldUserName = new TextField();
		PasswordField psswrdfldPassword = new PasswordField();
		txtfldUserID.setPromptText("User ID");
		Button bttntypOK = null;
		GridPane grdpn = new GridPane();
		grdpn.add(txtfldUserID, 1, 1);
		switch(type){
		case Add:
			bttntypOK = new Button("Create");
			txtfldUserName.setPromptText("User name");
			psswrdfldPassword.setPromptText("Password");
			grdpn.add(txtfldUserName, 1, 2);
			grdpn.add(psswrdfldPassword, 1, 3);
			grdpn.add(bttntypOK, 1, 4);
			break;
		case Delete:
			bttntypOK = new Button("Delete");
			grdpn.add(bttntypOK, 1, 2);
			break;		
		}
		bttntypOK.setDefaultButton(true);
		bttntypOK.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (!checkIfAllDialogHasBeenFilledIn(grdpn))
					showWarningNoDataEntered();
				else{
					try {
						DtCoordinatorID coordID = new DtCoordinatorID(new PtString(txtfldUserID.getText()));
						switch(type){
						case Add:
							if (userController.oeAddCoordinator(txtfldUserID.getText(), txtfldUserName.getText(), psswrdfldPassword.getText()).getValue()){
								listOfOpenWindows.add(new CreateICrashCoordGUI(coordID, systemstateController.getActCoordinator(txtfldUserName.getText())));
								anchrpnCoordinatorDetails.getChildren().remove(grdpn);
							}
							else
								showErrorMessage("Unable to add coordinator", "An error occured when adding the coordinator");
							break;
						case Delete:
							if (userController.oeDeleteCoordinator(txtfldUserID.getText()).getValue()){
								for(CreateICrashCoordGUI window : listOfOpenWindows){
									if (window.getDtCoordinatorID().value.getValue().equals(coordID.value.getValue()))
										window.closeWindow();
								}
								anchrpnCoordinatorDetails.getChildren().remove(grdpn);
							}
							else
								showErrorMessage("Unable to delete coordinator", "An error occured when deleting the coordinator");
							break;
						}
					} catch (ServerOfflineException | ServerNotBoundException | IncorrectFormatException e) {
						showExceptionErrorMessage(e);
					}					
				}
			}
		});
		anchrpnCoordinatorDetails.getChildren().add(grdpn);
		AnchorPane.setTopAnchor(grdpn, 0.0);
		AnchorPane.setLeftAnchor(grdpn, 0.0);
		AnchorPane.setBottomAnchor(grdpn, 0.0);
		AnchorPane.setRightAnchor(grdpn, 0.0);
		txtfldUserID.requestFocus();
	}
	
	///
	/**
	 * Shows the modify Police screen.
	 *
	 * @param type The type of edit to be done, this could be add or delete
	 */
	private void showPoliceScreen(TypeOfEdit type){
		for(int i = anchrpnPoliceDetails.getChildren().size() -1; i >= 0; i--)
			anchrpnPoliceDetails.getChildren().remove(i);
		TextField txtfldUserID = new TextField();
		TextField txtfldUserName = new TextField();
		PasswordField psswrdfldPassword = new PasswordField();
		txtfldUserID.setPromptText("User ID");
		Button bttntypOK = null;
		GridPane grdpn = new GridPane();
		grdpn.add(txtfldUserID, 1, 1);
		switch(type){
		case Add:
			bttntypOK = new Button("Create");
			txtfldUserName.setPromptText("User name");
			psswrdfldPassword.setPromptText("Password");
			grdpn.add(txtfldUserName, 1, 2);
			grdpn.add(psswrdfldPassword, 1, 3);
			grdpn.add(bttntypOK, 1, 4);
			break;
		case Delete:
			bttntypOK = new Button("Delete");
			grdpn.add(bttntypOK, 1, 2);
			break;		
		}
		bttntypOK.setDefaultButton(true);
		bttntypOK.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (!checkIfAllDialogHasBeenFilledIn(grdpn))
					showWarningNoDataEntered();
				else{
					try {
						DtPoliceID poliID = new DtPoliceID(new PtString(txtfldUserID.getText()));
						switch(type){
						case Add:
							if (userController.oeAddPolice(txtfldUserID.getText(), txtfldUserName.getText(), psswrdfldPassword.getText()).getValue()){
								listOfOpenWindowsPoli.add(new CreateICrashPoliGUI(poliID, systemstateController.getActPolice(txtfldUserName.getText())));
								anchrpnPoliceDetails.getChildren().remove(grdpn);
							}
							else
								showErrorMessage("Unable to add police", "An error occured when adding the police");
							break;
						case Delete:
							if (userController.oeDeletePolice(txtfldUserID.getText()).getValue()){
								for(CreateICrashPoliGUI window : listOfOpenWindowsPoli){
									if (window.getDtPoliceID().value.getValue().equals(poliID.value.getValue()))
										window.closeWindow();
								}
								anchrpnPoliceDetails.getChildren().remove(grdpn);
							}
							else
								showErrorMessage("Unable to delete police", "An error occured when deleting the Police");
							break;
						}
					} catch (ServerOfflineException | ServerNotBoundException | IncorrectFormatException e) {
						showExceptionErrorMessage(e);
					}					
				}
			}
		});
		anchrpnPoliceDetails.getChildren().add(grdpn);
		AnchorPane.setTopAnchor(grdpn, 0.0);
		AnchorPane.setLeftAnchor(grdpn, 0.0);
		AnchorPane.setBottomAnchor(grdpn, 0.0);
		AnchorPane.setRightAnchor(grdpn, 0.0);
		txtfldUserID.requestFocus();
	}
	///

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.abstractgui.AbstractAuthGUIController#logon()
	 */
	@Override
	public void logon() {
		if(txtfldAdminUserName.getText().length() > 0 && psswrdfldAdminPassword.getText().length() > 0){
			try {
				if (userController.oeLogin(txtfldAdminUserName.getText(), psswrdfldAdminPassword.getText()).getValue())
					logonShowPanes(true);
			}
			catch (ServerOfflineException | ServerNotBoundException e) {
				showExceptionErrorMessage(e);
			}	
    	}
    	else
    		showWarningNoDataEntered();
	}
	
	

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.abstractgui.AbstractAuthGUIController#logoff()
	 */
	@Override
	public void logoff() {
		try {
			if (userController.oeLogout().getValue()){
				logonShowPanes(false);
			}
		} catch (ServerOfflineException | ServerNotBoundException e) {
			showExceptionErrorMessage(e);
		}
	}
	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.abstractgui.AbstractGUIController#closeForm()
	 */
	@Override
	public void closeForm() {
		try {
			userController.removeAllListeners();
			systemstateController.closeServerConnection();
		} catch (ServerOfflineException | ServerNotBoundException e) {
			showExceptionErrorMessage(e);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		systemstateController = new SystemStateController();
		logonShowPanes(false);
		setUpTables();
		
	}

	@Override
	public PtBoolean setActor(JIntIsActor actor) {
		try {
			if (actor instanceof ActAdministrator)
				try{
					userController = new AdminController((ActAdministrator)actor);
					try{
						userController.getAuthImpl().listOfMessages.addListener(new ListChangeListener<Message>() {
							@Override
							public void onChanged(ListChangeListener.Change<? extends Message> c) {
								addMessageToTableView(tblvwAdminMessages, c.getList());
							}
						});
					} catch (Exception e){
						showExceptionErrorMessage(e);
					}
				}catch (RemoteException e){
					Log4JUtils.getInstance().getLogger().error(e);
					throw new ServerOfflineException();
				} catch (NotBoundException e) {
					Log4JUtils.getInstance().getLogger().error(e);
					throw new ServerNotBoundException();
				}
			else
				throw new IncorrectActorException(actor, ActAdministrator.class);
		} catch (ServerOfflineException | ServerNotBoundException | IncorrectActorException e) {
			showExceptionErrorMessage(e);
			return new PtBoolean(false);
		}
		return new PtBoolean(false);
	}	
}
