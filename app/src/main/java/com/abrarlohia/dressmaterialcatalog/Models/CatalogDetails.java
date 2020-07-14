package com.abrarlohia.dressmaterialcatalog.Models;

import java.util.HashMap;
import java.util.List;

public class CatalogDetails {

    private String catalogName;
    private String costing;
    private List<String> imageLink;
    private String catalogCategory;

    public CatalogDetails() {}

    public CatalogDetails(String catalogName, String costing, List<String> imageLink, String catalogCategory) {
        this.catalogName = catalogName;
        this.costing = costing;
        this.imageLink = imageLink;
        this.catalogCategory = catalogCategory;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public String getCosting() {
        return costing;
    }

    public List<String> getImageLink() {
        return imageLink;
    }

    public String getCatalogCategory() {
        return catalogCategory;
    }
}
