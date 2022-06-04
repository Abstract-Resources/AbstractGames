package dev.abstractgames.object;

import cn.nukkit.level.Location;
import com.google.common.collect.ImmutableMap;
import dev.abstractgames.utils.GameUtils;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

@RequiredArgsConstructor @Getter
public final class GameSpawn implements Serializable {

    private final int slot;
    @Setter private @NonNull Location location;

    public Map<String, Object> serialize() {
        return ImmutableMap.<String, Object>builder()
                .put("slot", this.slot)
                .put("location", GameUtils.locationToString(this.location))
                .build();
    }

    public static GameSpawn deserialize(Map<String, Object> serialized) {
        return new GameSpawn(
                (Integer) serialized.get("slot"),
                GameUtils.stringToLocation((String) serialized.get("location"))
        );
    }
}