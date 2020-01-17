package com.mdl.zhaopin.common.constant;

/**
 * @author xiekun
 * @ClassName: Constant
 * @Description: TODO global constant variables
 * @date 2017年11月1日 上午9:47:45
 */
public final class Constant {

    public static String[] sex = {"男", "女", "women", "men"};

    public static String[] COLLEGE_DEGREE_ARR = {"专科", "高职高专", "大专"};
    public static String[] BACHELOR_DEGREE_ARR = {"本科", "大学本科", "普通本科"};
    public static String[] GRADUATE_STUDENT_ARR = {"研究生", "硕士", "专业硕士", "学位硕士"};
    public static String[] DOCTORAL_DEGREE_ARR = {"博士", "PHD"};
    public static String[] POSTDOCTORAL_DEGREE_ARR = {"博士后", "Doctoral"};

    public static String COLLEGE_DEGREE = "专科";
    public static String BACHELOR_DEGREE = "本科";
    public static String GRADUATE_STUDENT = "硕士";
    public static String DOCTORAL_DEGREE = "博士";
    public static String POSTDOCTORAL_DEGREE = "博士后";

    public static final String ROOT = System.getProperty("user.dir");

    public static final String RAW_FILE_DIR = "rawfile";


    /**
     * 腾讯地图地址解析api
     */
    public static final String TENCENT_MAP_API = "http://apis.map.qq.com/ws/geocoder/v1/?address=";

    /**
     * 腾讯地图地址解析个人试用key
     */
    public static final String TENCENT_MAP_KEY = "HQ6BZ-YBZHV-ICSPH-UNMO2-QBZ6J-J3F4H";

}
