package com.root.ExcelService.impl;

import com.root.ExcelService.service.AsyncService;
import com.root.ExcelService.service.MonthlyPlanService;
import com.root.ExcelService.vo.ProductAndMonthlyPlanHolderVO;
import com.root.commondependencies.exception.ValidationException;
import com.root.commondependencies.vo.ChildPartVO;
import com.root.commondependencies.vo.ParsedDataVO;
import com.root.commondependencies.vo.ProductVO;
import lombok.SneakyThrows;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class MonthlyPlanServiceImpl implements MonthlyPlanService {

    private final AsyncService asyncService;

    @Autowired
    public MonthlyPlanServiceImpl(AsyncService asyncService){
        this.asyncService = asyncService;
    }
    @Override
    @SneakyThrows
    public ParsedDataVO save(MultipartFile multipartFile){

        ParsedDataVO parsedDataVO = new ParsedDataVO();
        try{
            InputStream inputStream = multipartFile.getInputStream();
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

            //getProductAndMonthlyPlan
            CompletableFuture<ProductAndMonthlyPlanHolderVO> productAndMonthlyPlanCf
                    = asyncService.getProductAndMonthlyPlan(workbook);

            //List getChildProducts
            CompletableFuture<List<ChildPartVO>> childPartsCf = asyncService.getChildPartList(workbook);

            //Loop for mapping
            CompletableFuture<Map<String, Map<String,Integer>>> childPartProductQuantityMappingCf
                    =  asyncService.getProductChildPartMapping(workbook);

            CompletableFuture.allOf(productAndMonthlyPlanCf, childPartsCf, childPartProductQuantityMappingCf).join();

            ProductAndMonthlyPlanHolderVO productAndMonthlyPlanHolder = productAndMonthlyPlanCf.get();
            List<ChildPartVO> childPartsList = childPartsCf.get();
            Map<String, Map<String,Integer>> childPartProductQuantityMap = childPartProductQuantityMappingCf.get();

            parsedDataVO.setChildPartsList(childPartsList);
            parsedDataVO.setProductsList(productAndMonthlyPlanHolder.getProductsList());
            parsedDataVO.setMonthlyPlanList(productAndMonthlyPlanHolder.getMonthlyPlanList());

            for(ProductVO productVO : parsedDataVO.getProductsList()){
                if (childPartProductQuantityMap.containsKey(productVO.getProductSeries())){
                    productVO.setChildParts(childPartProductQuantityMap.get(productVO.getProductSeries()));
                }
            }

        }catch(Exception ex){
            throw new ValidationException.Builder().errorMessage(ex.getMessage()).build();
        }
        return parsedDataVO;

    }
}
