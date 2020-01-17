概要：

针对求职者自己的简历进行解析：

1、简历解析已经基本情况如下：
```
已解析出来的：姓名、性别、年龄、家庭住址、手机号、邮箱、毕业院校、学历、专业、工年

可解析但还未解析的：QQ号、当前职位

未解析（难点）出来的：工作经验、期望薪资、项目经验、个人兴趣爱好、
```

2、简历解析的基本思路：

   2.1、分词器（HanLP）：姓名，毕业院校、性别、学历、专业、家庭住址，

```其中家庭住址是先通过分词器解析出一个大概，然后再调用腾讯的地址解析API根据关键字模糊匹配地址再返回得到精确的地址（准确率可达到85%）；```
   
   2.2、正则表达式：年龄、手机号、邮箱、工年，通过正则从字符串中提取相关信息；

   ```总结：除了工年是通过排序计算得出之外，其他基本信息都是按照简历字符串中首次出现相关信息就作为求职者的基本信息来判定，不能完全确定就是求职者的信息，比如出现两个邮箱，只能默认第一次出现的邮箱就是求职者的邮箱。```
   
注意点：

```
1、工程中有使用到elasticsearch，如果不需要使用es，可将代码中写数据进入es的部分注释掉；

   2、如果有需要自定义词典

    2.1、需要从官方下载词典库http://hanlp.linrunsoft.com/services.html 下载data.zip ，将词典库放在本地其他路径或者放在项目中的resources下；

    2.2、然后下载官方的hanlp.properties放在项目的classpath的任何目录下都可以，hanlp会自动获取hanlp.properties位置，将文件中的root路径更改为刚才下载解压的data.zip的路径，如root=C:/parse_word_dic/HanLP/data-for-1.3.3或者root=hanlp_data_dic/data-for-1.3.3。

```
   
官方地址：

http://hanlp.linrunsoft.com/index.html

https://github.com/hankcs/HanLP

详细配置可以参考： http://blog.csdn.net/a_step_further/article/details/50333961
