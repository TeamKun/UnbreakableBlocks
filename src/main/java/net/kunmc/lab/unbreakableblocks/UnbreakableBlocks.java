package net.kunmc.lab.unbreakableblocks;

import com.sun.scenario.Settings;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.EventListener;


public final class UnbreakableBlocks extends JavaPlugin implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {
        Player placer = event.getPlayer();

        if (placer == null) {
            return;
        }

        if (placer.getGameMode() != GameMode.CREATIVE) {
            Block block = event.getBlock();
            block.setMetadata("placer", new FixedMetadataValue(this, placer.getName()));
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onBreakPlace(BlockBreakEvent event) {
        Player breaker = event.getPlayer();


        if (breaker == null) {
            return;
        }

        Block block = event.getBlock();

        if (breaker.getGameMode() != GameMode.CREATIVE){
            if(!block.getMetadata("placer").isEmpty()){
                event.setCancelled(true);
                breaker.sendMessage("プレイヤーが設置したブロックです！壊すことはできません！");
            }
        }

    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
