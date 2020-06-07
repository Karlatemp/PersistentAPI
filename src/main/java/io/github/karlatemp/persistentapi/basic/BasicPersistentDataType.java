/*
 * Copyright (c) 2018-2020 Karlatemp. All rights reserved.
 * @author Karlatemp <karlatemp@vip.qq.com> <https://github.com/Karlatemp>
 * @create 2020/06/07 10:45:32
 *
 * PersistentApi/PersistentApi.main/BasicPersistentDataType.java
 */

package io.github.karlatemp.persistentapi.basic;

import io.github.karlatemp.persistentapi.PersistentDataType;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.jetbrains.annotations.NotNull;

public class BasicPersistentDataType<T, Z> implements org.bukkit.persistence.PersistentDataType<T, Z> {
    private final PersistentDataType<T, Z> parent;

    public BasicPersistentDataType(@NotNull PersistentDataType<T, Z> parent) {
        this.parent = parent;
    }

    @Override
    @NotNull
    public Class<T> getPrimitiveType() {
        return parent.getPrimitiveType();
    }

    @Override
    @NotNull
    public Class<Z> getComplexType() {
        return parent.getComplexType();
    }

    @Override
    @NotNull
    public T toPrimitive(@NotNull Z complex, @NotNull PersistentDataAdapterContext context) {
        return parent.toPrimitive(complex, new BasicPersistentDataAdapterContext(context));
    }

    @Override
    @NotNull
    public Z fromPrimitive(@NotNull T primitive, @NotNull PersistentDataAdapterContext context) {
        return parent.fromPrimitive(primitive, new BasicPersistentDataAdapterContext(context));
    }
}
