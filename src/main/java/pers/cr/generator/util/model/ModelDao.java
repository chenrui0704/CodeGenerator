package pers.cr.generator.util.model;

import java.util.List;

public interface ModelDao<T> {

    List<T> query(T data);

    T queryById(Integer id);

    void insert(T data);

    void updateById(T data);

    void deleteById(Integer id);


}



