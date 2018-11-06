package project.entity.enumonly;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum AgeLimit {
    EARLY_CHILDHOOD("Для детей младшего возраста"),
    EVERYONE("Для всех"),
    EVERYONE_10_AND_OLDER("Для всех от 10 лет и старше"),
    TEEN("Подросткам"),
    MATURE("От 17 лет"),
    ADULTS_ONLY("Только для взрослых");

    private String name;

    AgeLimit(String name) {
        this.name = name;
    }

    public static AgeLimit getByName(String name) {
        return Arrays.stream(values())
                .filter(n -> n.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}
