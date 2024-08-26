package me.harrylesanto.deathcounter;

import org.jetbrains.annotations.NotNull;

public class DeathDuo implements Comparable<DeathDuo> {
    private String name;
    private int deaths;

    public DeathDuo(String name, int deaths) {
        this.name = name;
        this.deaths = deaths;
    }

    @Override
    public int compareTo(@NotNull DeathDuo other) {
        return other.getDeaths() - deaths;
    }

    public String getName() {
        return name;
    }

    public int getDeaths() {
        return deaths;
    }
}
