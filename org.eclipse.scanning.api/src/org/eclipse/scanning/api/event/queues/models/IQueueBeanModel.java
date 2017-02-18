package org.eclipse.scanning.api.event.queues.models;

import org.eclipse.scanning.api.event.queues.beans.QueueBean;
import org.eclipse.scanning.api.event.queues.beans.Queueable;

/**
 * Interface for all models representing {@link QueueBean}s. This is a marker 
 * interface to control which {@link Queueable} types can be included in which 
 * register of the QueueBeanFactory.
 * 
 * @author Michael Wharmby
 *
 * @param <T> Type extending {@link QueueBean}.
 */
public interface IQueueBeanModel<T extends QueueBean> extends IQueueableModel<T> {

}
