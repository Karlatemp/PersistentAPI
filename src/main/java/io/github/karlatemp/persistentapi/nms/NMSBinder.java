/*
 * Copyright (c) 2018-2020 Karlatemp. All rights reserved.
 * @author Karlatemp <karlatemp@vip.qq.com> <https://github.com/Karlatemp>
 * @create 2020/06/07 11:30:36
 *
 * PersistentApi/PersistentApi.main/NMSBinder.java
 */

package io.github.karlatemp.persistentapi.nms;

import org.bukkit.Bukkit;

public class NMSBinder {
    public static final String VERSION;

    static {
        String path = Bukkit.getServer().getClass().getName();
        int last = path.lastIndexOf('.');
        String pck = path.substring(0, last);
        int verSplit = pck.lastIndexOf('.');
        VERSION = pck.substring(verSplit + 1);
    }
}
