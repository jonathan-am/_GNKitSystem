/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package garusnetwork.gnkit.events;

import garusnetwork.gnkit.objects.Categoria;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 *
 * @author Jhow
 */
public class CategoriaClickEvent extends Event {

    private final Categoria categoria;
    private final Player player;

    public Categoria getCategoria() {
        return this.categoria;
    }

    public Player getPlayer() {
        return this.player;
    }

    public CategoriaClickEvent(Player player, Categoria categoria) {
        this.categoria = categoria;
        this.player = player;
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
