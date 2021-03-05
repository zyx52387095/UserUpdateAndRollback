package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties
public class OidBackUp {
    private String oId;
    private String pr_identity;

    public OidBackUp(@JsonProperty("oId") String oId, @JsonProperty("pr_identity") String pr_identity) {
        this.oId = oId;
        this.pr_identity = pr_identity;
    }

    public String getoId() {
        return oId;
    }

    public void setoId(String oId) {
        this.oId = oId;
    }

    public String getPr_identity() {
        return pr_identity;
    }

    public void setPr_identity(String pr_identity) {
        this.pr_identity = pr_identity;
    }
}
