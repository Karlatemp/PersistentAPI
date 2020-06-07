/*
 * Copyright (c) 2018-2020 Karlatemp. All rights reserved.
 * @author Karlatemp <karlatemp@vip.qq.com> <https://github.com/Karlatemp>
 * @create 2020/06/07 10:47:05
 *
 * PersistentApi/PersistentApi.main/BasicPersistentDataContainer.java
 */

package io.github.karlatemp.persistentapi.basic;

import io.github.karlatemp.persistentapi.NamespacedKey;
import io.github.karlatemp.persistentapi.PersistentDataAdapterContext;
import io.github.karlatemp.persistentapi.PersistentDataContainer;
import io.github.karlatemp.persistentapi.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BasicPersistentDataContainer implements PersistentDataContainer {
    final org.bukkit.persistence.PersistentDataContainer parent;
    private final PersistentDataAdapterContext context;
    private static final Class<PersistentDataContainer> PERSISTENT_DATA_CONTAINER_CLASS = PersistentDataContainer.class;

    @SuppressWarnings("deprecation")
    private static org.bukkit.NamespacedKey toBukkit(NamespacedKey key) {
        return new org.bukkit.NamespacedKey(key.getNamespace(), key.getKey());
    }

    @SuppressWarnings("unchecked")
    private static <T, Z> org.bukkit.persistence.PersistentDataType<T, Z> toBukkit(PersistentDataType<T, Z> type) {
        if (type.getPrimitiveType() == PERSISTENT_DATA_CONTAINER_CLASS) {
            return new TCB(type);
        }
        return new BasicPersistentDataType<>(type);
    }

    public BasicPersistentDataContainer(org.bukkit.persistence.PersistentDataContainer parent) {
        this.parent = parent;
        final org.bukkit.persistence.PersistentDataAdapterContext
                context1 = parent.getAdapterContext(),
                context2 = parent.getAdapterContext();
        if (context1 == context2) {
            context = new BasicPersistentDataAdapterContext(context1);
        } else context = null;
    }

    public <T, Z> void set(@NotNull NamespacedKey key, @NotNull PersistentDataType<T, Z> type, @NotNull Z value) {
        parent.set(toBukkit(key), toBukkit(type), value);
    }

    public <T, Z> boolean has(@NotNull NamespacedKey key, @NotNull PersistentDataType<T, Z> type) {
        return parent.has(toBukkit(key), toBukkit(type));
    }

    @Nullable
    public <T, Z> Z get(@NotNull NamespacedKey key, @NotNull PersistentDataType<T, Z> type) {
        return parent.get(toBukkit(key), toBukkit(type));
    }

    @NotNull
    public <T, Z> Z getOrDefault(@NotNull NamespacedKey key, @NotNull PersistentDataType<T, Z> type, @NotNull Z defaultValue) {
        return parent.getOrDefault(toBukkit(key), toBukkit(type), defaultValue);
    }

    @Override
    public void remove(@NotNull NamespacedKey key) {
        parent.remove(toBukkit(key));
    }

    @Override
    public boolean isEmpty() {
        return parent.isEmpty();
    }

    @Override
    public @NotNull PersistentDataAdapterContext getAdapterContext() {
        return context == null ? new BasicPersistentDataAdapterContext(parent.getAdapterContext()) : context;
    }
}
