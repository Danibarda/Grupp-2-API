package org.API.dtos;

public class CarDTO {
    private Long id; 
    private String manufacturer;
    private int year;
    private String model;
    private int milage;
    private Long userId;

    public CarDTO(){}

    public CarDTO(Long id, String manufacturer, int year, String model, int milage, Long userId ){
        this.id = id;
        this.manufacturer = manufacturer;
        this.year = year;
        this.model = model;
        this.milage = milage;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getMilage() {
        return milage;
    }

    public void setMilage(int milage) {
        this.milage = milage;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    
    
}
