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
package lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.IcrashSystem;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtCrisis;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtComment;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCrisisID;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtLogin;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtCrisisStatus;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtCrisisType;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.Log4JUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.RmiUtils;

import org.apache.log4j.Logger;

/**
 * The Class ActPoliceImpl, which creates a server side actor of type Police.
 */
public class ActPoliceImpl extends ActAuthenticatedImpl implements ActPolice {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 227L;

	/**
	 * Instantiates a new server side actor of type Police.
	 *
	 * @param n The username of the actor, this is of type DtLogin. It is used in identifying the correct actor when working with their Class type (CtPolice)
	 * @throws RemoteException Thrown if the server is offline
	 */
	public ActPoliceImpl(DtLogin n) throws RemoteException {
		super(n);
	}
	
	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActCoordinator#oeGetCrisisSet(lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtCrisisStatus)
	 */
	synchronized public PtBoolean oeGetCrisisSet(EtCrisisStatus aEtCrisisStatus) throws RemoteException, NotBoundException {
	
		Logger log = Log4JUtils.getInstance().getLogger();
	
		Registry registry = LocateRegistry.getRegistry(RmiUtils.getInstance().getHost(),RmiUtils.getInstance().getPort());
			 	
		//Gathering the remote object as it was published into the registry
	    IcrashSystem iCrashSys_Server = (IcrashSystem)registry.lookup("iCrashServer");
		
	
		//set up ActAuthenticated instance that performs the request
		iCrashSys_Server.setCurrentRequestingAuthenticatedActor(this);

		log.info("message ActPolice.oeGetCrisisSet sent to system");
		PtBoolean res = iCrashSys_Server.oeGetCrisisSet(aEtCrisisStatus);
			
			
		if(res.getValue() == true)
			log.info("operation oeGetCrisisSet successfully executed by the system");


		return res;
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActPolice#oeSetCrisisHandler(lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCrisisID)
	 */
	synchronized public PtBoolean oeSetCrisisHandler(DtCrisisID aDtCrisisID) throws RemoteException, NotBoundException {
	
		Logger log = Log4JUtils.getInstance().getLogger();
	
		Registry registry = LocateRegistry.getRegistry(RmiUtils.getInstance().getHost(),RmiUtils.getInstance().getPort());
			 	
		//Gathering the remote object as it was published into the registry
	    IcrashSystem iCrashSys_Server = (IcrashSystem)registry.lookup("iCrashServer");
	
		//set up ActAuthenticated instance that performs the request
		iCrashSys_Server.setCurrentRequestingAuthenticatedActor(this);

		log.info("message ActCoordinator.oeSetCrisisHandler sent to system");
		PtBoolean res = iCrashSys_Server.oeSetCrisisHandler(aDtCrisisID);
			
			
		if(res.getValue() == true)
			log.info("operation oeSetCrisisHandler successfully executed by the system");


		return res;
	}

	
	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActPolice#oeSetCrisisStatus(lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCrisisID, lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtCrisisStatus)
	 */
	synchronized public PtBoolean oeSetCrisisStatus(DtCrisisID aDtCrisisID, EtCrisisStatus aEtCrisisStatus) throws RemoteException, NotBoundException {
	
		Logger log = Log4JUtils.getInstance().getLogger();
	
		Registry registry = LocateRegistry.getRegistry(RmiUtils.getInstance().getHost(),RmiUtils.getInstance().getPort());

			 	
		//Gathering the remote object as it was published into the registry
	    IcrashSystem iCrashSys_Server = (IcrashSystem)registry.lookup("iCrashServer");
		//set up ActAuthenticated instance that performs the request
		iCrashSys_Server.setCurrentRequestingAuthenticatedActor(this);

		log.info("message ActPolice.oeSetCrisisStatus sent to system");
		PtBoolean res = iCrashSys_Server.oeSetCrisisStatus(aDtCrisisID, aEtCrisisStatus);
			
			
		if(res.getValue() == true)
			log.info("operation oeSetCrisisStatus successfully executed by the system");


		return res;
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActPolice#oeSetCrisisType(lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCrisisID, lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtCrisisType)
	 */
	synchronized public PtBoolean oeSetCrisisType(DtCrisisID aDtCrisisID, EtCrisisType aEtCrisisType) throws RemoteException, NotBoundException {

		Logger log = Log4JUtils.getInstance().getLogger();

		Registry registry = LocateRegistry.getRegistry(RmiUtils.getInstance().getHost(),RmiUtils.getInstance().getPort());

		//Gathering the remote object as it was published into the registry
		IcrashSystem iCrashSys_Server = (IcrashSystem)registry.lookup("iCrashServer");
		//set up ActAuthenticated instance that performs the request
		iCrashSys_Server.setCurrentRequestingAuthenticatedActor(this);

		log.info("message ActPolice.oeSetCrisisType sent to system");
		PtBoolean res = iCrashSys_Server.oeSetCrisisType(aDtCrisisID, aEtCrisisType);


		if(res.getValue() == true)
			log.info("operation oeSetCrisisType successfully executed by the system");


		return res;
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActPolice#oeReportOnCrisis(lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCrisisID, lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtComment)
	 */
	synchronized public PtBoolean oeReportOnCrisis(DtCrisisID aDtCrisisID,DtComment aDtComment) throws RemoteException, NotBoundException {
	
		Logger log = Log4JUtils.getInstance().getLogger();

		Registry registry = LocateRegistry.getRegistry(RmiUtils.getInstance().getHost(),RmiUtils.getInstance().getPort());
			 	
		//Gathering the remote object as it was published into the registry
	    IcrashSystem iCrashSys_Server = (IcrashSystem)registry.lookup("iCrashServer");
		//set up ActAuthenticated instance that performs the request
		iCrashSys_Server.setCurrentRequestingAuthenticatedActor(this);
		
		
		//set up ActAuthenticated instance that performs the request
		iCrashSys_Server.setCurrentRequestingAuthenticatedActor(this);

		log.info("message ActPolice.oeReportOnCrisis sent to system");
		PtBoolean res = iCrashSys_Server.oeReportOnCrisis(aDtCrisisID, aDtComment);
			
			
		if(res.getValue() == true)
			log.info("operation oeReportOnCrisis successfully executed by the system");


		return res;

	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActPolice#oeCloseCrisis(lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCrisisID)
	 */
	synchronized public PtBoolean oeCloseCrisis(DtCrisisID aDtCrisisID) throws RemoteException, NotBoundException {
	
		Logger log = Log4JUtils.getInstance().getLogger();

		Registry registry = LocateRegistry.getRegistry(RmiUtils.getInstance().getHost(),RmiUtils.getInstance().getPort());

			 	
		//Gathering the remote object as it was published into the registry
	    IcrashSystem iCrashSys_Server = (IcrashSystem)registry.lookup("iCrashServer");
		//set up ActAuthenticated instance that performs the request
		iCrashSys_Server.setCurrentRequestingAuthenticatedActor(this);

		//set up ActAuthenticated instance that performs the request
		iCrashSys_Server.setCurrentRequestingAuthenticatedActor(this);

		log.info("message ActPolice.oeCloseCrisis sent to system");
		PtBoolean res = iCrashSys_Server.oeCloseCrisis(aDtCrisisID);
			
			
		if(res.getValue() == true)
			log.info("operation oeCloseCrisis successfully executed by the system");


		return res;

		
	}
	
	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActPolice#ieSendACrisis(lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtCrisis)
	 */
	public PtBoolean ieSendACrisis(CtCrisis aCtCrisis) {

		Logger log = Log4JUtils.getInstance().getLogger();

		log.info("message ActPolice.ieSendACrisis received from system");
		log.info("crisis id '"	+ aCtCrisis.id.value.getValue().toString() + "' "+
				"in status '"+ aCtCrisis.status.toString()+"'");
		
		for(ActProxyAuthenticated aProxy : listeners)
			try {
				if (aProxy instanceof ActProxyPolice)
					((ActProxyPolice)aProxy).ieSendACrisis(aCtCrisis);
			} catch (RemoteException e) {
				Log4JUtils.getInstance().getLogger().error(e);
			}

		return new PtBoolean(true);
	}
	
}
