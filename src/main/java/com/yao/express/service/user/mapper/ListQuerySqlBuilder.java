package com.yao.express.service.user.mapper;

import com.yao.express.service.user.dto.ListQueryOption;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;
import java.util.Map;

public class ListQuerySqlBuilder {

    public SQL build(String table, ListQueryOption queryOption) {
        if (StringUtils.isBlank(table) || queryOption == null) {
            return null;
        }
        SQL sql = new SQL();

        if (queryOption.getFields() == null || queryOption.getFields().size() == 0) {
            sql.SELECT("*");
        } else {
            sql.SELECT("*"); // FIXME
        }

        sql.FROM(table);
        sql.WHERE("1=1");

        if (queryOption.getBetween() != null) {
            if (queryOption.getBetween().size()==2) {
                String from = queryOption.getBetween().get(0);
                String to = queryOption.getBetween().get(1);
                sql.AND();
                sql.WHERE("create_time BETWEEN '"+from+"' AND '"+to+"'");
            }
        }

        if (queryOption.getEquals() != null && queryOption.getEquals().size()>0) {
            Map<String, String> equals = queryOption.getEquals();
            for (String field : equals.keySet()) {
                sql.AND();
                sql.WHERE("'"+field+"' = '"+equals.get(field)+"'");
            }
        }

        if (queryOption.getIns() != null && queryOption.getIns().size()>0) {
            Map<String, List<String>> ins = queryOption.getIns();
            for (String key : ins.keySet()) {
                String in = "";
                int i = 0;
                if (ins.get(key)!=null && ins.get(key).size()>0) {
                    for (String v : ins.get(key)) {
                        in += "'"+v+"'";
                        if (++i < ins.get(key).size()) {
                            in += ",";
                        }
                    }
                }
                sql.AND();
                sql.WHERE(key+" IN ("+in+")");
            }
        }

        if (queryOption.getIsNull() != null && queryOption.getIsNull().size()>0) {
            for (String field : queryOption.getIsNull()) {
                sql.AND();
                sql.WHERE(field + " IS NULL");
            }
        }

        if (queryOption.getIsNotNull() != null && queryOption.getIsNotNull().size()>0) {
            for (String field : queryOption.getIsNotNull()) {
                sql.AND();
                sql.WHERE(field + " IS NOT NULL");
            }
        }

        if (queryOption.getSearch() != null && queryOption.getSearch().size()>0) {
            sql.AND();
            String condition = "";
            for (String keyword: queryOption.getSearch().keySet()) {
                List<String> fields = queryOption.getSearch().get(keyword);
                if (null != fields && fields.size()>0) {
                    int count = 0;
                    for (String field: fields) {
                        condition += "("+field + " LIKE " + "'%"+keyword+"%')";
                        if (++count < fields.size()) {
                            condition +=" OR ";
                        }
                    }
                }
            }
            sql.WHERE(condition);
        }

        if (queryOption.getSorts() != null) {
            Map<String, Integer> sorts = queryOption.getSorts();
            if (sorts.size()>0) {
                for (String key : sorts.keySet()) {
                    String columns = key;
                    Integer v = sorts.get(key);
                    if (v > 0) {
                        columns += " asc";
                    } else if (v < 0) {
                        columns += " desc";
                    }

                    sql.ORDER_BY(columns);
                }
            }
        }

        return sql;
    }
}
