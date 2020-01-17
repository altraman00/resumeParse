package com.mdl.zhaopin.service.hanlp.prase;

import com.mdl.zhaopin.service.hanlp.factory.ParseFileFactory;
import com.mdl.zhaopin.service.read.ReadFile;
import com.mdl.zhaopin.service.read.impl.ReadPDF;

public class ParsePdf extends ParseFileFactory {

	private String filePath;

	public ParsePdf(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public String readFile() {

		ReadFile readPDF = new ReadPDF();

		String read = readPDF.read(filePath);

		super.separateStr = read;

		return read;
	}

}
