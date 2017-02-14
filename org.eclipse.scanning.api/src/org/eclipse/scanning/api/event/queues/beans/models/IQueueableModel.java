package org.eclipse.scanning.api.event.queues.beans.models;

import org.eclipse.scanning.api.event.queues.beans.Queueable;

/**
 * Base interface for all models to be used within the QueueBeanFactory. 
 * Implementation of this interface should be sufficient to include a Bean/Atom
 * model in the respective registers.
 * 
 * @author Michael Wharmby
 *
 * @param <T> Type extending {@link Queueable}
 */
public interface IQueueableModel<T extends Queueable> {
	
	/**
	 * Returns the user friendly name of the IQueueableModel.
	 * 
	 * @return String user-friendly name.
	 */
	public String getName();
	
	/**
	 * Returns a human-readable, unique name (c.f. a variable) which can be 
	 * used to include instances of IQueueableModel inside AtomQueueModels.
	 * 
	 * @return String human readable variable name.
	 */
	public String getReferenceName();
	
	/**
	 * Set the time in ms necessary for the step(s) described in this 
	 * {@link Queueable} to complete successfully.
	 * 
	 * @return long Time in ms for action to complete.
	 */
	public Long getRunTime();

	/**
	 * Return the time in ms necessary for the step(s) described in this 
	 * {@link Queueable} to complete successfully.
	 * 
	 * @param runTime long number of ms for action to complete.
	 */
	public void setRunTime(Long runTime);
	
	/**
	 * Call the constructor for the {@link Queueable} of the parameterised 
	 * type (T). This should return a completely configured instance of the 
	 * {@link Queueable}.
	 * 
	 * @param userName Identifier for the user building the bean.
	 * @param hostName Host where the bean was created/submitted.
	 * @return T fully configured instance.
	 */
	public T build(String userName, String hostName);
	
}
