package com.song.dto;

/**
 * // TODO description
 *
 * @author zhangsong
 * @version 2017-07-24
 */
public class SearchRecordDto extends RecordDto {
    private String splitNumber;

    public SearchRecordDto(String number, long dateLong) {
        super(number, dateLong);
    }

    public SearchRecordDto(String number, long dateLong, String splitNumber) {
        super(number, dateLong);
        this.splitNumber = splitNumber;
    }

    public String getSplitNumber() {
        return splitNumber;
    }

    public void setSplitNumber(String splitNumber) {
        this.splitNumber = splitNumber;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.number  + "||" + super.dateLong + "||" + splitNumber ;
    }

    @Override
    public int compareTo(RecordDto o) {
        return super.compareTo(o);
    }
}
