package org.eclipse.scanning.test.event.queues.processors;

import org.eclipse.scanning.api.event.queues.beans.IAtomBeanWithQueue;
import org.eclipse.scanning.api.event.queues.beans.QueueAtom;
import org.eclipse.scanning.api.event.queues.beans.Queueable;
import org.eclipse.scanning.api.event.status.Status;
import org.eclipse.scanning.event.queues.processors.AtomQueueProcessor;
import org.eclipse.scanning.test.event.queues.mocks.DummyAtom;
import org.junit.Before;
import org.junit.Test;

public abstract class AbstractAtomQueueProcessorTest<T extends Queueable> extends AbstractQueueProcessorTest<T> {
	
	private DummyAtom felix = new DummyAtom("Felix", 100);
	private T aqAt;
	
	@SuppressWarnings("unchecked") //queueHolder must be a child of Queueable
	@Before
	public void setup() {
		IAtomBeanWithQueue<QueueAtom> queueHolder = makeAtomBean();
		queueHolder.queue().add(felix);
		
		aqAt = (T)queueHolder;
		
		processorSetup();
	}
	
	private void processorSetup() {
		proc = new AtomQueueProcessor().makeProcess(aqAt, statPub, true);
	}
		
	@Test
	public void testSimpleExecution() throws Exception {
		doExecute();
		
		/*
		 * Execution of T containing single DummyAtom
		 * - Initial few beans should have percent completes/statuses defined 
		 *   by spooling process, c.f. ScanAtomProcessor
		 * - After run, DummyAtom will be 100% complete and status = COMPLETE
		 * - DummyAtom message & T message should be equal
		 * - First T in statPub should be Status = RUNNING
		 * - T should be 100% complete, Status = COMPLETE
		 * - T queueMessage should be "OK"
		 * - fields of child queue bean should be populated from parent bean
		 */
		
		Status[] reportedStatuses = new Status[]{Status.RUNNING, Status.RUNNING,
				Status.RUNNING, Status.RUNNING, Status.RUNNING};
		Double[] reportedPercent = new Double[]{0d, 0d, 
				1.25d, 2.5d, 5d};
		
		checkBeanStatuses(reportedStatuses, reportedPercent);
		checkBeanFinalStatus(Status.COMPLETE, true);
		
//		//Assert we have a properly structured ScanBean
//		List<DummyAtom> statusSet = scanConsumer.getStatusSet();
//		DummyAtom dummy = statusSet.get(statusSet.size()-1);
//		//Check the properties of the ScanAtom have been correctly passed down
//		assertFalse("No beamline set", dummy.getBeamline() == null);
//		assertEquals("Incorrect beamline", aqAt.getBeamline(), dummy.getBeamline());
//		assertFalse("No hostname set", dummy.getHostName() == null);
//		assertEquals("Incorrect hostname", aqAt.getHostName(), dummy.getHostName());
//		assertFalse("No name set", dummy.getName() == null);
//		assertEquals("Incorrect name", aqAt.getName(), dummy.getName());
//		assertFalse("No username set", dummy.getUserName() == null);
//		assertEquals("Incorrect username", aqAt.getUserName(), dummy.getUserName());
	}
	
//6	@Test
	public void testMultipleExecution() {
		
		/*
		 * Execution of T containing two DummyAtoms
		 * -  Start run & wait for completion of first bean;
		 *    pause execution and check:
		 *    - first DummyAtom is COMPLETE, 100%
		 *    - second DummyAtom is SUBMITTED & 0%
		 *    - T is RUNNING & 52.5% complete 
		 * - After unpause & run to completion:
		 *   - both DummyAtoms COMPLETE & 100%
		 *   - T is COMPLETE & 100%
		 */
	}
	
//2	@Test
	public void testTermination() {
		
		/*
		 * Termination of T containing single DummyAtom
		 * - Start run & call terminate after 1 second
		 * - DummyAtom will be <100% and status TERMINATED
		 * - First T in statPub should be Status = RUNNING
		 * - T will be <100% and status TERMINATED
		 * - DummyAtom message should be ~"Terminated by queue"
		 */
	}
	
//3	@Test
	public void testFailureFromQueue() {
		
		/*
		 * Failure of DummyAtom contained inside T
		 * - Start run & broadcast FAILED after 1 second
		 * - First T in statPub should be Status = RUNNING
		 * - DummyAtom will be <100% and status FAILED
		 * - T will be <100% and status FAILED
		 */
	}
	
//3	@Test
	public void testTerminateFromQueue() {
		
		/*
		 * Termination of DummyAtom contained inside T
		 * - Start run & broadcast REQUEST_TERMINATED after 1 second
		 * - First T in statPub should be Status = RUNNING
		 * - DummyAtom will be <100% and status TERMINATED
		 * - T will be <100% and status TERMINATED
		 */
		
	}
	
	protected abstract IAtomBeanWithQueue<QueueAtom> makeAtomBean();
}

