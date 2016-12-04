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
package lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.police;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActPolice;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtPoliceID;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.Log4JUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.abstractgui.CreatedWindows;

import java.net.URL;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * The Class MainICrashGUI that allows creation of a police iCrash window.
 */
public class CreateICrashPoliGUI implements CreatedWindows {
	
	/**
	 * Instantiates a new window for the police to use iCrash.
	 *
	 * @param aDtPoliceID the ID of the coordinator associated with this window
	 * @param aActPolice the actor associated with this window
	 */
	public CreateICrashPoliGUI(DtPoliceID aDtPoliceID, ActPolice aActPolice) {
		this._aDtPoliceID = aDtPoliceID;
		start(aActPolice);
	}
	
	/** The Police ID that was used to create this window, it's used to work out which windows to close when a coordinator has been deleted. */
	private DtPoliceID _aDtPoliceID;
	
	/**
	 * Gets the data type of the Police's ID.
	 *
	 * @return the datatype of the Police's ID
	 */
	public DtPoliceID getDtPoliceID(){
		return this._aDtPoliceID;
	}
	
	/** The stage that the form will be held in. */
	private Stage stage;
	
	/**
	 * Starts the system and opens the window up (If no exceptions have been thrown).
	 *
	 * @param aActPolice the actor associated with this window
	 */
	private void start(ActPolice aActPolice) {
		try {
			URL url = this.getClass().getResource("ICrashPoliGUI.fxml");
			FXMLLoader loader = new FXMLLoader(url);
			Parent root = (Parent)loader.load();
            stage = new Stage();
            stage.setTitle("iCrash Police - " + aActPolice.getLogin().value.getValue());
            stage.setScene(new Scene(root));
            stage.show();
            ((ICrashPoliGUIController)loader.getController()).setActor(aActPolice);
            ((ICrashPoliGUIController)loader.getController()).setWindow(stage);
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					((ICrashPoliGUIController)loader.getController()).closeForm();
				}
			});
		} catch(Exception e) {
			Log4JUtils.getInstance().getLogger().error(e);
		}
	}
	
	/**
	 * Allows the other screens to force this window to close. Will be used if the coordinator has been deleted
	 */
	@Override
	public void closeWindow(){
		if (stage != null)
			stage.close();
	}
}
