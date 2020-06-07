/*
 * Copyright (c) 2018-2020 Karlatemp. All rights reserved.
 * @author Karlatemp <karlatemp@vip.qq.com> <https://github.com/Karlatemp>
 * @create 2020/06/07 11:11:16
 *
 * PersistentApi/PersistentApi.main/PersistentDataTypeRegistry.java
 */

package io.github.karlatemp.persistentapi.nms;

import org.apache.commons.lang.Validate;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings({"rawtypes", "unchecked"})
public class PersistentDataTypeRegistry {
    private static final BiFunction<PersistentDataTypeRegistry, Class, TagAdapter> CREATE_ADAPTER0;
    public static final PersistentDataTypeRegistry REGISTRY = new PersistentDataTypeRegistry();
    public static final Supplier<Object> NEW_TAB_C;

    static {
        try {
            CREATE_ADAPTER0 = Class.forName("io.github.karlatemp.persistentapi.nms." + NMSBinder.VERSION +
                    ".Registry").asSubclass(BiFunction.class).newInstance();
            NEW_TAB_C = (Supplier) CREATE_ADAPTER0;
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private final Function<Class, TagAdapter> CREATE_ADAPTER = v -> CREATE_ADAPTER0.apply(this, v);
    private final Map<Class, TagAdapter> adapters = new ConcurrentHashMap<>();

    public PersistentDataTypeRegistry() {
    }

    public static <T, Z> TagAdapter<T, Z> createAdapter(Class<T> primitiveType, Class<Z> nbtBaseType, Function<T, Z> builder, Function<Z, T> extractor) {
        return new TagAdapter<>(primitiveType, nbtBaseType, builder, extractor);
    }

    public <T> Object wrap(Class<T> type, T value) {
        return this.adapters.computeIfAbsent(type, CREATE_ADAPTER).build(value);
    }

    public <T> boolean isInstanceOf(Class<T> type, Object base) {
        return this.adapters.computeIfAbsent(type, CREATE_ADAPTER).isInstance(base);
    }

    public <T> T extract(Class<T> type, Object tag) throws ClassCastException, IllegalArgumentException {
        TagAdapter adapter = this.adapters.computeIfAbsent(type, CREATE_ADAPTER);
        Validate.isTrue(adapter.isInstance(tag), String.format("`The found tag instance cannot store %s as it is a %s", type.getSimpleName(), tag.getClass().getSimpleName()));
        Object foundValue = adapter.extract(tag);
        Validate.isTrue(type.isInstance(foundValue), String.format("The found object is of the type %s. Expected type %s", foundValue.getClass().getSimpleName(), type.getSimpleName()));
        return type.cast(foundValue);
    }

}
/*

    private <T> CraftPersistentDataTypeRegistry.TagAdapter createAdapter(Class<T> type) {
        if (!Primitives.isWrapperType(type)) {
            type = Primitives.wrap(type);
        }

        if (Objects.equals(Byte.class, type)) {
            return this.createAdapter(Byte.class, NBTTagByte.class, NBTTagByte::a, NBTTagByte::asByte);
        } else if (Objects.equals(Short.class, type)) {
            return this.createAdapter(Short.class, NBTTagShort.class, NBTTagShort::a, NBTTagShort::asShort);
        } else if (Objects.equals(Integer.class, type)) {
            return this.createAdapter(Integer.class, NBTTagInt.class, NBTTagInt::a, NBTTagInt::asInt);
        } else if (Objects.equals(Long.class, type)) {
            return this.createAdapter(Long.class, NBTTagLong.class, NBTTagLong::a, NBTTagLong::asLong);
        } else if (Objects.equals(Float.class, type)) {
            return this.createAdapter(Float.class, NBTTagFloat.class, NBTTagFloat::a, NBTTagFloat::asFloat);
        } else if (Objects.equals(Double.class, type)) {
            return this.createAdapter(Double.class, NBTTagDouble.class, NBTTagDouble::a, NBTTagDouble::asDouble);
        } else if (Objects.equals(String.class, type)) {
            return this.createAdapter(String.class, NBTTagString.class, NBTTagString::a, NBTTagString::asString);
        } else if (Objects.equals(byte[].class, type)) {
            return this.createAdapter(byte[].class, NBTTagByteArray.class, (array) -> {
                return new NBTTagByteArray(Arrays.copyOf(array, array.length));
            }, (n) -> {
                return Arrays.copyOf(n.getBytes(), n.size());
            });
        } else if (Objects.equals(int[].class, type)) {
            return this.createAdapter(int[].class, NBTTagIntArray.class, (array) -> {
                return new NBTTagIntArray(Arrays.copyOf(array, array.length));
            }, (n) -> {
                return Arrays.copyOf(n.getInts(), n.size());
            });
        } else if (Objects.equals(long[].class, type)) {
            return this.createAdapter(long[].class, NBTTagLongArray.class, (array) -> {
                return new NBTTagLongArray(Arrays.copyOf(array, array.length));
            }, (n) -> {
                return Arrays.copyOf(n.getLongs(), n.size());
            });
        } else if (Objects.equals(PersistentDataContainer.class, type)) {
            return this.createAdapter(CraftPersistentDataContainer.class, NBTTagCompound.class, CraftPersistentDataContainer::toTagCompound, (tag) -> {
                CraftPersistentDataContainer container = new CraftPersistentDataContainer(this);
                Iterator var4 = tag.getKeys().iterator();

                while(var4.hasNext()) {
                    String key = (String)var4.next();
                    container.put(key, tag.get(key));
                }

                return container;
            });
        } else {
            throw new IllegalArgumentException("Could not find a valid TagAdapter implementation for the requested type " + type.getSimpleName());
        }
    }

 */