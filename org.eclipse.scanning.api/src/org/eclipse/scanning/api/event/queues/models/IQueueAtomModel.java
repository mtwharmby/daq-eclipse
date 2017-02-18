package org.eclipse.scanning.api.event.queues.models;

import org.eclipse.scanning.api.event.queues.beans.QueueAtom;
import org.eclipse.scanning.api.event.queues.beans.Queueable;

/**
 * Interface for all models representing {@link QueueAtom}s. This is a marker 
 * interface to control which {@link Queueable} types can be included in which 
 * register of the QueueBeanFactory.
 * 
 * @author Michael Wharmby
 *
 * @param <T> Type extending {@link QueueAtom}
 */
public interface IQueueAtomModel<T extends QueueAtom> extends IQueueableModel<T> {

}
