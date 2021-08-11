package com.example.repairagency.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
public enum Area {
    ELECTRONICS, AUTO, HOME;


    public static List<Area> getAll(){
        List<Area> areaList = new ArrayList<>();
        areaList.add(Area.ELECTRONICS);
        areaList.add(Area.HOME);
        areaList.add(Area.AUTO);
        return areaList;
    }
}
