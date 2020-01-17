package com.mdl.zhaopin.service.hanlp;

import com.hankcs.hanlp.HanLP;

/**
 * @Project : reusme-parse
 * @Package Name : com.mdl.zhaopin.service.hanlp
 * @Description : TODO
 * @Author : xiekun
 * @Create Date : 2020年01月17日 14:57
 * @ModificationHistory Who   When     What
 * ------------    --------------    ---------------------------------
 */
public class Test {

    public static void main(String[] args) {
        System.out.println(HanLP.segment("你好，欢迎使用HanLP汉语处理包！"));
    }


}
