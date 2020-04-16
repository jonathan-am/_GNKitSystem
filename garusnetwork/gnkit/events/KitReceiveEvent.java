/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package garusnetwork.gnkit.events;

import garusnetwork.gnkit.objects.Kit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 *
 * @author Jhow
 */
public class KitReceiveEvent extends Event {

    private final Kit kit;
    private final Player player;
    private boolean cancelled;

    public KitReceiveEvent(Kit kit, Player player) {
        this.kit = kit;
        this.player = player;
    }

    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public Kit getKit() {
        return this.kit;
    }

    public Player getPlayer() {
        return this.player;
    }

    private static final HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
