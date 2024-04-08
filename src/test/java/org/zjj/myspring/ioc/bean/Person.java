package org.zjj.myspring.ioc.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhongjunjie on 2024/4/8
 */
@Data
public class Person {
    private String name;
    private int age;

    private Car car;
}
