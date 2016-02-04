package com.paito.biz.auction.util.sms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SmsSender  {
	
	static final Logger logger = LoggerFactory.getLogger(SmsSender.class);
   	private static Integer x_eid=0;
	private static String x_uid="ssyy_pmxt";
	private static String x_pwd_md5="e10adc3949ba59abbe56e057f20f883e";
	private static Integer x_gate_id=300;
	
	public SmsSender(){
		
	}
	
	public static String SendSms(String mobile,String content) throws UnsupportedEncodingException{
		Integer x_ac=10;
		HttpURLConnection httpconn = null;
		String result="-20";
		String memo = content.length()<70?content.trim():content.trim().substring(0, 70);
		StringBuilder sb = new StringBuilder();
		sb.append("http://gateway.woxp.cn:6630/utf8/web_api/?x_eid=");
		sb.append(x_eid);
		sb.append("&x_uid=").append(x_uid);
		sb.append("&x_pwd_md5=").append(x_pwd_md5);
		sb.append("&x_ac=").append(x_ac);
		sb.append("&x_gate_id=").append(x_gate_id);
		sb.append("&x_target_no=").append(mobile);
		sb.append("&x_memo=").append(URLEncoder.encode(memo, "utf-8"));
		try {
			URL url = new URL(sb.toString());
			httpconn = (HttpURLConnection) url.openConnection();
			BufferedReader rd = new BufferedReader(new InputStreamReader(httpconn.getInputStream()));
			result = rd.readLine();
			rd.close();
		} catch (MalformedURLException e) {
			//TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			//TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(httpconn!=null){
				httpconn.disconnect();
				httpconn=null;
			}
		}
		return result;
	}

	public static void sendSMS(String mobileno, String text, String msgid) {
		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod("http://gbk.sms.webchinese.cn");
		post.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=gbk");//在头文件中设置转码
		NameValuePair[] data ={ new NameValuePair("Uid", "wuguang"),new NameValuePair("Key", "b654901f50d087d54802"),
				new NameValuePair("smsMob",mobileno),new NameValuePair("smsText",text)};
		post.setRequestBody(data);

		try {
			client.executeMethod(post);

			Header[] headers = post.getResponseHeaders();
			int statusCode = post.getStatusCode();

			String result = new String(post.getResponseBodyAsString().getBytes("gbk"));

			post.releaseConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}