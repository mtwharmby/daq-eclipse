package org.eclipse.scanning.test.event.queues;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.eclipse.scanning.api.event.EventException;
import org.eclipse.scanning.api.event.core.IConsumer;
import org.eclipse.scanning.api.event.queues.IQueueControllerService;
import org.eclipse.scanning.api.event.queues.IQueueService;
import org.eclipse.scanning.api.event.queues.beans.Queueable;
import org.eclipse.scanning.api.event.status.Status;
import org.eclipse.scanning.event.queues.QueueProcessorFactory;
import org.eclipse.scanning.event.queues.ServicesHolder;
import org.eclipse.scanning.test.event.queues.dummy.DummyAtomProcessor;
import org.eclipse.scanning.test.event.queues.dummy.DummyBean;
import org.eclipse.scanning.test.event.queues.dummy.DummyBeanProcessor;
import org.eclipse.scanning.test.event.queues.util.EventInfrastructureFactoryService;
import org.junit.Before;
import org.junit.Test;

public class QueueServicePluginTest {
	
	private static IQueueService queueService;
	private static IQueueControllerService queueControl;
	private EventInfrastructureFactoryService infrastructureServ;
	private static String qRoot = "fake-queue-root";
	
	private DummyBean dummyBean;
	
	@Before
	public void setup() throws Exception {
		infrastructureServ = new EventInfrastructureFactoryService();
		infrastructureServ.start(false);
		
		queueService = ServicesHolder.getQueueService();
		queueService.setQueueRoot(qRoot);
		queueService.setUri(infrastructureServ.getURI());
		
		queueControl = ServicesHolder.getQueueControllerService();
		
		QueueProcessorFactory.registerProcessor(DummyAtomProcessor.class);
		QueueProcessorFactory.registerProcessor(DummyBeanProcessor.class);
		
		queueService.init();
	}
	
	@Test
	public void testRunningBean() throws EventException {
		dummyBean = new DummyBean("Bob", 50);
		
		String jobQueueID = queueService.getJobQueueID();
		
		queueControl.submit(dummyBean, jobQueueID);
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		IConsumer<Queueable> jobConsumer = queueService.getQueue(jobQueueID).getConsumer();
		List<Queueable> statusSet = jobConsumer.getStatusSet();
		
		for (Queueable bean : statusSet) {
			if (bean.getUniqueId().equals(dummyBean.getUniqueId())) {
				assertTrue(bean.getStatus().isFinal());
				assertEquals(Status.COMPLETE, bean.getStatus());
			}
		}
		
	}

}
