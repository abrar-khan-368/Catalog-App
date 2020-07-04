package com.abrarlohia.dressmaterialcatalog.Models;

public class CatalogDetails {

    private String catalogName;
    private String costing;

    public CatalogDetails() {}

    public CatalogDetails(String catalogName, String costing) {
        this.catalogName = catalogName;
        this.costing = costing;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public String getCosting() {
        return costing;
    }
}
