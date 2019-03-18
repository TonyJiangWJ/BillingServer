package com.tony.billing.entity.base;

/**
 * 带乐观锁的对象
 *
 * @author jiangwenjie 2019-03-18
 */
public class BaseVersionedEntity extends BaseEntity {


    private Integer version;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
