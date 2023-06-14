package com.ransankul.clickmart.model;

public class Category {

    private int CategoryId;
    private String name, color, brief, icon;
    private int priority;
    private long createdAt, lastUpdate;
    private boolean isPublic;
    

    public Category(int id, String name, String color, String brief, String icon, int priority, long createdAt,
            long lastUpdate, boolean isPublic) {
        this.CategoryId = id;
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
        return CategoryId;
    }

    public void setId(int id) {
        this.CategoryId = id;
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
