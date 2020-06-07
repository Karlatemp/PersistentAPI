/*
 * Copyright (c) 2018-2020 Karlatemp. All rights reserved.
 * @author Karlatemp <karlatemp@vip.qq.com> <https://github.com/Karlatemp>
 * @create 2020/06/07 11:08:01
 *
 * PersistentApi/PersistentApi.main/TagAdapter.java
 */

package io.github.karlatemp.persistentapi.nms;

import org.apache.commons.lang.Validate;

import java.util.function.Function;

public class TagAdapter<T, Z> {
    private final Function<T, Z> builder;
    private final Function<Z, T> extractor;
    private final Class<T> primitiveType;
    private final Class<Z> nbtBaseType;

    public TagAdapter(Class<T> primitiveType, Class<Z> nbtBaseType, Function<T, Z> builder, Function<Z, T> extractor) {
        this.primitiveType = primitiveType;
        this.nbtBaseType = nbtBaseType;
        this.builder = builder;
        this.extractor = extractor;
    }

    T extract(Object base) {
        Validate.isTrue(this.nbtBaseType.isInstance(base), String.format("The provided NBTBase was of the type %s. Expected type %s", base.getClass().getSimpleName(), this.nbtBaseType.getSimpleName()));
        return this.extractor.apply(this.nbtBaseType.cast(base));
    }

    Z build(Object value) {
        Validate.isTrue(this.primitiveType.isInstance(value), String.format("The provided value was of the type %s. Expected type %s", value.getClass().getSimpleName(), this.primitiveType.getSimpleName()));
        return this.builder.apply(this.primitiveType.cast(value));
    }

    boolean isInstance(Object base) {
        return this.nbtBaseType.isInstance(base);
    }
}
