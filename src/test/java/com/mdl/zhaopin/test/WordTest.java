package com.mdl.zhaopin.test;


import com.hankcs.hanlp.corpus.document.sentence.Sentence;
import com.hankcs.hanlp.corpus.document.sentence.word.IWord;
import com.hankcs.hanlp.tokenizer.NLPTokenizer;

import java.util.List;

public class WordTest {

    public static void main(String[] args) {

        String str = "我的名字叫谢天，今年18岁，性别男，手机号是13397158888，邮箱是86456789@qq.com,备用邮箱是xie_xx@163.com，在武汉市洪山区关山大道软件园上班，公司名字叫尚德科技有限公司";

        Sentence analyze = NLPTokenizer.analyze(str);
        List<IWord> list = analyze.wordList;

        System.out.println(analyze.toString());

        list.stream().forEach(t -> {
            String lable = t.getLabel();
            String value = t.getValue();
            System.out.println(lable + "---" + value);
        });

    }

}
