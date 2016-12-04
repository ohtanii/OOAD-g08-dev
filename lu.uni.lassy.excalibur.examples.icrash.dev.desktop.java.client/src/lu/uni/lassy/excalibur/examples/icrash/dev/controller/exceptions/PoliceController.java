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
package lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Hashtable;

import lu.uni.lassy.excalibur.examples.icrash.dev.controller.AbstractUserController;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.IncorrectFormatException;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.ServerNotBoundException;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.ServerOfflineException;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActCoordinator;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActPolice;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActProxyAuthenticated.UserType;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActProxyCoordinator;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActProxyPolice;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.design.JIntIs;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtAlertID;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtComment;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCrisisID;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtAlertStatus;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtCrisisStatus;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.Log4JUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.model.actors.ActProxyCoordinatorImpl;
import lu.uni.lassy.excalibur.examples.icrash.dev.model.actors.ActProxyPoliceImpl;

/**
 * The Class PoliceController.
 */
public class PoliceController extends AbstractUserController {

	/**
	 * Instantiates a new Police controller.
	 *
	 * @param aActCoord the a act poli
	 * @throws RemoteException the remote exception
	 * @throws NotBoundException the not bound exception
	 */
	public PoliceController(ActPolice aActPoli ) throws RemoteException, NotBoundException{
		super(new ActProxyPoliceImpl(aActPoli));
	}
	/**
	 * Takes an crisis that exists in the system and will change it's status to the one specified.
	 *
	 * @param crisisID The ID of the crisis to change the status of
	 * @param status The EtCrisisStatus type to set the crisis status to
	 * @return Returns a PtBoolean of true if done successfully, otherwise will return a false
	 * @throws ServerNotBoundException is only thrown when attempting to access a server which has no current binding. This shouldn't happen, but you never know!
	 * @throws ServerOfflineException is an error that is thrown when the server is offline or not reachable
	 * @throws IncorrectFormatException is thrown when a Dt/Et information type does not match the is() method specified in the specification
	 */
	public PtBoolean changeCrisisStatus(String crisisID, EtCrisisStatus status) throws ServerNotBoundException, ServerOfflineException, IncorrectFormatException{
		DtCrisisID aDtCrisisID = new DtCrisisID(new PtString(crisisID));
		Hashtable<JIntIs, String> ht = new Hashtable<JIntIs, String>();
		ht.put(aDtCrisisID, aDtCrisisID.value.getValue());
		ht.put(status, status.name());
		if (this.getUserType() == UserType.Police){
			ActProxyPolice actCoord = (ActProxyPolice)this.getAuth();
			try {
				return actCoord.oeSetCrisisStatus(aDtCrisisID, status);
			} catch (RemoteException e) {
				Log4JUtils.getInstance().getLogger().error(e);
				throw new ServerOfflineException();
			} catch (NotBoundException e) {
				Log4JUtils.getInstance().getLogger().error(e);
				throw new ServerNotBoundException();
			}
		}
		return new PtBoolean(false);
	}
	/**
	 * Takes an crisis that exists in the system and assign it to the current logged in user.
	 *
	 * @param crisisID The ID of the crisis to assign to the logged in user
	 * @return Returns a PtBoolean of true if done successfully, otherwise will return a false
	 * @throws ServerOfflineException is an error that is thrown when the server is offline or not reachable
	 * @throws ServerNotBoundException is only thrown when attempting to access a server which has no current binding. This shouldn't happen, but you never know!
	 * @throws IncorrectFormatException is thrown when a Dt/Et information type does not match the is() method specified in the specification
	 */
	public PtBoolean handleCrisis(String crisisID) throws ServerOfflineException, ServerNotBoundException, IncorrectFormatException{
		DtCrisisID aDtCrisisID = new DtCrisisID(new PtString(crisisID));
		Hashtable<JIntIs, String> ht = new Hashtable<JIntIs, String>();
		ht.put(aDtCrisisID, aDtCrisisID.value.getValue());
		if (this.getUserType() == UserType.Police){
			ActProxyPolice actPoli = (ActProxyPolice)this.getAuth();
			try {
				return actPoli.oeSetCrisisHandler(aDtCrisisID);
			} catch (RemoteException e) {
				Log4JUtils.getInstance().getLogger().error(e);
				throw new ServerOfflineException();
			} catch (NotBoundException e) {
				Log4JUtils.getInstance().getLogger().error(e);
				throw new ServerNotBoundException();
			}
		}
		return new PtBoolean(false);
	}
	
	/**
	 * Takes an crisis that exists in the system and closes it. This will set the crisis status to closed
	 *
	 * @param crisisID The ID of the crisis to close
	 * @return Returns a PtBoolean of true if done successfully, otherwise will return a false
	 * @throws ServerOfflineException is an error that is thrown when the server is offline or not reachable
	 * @throws ServerNotBoundException is only thrown when attempting to access a server which has no current binding. This shouldn't happen, but you never know!
	 * @throws IncorrectFormatException is thrown when a Dt/Et information type does not match the is() method specified in the specification
	 */
	public PtBoolean closeCrisis(String crisisID) throws ServerOfflineException, ServerNotBoundException, IncorrectFormatException{
		DtCrisisID aDtCrisisID = new DtCrisisID(new PtString(crisisID));
		Hashtable<JIntIs, String> ht = new Hashtable<JIntIs, String>();
		ht.put(aDtCrisisID, aDtCrisisID.value.getValue());
		if (this.getUserType() == UserType.Police){
			ActProxyPolice actPoli = (ActProxyPolice)this.getAuth();
			try {
				return actPoli.oeCloseCrisis(aDtCrisisID);
			} catch (RemoteException e) {
				Log4JUtils.getInstance().getLogger().error(e);
				throw new ServerOfflineException();
			} catch (NotBoundException e) {
				Log4JUtils.getInstance().getLogger().error(e);
				throw new ServerNotBoundException();
			}
		}
		return new PtBoolean(false);
	}
	
	/**
	 * Takes an crisis that exists in the system and changes the report associated to it to the value specified.
	 *
	 * @param crisisID The ID of the crisis to change the report of
	 * @param comment The text to set the report of the crisis to
	 * @return Returns a PtBoolean of true if done successfully, otherwise will return a false
	 * @throws ServerOfflineException is an error that is thrown when the server is offline or not reachable
	 * @throws ServerNotBoundException is only thrown when attempting to access a server which has no current binding. This shouldn't happen, but you never know!
	 * @throws IncorrectFormatException is thrown when a Dt/Et information type does not match the is() method specified in the specification
	 */
	public PtBoolean reportOnCrisis(String crisisID, String comment) throws ServerOfflineException, ServerNotBoundException, IncorrectFormatException{
		DtCrisisID aDtCrisisID = new DtCrisisID(new PtString(crisisID));
		DtComment aDtComment = new DtComment(new PtString(comment));
		Hashtable<JIntIs, String> ht = new Hashtable<JIntIs, String>();
		ht.put(aDtCrisisID, aDtCrisisID.value.getValue());
		ht.put(aDtComment, aDtComment.value.getValue());
		if (this.getUserType() == UserType.Police){
			ActProxyPolice actPoli = (ActProxyPolice)this.getAuth();
			try {
				return actPoli.oeReportOnCrisis(aDtCrisisID, aDtComment);
			} catch (RemoteException e) {
				Log4JUtils.getInstance().getLogger().error(e);
				throw new ServerOfflineException();
			} catch (NotBoundException e) {
				Log4JUtils.getInstance().getLogger().error(e);
				throw new ServerNotBoundException();
			}
		}
		return new PtBoolean(false);
	}
	
	/**
	 * Gets a list of all crises from the server with the status type of the one provided
	 * The list will be sent to the actor directly, via the ie method.
	 *
	 * @param aEtCrisisStatus The status of the crisis to filter by
	 * @return The success of the method
	 * @throws ServerOfflineException Thrown if the server is offline
	 * @throws ServerNotBoundException Thrown if the server is not bound
	 */
	public PtBoolean oeGetCrisisSet(EtCrisisStatus aEtCrisisStatus) throws ServerOfflineException, ServerNotBoundException{
		if (this.getUserType() == UserType.Police){
			ActProxyPolice actPoli = (ActProxyPolice)this.getAuth();
			try {
				return actPoli.oeGetCrisisSet(aEtCrisisStatus);
			} catch (RemoteException e) {
				Log4JUtils.getInstance().getLogger().error(e);
				throw new ServerOfflineException();
			} catch (NotBoundException e) {
				Log4JUtils.getInstance().getLogger().error(e);
				throw new ServerNotBoundException();
			}
		}
		return new PtBoolean(false);
	}
	
}
