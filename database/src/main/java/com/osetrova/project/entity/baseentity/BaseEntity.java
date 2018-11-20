package com.osetrova.project.entity.baseentity;

import java.io.Serializable;

public interface BaseEntity<T extends Serializable> {

    T getId();
}
