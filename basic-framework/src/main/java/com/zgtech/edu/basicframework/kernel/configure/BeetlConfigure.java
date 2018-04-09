package com.zgtech.edu.basicframework.kernel.configure;

import org.beetl.core.GroupTemplate;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;
import org.beetl.ext.spring.BeetlSpringViewResolver;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;

@Configuration
public class BeetlConfigure {
    @Bean(name = "beetlConfig")
    @ConditionalOnMissingBean(name = {"beetlConfig"})
    public BeetlGroupUtilConfiguration getBeetlGroupUtilConfiguration() {
        BeetlGroupUtilConfiguration beetlGroupUtilConfiguration = new BeetlGroupUtilConfiguration();
        ResourcePatternResolver patternResolver = ResourcePatternUtils.getResourcePatternResolver(new DefaultResourceLoader());
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            if (loader == null) {
                loader = BeetlConfigure.class.getClassLoader();
            }
            beetlGroupUtilConfiguration.setConfigFileResource(patternResolver.getResource("classpath:beetl.yml"));
            ClasspathResourceLoader cploder = new ClasspathResourceLoader(loader,
                    "templates");
            beetlGroupUtilConfiguration.setResourceLoader(cploder);
            beetlGroupUtilConfiguration.init();
            //如果使用了优化编译器，涉及到字节码操作，需要添加ClassLoader
            beetlGroupUtilConfiguration.getGroupTemplate().setClassLoader(loader);
            return beetlGroupUtilConfiguration;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Bean(name = "beetlViewResolver")
    @ConditionalOnMissingBean(name = {"beetlViewResolver"})
    public BeetlSpringViewResolver getBeetlSpringViewResolver(
            @Qualifier("beetlConfig") BeetlGroupUtilConfiguration beetlGroupUtilConfiguration) {
        BeetlSpringViewResolver beetlSpringViewResolver = new BeetlSpringViewResolver();
        beetlSpringViewResolver.setContentType("text/html;charset=UTF-8");
        //  beetlSpringViewResolver.setViewNames("*." + suffix);
        beetlSpringViewResolver.setOrder(0);
        beetlSpringViewResolver.setConfig(beetlGroupUtilConfiguration);
        return beetlSpringViewResolver;
    }

    @Bean(name = "groupTemplate")
    @ConditionalOnMissingBean(GroupTemplate.class)
    public GroupTemplate getGroupTemplate(
            @Qualifier("beetlConfig") BeetlGroupUtilConfiguration beetlGroupUtilConfiguration) {
        GroupTemplate gt = beetlGroupUtilConfiguration.getGroupTemplate();
        return gt;
    }
}
