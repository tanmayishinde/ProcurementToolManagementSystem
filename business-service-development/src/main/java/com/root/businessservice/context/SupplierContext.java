package com.root.businessservice.context;

import com.root.commondependencies.displayvo.ChildPartDisplayVO;
import com.root.commondependencies.displayvo.ChildPartQuantityVO;
import com.root.commondependencies.displayvo.ProductDisplayVO;
import com.root.commondependencies.vo.ProductChildPartRelationShipVO;
import com.root.redis.context.RedisSessionContext;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class SupplierContext extends RedisSessionContext {

    private List<ProductDisplayVO> productList;
    private List<ChildPartDisplayVO> childPartList;
    private Map<Long,List<ChildPartQuantityVO>> productChildPartList;
    private List<ProductChildPartRelationShipVO> productChildPartRelationshipVOList;

    @Override
    public String getContextIdentifier() {
        return "SUPPLIER";
    }

    @Override
    public Integer sessionExpiryTime() {
        return null;
    }
}