package com.bionic_university.carrental.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * A bean for "orders" table
 *
 */
@Data
public class Order implements Serializable {

    private int orderID;
    private Vehicle vehicle;
    private User user;
    private Passport passport;
    private Timestamp pickUpDate;
    private Timestamp dropOffDate;
    private BigDecimal rentCost;
    private boolean processed;
    private boolean rejected;
    private String rejectDesc;
    private boolean picked;
    private boolean returned;
    private boolean damaged;
    private String damageDesc;
    private BigDecimal damageCost;
    private boolean paid;

    public Order() {
    }

    public Order(int orderID, Vehicle vehicle, User user, Passport passport,
            Timestamp pickUpDate, Timestamp dropOffDate, BigDecimal rentCost,
            boolean processed, boolean rejected, String rejectDesc,
            boolean picked, boolean returned, boolean damaged,
            String damageDesc, BigDecimal damageCost, boolean paid) {
        this.orderID = orderID;
        this.vehicle = vehicle;
        this.user = user;
        this.passport = passport;
        this.pickUpDate = pickUpDate;
        this.dropOffDate = dropOffDate;
        this.rentCost = rentCost;
        this.processed = processed;
        this.rejected = rejected;
        this.rejectDesc = rejectDesc;
        this.picked = picked;
        this.returned = returned;
        this.damaged = damaged;
        this.damageDesc = damageDesc;
        this.damageCost = damageCost;
        this.paid = paid;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("orderID", orderID)
                .append("vehicle", vehicle)
                .append("user", user)
                .append("passport", passport)
                .append("pickUpDate", pickUpDate)
                .append("dropOffDate", dropOffDate)
                .append("rentCost", rentCost)
                .append("processed", processed)
                .append("rejected", rejected)
                .append("rejectDesc", rejectDesc)
                .append("picked", picked)
                .append("returned", returned)
                .append("damaged", damaged)
                .append("damageDesc", damageDesc)
                .append("damageCost", damageCost)
                .append("paid", paid)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(orderID)
                .append(vehicle)
                .append(user)
                .append(passport)
                .append(pickUpDate)
                .append(dropOffDate)
                .append(rentCost)
                .append(processed)
                .append(rejected)
                .append(rejectDesc)
                .append(picked)
                .append(returned)
                .append(damaged)
                .append(damageDesc)
                .append(damageCost)
                .append(paid)
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Order other = (Order) obj;
        return new EqualsBuilder()
                .append(orderID, other.orderID)
                .append(vehicle, other.vehicle)
                .append(user, other.user)
                .append(passport, other.passport)
                .append(pickUpDate, other.pickUpDate)
                .append(dropOffDate, other.dropOffDate)
                .append(rentCost, other.rentCost)
                .append(processed, other.processed)
                .append(rejected, other.rejected)
                .append(rejectDesc, other.rejectDesc)
                .append(picked, other.picked)
                .append(returned, other.returned)
                .append(damaged, other.damaged)
                .append(damageDesc, other.damageDesc)
                .append(damageCost, other.damageCost)
                .append(paid, other.paid)
                .isEquals();
    }
}
