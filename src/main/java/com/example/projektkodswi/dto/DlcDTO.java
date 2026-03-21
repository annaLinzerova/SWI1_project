package com.example.projektkodswi.dto;

public class DlcDTO {
    private String dlcId;
    private String dlcName;
    private String dlcDescription;

    public DlcDTO() {
    }

    public DlcDTO(String dlcId, String dlcName, String dlcDescription) {
        this.dlcId = dlcId;
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
