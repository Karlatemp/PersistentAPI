/*
 * Copyright (c) 2018-2020 Karlatemp. All rights reserved.
 * @author Karlatemp <karlatemp@vip.qq.com> <https://github.com/Karlatemp>
 * @create 2020/06/07 10:45:05
 *
 * PersistentApi/PersistentApi.main/PersistentAPI.java
 */

package io.github.karlatemp.persistentapi;

import io.github.karlatemp.persistentapi.basic.BasicPersistentDataContainer;
import io.github.karlatemp.persistentapi.nms.ItemMetaNBTGetter;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class PersistentAPI extends JavaPlugin {
    private static final Function<ItemMeta, PersistentDataContainer> GET_PersistentDataContainer;
    private static final boolean DEBUG = System.getProperty("PersistentAPI.debug") != null;

    static {
        Function<ItemMeta, PersistentDataContainer> a;
        try {
            Class.forName("org.bukkit.persistence.PersistentDataContainer");
            //noinspection Convert2Lambda
            a = new Function<ItemMeta, PersistentDataContainer>() {
                @Override
                public PersistentDataContainer apply(ItemMeta itemMeta) {
                    return new BasicPersistentDataContainer(itemMeta.getPersistentDataContainer());
                }
            };
        } catch (Throwable ignored) {
            a = ItemMetaNBTGetter::getTags;
        }
        GET_PersistentDataContainer = a;
    }

    public static PersistentDataContainer getPersistentDataContainer(ItemMeta meta) {
        return GET_PersistentDataContainer.apply(meta);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!DEBUG) return true;
        if (sender instanceof Player) {
            Player p = (Player) sender;
            final ItemStack hand = p.getInventory().getItemInMainHand();
            if (hand.getType() != Material.AIR) {
                ItemMeta meta = hand.getItemMeta();
                final PersistentDataContainer container = getPersistentDataContainer(meta);
                sender.sendMessage(
                        String.valueOf(
                                container.get(NamespacedKey.minecraft("test"), PersistentDataType.STRING)
                        )
                );
                container.set(NamespacedKey.minecraft("test"), PersistentDataType.STRING, "Hello World!");
                hand.setItemMeta(meta);
            }
        }
        return true;
    }
}
