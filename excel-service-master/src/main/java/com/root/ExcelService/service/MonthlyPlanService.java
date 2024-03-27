package com.root.ExcelService.service;

import com.root.commondependencies.vo.ParsedDataVO;
import org.springframework.web.multipart.MultipartFile;

public interface MonthlyPlanService {
    ParsedDataVO save(MultipartFile multipartFile);
}
