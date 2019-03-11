package vudq.service.base.dao;

import java.util.List;

public interface BaseFWDAO<T extends Object> {
    public T findById(long id);
    public T findByName(String name);
    public List<T> findAll();
    public String delete(long id);
    public Long countAll();
}
