package com.root.ExcelService.controllers;

import com.root.ExcelService.helper.ExcelHelper;
import com.root.ExcelService.service.MonthlyPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin("*")
public class MonthlyPlanController {

    @Autowired
    private MonthlyPlanService monthlyPlanService;

    @PostMapping("/excel/upload")
    public ResponseEntity<?> upload(@RequestParam("file")MultipartFile multipartFile){
        if(ExcelHelper.isValidExcel(multipartFile)){
            return ResponseEntity.ok(this.monthlyPlanService.save(multipartFile));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload excel file only.");
    }


}
