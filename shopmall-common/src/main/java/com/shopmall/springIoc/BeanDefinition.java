package com.shopmall.springIoc;

import lombok.Getter;
import lombok.Setter;

/**
 * @author xiao
 * @data 2024/4/1 19:43
 */
@Getter
@Setter
public class BeanDefinition {
    private String beanName;

    private Class beanClass;
}
