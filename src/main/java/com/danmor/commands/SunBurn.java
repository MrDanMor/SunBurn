package com.danmor.commands;

import com.danmor.Plugin;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;

import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;

public class SunBurn {
    public static LiteralCommandNode<CommandSourceStack> createCommand(Plugin plugin) {
        return Commands.literal("toggle")
            .requires(source -> source.getSender().isOp())
            .then(Commands.argument("param", StringArgumentType.word())
                .suggests((context, builder) -> {
                    builder.suggest("on");
                    builder.suggest("off");
                    builder.suggest("status");
                    return builder.buildFuture();
                })
                .executes(context -> {
                    CommandSourceStack source = context.getSource();
                    String param = StringArgumentType.getString(context, "param");

                    switch (param) {
                        case "on":
                            plugin.getSession().EnableBurning(source.getSender());
                            return 1;
                        case "off":
                            plugin.getSession().DisableBurning(source.getSender());
                            return 1;
                        case "status":
                            plugin.getSession().printStatus(source.getSender());
                            return 1;
                        default:
                            return 1;
                    }  
                }))
            .build();
    }
}
