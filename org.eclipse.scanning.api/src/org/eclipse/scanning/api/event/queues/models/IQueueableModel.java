package org.eclipse.scanning.api.event.queues.models;

import java.util.List;
import java.util.Map;

import org.eclipse.scanning.api.event.queues.beans.Queueable;
import org.eclipse.scanning.api.event.queues.models.arguments.ArrayArg;
import org.eclipse.scanning.api.event.queues.models.arguments.IArg;

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
	 * Returns the user friendly name of the IQueueableModel instance.
	 * 
	 * @return String user-friendly name.
	 */
	public String getName();
	
	/**
	 * Changes the user friendly name of the IQueueableModel instance.
	 */
	public void setName(String name);
	
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
	 * Return a mapping of {@link IArg} arguments and their String names.
	 * 
	 * @return Map<String, IArg<?>> names and evaluateable {@link IArg}s.
	 */
	public Map<String, IArg<?>> getArgs();
	
	/**
	 * Return the evaluateable {@link IArg} for a given argument name.
	 * 
	 * @param argName String name of argument.
	 * @return {@link IArg} which holds the value of the argument.
	 */
	public IArg<?> getArg(String argName);
	
	/**
	 * Return a List of all names of arguments set on this IQueueableModel.
	 * 
	 * @return List<String> of argument names.
	 */
	public List<String> getArgNames();
	
	/**
	 * Return the number of arguments set on this IQueueableModel.
	 * 
	 * @return int number of arguments.
	 */
	public int getNumberOfArgs();
	
	/**
	 * Set the {@link IArg}s for the argument names. Number of arguments 
	 * expected is variable, but must be equal to the number of arguments 
	 * already set on this IQueueableModel. 
	 * 
	 * @param args variable number of {@link IArgs} corresponding to the 
	 *             number of arguments set on this instance.
	 */
	public void configure(IArg<?>... args);
	
	/**
	 * Set the values for the argument names from a single array supplied by 
	 * an {@link ArrayArg}. The size of the array must be equal to the number 
	 * of arguments set on this IQueueableModel. 
	 * 
	 * @param array {@link ArrayArg} supplying array of values for all 
	 *              arguments.
	 * @param size int number of values in the array.
	 */
	public void configure(ArrayArg<?,?> array, int size);
	
	/**
	 * Set the values for the argument names from a slice of an array supplied
	 * by an {@link ArrayArg}. The value of (stop-start+1) must be equal to the 
	 * number of arguments set on this IQueueableModel. 
	 * 
	 * @param array {@link ArrayArg} supplying array of values for all 
	 *              arguments.
	 * @param start int first position in array to read a value from.
	 * @param stop int last position (inclusive) in array to read a value from.
	 */
	public void configure(ArrayArg<?,?> array, int start, int stop);
	
	/**
	 * Determine the values of the arguments configured on this 
	 * IQueueableModel by calling the {@link IArg} evaluate() method.
	 */
	public void evaluateArgs();
	
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
