package com.ts.birtugla.domain;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import javax.persistence.*;

/**
 * A Donor.
 */
@Entity
@Table(name = "donor")
public class Donor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "int_id")
    private Integer intId;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "amount", precision = 21, scale = 2)
    private BigDecimal amount;

    @Column(name = "description")
    private String description;

    @Column(name = "created_by")
    private LocalDate createdBy;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "name_visible")
    private Boolean nameVisible;

    @Column(name = "amount_visible")
    private Boolean amountVisible;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public Donor id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getIntId() {
        return this.intId;
    }

    public Donor intId(Integer intId) {
        this.setIntId(intId);
        return this;
    }

    public void setIntId(Integer intId) {
        this.intId = intId;
    }

    public String getName() {
        return this.name;
    }

    public Donor name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return this.surname;
    }

    public Donor surname(String surname) {
        this.setSurname(surname);
        return this;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public Donor amount(BigDecimal amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return this.description;
    }

    public Donor description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getCreatedBy() {
        return this.createdBy;
    }

    public Donor createdBy(LocalDate createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(LocalDate createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreateDate() {
        return this.createDate;
    }

    public Donor createDate(LocalDate createDate) {
        this.setCreateDate(createDate);
        return this;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public Donor isDeleted(Boolean isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Boolean getNameVisible() {
        return this.nameVisible;
    }

    public Donor nameVisible(Boolean nameVisible) {
        this.setNameVisible(nameVisible);
        return this;
    }

    public void setNameVisible(Boolean nameVisible) {
        this.nameVisible = nameVisible;
    }

    public Boolean getAmountVisible() {
        return this.amountVisible;
    }

    public Donor amountVisible(Boolean amountVisible) {
        this.setAmountVisible(amountVisible);
        return this;
    }

    public void setAmountVisible(Boolean amountVisible) {
        this.amountVisible = amountVisible;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Donor)) {
            return false;
        }
        return id != null && id.equals(((Donor) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Donor{" +
            "id=" + getId() +
            ", intId=" + getIntId() +
            ", name='" + getName() + "'" +
            ", surname='" + getSurname() + "'" +
            ", amount=" + getAmount() +
            ", description='" + getDescription() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", isDeleted='" + getIsDeleted() + "'" +
            ", nameVisible='" + getNameVisible() + "'" +
            ", amountVisible='" + getAmountVisible() + "'" +
            "}";
    }
}
