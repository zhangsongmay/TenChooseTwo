package com.song.dto;

/**
 * // TODO description
 *
 * @author zhangsong
 * @version 2017-07-24
 */
public class RecordDto implements Comparable<RecordDto> {
    protected String number;
    protected long dateLong;

    public RecordDto(String number, long dateLong) {
        this.number = number;
        this.dateLong = dateLong;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public long getDateLong() {
        return dateLong;
    }

    public void setDateLong(long dateLong) {
        this.dateLong = dateLong;
    }

    @Override
    public String toString() {
        return number  + "||" + dateLong + "\n" ;
    }

    @Override
    public int compareTo(RecordDto o) {
        if(this.getDateLong() > o.getDateLong()) {
            return -1;
        } else if(this.getDateLong() < o.getDateLong()) {
            return 1;
        }
        return 0;
    }

    @Override
    public int hashCode() {
        return (this.getNumber()+this.getDateLong()).hashCode();
    }
}
