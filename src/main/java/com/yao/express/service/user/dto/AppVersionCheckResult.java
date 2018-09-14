package com.yao.express.service.user.dto;

public class AppVersionCheckResult {
    // id
    private Long id;
    // 最新版本号
    private String version;
    // app包名
    private String pkg;
    // 包类型:android，ios
    private String type;
    // 应用名
    private String name;
    // 是否强制更新
    private Boolean isForce;
    // 更新包地址
    private String url;
    // 更新日志
    private String log;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPkg() {
        return pkg;
    }

    public void setPkg(String pkg) {
        this.pkg = pkg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getForce() {
        return isForce;
    }

    public void setForce(Boolean force) {
        isForce = force;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }
}
