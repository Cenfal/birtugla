package com.ts.birtugla.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
@Data
public class DonorsForTable {
    private String name;
    private String surname;
    private BigDecimal amount;
    private String description;
    private LocalDate createDate;

    public DonorsForTable(String name, String surname, BigDecimal amount, String description, LocalDate createDate) {
        this.name = name;
        this.surname = surname;
        this.amount = amount;
        this.description = description;
        this.createDate = createDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }
}
