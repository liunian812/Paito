package com.paito.web.common;

import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by patrick on 16/1/17.
 */
public class AjaxResult {
    private Info info;

    private Map<?, ?> data;

    public AjaxResult() {
    }

    private AjaxResult(boolean isOk) {
        this.info = new Info(isOk);
    }

    public Info getInfo() {
        return info;
    }

    public Map<?, ?> getData() {
        if (data == null) {
            data = new HashMap<Object, Object>();
        }
        return data;
    }

    public void setData(Map<?, ?> data) {
        this.data = data;
    }

    public static AjaxResult succResult() {
        return new AjaxResult(true);
    }

    public static AjaxResult succResult(String message) {
        AjaxResult ajaxResult = new AjaxResult(true);
        ajaxResult.getInfo().setMessage(message);
        return ajaxResult;
    }

    public static AjaxResult succResult(String key, Object value) {
        AjaxResult ajaxResult = new AjaxResult(true);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put(key, value);
        ajaxResult.setData(data);
        return ajaxResult;
    }

    public static AjaxResult succResult(Map<?, ?> dataMap) {
        AjaxResult ajaxResult = new AjaxResult(true);
        ajaxResult.setData(dataMap);
        return ajaxResult;
    }

    public static AjaxResult succPageResult(Object pager, List<?> list) {
        AjaxResult ajaxResult = new AjaxResult(true);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("pager", pager);
        data.put("list", list);
        ajaxResult.setData(data);
        return ajaxResult;
    }

    public static AjaxResult errorResult(String message) {
        AjaxResult result = new AjaxResult(false);
        result.getInfo().setMessage(message);
        return result;
    }

    public static AjaxResult errorResult() {
        AjaxResult ajaxResult = new AjaxResult(false);
        ajaxResult.getInfo().setMessage("system.error");
        return ajaxResult;
    }

    public String toSimpleJsonString() {
        return "{\"info\":{\"message\":\"" + info.getMessage() + "\",\"ok\":" + info.isOk() + "},\"data\":{}}";
    }

    @Override
    public String toString() {
        return JSONObject.fromObject(this).toString();
    }

    public static AjaxResult errorResult(String message, boolean isVirtualLogin) {
        AjaxResult result = new AjaxResult(false);
        result.getInfo().setMessage(message);
        return result;
    }
}
