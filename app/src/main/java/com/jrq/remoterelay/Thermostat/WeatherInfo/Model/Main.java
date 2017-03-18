package com.jrq.remoterelay.Thermostat.WeatherInfo.Model;

import java.util.ArrayList;

/**
 * Created by rohit on 10/15/15.
 */
public class Main {

    private Double temp;

    public Double getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(Double temp_min) {
        this.temp_min = temp_min;
    }

    public Double getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(Double temp_max) {
        this.temp_max = temp_max;
    }

    private Double temp_min;
    private Double temp_max;
    private Double pressure;
    private Double seaLevel;
    private Double grndLevel;
    private Integer humidity;
    private Integer tempKf;

    /**
     *
     * @return
     * The temp
     */
    public Double getTemp() {
        return temp;
    }

    /**
     *
     * @param temp
     * The temp
     */
    public void setTemp(Double temp) {
        this.temp = temp;
    }


    /**
     *
     * @return
     * The pressure
     */
    public Double getPressure() {
        return pressure;
    }

    /**
     *
     * @param pressure
     * The pressure
     */
    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    /**
     *
     * @return
     * The seaLevel
     */
    public Double getSeaLevel() {
        return seaLevel;
    }

    /**
     *
     * @param seaLevel
     * The sea_level
     */
    public void setSeaLevel(Double seaLevel) {
        this.seaLevel = seaLevel;
    }

    /**
     *
     * @return
     * The grndLevel
     */
    public Double getGrndLevel() {
        return grndLevel;
    }

    /**
     *
     * @param grndLevel
     * The grnd_level
     */
    public void setGrndLevel(Double grndLevel) {
        this.grndLevel = grndLevel;
    }

    /**
     *
     * @return
     * The humidity
     */
    public Integer getHumidity() {
        return humidity;
    }

    /**
     *
     * @param humidity
     * The humidity
     */
    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    /**
     *
     * @return
     * The tempKf
     */
    public Integer getTempKf() {
        return tempKf;
    }

    /**
     *
     * @param tempKf
     * The temp_kf
     */
    public void setTempKf(Integer tempKf) {
        this.tempKf = tempKf;
    }

    private City city;
    private String cod;
    private String message;
    private Integer cnt;
    private java.util.List<List> list = new ArrayList<List>();

    /**
     *
     * @return
     * The city
     */
    public City getCity() {
        return city;
    }

    /**
     *
     * @param city
     * The city
     */
    public void setCity(City  city) {
        this.city = city;
    }

    /**
     *
     * @return
     * The cod
     */
    public String getCod() {
        return cod;
    }

    /**
     *
     * @param cod
     * The cod
     */
    public void setCod(String cod) {
        this.cod = cod;
    }

    /**
     *
     * @return
     * The message
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message
     * The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     *
     * @return
     * The cnt
     */
    public Integer getCnt() {
        return cnt;
    }

    /**
     *
     * @param cnt
     * The cnt
     */
    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    /**
     *
     * @return
     * The list
     */
    public java.util.List<List> getList() {
        return list;
    }

    /**
     *
     * @param list
     * The list
     */
    public void setList(java.util.List<List> list) {
        this.list = list;
    }

}