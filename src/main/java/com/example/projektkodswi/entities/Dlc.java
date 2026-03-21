package com.example.projektkodswi.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "dlcs")
public class Dlc {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "dlc_id", nullable = false, updatable = false, length = 36)
    private String dlcId;

    @Column(name = "dlc_name", nullable = false, length = 200)
    private String dlcName;

    @Column(name = "dlc_description", length = 500)
    private String dlcDescription;

    public Dlc() {
    }

    public Dlc(String dlcName, String dlcDescription) {
        this.dlcName = dlcName;
        this.dlcDescription = dlcDescription;
    }

    public String getDlcId() {
        return dlcId;
    }

    public void setDlcId(String dlcId) {
        this.dlcId = dlcId;
    }

    public String getDlcName() {
        return dlcName;
    }

    public void setDlcName(String dlcName) {
        this.dlcName = dlcName;
    }

    public String getDlcDescription() {
        return dlcDescription;
    }

    public void setDlcDescription(String dlcDescription) {
        this.dlcDescription = dlcDescription;
    }
}
