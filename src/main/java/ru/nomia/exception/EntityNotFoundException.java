package ru.nomia.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(Class<?> clazz, String id) {
        super(String.format("Entity [%s] with id [%s] is not found", clazz.getSimpleName(), id));
    }

}
