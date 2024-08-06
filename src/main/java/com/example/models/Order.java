package com.example.models;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.example.models.meal.Meal;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.micronaut.serde.annotation.Serdeable;

import java.time.Instant;
import java.util.Objects;
import java.util.Set;

@DynamoDBTable(tableName = "order_table")
@DynamoDBDocument
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
@JsonSubTypes({@JsonSubTypes.Type(value = Order.class, name = "Order"), @JsonSubTypes.Type(value = AnonymousOrder.class, name = "AnonymousOrder")})
@Serdeable
public class Order implements Model {
    private String id;
    private Meal meal;
    protected String uid;
    private String participantsName;
    private Set<MenuItem> menuItems;
    private boolean submitted = false;

    public Order(String id, Meal meal, String uid, String participantsName, Set<MenuItem> menuItems) {
        this.id = id;
        this.meal = meal;
        this.uid = uid;
        this.participantsName = participantsName;
        this.menuItems = menuItems;
    }

    public Order(Meal meal, String uid, String participantsName, Set<MenuItem> menuItems) {
        this.meal = meal;
        this.uid = uid;
        this.participantsName = participantsName;
        this.menuItems = menuItems;
    }

    public Order() {
        this.meal = new Meal();
    }

    @DynamoDBAttribute
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @DynamoDBAttribute
    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }


    @DynamoDBHashKey(attributeName = "meal_id")
    @DynamoDBIndexRangeKey(globalSecondaryIndexName = "uid_gsi", attributeName = "meal_id")
    public String getPrimaryKey() {
        return "Order_" + meal.getId();
    }

    public void setPrimaryKey(String pk) {
        this.meal.setId(pk.replace("Order_", ""));
    }

    @DynamoDBRangeKey(attributeName = "date_of_meal")
    public String getSortKey() {
        if (meal.getMealDate() == null) return null;
        return meal.getMealDate().toString();
    }

    public void setSortKey(String sk) {
        this.meal.setMealDate(Instant.parse(sk));
    }

    @DynamoDBIndexHashKey(globalSecondaryIndexName = "uid_gsi", attributeName = "uid")
    public String getGSIPrimaryKey() {
        return "Order_" + uid;
    }

    public void setGSIPrimaryKey(String primaryKeyGSI) {
        uid = primaryKeyGSI.replace("Order_", "");
    }

    @DynamoDBIgnore
    public String getUid() {
        return uid;
    }

    @DynamoDBIgnore
    public void setUid(String uid) {
        this.uid = uid;
    }

    @DynamoDBAttribute
    public Set<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(Set<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }


    @DynamoDBAttribute
    public String getParticipantsName() {
        return participantsName;
    }

    public void setParticipantsName(String participantsName) {
        this.participantsName = participantsName;
    }


    public boolean isSubmitted() {
        return submitted;
    }

    @DynamoDBAttribute
    public void setSubmitted(boolean submitted) {
        this.submitted = submitted;
    }


    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order order)) return false;

        return submitted == order.submitted && Objects.equals(id, order.id) && Objects.equals(meal, order.meal) && Objects.equals(uid, order.uid) && Objects.equals(participantsName, order.participantsName) && Objects.equals(menuItems, order.menuItems);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(meal);
        result = 31 * result + Objects.hashCode(uid);
        result = 31 * result + Objects.hashCode(participantsName);
        result = 31 * result + Objects.hashCode(menuItems);
        result = 31 * result + Boolean.hashCode(submitted);
        return result;
    }
}
