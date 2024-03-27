package com.root.ExcelService.service;

import com.root.ExcelService.helper.ExcelHelper;
import com.root.ExcelService.vo.ProductAndMonthlyPlanHolderVO;
import com.root.commondependencies.vo.ChildPartVO;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Component
public class AsyncService {

    @Async
    public CompletableFuture<ProductAndMonthlyPlanHolderVO> getProductAndMonthlyPlan(XSSFWorkbook workbook){
        ProductAndMonthlyPlanHolderVO productAndMonthlyPlanHolderVO = ExcelHelper.getProductAndMonthlyPlan(workbook);
        return CompletableFuture.completedFuture(productAndMonthlyPlanHolderVO);
    }

    @Async
    public CompletableFuture<List<ChildPartVO>> getChildPartList(XSSFWorkbook workbook){
        List<ChildPartVO> childPartsList = ExcelHelper.getChildPartsList(workbook);
        return CompletableFuture.completedFuture(childPartsList);
    }

    @Async
    public CompletableFuture<Map<String, Map<String, Integer>>> getProductChildPartMapping(XSSFWorkbook workbook){
        Map<String, Map<String, Integer>> map = ExcelHelper.getProductChildPartMapping(workbook);
        return CompletableFuture.completedFuture(map);
    }

}
