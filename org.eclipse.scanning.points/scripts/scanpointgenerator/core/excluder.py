###
# Copyright (c) 2016 Diamond Light Source Ltd.
#
# All rights reserved. This program and the accompanying materials
# are made available under the terms of the Eclipse Public License v1.0
# which accompanies this distribution, and is available at
# http://www.eclipse.org/legal/epl-v10.html
#
# Contributors:
#    Gary Yendell - initial API and implementation and/or initial documentation
#    Charles Mita - initial API and implementation and/or initial documentation
# 
###

class Excluder(object):
    """
    A class to remove points that lie outside of a given 2D region of interest
    """

    def __init__(self, roi, scannables):
        """
        Args:
            roi(ROI): Region of interest to filter points by
            scannables(list): List of two scannables to filter points by
        """

        self.roi = roi
        self.scannables = scannables

    def contains_point(self, d):
        """
        Create a 2D sub-point from the ND point d and pass the sub_point to
        the roi to check if it contains it.

        Args:
            d(dict): Dictionary representation of point

        Returns:
            bool: Whether roi contains the given point
        """

        scannable_1 = d[self.scannables[0]]
        scannable_2 = d[self.scannables[1]]

        sub_point = (scannable_1, scannable_2)

        return self.roi.contains_point(sub_point)

    def create_mask(self, x_points, y_points):
        return self.roi.mask_points([x_points, y_points])

    def to_dict(self):
        """Convert object attributes into a dictionary"""

        d = dict()
        d['roi'] = self.roi.to_dict()
        d['scannables'] = self.scannables

        return d

    @classmethod
    def from_dict(cls, d):
        """
        Create a Excluder instance from a serialised dictionary

        Args:
            d(dict): Dictionary of attributes

        Returns:
            Excluder: New Excluder instance
        """

        roi = d['roi'].from_dict()
        scannables = d['scannables']

        return cls(roi, scannables)
