package org.eclipse.scanning.test.event.queues.beanmodels;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.scanning.api.event.queues.models.MoveAtomModel;
import org.eclipse.scanning.api.event.queues.models.SubTaskAtomModel;
import org.eclipse.scanning.api.event.queues.models.arguments.Arg;
import org.eclipse.scanning.api.event.queues.models.arguments.ArrayArg;
import org.eclipse.scanning.api.event.queues.models.arguments.LookupArg;
import org.junit.Before;
import org.junit.Test;

public class SubTaskAtomModelTest {
	
	private MoveAtomModel moveModelA, moveModelB, moveModelC;
	
	@Before
	public void setUp() {
		moveModelA = new MoveAtomModel("moveBender", "xtalBend");
		moveModelB = new MoveAtomModel("moveAndPrePosition", "xtalY", "xtalBragg", "xtalRoll", "xtalYaw");
		moveModelC = new MoveAtomModel("moveAndPrePosition", "xtalBragg", "xtalRoll", "xtalYaw");
	}
	
	/*
	 * This is all based around the xtalSelect call in pe.py (simplified form):
	 * def xtalSelect(xtal):
	 * 		xtalBendOut = 1.5
	 * 		pos xtalBend xtalBendOut
* 		pos xtalY xtalPosns[xtal][0] xtalBragg xtalPosns[xtal][1] xtalRoll xtalPosns[xtal][2] xtalYaw xtalPosns[xtal][3]
* 		pos                          xtalBragg xtalPosns[xtal][1] xtalRoll xtalPosns[xtal][2] xtalYaw xtalPosns[xtal][3]
* 		if xtalPosns[xtal][4] > xtalBendOut:
* 			pos xtalBend xtalPosns[xtal][4]
	 */
	
	@Test
	public void testProgrammaticDefine() {
		//Simple one atom, no arg SubTask
		SubTaskAtomModel subTaskModelA = new SubTaskAtomModel("xtalBend");
		subTaskModelA.addAtom(moveModelA, new Arg(1.5));
		asserEquals("Expected exactly one atom in this SubTaskAtomModel", 1, subTaskModelA.getAtoms().size());
		assertEquals("Incorrect model found in atom models", moveModelA, subTaskModelA.getAtoms().get(0));
		
		//Mode complicated multi-atom SubTask
		Map<String, Double[]> xtalPosns = new HashMap<>();
		xtalPosns.put("311", new Double[]{0.600, 8.1762,-6.341,0.0,1.7199});
		xtalPosns.put("220", new Double[]{-49.000, 6.7675,-5.292,0.0,1.6294});
		xtalPosns.put("111", new Double[]{50.048, 8.7988,-5.786,0.0,1.7938});
		
		SubTaskAtomModel subTaskModelB = new SubTaskAtomModel("xtalSelect", "xtal");
		subTaskModelB.addArg(new LookupArg(subTaskModelB.getArg("xtal"), xtalPosns));
		subTaskModelB.addAtom(moveModelA, new Arg(1.5));
		subTaskModelB.addAtom(moveModelB, subTaskModelB.getArrayArg("xtalPosns", 4));
		subTaskModelB.addAtom(moveModelC, subTaskModelB.getArrayArg("xtalPosns", 4, 1));
		
	}
	
//	public void Task() {
//		TaskBeanModel taskModel = new TaskBeanModel("Do measure");
//		taskModel.add("xtalSelect", new Arg("311"));
//	}
}
