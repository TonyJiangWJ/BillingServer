package com.tony.dao;

import com.tony.entity.TagInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Author by TonyJiang on 2017/6/14.
 */
@Repository
public interface TagInfoDao {
    Long insert(TagInfo tagInfo);

    Long updateById(TagInfo tagInfo);

    List<TagInfo> find(TagInfo tagInfo);
}
