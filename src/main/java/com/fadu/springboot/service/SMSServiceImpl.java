package com.fadu.springboot.service;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.fadu.springboot.common.JsonData;
import com.fadu.springboot.service.interfaces.SMSService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 发送短信业务组件
 *
 * @Auther: wangchun
 * @Date: 2018/6/27 13:32
 */
@Service("SMSServiceImpl")
@Slf4j
public class SMSServiceImpl implements SMSService {

    @Override
    public JsonData sendDayuSystemMsg(String mobile, String templateCode, String templateParam, String signName) {
        JsonData res = new JsonData(false);
        if (!checkPhone(mobile)) {
            res.setMsg("手机号码不正确" + mobile);
            return res;
        }
        res = sendDayuSms(mobile, templateCode, templateParam, signName);
        return res;
    }

    @Override
    public JsonData sendDayuSystemMsg(String mobile, String templateCode, String templateParam) {
        return this.sendDayuSystemMsg(mobile, templateCode, templateParam, DEFAULT_SIGNNAME);
    }

    @Override
    public JsonData sendDayuSystemMsg(String mobile, String templateCode) {
        return this.sendDayuSystemMsg(mobile, templateCode, null);
    }

    // 检查是否是手机号
    private boolean checkPhone(String phone) {
        if (StringUtils.isEmpty(phone)) {
            return false;
        }
        String regex = "^\\d{11}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phone);
        return matcher.find();
    }

    protected String connectURL(String dest_url, String content, int timeout) {
        String rec_string = "";
        URL url = null;
        HttpURLConnection urlconn = null;
        OutputStream out = null;
        BufferedReader rd = null;
        try {
            url = new URL(dest_url);
            urlconn = (HttpURLConnection) url.openConnection();
            if (timeout > 1000)
                urlconn.setReadTimeout(timeout);
            else
                urlconn.setReadTimeout(5000);
            urlconn.setRequestMethod("POST");

            urlconn.setDoInput(true);
            urlconn.setDoOutput(true);
            out = urlconn.getOutputStream();
            out.write(content.getBytes("UTF-8"));
            out.flush();
            out.close();
            rd = new BufferedReader(new InputStreamReader(urlconn.getInputStream(), "UTF-8"));
            StringBuffer sb = new StringBuffer();
            int ch;
            while ((ch = rd.read()) > -1)
                sb.append((char) ch);
            rec_string = sb.toString();

        } catch (Exception e) {
            log.error("发送错误：", e);
        } finally {
            try {
                if (rd != null) {
                    rd.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (urlconn != null) {
                    urlconn.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return rec_string;
    }

    /**
     * 发送手机短信
     *
     * @param mobile  手机号
     * @param content 短信内容
     * @return 结果信息
     */
    private JsonData sendSms(String mobile, String content) {
        JsonData res = new JsonData(false);
        try {
            String body = "account=5100030&pswd=Aa123456&mobile=" + mobile + "&needstatus=true&msg=" + content;
            String smsret = connectURL("http://54.223.235.82/msg/HttpBatchSendSM", body, 5000);
            if (null == smsret) {
                res.setMsg("无返回数据！");
                log.error("发送短信验证码错误,由于第三方无返回数据。此次发送的手机号为：->{}", mobile);
            } else {
                if (smsret.contains(",0")) {
                    res.setMsg("提交成功,请注意查收短信！");
                    res.setRet(true);
                } else if (smsret.contains(",103")) {
                    res.setMsg("提交太频繁，请稍后再试！");
                    log.error("发送提交太频繁(" + mobile + ")，第三方返回：" + smsret);
                } else if (smsret.contains(",107")) {
                    res.setMsg("手机号码错误！");
                    log.error("发送短信验证码手机号码错误,手机号为：->{}，阿里错误信息返回：->{}", mobile, smsret);
                } else {
                    res.setMsg("发送失败，请稍后重试！");
                    log.error("发送短信错误,发送手机号为：->{}，阿里错误信息返回：->{}", mobile, smsret);
                }
            }
        } catch (Exception e) {
            log.error("发生错误:", e);
            e.printStackTrace();
            res.setMsg("发送短信验证码,提交出现错误[" + e.getMessage() + "]");
        }
        return res;
    }

    /**
     * 阿里大于短信发送平台
     *
     * @param mobile        手机号
     * @param templateCode  模板号
     * @param templateParam 模板使用业务参数变量
     * @param signName      商户签名
     * @return 结果信息
     */
    private JsonData sendDayuSms(String mobile, String templateCode, String templateParam, String signName) {
        JsonData response = new JsonData(false);
        response.setRet(false);
        //设置超时时间-可自行调整
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        //初始化ascClient需要的几个参数
        final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
        final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
        //替换成你的AK
        final String accessKeyId = "LTAIsgx76QHeOxiS";//你的accessKeyId,参考本文档步骤2
        final String accessKeySecret = "FY68AIdO1a4izt6onsKkDdYFDpzMI5";//你的accessKeySecret，参考本文档步骤2
        //初始化ascClient,暂时不支持多region（请勿修改）
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        try {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        } catch (ClientException e) {
            log.error("发生错错误：", e);
            response.setMsg("发送短信验证码,服务器异常错误[" + e.getMessage() + "]");
            return response;
        }
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象
        SendSmsRequest request = new SendSmsRequest();
        //使用post提交
        request.setMethod(MethodType.POST);
        //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
        request.setPhoneNumbers(mobile);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName(signName);
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(templateCode);
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
        request.setTemplateParam(templateParam);
        //可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");
        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("yourOutId");
        //请求失败这里会抛ClientException异常
        SendSmsResponse sendSmsResponse;
        try {
            sendSmsResponse = acsClient.getAcsResponse(request);
            if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
                response.setRet(true);
                response.setMsg("发送成功！");
            } else {
                log.error("大鱼短信发送失败，阿里返回错误原因为：->{}", sendSmsResponse.getMessage());
                response.setMsg("发送失败，请稍后重试！");
            }
        } catch (ClientException e) {
            log.error("ClientException:", e);
            return response;
        }
        return response;
    }

}
