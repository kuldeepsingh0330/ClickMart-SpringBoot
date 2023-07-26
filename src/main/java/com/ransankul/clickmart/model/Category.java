package com.ransankul.clickmart.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private int categoryId;

    @NotBlank(message = "Name is required")
    @Column(name = "name")
    private String name;

    @NotBlank(message = "Color is required")
    @Column(name = "color")
    private String color;

    @NotBlank(message = "Brief is required")
    @Column(name = "brief")
    private String brief;

    @NotBlank(message = "Icon is required")
    @Column(name = "icon")
    private String icon;

    @Column(name = "priority")
    private int priority;

    @Column(name = "created_at")
    private Long createdAt;

    @Column(name = "last_update")
    private Long lastUpdate;

    @NotNull(message = "Public flag is required")
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

	public void setCreatedAt(Long createdAt) {
		this.createdAt = createdAt;
	}


	public void setLastUpdate(Long lastUpdate) {
		this.lastUpdate = lastUpdate;
	}


	public boolean getIsPublic() {
		return isPublic;
	}


	public void setIsPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}
	
	
  
    

    
}
