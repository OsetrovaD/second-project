package project.entity.enumonly;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum PaymentForm {

    CASH("Наличными"),
    CARD("Пластиковой картой"),
    ONLINE("Оплата онлайн");

    private String name;

    PaymentForm(String name) {
        this.name = name;
    }

    public static PaymentForm getByName(String name) {
        return Arrays.stream(values())
                .filter(n -> n.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}
