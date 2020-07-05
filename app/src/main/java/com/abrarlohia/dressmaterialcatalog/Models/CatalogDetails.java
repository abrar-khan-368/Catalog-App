package com.abrarlohia.dressmaterialcatalog.Models;

import java.util.HashMap;
import java.util.List;

public class CatalogDetails {

    private String catalogName;
    private String costing;
    private List<DownloadUrl> imageLink;
    private String catalogCategory;

    public CatalogDetails() {}

    public CatalogDetails(String catalogName, String costing, List<DownloadUrl> imageLink, String catalogCategory) {
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

    public List<DownloadUrl> getImageLink() {
        return imageLink;
    }

    public String getCatalogCategory() {
        return catalogCategory;
    }
}
