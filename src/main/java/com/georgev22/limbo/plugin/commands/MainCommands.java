package com.georgev22.limbo.plugin.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandIssuer;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import ua.nanit.limbo.BuildConfig;
import ua.nanit.limbo.server.LimboServer;

@CommandAlias("limbo")
@CommandPermission("limbo.admin")
public class MainCommands extends BaseCommand {

    private final LimboServer limboServer;

    public MainCommands(LimboServer limboServer) {
        this.limboServer = limboServer;
    }

    @Default
    public void onHelp(CommandIssuer commandIssuer) {
    }

    @Subcommand("connections")
    public void onConnections(CommandIssuer commandIssuer) {
        commandIssuer.sendMessage("Connections: " + limboServer.getConnections().getCount());
    }

    @Subcommand("memory")
    public void onMemory(CommandIssuer commandIssuer) {
        Runtime runtime = Runtime.getRuntime();
        long mb = 1024 * 1024;
        long used = (runtime.totalMemory() - runtime.freeMemory()) / mb;
        long total = runtime.totalMemory() / mb;
        long free = runtime.freeMemory() / mb;
        long max = runtime.maxMemory() / mb;

        commandIssuer.sendMessage("Memory usage:");
        commandIssuer.sendMessage("Used: " + used + " MB");
        commandIssuer.sendMessage("Total: " + total + " MB");
        commandIssuer.sendMessage("Free: " + free + " MB");
        commandIssuer.sendMessage("Max: " + max + " MB");
    }

    @Subcommand("stop")
    public void onStop(CommandIssuer commandIssuer) {
        limboServer.stop();
    }

    @Subcommand("version")
    public void onVersion(CommandIssuer commandIssuer) {
        commandIssuer.sendMessage("Version: " + BuildConfig.LIMBO_VERSION);
    }

}
