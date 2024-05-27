package com.whistle6.api.common.command;

import com.whistle6.api.common.component.Messenger;

public interface CommandService<Entity, DTO> {
    Messenger save(DTO dto);
    Messenger update(DTO dto);
    Messenger deleteById(Long id);
    Messenger deleteAll();
}
