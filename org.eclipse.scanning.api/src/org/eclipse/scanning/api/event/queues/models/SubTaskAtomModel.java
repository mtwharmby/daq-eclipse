package org.eclipse.scanning.api.event.queues.models;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.scanning.api.event.queues.beans.QueueAtom;
import org.eclipse.scanning.api.event.queues.beans.SubTaskAtom;
import org.eclipse.scanning.api.event.queues.models.arguments.IArg;

public class SubTaskAtomModel extends QueueableModel<SubTaskAtom> implements IQueueAtomModel<SubTaskAtom> {

	private final Map<String, IQueueAtomModel<? extends QueueAtom>> atoms;
	
	protected SubTaskAtomModel(String referenceName, String name) {
		super(referenceName, name);
		atoms = new LinkedHashMap<>();
	}
	
	public void addAtom(IQueueAtomModel<?> atom, IArg<?>... modelArgs) {
		atoms.
	}
	
	public Map<String, IQueueAtomModel<? extends QueueAtom>> getAtoms() {
		return atoms;
	}

	@Override
	protected SubTaskAtom buildBean(String userName, String hostName) {
		// TODO Auto-generated method stub
		return null;
	}

}
