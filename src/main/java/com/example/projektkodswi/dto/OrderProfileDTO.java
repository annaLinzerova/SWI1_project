package com.example.projektkodswi.dto;

import java.time.LocalDateTime;
import java.util.List;

public class OrderProfileDTO {
    private String orderId;
    private LocalDateTime orderDate;
    private String orderDescription;
    private List<SkinDTO> skins;
    private List<DlcDTO> dlcs;

    public OrderProfileDTO() {
    }

    public OrderProfileDTO(String orderId, LocalDateTime orderDate, String orderDescription, List<SkinDTO> skins, List<DlcDTO> dlcs) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.orderDescription = orderDescription;
        this.skins = skins;
        this.dlcs = dlcs;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderDescription() {
        return orderDescription;
    }

    public void setOrderDescription(String orderDescription) {
        this.orderDescription = orderDescription;
    }

    public List<SkinDTO> getSkins() {
        return skins;
    }

    public void setSkins(List<SkinDTO> skins) {
        this.skins = skins;
    }

    public List<DlcDTO> getDlcs() {
        return dlcs;
    }

    public void setDlcs(List<DlcDTO> dlcs) {
        this.dlcs = dlcs;
    }
}
