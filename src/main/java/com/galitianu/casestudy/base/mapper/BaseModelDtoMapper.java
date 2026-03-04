package com.galitianu.casestudy.base.mapper;


import com.galitianu.casestudy.base.api.BaseEntityDto;
import com.galitianu.casestudy.base.service.BaseEntityModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.Optional;

public interface BaseModelDtoMapper<M extends BaseEntityModel, D extends BaseEntityDto> {

    D mapToDto(M model);

    M mapToModel(D dto);

    default Optional<D> mapToDto(Optional<M> element) {
        return element.map(this::mapToDto);
    }

    default Page<D> mapToDto(Page<M> page) {
        if (page == null) {
            return null;
        }

        List<D> content = page.stream().map(this::mapToDto).toList();
        return new PageImpl<>(content, page.getPageable(), page.getTotalElements());
    }

    default List<D> mapToDto(List<M> models) {
        if (models == null) {
            return null;
        }

        return models.stream().map(this::mapToDto).toList();
    }

    default List<M> mapDtosToModels(List<D> dtos) {
        if (dtos == null) {
            return null;
        }

        return dtos.stream().map(this::mapToModel).toList();
    }

}

