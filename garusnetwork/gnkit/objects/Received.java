/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package garusnetwork.gnkit.objects;

import java.util.HashMap;

/**
 *
 * @author Jhow
 */
public class Received {

    private final String name;
    public int cooldown_time;
    private final HashMap<Kit, Cooldown> kits;

    public Received(String name) {
        this.name = name;
        this.kits = new HashMap<>();
    }

    public void addKit(Cooldown cooldown) {
        this.kits.put(cooldown.getKit(), cooldown);
    }

    public String getName() {
        return this.name;
    }

    public HashMap<Kit, Cooldown> getUsedKits() {
        return this.kits;
    }

}
