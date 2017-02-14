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
	
	private Map<String, Object> positionConfig;
	
	/**
	 * No arg constructor for JSON
	 */
	public MoveAtom() {
		super();
	}
	
	/**
	 * Basic constructor, allowing MoveAtom to be named. Positioner parameters 
	 * need to be added later by the user.
	 * 
	 * @param mvName String automatically/user supplied name for this move.
	 */
	public MoveAtom(String mvName) {
		super();
		setName(mvName);
		
		positionConfig = new LinkedHashMap<String, Object>();
	}
	
	/**
	 * Constructor with required arguments to configure one motor position.
	 * 
	 * @param mvName String automatically/user supplied name for this move. 
	 * @param moveDev String name of motor to move.
	 * @param tgt Object target to move motor to.
	 * @param runTime long Duration of this move in ms.

	 */
	public MoveAtom(String mvName, String moveDev, Object tgt) {
		super();
		setName(mvName);
		
		positionConfig = new LinkedHashMap<String, Object>();
		positionConfig.put(moveDev, tgt);
	}
	
	/**
	 * Constructor with required arguments for multiple motor positions.
	 * 
	 * @param mvName String automatically/user supplied name for this move.
	 * @param config Map of form String motor name Object target position
	 * @param runTime
	 */
	public MoveAtom(String mvName, Map<String, Object> config) {
		super();
		setName(mvName);
		positionConfig = config;
	}
	
	/**
	 * Return all the names of the motors controlled by this MoveAtom.
	 * 
	 * @return List of String names of the motors in the configuration.
	 */
	public List<String> getPositionerNames() {
		return new ArrayList<String>(positionConfig.keySet());
	}
	
	/**
	 * Return the tgt to which this motor will be moved to.
	 * 
	 * @param moveDev String name of motor to move.
	 * @return Object representing the target move position.
	 */
	public Object getPositioner(String moveDev) {
		return positionConfig.get(moveDev);
	}
	
	/**
	 * Change or add a new motor to be moved by this MoveAtom.
	 * 
	 * @param moveDev String name of motor to move.
	 * @param tgt Object target to move motor to.
	 */
	public void addPositioner(String moveDev, Object tgt) {
		positionConfig.put(moveDev, tgt);
	}
	
	/**
	 * Remove a motor from the configuration of this MoveAtom.
	 * 
	 * @param moveDev String name of motor to move.
	 */
	public void removePositioner(String moveDev) {
		positionConfig.remove(moveDev);
	}
	
	/**
	 * Return complete set of motor names and target positions.
	 * 
	 * @return Map<String, Object> String key name of motor and Object target.
	 */
	public Map<String, Object> getPositionConfig() {
		return positionConfig;
	}

	/**
	 * Change the complete set of motor names and target positions.
	 * 
	 * @param positionConfig Map<String, Object> String key name of motor and 
	 *                       Object target.
	 */
	public void setPositionConfig(Map<String, Object> positionConfig) {
		this.positionConfig = positionConfig;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((positionConfig == null) ? 0 : positionConfig.hashCode());
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
		if (positionConfig == null) {
			if (other.positionConfig != null)
				return false;
		} else if (!positionConfig.equals(other.positionConfig))
			return false;
		if (runTime != other.runTime)
			return false;
		return true;
	}

}
