package com.song.form;

import com.song.dto.RecordDto;
import com.song.util.FileUtil;
import com.song.util.ValidUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

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
    private String[] headers = {"中奖号码", "期数"};
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
    private JPanel staticsPanel;
    private JButton staticButton;
    private JTable staticTable;
    private String[] searchHeaders = {"中奖号码", "期数", "截取值"};
    private DefaultTableModel searchTableModel = new DefaultTableModel(cellData, searchHeaders);
    private String[] staticHeaders = {"未买的2位数字", "连续不中奖次数"};
    private DefaultTableModel staticTableModel = new DefaultTableModel(cellData, staticHeaders);


    public TenChooseTwoForm() {
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String number = numberText.getText();
                String dateStr = dateText.getText();
                if (null == number || "".equals(number.trim())
                        || null == dateStr || "".equals(dateStr.trim())) {
                    JOptionPane.showMessageDialog(null, "中奖号码或期数不能为空", "错误提示", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                FileUtil.saveFile(new RecordDto(number, dateStr));
                allListTableModel.setDataVector(FileUtil.loadData(), headers);

            }
        });
        numberText.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String inputNumber = numberText.getText();
                if (!ValidUtil.validInputText(inputNumber, 5)) {
                    JOptionPane.showMessageDialog(null, "0到9的数字必须以英文逗号分隔且只能有5个数字\n形如：1,2,3,4,5", "错误提示", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        dateText.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String dateStr = dateText.getText();
                if (!ValidUtil.validDate(dateStr)) {
                    JOptionPane.showMessageDialog(null, "期数格式不对，形如:20170101-001", "错误提示", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchStr = searchText.getText();
                if (null == searchStr || "".equals(searchStr.trim())) {
                    JOptionPane.showMessageDialog(null, "购买值不能为空", "错误提示", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int searchType = searchCombo.getSelectedIndex();
                searchTableModel.setDataVector(FileUtil.searchData(ValidUtil.getNumSet(searchStr), searchType), searchHeaders);
            }
        });
        searchText.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String searchStr = searchText.getText();
                if (!ValidUtil.validInputText(searchStr, 2, false)) {
                    JOptionPane.showMessageDialog(null, "0到9的数字必须以英文逗号分隔且只能有2个数字\n形如：1,2", "错误提示", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        staticButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                staticTableModel.setDataVector(FileUtil.staticData(), staticHeaders);
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("TenChooseTwoForm");
        TenChooseTwoForm form = new TenChooseTwoForm();
        JPanel rootPane = form.mainPanel;
        form.allListTable.setModel(form.allListTableModel);
        form.allListTableModel.setDataVector(FileUtil.loadData(), form.headers);
        form.searchTable.setModel(form.searchTableModel);
        form.staticTable.setModel(form.staticTableModel);
        frame.setContentPane(rootPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(rootPane);//居中
        frame.setVisible(true);
    }
}
