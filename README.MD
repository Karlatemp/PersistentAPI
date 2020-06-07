# PersistentAPI

-----


ItemMeta:
```text
ItemMeta meta = hand.getItemMeta();
final PersistentDataContainer container = getPersistentDataContainer(meta);
sender.sendMessage(
        String.valueOf(
                container.get(NamespacedKey.minecraft("test"), PersistentDataType.STRING)
        )
);
container.set(NamespacedKey.minecraft("test"), PersistentDataType.STRING, "Hello World!");
hand.setItemMeta(meta);
```

