package org.eclipse.scanning.api.event.queues.models;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.scanning.api.event.queues.beans.Queueable;
import org.eclipse.scanning.api.event.queues.models.arguments.ArrayArg;
import org.eclipse.scanning.api.event.queues.models.arguments.IArg;

/**
 * Parent class defining basic fields necessary for {@link QueueBeanFactory} to 
 * create {@link Queueable} objects from templates implementing 
 * {@link IQueueableModel}.
 * 
 * @author Michael Wharmby
 *
 * @param <T> Type extending {@link Queueable}
 */
public abstract class QueueableModel<T extends Queueable> implements IQueueableModel<T> {
	
	protected final String referenceName;
	protected String name;
	private Long runTime = null;
	protected final Map<String, IArg<?>> args;
	
	protected QueueableModel(String referenceName, String name) {
		this.referenceName = referenceName;
		this.name = name;
		args = new LinkedHashMap<>();
	}

	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getReferenceName() {
		return referenceName;
	}

	@Override
	public Long getRunTime() {
		return runTime;
	}

	@Override
	public void setRunTime(Long runTime) {
		this.runTime = runTime;
	}

	@Override
	public Map<String, IArg<?>> getArgs() {
		return args;
	}
	
	@Override
	public IArg<?> getArg(String argName) {
		return args.get(argName);
	}
	
	@Override
	public List<String> getArgNames() {
		return new ArrayList<>(args.keySet());
	}

	@Override
	public int getNumberOfArgs() {
		return args.size();
	}

	@Override
	public void configure(IArg<?>... args) {
		if (args.length != getNumberOfArgs()) throw new QueueModelException("Wrong number of argument values supplied to model");
		
		int i = 0;
		for (String argName : this.args.keySet()) {
			this.args.put(argName, args[i]);
			i++;
		}
	}
	
	@Override
	public void configure(ArrayArg<?,?> array, int size) {
		configure(array, 0, size-1);
	}
	
	@Override
	public void configure(ArrayArg<?,?> array, int start, int stop) {
		if ((stop - start + 1) != getNumberOfArgs()) throw new QueueModelException("Wrong number of argument values in array supplied to model");
		
		int i = start;
		for (String argName : this.args.keySet()) {
			args.put(argName, array.setIndex(i));
			i++;
		}
	}
	
	@Override
	public void evaluateArgs() {
		for (String argName : args.keySet()) {
			args.get(argName).evaluate();
		}
	}
	
	@Override
	public T build(String userName, String hostName) {
		evaluateArgs();
		return buildBean(userName, hostName);
	}
	
	protected abstract T buildBean(String userName, String hostName);
}
