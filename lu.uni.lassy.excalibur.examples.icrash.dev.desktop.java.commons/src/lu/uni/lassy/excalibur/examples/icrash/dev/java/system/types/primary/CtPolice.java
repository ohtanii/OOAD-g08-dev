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
package lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;

/**
 * The Class CtPolice which extends the class of CtAuthenticated
 * This is the class that stores details about the Police.
 */
public class CtPolice extends CtAuthenticated {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 227L;

	/** The id of the Police. */
	public DtPoliceID id;
		
	/**
	 * Initialises the Police.
	 *
	 * @param aId The ID of the Police
	 * @param aLogin The username of the Police
	 * @param aPwd The password of the Police
	 * @return The success of the initialisation
	 */
	public PtBoolean init(DtPoliceID aId,DtLogin aLogin,DtPassword aPwd){
			super.init(aLogin, aPwd);
			id = aId;
			return new PtBoolean(true); 
	}
	
	/**
	 * Updates the user details, used when the administrator does an update on the Police.
	 *
	 * @param aLogin The value to change the login to
	 * @param aPwd the value to change the password to 
	 * @return the success of the update method
	 */
	public PtBoolean update(DtLogin aLogin,DtPassword aPwd){
		login = aLogin;
		pwd = aPwd;
		return new PtBoolean(true);
	}
	
	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtAuthenticated#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj){
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof CtPolice))
			return false;
		CtPolice aCtPolice = (CtPolice)obj;
		if (!aCtPolice.id.value.getValue().equals(this.id.value.getValue()))
			return false;
		return true;
	}
	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtAuthenticated#hashCode()
	 */
	@Override
	public int hashCode(){
		return this.id.value.getValue().length() + super.hashCode();
	}
}
