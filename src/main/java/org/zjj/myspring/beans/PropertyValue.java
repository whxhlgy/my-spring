package org.zjj.myspring.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A Single property value of bean
 *
 * @author zhongjunjie on 2024/4/8
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropertyValue {
    private String name;
    private Object value;
}
