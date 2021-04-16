package org.ravic.blog.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 这是配置资源路径的配置类
 * 采用配置文件yml注入的方式
 */
@Component
@ConfigurationProperties(prefix = "sourceconfig")
public class SourceConfig {
    //微信收款码
    private String weChatPay;

    //支付宝收款码
    private String aliPay;

    //作者二维码
    private String weChatQr;


    public String getWeChatPay() {
        return weChatPay;
    }

    public void setWeChatPay(String weChatPay) {
        this.weChatPay = weChatPay;
    }

    public String getAliPay() {
        return aliPay;
    }

    public void setAliPay(String aliPay) {
        this.aliPay = aliPay;
    }

    public String getWeChatQr() {
        return weChatQr;
    }

    public void setWeChatQr(String weChatQr) {
        this.weChatQr = weChatQr;
    }
}
