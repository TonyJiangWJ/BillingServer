package com.tony.service.impl;

import com.alibaba.fastjson.JSON;
import com.tony.dao.CostRecordDao;
import com.tony.entity.CostRecord;
import com.tony.service.AlipayBillCsvConvertService;
import com.tony.util.CsvParser;
import com.tony.util.MoneyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author by TonyJiang on 2017/6/3.
 */
@Service
public class AlipayBillCsvConvertServiceImpl implements AlipayBillCsvConvertService {

    private Logger logger = LoggerFactory.getLogger(AlipayBillCsvConvertService.class);
    @Resource
    private CostRecordDao costRecordDao;

    public boolean convertToPOJO(MultipartFile multipartFile) {
        if (multipartFile != null) {
            try {
                InputStream inputStream = multipartFile.getInputStream();
                CsvParser csvParser = new CsvParser(inputStream);
                if (!CollectionUtils.isEmpty(csvParser.getList())) {
                    if (csvParser.getRowNum() <= 7) {
                        throw new RuntimeException("Illegal file");
                    }
                    List<String> fixedList = csvParser.getListCustom(5, csvParser.getRowNum() - 7);// alipay format
                    try {
                        RecordRefUtil recordRefUtil = new RecordRefUtil();
                        List<Record> records = new ArrayList<Record>();
                        for (String csvLine : fixedList) {
                            records.add(recordRefUtil.convertCsv2Record(csvLine));
                        }
                        System.out.println(JSON.toJSONString(records));
                        if (!CollectionUtils.isEmpty(records)) {
                            for (Record entity : records) {
                                convertToDBJOAndInsert(entity);
                            }
                        }
                        return true;
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    private void convertToDBJOAndInsert(Record entity) {
        CostRecord record = costRecordDao.findByTradeNo(entity.getTradeNo());
        if (record == null) {
            record = new CostRecord();


            record.setIsDelete(0);
            record.setCreateTime(entity.createTime);
            record.setGoodsName(entity.getGoodsName());
            record.setInOutType(entity.getInOutType());
            record.setLocation(entity.getLocation());
            record.setMemo(entity.getMemo());
            record.setModifyTime(entity.getModifyTime());
            record.setMoney(MoneyUtil.yuan2fen(entity.getMoney()));
            record.setOrderNo(entity.getOrderNo());
            record.setOrderStatus(entity.getOrderStatus());
            record.setOrderType(entity.getOrderType());
            record.setPaidTime(entity.getPaidTime());
            record.setRefundMoney(MoneyUtil.yuan2fen(entity.getRefundMoney()));
            record.setServiceCost(MoneyUtil.yuan2fen(entity.getServiceCost()));
            record.setTarget(entity.getTarget());
            record.setTradeNo(entity.getTradeNo());
            record.setTradeStatus(entity.getTradeStatus());
            if (costRecordDao.insert(record) > 0) {
                logger.debug("record insert success");
            } else {
                logger.error("record insert fail");
            }
        } else {
            logger.warn("record already exist");
        }


    }

    static class RecordRefUtil {
        public Record convertCsv2Record(String csvLine) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
            String[] strings = csvLine.split(",");
            Class clazz = Record.class;
            Record record = new Record();
            Field[] fields = clazz.getDeclaredFields();
            if (strings.length != fields.length) {
                System.out.println("Error Line");
                return null;
            } else {
                Method[] methods = clazz.getMethods();
                Map<String, Method> methodMap = new HashMap<String, Method>();
                for (Method method : methods) {
                    methodMap.put(method.getName(), method);
                }
                Method method = null;
                for (int i = 0; i < fields.length; i++) {
                    String fieldName = fields[i].getName();
                    String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                    method = methodMap.get(methodName);
                    method.invoke(record, strings[i].trim());
                }
                return record;
            }
        }

    }

    static class Record {
        private String tradeNo;
        private String orderNo;
        private String createTime;
        private String paidTime;
        private String modifyTime;
        private String location;
        private String orderType;
        private String target;
        private String goodsName;
        private String money;
        private String inOutType;
        private String orderStatus;
        private String serviceCost;
        private String refundMoney;
        private String memo;
        private String tradeStatus;

        public String getTradeNo() {
            return tradeNo;
        }

        public void setTradeNo(String tradeNo) {
            this.tradeNo = tradeNo;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getPaidTime() {
            return paidTime;
        }

        public void setPaidTime(String paidTime) {
            this.paidTime = paidTime;
        }

        public String getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(String modifyTime) {
            this.modifyTime = modifyTime;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getOrderType() {
            return orderType;
        }

        public void setOrderType(String orderType) {
            this.orderType = orderType;
        }

        public String getTarget() {
            return target;
        }

        public void setTarget(String target) {
            this.target = target;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getInOutType() {
            return inOutType;
        }

        public void setInOutType(String inOutType) {
            this.inOutType = inOutType;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getServiceCost() {
            return serviceCost;
        }

        public void setServiceCost(String serviceCost) {
            this.serviceCost = serviceCost;
        }

        public String getRefundMoney() {
            return refundMoney;
        }

        public void setRefundMoney(String refundMoney) {
            this.refundMoney = refundMoney;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        public String getTradeStatus() {
            return tradeStatus;
        }

        public void setTradeStatus(String tradeStatus) {
            this.tradeStatus = tradeStatus;
        }
    }
}
