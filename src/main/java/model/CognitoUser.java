package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class CognitoUser {
    public String username;
    public String email;
    public String sub;
}
