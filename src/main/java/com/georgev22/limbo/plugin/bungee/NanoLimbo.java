package com.georgev22.limbo.plugin.bungee;

import co.aikar.commands.BungeeCommandManager;
import com.georgev22.limbo.plugin.commands.MainCommands;
import net.md_5.bungee.api.plugin.Plugin;
import ua.nanit.limbo.server.LimboServer;

public class NanoLimbo extends Plugin {

    private LimboServer limboServer;

    @Override
    public void onEnable() {
        this.limboServer = new LimboServer();
        BungeeCommandManager commandManager = new BungeeCommandManager(this);
        try {
            this.limboServer.start(this.getDataFolder().toPath());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        commandManager.registerCommand(new MainCommands(this.limboServer));
    }

    @Override
    public void onDisable() {
        this.limboServer.stop();
    }
}
