/*-
 *******************************************************************************
 * Copyright (c) 2011, 2016 Diamond Light Source Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Matthew Gerring - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.scanning.sequencer.nexus;

public class SolsticeConstants {

	public static final String GROUP_NAME_SOLSTICE_SCAN = "solstice_scan";	
	public static final String GROUP_NAME_KEYS          = "keys";	
	public static final String FIELD_NAME_UNIQUE_KEYS   = "uniqueKeys";
	public static final String FIELD_NAME_SCAN_RANK     = "scanRank";	
	public static final String FIELD_NAME_SCAN_FINISHED = "scan_finished";	
	public static final String FIELD_NAME_SCAN_CMD      = "scan_cmd";
	public static final String FIELD_NAME_SCAN_MODELS   = "scan_models";
	
	/**
	 * Property name for the path within an external (linked) nexus file to the unique keys dataset. 
	 */
	public static final String PROPERTY_NAME_UNIQUE_KEYS_PATH = "uniqueKeys";

}
