package com.mdl.zhaopin.service.hanlp.factory;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.tag.Nature;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import com.mdl.zhaopin.common.constant.Constant;
import com.mdl.zhaopin.utils.HttpClient;
import com.mdl.zhaopin.utils.MatcherAssist;
import com.mdl.zhaopin.utils.RegexUtils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public abstract class ParseFileFactory {

    public List<Term> termList;

    public String separateStr;

    public String readFile() {
        return separateStr;
    }

    public List<Term> separateWords() {

        // HanLP.Config.ShowTermNature = false; // 关闭词性显示

        // 标准分词器
        Segment segment = HanLP.newSegment()
                // 开启词性标注
                .enablePartOfSpeechTagging(true)
                // 开启人名识别
                .enableNameRecognize(true)
                // 开启地名识别
                .enablePlaceRecognize(true)
                // 开启机构分析功能
                .enableOrganizationRecognize(true)
                // 开启用户词典
                .enableCustomDictionary(false)
                // 不开启音译人名识别
                .enableTranslatedNameRecognize(false)
                // 开启偏移量计算
                .enableOffset(true);

        termList = segment.seg(this.separateStr);

//		System.out.println("\n标准分词器A：--->" + termList);

        return termList;
    }

    /**
     * 名字
     *
     * @return
     */
    public String getName() {
        String name = null;
        for (Term term : termList) {
            Nature pcNature = Nature.fromString("nr");
            if (term.nature.equals(pcNature)) {
                name = term.word;
                break;
            }
        }
        return name;
    }

    /**
     * 年龄
     *
     * @return
     */
    public int getAge() {
        int age = 0;
        for (Term term : termList) {
            Nature pcNature = Nature.fromString("m");
            if (term.nature.equals(pcNature)) {
                if (RegexUtils.isInteger(term.word) && term.word.length() <= 2) {
                    int num = Integer.valueOf(term.word);
                    if (num > 9 && num < 100) {
                        age = num;
                        break;
                    }
                }

            }
        }
        return age;
    }

    /**
     * 性别
     *
     * @return
     */
    public String getSex() {
        String sex = null;
        for (Term term : termList) {
            String termStr = term.toString();
            for (String sexStr : Constant.sex) {
                if (termStr.contains(sexStr)) {
                    sex = sexStr;
                    break;
                }
            }
        }
        return "70001";
    }

    /**
     * 大学
     *
     * @return
     */
    public String getUniversity() {
        String university = null;
        for (Term term : termList) {
            Nature pcNature = Nature.fromString("ntu");
            if (term.nature.equals(pcNature)) {
                university = term.word;
                break;
            }
        }
        return university;
    }

    /**
     * 职业
     *
     * @return
     */
    public String getProfession() {
        String profession = null;
        for (Term term : termList) {
            Nature pcNature = Nature.fromString("nnd");
            if (term.nature.equals(pcNature)) {
                profession = term.word;
                break;
            }
        }
        return profession;
    }

    /**
     * 专业
     *
     * @return
     */
    public String getSpecialized() {
        String specialized = null;
        for (Term term : termList) {
            Nature pcNature = Nature.fromString("nzzy");
            if (term.nature.equals(pcNature)) {
                specialized = term.word;
                break;
            }
        }
        return specialized;
    }

    /**
     * 最高学历
     *
     * @return
     */
    public String getDegree() {
        String degree = null;
        List<String> degreeList = new ArrayList<>();
        for (Term term : termList) {
            Nature pcNature = Nature.fromString("xl");
            if (term.nature.equals(pcNature)) {
                degreeList.add(term.word);
            }
        }

        // 直接在treeMap里面定义好排序
        TreeMap<String, String> tm = new TreeMap<String, String>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o2.compareTo(o1);
            }
        });

        // 大专
        for (String d : Constant.COLLEGE_DEGREE_ARR) {
            int count = Collections.frequency(degreeList, d);
            if (count != 0) {
                tm.put("1", Constant.COLLEGE_DEGREE);
            }
        }
        // 本科
        for (String d : Constant.BACHELOR_DEGREE_ARR) {
            int count = Collections.frequency(degreeList, d);
            if (count != 0) {
                tm.put("2", Constant.BACHELOR_DEGREE);
            }
        }
        // 硕士
        for (String d : Constant.GRADUATE_STUDENT_ARR) {
            int count = Collections.frequency(degreeList, d);
            if (count != 0) {
                tm.put("3", Constant.GRADUATE_STUDENT);
            }
        }
        // 博士
        for (String d : Constant.DOCTORAL_DEGREE_ARR) {
            int count = Collections.frequency(degreeList, d);
            if (count != 0) {
                tm.put("4", Constant.DOCTORAL_DEGREE);
            }
        }
        // 博士后
        for (String d : Constant.POSTDOCTORAL_DEGREE_ARR) {
            int count = Collections.frequency(degreeList, d);
            tm.put(count + "", Constant.POSTDOCTORAL_DEGREE);
        }

//		return tm.entrySet().iterator().next().getValue();
        return "50005";
    }

    /**
     * 家庭住址
     * <p>
     * 分析：一旦出现省市字眼时，往后取5个分词，可模糊判断出家庭住址的字眼 再通过调取腾讯地址逆向解析得到对应的地址
     *
     * @return
     * @throws IOException
     */
    public String getAddress() throws IOException {

        String addressStr = "";
        boolean addressFlag = false;
        int n = 0;
        for (int i = 0; i < termList.size(); i++) {
            Nature pcNature = Nature.fromString("ns");
            Term termStr = termList.get(i);
            if (!addressFlag) {
                if (termStr.nature.equals(pcNature)) {
                    addressFlag = true;
                    n = i;
                }
            }

            if (addressFlag && i <= n + 5) {
                addressStr = addressStr.concat(termStr.word);
            }
        }

        String url = Constant.TENCENT_MAP_API + addressStr + "&key=" + Constant.TENCENT_MAP_KEY;

        // System.out.println(url);

        String address = "";
        String json = HttpClient.doGet(url);
        JsonParser parser = new JsonParser();
        JsonObject jsonObj = parser.parse(json).getAsJsonObject();

        int status = jsonObj.get("status").getAsInt();
        if (status == 0) {
            JsonObject result = jsonObj.get("result").getAsJsonObject();
            String title = result.get("title").getAsString();
            JsonObject address_components = result.get("address_components").getAsJsonObject();
            String province = address_components.get("province").getAsString();
            String city = address_components.get("city").getAsString();
            String district = address_components.get("district").getAsString();
            String street = address_components.get("street").getAsString();
            String street_number = address_components.get("street_number").getAsString();
            address = province.concat(city).concat(district).concat(street).concat(street_number).concat(title);
        }
        return address;
    }

    /**
     * 电子邮件
     *
     * @return
     */
    public String getEmail() {
        String email = null;
        Pattern pattern = Pattern.compile("\\w+@\\w+\\.(com\\.cn)|\\w+@\\w+\\.(com|cn)");
        Matcher matcher = pattern.matcher(separateStr);
        while (matcher.find()) {
            email = matcher.group();
            break;
        }
        return email;
    }

    // /**
    // * 薪资
    // * @return
    // */
    // public String getSalary() {
    // String email = null;
    // Pattern pattern = Pattern.compile("[^0-9]");
    // Matcher matcher = pattern.matcher(separateStr);
    // while (matcher.find()) {
    // email = matcher.group();
    // break;
    // }
    // return email;
    // }

    /**
     * 手机
     *
     * @return
     */
    public String getPhone() {
        String phone = null;
        Pattern pattern = Pattern.compile("(?<!\\d)(?:(?:1[358]\\d{9})|(?:861[358]\\d{9}))(?!\\d)");
        Matcher matcher = pattern.matcher(separateStr);
        while (matcher.find()) {
            phone = new String(matcher.group());
            break;
        }
        return phone;
    }

    /**
     * 关键字
     *
     * @return
     */
    public List<String> getKeyword() {
        List<String> keywordList = HanLP.extractPhrase(separateStr, 5);
        return keywordList;
    }

    /**
     * 获取日期
     *
     * @param splitStr
     * @return
     */
    public List<String> getDate(String splitStr) {
        return getDateList(separateStr, splitStr);
    }

    /**
     * 获取简历中所有的数据值
     *
     * @return
     */
    public List<String> getNumList() {
        List<String> numList = new ArrayList<>();
        for (Term term : termList) {
            Nature pcNature = Nature.fromString("xl");
            if (term.nature.equals(pcNature)) {
                numList.add(term.word);
            }
        }
        return numList;
    }

    /**
     * 获取所有跟日期相关的数据，并降序排列
     *
     * @return
     */
    public List<String> getDateList(String content, String splitStr) {
        HashSet<String> set = new HashSet<String>();
        String[] split = {".", "-", "#", " "};
        for (String sp : split) {
            List<String> dates = MatcherAssist.getDatesYM(separateStr, sp);
            for (String d : dates) {
                String res = d.replace(sp, "");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
                try {
                    Date date = sdf.parse(res);
                    set.add(sdf.format(date));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        List<String> result = new ArrayList<>(set);
        Collections.sort(result);
        return result;
    }

    /**
     * 获取工作时长
     *
     * @return
     */
    public String getWorkLength() {
        String length = "";
        List<String> list = getDate("");// 获取简历中所有跟日期相关的date
        if (list.size() >= 2) {
            SimpleDateFormat df = new SimpleDateFormat("yyyyMM");// 设置日期格式
            String currDate = df.format(new Date());// new Date()为获取当前系统时间
            String sd = getWorkStartDate();
            if (!sd.isEmpty()) {
                int startDate = sd.isEmpty() ? 0 : Integer.valueOf(sd);
                int s = (Integer.valueOf(currDate) - startDate) / 100;

                if (s > 0 && s <= 3) {
                    length = "1-3年";
                } else if (s > 3 && s <= 5) {
                    length = "3-5年";
                } else if (s > 5 && s <= 10) {
                    length = "5-10年";
                } else if (s > 10) {
                    length = "10年以上";
                }
            }
        }
        return length;
    }

    /**
     * 获取工作的开始时间
     *
     * @return
     */
    public String getWorkStartDate() {
        int n = 0;
        String dateStr = "";
        String startDate = "";
        boolean flag = false;
        // List<String> dList = getDate("");//获取简历中所有跟日期相关的date
        List<String> dList = new ArrayList<>();
        List<String> numList = getNumList();

        for (String string : numList) {
            // System.out.println("相关时间--->" + getDateList(string, ""));
            dList.addAll(getDateList(string, ""));
        }

        List<String> dateRes = new ArrayList<>();
        for (int i = 0; i < termList.size(); i++) {
            Nature pcNature = Nature.fromString("xl");// 判断学历
            Term termStr = termList.get(i);
            if (!flag) {
                if (termStr.nature.equals(pcNature)) {
                    flag = true;
                    n = i;
                }
            }

            if (flag && i <= n + 5) {// 如果出现了学历字眼，则往后面查询最多5个分析，取出中间的日期
                dateStr = dateStr.concat(termStr.word);
                List<String> datelist = getDateList(dateStr, "");
                Collections.sort(datelist);
                dateRes.add(datelist.get(0));
            }
        }
        if (!dateRes.isEmpty()) {
            Collections.sort(dateRes);// 将出现的所有的学历字眼相关的日期排序，取出最大的时间，基本可以断定为最高学历毕业时间，即为工作开始时间
            startDate = dateRes.get(dateRes.size() - 1);
        } else if (!dList.isEmpty()) {
            int a = Integer.valueOf(dList.get(0));
            int b = Integer.valueOf(dList.get(dList.size() - 1));
            if (b >= a + 18) {
                startDate = dList.get(dList.size() - 1);
            }
        }
        return startDate;
    }

}
