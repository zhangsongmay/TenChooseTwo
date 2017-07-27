package com.song.dto;

/**
 * // TODO description
 *
 * @author zhangsong
 * @version 2017-07-24
 */
public class SearchRecordDto extends RecordDto {
    /**
     * 截取的中奖号码的3位数
     */
    private String splitNumber;

    public SearchRecordDto(String number, String period, String splitNumber) {
        super(number, period);
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
        return super.toString();
    }

    @Override
    public int compareTo(RecordDto o) {
        return super.compareTo(o);
    }
}
