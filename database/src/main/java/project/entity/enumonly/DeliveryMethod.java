package project.entity.enumonly;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum  DeliveryMethod {

    PICKUP("Самовывоз", 0),
    POST("Доставка почтой", 7),
    EXPRESS("Экспресс-доставка", 15),
    COURIER("Доставка курьером", 10);

    private String name;
    private int price;

    DeliveryMethod(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public static DeliveryMethod getByName(String name) {
        return Arrays.stream(values())
                .filter(n -> n.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}
