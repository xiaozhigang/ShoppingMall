package com.shopmall.springIoc;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xiao
 * @data 2024/4/1 19:58
 */
public class BeanRegister {

    //单例Bean缓存
    private final Map<String, Object> singletonBeanMap = new HashMap<>();

    /**
     * 获取单例Bean
     *
     * @param beanName bean名称
     * @return Object 单例Bean
     */
    public Object getSingletonBean(String beanName) {
        return singletonBeanMap.get(beanName);
    }

    /**
     * 注册单例bean
     *
     * @param beanName bean名称
     * @param bean 单例bean
     */
    public void registerSingletonBean(String beanName, Object bean) {
        if (singletonBeanMap.containsKey(beanName)) {
            return;
        }
        singletonBeanMap.put(beanName, bean);
    }

}
