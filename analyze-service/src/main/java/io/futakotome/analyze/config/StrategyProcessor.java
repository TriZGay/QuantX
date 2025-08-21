package io.futakotome.analyze.config;

import io.futakotome.analyze.biz.backtest.StrategyType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.SystemPropertyUtils;
import org.springframework.util.TypeUtils;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.*;

@Component
@SuppressWarnings({"unchecked", "rawtypes"})
public class StrategyProcessor implements BeanFactoryPostProcessor {
    private static final String BASE_PACKAGE = "io.futakotome.analyze.biz.backtest.strategy";
    private static final Logger LOGGER = LoggerFactory.getLogger(StrategyProcessor.class);

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        Map<Integer, Class> map = new HashMap<>();

        ClassScanner.scan(BASE_PACKAGE, StrategyType.class).forEach(x -> {
            int type = x.getAnnotation(StrategyType.class).value();
            map.put(type, x);
        });
        String beanName = StrategyType.class.getName();
        configurableListableBeanFactory.registerSingleton(beanName, map);
        LOGGER.info("策略处理器初始化:{}", configurableListableBeanFactory.getBean(beanName));
    }

    public static final class ClassScanner {
        private ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        private final List<TypeFilter> includeFilters = new ArrayList<>();
        private final List<TypeFilter> excludeFilters = new ArrayList<>();
        private MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resourcePatternResolver);

        public void addIncludeFilter(TypeFilter includeFilter) {
            this.includeFilters.add(includeFilter);
        }

        public void addExcludeFilter(TypeFilter excludeFilter) {
            this.excludeFilters.add(excludeFilter);
        }

        public static Set<Class<?>> scan(String basePackage,
                                         Class<?>... targetTypes) {
            ClassScanner cs = new ClassScanner();
            for (Class<?> targetType : targetTypes) {
                if (TypeUtils.isAssignable(Annotation.class, targetType)) {
                    cs.addIncludeFilter(new AnnotationTypeFilter((Class<? extends Annotation>) targetType));
                } else {
                    cs.addIncludeFilter(new AssignableTypeFilter(targetType));
                }
            }
            return cs.doScan(basePackage);
        }

        public static Set<Class<?>> scan(String[] basePackages,
                                         Class<?>... targetTypes) {
            ClassScanner cs = new ClassScanner();
            for (Class<?> targetType : targetTypes) {
                if (TypeUtils.isAssignable(Annotation.class, targetType)) {
                    cs.addIncludeFilter(new AnnotationTypeFilter((Class<? extends Annotation>) targetType));
                } else {
                    cs.addIncludeFilter(new AssignableTypeFilter(targetType));
                }
            }
            Set<Class<?>> classes = new HashSet<Class<?>>();
            for (String s : basePackages) {
                classes.addAll(cs.doScan(s));
            }
            return classes;
        }

        public Set<Class<?>> doScan(String[] basePackages) {
            Set<Class<?>> classes = new HashSet<Class<?>>();
            for (String basePackage : basePackages) {
                classes.addAll(doScan(basePackage));
            }
            return classes;
        }

        public Set<Class<?>> doScan(String basePackage) {
            Set<Class<?>> classes = new HashSet<Class<?>>();
            try {
                String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
                        + ClassUtils.convertClassNameToResourcePath(
                        SystemPropertyUtils.resolvePlaceholders(basePackage)) + "/**/*.class";
                Resource[] resources = this.resourcePatternResolver.getResources(packageSearchPath);
                for (int i = 0; i < resources.length; i++) {
                    Resource resource = resources[i];
                    if (resource.isReadable()) {
                        MetadataReader metadataReader = this.metadataReaderFactory.getMetadataReader(resource);
                        if ((includeFilters.size() == 0 && excludeFilters.size() == 0) || matches(metadataReader)) {
                            try {
                                classes.add(Class.forName(metadataReader.getClassMetadata().getClassName()));
                            } catch (ClassNotFoundException ignore) {
                            }
                        }
                    }
                }
            } catch (IOException ex) {
                throw new RuntimeException("I/O failure during classpath scanning", ex);
            }
            return classes;
        }

        private boolean matches(MetadataReader metadataReader) throws IOException {
            for (TypeFilter tf : this.excludeFilters) {
                if (tf.match(metadataReader, this.metadataReaderFactory)) {
                    return false;
                }
            }
            for (TypeFilter tf : this.includeFilters) {
                if (tf.match(metadataReader, this.metadataReaderFactory)) {
                    return true;
                }
            }
            return false;
        }
    }
}
