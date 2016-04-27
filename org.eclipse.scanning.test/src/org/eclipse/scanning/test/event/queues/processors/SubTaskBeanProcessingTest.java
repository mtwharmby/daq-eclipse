package org.eclipse.scanning.test.event.queues.processors;

import org.eclipse.scanning.api.event.queues.beans.IAtomBeanWithQueue;
import org.eclipse.scanning.api.event.queues.beans.QueueAtom;
import org.eclipse.scanning.event.queues.beans.SubTaskBean;

public class SubTaskBeanProcessingTest extends AbstractAtomQueueProcessorTest<SubTaskBean> {

	@Override
	protected IAtomBeanWithQueue<QueueAtom> makeAtomBean() {
		return new SubTaskBean("TestBean");
	}

}
