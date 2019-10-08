package com.ais.brm.common.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * Excle操作的工具类.
 * Created by xuechen on 2016-10-17.
 *
 * @author xuechen
 */
public class ImportUtil {
    private static HSSFWorkbook wb;

    private static CellStyle titleStyle; // 标题行样式

    private static HSSFFont titleFont; // 标题行字体

    private static CellStyle dateStyle; // 日期行样式

    private static HSSFFont dateFont; // 日期行字体

    private static CellStyle headStyle; // 表头行样式

    private static HSSFFont headFont; // 表头行字体

    private static CellStyle contentStyle; // 内容行样式

    private static HSSFFont contentFont; // 内容行字体

    /**
     * @param setInfo
     * @throws IOException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @description 将Map里的集合对象数据输出Excel数据流
     */
    public static void export2Excel(ImportSetInfo setInfo)
            throws IOException, IllegalArgumentException, IllegalAccessException {
        init();
        Set<Entry<String, List<List<String>>>> set = setInfo.getObjsMap().entrySet();
        String[] sheetNames = new String[setInfo.getObjsMap().size()];
        int sheetNameNum = 0;
        for (Entry<String, List<List<String>>> entry : set) {
            sheetNames[sheetNameNum] = entry.getKey();
            sheetNameNum++;
        }
        HSSFSheet[] sheets = getSheets(setInfo.getObjsMap().size(), sheetNames);
        int sheetNum = 0;
        for (Entry<String, List<List<String>>> entry : set) {
            // Sheet
            List<List<String>> objs = entry.getValue();
            // 标题行
            // createTableTitleRow(setInfo, sheets, sheetNum);
            // 表头
            creatTableHeadRow(setInfo, sheets, sheetNum);
            // 表体
            String[] fieldNames = setInfo.getHeadNames().get(sheetNum);
            int rowNum = 1;
            for (List<String> obj : objs) {
                HSSFRow contentRow = sheets[sheetNum].createRow(rowNum);
                contentRow.setHeight((short) 300);
                HSSFCell[] cells = getCells(contentRow, setInfo.getHeadNames().get(sheetNum).length);
                // int cellNum = 1; // 去掉一列序号，因此从1开始
                int cellNum = 0;
                if (fieldNames != null) {
                    for (int num = 0; num < obj.size(); num++) {
                        // 表格内容的长度大于标题行的长度，则忽略后面的内容
                        if (num > fieldNames.length - 1) {
                            continue;
                        }
                        cells[cellNum].setCellValue(obj.get(num) == null ?
                                "" : obj.get(num).toString());

                        cellNum++;
                    }
                }
                rowNum++;
            }
            if (sheets != null && fieldNames != null) {
                adjustColumnSize(sheets, sheetNum, fieldNames); // 自动调整列宽
            }
            sheetNum++;
        }
        wb.write(setInfo.getOut());
        setInfo.getOut().close();
    }

    /**
     * @description 初始化
     */
    private static void init() {
        wb = new HSSFWorkbook();
        titleFont = wb.createFont();
        titleStyle = wb.createCellStyle();
        dateStyle = wb.createCellStyle();
        dateFont = wb.createFont();
        headStyle = wb.createCellStyle();
        headFont = wb.createFont();
        contentStyle = wb.createCellStyle();
        contentFont = wb.createFont();
        initTitleCellStyle();
        initTitleFont();
        initDateCellStyle();
        initDateFont();
        initHeadCellStyle();
        initHeadFont();
        initContentCellStyle();
        initContentFont();
    }

    /**
     * @param sheets
     * @param sheetNum
     * @param fieldNames
     * @description 自动调整列宽
     */
    private static void adjustColumnSize(HSSFSheet[] sheets, int sheetNum, String[] fieldNames) {
        for (int i = 0; i < fieldNames.length + 1; i++) {
            sheets[sheetNum].autoSizeColumn(i, true);
        }
    }

    /**
     * @Description: 创建标题行(需合并单元格)
     */

    @SuppressWarnings("unused")
    private static void createTableTitleRow(ImportSetInfo setInfo, HSSFSheet[] sheets, int sheetNum) {
        CellRangeAddress titleRange = new CellRangeAddress(0, 0, 0,
                setInfo.getHeadNames().get(sheetNum).length);
        sheets[sheetNum].addMergedRegion(titleRange);
        HSSFRow titleRow = sheets[sheetNum].createRow(0);
        titleRow.setHeight((short) 800);
        HSSFCell titleCell = titleRow.createCell(0);
        titleCell.setCellStyle(titleStyle);
        titleCell.setCellValue(setInfo.getTitles()[sheetNum]);
    }

    /**
     * @param setInfo
     * @param sheets
     * @param sheetNum
     * @description 创建日期行(需合并单元格)
     */
    @SuppressWarnings("unused")
    private static void createTableDateRow(ImportSetInfo setInfo, HSSFSheet[] sheets, int sheetNum) {
        CellRangeAddress dateRange = new CellRangeAddress(1, 1, 0,
                setInfo.getHeadNames().get(sheetNum).length);
        sheets[sheetNum].addMergedRegion(dateRange);
        HSSFRow dateRow = sheets[sheetNum].createRow(1);
        dateRow.setHeight((short) 350);
        HSSFCell dateCell = dateRow.createCell(0);
        dateCell.setCellStyle(dateStyle);
        dateCell.setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
    }

    /**
     * @param setInfo
     * @param sheets
     * @param sheetNum
     * @description 创建表头行(需合并单元格)
     */
    private static void creatTableHeadRow(ImportSetInfo setInfo, HSSFSheet[] sheets, int sheetNum) {
        // 表头
        HSSFRow headRow = sheets[sheetNum].createRow(0);
        headRow.setHeight((short) 350);
        // 序号列
        // HSSFCell snCell = headRow.createCell(0);
        // snCell.setCellStyle(headStyle);
        // snCell.setCellValue("序号");
        // 列头名称
        for (int num = 0, len = setInfo.getHeadNames().get(sheetNum).length; num < len; num++) {
            HSSFCell headCell = headRow.createCell(num);
            headCell.setCellStyle(headStyle);
            headCell.setCellValue(setInfo.getHeadNames().get(sheetNum)[num]);
        }
    }

    /**
     * @param num
     * @param names
     * @return
     * @description 创建所有的Sheet
     */
    private static HSSFSheet[] getSheets(int num, String[] names) {
        HSSFSheet[] sheets = new HSSFSheet[num];
        for (int i = 0; i < num; i++) {
            sheets[i] = wb.createSheet(names[i]);
        }
        return sheets;
    }

    /**
     * @param contentRow
     * @param num
     * @return
     * @description 创建内容行的每一列(附加一列序号)
     */
    private static HSSFCell[] getCells(HSSFRow contentRow, int num) {
        HSSFCell[] cells = new HSSFCell[num];
        for (int i = 0, len = cells.length; i < len; i++) {
            cells[i] = contentRow.createCell(i);
            cells[i].setCellStyle(contentStyle);
        }
        // 设置序号列值，因为出去标题行，所有-1
        // cells[0].setCellValue(contentRow.getRowNum() - 1);
        return cells;
    }

    /**
     * @description 初始化标题行样式
     */
    private static void initTitleCellStyle() {
        titleStyle.setAlignment(CellStyle.ALIGN_CENTER);
        titleStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        titleStyle.setFont(titleFont);
        titleStyle.setFillBackgroundColor(IndexedColors.SKY_BLUE.index);
    }

    /**
     * @description 初始化日期行样式
     */
    private static void initDateCellStyle() {
        dateStyle.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);
        dateStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        dateStyle.setFont(dateFont);
        dateStyle.setFillBackgroundColor(IndexedColors.SKY_BLUE.index);
    }

    /**
     * @description 初始化表头行样式
     */
    private static void initHeadCellStyle() {
        headStyle.setAlignment(CellStyle.ALIGN_CENTER);
        headStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        headStyle.setFont(headFont);
        headStyle.setFillBackgroundColor(IndexedColors.YELLOW.index);
        headStyle.setBorderTop(CellStyle.BORDER_MEDIUM);
        headStyle.setBorderBottom(CellStyle.BORDER_THIN);
        headStyle.setBorderLeft(CellStyle.BORDER_THIN);
        headStyle.setBorderRight(CellStyle.BORDER_THIN);
        headStyle.setTopBorderColor(IndexedColors.BLUE.index);
        headStyle.setBottomBorderColor(IndexedColors.BLUE.index);
        headStyle.setLeftBorderColor(IndexedColors.BLUE.index);
        headStyle.setRightBorderColor(IndexedColors.BLUE.index);
    }

    /**
     * @description 初始化内容行样式
     */
    private static void initContentCellStyle() {
        contentStyle.setAlignment(CellStyle.ALIGN_CENTER);
        contentStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        contentStyle.setFont(contentFont);
        contentStyle.setBorderTop(CellStyle.BORDER_THIN);
        contentStyle.setBorderBottom(CellStyle.BORDER_THIN);
        contentStyle.setBorderLeft(CellStyle.BORDER_THIN);
        contentStyle.setBorderRight(CellStyle.BORDER_THIN);
        contentStyle.setTopBorderColor(IndexedColors.BLUE.index);
        contentStyle.setBottomBorderColor(IndexedColors.BLUE.index);
        contentStyle.setLeftBorderColor(IndexedColors.BLUE.index);
        contentStyle.setRightBorderColor(IndexedColors.BLUE.index);
        contentStyle.setWrapText(true); // 字段换行
    }

    /**
     * @description 初始化标题行字体
     */
    private static void initTitleFont() {
        titleFont.setFontName("华文楷体");
        titleFont.setFontHeightInPoints((short) 20);
        titleFont.setColor(IndexedColors.BLUE_GREY.index);
    }

    /**
     * @description 初始化日期行字体
     */
    private static void initDateFont() {
        dateFont.setFontName("隶书");
        dateFont.setFontHeightInPoints((short) 10);
        dateFont.setColor(IndexedColors.BLUE_GREY.index);
    }

    /**
     * @description 初始化表头行字体
     */
    private static void initHeadFont() {
        headFont.setFontName("宋体");
        headFont.setFontHeightInPoints((short) 10);
        headFont.setColor(IndexedColors.BLUE_GREY.index);
    }

    /**
     * @description 初始化内容行字体
     */
    private static void initContentFont() {
        contentFont.setFontName("宋体");
        contentFont.setFontHeightInPoints((short) 10);
        contentFont.setColor(IndexedColors.BLUE_GREY.index);
    }

}
