package homework.appcontainer;

import homework.appcontainer.api.AppComponent;
import homework.appcontainer.api.AppComponentsContainer;
import homework.appcontainer.api.AppComponentsContainerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private static final Logger logger = LoggerFactory.getLogger(AppComponentsContainerImpl.class);

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);
        // You code here...
        appComponents.clear();
        appComponentsByName.clear();

        var methodList = Arrays.stream(configClass.getMethods())
            .filter(method -> method.isAnnotationPresent(AppComponent.class))
            .sorted(Comparator.comparingInt(method -> method.getAnnotation(AppComponent.class).order()))
            .toList();

        try {
            var classInstance = configClass.getDeclaredConstructor().newInstance();

            for (var method: methodList) {
                Object singleton;
                var paramsQ = method.getParameterCount();
                if (paramsQ > 0) {
                    var params = new Object[paramsQ];
                    for (var i = 0; i < paramsQ; i++) {
                        params[i] = getAppComponent(method.getParameters()[i].getType());
                    }
                    singleton = method.invoke(classInstance, params);
                } else {
                    singleton = method.invoke(classInstance);
                }
                appComponents.add(singleton);
                appComponentsByName.put(method.getAnnotation(AppComponent.class).name(), singleton);
            }

        } catch (InstantiationException e) {
            logger.error("Instatiation exception: {}",  e.getMessage());
        } catch (IllegalAccessException e) {
            logger.error("Illegal access exception: {}",  e.getMessage());
        } catch (InvocationTargetException e) {
            logger.error("Invocation target exception: {}",  e.getMessage());
        } catch (NoSuchMethodException e) {
            logger.error("No such method: {}",  e.getMessage());
        }

    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        for (var component: appComponents) {
            if (componentClass.isInstance(component)) {
                return componentClass.cast(component);
            }
        }
        throw new IllegalArgumentException("Illegal class: " + componentClass.getName());
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        return (C) appComponentsByName.get(componentName);
    }
}
