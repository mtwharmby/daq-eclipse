/*-
 *******************************************************************************
 * Copyright (c) 2011, 2016 Diamond Light Source Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Matthew Gerring - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.scanning.api.event.queues.beans;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * MoveAtom is a type of {@link QueueAtom} which may be processed within an 
 * active-queue of an {@link IQueueService}. It contains all the configuration 
 * necessary to create an {@link IPositioner} which is used to set the 
 * positions of one or more motors. Motor moves may occur simultaneously, 
 * depending on the configured level of the motor.
 * 
 * @author Michael Wharmby
 *
 */
public class MoveAtom extends QueueAtom {
	
	/**
	 * Version ID for serialization. Should be updated when class changed. 
	 */
	private static final long serialVersionUID = 20161021L;
	
	private Map<String, Object> positionerConfig;
	
	/**
	 * No arg constructor for JSON
	 */
	public MoveAtom() {
		super();
	}
	
	/**
	 * Constructor with required arguments to configure one motor position.
	 * 
	 * @param mvName String automatically/user supplied name for this move. 
	 * @param positionDev String name of motor to move.
	 * @param target Object target to move positioner to.
	 */
	public MoveAtom(String mvName, String positionDev, Object target) {
		super();
		setName(mvName);
		
		positionerConfig = new LinkedHashMap<String, Object>();
		positionerConfig.put(positionDev, target);
	}
	
	/**
	 * Constructor with required arguments for multiple motor positions.
	 * 
	 * @param mvName String automatically/user supplied name for this move.
	 * @param positionerConfig Map of form String positioner name Object 
	 *        target position.
	 */
	public MoveAtom(String mvName, Map<String, Object> positionerConfig) {
		super();
		setName(mvName);
		this.positionerConfig = positionerConfig;
	}
	
	/**
	 * Return all the names of the motors controlled by this MoveAtom.
	 * 
	 * @return List of String names of the motors in the configuration.
	 */
	public List<String> getPositionerNames() {
		return new ArrayList<String>(positionerConfig.keySet());
	}
	
	/**
	 * Return the target to which this motor will be moved to.
	 * 
	 * @param positionDev String name of motor to move.
	 * @return Object representing the target move position.
	 */
	public Object getPositioner(String positionDev) {
		return positionerConfig.get(positionDev);
	}
	
	/**
	 * Change or add a new positioner to be moved by this MoveAtom.
	 * 
	 * @param positionDev String name of motor to move.
	 * @param tgt Object target to move motor to.
	 */
	public void putPositioner(String positionDev, Object tgt) {
		positionerConfig.put(positionDev, tgt);
	}
	
	/**
	 * Remove a positioner from the configuration of this MoveAtom.
	 * 
	 * @param positionDev String name of positioner to move.
	 */
	public void remove(String positionDev) {
		positionerConfig.remove(positionDev);
	}
	
	/**
	 * Report the number of positioners whose positions are set by this 
	 * MoveAtom.
	 * 
	 * @return int number of positioners in the configuration.
	 */
	public int size() {
		return positionerConfig.size();
	}

	/**
	 * Return complete set of positioner names and target positions.
	 * 
	 * @return Map<String, Object> String key name of motor and Object target.
	 */
	public Map<String, Object> getPositionerConfig() {
		return positionerConfig;
	}

	/**
	 * Change the complete set of positioner names and target positions.
	 * 
	 * @param positionerConfig Map<String, Object> String key name of motor and 
	 *                       Object target.
	 */
	public void setPositionerConfig(Map<String, Object> positionerConfig) {
		this.positionerConfig = positionerConfig;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((positionerConfig == null) ? 0 : positionerConfig.hashCode());
		result = prime * result + (int) (runTime ^ (runTime >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		MoveAtom other = (MoveAtom) obj;
		if (positionerConfig == null) {
			if (other.positionerConfig != null)
				return false;
		} else if (!positionerConfig.equals(other.positionerConfig))
			return false;
		if (runTime != other.runTime)
			return false;
		return true;
	}

}
