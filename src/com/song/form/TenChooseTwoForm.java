package com.song.form;

import com.song.dto.RecordDto;
import com.song.util.DateUtil;
import com.song.util.FileUtil;
import com.song.util.ValidUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerAdapter;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.ParseException;
import java.util.Date;

/**
 * // TODO description
 *
 * @author zhangsong
 * @version 2017-07-24
 */
public class TenChooseTwoForm {
    private JPanel mainPanel;
    private JTabbedPane tabPanel;
    private JPanel savePanel;
    private JPanel searchPanel;
    private JTextField numberText;
    private JButton saveButton;
    private JLabel inputLabel;
    private JTable allListTable;
    private String[] headers = { "期数值", "时间" };
    private Object[][] cellData = null;

    private DefaultTableModel allListTableModel = new DefaultTableModel(cellData, headers);
    private JTextField dateText;
    private JLabel dateLabel;
    private JTextField searchText;
    private JButton searchButton;
    private JLabel searchLabel;
    private JTable searchTable;
    private JLabel searchTypeLabel;
    private JComboBox searchCombo;
    private String[] searchHeaders = { "期数值", "时间" , "截取值"};
    private DefaultTableModel searchTableModel = new DefaultTableModel(cellData, searchHeaders);


    public TenChooseTwoForm() {
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String number = numberText.getText();
                String dateStr = dateText.getText();
                if(null == number || "".equals(number.trim())
            || null == dateStr || "".equals(dateStr.trim())) {
                    JOptionPane.showMessageDialog(null, "期数或时间不能为空", "错误提示", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    Date date = DateUtil.parseStrToDate(dateStr);
                    FileUtil.saveFile(new RecordDto(number, date.getTime()));
                    allListTableModel.setDataVector(FileUtil.loadData(),headers);
                } catch (ParseException e1) {
                    JOptionPane.showMessageDialog(null, "时间格式不对，形如:2017-01-01", "错误提示", JOptionPane.ERROR_MESSAGE);
                    return;
                }

            }
        });
        numberText.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String inputNumber = numberText.getText();
                if(!ValidUtil.validInputText(inputNumber, 5)) {
                    JOptionPane.showMessageDialog(null, "0到9的数字必须以英文逗号分隔且只能有5个数字\n形如：1,2,3,4,5", "错误提示", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        dateText.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String dateStr = dateText.getText();
                if(!ValidUtil.validDate(dateStr)) {
                    JOptionPane.showMessageDialog(null, "时间格式不对，形如:2017-01-01", "错误提示", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchStr = searchText.getText();
                if(null == searchStr || "".equals(searchStr.trim())) {
                    JOptionPane.showMessageDialog(null, "购买值不能为空", "错误提示", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int searchType = searchCombo.getSelectedIndex();
                searchTableModel.setDataVector(FileUtil.searchData(ValidUtil.getNumSet(searchStr), searchType),searchHeaders);
            }
        });
        searchText.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String searchStr = searchText.getText();
                if(!ValidUtil.validInputText(searchStr, 2, false)) {
                    JOptionPane.showMessageDialog(null, "0到9的数字必须以英文逗号分隔且只能有2个数字\n形如：1,2", "错误提示", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("TenChooseTwoForm");
        TenChooseTwoForm form = new TenChooseTwoForm();
        JPanel rootPane=form.mainPanel;
        form.allListTable.setModel(form.allListTableModel);
        form.allListTableModel.setDataVector(FileUtil.loadData(),form.headers);
        form.searchTable.setModel(form.searchTableModel);
        frame.setContentPane(rootPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(rootPane);//居中
        frame.setVisible(true);
    }
}
