package common.buessiness.problems.utils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.beans.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author : weizc
 * @since 2020/7/30
 */
@Slf4j
public class JdkDynamicProxyDemo {


    public static void main(String[] args) {

        Person person = new Person();
        PersonInvocationHandler invocationHandler = new PersonInvocationHandler(person);
        invocationHandler.addPropertyChangeListener(evt ->
                log.info("old value:{},new value:{}", evt.getOldValue(), evt.getNewValue()));

        invocationHandler.addVetoableChangeListener(evt -> {
            String value = String.valueOf(evt.getNewValue());
            if (!value.startsWith("h")) {
                throw new PropertyVetoException(String.format("value: %s不以h开头", value), evt);
            }
        });

        Namable namable = (Namable) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{Namable.class}, invocationHandler);
        namable.setName("hello");

        namable.setName("xhello");


    }


    static class PersonInvocationHandler implements InvocationHandler {


        Person person;
        final transient PropertyChangeSupport changeSupportListener;

        final transient VetoableChangeSupport vetoableChangeListener;

        public void addPropertyChangeListener(PropertyChangeListener listener) {
            changeSupportListener.addPropertyChangeListener(listener);
        }

        public void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
            changeSupportListener.firePropertyChange(propertyName, oldValue, newValue);
        }

        public void addVetoableChangeListener(VetoableChangeListener listener) {
            vetoableChangeListener.addVetoableChangeListener(listener);
        }

        public void fireVetoableChange(String propertyName, Object oldValue, Object newValue) throws PropertyVetoException {
            vetoableChangeListener.fireVetoableChange(propertyName, oldValue, newValue);
        }

        public PersonInvocationHandler(Person person) {
            this.person = person;
            changeSupportListener = new PropertyChangeSupport(person);
            vetoableChangeListener = new VetoableChangeSupport(person);
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if ("setName".equals(method.getName())
                    && args.length == 1
                    && method.getReturnType().equals(void.class) &&
                    args[0] instanceof String
            ) {

                String oldValue = person.getName();
                String newValue = (String) args[0];
                fireVetoableChange("name", person, newValue);

                method.invoke(person, args);
                firePropertyChange("name", oldValue, newValue);


            }
            return null;
        }
    }

    @Data
    static class Person implements Namable {

        String name;

    }

    interface Namable {
        void setName(String name);
    }
}
