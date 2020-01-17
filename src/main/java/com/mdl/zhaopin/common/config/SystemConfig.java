package com.mdl.zhaopin.common.config;

/**
 * @Project : elearningweb
 * @Package Name : com.sunlands.framework.config
 * @Description : TODO
 * @Author : xiekun
 * @Create Date : 2018年07月25日 上午10:23
 * @ModificationHistory Who   When     What
 * ------------    --------------    ---------------------------------
 */

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

public class SystemConfig {

    private static final Logger logger = LoggerFactory.getLogger(SystemConfig.class);
    /**
     * 系统唯一标识，用于标记应用节点的唯一性，用于区分相同应用的不同节点、互斥执行
     */
    public static final String id = UUID.randomUUID().toString();

    private static Properties pp = new Properties();
    private static final String DEFAULT_CONFIG_FILE = "configs.properties";

    static {
        InputStream is = null;

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("加载全局配置文件:%s", DEFAULT_CONFIG_FILE));
        }

        try {
            is = SystemConfig.class.getClassLoader().getResourceAsStream(DEFAULT_CONFIG_FILE);
        } catch (Exception e) {
            logger.error("加载全局配置文件失败！", e);
        }
        try {
            if (is == null) {
                is = SystemConfig.class.getResourceAsStream(DEFAULT_CONFIG_FILE);
            }
        } catch (Exception e) {
            logger.error("加载全局配置文件失败！", e);
        }
        if (is == null) {
            logger.error("加载配置文件失败,类路径中没有找到配置文件:" + DEFAULT_CONFIG_FILE);
        } else {
            try {
                pp.load(is);
            } catch (IOException e) {
                logger.error("加载配置文件失败:" + DEFAULT_CONFIG_FILE, e);
            }
        }

    }


    /**
     * 描述：取得指定属性的值
     * 时间：2010-1-12
     * 作者：谭畅
     * 参数：
     *
     * @param name 参数名称
     *             返回值:
     * @return 返回对应属性的值
     * 抛出异常：
     */
    public static String getProperty(String name) {
        return getProperty(name, null);
    }

    /**
     * 获取map集合属性，格式如下：
     * cache.redis.expires=\
     * account:30;\
     * tenant:30
     *
     * @param name 属性名称
     * @return
     */
    public static Map<String, String> getMapProperty(String name) {
        Map<String, String> resultMap = new HashMap<>();
        String value = getProperty(name);
        if (StringUtils.isNotEmpty(value)) {
            String[] items = value.split(";");
            for (String item : items) {
                int idx = item.indexOf(":");
                if (idx > 0) {
                    resultMap.put(item.substring(0, idx), item.substring(idx + 1));
                }
            }
        }
        return resultMap;
    }

    /**
     * 描述：取得指定属性的值,如果没有配置该值，则给出一个默认的配置值
     * 时间：2010-1-12
     * 作者：谭畅
     * 参数：
     *
     * @param name 参数名称
     *             返回值:
     * @return 返回对应属性的值
     * 抛出异常：
     */
    public static String getProperty(String name, String defaultValue) {
        String value = null;
        if (pp != null) {
            value = pp.getProperty(name);
        }
        if (value != null && !"".equals(value)) {
            value = value.trim();
        } else {
            value = defaultValue;
        }
        if (logger.isDebugEnabled()) {
            logger.debug("get system config {}:{}", name, value);
        }
        return value;
    }


    /**
     * 获取版本时间戳
     * 版本时间戳主要用于脚本文件或者css样式等静态文件添加后缀，如果在不改变的情况下，
     * 时间戳也不发生变化，此时浏览器会加载浏览器缓存的静态文件
     * 如果js文件发生变化，则需要强制浏览器从服务器端获取最新版本的js文件
     *
     * @return
     */
    public static String getVersionStamp() {
        if (isDevelopModel()) {
            return System.currentTimeMillis() + "";
        } else {
            return SystemConfig.getProperty("global.versionStamp", System.currentTimeMillis() + "");
        }
    }

    /**
     * 是否开发模式
     *
     * @return
     */
    public static boolean isDevelopModel() {
        return getProperty("global.developModel", "false").equals("true");
    }

}
