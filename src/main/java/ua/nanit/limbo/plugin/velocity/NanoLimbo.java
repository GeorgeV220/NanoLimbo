package ua.nanit.limbo.plugin.velocity;

import co.aikar.commands.VelocityCommandManager;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;

import javax.inject.Inject;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import ua.nanit.limbo.plugin.MainCommands;
import ua.nanit.limbo.server.LimboServer;

import java.nio.file.Path;

@SuppressWarnings("ALL")
@Plugin(id = "nanolimbo",
        name = "NanoLimbo",
        version = "${version}",
        url = "https://github.com/GeorgeV220/NanoLimbo",
        authors = {"GeorgeV220"})
public class NanoLimbo {


    private final ProxyServer server;
    private final Logger logger;
    private final Path dataDirectory;
    private VelocityCommandManager commandManager;
    private LimboServer limboServer;

    @Inject
    public NanoLimbo(ProxyServer server, @NotNull Logger logger, @DataDirectory @NotNull Path dataDirectory) {
        this.server = server;
        this.logger = logger;
        this.dataDirectory = dataDirectory;
        logger.info("Loading NanoLimbo...");
        logger.info("Data directory: {}", dataDirectory.toString());
        if (!dataDirectory.toFile().exists()) {
            dataDirectory.toFile().mkdirs();
        }

    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        try {
            this.limboServer = new LimboServer();
            this.limboServer.start(dataDirectory);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        this.commandManager = new VelocityCommandManager(server, this, dataDirectory.toFile());
        this.commandManager.registerCommand(new MainCommands(this.limboServer));
    }

    @Subscribe
    public void onProxyShutdown(ProxyShutdownEvent event) {
        this.limboServer.stop();
    }

    public Logger getLogger() {
        return logger;
    }
}
