package com.song.dto;

/**
 * // TODO description
 *
 * @author zhangsong
 * @version 2017-07-24
 */
public class RecordDto implements Comparable<RecordDto> {
    /**
     * 中奖号码
     */
    protected String number;
    /**
     * 期数，格式为20170701-001
     */
    protected String period;
    /**
     *期数的数字格式20170701001，用于排序
     */
    protected long dateLong;

    public RecordDto(String number, String period) {
        this.number = number;
        this.period = period;
        this.dateLong = Long.parseLong(period.replace("-",""));
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
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
        return number  + "||" + period + "\n" ;
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
        return (this.getNumber()+this.getPeriod()).hashCode();
    }

}
