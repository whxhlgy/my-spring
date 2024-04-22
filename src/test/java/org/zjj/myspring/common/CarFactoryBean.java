package org.zjj.myspring.common;

import org.zjj.myspring.beans.factory.FactoryBean;
import org.zjj.myspring.bean.Car;

public class CarFactoryBean implements FactoryBean<Car> {
    private String brand;

    @Override
    public Car getObject() throws Exception {
        Car car = new Car();
        car.setBrand(brand);
        return car;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
