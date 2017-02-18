package org.eclipse.scanning.api.event.queues.models.arguments;

/**
 * Abstract class for the decorator pattern. It allows {@link IArgs} to be 
 * augmented with additional data/tables from which to find their values.
 * 
 * @author Michael Wharmby
 *
 * @param <P> Type of input parameter.
 * @param <V> Type of output value.
 */
public abstract class ArgDecorator<P, V> implements INamedArg<V> {
	
	protected IArg<P> childArg;
	protected V value;
	protected String name;
	
	protected ArgDecorator(IArg<P> childArg) {
		this.childArg = childArg;
	}
	
	@Override
	public V getValue() {
		return value;
	}
	
	/**
	 * Take the value of the decorated {@link IArg} use it to determine the 
	 * value of this ArgDecorator.
	 */
	@Override
	public void evaluate() {
		childArg.evaluate();
		processArg(childArg.getValue());
	}
	
	/*
	 * Take the value determined from the child {@link IArg} and use it to 
	 * determine the value of this ArgDecorator.
	 */
	protected abstract void processArg(P parameter);
	
	@Override
	public String getName() {
		if (name == null && childArg instanceof INamedArg) {
			return ((INamedArg<P>) childArg).getName();
		} else {
			return name;
		}
		
	}

	@Override
	public String toString() {
		String str = "";
		if (name != null) {
			str = str + "name=" + name+ ", ";
		}
		return localToString(str);
	}
	
	protected abstract String localToString(String str);

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((childArg == null) ? 0 : childArg.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		ArgDecorator<?,?> other = (ArgDecorator<?,?>) obj;
		if (childArg == null) {
			if (other.childArg != null)
				return false;
		} else if (!childArg.equals(other.childArg))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	
}
