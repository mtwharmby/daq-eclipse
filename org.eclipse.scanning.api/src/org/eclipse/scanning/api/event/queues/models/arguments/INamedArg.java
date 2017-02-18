package org.eclipse.scanning.api.event.queues.models.arguments;

/**
 * An {@link IArg} with a string name associated with it.
 * 
 * @author Michael Wharmby
 *
 * @param <V> Type of the value held by this argument.
 */
public interface INamedArg<V> extends IArg<V> {
	
	/**
	 * Return the name of this object
	 * @return String name of {@link IArg}
	 */
	public String getName();

}
