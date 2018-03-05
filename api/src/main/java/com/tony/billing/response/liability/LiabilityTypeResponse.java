package com.tony.billing.response.liability;

import com.tony.billing.dto.LiabilityTypeDTO;
import com.tony.billing.response.BaseResponse;

import java.util.List;

/**
 * <p>
 * </p>
 * <li></li>
 *
 * @author jiangwj20966 2018/3/5
 */
public class LiabilityTypeResponse extends BaseResponse {
    private List<LiabilityTypeDTO> liabilityTypes;

    public List<LiabilityTypeDTO> getLiabilityTypes() {
        return liabilityTypes;
    }

    public void setLiabilityTypes(List<LiabilityTypeDTO> liabilityTypes) {
        this.liabilityTypes = liabilityTypes;
    }
}
