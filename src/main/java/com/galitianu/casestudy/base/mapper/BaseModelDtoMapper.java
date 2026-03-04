package com.galitianu.casestudy.base.mapper;


import com.galitianu.casestudy.base.service.BaseEntityModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.Optional;

public interface BaseModelDtoMapper<M extends BaseEntityModel, I, O> {

    O mapToResponse(M model);

    M mapRequestToModel(I input);

    default Optional<O> mapToResponse(Optional<M> element) {
        return element.map(this::mapToResponse);
    }

    default Page<O> mapToResponse(Page<M> page) {
        if (page == null) {
            return null;
        }

        List<O> content = page.stream().map(this::mapToResponse).toList();
        return new PageImpl<>(content, page.getPageable(), page.getTotalElements());
    }

    default List<O> mapToResponses(List<M> models) {
        if (models == null) {
            return null;
        }

        return models.stream().map(this::mapToResponse).toList();
    }

    default List<M> mapRequestsToModels(List<I> inputs) {
        if (inputs == null) {
            return null;
        }

        return inputs.stream().map(this::mapRequestToModel).toList();
    }
}
