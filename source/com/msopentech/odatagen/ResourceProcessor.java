package com.msopentech.odatagen;
import java.io.IOException;
import java.io.InputStream;
/**
 * 2015-1-14
 * @author Bruce Li
 */
public class ResourceProcessor {
	private String CODER = "utf-8";
	public  StringBuilder getResourceAsStringBuilder(String resource) throws IOException{
		StringBuilder stringBuilder = new StringBuilder();
		InputStream input = ResponseProcessor.class.getResourceAsStream(resource);
		int end;
		byte[] b = new byte[1024];
		while ((end = input.read(b)) > 0) {
			stringBuilder.append(new String(b,0,end,CODER));
		}
		return stringBuilder;
	}
}
