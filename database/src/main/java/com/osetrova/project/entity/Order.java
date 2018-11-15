package com.osetrova.project.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import com.osetrova.project.entity.baseentity.BaseEntity;
import com.osetrova.project.entity.enumonly.Condition;
import com.osetrova.project.entity.enumonly.DeliveryMethod;
import com.osetrova.project.entity.enumonly.PaymentForm;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "itemsInOrder")
@Data
@Builder
@EqualsAndHashCode(
        exclude = {
            "deliveryMethod",
            "paymentForm",
            "condition",
            "date",
            "deliveryDate",
            "itemsInOrder",
            "user"
        })
@Entity
@Table(name = "order_data", schema = "computer_games_e_shop_storage")
public class Order implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "delivery_method")
    @Enumerated(EnumType.STRING)
    private DeliveryMethod deliveryMethod;

    @Column(name = "payment_form")
    @Enumerated(EnumType.STRING)
    private PaymentForm paymentForm;

    @Column(name = "condition")
    @Enumerated(EnumType.STRING)
    private Condition condition;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "delivery_date")
    private LocalDate deliveryDate;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private Set<ItemInOrder> itemsInOrder = new HashSet<>();
}
