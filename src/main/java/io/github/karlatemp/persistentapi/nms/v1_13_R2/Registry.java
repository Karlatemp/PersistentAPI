/*
 * Copyright (c) 2018-2020 Karlatemp. All rights reserved.
 * @author Karlatemp <karlatemp@vip.qq.com> <https://github.com/Karlatemp>
 * @create 2020/06/07 11:35:57
 *
 * PersistentApi/PersistentApi.main/Registry.java
 */

package io.github.karlatemp.persistentapi.nms.v1_13_R2;

import com.google.common.primitives.Primitives;
import io.github.karlatemp.persistentapi.nms.NMSPersistentDataContainer;
import io.github.karlatemp.persistentapi.nms.PersistentDataTypeRegistry;
import io.github.karlatemp.persistentapi.nms.TagAdapter;
import net.minecraft.server.v1_13_R2.*;
import org.bukkit.persistence.PersistentDataContainer;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Supplier;

@SuppressWarnings({"rawtypes", "DuplicatedCode"})
public class Registry implements BiFunction<PersistentDataTypeRegistry, Class, TagAdapter>, Supplier {
    @Override
    public Object get() {
        return new NBTTagCompound();
    }

    public TagAdapter apply(PersistentDataTypeRegistry registry, Class type) {
        if (!Primitives.isWrapperType(type)) {
            type = Primitives.wrap(type);
        }

        if (Objects.equals(Byte.class, type)) {
            return PersistentDataTypeRegistry.createAdapter(Byte.class, NBTTagByte.class, NBTTagByte::new, NBTTagByte::asByte);
        } else if (Objects.equals(Short.class, type)) {
            return PersistentDataTypeRegistry.createAdapter(Short.class, NBTTagShort.class, NBTTagShort::new, NBTTagShort::asShort);
        } else if (Objects.equals(Integer.class, type)) {
            return PersistentDataTypeRegistry.createAdapter(Integer.class, NBTTagInt.class, NBTTagInt::new, NBTTagInt::asInt);
        } else if (Objects.equals(Long.class, type)) {
            return PersistentDataTypeRegistry.createAdapter(Long.class, NBTTagLong.class, NBTTagLong::new, NBTTagLong::asLong);
        } else if (Objects.equals(Float.class, type)) {
            return PersistentDataTypeRegistry.createAdapter(Float.class, NBTTagFloat.class, NBTTagFloat::new, NBTTagFloat::asFloat);
        } else if (Objects.equals(Double.class, type)) {
            return PersistentDataTypeRegistry.createAdapter(Double.class, NBTTagDouble.class, NBTTagDouble::new, NBTTagDouble::asDouble);
        } else if (Objects.equals(String.class, type)) {
            return PersistentDataTypeRegistry.createAdapter(String.class, NBTTagString.class, NBTTagString::new, NBTTagString::asString);
        } else if (Objects.equals(byte[].class, type)) {
            return PersistentDataTypeRegistry.createAdapter(byte[].class, NBTTagByteArray.class, (array) -> {
                return new NBTTagByteArray(Arrays.copyOf(array, array.length));
            }, (n) -> {
                final byte[] c = n.c();
                return Arrays.copyOf(c, c.length);
            });
        } else if (Objects.equals(int[].class, type)) {
            return PersistentDataTypeRegistry.createAdapter(int[].class, NBTTagIntArray.class, (array) -> {
                return new NBTTagIntArray(Arrays.copyOf(array, array.length));
            }, (n) -> {
                final int[] c = n.d();
                return Arrays.copyOf(c, c.length);
            });
        } else if (Objects.equals(PersistentDataContainer.class, type)) {
            return PersistentDataTypeRegistry.createAdapter(
                    NMSPersistentDataContainer.class, NBTTagCompound.class,
                    con -> {
                        NBTTagCompound tc = new NBTTagCompound();
                        final Map<String, Object> tags = con.getTags();
                        for (Map.Entry<String, Object> tag : tags.entrySet()) {
                            tc.set(tag.getKey(), (NBTBase) tag.getValue());
                        }
                        return tc;
                    }, (tag) -> {
                        NMSPersistentDataContainer container = new NMSPersistentDataContainer();

                        for (Object o : tag.getKeys()) {
                            String key = (String) o;
                            container.put(key, tag.get(key));
                        }

                        return container;
                    });
        } else {
            throw new IllegalArgumentException("Could not find a valid TagAdapter implementation for the requested type " + type.getSimpleName());
        }
    }

}
