package org.eclipse.scanning.api.event.queues.models.arguments;

/**
 * Basic model argument which holds a single object. Typically this would be a 
 * String, Double, Integer or another simple type. 
 * 
 * @author Michael Wharmby
 *
 * @param <V> Type of the value held by this argument.
 */
public class Arg<V> implements IArg<V> {
	
	protected V parameter,value;
	
	/**
	 * Construct a new argument with a given parameter.
	 * 
	 * @param parameter value V to be stored in this argument.
	 */
	public Arg(V parameter) {
		this.parameter = parameter;
	}
	
	/**
	 * Sets the value equal to the parameter.
	 */
	@Override
	public void evaluate() {
		value = parameter;
	}
	
	/**
	 * Return the parameter. This field is populated prior to evaluate() call.
	 * 
	 * @return V representing the configured parameter of this object.
	 */
	public V getParameter() {
		return parameter;
	}
	
	@Override
	public V getValue() {
		return value;
	}
	

	@Override
	public String toString() {
		return "Arg [value=" + value + ", parameter=" + parameter + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((parameter == null) ? 0 : parameter.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Arg<?> other = (Arg<?>) obj;
		if (parameter == null) {
			if (other.parameter != null)
				return false;
		} else if (!parameter.equals(other.parameter))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

}
