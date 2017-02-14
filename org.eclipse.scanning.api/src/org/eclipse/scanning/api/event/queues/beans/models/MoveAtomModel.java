package org.eclipse.scanning.api.event.queues.beans.models;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.scanning.api.event.queues.beans.MoveAtom;

public class MoveAtomModel extends QueueableModel<MoveAtom> implements IQueueAtomModel<MoveAtom> {
	
	private Map<String, Object> positionerConfig;

	/**
	 * Construct a
	 * @param name
	 * @param referenceName
	 */
	public MoveAtomModel(String referenceName, String... positionerNames) {
		super(referenceName);
		
		int nrPositioners = positionerNames.length;
		positionerConfig = new LinkedHashMap<>(nrPositioners);
		for (String positioner : positionerNames) positionerConfig.put(positioner, null);
	}

	@Override
	public MoveAtom build(String userName, String hostName) {
		String mvName;
		if (name == null) {
			mvName = "Set position of ";
			for (Map.Entry<String, Object> poser : positionerConfig.entrySet()) {
				mvName = mvName+poser.getKey()+" to "+poser.getValue().toString()+", ";
			}
			mvName = mvName.substring(0, mvName.length()-2);
		} else {
			mvName= name;
		}
		
		MoveAtom moveAtom = new MoveAtom(mvName, positionerConfig);
		moveAtom.setHostName(hostName);
		moveAtom.setUserName(userName);
		return moveAtom;
	}
	
	public void configure(Object... targets) {
		if (targets.length != positionerConfig.size()) throw new IllegalArgumentException("Too few arguments supplied to configure model.");
		
		int i = 0;
		for (String posnName : positionerConfig.keySet()) {
			positionerConfig.put(posnName, targets[i]);
			i++;
		}
	}
	
	public List<String> getPositionerNames() {
		return new ArrayList<String>(positionerConfig.keySet());
	}
	
	public Map<String, Object> getPositionerConfig() {
		return positionerConfig;
	}

}
