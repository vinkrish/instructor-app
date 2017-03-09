package com.aanglearning.instructorapp.model;

public class SliptestPortion {
    private long id;
    private long sliptestId;
    private long portionId;
    private String portionName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSliptestId() {
        return sliptestId;
    }

    public void setSliptestId(long sliptestId) {
        this.sliptestId = sliptestId;
    }

    public long getPortionId() {
        return portionId;
    }

    public void setPortionId(long portionId) {
        this.portionId = portionId;
    }

    public String getPortionName() {
        return portionName;
    }

    public void setPortionName(String portionName) {
        this.portionName = portionName;
    }

}
