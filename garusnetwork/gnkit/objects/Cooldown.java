/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package garusnetwork.gnkit.objects;

/**
 *
 * @author Jhow
 */
public class Cooldown {

    public int cooldown;
    private final Kit kit;

    public Cooldown(Kit kit, int cooldown) {
        this.cooldown = cooldown;
        this.kit = kit;
    }

    public int getCooldown() {
        return this.cooldown;
    }

    public Kit getKit() {
        return this.kit;
    }
}
