package org.eclipse.scanning.points;

import java.util.Iterator;

import org.eclipse.scanning.api.points.Point;
import org.eclipse.scanning.api.points.models.GridModel;

class GridIterator implements Iterator<Point> {

	private final GridGenerator gen;
	private final int columns;
	private final int rows;
	private final boolean snake;
	private final String xName;
	private final String yName;
	private final double minX;
	private final double minY;
	private final double xStep;
	private final double yStep;

	private int yIndex, xIndex;
	private boolean forwards = true;

	public GridIterator(GridGenerator gen) {
		this.gen = gen;
		GridModel model = gen.getModel();
		this.columns = model.getColumns();
		this.rows = model.getRows();
		this.snake = model.isSnake();
		this.xName = model.getxName();
		this.yName = model.getyName();
		this.xStep = model.getBoundingBox().getWidth() / columns;
		this.yStep = model.getBoundingBox().getHeight() / rows;
		this.minX = model.getBoundingBox().getxStart() + xStep / 2;
		this.minY = model.getBoundingBox().getyStart() + yStep / 2;
		yIndex = 0;
		xIndex = -1;
	}

	@Override
	public boolean hasNext() {
		
		int[] next = increment(snake, columns, yIndex, xIndex, forwards); 
		int yIndex = next[0];
		int xIndex = next[1];
			
		if (yIndex > (rows - 1) || yIndex < 0)    {
			return false;  // Normal termination
		}
		if (xIndex > (columns - 1) || xIndex < 0) return false;
		
		double x = minX + xIndex * xStep;
		double y = minY + yIndex * yStep;
		if (!gen.containsPoint(x, y)) {
			this.yIndex = yIndex;
			this.xIndex = xIndex;
			this.forwards = next[2]==1;
			return hasNext();
		}

		return true;
	}


	private static final int[] increment(boolean snake, int columns, int yIndex, int xIndex, boolean forwards) {
		
		if (snake) {
			if (forwards) {
				xIndex++;
				if (xIndex > (columns - 1)) {
					xIndex = columns - 1;
					yIndex++;
					forwards = !forwards;
				}
			} else {
				xIndex--;
				if (xIndex<0) {
					xIndex=0;
					yIndex++;
					forwards = !forwards;
				}
			}

		} else {
			xIndex++;
			if (xIndex>(columns-1)) {
				xIndex=0;
				yIndex++;
			}
		}
		return new int[]{yIndex,xIndex, forwards?1:0}; // Bit slow because makes array object to return int values
	}

	
	@Override
	public Point next() {
		
		int[] next = increment(snake, columns, yIndex, xIndex, forwards);
		this.yIndex = next[0];
		this.xIndex = next[1];
		this.forwards = next[2]==1;
		
		if (yIndex > (rows - 1) || yIndex < 0)    return null;  // Normal termination
		if (xIndex > (columns - 1) || xIndex < 0) throw new NullPointerException("Unexpected index. The j index was "+xIndex);

		double x = minX + xIndex * xStep;
		double y = minY + yIndex * yStep;
		if (gen.containsPoint(x, y)) {
			return new Point(xName, xIndex, x, yName, yIndex, y);
		} else {
			return next();
		}
	}

	public void remove() {
		throw new UnsupportedOperationException("remove");
	}

}
