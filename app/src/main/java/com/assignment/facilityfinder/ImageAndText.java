package com.assignment.facilityfinder;

public class ImageAndText {
    private String itemName;
    private int itemImg;

    public ImageAndText(String facilityName, int imgFacility) {
        this.itemName = facilityName;
        this.itemImg = imgFacility;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemImg() {
        return itemImg;
    }

    public void setItemImg(int itemImg) {
        this.itemImg = itemImg;
    }
}
