package devv.rbfz.entityBuilder.builder;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public class EntityItem {
    private final boolean success;
    private final List<String> messages;
    private final ItemStack item;

    public EntityItem(boolean success, List<String> messages, ItemStack item) {
        this.success = success;
        this.messages = messages;
        this.item = item;
    }

    public boolean isSuccessful() {
        return success;
    }
    public List<String> getMessages() {
        return messages;
    }
    public ItemStack getBuiltItem() {
        return item;
    }
}
