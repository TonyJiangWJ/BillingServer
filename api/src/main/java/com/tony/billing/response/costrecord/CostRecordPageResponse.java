package com.tony.billing.response.costrecord;

import com.tony.billing.dto.CostRecordDto;
import com.tony.billing.response.BaseResponse;

import java.util.List;

/**
 * Author jiangwj20966 on 2017/6/2.
 */
public class CostRecordPageResponse extends BaseResponse {
    private List<CostRecordDto> costRecordList;
    private Integer totalPage;
    private Integer pageSize;
    private Integer pageNo;
    private Integer totalItem;
    private String currentAmount;

    public Integer getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(Integer totalItem) {
        this.totalItem = totalItem;
    }

    public List<CostRecordDto> getCostRecordList() {
        return costRecordList;
    }

    public void setCostRecordList(List<CostRecordDto> costRecordList) {
        this.costRecordList = costRecordList;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public String getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(String currentAmount) {
        this.currentAmount = currentAmount;
    }
}
