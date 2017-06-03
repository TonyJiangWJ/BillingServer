package com.tony.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Author by TonyJiang on 2017/6/3.
 */
public interface AlipayBillCsvConvertService {
    boolean convertToPOJO(MultipartFile multipartFile);
}
