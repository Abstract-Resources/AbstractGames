package dev.abstractgames.factory;

import lombok.Getter;

public final class ArenaFactory {

    @Getter private final static ArenaFactory instance = new ArenaFactory();

    public void init() {

    }
}