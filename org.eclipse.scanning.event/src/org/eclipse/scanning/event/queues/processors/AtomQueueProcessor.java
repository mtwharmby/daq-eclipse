package org.eclipse.scanning.event.queues.processors;

import java.util.ListIterator;

import org.eclipse.scanning.api.event.EventException;
import org.eclipse.scanning.api.event.bean.IBeanListener;
import org.eclipse.scanning.api.event.core.IConsumerProcess;
import org.eclipse.scanning.api.event.core.IPublisher;
import org.eclipse.scanning.api.event.core.ISubscriber;
import org.eclipse.scanning.api.event.queues.IQueueProcessor;
import org.eclipse.scanning.api.event.queues.IQueueService;
import org.eclipse.scanning.api.event.queues.beans.IAtomBeanWithQueue;
import org.eclipse.scanning.api.event.queues.beans.IAtomQueue;
import org.eclipse.scanning.api.event.queues.beans.QueueAtom;
import org.eclipse.scanning.api.event.queues.beans.Queueable;
import org.eclipse.scanning.api.event.status.Status;
import org.eclipse.scanning.event.queues.QueueServicesHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Generic class for processing a {@link Queueable} composed of an 
 * {@link IAtomQueue}. The processor spools the atoms in the contained queue 
 * into a new queue created through the {@link IQueueService}. The new queue is
 * monitored using the {@link QueueListener} and through the queue service.
 * 
 * TODO Implement class!!!
 * TODO Rehash java-doc once implemented
 * TODO Add test of wrong bean type before cast.
 * 
 * @author Michael Wharmby
 *
 * @param <T> Bean implementing {@link Queueable}, but must be an 
 *            {@link IAtomBeanWithQueue}.
 */
public class AtomQueueProcessor implements IQueueProcessor {
	
	private static Logger logger = LoggerFactory.getLogger(AtomQueueProcessor.class);

	@Override
	public <T extends Queueable> IConsumerProcess<T> makeProcess(T bean,
			IPublisher<T> publisher, boolean blocking) throws EventException {
		return new AtomQueueProcess<T, QueueAtom>(bean, publisher, blocking);
	}

	class AtomQueueProcess <T extends Queueable, U extends QueueAtom> extends AbstractQueueProcessor<T> {

		private final IQueueService queueService;
		private ISubscriber<IBeanListener<U>> queueSubscriber;
		private String childQueueName;

		private IAtomBeanWithQueue<U> atom;
		
		@SuppressWarnings("unchecked")//We check the atom before doing the cast.
		public AtomQueueProcess(T bean, IPublisher<T> publisher, boolean blocking) throws EventException {
			super(bean, publisher);
			this.blocking = blocking;
			
			//We know the bean implements IAtomBeanWithQueue as this processor 
			//wouldn't get called otherwise
			if (bean instanceof IAtomBeanWithQueue) {
				atom = (IAtomBeanWithQueue<U>) bean;
			} else {
				bean.setMessage("Bean does not have a queue.");
				broadcast(bean, Status.FAILED);
				throw new EventException("Given bean does not have a queue to operate on (not an instance of IAtomBeanWithQueue).");
			}
			
			queueService = QueueServicesHolder.getQueueService();
		}

		@Override
		public void execute() throws EventException {
			//This is the percentage of the ScanAtom given over to config. 
			final double beanConfigPercent = 5d;
			
			broadcast(bean, Status.RUNNING);
			
			//Create new queue
			bean.setMessage("Creating new active queue.");
			broadcast(bean, Status.RUNNING);
			childQueueName = queueService.registerNewActiveQueue();
			broadcast(bean, beanConfigPercent/5);
			
			//Spool atoms into the new queue
			try {
				bean.setMessage("Spooling AtomQueue to active-queue "+childQueueName);
				broadcast(bean, Status.RUNNING);
				spoolAtomQueue();
				broadcast(bean, beanConfigPercent*4/5);
			} catch (EventException e) {
				logger.error("Failed to spool atoms from "+bean.getName()+" into "+childQueueName+" queue.");
				bean.setMessage("Failed to spool AtomQueue into "+childQueueName);
				broadcast(bean, Status.FAILED);
				throw new EventException(e);
			}
			
			//Add monitor to queue
			queueSubscriber = queueService.getQueueSubscriber(childQueueName);
			//TODO Change uniqueID arg to be list of beanIDs
			queueSubscriber.addListener(new QueueListener<U, T>(bean, this, bean.getUniqueId(), beanConfigPercent));
			broadcast(bean, beanConfigPercent*9/10);
			
			//Start the new queue
			bean.setMessage("Starting active queue");
			broadcast(bean, Status.RUNNING);
			queueService.startActiveQueue(childQueueName);
			broadcast(bean, beanConfigPercent);
			
			//When queue complete, cleanup.
			while(!runComplete) {
				try {
					Thread.sleep(loopSleepTime);
				} catch(InterruptedException e) {
					throw new EventException(e);
				}

				if (runComplete) {
					queueSubscriber.disconnect();
					break;
				}
				
				if (terminated) {
					System.out.println("Hahahaha");
				}
			}

		}

		@Override
		public void terminate() throws EventException {
			// TODO Auto-generated method stub

		}
		
		private void spoolAtomQueue() throws EventException {
			ListIterator<U> atomIterator = atom.queue().getQueueIterator();
			
			while (atomIterator.hasNext()) {
				//Get the atoms in the queue and set some fields.
				U queuedAtom = atomIterator.next();
				
				queueService.activeQueueSubmit(queuedAtom, childQueueName);
			}
		}

	}

}
