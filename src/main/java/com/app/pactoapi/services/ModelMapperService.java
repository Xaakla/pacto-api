package com.app.pactoapi.services;

import com.app.pactoapi.commons.ResultPageDto;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModelMapperService {
    private final ModelMapper modelMapper = new ModelMapper();

    public <T> List<T> toList(Class<T> clazz, List<?> items) {
        return items.stream()
                .map(item -> toObject(clazz, item))
                .toList();
    }

    public <T> T toObject(Class<T> clazz, Object item) {
        if (item == null) return null;
        return modelMapper.map(item, clazz);
    }

    public <T> ResultPageDto<T> toPage(Class<T> clazz, Page<?> page) {
        if (page == null) return null;

        return new ResultPageDto<>(
                page.getTotalElements(),
                page.getTotalPages(),
                page.getNumber(),
                toList(clazz, page.getContent())
        );
    }
}
