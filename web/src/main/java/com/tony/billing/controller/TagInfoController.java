package com.tony.billing.controller;

import com.tony.billing.dto.TagInfoDTO;
import com.tony.billing.entity.CostRecord;
import com.tony.billing.entity.TagCostRef;
import com.tony.billing.entity.TagInfo;
import com.tony.billing.request.BaseRequest;
import com.tony.billing.request.taginfo.CostTagDelRequest;
import com.tony.billing.request.taginfo.CostTagListRequest;
import com.tony.billing.request.taginfo.CostTagPutRequest;
import com.tony.billing.request.taginfo.TagInfoDelRequest;
import com.tony.billing.request.taginfo.TagInfoPutRequest;
import com.tony.billing.response.BaseResponse;
import com.tony.billing.response.taginfo.CostTagListResponse;
import com.tony.billing.response.taginfo.TagInfoListResponse;
import com.tony.billing.service.CostRecordService;
import com.tony.billing.service.TagInfoService;
import com.tony.billing.util.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author by TonyJiang on 2017/6/14.
 */
@RestController
@RequestMapping("/bootDemo")
public class TagInfoController extends BaseController {


    @Resource
    private CostRecordService costRecordService;
    @Resource
    private TagInfoService tagInfoService;

    /**
     * 列出所有标签
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/tag/list")
    public TagInfoListResponse listTag(@ModelAttribute("request") BaseRequest request) {
        TagInfoListResponse response = new TagInfoListResponse();
        TagInfo tagInfo = new TagInfo();
        tagInfo.setUserId(request.getUserId());
        List<TagInfo> tagInfos = tagInfoService.listTagInfo(tagInfo);
        if (!CollectionUtils.isEmpty(tagInfos)) {
            response.setTagInfoList(tagInfos.stream()
                    .map(tag -> {
                        TagInfoDTO model = new TagInfoDTO();
                        model.setTagName(tag.getTagName());
                        model.setTagId(tag.getId());
                        model.setUsageCount(tagInfoService.countTagUsage(tag.getId()));
                        return model;
                    })
                    .sorted(Comparator.comparing(TagInfoDTO::getUsageCount).reversed())
                    .collect(Collectors.toList()));
        }
        ResponseUtil.success(response);
        return response;
    }

    /**
     * 添加标签
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/tag/put")
    public BaseResponse putTag(@ModelAttribute("request") TagInfoPutRequest request) {
        BaseResponse response = new BaseResponse();
        if (StringUtils.isEmpty(request.getTagName())) {
            return ResponseUtil.paramError(response);
        }
        try {
            TagInfo tagInfo = new TagInfo();
            tagInfo.setTagName(request.getTagName());
            tagInfo.setUserId(request.getUserId());
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

    /**
     * 校验标签名称是否唯一
     *
     * @param tagName 标签名称
     * @return
     */
    @RequestMapping(value = "/tag/name/unique")
    public BaseResponse checkTagNameUnique(@RequestParam("tagName") String tagName) {
        TagInfo record = tagInfoService.findTagInfoByName(tagName);
        return record == null ? ResponseUtil.success() : ResponseUtil.error();
    }

    /**
     * 删除标签
     *
     * @param request
     * @return
     */
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

    /**
     * 列出账单赋值的标签
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/cost/tag/list")
    public CostTagListResponse listCostTag(@ModelAttribute("request") CostTagListRequest request) {
        CostTagListResponse response = new CostTagListResponse();
        if (StringUtils.isEmpty(request.getTradeNo())) {
            return ResponseUtil.paramError(response);
        }
        try {
            List<TagInfo> costTagList = tagInfoService.listTagInfoByTradeNo(request.getTradeNo());
            TagInfoDTO model;
            List<TagInfoDTO> result = new ArrayList<>();
            for (TagInfo entity : costTagList) {
                model = new TagInfoDTO();
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

    /**
     * 添加账单标签
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/cost/tag/put")
    public BaseResponse putCostTag(@ModelAttribute("request") CostTagPutRequest request) {
        BaseResponse response = new BaseResponse();
        if (StringUtils.isEmpty(request.getTradeNo()) || request.getTagId() == null) {
            return ResponseUtil.paramError(response);
        }
        try {
            CostRecord costRecord = costRecordService.findByTradeNo(request.getTradeNo(), request.getUserId());
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

    /**
     * 删除账单标签
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/cost/tag/delete")
    public BaseResponse delCostTag(@ModelAttribute("request") CostTagDelRequest request) {
        BaseResponse response = new BaseResponse();
        if (request.getTagId() == null || StringUtils.isEmpty(request.getTradeNo())) {
            return ResponseUtil.paramError(response);
        }
        try {
            CostRecord costRecord = costRecordService.findByTradeNo(request.getTradeNo(), request.getUserId());
            TagInfo tagInfo = tagInfoService.getTagInfoById(request.getTagId());
            if (costRecord != null && tagInfo != null) {
                if (tagInfoService.deleteCostTag(costRecord.getId(), tagInfo.getId())) {
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
