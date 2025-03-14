package devv.rbfz.entityBuilder.commands;

import devv.rbfz.entityBuilder.builder.EntityBuilder;
import devv.rbfz.entityBuilder.builder.EntityItem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static devv.rbfz.entityBuilder.EntityBuilderPlugin.builders;

public class EntityBuilderCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can execute this command!");
            return true;
        }

        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("create")) {
                if (args.length > 1) {
                    String input = args[1];
                    EntityType type;
                    try {
                        type = EntityType.valueOf(input);
                    } catch (IllegalArgumentException e) {
                        type = null;
                    }
                    if (type == null) {
                        player.sendMessage("Invalid ENTITY TYPE");
                        return true;
                    }
                    EntityBuilder builder = new EntityBuilder(type);
                    builders.put(player, builder);
                    player.sendMessage("You are now building an entity! (" + type + ")");
                }
            } else if (args[0].equalsIgnoreCase("build")) {
                if (!builders.containsKey(player)) {
                    player.sendMessage("You are not building an entity!");
                    return true;
                }
                EntityBuilder builder = builders.get(player);
                EntityItem result = builder.build();
                if (!result.isSuccessful()) {
                    player.sendMessage("The build was not successful. Try contacting the owner of this plugin!");
                    return true;
                }

                if (!result.getMessages().isEmpty()) {
                    for (String msg : result.getMessages()) {
                        player.sendMessage(msg);
                    }
                    return true;
                }

                if (result.getBuiltItem() == null) {
                    player.sendMessage("The build was not successful.");
                    return true;
                }

                player.getInventory().addItem(result.getBuiltItem());
            } else if (args[0].equalsIgnoreCase("edit")) {
                if (!builders.containsKey(player)) {
                    player.sendMessage("You are not building an entity!");
                    return true;
                }
                EntityBuilder builder = builders.get(player);
                if (args.length > 1) {
                    String meta = args[1];
                    if (args.length > 2) {
                        String rawValue = args[2];
                        String flag = "";
                        if (args.length > 3) {
                            flag = args[3];
                        }

                        Object value;
                        if (flag.equalsIgnoreCase("-int")) {
                            try {
                                value = Integer.parseInt(rawValue);
                            } catch (NumberFormatException e) {
                                value = 1;
                            }
                        } else if (flag.equalsIgnoreCase("-boolean")) {
                            value = Boolean.parseBoolean(rawValue);
                        }
                        else {
                            value = rawValue;
                        }
                        builder.setMetaValue(meta, value);
                    }
                }
            }
        }


        return false;
    }
}
