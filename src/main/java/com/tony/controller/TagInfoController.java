package com.tony.controller;

import com.tony.entity.CostRecord;
import com.tony.entity.TagCostRef;
import com.tony.entity.TagInfo;
import com.tony.model.TagInfoModel;
import com.tony.request.*;
import com.tony.response.BaseResponse;
import com.tony.response.CostTagListResponse;
import com.tony.response.TagInfoListResponse;
import com.tony.service.CostRecordService;
import com.tony.service.TagInfoService;
import com.tony.util.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author by TonyJiang on 2017/6/14.
 */
@RestController
@RequestMapping("/bootDemo")
public class TagInfoController extends BaseController {


    @Resource
    private CostRecordService costRecordService;
    @Resource
    private TagInfoService tagInfoService;

    // 列出所有标签
    @RequestMapping(value = "/tag/list")
    public TagInfoListResponse listTag() {
        TagInfoListResponse response = new TagInfoListResponse();
        List<TagInfo> tagInfos = tagInfoService.listTagInfo(new TagInfo());
        if (!CollectionUtils.isEmpty(tagInfos)) {

            List<TagInfoModel> list = new ArrayList<>();
            TagInfoModel model = null;
            for (TagInfo entity : tagInfos) {
                model = new TagInfoModel();
                model.setTagId(entity.getId());
                model.setTagName(entity.getTagName());
                list.add(model);
            }
            response.setTagInfoList(list);
        }
        ResponseUtil.success(response);
        return response;
    }

    // 添加标签
    @RequestMapping(value = "/tag/put")
    public BaseResponse putTag(@ModelAttribute("request") TagInfoPutRequest request) {
        BaseResponse response = new BaseResponse();
        if (StringUtils.isEmpty(request.getTagName())) {
            return ResponseUtil.paramError(response);
        }
        try {
            TagInfo tagInfo = new TagInfo();
            tagInfo.setTagName(request.getTagName());
            if (tagInfoService.putTagInfo(tagInfo) > 0) {
                ResponseUtil.success(response);
            } else {
                ResponseUtil.error(response);
            }
        } catch (Exception e) {
            logger.error("/tag/put error", e);
            ResponseUtil.sysError(response);
        }
        return response;
    }

    // 删除标签
    @RequestMapping(value = "/tag/delete")
    public BaseResponse delTag(@ModelAttribute("request") TagInfoDelRequest request) {
        BaseResponse response = new BaseResponse();
        if (request.getTagId() == null) {
            return ResponseUtil.paramError(response);
        }
        try {
            if (tagInfoService.deleteTagById(request.getTagId()) > 0) {
                ResponseUtil.success(response);
            } else {
                ResponseUtil.error(response);
            }
        } catch (Exception e) {
            logger.error("/tag/delete error ", e);
            ResponseUtil.sysError(response);
        }
        return response;
    }

    // 列出账单赋值的标签
    @RequestMapping(value = "/cost/tag/list")
    public CostTagListResponse listCostTag(@ModelAttribute("request") CostTagListRequest request) {
        CostTagListResponse response = new CostTagListResponse();
        if (StringUtils.isEmpty(request.getTradeNo())) {
            return (CostTagListResponse) ResponseUtil.paramError(response);
        }
        try {
            List<TagInfo> costTagList = tagInfoService.listTagInfoByTradeNo(request.getTradeNo());
            TagInfoModel model;
            List<TagInfoModel> result = new ArrayList<>();
            for (TagInfo entity : costTagList) {
                model = new TagInfoModel();
                model.setTagName(entity.getTagName());
                model.setTagId(entity.getId());
                result.add(model);
            }
            if (!CollectionUtils.isEmpty(result)) {
                response.setTagInfoModels(result);
                ResponseUtil.success(response);
            } else {
                ResponseUtil.dataNotExisting(response);
            }
        } catch (Exception e) {
            ResponseUtil.sysError(response);
            logger.error("/cost/tag/list error", e);
        }
        return response;
    }

    // 添加账单标签
    @RequestMapping(value = "/cost/tag/put")
    public BaseResponse putCostTag(@ModelAttribute("request") CostTagPutRequest request) {
        BaseResponse response = new BaseResponse();
        if (StringUtils.isEmpty(request.getTradeNo()) || request.getTagId() == null) {
            return ResponseUtil.paramError(response);
        }
        try {
            CostRecord costRecord = costRecordService.findByTradeNo(request.getTradeNo());
            TagInfo tagInfo = tagInfoService.getTagInfoById(request.getTagId());
            if (costRecord != null && tagInfo != null) {
                TagCostRef ref = new TagCostRef();
                ref.setCostId(costRecord.getId());
                ref.setTagId(tagInfo.getId());
                if (tagInfoService.insertTagCostRef(ref) > 0) {
                    ResponseUtil.success(response);
                } else {
                    ResponseUtil.error(response);
                }
            } else {
                ResponseUtil.paramError(response);
            }
        } catch (Exception e) {
            logger.error("/cost/tag/put error", e);
            ResponseUtil.sysError(response);
        }
        return response;
    }

    // 删除账单标签
    @RequestMapping(value = "/cost/tag/delete")
    public BaseResponse delCostTag(@ModelAttribute("request") CostTagDelRequest request) {
        BaseResponse response = new BaseResponse();
        if (request.getTagId() == null || StringUtils.isEmpty(request.getTradeNo())) {
            return ResponseUtil.paramError(response);
        }
        try {
            CostRecord costRecord = costRecordService.findByTradeNo(request.getTradeNo());
            TagInfo tagInfo = tagInfoService.getTagInfoById(request.getTagId());
            if (costRecord != null && tagInfo != null) {
                Map<String, Object> param = new HashMap<>();
                param.put("tagId", tagInfo.getId());
                param.put("costId", costRecord.getId());
                if (tagInfoService.deleteCostTag(param) > 0) {
                    ResponseUtil.success(response);
                } else {
                    ResponseUtil.error(response);
                }
            } else {
                ResponseUtil.paramError(response);
            }
        } catch (Exception e) {
            logger.error("/cost/tag/delete error", e);
            ResponseUtil.sysError(response);
        }
        return response;
    }
}
