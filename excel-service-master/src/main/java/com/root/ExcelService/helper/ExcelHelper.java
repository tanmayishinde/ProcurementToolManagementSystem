package com.root.ExcelService.helper;

import com.root.ExcelService.vo.ProductAndMonthlyPlanHolderVO;
import com.root.commondependencies.vo.ChildPartVO;
import com.root.commondependencies.vo.MonthlyPlanVO;
import com.root.commondependencies.vo.ProductVO;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ExcelHelper {

    public static boolean isValidExcel(MultipartFile multipartFile) {
        String contentType = multipartFile.getContentType();
        return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet".equals(contentType);
    }

    private static Map<String, Integer> getChildPartQuantityMapping(XSSFSheet sheet, int quantityColumnIndex) {
        int index = -1;
        Map<String, Integer> childPartMapping = new HashMap<>();
        for (Row row : sheet) {
            index++;
            if (index < 3) {
                continue;
            }

            Cell childpartSeriesCell = row.getCell(1);
            if (childpartSeriesCell.getCellType() == CellType.BLANK) {
                break;
            }
            Cell childPartQuantityCell = row.getCell(quantityColumnIndex);
            if ((int) childPartQuantityCell.getNumericCellValue() > 0) {
                childPartMapping.put(childpartSeriesCell.getStringCellValue(),
                        (int) childPartQuantityCell.getNumericCellValue());
            }

        }
        return childPartMapping;
    }

    public static List<ChildPartVO> getChildPartsList(XSSFWorkbook workbook) {
        XSSFSheet sheet = workbook.getSheet("Child Part Plan");
        List<ChildPartVO> childPartsList = new ArrayList<>();

        int rowNumber = -1;

        for (Row row : sheet) {
            rowNumber++;
            if (rowNumber < 3) {
                continue;
            }

            Cell childPartNameCell = row.getCell(2);
            if (childPartNameCell.getCellType() == CellType.BLANK) {
                break;
            }

            Cell childpartSeriesCell = row.getCell(1);
            Cell childPartOpeningStockCell = row.getCell(206);

            ChildPartVO childPartVO = new ChildPartVO();
            childPartVO.setChildPartName(childPartNameCell.getStringCellValue());
            childPartVO.setChildPartSeries(childpartSeriesCell.getStringCellValue());
            childPartVO.setChildPartOpeningStock((int) childPartOpeningStockCell.getNumericCellValue());

            childPartsList.add(childPartVO);
        }
        return childPartsList;
    }

    public static Map<String, Map<String, Integer>> getProductChildPartMapping(XSSFWorkbook workbook) {
        XSSFSheet sheet = workbook.getSheet("Child Part Plan");
        Map<String, Map<String, Integer>> childPartProductMapping = new HashMap<>();
        Row headerRow = sheet.getRow(2);

        int index = -1;

        for (Cell cell : headerRow) {
            index++;
            if (index < 8) {
                continue;
            }

            if (CellType.STRING == cell.getCellType()) {
                String productSeries = cell.getStringCellValue();
                Map<String, Integer> childPartQuantityMapping = getChildPartQuantityMapping(sheet, index);
                childPartProductMapping.put(productSeries, childPartQuantityMapping);
            }

        }
        return childPartProductMapping;
    }

    public static ProductAndMonthlyPlanHolderVO getProductAndMonthlyPlan(XSSFWorkbook workbook) {
        XSSFSheet sheet = workbook.getSheet("monthly plan");

        ProductAndMonthlyPlanHolderVO holderVO = new ProductAndMonthlyPlanHolderVO();

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
        holderVO.setProductsList(productsList);
        holderVO.setMonthlyPlanList(monthlyPlansList);
        return holderVO;
    }


}
