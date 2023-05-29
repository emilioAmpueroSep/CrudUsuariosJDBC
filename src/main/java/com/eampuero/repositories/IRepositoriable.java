package com.eampuero.repositories;

import java.util.List;

public interface IRepositoriable<T> {
    List<T> listar();
    T porId(Long id);
    void guardar(T t);
    void eliminar(Long id);
}
