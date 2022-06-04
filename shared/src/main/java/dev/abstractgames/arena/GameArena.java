package dev.abstractgames.arena;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor @Getter
public final class GameArena {

    private final int id;
    private final GameMap map;


}