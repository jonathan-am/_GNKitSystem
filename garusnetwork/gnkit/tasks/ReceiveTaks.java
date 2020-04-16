/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package garusnetwork.gnkit.tasks;

import garusnetwork.gnkit.Main;
import garusnetwork.gnkit.db.ReceivedDB;
import garusnetwork.gnkit.objects.Cooldown;
import garusnetwork.gnkit.objects.Received;
import org.bukkit.scheduler.BukkitRunnable;

/**
 *
 * @author Jhow
 */
public class ReceiveTaks extends BukkitRunnable {

    @Override
    public void run() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Received receive : ReceivedDB.receiveds.values()) {
                    for (Cooldown cooldown : receive.getUsedKits().values()) {
                        cooldown.cooldown--;
                        if (cooldown.cooldown == 0) {
                            receive.getUsedKits().remove(cooldown.getKit());
                            break;
                        }
                    }
                }
            }
        }.runTaskTimerAsynchronously(Main.instance, 20 * 60, 20 * 60);
    }

}
