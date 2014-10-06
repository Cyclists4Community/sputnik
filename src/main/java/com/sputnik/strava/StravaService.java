package com.sputnik.strava;

import com.sputnik.strava.segmenteffort.SegmentEffort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.strava.api.Strava;
import org.springframework.social.strava.api.StravaSegmentEffort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StravaService {

    @Autowired
    Strava strava;

    public List<SegmentEffort> getSegmentEfforts() {
        String currentAthleteId = String.valueOf(strava.athleteOperations().getAthleteProfile().getId());
        List<StravaSegmentEffort> stravaSegmentEfforts = strava.segmentEffortOperations().getSegmentEfforts("8269800", currentAthleteId);

        return stravaSegmentEfforts.stream().map(this::segmentEffortCreator).collect(Collectors.toList());
    }

    private SegmentEffort segmentEffortCreator(StravaSegmentEffort stravaSegmentEffort) {
        return new SegmentEffort(
                stravaSegmentEffort.getId(),
                stravaSegmentEffort.getName(),
                stravaSegmentEffort.getAthlete().getId(),
                stravaSegmentEffort.getDistance(),
                stravaSegmentEffort.getDate(),
                stravaSegmentEffort.getSegment().getId(),
                stravaSegmentEffort.getElapsedTime()
        );
    }
}