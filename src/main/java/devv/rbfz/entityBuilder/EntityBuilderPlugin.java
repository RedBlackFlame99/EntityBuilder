package devv.rbfz.entityBuilder;

import devv.rbfz.entityBuilder.builder.EntityBuilder;
import devv.rbfz.entityBuilder.commands.EntityBuilderCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public final class EntityBuilderPlugin extends JavaPlugin {

    public static Map<Player, EntityBuilder> builders = new HashMap<>();

    @Override
    public void onEnable() {
        getCommand("entitybuilder").setExecutor(new EntityBuilderCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
