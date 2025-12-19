package com.codingshuttle.uber.uberApp.services.Impl;

import com.codingshuttle.uber.uberApp.services.DistanceService;
import lombok.Data;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class DistanceServiceOSRMImpl implements DistanceService {

    private final String OSRM_API_BASE_URL = "https://router.project-osrm.org/route/v1/driving/";

    @Override
    public double calculateDisatnce(Point src, Point dest) {
//        Call third party API call OSRM to fetch Distance
        String uri = src.getX()+","+src.getY()+";"+dest.getX()+","+dest.getY();
        try{
            OSRNResponseDto responseDto = RestClient.builder()
                    .baseUrl(OSRM_API_BASE_URL)
                    .build()
                    .get()
                    .uri(uri)
                    .retrieve()
                    .body(OSRNResponseDto.class);
            return responseDto.getRoutes().get(0).getDistance()/1000.0;
        }catch (Exception e){
            throw new RuntimeException("Error getting data from OSRM " +e.getMessage());
        }

    }
}

@Data
class OSRNResponseDto{
    private List<OSRMRoutes> routes;
}

@Data
class OSRMRoutes{
    private double distance;
}
