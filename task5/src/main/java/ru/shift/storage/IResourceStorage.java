package ru.shift.storage;

import ru.shift.entity.Resource;

public interface IResourceStorage {
    void putResource(Resource resource);

    Resource getResource();
}
