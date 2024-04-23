package org.zjj.myspring.service;

import lombok.Getter;

public class WorldServiceImpl implements WorldService {

    private String name;

    @Override
    public void explode() {
        System.out.println("World explode!");
    }

    @Override
    public String getName() {
        return name;
    }

}
