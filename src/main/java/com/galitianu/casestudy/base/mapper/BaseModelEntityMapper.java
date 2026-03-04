package com.galitianu.casestudy.base.mapper;

import com.galitianu.casestudy.base.persistence.entity.BaseEntity;
import com.galitianu.casestudy.base.service.BaseEntityModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.Optional;

public interface BaseModelEntityMapper<M extends BaseEntityModel, E extends BaseEntity> {

    E mapToEntity(M model);

    M mapToModel(E entity);

    default Optional<M> mapToModel(Optional<E> e) {
        return e.map(this::mapToModel);
    }

    default Page<M> mapToModel(Page<E> page) {
        if (page == null) {
            return null;
        }

        List<M> content = page.stream().map(this::mapToModel).toList();
        return new PageImpl<>(content, page.getPageable(), page.getTotalElements());
    }

    default List<M> mapToModels(List<E> es) {
        if (es == null) {
            return null;
        }

        return es.stream().map(this::mapToModel).toList();
    }

    default List<E> mapToEntities(List<M> ms) {
        if (ms == null) {
            return null;
        }

        return ms.stream().map(this::mapToEntity).toList();
    }

}

