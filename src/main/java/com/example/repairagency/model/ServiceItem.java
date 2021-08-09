package com.example.repairagency.model;

public enum ServiceItem {
    PAID_THE_WALLS("Paid the walls", 100),
    COVER_CEILING("Cover the ceiling", 200),
    INSTALL_TILE_FLOOR("Install tile on floor", 150),
    INSTALL_PARQUET("Install parquet on floor", 140),
    MAKE_A_ROOF("Make a roof", 300);

    private String description;
    private int priceForOneMeter;

    ServiceItem(String description, int priceForOneMeter) {
        this.description = description;
        this.priceForOneMeter = priceForOneMeter;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriceForOneMeter() {
        return priceForOneMeter;
    }

    public void setPriceForOneMeter(int priceForOneMeter) {
        this.priceForOneMeter = priceForOneMeter;
    }
}
