package com.example.demo.db.entities.interfaces;

/**
 * Make sure that the object can be validated using his instaces
 * @param <T> of the object to be validated
 */
public interface Validatable<T> {
    /**
     *
     * @param toValidate object of type T
     * @return if the given object is valid
     */
    boolean isValid(T toValidate);

}
