package org.eclipse.scanning.api.event.queues.models;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.scanning.api.event.queues.beans.MoveAtom;
import org.eclipse.scanning.api.event.queues.models.arguments.IArg;

/**
 * Model class providing a template for the creation of {@link MoveAtom}s. 
 * Targets of positioners are supplied as {@link IArg}s as with other 
 * {@link QueueableModel} classes.
 * 
 * @author Michael Wharmby
 *
 */
public class MoveAtomModel extends QueueableModel<MoveAtom> implements IQueueAtomModel<MoveAtom> {
	
	/**
	 * Construct a new MoveAtomModel with a String name to reference it with 
	 * in models of parent tasks (i.e. {@link SubTaskAtomModel}s). A user 
	 * friendly name can also be supplied by calling setName(). A variable 
	 * number of Strings may also be supplied which corresponds to the 
	 * positioners which will be moved by the resulting MoveAtom. Calls to 
	 * configure(...) must supply the same number of arguments; arguments will 
	 * be associated with positioners in the order they are given here. 
	 * 
	 * @param referenceName String name to refer to this model in parent tasks.
	 * @param positioners String names of positioners to be moved.
	 */
	public MoveAtomModel(String referenceName, String... positioners) {
		super(referenceName, null);
		
		for (String positioner : positioners) {
			args.put(positioner, null);
		}
	}

	@Override
	public MoveAtom buildBean(String userName, String hostName) {
		//If there is no name set, generate one from the configured positioners
		String mvName;
		if (name == null) {
			mvName = "Set position of ";
			for (String poser : args.keySet()) {
				mvName = mvName + poser + " to "+ args.get(poser).getValue() + ", ";
			}
			//When generating, we need to remove the trailing ", ".
			mvName = mvName.substring(0, mvName.length()-2);
		} else {
			mvName = name;
		}
		
		//Transfer the argument values to positioner arguments
		Map<String, Object> positionerConfig = new HashMap<>();
		for (String poser : args.keySet()) {
			positionerConfig.put(poser, args.get(poser).getValue());
		}
		
		//Create new MoveAtom
		MoveAtom moveAtom = new MoveAtom(mvName, positionerConfig);
		moveAtom.setHostName(hostName);
		moveAtom.setUserName(userName);
		return moveAtom;
	}

}
