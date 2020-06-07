/*
 * Copyright (c) 2018-2020 Karlatemp. All rights reserved.
 * @author Karlatemp <karlatemp@vip.qq.com> <https://github.com/Karlatemp>
 * @create 2020/06/07 11:47:52
 *
 * PersistentApi/PersistentApi.main/NMSPersistentDataContainer.java
 */

package io.github.karlatemp.persistentapi.nms;

import io.github.karlatemp.persistentapi.NamespacedKey;
import io.github.karlatemp.persistentapi.PersistentDataAdapterContext;
import io.github.karlatemp.persistentapi.PersistentDataContainer;
import io.github.karlatemp.persistentapi.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class NMSPersistentDataContainer implements PersistentDataContainer {
    private Map<String, Object> tags;
    private final PersistentDataTypeRegistry registry;
    private final NMSPDC adapterContext;

    public NMSPersistentDataContainer() {
        this(PersistentDataTypeRegistry.REGISTRY);
    }

    public NMSPersistentDataContainer(PersistentDataTypeRegistry registry) {
        this.registry = registry;
        this.adapterContext = new NMSPDC(registry);
        tags = new HashMap<>();
    }

    public Map<String, Object> getTags() {
        return tags;
    }

    public void setTags(Map<String, Object> tags) {
        this.tags = tags;
    }

    @Override
    public <T, Z> void set(@NotNull NamespacedKey key, @NotNull PersistentDataType<T, Z> type, @NotNull Z value) {
        put(key.toString(), registry.wrap(type.getPrimitiveType(), type.toPrimitive(value, getAdapterContext())));
    }

    public void put(String key, Object val) {
        tags.put(key, val);
    }

    @Override
    public <T, Z> boolean has(@NotNull NamespacedKey key, @NotNull PersistentDataType<T, Z> type) {
        final Object o = tags.get(key.toString());
        return o != null && registry.isInstanceOf(type.getPrimitiveType(), o);
    }

    @Override
    public <T, Z> @Nullable Z get(@NotNull NamespacedKey key, @NotNull PersistentDataType<T, Z> type) {
        final Object o = tags.get(key.toString());
        return registry.isInstanceOf(type.getPrimitiveType(), o)
                ? type.fromPrimitive(this.registry.extract(type.getPrimitiveType(), o), adapterContext)
                : null;
    }

    @Override
    public <T, Z> @NotNull Z getOrDefault(@NotNull NamespacedKey key, @NotNull PersistentDataType<T, Z> type, @NotNull Z defaultValue) {
        final Object o = tags.get(key.toString());
        return registry.isInstanceOf(type.getPrimitiveType(), o)
                ? type.fromPrimitive(this.registry.extract(type.getPrimitiveType(), o), adapterContext)
                : defaultValue;
    }

    @Override
    public void remove(@NotNull NamespacedKey key) {
        tags.remove(key.toString());
    }

    @Override
    public boolean isEmpty() {
        return tags.isEmpty();
    }

    @Override
    public @NotNull PersistentDataAdapterContext getAdapterContext() {
        return adapterContext;
    }
}
