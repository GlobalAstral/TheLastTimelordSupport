package me.globalastral.the_last_timelord_support.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import me.globalastral.the_last_timelord_support.LoreBookItem;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.Collection;

public class IncrementLoreLevelCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands
                .literal("inclorelvl")
                .requires(commandSourceStack -> commandSourceStack.hasPermission(Commands.LEVEL_GAMEMASTERS))
                .then(Commands.argument("targets", EntityArgument.players())
                .then(Commands.argument("amount", IntegerArgumentType.integer())
                .executes(commandContext -> increment_lore_level(commandContext.getSource(), IntegerArgumentType.getInteger(commandContext, "amount"), EntityArgument.getPlayers(commandContext, "targets")))))
        );
    }

    private static int increment_lore_level(CommandSourceStack pSource, int amount, Collection<ServerPlayer> pTargets) {
        for (ServerPlayer player : pTargets) {
            CompoundTag nbt = player.getPersistentData();
            if (!nbt.contains(LoreBookItem.LORE_LEVEL_KEY))
                nbt.putInt(LoreBookItem.LORE_LEVEL_KEY, 0);
            int current = nbt.getInt(LoreBookItem.LORE_LEVEL_KEY);
            current += amount;
            nbt.putInt(LoreBookItem.LORE_LEVEL_KEY, current);
        }

        for (ServerPlayer player : pTargets) {
            player.sendSystemMessage(Component.translatable("commands.inclorelvl.success", amount), true);
        }
        return 1;
    }
}
