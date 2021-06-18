package com.antont.petclinic.pet;

public class PetResponseModel {
    private Integer id;
    private String name;
    private String owner;
    private String type;
    private Integer typeId;

    public PetResponseModel(Integer id, String name, String owner, String type, Integer typeId) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.type = type;
        this.typeId = typeId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }
}
