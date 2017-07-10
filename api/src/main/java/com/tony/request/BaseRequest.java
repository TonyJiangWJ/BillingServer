package com.tony.request;

/**
 * Author jiangwj20966 on 2017/6/2.
 */
public class BaseRequest {
    private String tokenId;
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }
}
