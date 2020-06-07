/*
 * Copyright (c) 2018-2020 Karlatemp. All rights reserved.
 * @author Karlatemp <karlatemp@vip.qq.com> <https://github.com/Karlatemp>
 * @create 2020/06/07 10:59:04
 *
 * PersistentApi/PersistentApi.main/BasicPersistentDataAdapterContext.java
 */

package io.github.karlatemp.persistentapi.basic;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.jetbrains.annotations.NotNull;

public class BasicPersistentDataAdapterContext implements io.github.karlatemp.persistentapi.PersistentDataAdapterContext {
    private final PersistentDataAdapterContext parent;

    public BasicPersistentDataAdapterContext(PersistentDataAdapterContext parent) {
        this.parent = parent;
    }

    @Override
    public @NotNull io.github.karlatemp.persistentapi.PersistentDataContainer newPersistentDataContainer() {
        return new BasicPersistentDataContainer(parent.newPersistentDataContainer());
    }
}
