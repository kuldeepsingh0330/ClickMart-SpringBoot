package com.ransankul.clickmart.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private int categoryId;

    @Column(name = "name")
    private String name;

    @Column(name = "color")
    private String color;

    @Column(name = "brief")
    private String brief;

    @Column(name = "icon")
    private String icon;

    @Column(name = "priority")
    private int priority;

    @Column(name = "created_at")
    private long createdAt;

    @Column(name = "last_update")
    private long lastUpdate;

    @Column(name = "is_public")
    private boolean isPublic;

    public Category() {
        // Default constructor required by Hibernate
    }
    

    public Category(int id, String name, String color, String brief, String icon, int priority, long createdAt,
            long lastUpdate, boolean isPublic) {
        this.categoryId = id;
        this.name = name;
        this.color = color;
        this.brief = brief;
        this.icon = icon;
        this.priority = priority;
        this.createdAt = createdAt;
        this.lastUpdate = lastUpdate;
        this.isPublic = isPublic;
    }

    public int getId() {
        return categoryId;
    }

    public void setId(int id) {
        this.categoryId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }   
    

    
}
