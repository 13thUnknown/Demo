package com.example.demo.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "transactions")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Transaction extends BaseEntity{
    @Column
    @NotNull
    private BigDecimal value;

    @Column
    @NotNull
    private String currency;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_users_transactions_user_id"))
    private User user;
}
