package org.eclipse.scanning.test.event.queues.beanmodels;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.scanning.api.event.queues.beans.MoveAtom;
import org.eclipse.scanning.api.event.queues.models.MoveAtomModel;
import org.eclipse.scanning.api.event.queues.models.arguments.Arg;
import org.eclipse.scanning.api.event.queues.models.arguments.ArrayArg;
import org.eclipse.scanning.api.event.queues.models.arguments.IArg;
import org.junit.Test;

public class MoveAtomModelTest {
	
	@Test
	public void testDefine() {
		//Define a single positioner
		MoveAtomModel moveModelA = new MoveAtomModel("moveBender", "xtalBend");
		assertEquals("Expected only one positioner in this MoveAtomModel", 1, moveModelA.getArgNames().size());
		assertEquals("Incorrect positioner in MoveAtomModel", "xtalBend", moveModelA.getArgNames().get(0));

		//Define multiple positioners
		MoveAtomModel moveModelB = new MoveAtomModel("moveAndPrePosition", "xtalY", "xtalBragg", "xtalRoll", "xtalYaw");
		List<String> positioners = new ArrayList<>();
		positioners.add("xtalY");
		positioners.add("xtalBragg");
		positioners.add("xtalRoll");
		positioners.add("xtalYaw");
		assertEquals("Configured positioners in MoveAtomModel differ", positioners, moveModelB.getArgNames());
	}
	
	@Test
	public void testConfigure() {
		//Configuring a single positioner
		MoveAtomModel moveModelA = new MoveAtomModel("moveBender", "xtalBend");
		
		//Test configuring by order
		IArg<Double> arg = new Arg<>(1.5);
		moveModelA.configure(arg);
		assertEquals("xtalBend config incorrect", arg, moveModelA.getArgs().get("xtalBend"));
		
		//Configure multiple positioners
		MoveAtomModel moveModelB = new MoveAtomModel("moveAndPrePosition", "xtalY", "xtalBragg", "xtalRoll", "xtalYaw");
		ArrayArg argArray = new ArrayArg(new Arg(new Double[]{50.048, 8.7988, -5.786, 0.0}));
		moveModelB.configure(argArray, 4);
		Map<String, IArg<?>> modelBConfig = moveModelB.getArgs();
		assertEquals(argArray.setIndex(0), modelBConfig.get("xtalY"));
		assertEquals(argArray.setIndex(1), modelBConfig.get("xtalBragg"));
		assertEquals(argArray.setIndex(2), modelBConfig.get("xtalRoll"));
		assertEquals(argArray.setIndex(3), modelBConfig.get("xtalYaw"));
	}
	
	@Test
	public void testBuild() {
		//Create a one positioner MoveAtom
		MoveAtomModel moveModelA = new MoveAtomModel("moveBender", "xtalBend");
		IArg<Double> arg = new Arg<>(1.5);
		moveModelA.configure(arg);
		MoveAtom builtA = moveModelA.build("abc12345", "localhost");
		
		MoveAtom mvAtA = new MoveAtom("Set position of xtalBend to 1.5", "xtalBend", 1.5);
		mvAtA.setUserName("abc12345");
		mvAtA.setHostName("localhost");
		assertEquals("Built MoveAtom wrong", mvAtA, builtA);
		
		//Create a multi-positioner MoveAtom
		MoveAtomModel moveModelB = new MoveAtomModel("moveAndPrePosition", "xtalY", "xtalBragg", "xtalRoll", "xtalYaw");
		ArrayArg argArray = new ArrayArg(new Arg(new Double[]{50.048, 8.7988, -5.786, 0.0}));
		moveModelB.configure(argArray, 4);
		MoveAtom builtB = moveModelB.build("abc12345", "localhost");
		
		Map<String, Object> posConf = new HashMap<>();
		posConf.put("xtalY", 50.048);
		posConf.put("xtalBragg", 8.7988);
		posConf.put("xtalRoll", -5.786);
		posConf.put("xtalYaw", 0.0);
		MoveAtom mvAtB = new MoveAtom("Set position of xtalY to 50.048, xtalBragg to 8.7988, xtalRoll to -5.786, xtalYaw to 0.0", posConf);
		mvAtB.setUserName("abc12345");
		mvAtB.setHostName("localhost");
		assertEquals("Built MoveAtom wrong", mvAtB, builtB);
	}

}
