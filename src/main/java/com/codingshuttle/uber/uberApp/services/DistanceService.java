package com.codingshuttle.uber.uberApp.services;

import org.locationtech.jts.geom.Point;


public interface DistanceService {

    double calculateDisatnce(Point src ,Point dest);
}
