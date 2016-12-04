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
package lu.uni.lassy.excalibur.examples.icrash.dev.java.system.db;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtPolice;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtLogin;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtPassword;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtPoliceID;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtString;

/**
 * The Class DbPolices for updating or retrieving information on the coordinators table.
 */
public class DbPolices extends DbAbstract{

	/**
	 * Creates a new Police in the database using the data from the CtPolice passed.
	 *
	 * @param aCtPolice The CtPolice of which to use the data of to create the row in the database
	 */
	static public void insertPolice(CtPolice aCtPolice){
	
		try {
			conn = DriverManager.getConnection(url+dbName,userName,password);
			log.debug("Connected to the database");

			/********************/
			//Insert
			
			try{
				Statement st = conn.createStatement();
				
				String id = aCtPolice.id.value.getValue();
				String login =  aCtPolice.login.value.getValue();
				String pwd =  aCtPolice.pwd.value.getValue();
	
				log.debug("[DATABASE]-Insert police");
				int val = st.executeUpdate("INSERT INTO "+ dbName+ ".polices" +
											"(id,login,pwd)" + 
											"VALUES("+"'"+id+"'"+",'"+login+"','"+pwd+"')");
				
				log.debug(val + " row affected");
			}
			catch (SQLException s){
				log.error("SQL statement is not executed! "+s);
			}

	
			conn.close();
			log.debug("Disconnected from database");
		} catch (Exception e) {
			logException(e);
		}
	}
	
	/**
	 * Gets a Police details from the database, depending on the ID used and creates a CtPolice class to house the data.
	 *
	 * @param poliId The ID of which Police to get the data of
	 * @return The Police that holds the data retrieved. It could be an empty class!
	 */
	static public CtPolice getPolice(String poliId){
		
		CtPolice aCtPolice = new CtPolice();
		
		try {
			conn = DriverManager.getConnection(url+dbName,userName,password);
			log.debug("Connected to the database");

			

			/********************/
			//Select
			
			try{
				String sql = "SELECT * FROM "+ dbName + ".polices WHERE id = " + poliId;
				

				PreparedStatement statement = conn.prepareStatement(sql);
				ResultSet  res = statement.executeQuery(sql);
				
				if(res.next()) {				
					
					aCtPolice = new CtPolice();
					//Police's id
					DtPoliceID aId = new DtPoliceID(new PtString(res.getString("id")));
					//Police's login
					DtLogin aLogin = new DtLogin(new PtString(res.getString("login")));
					//Police's pwd
					DtPassword aPwd = new DtPassword(new PtString(res.getString("pwd")));

					aCtPolice.init(aId, aLogin,aPwd);
					
				}
								
			}
			catch (SQLException s){
				log.error("SQL statement is not executed! "+s);
			}
			conn.close();
			log.debug("Disconnected from database");
			
			
		} catch (Exception e) {
			logException(e);
		}
		
		return aCtPolice;

	}
	
	/**
	 * Delete a Police from the database.
	 *
	 * @param aCtPolice The Police to delete from the database
	 */
	static public void deletePolice(CtPolice aCtPolice){
		
		try {
			conn = DriverManager.getConnection(url+dbName,userName,password);
			log.debug("Connected to the database");

			/********************/
			//Delete
			
			try{
				String sql = "DELETE FROM "+ dbName+ ".polices WHERE id = ?";
				String id = aCtPolice.id.value.getValue();

				PreparedStatement statement = conn.prepareStatement(sql);
				statement.setString(1, id);
				int rows = statement.executeUpdate();
				log.debug(rows+" row deleted");				
			}
			catch (SQLException s){
				log.error("SQL statement is not executed! "+s);
			}


			conn.close();
			log.debug("Disconnected from database");
		} catch (Exception e) {
			logException(e);
		}
	}
	
	/**
	 * Updates a Police with the data passed. It uses the ID to update the details in the database
	 *
	 * @param aCtPolice The Police to update
	 * @return the pt boolean
	 */
	static public PtBoolean updatePolice(CtPolice aCtPolice){
		PtBoolean success = new PtBoolean(false);
		try {
			conn = DriverManager.getConnection(url+dbName,userName,password);
			log.debug("Connected to the database");

			/********************/
			//edit
			
			try{
				Statement st = conn.createStatement();
				String id = aCtPolice.id.value.getValue();
				String login =  aCtPolice.login.value.getValue();
				String pwd =  aCtPolice.pwd.value.getValue();
				String statement = "UPDATE "+ dbName+ ".polices" +
						" SET pwd='"+pwd+"',  login='"+ login+"' " +
						"WHERE id='"+id+"'";
				int val = st.executeUpdate(statement);
				log.debug(val+" row updated");
				success = new PtBoolean(val == 1);
			}
			catch (SQLException s){
				log.error("SQL statement is not executed! "+s);
			}
			conn.close();
			log.debug("Disconnected from database");
		} catch (Exception e) {
			logException(e);
		}
		return success;
	}
	
	/**
	 * Gets a list of all Police from the database. It's stored in a hashtable of the ID and the Police class
	 *
	 * @return Return a hashtable of Police ID's and their class
	 */
	static public Hashtable<String, CtPolice> getSystemPolices(){
		Hashtable<String, CtPolice> cmpSystemCtPoli = new Hashtable<String, CtPolice>();

		try {
			conn = DriverManager
					.getConnection(url + dbName, userName, password);
			log.debug("Connected to the database");

			/********************/
			//Select

			try {
				String sql = "SELECT * FROM " + dbName + ".polices ";

				PreparedStatement statement = conn.prepareStatement(sql);
				ResultSet res = statement.executeQuery(sql);

				CtPolice aCtPoli = null;

				while (res.next()) {

					aCtPoli = new CtPolice();
					//alert's id
					DtPoliceID aId = new DtPoliceID(new PtString(
							res.getString("id")));
					DtLogin aLogin = new DtLogin(new PtString(res.getString("login")));
					DtPassword aPwd = new DtPassword(new PtString(res.getString("pwd")));
					//init aCtAlert instance
					aCtPoli.init(aId, aLogin, aPwd);
					
					//add instance to the hash
					cmpSystemCtPoli
							.put(aCtPoli.id.value.getValue(), aCtPoli);
				}
			} catch (SQLException s) {
				log.error("SQL statement is not executed! " + s);
			}
			conn.close();
			log.debug("Disconnected from database");

		} catch (Exception e) {
			logException(e);
		}

		return cmpSystemCtPoli;
	}
	

}
