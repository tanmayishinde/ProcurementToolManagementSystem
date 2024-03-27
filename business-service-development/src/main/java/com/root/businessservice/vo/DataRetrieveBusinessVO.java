package com.root.businessservice.vo;

import com.root.commondependencies.displayvo.ChildPartDisplayVO;
import com.root.commondependencies.displayvo.ChildPartQuantityVO;
import com.root.commondependencies.displayvo.ProductDisplayVO;
import com.root.commondependencies.vo.ProductChildPartRelationShipVO;
import lombok.Data;

import java.util.List;
import java.util.Map;
@Data
public class DataRetrieveBusinessVO {
    List<ProductDisplayVO> productDisplayVOList;
    List<ChildPartDisplayVO> ChildPartList;
    List<ProductChildPartRelationShipVO> productChildPartVOList;
    Map<Long,List<ChildPartQuantityVO>> ProductChildPartList;
}
