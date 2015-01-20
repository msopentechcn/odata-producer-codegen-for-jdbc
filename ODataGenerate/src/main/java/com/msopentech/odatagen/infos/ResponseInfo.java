package com.msopentech.odatagen.infos;
/**
 * 2015-1-7
 * @author Bruce Li
 */
public class ResponseInfo {
	private String pathUrl;
	private String responseState;
	private String responseMessage;
	
	public String getPathUrl() {
		return pathUrl;
	}
	public ResponseInfo setPathUrl(String pathUrl) {
		this.pathUrl = pathUrl;
		return this;
	}
	public String getResponseState() {
		return responseState;
	}
	public ResponseInfo setResponseState(String responseState) {
		this.responseState = responseState;
		return this;
	}
	public String getResponseMessage() {
		return responseMessage;
	}
	public ResponseInfo setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
		return this;
	}
	
}
