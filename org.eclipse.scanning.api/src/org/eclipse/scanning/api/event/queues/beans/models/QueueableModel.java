package org.eclipse.scanning.api.event.queues.beans.models;

import org.eclipse.scanning.api.event.queues.beans.Queueable;

/**
 * Parent class defining basic fields necessary for QueueBeanFactory to 
 * interact with objects implementing {@link IQueueableModel}.
 * 
 * @author Michael Wharmby
 *
 * @param <T> Type extending {@link Queueable}
 */
public abstract class QueueableModel<T extends Queueable> implements IQueueableModel<T> {
	
	private final String referenceName;
	protected String name = null;
	private Long runTime = null;
	
	protected QueueableModel(String referenceName) {
		this.referenceName = referenceName;
	}

	@Override
	public String getName() {
		return name;
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
	
	/*
	 * TODO 
	 * .if
	 * .getArg
	 * .getArgEval
	 * .getArgEvalArray
	 * .setArgEval
	 */

}
