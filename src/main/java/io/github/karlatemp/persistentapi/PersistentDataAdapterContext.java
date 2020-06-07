/*
 * Copyright (c) 2018-2020 Karlatemp. All rights reserved.
 * @author Karlatemp <karlatemp@vip.qq.com> <https://github.com/Karlatemp>
 * @create 2020/06/07 10:57:40
 *
 * PersistentApi/PersistentApi.main/PersistentDataAdapterContext.java
 */

package io.github.karlatemp.persistentapi;

import org.jetbrains.annotations.NotNull;

/**
 * This interface represents the context in which the {@link PersistentDataType} can
 * serialize and deserialize the passed values.
 */
public interface PersistentDataAdapterContext {

    /**
     * Creates a new and empty meta container instance.
     *
     * @return the fresh container instance
     */
    @NotNull
    PersistentDataContainer newPersistentDataContainer();
}
