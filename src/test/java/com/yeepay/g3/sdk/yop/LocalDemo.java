package com.yeepay.g3.sdk.yop;

import com.yeepay.g3.sdk.yop.client.YopClient3;
import com.yeepay.g3.sdk.yop.client.YopRequest;
import com.yeepay.g3.sdk.yop.client.YopResponse;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotNull;

/**
 * title: <br>
 * description: 描述<br>
 * Copyright: Copyright (c)2014<br>
 * Company: 易宝支付(YeePay)<br>
 *
 * @author baitao.ji
 * @version 1.0.0
 * @since 15/7/8 10:23
 */
public class LocalDemo {

    private static final String appKey = "yop-boss";
    private static final String priKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCXsKWWClznZbdTwp9183e4Ygu/twbQhuS6LPpu/TZ+OFwwauvIZnOyKu+rFh6apKyVxiLEkssnTsBjLjUIlypEGU2SdLGkswWAPvVdunLjjWEz37W2w4VNkGf8bGCQ9fIxMynoBCTeeWcQz896e1y2p5YZHygUhXGLM/9q5mr3iQQgrEPdFEAdlfLexkbVIF2bS02NsDFLNvqKNk7219cefxWPgJfN7RukUIZyy4nbeevbMAAFpNUFh1NlAh4qzwocOfbZ3NgtwJDf29jibpM3dacS7tqYGwpeGpKazS9tZgTAYcX2kLT7s+G6vVzVQR61pvvDs5ubyfsw/KFR8KDDAgMBAAECggEAShSE6Z+p+4AbZhaYVbxPbYbEgh5af6BBOAMbUvTqlf3kV+j/uWD/g7WgUod87r0ZZBPdiu69tDarkkRQth9NDvDkh2/iCbM8LoOQxPN3hFXZcMICNn2KLnUls4siJelXHFwGTT8o2lWj1fwHMaPphXKWxTIIGu2IpBkC1iwtdTF8mqe2HH+H2djBE96JXVZIf3/FgGu8ppmXa/xG4DfrTxFnGEJzgaadT3Z+ybXbqjYgFgmmBnZOaTx1XPQfLGQVYJz9BunDhwhrqBUM+QuLr1jUsMsj/Yud52cNXjwq9z8FfkKUdVVfE4VrzH8JpKKk7Vim7RWBQER29jlEnV+ysQKBgQDjMWxZz4AveXxWSx7MgXN9PEzxzmGWSApseDskSi5PAmXa4ut5XyNJUiGJ8Zf+cssPfWFNtB7suJBuoMTtrQSap2tgoo70y7QSO0ZlZ0v5Ny9LYh8oHvDgBJVNmS5HWv1U1/VHxNHczNmQ05smXNo1bzMYe5Xo10J2W47UUTgOHwKBgQCq7G6B5RfD+O1jdmYWlilh5oi1XGdYJGnzhs9DmAUN5plQ3VxpUFxxQCgOwXCskfT9QUVYhsIpQIs2iCylwuNDuxxiEQyRpeBirRaqmxvosv08Trwsr1Vs/Cuh17ZZOS+OUehN0fDZCiruK4e2btVfv8LlE1KMuoiUsn1X2gWQ3QKBgCyqBrcRSA4NQBhm5EMoH+A6/pV7EUxOFV6FtHrJ6pi1y/hgLBLMVU+Qye8og80OHEWLTJnOE1ZOYnadPJnNLd6Jk16IFrqhYWFELe65hAIWi0GypJVqn8gqnn+G4cY9aRhI7HuTgf56dzs1nobIMk3W8qCZizsfNn22OjobTX3ZAoGBAJsTusvF1IMs5g05DjTt9wvpQx3xgZ46I5sdNA3q7qMHFxGEVeUDUWw7Plzs61LXdoUU5FsGoUEWW3iVopSett3r9TuQpmu7KVO+IXOXGYJOa259LUQJrKMeRGQpuDtJpDknXXLFyRTSodLH0fEWrCecb7KxjlM6ptLrAshjemtNAoGBAMzGo6aNER8VZfET8Oy0i5G8aVBp6yrMiQsNOj4S1VPoHI+Pc6ot5rDQdjek9PRzF9xeCU4K7+KLaOs6fVmTfsFpPbDafCTTmos9LGr5FIyXpU7LQCl3QPHWPDd5ezsu9SPVjzsEPX3WTSOJuUA8hE7pJnAzMHLGAFpIXJRu3Z/y";

    @BeforeClass
    public static void setUp() throws Exception {
        System.setProperty("yop.sdk.config.file", "config/yop_sdk_config_local.json");
//        System.setProperty("yop.sdk.trust.all.certs", "true");
    }

    @Test
    public void testRsa_auth() throws Exception {
        YopRequest request = new YopRequest();
        request.addParam("corpName", "安徽四创电子股份有限公司青海分公司");//企业名称
        request.addParam("regNo", "630104063035716");//工商注册号
        request.addParam("requestCustomerId", "yop-boss");//子商户编号
        request.addParam("requestFlowId", "test-" + System.currentTimeMillis() + RandomStringUtils.randomNumeric(3));//请求流水标识
        request.addParam("requestIdentification", "unit test");//请求者标识

//        request.setNeedEncrypt(true);

        YopResponse response = YopClient3.postRsa("/rest/v3.0/auth/enterprise", request);
        AssertUtils.assertYopResponse(response);
    }

    @Test
    public void testRsa_notifier() throws Exception {
        YopRequest request = new YopRequest();
        request.setSignAlg("sha-256");//具体看api签名算法而定
        String notifyRule = "ad_rule";//通知规则
        List<String> recipients = new ArrayList<>();//接收人
        recipients.add("18511620061");
        String content = "{\"code\":\"123445\"}";//json字符串，code为消息模板变量
        request.addParam("notifyRule", notifyRule);
        request.addParam("recipients", recipients);
        request.addParam("content", content);

        YopResponse response = YopClient3.postRsa("/rest/v4.0/notifier/send", request);
        AssertUtils.assertYopResponse(response);
    }

    @Test
    public void testRsa2() throws IOException {
        YopRequest request = new YopRequest(appKey, priKey);
        request.addParam("corpName", "安徽四创电子股份有限公司青海分公司");//企业名称
        request.addParam("regNo", "630104063035716");//工商注册号
        request.addParam("requestCustomerId", "yop-boss");//子商户编号
        request.addParam("requestFlowId", "test-" + System.currentTimeMillis() + RandomStringUtils.randomNumeric(3));//请求流水标识
        request.addParam("requestIdentification", "unit test");//请求者标识

        YopResponse response = YopClient3.postRsa("/rest/v1.0/sys/trade/order", request);
        AssertUtils.assertYopResponse(response);
    }

    @Test(timeout = 30000)
    public void testRsaUploadFile() throws IOException {
        YopRequest request = new YopRequest();
        request.addParam("fileType", "IMAGE");
//        request.addParam("_file", "file:/Users/dreambt/xuekun-3.pfx");
        request.addParam("_file", "src/test/resources/log4j2.xml");

        YopResponse response = YopClient3.uploadRsa("/rest/v1.0/file/upload", request);
        AssertUtils.assertYopResponse(response);
        assertTrue(StringUtils.endsWith((String) ((HashMap) (((ArrayList) ((HashMap) response.getResult()).get("files")).get(0))).get("fileName"), ".xml"));
    }

    @Test
    public void testRsaUploadFileNew1() throws IOException {
        YopRequest request = new YopRequest();
        request.addParam("fileType", "IMAGE");

        request.addFile("src/test/resources/log4j2.xml");

        YopResponse response = YopClient3.uploadRsa("/rest/v1.0/file/upload", request);
        AssertUtils.assertYopResponse(response);
        assertTrue(StringUtils.endsWith((String) ((HashMap) (((ArrayList) ((HashMap) response.getResult()).get("files")).get(0))).get("fileName"), ".xml"));
    }

    @Test(timeout = 30000)
    public void testRsaUploadFileNew2() throws IOException {
        YopRequest request = new YopRequest();
        request.addParam("fileType", "IMAGE");

        request.addFile(new File("src/test/resources/log4j2.xml"));

        YopResponse response = YopClient3.uploadRsa("/rest/v1.0/file/upload", request);
        AssertUtils.assertYopResponse(response);
        assertTrue(StringUtils.endsWith((String) ((HashMap) (((ArrayList) ((HashMap) response.getResult()).get("files")).get(0))).get("fileName"), ".xml"));
    }

    @Test(timeout = 30000)
    public void testRsaUploadFileNew3() throws IOException, URISyntaxException {
        YopRequest request = new YopRequest();
        request.addParam("fileType", "IMAGE");

        FileInputStream stream = new FileInputStream(new File("src/test/resources/log4j2.xml"));
        request.addFile(stream);

        YopResponse response = YopClient3.uploadRsa("/rest/v1.0/file/upload", request);

        AssertUtils.assertYopResponse(response);
        assertTrue(StringUtils.endsWith((String) ((HashMap) (((ArrayList) ((HashMap) response.getResult()).get("files")).get(0))).get("fileName"), ".xml"));
    }

    @Test(timeout = 30000)
    public void testRsaUploadFileNewUseURL() throws IOException, URISyntaxException {
        YopRequest request = new YopRequest();
        request.addParam("fileType", "IMAGE");

        request.addFile(new URL("https://www.baidu.com/img/bd_logo1.png").openStream());

        YopResponse response = YopClient3.uploadRsa("/rest/v1.0/file/upload", request);
        AssertUtils.assertYopResponse(response);
        assertTrue(0 < ((Integer) ((HashMap) (((ArrayList) ((HashMap) response.getResult()).get("files")).get(0))).get("fileSize")));
        assertTrue(StringUtils.endsWith((String) ((HashMap) (((ArrayList) ((HashMap) response.getResult()).get("files")).get(0))).get("fileName"), ".png"));
    }

    @Test
    public void name1() throws IOException {
        //secretKey:商户私钥（字符串形式）
        String OPRkey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDKLgI+64mmJdNg1TwlCPBnNH3b3qfw2TdHVc2uDd4LTyQI8nRr0heFhhdj0OZi6agqekIyzAH/XmO9PdLrTi4YXJXOfiO/dYwKA6gSktRe6FKY4C2WzX1yA4fGfqJMV7RYVoL6In50Hur6rGnavNSQZqbiDJOgy5yokJ14Mey1iMqqqWvADtKN9SqxtbyIxYD/jj/6qLWwmu88wSwSaGdO3wNFgzajsHgRJe9G9IhD0zr5d72HvJGoedq7VaPn3jhIszcPQE6oqbXAddZRGKBehA4WSCjLEl87XH33zZPrxrQlBTHVVGzfxjbB4QvYz0hlEoWh1ntxeDHTfgyhdPQpAgMBAAECggEATmxMSLW6Xe08McpkmwT9ozq0Oy4BvKW1EIGS15nfcEmRc7sAN7Z1k0BxIDGuu91gcqGbvfJuL+0gCQ7LGqTnsmFvZnp9SU3CNTw33ISBxhKdv1jtthodN7Vw3CjQsYYvmThtc7Mfk9FOWk+4e7VVSnHW98XjGbMBIE2AF1heNgeZ40ubdgzuz9+4g4pphjWncPpwcaMfsDZm3JtFyvUp0+LME0CmUqrxvONZAkpFR/PyejGHnIh3ptHzhe/VjNcuIC4PphkCNBakCBCrtohTy0YeeWfDAUTAO4tPXF/JUhlxjPuqR6rpQY/0uQdMAtTpiWHVJar7eGdK81QnuuOFRQKBgQDrklUPM0pkvGG/wREa0bgUI+ki+1/wv7O8X94/8onomJqPpkD8z4hv/Lev/wD5gDcgmgLC36u/XDuhFfVNOmw4eUWenU6pzonroEjhi91AKcRRfzDfOfWg3wPm1J9WQOn5A033tNRydCpVcX/Ot4qDbKcAwLiPNPXXMTn4LUQE/wKBgQDbtmE0KS/kSfjscWJOqwv1XbxckipkxncqIbdiSdU+DzaLd+Vuaco7TLQJRFp7S7WJW4Tz6KBX2UiA7O7ezXY9PwlgXxXiZDDtneXNAqk7DNxmTTZHrF2C7qdU98klppCFiFx9bysGY6lFWofWmg3Pu5IiPqO3iLRPTvZgQOE+1wKBgQC9SCgmfYzyIlfcjtIinY5uSGiEnjz5od9WpiVbdpOPHEdc0zZ2rH6xlPs3ZAuxbm9dN8KuOLC0ovSau50Nv7rDKdZh234gfP9fH7xP1mUhsC25Why30MdnyqpE6GVbFe+qERitx1PI30RAwWDzhZC7hystNK1XDDPZBAnTOvPjmwKBgDFuujX7IkxRnFDOPdkHQNyGp2+Ib0NXJ85x4YmapQCeeZ4tbpBF+vsWidcf6t+crA5oaeRarWC2gUqIhEHapkSnXxuwqQLTmfKMOPzEIYEoppnZu2Gq1Ss1OK60RSxUamWwxWZvUZXRbG8vLCrLZFodkIZl433SowbI9EO5tTPnAoGAJRsy1z95Q1GPkKrFtKivkxZy1k7zJXjM0VWDc7lT9fBnoeGUyt+vuq+lC5i2aiWKJK7pe8MM9QFDGlWPnly+J8jbyMfm99k5oJtCWDfF0or1pAQ4mw0kjL9TvDVXdojgYA+rxSMQ09hwsYukQ4bblrwfBUmRjLN5WibcRzIW5ZA=";

        //step1  生成yop请求对象
        //arg0:appkey（举例授权扣款是SQKK+商户编号，亿企通是OPR:+商户编号，具体是什么请参考自己开通产品的手册。
        //arg1:商户私钥字符串
        YopRequest request = new YopRequest("OPR:10000466938", OPRkey);

        //step2 配置参数
        //arg0:参数名
        //arg1:参数值
        request.addParam("merchantNo", "10000466938");
        request.addParam("dayString", "2018-11-20");


        //step3 发起请求
        //arg0:接口的uri（参见手册）
        //arg1:配置好参数的请求对象
        YopResponse response = YopClient3.postRsa("/yos/v1.0/std/bill/tradedaydownload", request);
        AssertUtils.assertYopResponse(response);

//        InputStream retstream= response.getFile();
//        byte[] bytes = new byte[0];
//        bytes = new byte[retstream.available()];
//        retstream.read(bytes);
//        String str = new String(bytes);

        System.out.println(IOUtils.toString(response.getFile()));
    }

}
