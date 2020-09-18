package com.example.UserWallet.converter;

public interface Converter<E, M> {

    E convertModelToEntity(M model);

    M convertEntityToModel(E entity);
}
