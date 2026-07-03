package com.fauzydev.linkservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.fauzydev.linkservice.dto.request.UrlRequest;
import com.fauzydev.linkservice.dto.response.UrlResponse;
import com.fauzydev.linkservice.entity.Url;

@Mapper(componentModel = "spring")
public interface UrlMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(target = "urlCurta", ignore = true)
    Url toEntity(UrlRequest request);

    @Mapping(target = "urlEncurtada", ignore = true)
    UrlResponse toResponse(Url url);
}
