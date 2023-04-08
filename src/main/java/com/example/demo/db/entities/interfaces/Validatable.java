package com.example.demo.db.entities.interfaces;

/**
 * Make sure that the object can be validate using is valud
 * @param <T>
 */
public interface Validatable<T> {
    /**
     *
     * @param toValidate object of type T
     * @return if the given object is valid
     */
    boolean isValid(T toValidate);

}
