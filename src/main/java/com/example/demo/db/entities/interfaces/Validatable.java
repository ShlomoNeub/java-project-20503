package com.example.demo.db.entities.interfaces;

/**
 * Int
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
