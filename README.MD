# PersistentAPI

-----

Here is PersistentAPI.

Only ItemMeta is supported, 
because TileEntity and/or Entity do not have any extended storage

## How to use

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

