package com.yao.express.service.user.util.html;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;

/**
 * 静态化工具
 *
 * @author: York.Yu
 * @date: 2017/5/10
 */
@Service
public class HTMLService {

    private Logger logger = LoggerFactory.getLogger(HTMLService.class);

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 生成静态页面主方法
     *
     * @param data 一个Map的数据结果集
     * @param templatePath ftl模版路径
     * @param redisKey 生成静态页面数据缓存到redis的键值
     */
    public String create(Map<String, Object> data, String templatePath, String redisKey) {
        return createHTML(data, templatePath, redisKey);
    }

    /**
     * 生成静态页面主方法
     *
     * @param data 一个Map的数据结果集
     * @param srcFtlPath ftl模版路径
     * @param redisKey 生成静态页面数据缓存到redis的键值
     */
    private String createHTML(Map<String, Object> data, String srcFtlPath, String redisKey) {
        logger.info("生成HTML静态页面数据：[data:{}, src:{}, redisKey: {}]", data, srcFtlPath, redisKey);
        long start = System.currentTimeMillis();
        Configuration fmCfg = new Configuration(Configuration.VERSION_2_3_25);
        /**加载ftl模版**/
        fmCfg.setEncoding(Locale.getDefault(), "UTF-8");
        String htmlStr = null;
        try {
            // 指定模板加载器
            fmCfg.setClassForTemplateLoading(this.getClass(), "/templates");

            // 指定模版路径
            Template template = fmCfg.getTemplate(srcFtlPath, "UTF-8");
            // 处理模版数据
            Writer out = new StringWriter();
            template.process(data, out);
            // 静态页面保存到redis缓存
            htmlStr = out.toString();
            redisTemplate.opsForValue().set(redisKey, htmlStr);

            logger.info("生成静态化首页完成[time: {}ms]", System.currentTimeMillis() - start);
            out.flush();
            out.close();
        } catch (Exception e) {
            logger.error("静态化首页完成失败！！！");
            e.printStackTrace();
        }

        return htmlStr;
    }

    /**
     * 从缓存中获取静态页面数据
     * @param htmlName
     * @return
     */
    public String getHtml(String htmlName) {
        if(StringUtils.isBlank(htmlName)) return null;
        return (String) redisTemplate.opsForValue().get(htmlName);
    }

}
