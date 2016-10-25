package com.xinding.travel.security;

import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.xinding.travel.util.DesUtil;


/**
 * ClassName: EncryptablePropertyPlaceholderConfigurer <br/>
 * Date: 2016-2-23 下午2:39:12 <br/>
 * Description: 数据库配置文件解密 <br/>
 * 
 * @author wangds
 * @version
 * @see
 */
public class EncryptablePropertyPlaceholderConfigurer extends
        PropertyPlaceholderConfigurer {
    private static final String key = "0006000700040005";

    protected void processProperties(
            ConfigurableListableBeanFactory beanFactory, Properties props)
            throws BeansException {
        try {
            DesUtil des = new DesUtil();
            String username = props.getProperty("username");
            if (username != null) {
                props.setProperty("username",
                        des.Decrypt(username, des.hex2byte(key)));
            }

            String password = props.getProperty("password");
            if (password != null) {
                props.setProperty("password",
                        des.Decrypt(password, des.hex2byte(key)));
            }
            super.processProperties(beanFactory, props);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BeanInitializationException(e.getMessage());
        }
    }
}
