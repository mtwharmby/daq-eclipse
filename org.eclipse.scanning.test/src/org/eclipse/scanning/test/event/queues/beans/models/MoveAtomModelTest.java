package org.eclipse.scanning.test.event.queues.beans.models;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.scanning.api.event.queues.beans.MoveAtom;
import org.eclipse.scanning.api.event.queues.beans.models.MoveAtomModel;
import org.junit.Test;

public class MoveAtomModelTest {
	
	@Test
	public void testConfigure() {
		//Configuring a single positioner
		MoveAtomModel moveModelA = new MoveAtomModel("moveBender", "xtalBend");
		assertEquals("Expected only one positioner in this MoveAtomModel", 1, moveModelA.getPositionerNames().size());
		assertEquals("Incorrect positioner in MoveAtomModel", "xtalBend", moveModelA.getPositionerNames().get(0));
		
		moveModelA.configure(1.5);
		assertEquals("xtalBend config incorrect", 1.5, moveModelA.getPositionerConfig().get("xtalBend"));
		
		//Configuring multiple positioners
		MoveAtomModel moveModelB = new MoveAtomModel("moveAndPrePosition", "xtalY", "xtalBragg", "xtalRoll", "xtalYaw");
		List<String> positioners = new ArrayList<>();
		positioners.add("xtalY");
		positioners.add("xtalBragg");
		positioners.add("xtalRoll");
		positioners.add("xtalYaw");
		assertEquals("Configured positioners in MoveAtomModel differ", positioners, moveModelB.getPositionerNames());
		
		moveModelB.configure(50.048, 8.7988, -5.786, 0.0);
		Map<String, Object> modelBConfig = moveModelB.getPositionerConfig();
		assertEquals(50.048, modelBConfig.get("xtalY"));
		assertEquals(8.7988, modelBConfig.get("xtalBragg"));
		assertEquals(-5.786, modelBConfig.get("xtalRoll"));
		assertEquals(0.0, modelBConfig.get("xtalYaw"));
	}
	
	@Test
	public void testBuild() {
		MoveAtomModel moveModelA = new MoveAtomModel("moveBender", "xtalBend");
		moveModelA.configure(1.5);
		MoveAtom builtAtomA = moveModelA.build("abc12345", "control.diamond.ac.uk");
		
		MoveAtom mvAtA = new MoveAtom("Set position of xtalBend to 1.5", "xtalBend", 1.5);
		mvAtA.setUserName("abc12345");
		mvAtA.setHostName("control.diamond.ac.uk");
		assertEquals(mvAtA, builtAtomA);
		
		MoveAtomModel moveModelB = new MoveAtomModel("moveAndPrePosition", "xtalY", "xtalBragg", "xtalRoll", "xtalYaw");
		moveModelB.configure(50.048, 8.7988, -5.786, 0.0);
		MoveAtom builtAtomB = moveModelB.build("abc12345", "control.diamond.ac.uk");
		
		MoveAtom mvAtB = new MoveAtom("Set position of xtalY to 50.048, xtalBragg to 8.7988, xtalRoll to -5.786, xtalYaw to 0.0");
		mvAtB.addPositioner("xtalY", 50.048);
		mvAtB.addPositioner("xtalBragg", 8.7988);
		mvAtB.addPositioner("xtalRoll", -5.786);
		mvAtB.addPositioner("xtalYaw", 0.0);
		mvAtB.setUserName("abc12345");
		mvAtB.setHostName("control.diamond.ac.uk");
		assertEquals(mvAtB, builtAtomB);
	}

}
