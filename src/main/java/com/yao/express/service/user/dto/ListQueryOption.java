package com.yao.express.service.user.dto;

import com.yao.express.service.user.response.AppResponse;
import com.yao.express.service.user.exception.ResponseErrorCode;
import org.apache.commons.lang.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ListQueryOption {

    // 页数，从1开始
    private Integer page;
    // 每页大小
    private Integer size;
    // 排序字段，+fieldName|-fieldName，+表示升序，-表示降序，多个以英文都好隔开
    private Map<String, Integer> sorts;
    // 返回字段
    private List<String> fields;
    // 筛选条件：in / bettween / > / < / =
    // createTime
    private List<String> between;
    // =
    private Map<String, String> equals;
    private List<String> isNull;
    private List<String> isNotNull;
    // in ([])
    private Map<String, List<String>> ins;
    // keyword模糊匹配, 模糊匹配key=keyword，value=适应范围字段
    private Map<String, List<String>> search;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Map<String, Integer> getSorts() {
        return sorts;
    }

    public void setSorts(Map<String, Integer> sorts) {
        this.sorts = sorts;
    }

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    public List<String> getBetween() {
        return between;
    }

    public void setBetween(List<String> between) {
        this.between = between;
    }
    public Map<String, String> getEquals() {
        return equals;
    }

    public void setEquals(Map<String, String> equals) {
        this.equals = equals;
    }

    public List<String> getIsNull() {
        return isNull;
    }

    public void setIsNull(List<String> isNull) {
        this.isNull = isNull;
    }

    public List<String> getIsNotNull() {
        return isNotNull;
    }

    public void setIsNotNull(List<String> isNotNull) {
        this.isNotNull = isNotNull;
    }

    public Map<String, List<String>> getIns() {
        return ins;
    }

    public void setIns(Map<String, List<String>> ins) {
        this.ins = ins;
    }

    public Map<String, List<String>> getSearch() {
        return search;
    }

    public void setSearch(Map<String, List<String>> search) {
        this.search = search;
    }

    public ListQueryOption page(Integer page) {
        this.setPage(page == null || page <= 0 ? 1 : page);
        return this;
    }

    public ListQueryOption size(Integer size) {
        this.setSize(size == null || size <= 0 ? 10 : size);
        return this;
    }

    public ListQueryOption between(List<String> between) {
        if (null != between && between.size() >= 2) {
            for (String dateStr : between) {
                if (StringUtils.isNotBlank(dateStr)) {
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = null;
                    try {
                        date = df.parse(dateStr);
                    } catch (ParseException e) {
                        e.printStackTrace();// FIXME
                    }
                }
            }
            this.setBetween(between);
        }
        return this;
    }

    public ListQueryOption sorts(String sorts) {
        if (StringUtils.isNotBlank(sorts)) {
            String[] sts = sorts.trim().split(",");
            Map<String, Integer> sortMap =
                    this.getSorts()==null ? new LinkedHashMap<>(sts.length) : this.getSorts();
            for (String s : sts) {
                if (StringUtils.isNotBlank(s)) {
                    String key = s;
                    Integer value = 1;
                    if (s.startsWith("+")) {
                        key = s.substring(1);
                        value = 1;
                    } else if (s.startsWith("-")) {
                        key = s.substring(1);
                        value = -1;
                    }
                    sortMap.put(key, value);
                }
            }
            this.setSorts(sortMap);
        } else {
            Map<String, Integer> sortMap = new LinkedHashMap<>(1);
            sortMap.put("id", -1);
            this.setSorts(sortMap);
        }
        return this;
    }

    public ListQueryOption ins(String field, List<String> ins) {
        if (null != ins && ins.size()>0 && StringUtils.isNotBlank(field)) {
            Map<String, List<String>> inMap = this.getIns()==null ? new LinkedHashMap<>() : this.getIns();
            inMap.put(field, ins);
            this.setIns(inMap);
        }

        return this;
    }

    public ListQueryOption search(String keyword, List<String> searchFields) {
        if (StringUtils.isNotBlank(keyword)) {
            Map<String, List<String>> search =
                    this.getSearch()==null ? new LinkedHashMap<>() : this.getSearch();
            search.put(keyword, searchFields);
            this.setSearch(search);
        }

        return this;
    }

    public ListQueryOption isNull(String field) {
        if (StringUtils.isNotBlank(field)) {
            List<String> fields = this.isNull == null ? new ArrayList<>() : this.isNull;
            fields.add(field);
            this.setIsNull(fields);
        }

        return this;
    }

    public ListQueryOption isNotNull(String field) {
        if (StringUtils.isNotBlank(field)) {
            List<String> fields = this.isNotNull == null ? new ArrayList<>() : this.isNotNull;
            fields.add(field);
            this.setIsNotNull(fields);
        }

        return this;
    }

    public ListQueryOption eq(String field, String value) {
        if (StringUtils.isNotBlank(field) && StringUtils.isNotBlank(value)) {
            Map<String, String> eqMap = this.getEquals() == null ? new LinkedHashMap<>() : this.getEquals();
            eqMap.put(field, value);
            this.setEquals(eqMap);
        }
        return this;
    }

    public static ListQueryOption build() {
        return new ListQueryOption();
    }
}

