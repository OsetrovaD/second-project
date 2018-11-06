package project.entity.enumonly;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum GamePlatform {
    PC("PC"),
    XBOX_ONE("Xbox One"),
    XBOX_360("Xbox 360"),
    PLAYSTATION_4("PlayStation 4"),
    PLAYSTATION_3("PlayStation 3"),
    NINTENDO_WII_U("Nintendo Wii U"),
    NINTENDO_SWITCH("Nintendo Switch");

    private String name;

    GamePlatform(String name) {
        this.name = name;
    }

    public static GamePlatform getByName(String name) {
        return Arrays.stream(values())
                .filter(n -> n.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}
