package com.whistle6.api.common.query;

import java.util.List;

public interface QueryService<Entity, DTO> {
    List<DTO> findAll();
    DTO findById(Long id);
    Long count();
    Boolean existsById(Long id);
}
