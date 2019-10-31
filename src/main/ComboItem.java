/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

/**
 *
 * @author Muntari
 */
public class ComboItem {
    private String key;
    private String value;
    private String price;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    public ComboItem(String key, String value)
    {
        this.key = key;
        this.value = value;
        this.price = price;
    }
    public ComboItem(String key, String value, String price)
    {
        this.key = key;
        this.value = value;
        this.price = price;
    }

    @Override
    public String toString()
    {
        return key;
    }

    public String getKey()
    {
        return key;
    }

    public String getValue()
    {
        return value;
    }
}
