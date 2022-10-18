package entity;

import lombok.Getter;
import lombok.Setter;

public class Logins {
    @Getter
    @Setter
    private String Application;
    @Getter
    @Setter
    private String AppAccountName;
    @Getter
    @Setter
    private String isActive;
    @Getter
    @Setter
    private String JobTitle;
    @Getter
    @Setter
    private String Department;

    public Logins() {
    }

    public Logins(String application, String appAccountName, String isActive, String jobTitle, String department) {
        this.Application = application;
        this.AppAccountName = appAccountName;
        this.isActive = isActive;
        this.JobTitle = jobTitle;
        this.Department = department;
    }
}
