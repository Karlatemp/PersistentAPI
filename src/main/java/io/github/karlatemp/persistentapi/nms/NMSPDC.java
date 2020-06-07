/*
 * Copyright (c) 2018-2020 Karlatemp. All rights reserved.
 * @author Karlatemp <karlatemp@vip.qq.com> <https://github.com/Karlatemp>
 * @create 2020/06/07 11:55:01
 *
 * PersistentApi/PersistentApi.main/NMSPDC.java
 */

package io.github.karlatemp.persistentapi.nms;

import io.github.karlatemp.persistentapi.PersistentDataAdapterContext;
import io.github.karlatemp.persistentapi.PersistentDataContainer;
import org.jetbrains.annotations.NotNull;

public class NMSPDC implements PersistentDataAdapterContext {
    private final PersistentDataTypeRegistry registry;

    public NMSPDC(PersistentDataTypeRegistry registry) {
        this.registry = registry;
    }

    @Override
    public @NotNull PersistentDataContainer newPersistentDataContainer() {
        return new NMSPersistentDataContainer(this.registry);
    }
}
