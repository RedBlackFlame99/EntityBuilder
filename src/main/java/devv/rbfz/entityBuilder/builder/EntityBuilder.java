package devv.rbfz.entityBuilder.builder;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBT;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.NBTComponent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static devv.rbfz.entityBuilder.utils.TextUtils.processColorCodes;

public class EntityBuilder {
    private final EntityType entityType;

    private final Map<String, Object> meta;

    // This list is required for the strings to be "components"
    private final List<String> customStringFlags = List.of("CustomName");

    public EntityBuilder(EntityType entityType) {
        this.entityType = entityType;
        meta = new HashMap<>();
        meta.put("id", entityType.name().toLowerCase());
        meta.put("CustomName", "§dThis was made with §5EntityBuilder §dby §5RedTheDev");
        meta.put("CustomNameVisible", true);
    }

    public EntityItem build() {
        boolean success = true;
        List<String> messages = new ArrayList<>();

        Material material = Bukkit.getServer().getItemFactory().getSpawnEgg(entityType);
        if (material == null) {
            material = Material.ALLAY_SPAWN_EGG;
            messages.add("Failed to get spawn egg for " + entityType + ". Defaulting to ALLAY_SPAWN_EGG");
        }

        ItemStack item = new ItemStack(material);

        NBT.modifyComponents(item, nbt -> {
            ReadWriteNBT entityData = nbt.getOrCreateCompound("minecraft:entity_data");

            for (String key : meta.keySet()) {
                Object value = meta.get(key);
                if (value instanceof String) {
                    value = ((String) value).replace("&", "§");
                    if (customStringFlags.contains(key)) {
                        value = "\"" + value + "\"";
                    }
                    entityData.setString(key, String.valueOf(value));
                } else if (value instanceof Boolean) {
                    entityData.setBoolean(key, (boolean) value);
                } else if (value instanceof Integer) {
                    entityData.setInteger(key, (int) value);
                }
            }

        });
        return new EntityItem(success, messages, item);
    }

    public void setMetaValue(String key, Object value) {
        meta.remove(key);
        meta.put(key, value);
    }

    public Object getMetaValue(String key) {
        meta.getOrDefault(key, null);
        return meta.getOrDefault(key, null);
    }
}
