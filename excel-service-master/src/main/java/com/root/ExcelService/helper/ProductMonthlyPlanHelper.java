package com.root.ExcelService.helper;

import com.root.commondependencies.vo.MonthlyPlanVO;
import com.root.commondependencies.vo.ParsedDataVO;
import com.root.commondependencies.vo.ProductVO;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.ArrayList;
import java.util.List;

public class ProductMonthlyPlanHelper {

    public static void populateProductAndMonthlyPlan(XSSFWorkbook workbook, ParsedDataVO parsedDataVO) {
        XSSFSheet sheet = workbook.getSheet("monthly plan");

        List<ProductVO> productsList = new ArrayList<>();
        List<MonthlyPlanVO> monthlyPlansList = new ArrayList<>();

        int rowNumber = -1;

        for (Row row : sheet) {
            rowNumber++;

            if (rowNumber < 3) {
                continue;
            }

            Cell productNameCell = row.getCell(1);
            if (productNameCell.getCellType() != CellType.STRING || productNameCell.getCellType() == CellType.BLANK) {
                break;
            }

            Cell productSeriesCell = row.getCell(2);
            Cell week1Cell = row.getCell(3);
            Cell week2Cell = row.getCell(4);
            Cell week3Cell = row.getCell(5);
            Cell week4Cell = row.getCell(6);
            Cell productOpeningStock = row.getCell(11);

            ProductVO productVO = new ProductVO();
            productVO.setProductName(productNameCell.getStringCellValue());
            productVO.setProductSeries(productSeriesCell.getStringCellValue());
            productVO.setProductOpeningStock((int) productOpeningStock.getNumericCellValue());

            MonthlyPlanVO monthlyPlanVO = new MonthlyPlanVO();
            monthlyPlanVO.setProductSeries(productSeriesCell.getStringCellValue());
            monthlyPlanVO.setWeek1((int) week1Cell.getNumericCellValue());
            monthlyPlanVO.setWeek2((int) week2Cell.getNumericCellValue());
            monthlyPlanVO.setWeek3((int) week3Cell.getNumericCellValue());
            monthlyPlanVO.setWeek4((int) week4Cell.getNumericCellValue());

            productsList.add(productVO);
            monthlyPlansList.add(monthlyPlanVO);
        }
        parsedDataVO.setProductsList(productsList);
        parsedDataVO.setMonthlyPlanList(monthlyPlansList);
    }

}
