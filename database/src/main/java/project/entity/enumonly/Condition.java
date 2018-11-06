package project.entity.enumonly;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Condition {
    ACCEPTED("Принят"),
    COMPLETING("Комплектуется"),
    SEND("Отправлен"),
    DELIVERED_AND_PAID("Доставлен и оплачен"),
    CANCELLED("Отменён");

    private String name;

    Condition(String name) {
        this.name = name;
    }

    public static Condition getByName(String name) {
        return Arrays.stream(values())
                .filter(n -> n.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}
