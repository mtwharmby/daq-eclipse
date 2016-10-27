package org.eclipse.scanning.test.event.queues.processors;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.eclipse.scanning.api.event.EventException;
import org.eclipse.scanning.api.event.IEventService;
import org.eclipse.scanning.api.event.bean.BeanEvent;
import org.eclipse.scanning.api.event.bean.IBeanListener;
import org.eclipse.scanning.api.event.core.ISubscriber;
import org.eclipse.scanning.api.event.queues.IQueue;
import org.eclipse.scanning.api.event.queues.IQueueControllerService;
import org.eclipse.scanning.api.event.queues.IQueueService;
import org.eclipse.scanning.api.event.queues.beans.QueueBean;
import org.eclipse.scanning.api.event.queues.beans.Queueable;
import org.eclipse.scanning.api.event.queues.beans.SubTaskAtom;
import org.eclipse.scanning.api.event.status.Status;
import org.eclipse.scanning.event.queues.QueueProcessorFactory;
import org.eclipse.scanning.event.queues.ServicesHolder;
import org.eclipse.scanning.event.queues.processors.SubTaskAtomProcessor;
import org.eclipse.scanning.test.BrokerTest;
import org.eclipse.scanning.test.event.queues.dummy.DummyAtom;
import org.eclipse.scanning.test.event.queues.dummy.DummyAtomProcessor;
import org.eclipse.scanning.test.event.queues.dummy.DummyBeanProcessor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SubTaskAtomProcessorIntegrationPluginTest extends BrokerTest {
	
	protected static IQueueService queueService;
	protected static IQueueControllerService queueControl;
	private String qRoot = "fake-queue-root";
	
	private ProcessorTestInfrastructure pti;
	private SubTaskAtomProcessor stAtProcr;
	
	@Before
	public void setup() throws Exception {
		pti = new ProcessorTestInfrastructure();
		
		/*
		 * This section is the same as the QueueServiceIntegrationTest
		 */
		//FOR TESTS ONLY
		QueueProcessorFactory.registerProcessor(DummyAtomProcessor.class);
		QueueProcessorFactory.registerProcessor(DummyBeanProcessor.class);
		
		//Configure the queue service
		queueService = ServicesHolder.getQueueService();
		queueService.setUri(uri);
		queueService.setQueueRoot(qRoot);
		queueService.init();
		
		//Configure the queue controller service
		queueControl = ServicesHolder.getQueueControllerService();
		queueControl.init();
		
		//Above here - spring will make the calls
		queueControl.startQueueService();
	}
	
	@After
	public void tearDown() throws EventException {
		queueControl.stopQueueService(false);
	}
	
	@Test
	public void testSubTaskAtom() throws Exception {
		stAtProcr = new SubTaskAtomProcessor();
		
		//Create the beans to be processed
		SubTaskAtom subTask = new SubTaskAtom();
		subTask.setName("Test SubTask");
		
		DummyAtom dummyA = new DummyAtom("Gregor", 70);
		subTask.addAtom(dummyA);
		
		//Execute the processor & wait for it to complete
		pti.executeProcessor(stAtProcr, subTask);
		waitForBeanFinalStatus(subTask, queueService.getJobQueueID());//FIXME Put this on the QueueController
			
		//Check the bean state and that it's the right bean
		//(can't check the number of beans in the StatusSet MockPublisher records ALL broadcasts) 
		List<Queueable> statusSet = pti.getBroadcastBeans();
		assertEquals(Status.COMPLETE, statusSet.get(0).getStatus());
		assertEquals(subTask.getUniqueId(), statusSet.get(0).getUniqueId());
	}
	
	/**
	 * Same as below, but does not check for final state and waits for 10s
	 */
	private void waitForBeanStatus(Queueable bean, Status state, String queueID) throws EventException, InterruptedException {
		waitForBeanStatus(bean, state, queueID, false, 10000);
	}
	
	/**
	 * Same as below, but checks for isFinal and waits 10s
	 */
	private void waitForBeanFinalStatus(Queueable bean, String queueID) throws EventException, InterruptedException {
		waitForBeanStatus(bean, null, queueID, true, 10000);
	}
	
	/**
	 * Timeout is in ms
	 */
	private void waitForBeanStatus(Queueable bean, Status state, String queueID, boolean isFinal, long timeout) 
			throws EventException, InterruptedException {
		List<Queueable> broadBeans = pti.getBroadcastBeans();
		boolean waitingForStatus = true;
		while (waitingForStatus) {
			Thread.sleep(50);
			timeout = timeout-50;
			for (Queueable broadcast : broadBeans) {
				if ((broadcast.getStatus() == state) || (isFinal && broadcast.getStatus().isFinal())) {
					waitingForStatus = false;
					break;
				}
			}
			//Check whether the sands of time have beaten our bean
			if (timeout == 0) break;
		}
		
		if (timeout == 0) {
			System.out.println("#########################\n No final state reported\n#########################");
		} else {
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~\n Final state reached\n~~~~~~~~~~~~~~~~~~~~~~~~~");
		}
	}

}