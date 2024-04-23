package org.zjj.myspring.bean;

import org.zjj.myspring.context.annotation.Value;
import org.zjj.myspring.stereotype.Component;

import lombok.Data;

/**
 * @author zhongjunjie on 2024/4/8
 */
@Data
@Component
public class Car {

    @Value("${brand}")
    private String brand;
}
