package org.eclipse.scanning.api.event.queues.models.arguments;

import java.util.Arrays;

import org.eclipse.scanning.api.event.queues.models.QueueModelException;

/**
 * {@link IArg} which holds an array. The value is selected from the array 
 * from the supplied index.
 * 
 * @author Michael Wharmby
 *
 * @param <P> Either an array of objects of type V or an integer.
 * @param <V> Type of the value held in the array of this argument.
 */
public class ArrayArg<P, V> extends ArgDecorator<P, V> {
	
	private V[] valuesArray;
	private Integer index;
	
	/**
	 * Construct an ArrayArg with the array set from the value of the 
	 * evaluated childArg. Do not use evaluate() directly with instances 
	 * created by this method, but instead use the index() method to get 
	 * individual values.
	 * 
	 * @param childArg Argument evaluating to an array.
	 */
	public ArrayArg(IArg<P> childArg) {
		super(childArg);
	}
	
	/**
	 * Construct an ArrayArg with the array set from the value of the 
	 * evaluated childArg. The value is found by selecting the item in the 
	 * array at the given index.
	 * 
	 * @param childArg Argument evaluating to an array.
	 * @param index Integer position in the array.
	 */
	public ArrayArg(IArg<P> childArg, int index) {
		super(childArg);
		this.index = index;
	}
	
	/**
	 * Construct an ArrayArg with the array explicitly specified. The value is 
	 * calculated by evaluating the childArg to obtain an integer index value.
	 * 
	 * @param childArg Argument evaluating to an integer.
	 * @param valuesArray An array of possible values.
	 */
	public ArrayArg(IArg<P> childArg, V[] valuesArray) {
		super(childArg);
		this.valuesArray = valuesArray;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void processArg(P parameter) {
		if (valuesArray == null) {
			valuesArray = (V[])parameter;
		} else if (index == null && parameter instanceof Integer) {
			index = (Integer)parameter;
		}
		if (valuesArray == null || index == null) {
			throw new QueueModelException("ArrayArg missing array or index. Cannot be evaluated.");
		}
		value = valuesArray[index];
	}
	
	/**
	 * Change the index for which the value should be evaluated. This 
	 * necessarily creates a new ArrayArg object.
	 *  
	 * @param index New Integer position in the array.
	 * @return New ArrayArg with changed index value.
	 */
	public ArrayArg<P,V> setIndex(int index) {
		return new ArrayArg<>(childArg, index);
	}
	
	/**
	 * Return the value in the held array at a given index.
	 * 
	 * @param index integer position in array.
	 * @return value V at index in array.
	 */
	public V index(int index) {
		if (valuesArray == null) { 
			this.index = ((this.index == null) ? index : this.index); 
			evaluate();
		}
		return valuesArray[index];
	}

	@Override
	protected String localToString(String str) {
		return "ArrayArg [" + str + ", value=" + value + "index=" + index + "valuesArray=" + valuesArray + ", childArg=" + childArg + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((index == null) ? 0 : index.hashCode());
		result = prime * result + Arrays.hashCode(valuesArray);
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
		ArrayArg<?,?> other = (ArrayArg<?,?>) obj;
		if (index == null) {
			if (other.index != null)
				return false;
		} else if (!index.equals(other.index))
			return false;
		if (!Arrays.equals(valuesArray, other.valuesArray))
			return false;
		return true;
	}
	
	
	

}
