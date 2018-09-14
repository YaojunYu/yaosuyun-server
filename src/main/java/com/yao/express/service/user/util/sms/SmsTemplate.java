package com.yao.express.service.user.util.sms;

/**
 * SMS短信模板
 *
 * @author: York.Yu
 * @date: 2017/4/29
 */
public class SmsTemplate {

    // 短信验证码模板
    public static String VALIDATE_CODE_TEMPLATE_ID = "3063296";
    // 采购合伙人入驻成功
    public static String PARTNER_SUCCESS_TEMPLATE_ID = "3060386";
    // 供应商入驻成功通知
    public static String SUPPLIER_SUCCESS_TEMPLATE_ID = "3063295";
    // 供应商入驻失败通知
    public static String SUPPLIER_FAILD_TEMPLATE_ID = "3064344";

    /**短信模板id**/
    private String templateId;

    /**模板变量数组**/
    private String[] params;

    public SmsTemplate(String templateId, String[] params) {
        this.templateId = templateId;
        this.params = params;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String[] getParams() {
        return params;
    }

    public void setParams(String[] params) {
        this.params = params;
    }
}
