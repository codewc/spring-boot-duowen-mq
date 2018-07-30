package com.fadu.springboot.rmi;

import com.fadu.springboot.model.rmi.app.e.E103Request;
import com.fadu.springboot.model.rmi.app.e.E103Response;
import com.fadu.springboot.service.interfaces.rmi.AppEServiceRMIHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: wangchun
 * @Date: 2018/7/27 18:42
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class AppEServiceRMITest {

    @Autowired
    private AppEServiceRMIHelper serviceRMIHelper;

    @Test
    public void doE103Service() throws Exception {
        List<String> phoneContainer = new ArrayList<>();
        readFileByLines("E:\\wangchun\\Intelli_workspace\\git\\spring-boot-examples\\spring-boot-rabbitmq\\src\\test\\java\\com\\fadu\\springboot\\rmi\\TestE103.TXT", phoneContainer);
        for (String phone : phoneContainer) {
            doService(phone);
        }

    }

    public List<String> readFileByLines(String fileName, List<String> container) {
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                tempString = tempString.trim();
                if (!StringUtils.isEmpty(tempString)) {
                    container.add(tempString);
                }
                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        log.info("手机号数量为：{}", container.size());
        return container;
    }

    private boolean doService(String phone) {
        boolean ret = false;
        E103Request request = new E103Request();
        //{"actionCode":"B520","encryptCode":"","time":0,"token":"8af40d1761dc13fa0161dc17d1300004","version":560}
        request.setPhone(phone);
        request.setActionCode("E103");
        try {
            E103Response response = serviceRMIHelper.doService(request);
            if (response != null) {
                ret = response.isSuccess();
            }
        } catch (Exception e) {

            log.error("doService -> ", e);
        }
        if (!ret) {
            log.error("phone【{}】=={}", phone, "处理失败，请重新处理");
        }
        return ret;
    }


}
