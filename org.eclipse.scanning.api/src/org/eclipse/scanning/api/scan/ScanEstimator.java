package org.eclipse.scanning.api.scan;

import java.util.Map;

import org.eclipse.scanning.api.device.models.IDetectorModel;
import org.eclipse.scanning.api.event.scan.ScanRequest;
import org.eclipse.scanning.api.points.GeneratorException;
import org.eclipse.scanning.api.points.IPointGenerator;
import org.eclipse.scanning.api.points.IPointGeneratorService;

/**
 * 
 * Most scans are static and therefore they can have their shape and size
 * discovered. Those scans which truely are iterators and on the fly decide
 * the next position, can only have their shapes and sizes estimated.
 * 
 * Shape is more expensive to estimate that size. For 10 million points size
 * is ~100ms depending on iterator type. Shape will take longer as more floating 
 * point operations are required.
 * 
 * @author Matthew Gerring
 *
 */
public class ScanEstimator {

	/**
	 * Size, number of points in scan
	 */
	private int   size;
	
	/**
	 * 
	 */
	private long  timePerPoint = -1;
	private long  scanTime = -1;

	/**
	 * 
	 * @param pservice
	 * @param bean
	 */
	public ScanEstimator(IPointGeneratorService pservice, ScanRequest<?> request) throws GeneratorException{
		this(pservice, request, Long.MIN_VALUE);
	}

	/**
	 * 
	 * @param pservice
	 * @param bean
	 * @param timePerPoint ms
	 * @throws GeneratorException 
	 */
	public ScanEstimator(IPointGeneratorService pservice, ScanRequest<?> request, long timePerPoint) throws GeneratorException {
		this(pservice.createCompoundGenerator(request.getCompoundModel()), request.getDetectors(), timePerPoint);
	}
	/**
	 * 
	 * @param pservice
	 * @param request
	 * @param timePerPoint
	 * @throws GeneratorException
	 */
	public ScanEstimator(IPointGenerator<?> gen, Map<String, Object> detectors, long timePerPoint) throws GeneratorException {

		
		// TODO FIXME If some detectors are malcolm, they may have a wait time.
		// If some are malcolm we may wish to ignore the input point time from the user
		// in favour of the malcolm time per point or maybe the device tells us how long it will take?
		if (detectors!=null) for (Object model : detectors.values()) {
			if (model instanceof IDetectorModel) {
				timePerPoint = Math.max(timePerPoint, Math.round(1000*((IDetectorModel)model).getExposureTime()));
			}
		}
		this.size = gen.size();
		this.timePerPoint  = timePerPoint;
		this.scanTime      = size*timePerPoint;
	}

	public int getSize() {
		return size;
	}

	public long getTimePerPoint() {
		return timePerPoint;
	}

	public void setTimePerPoint(long timePerPoint) {
		this.timePerPoint = timePerPoint;
	}

	public long getScanTime() {
		return scanTime;
	}

	public void setScanTime(long scanTime) {
		this.scanTime = scanTime;
	}
}
