package org.eclipse.scanning.test.event.queues;

import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.dawnsci.json.MarshallerService;
import org.eclipse.scanning.api.event.queues.IQueueService;
import org.eclipse.scanning.api.event.queues.beans.QueueAtom;
import org.eclipse.scanning.api.event.queues.beans.QueueBean;
import org.eclipse.scanning.event.EventServiceImpl;
import org.eclipse.scanning.event.queues.AtomQueueService;
import org.eclipse.scanning.event.queues.QueueServicesHolder;
import org.eclipse.scanning.test.event.queues.mocks.AllBeanQueueProcessCreator;
import org.junit.Before;
import org.junit.BeforeClass;

import uk.ac.diamond.daq.activemq.connector.ActivemqConnectorService;

/**
 * Test of the concrete implementation {@link AtomQueueService} of the {@link IQueueService}.
 * Tests located in {@link AbstractQueueServiceTest}.
 * @author wnm24546
 *
 */
public class AtomQueueServiceDummyTest extends AbstractQueueServiceTest {
	
	@BeforeClass
	public static void setupClass() throws URISyntaxException {
		qRoot = "uk.ac.diamond.i15-1";
		uri = new URI("vm://localhost?broker.persistent=false");
	}
	
	@Before
	public void createService() throws Exception {
		//Configure AtomQueueService as necessary before setting the test field
		//TODO Change setting of queue service.
		
		ActivemqConnectorService.setJsonMarshaller(new MarshallerService());
		QueueServicesHolder.setEventService(new EventServiceImpl(new ActivemqConnectorService()));
		
		qServ = new AtomQueueService();
		qServ.setQueueRoot(qRoot);
		qServ.setURI(uri);
		
		//Reset the IQueueService generic process creator
		qServ.setJobQueueProcessor(new AllBeanQueueProcessCreator<QueueBean>(true));
		qServ.setActiveQueueProcessor(new AllBeanQueueProcessCreator<QueueAtom>(true));
		
		//All set? Let's go!
		qServ.init();
	}

}
