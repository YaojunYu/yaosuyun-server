package com.yao.express.service.user.util.html;

import com.yao.express.service.user.util.html.HTMLService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 首页静态化
 *
 * @author: York.Yu
 * @date: 2017/5/10
 */
@Service
public class IndexHtmlService {

    private Logger logger = LoggerFactory.getLogger(com.yao.express.service.user.util.html.IndexHtmlService.class);

    @Autowired
    private HTMLService htmlService;

    /**
     * 生成首页静态页面
     */
    public String createIndexHtml() {
        return htmlService
                .create(getIndexData(), "pages/index.ftl", "index.html");
    }

    /**
     * 获取首页静态页面收据
     */
    public String getIndexHtml(boolean refresh) {
        long start = System.currentTimeMillis();
        String result = htmlService.getHtml("index.html");
        if (null == result || refresh) {
            // 如果缓存中没有则重新生成
            result = createIndexHtml();
        } else {
            // 如果缓存中存在则异步刷新
            /*new Thread(() -> {
                // FIXME 异步处理请求生成静态化页面
                createIndexHtml();
            }).start();*/
        }
        logger.info("商城首页响应时间：{}ms", System.currentTimeMillis() - start);
        return result;
    }

    /**
     * 获取商城首页数据
     */
    private Map getIndexData() {
        // TODO 获取首页数据
        return null;
    }
}
