package ru.example;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import ru.example.annotation.Log;

public class ProxyFactory {

    public static <T> T createLoggedProxy(Class<? extends T> implementationClass, Class<T> interfaceClass)
            throws ReflectiveOperationException {

        T implementationInstance = implementationClass.getDeclaredConstructor().newInstance();
        InvocationHandler handler = new LoggingInvocationHandler<>(implementationInstance);

        Object proxy =
                Proxy.newProxyInstance(ProxyFactory.class.getClassLoader(), new Class<?>[] {interfaceClass}, handler);

        return interfaceClass.cast(proxy);
    }

    private static class LoggingInvocationHandler<T> implements InvocationHandler {
        private final T targetInstance;
        private final Set<MethodDescriptor> loggedMethods;

        private record MethodDescriptor(String name, List<Class<?>> parameterTypes) {
            static MethodDescriptor fromMethod(Method method) {
                return new MethodDescriptor(method.getName(), Arrays.asList(method.getParameterTypes()));
            }
        }

        LoggingInvocationHandler(T targetInstance) {
            this.targetInstance = Objects.requireNonNull(targetInstance);
            this.loggedMethods = scanMethodsForLogAnnotation(targetInstance);
        }

        private Set<MethodDescriptor> scanMethodsForLogAnnotation(T instance) {
            return Arrays.stream(instance.getClass().getMethods())
                    .filter(method -> method.isAnnotationPresent(Log.class))
                    .map(MethodDescriptor::fromMethod)
                    .collect(Collectors.toUnmodifiableSet());
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] arguments) throws Throwable {
            if (shouldLogMethod(method)) {
                logMethodCall(method, arguments);
            }

            return method.invoke(targetInstance, arguments);
        }

        private boolean shouldLogMethod(Method method) {
            return loggedMethods.contains(MethodDescriptor.fromMethod(method));
        }

        private void logMethodCall(Method method, Object[] arguments) {
            StringBuilder logMessage = new StringBuilder();
            logMessage.append("Method: ").append(method.getName()).append(", parameters: ");

            if (arguments != null && arguments.length > 0) {
                for (int i = 0; i < arguments.length; i++) {
                    if (i > 0) logMessage.append(", ");
                    logMessage.append(arguments[i]);
                }
            }

            System.out.println(logMessage);
        }

        @Override
        public String toString() {
            return "LoggingInvocationHandler[target=" + targetInstance + "]";
        }
    }
}
