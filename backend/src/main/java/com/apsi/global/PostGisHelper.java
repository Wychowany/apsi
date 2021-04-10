package com.apsi.global;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

public class PostGisHelper {

	public static Point createPoint(CoordinatesDTO coordinatesDTO) {
		return getPoint(coordinatesDTO.getLatitude(), coordinatesDTO.getLongitude());
	}

	public static Point createPoint(double latitude, double longitude) {
		return getPoint(latitude, longitude);
	}

	private static Point getPoint(double latitude, double longitude) {
		Geometry geometry = null;
		try {
			geometry = new WKTReader().read("POINT (".concat(String.valueOf(latitude))
					.concat(" ").concat(String.valueOf(longitude)).concat(")"));
		} catch (ParseException e) {
			System.out.println("Cannot parse Point");
		}
		return geometry.getInteriorPoint();
	}
}
