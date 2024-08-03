package com.prodavalnik.prodavalnik.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AddSupplierDTO {
    @NotNull
    @Size(min = 3, max = 30, message = "{add_supplier_error_name_length}")
    private String name;

    @NotNull
    @Size(message = "{add_supplier_error_email_length}")
    private String email;

    @NotNull
    @Size(message = "{add_supplier_error_site_length}")
    private String site;

    @NotNull
    @Size(message = "{add_supplier_image_url_length}")
    private String logoUrl;

    public AddSupplierDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
}
