package com.mdl.zhaopin.service.otherplat;

import com.mdl.zhaopin.utils.TxtUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PraseHtml {

    public static void main(String[] args) {
        String str = TxtUtils.read("C:\\Users\\xiekun\\Desktop\\file\\html\\任晨光.html");
        System.out.println(praseHtml(str));
    }


    public static String praseHtml(String html) {

        HashMap<String, Object> resultMap = new HashMap<>();

        //解析HTML字符串返回一个Document实现
        Document doc = Jsoup.parse(html);

        //求职的工作岗位
        String JobTitle = doc.getElementById("JobTitle").text();
        //期望的工作地点
        String workLocaltion = doc.getElementById("work_localtion").text();
        //求职者名字
        String userName = doc.getElementById("userName").text();
        //图像
        String headerImg = doc.getElementsByClass("headerImg").attr("src");

        resultMap.put("JobTitle", JobTitle);
        resultMap.put("workLocaltion", workLocaltion);
        resultMap.put("userName", userName);
        resultMap.put("headerImg", headerImg);

        String str_1 = doc.getElementsByClass("summary-top").select("span").text();
        String[] arr = str_1.split("    ");
        //性别
        String sex = arr[0];
        //年龄
        String age = arr[1];
        //工作年限
        String workLength = arr[2];
        //学历
        String degree = arr[3];
        //是否结婚
        String isMarry = arr[4];

        resultMap.put("sex", sex);
        resultMap.put("age", age);
        resultMap.put("workLength", workLength);
        resultMap.put("degree", degree);
        resultMap.put("isMarry", isMarry);

        String summaryTop = doc.getElementsByClass("summary-top").html();

        String reg = "<br[^>]*>((?:(?!<br[^>]*>)[\\s\\S])*)";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(summaryTop);
        String[] topArr = null;
        while (m.find()) {
            topArr = m.group(1).split("：");
        }

        //现居住地
        String address = topArr[1].replace(" ", "").split("\\|")[0];
        //政治面貌
        String politicalStatus = topArr[1].trim().split("\\|")[1];

        resultMap.put("address", address);
        resultMap.put("politicalStatus", politicalStatus);

        Elements element_1 = doc.getElementById("feedbackD").select("div").select("span");

        //手机号
        String phone = element_1.first().select("b").first().text();
        //邮箱
        String email = element_1.select("i").text();

        resultMap.put("phone", phone);
        resultMap.put("email", email);


        Elements table0_trs = doc.select("table").get(0).select("tr");
        //期望工作地区
        String expectAddress = table0_trs.get(0) == null ? "" : table0_trs.get(0).select("td").get(1).text();
        //期望月薪
        String expectSalary = table0_trs.get(1) == null ? "" : table0_trs.get(1).select("td").get(1).text();
        //目前状况
        String currentState = table0_trs.get(2) == null ? "" : table0_trs.get(2).select("td").get(1).text();
        //期望工作性质
        String expectJobProperty = table0_trs.get(3) == null ? "" : table0_trs.get(3).select("td").get(1).text();
        //期望从事职业
        String expectJob = table0_trs.get(4) == null ? "" : table0_trs.get(4).select("td").get(1).text();
        //期望从事行业
        String expectIndustry = table0_trs.get(5) == null ? "" : table0_trs.get(5).select("td").get(1).text();
        //自我评价
        String selfEvaluation = doc.getElementsByClass("rd-break").text();

        resultMap.put("expectAddress", expectAddress);
        resultMap.put("expectSalary", expectSalary);
        resultMap.put("currentState", currentState);
        resultMap.put("expectJobProperty", expectJobProperty);
        resultMap.put("expectJob", expectJob);
        resultMap.put("expectIndustry", expectIndustry);
        resultMap.put("selfEvaluation", selfEvaluation);

        //工作经验
        Elements workExperiences = doc.getElementsByClass("workExperience");
        Map<String, Object> workExpMap = null;
        List<String> jobExpList = null;
        List<Map<String, Object>> workExpList = new ArrayList<>();
        Elements we_h2 = workExperiences.select("h2");
        for (int i = 0; i < we_h2.size(); i++) {
            workExpMap = new HashMap<>();
            String[] workExpArr = we_h2.get(i).text().split("  ");
            String year = workExpArr[0] == null ? "" : workExpArr[0];
            String company = workExpArr[1] == null ? "" : workExpArr[1];
            String length = workExpArr[2] == null ? "" : workExpArr[2];
            workExpMap.put("year", year);
            workExpMap.put("company", company);
            workExpMap.put("length", length);

            Elements we_h5 = workExperiences.select("h5");
            String position = we_h5.get(i).text().replace(" ", "").split("\\|")[0];
            String salary = we_h5.get(i).text().replace(" ", "").split("\\|")[1];

            workExpMap.put("position", position);
            workExpMap.put("salary", salary);

            Elements divs = workExperiences.select("div.resume-preview-dl");
            String gangwei = divs.get(2 * i).text().replace(" ", "").split("\\|")[0];
            String guimo = divs.get(2 * i).text().replace(" ", "").split("\\|")[1].split("：")[1];

            workExpMap.put("gangwei", gangwei);
            workExpMap.put("guimo", guimo);

            jobExpList = new ArrayList<>();
            Elements tables_tr = workExperiences.select("table").get(i).select("tr").select("td");
            for (int td = 0; td < tables_tr.size(); td++) {
                if (td != 0) {
                    jobExpList.add(tables_tr.get(td).text());
                }
            }

            workExpMap.put("desc", jobExpList);
            workExpList.add(workExpMap);
        }

        resultMap.put("workExpList", workExpList);

        //项目经验
        Elements projectExp = doc.select("#resumeContentBody").select("div:nth-child(6)");
        Elements project_h2 = projectExp.select("h2");


        Map<String, String> projectMap;
        List<Map<String, String>> projectList = new ArrayList<>();
        ;
        for (int pj = 0; pj < project_h2.size(); pj++) {
            projectMap = new HashMap<>();

            String[] projectTitle = project_h2.get(pj).text().split("  ");
            String projectDate = projectTitle[0];
            String projectName = projectTitle[1];
            projectMap.put("projectDate", projectDate);
            projectMap.put("projectName", projectName);

            Elements projectDetail = projectExp.select("table").get(pj).select("tr");
            String developEnvironment = projectDetail.get(0).select("td").get(1).text();
            String developTool = projectDetail.get(1).select("td").get(1).text();
            String dutyDesc = projectDetail.get(2).select("td").get(1).text();
            String developDesc = projectDetail.get(3).select("td").get(1).text();

            projectMap.put("developEnvironment", developEnvironment);
            projectMap.put("developTool", developTool);
            projectMap.put("dutyDesc", dutyDesc);
            projectMap.put("developDesc", developDesc);

            projectList.add(projectMap);
        }

        resultMap.put("projectList", projectList);


        List<HashMap<String, String>> educationList = new ArrayList<>();
        HashMap<String, String> educationMap = null;
        Elements educationStr = doc.select("#resumeContentBody").select("div.educationContent");
        for (int edu = 0; edu < educationStr.size(); edu++) {
            educationMap = new HashMap<>();
            String[] education = educationStr.get(edu).text().split("  ");
            String educationTime = education[0];
            String university = education[1];
            String major = education[2];
            String eduDegree = education[3];

            educationMap.put("educationTime", educationTime);
            educationMap.put("university", university);
            educationMap.put("major", major);
            educationMap.put("eduDegree", eduDegree);

            educationList.add(educationMap);
        }

        resultMap.put("educationList", educationList);


        List<HashMap<String, String>> certificateList = new ArrayList<>();
        HashMap<String, String> certificateMap = null;
        Elements certificate = doc.select("#resumeContentBody").select("div:nth-child(8)").select("h2");
        if (certificate != null) {
            for (int ctf = 0; ctf < certificate.size(); ctf++) {
                certificateMap = new HashMap<>();
                String[] certificateStr = certificate.get(ctf).text().split("  ");
                String time = certificateStr[0];
                String certificateName = certificateStr[1];
                certificateMap.put("time", time);
                certificateMap.put("certificateName", certificateName);
                certificateList.add(certificateMap);
            }
        }

        resultMap.put("certificateList", certificateList);

        List<String> languageList = new ArrayList<>();
        Elements language = doc.getElementsByClass("resume-preview-line-height");
        if (language != null) {
            for (int lan = 0; lan < language.size(); lan++) {
                String languageStr = language.get(lan).text();
                languageList.add(languageStr);
            }
        }

        resultMap.put("languageList", languageList);
        return null;
    }


    private static int table(Document doc) {
        Elements trs = doc.select("table").select("tr");
        int i;
        for (i = 0; i < trs.size(); i++) {
            Elements tds = trs.get(i).select("td");
            for (int j = 0; j < tds.size(); j++) {
                String TxtUtils = tds.get(j).text();
                System.out.print(TxtUtils + " ");
            }
            System.out.println("");
        }
        return i;
    }

    public void test() throws IOException {
        Document doc0 = Jsoup.connect("http://www.jb51.net/").get();
        String title = doc0.title();
        System.out.println(title);

        String html1 = "<html><head><title>First parse</title></head> <body><p>Parsed HTML into a doc.</p></body></html>";
        Document doc1 = Jsoup.parse(html1);
        System.out.println(doc1.getAllElements());

        String html = "<p>An <a href='http://www.jb51.net/'><b>www.jb51.net123</b></a> link.</p>";
        //解析HTML字符串返回一个Document实现
        Document doc = Jsoup.parse(html);
        //查找第一个a元素
        Element link = doc.select("a").first();
        // "An www.jb51.net link"//取得字符串中的文本
        String text = doc.body().text();
        //"http://www.jb51.net/"//取得链接地址
        String linkHref = link.attr("href");
        // "www.jb51.net""//取得链接地址中的文本
        String linkText = link.text();
        // "<a href="http://www.jb51.net"><b>www.jb51.net</b></a>"
        String linkOuterH = link.outerHtml();
        // "<b>www.jb51.net</b>"//取得链接内的html内容
        String linkInnerH = link.html();

        System.out.println(text + "\n" + linkHref + "\n" + linkOuterH + "\n" + "\n" + linkText + "\n" + linkInnerH);
    }

}
