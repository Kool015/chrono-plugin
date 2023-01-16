package com.chrono;

import lombok.Getter;

import java.util.*;

public enum Release {
    JANUARY_2001(
        "January 2001",
        new GregorianCalendar(2001, Calendar.JANUARY, 31).getTime(),
        Arrays.asList(12337, 12338, 12339, 12340, 12341, 12342, 12593, 12594, 12595, 12596, 12597, 12598, 12849, 12850, 12851, 12852, 12853, 12854, 13108, 13109, 13110)
    ),
    FEBRUARY_2001(
        "February 2001",
        new GregorianCalendar(2001, Calendar.FEBRUARY, 31).getTime(),
        Arrays.asList(0)
    );

    @Getter
    private String name;
    @Getter
    private Date date;
    @Getter
    private List<Integer> regions;

//    Release(String name, Date date, List<Integer> regions, List<Integer> pastRegions) {
//        this.name = name;
//        this.date = date;
//        this.regions = regions;
//        this.regions.addAll(pastRegions);
//    }

    Release(String name, Date date, List<Integer> regions) {
        this.name = name;
        this.date = date;
        this.regions = regions;
    }

    public static List<Integer> getRegions(Release release) {
        return release.getRegions();
    }
}
