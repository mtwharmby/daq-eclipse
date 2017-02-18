package org.eclipse.scanning.api.event.queues.models.arguments;

/**
 * Model argument with a name which holds a single object. Typically this 
 * would be a String, Double, Integer or another simple type. 
 * 
 * @author Michael Wharmby
 *
 * @param <V> Type of the value held by this argument.
 */
public class NamedArg<V> extends Arg<V> implements INamedArg<V> {
	
	private final String name;

	/**
	 * Construct a new argument with a name and a parameter.
	 * 
	 * @param name String name of argument.
	 * @param parameter value V to be stored in this argument.
	 */
	public NamedArg(String name, V parameter) {
		super(parameter);
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "NamedArg [name=" + name + ", value=" + value + ", parameter=" + parameter + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		NamedArg<?> other = (NamedArg<?>) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
