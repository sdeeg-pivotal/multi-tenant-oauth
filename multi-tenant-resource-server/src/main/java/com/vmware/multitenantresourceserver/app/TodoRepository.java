package com.vmware.multitenantresourceserver.app;

import java.util.List;
import java.util.UUID;

public interface TodoRepository {
    Todo create(Todo todo);
    List<Todo> findAll();
    void deleteById(UUID id);
}
