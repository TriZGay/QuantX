package io.futakotome.trade.controller.vo;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class EnumValidator implements ConstraintValidator<EnumValid, Object> {
    private String validField;
    private Class<?>[] classes;

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value != null && value.toString().length() > 0 && classes.length > 0) {
            for (Class<?> clz : classes) {
                try {
                    if (clz.isEnum()) {
                        Object[] objects = clz.getEnumConstants();
                        Method method = clz.getMethod("name");
                        for (Object obj : objects) {
                            Object code = method.invoke(obj, null);
                            if (value.equals(code.toString())) {
                                return true;
                            }
                        }
                        Method codeMethod = clz.getMethod(validField);
                        for (Object obj : objects) {
                            Object code = codeMethod.invoke(obj, null);
                            if (value.toString().equals(code.toString())) {
                                return true;
                            }
                        }
                    }
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        } else {
            return true;
        }
        return false;
    }

    @Override
    public void initialize(EnumValid constraintAnnotation) {
        classes = constraintAnnotation.target();
        validField = constraintAnnotation.yanzhengField();
    }

}
