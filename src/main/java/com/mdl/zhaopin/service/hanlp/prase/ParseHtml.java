package com.mdl.zhaopin.service.hanlp.prase;


import com.mdl.zhaopin.service.hanlp.factory.ParseFileFactory;
import com.mdl.zhaopin.service.read.ReadFile;
import com.mdl.zhaopin.service.read.impl.ReadHtml;

public class ParseHtml extends ParseFileFactory {

	private String filePath;

	public ParseHtml(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public String readFile() {

		ReadFile readHtml = new ReadHtml();

		String read = readHtml.read(filePath);

		super.separateStr = read;

		return read;

	}

}
