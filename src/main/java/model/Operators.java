package model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Operators {
    private String id;
    private String status;

    public Operators(@JsonProperty("id")String id, @JsonProperty("status")String status) {
        this.id=id;
        this.status=status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
