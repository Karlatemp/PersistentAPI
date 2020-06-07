/*
 * Copyright (c) 2018-2020 Karlatemp. All rights reserved.
 * @author Karlatemp <karlatemp@vip.qq.com> <https://github.com/Karlatemp>
 * @create 2020/06/07 12:06:35
 *
 * PersistentApi/PersistentApi.main/TCB.java
 */

package io.github.karlatemp.persistentapi.basic;

import io.github.karlatemp.persistentapi.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings({"rawtypes", "unchecked"})
public class TCB implements PersistentDataType {
    private final io.github.karlatemp.persistentapi.PersistentDataType type;

    public TCB(io.github.karlatemp.persistentapi.PersistentDataType type) {
        this.type = type;
    }

    @Override
    public @NotNull Class<PersistentDataContainer> getComplexType() {
        return PersistentDataContainer.class;
    }

    @NotNull
    @Override
    public org.bukkit.persistence.PersistentDataContainer toPrimitive(@NotNull Object complex, @NotNull PersistentDataAdapterContext context) {
        return ((BasicPersistentDataContainer) type.toPrimitive(complex, new BasicPersistentDataAdapterContext(context))).parent;
    }

    @NotNull
    @Override
    public Object fromPrimitive(@NotNull Object primitive, @NotNull PersistentDataAdapterContext context) {
        return type.fromPrimitive(new BasicPersistentDataContainer((org.bukkit.persistence.PersistentDataContainer) primitive),
                new BasicPersistentDataAdapterContext(context));
    }

    @Override
    public @NotNull Class<org.bukkit.persistence.PersistentDataContainer> getPrimitiveType() {
        return org.bukkit.persistence.PersistentDataContainer.class;
    }
}
