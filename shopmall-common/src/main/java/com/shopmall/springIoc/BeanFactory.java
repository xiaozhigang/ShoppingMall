package com.shopmall.springIoc;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xiao
 * @data 2024/4/1 20:02
 */
public class BeanFactory {
    private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();

    private BeanRegister beanRegister;

    public BeanFactory() {
        //创建bean注册器
        beanRegister = new BeanRegister();
        //加载资源
        this.beanDefinitionMap = new ResourceLoader().getResource();
    }

    /**
     * 获取bean
     *
     * @param beanName bean名称
     * @return Object Bean
     */
    public Object getBean(String beanName) {
        //从bean缓存中取
        Object bean = beanRegister.getSingletonBean(beanName);
        if (bean != null) {
            return bean;
        }
        //根据bean定义，创建bean
        return createBean(beanDefinitionMap.get(beanName));
    }

    /**
     * 创建Bean
     *
     * @param beanDefinition bean定义
     * @return Object Bean
     */
    private Object createBean(BeanDefinition beanDefinition) {
        try {
            Object bean = beanDefinition.getBeanClass().newInstance();
            //缓存bean
            beanRegister.registerSingletonBean(beanDefinition.getBeanName(), bean);
            return bean;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
