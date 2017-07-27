package com.song.dto;

/**
 * // TODO description
 *
 * @author zhangsong
 * @version 2017-07-27
 */
public class StaticDto implements Comparable<StaticDto> {
    private String number;
    private Integer count = 0;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public StaticDto(String number, Integer count) {
        this.number = number;
        this.count = count;
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @Override
    public int compareTo(StaticDto o) {
        if(this.getCount() > o.getCount()) {
            return -1;
        } else if(this.getCount() < o.getCount()) {
            return 1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return this.getNumber()+"-"+this.getCount();
    }
}
