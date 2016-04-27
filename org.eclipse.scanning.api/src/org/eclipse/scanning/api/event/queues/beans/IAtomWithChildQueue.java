package org.eclipse.scanning.api.event.queues.beans;

import java.util.List;

import org.eclipse.scanning.api.event.status.StatusBean;

/**
 * Interface allowing messages which are specific to the behaviour & operation
 * of the queue to be passed through the queue hierarchy.
 * 
 * TODO Make IQueueable
 * 
 * @author Michael Wharmby
 *
 */
public interface IAtomWithChildQueue<T extends StatusBean> extends IQueueable {
	
	/**
	 * Get the string reporting changes in the child queue, affecting this 
	 * atom/bean.
	 * 
	 * @return String report of child queue state.
	 */
	public String getQueueMessage();
	
	/**
	 * Set the string reporting changes in the child queue, affecting this 
	 * atom/bean.
	 * 
	 * @param String report of child queue state.
	 */
	public void setQueueMessage(String msg);
	
	/**
	 * Return list of beans from child queue in their final, 
	 * post-processing states.
	 * 
	 * @return List of beans extending {@link StatusBean} in their final 
	 *         states.
	 */
	public List<T> getFinalStatusSet();
	
	/**
	 * Change the list of post-processed beans to a new list.
	 * 
	 * @param statusSet New list of beans in final states.
	 */
	public void setFinalStatusSet(List<T> statusSet);
	
	/**
	 * Add a final state bean to the list of post-processed beans.
	 * 
	 * @param finalBeanBean extending {@link StatusBean} to be added to status
	 *                      set.
	 */
	public void addFinalStatusBean(T finalBean);

}
