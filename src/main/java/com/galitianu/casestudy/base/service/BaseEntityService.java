package com.galitianu.casestudy.base.service;


import com.galitianu.casestudy.base.mapper.BaseModelEntityMapper;
import com.galitianu.casestudy.base.persistence.entity.BaseEntity;
import com.galitianu.casestudy.base.persistence.repository.BaseRepository;
import jakarta.persistence.EntityManager;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.IteratorUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

@Getter
@Setter
public abstract class BaseEntityService<M extends BaseEntityModel, E extends BaseEntity> extends BaseService {

    protected EntityManager entityManager;

    protected abstract BaseRepository<E> getRepository();

    protected abstract BaseModelEntityMapper<M, E> getMapper();

    public Optional<M> findById(UUID id) {
        return getMapper().mapToModel(getRepository().findById(id));
    }

    public List<M> findAll() {
        Iterable<E> iterable = getRepository().findAll();
        return StreamSupport.stream(iterable.spliterator(), false).map(getMapper()::mapToModel).toList();
    }

    public M save(M m) {
        E e = getMapper().mapToEntity(m);
        e = getRepository().save(e);
        entityManager.flush();
        entityManager.clear();
        return getMapper().mapToModel(e);
    }

    public List<M> saveAll(List<M> ms) {
        List<E> es = getMapper().mapToEntities(ms);
        es = IteratorUtils.toList(getRepository().saveAll(es).iterator());
        entityManager.flush();
        entityManager.clear();
        return getMapper().mapToModels(es);
    }

    public void delete(M m) {
        E e = getMapper().mapToEntity(m);
        getRepository().delete(e);
        entityManager.flush();
        entityManager.clear();
    }

}

