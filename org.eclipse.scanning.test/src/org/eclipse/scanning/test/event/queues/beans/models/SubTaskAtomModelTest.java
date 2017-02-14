package org.eclipse.scanning.test.event.queues.beans.models;

import org.eclipse.scanning.api.event.queues.beans.models.MoveAtomModel;
import org.eclipse.scanning.api.event.queues.beans.models.SubTaskAtomModel;
import org.junit.Before;
import org.junit.Test;

public class SubTaskAtomModelTest {
	
	private MoveAtomModel moveModelA, moveModelB;
	
	@Before
	public void setUp() {
		moveModelA = new MoveAtomModel("moveBender", "xtalBend");
		moveModelB = new MoveAtomModel("moveAndPrePosition", "xtalY", "xtalBragg", "xtalRoll", "xtalYaw");
	}
	
	@Test
	public void testDecorateModel() {
		SubTaskAtomModel subTaskModelA = new LookupDecorator("xtal", new SubTaskAtomModel("xtalSelect"));
		
		
		SubTaskAtomModel subTaskModelB = new EquationModel("")
	}
	
	@Test
	public void testConfigure() {
		SubTaskAtomModel subTaskModelA = new SubTaskAtomModel("xtalSelect")
		subTaskModelA.addAtom(moveModelA, );
		subTaskModelA.addAtom(moveModelB, );
		subTaskModelA.addAtom(moveModelA, );
	}
	
}
