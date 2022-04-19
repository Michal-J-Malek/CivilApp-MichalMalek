package com.example.civiladvocacymichalmalek;

import java.io.Serializable;

public class GovData implements Serializable {
    String name;
    String office;
    String address;
    String party;
    String phone;
    String url;
    String email;
    String photo;
    String FID;
    String TID;
    String YID;

    public GovData(String name, String office, String address, String party, String phone, String url, String email, String photo, String FID, String TID, String YID) {
        this.name = name;
        this.office = office;
        this.address = address;
        this.party = party;
        this.phone = phone;
        this.url = url;
        this.email = email;
        this.photo = photo;
        this.FID = FID;
        this.TID = TID;
        this.YID = YID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getFID() {
        return FID;
    }

    public void setFID(String FID) {
        this.FID = FID;
    }

    public String getTID() {
        return TID;
    }

    public void setTID(String TID) {
        this.TID = TID;
    }

    public String getYID() {
        return YID;
    }

    public void setYID(String YID) {
        this.YID = YID;
    }
}
