package com.mdl.zhaopin.service.hanlp.prase;


import com.mdl.zhaopin.service.hanlp.factory.ParseFileFactory;
import com.mdl.zhaopin.service.read.ReadFile;
import com.mdl.zhaopin.service.read.impl.ReadTxt;
import com.mdl.zhaopin.utils.RegexUtils;

public class ParseTxt extends ParseFileFactory {

	private String filePath = null;

	public ParseTxt(String filePath) {
		this.filePath = filePath;
	}

	public String readFile() {

		ReadFile readTxt = new ReadTxt();

		String str = readTxt.read(this.filePath);

//		List<String> list = SentencesUtil.toSentenceList(str);
//		for (String string : list) {
//			System.out.println("----------------->" + string);
//		}

//		System.out.println(str + "\n");

		String res = RegexUtils.replaceBlank(str);

//		System.out.println(res + "\n");

		super.separateStr = res;

		return separateStr;
	}

}
