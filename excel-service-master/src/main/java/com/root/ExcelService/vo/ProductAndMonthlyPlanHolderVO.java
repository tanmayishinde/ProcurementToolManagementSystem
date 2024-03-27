package com.root.ExcelService.vo;

import com.root.commondependencies.vo.MonthlyPlanVO;
import com.root.commondependencies.vo.ProductVO;
import lombok.Data;

import java.util.List;

@Data
public class ProductAndMonthlyPlanHolderVO {
    private List<ProductVO> productsList;
    private List<MonthlyPlanVO> monthlyPlanList;
}
