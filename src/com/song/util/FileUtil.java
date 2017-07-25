package com.song.util;

import com.song.dto.RecordDto;
import com.song.dto.SearchRecordDto;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * // TODO description
 *
 * @author zhangsong
 * @version 2017-07-24
 */
public class FileUtil {

    public static final int COLUM_2 = 2;
    public static final int COLUM_3 = 3;

    public static void saveFile(RecordDto recordDto) {
        FileWriter write = null;
        try {
            File file = new File("");
            file = new File(file.getCanonicalPath() + "/data/data.txt");
            write = new FileWriter(file, true);
            write.write(recordDto.toString());
            write.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != write) {
                try {
                    write.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    public static List<RecordDto> readFile() {
        List<RecordDto> recordDtoList = new ArrayList<>();
        BufferedReader reader = null;
        try {
            File file = new File("");
            file = new File(file.getCanonicalPath() + "/data/data.txt");
            reader = new BufferedReader(new FileReader(file));
            while (reader.ready()) {
                String line = reader.readLine();
                if (null != line && !"".equals(line.trim())) {
                    String[] lineSplits = line.split("\\|\\|");
                    RecordDto recordDto = new RecordDto(lineSplits[0], Long.parseLong(lineSplits[1]));
                    recordDtoList.add(recordDto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return recordDtoList;
    }

    /**
     * 1,2,3,4,5
     * 按照type把原有的RecordDto的number值进行截取
     *
     * @param type 0:前3,1:中3,2:后3
     * @return
     */
    public static List<SearchRecordDto> readFile(int type) {
        List<RecordDto> recordDtoList = readFile();
        List<SearchRecordDto> splitRecordDtoList = null;
        if (null != recordDtoList && !recordDtoList.isEmpty()) {
            splitRecordDtoList = new ArrayList<>();
            for (RecordDto recordDto : recordDtoList) {
                String number = recordDto.getNumber();
                String splitNumber = "";
                if (0 == type) {
                    // 前3
                    splitNumber = number.substring(0, 5);

                } else if (1 == type) {
                    // 中3
                    splitNumber = number.substring(2, 7);
                } else if (2 == type) {
                    // 后3
                    splitNumber = number.substring(4);
                }
                splitRecordDtoList.add(new SearchRecordDto(number, recordDto.getDateLong(), splitNumber));
            }

        }
        return splitRecordDtoList;
    }

    public static Object[][] loadData() {
        List<RecordDto> recordDtoList = readFile();
        return getObjectsByRecordDto(recordDtoList);
    }

    private static Object[][] getObjectsByRecordDto(List<RecordDto> recordDtoList) {
        Object[][] datas = null;
        if (null != recordDtoList && !recordDtoList.isEmpty()) {
            Collections.sort(recordDtoList);
            int row = recordDtoList.size();
            datas = new Object[row][COLUM_2];
            for (int i = 0; i < row; i++) {
                datas[i][0] = recordDtoList.get(i).getNumber();
                datas[i][1] = DateUtil.parseDateToStr(new Date(recordDtoList.get(i).getDateLong()));
            }
        }
        return datas;
    }

    private static Object[][] getObjectsBySearchRecordDto(List<SearchRecordDto> recordDtoList) {
        Object[][] datas = null;
        if (null != recordDtoList && !recordDtoList.isEmpty()) {
            Collections.sort(recordDtoList);
            List<SearchRecordDto> filterSearchRecordDtos = filterSearchRecordDto(recordDtoList);
            int row = filterSearchRecordDtos.size();
            datas = new Object[row][COLUM_3];
            for (int i = 0; i < row; i++) {
                datas[i][0] = filterSearchRecordDtos.get(i).getNumber();
                datas[i][1] = DateUtil.parseDateToStr(new Date(filterSearchRecordDtos.get(i).getDateLong()));
                datas[i][2] = filterSearchRecordDtos.get(i).getSplitNumber();
            }
        }
        return datas;
    }

    /**
     * 过滤掉时间不连续的记录
     *
     * @param recordDtoList
     * @return
     */
    private static List<SearchRecordDto> filterSearchRecordDto(List<SearchRecordDto> recordDtoList) {
        List<SearchRecordDto> filterSearchRecordDtos = null;
        if (null != recordDtoList && !recordDtoList.isEmpty()) {
            filterSearchRecordDtos = new ArrayList<>();
            long dateLong = recordDtoList.get(0).getDateLong();
            filterSearchRecordDtos.add(recordDtoList.get(0));
            int size = recordDtoList.size();
            for (int i = 1; i < size; i++) {
                long nowDateLong = recordDtoList.get(i).getDateLong();
                if (Math.abs(nowDateLong - dateLong) <= 24 * 60 * 60 * 1000) {
                    filterSearchRecordDtos.add(recordDtoList.get(i));
                    dateLong = nowDateLong;
                } else {
                    break;
                }
            }
        }
        return filterSearchRecordDtos;
    }

    /**
     * 查询方法
     *
     * @param intSet 查询数字集合
     * @param type   0:前3,1:中3,2:后3
     * @return
     */
    public static Object[][] searchData(Set<Integer> intSet, int type) {
        Object[][] datas = null;
        List<SearchRecordDto> searchRecordDtos = readFile(type);
        Set<SearchRecordDto> resultDtoSet = null;
        if (null != searchRecordDtos && !searchRecordDtos.isEmpty()
                && null != intSet && !intSet.isEmpty()) {
            resultDtoSet = new HashSet<>();
            Iterator<Integer> iterator = intSet.iterator();
            while (iterator.hasNext()) {
                String intSetString = "" + iterator.next();
                for (SearchRecordDto searchRecordDto : searchRecordDtos) {
                    String splitNumber = searchRecordDto.getSplitNumber();
                    if (splitNumber.contains(intSetString)
                            || ValidUtil.isSameNumber(splitNumber)) {
                        // 如果截取的字符串包含搜索的数字，则为搜索结果
                        // 如果截取的字符串有相同的数字，也为搜索结果
                        resultDtoSet.add(searchRecordDto);
                    }
                }
            }
        }

        if (null != resultDtoSet && !resultDtoSet.isEmpty()) {
            List<SearchRecordDto> searchs = new ArrayList<>();
            searchs.addAll(resultDtoSet);
            datas = getObjectsBySearchRecordDto(searchs);
        }
        return datas;
    }

}
