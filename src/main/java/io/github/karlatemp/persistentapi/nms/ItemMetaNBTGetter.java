/*
 * Copyright (c) 2018-2020 Karlatemp. All rights reserved.
 * @author Karlatemp <karlatemp@vip.qq.com> <https://github.com/Karlatemp>
 * @create 2020/06/07 12:17:56
 *
 * PersistentApi/PersistentApi.main/ItemMetaNBTGetter.java
 */

package io.github.karlatemp.persistentapi.nms;

import io.github.karlatemp.persistentapi.PersistentDataContainer;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;

public class ItemMetaNBTGetter {
    static final Field unhandledTags;
    static final Field TC$map;

    static {
        try {
            final Class<?> metaClass = Class.forName("org.bukkit.craftbukkit." + NMSBinder.VERSION + ".inventory.CraftMetaItem");
            unhandledTags = metaClass.getDeclaredField("unhandledTags");
            unhandledTags.setAccessible(true);
            Class<?> NBTTagCClass = Class.forName("net.minecraft.server." + NMSBinder.VERSION + ".NBTTagCompound");
            Field f = null;
            for (Field x : NBTTagCClass.getDeclaredFields()) {
                if (!Modifier.isStatic(x.getModifiers())) {
                    if (Map.class.isAssignableFrom(x.getType())) {
                        f = x;
                        break;
                    }
                }
            }
            if (f == null) throw new ExceptionInInitializerError("No NBTTagCompound$map found");
            f.setAccessible(true);
            TC$map = f;
        } catch (Throwable any) {
            throw new ExceptionInInitializerError(any);
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static PersistentDataContainer getTags(ItemMeta meta) {
        try {
            final Map<String, Object> o = (Map) unhandledTags.get(meta);
            // PublicBukkitValues
            final Object values = o.get("PublicBukkitValues");
            final NMSPersistentDataContainer container = new NMSPersistentDataContainer();
            if (values != null) {
                container.setTags((Map) TC$map.get(values));
                return container;
            }
            Object tg = PersistentDataTypeRegistry.NEW_TAB_C.get();
            o.put("PublicBukkitValues", tg);
            container.setTags((Map) TC$map.get(tg));
            return container;
        } catch (IllegalAccessException e) {
            throw new RuntimeException();
        }
    }
}
