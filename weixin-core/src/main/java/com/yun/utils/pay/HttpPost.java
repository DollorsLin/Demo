package com.yun.utils.pay;


import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


public class HttpPost {
	public static String http(String url,String proxyUrl,int proxyPort, Map<String, Object> params,String chartSet)
			throws Exception {
		URL u = null;
		HttpURLConnection con = null;
		// 构建请求参数
		StringBuffer  sb = new StringBuffer();
		OutputStreamWriter osw = null;
		BufferedReader br = null;
		if (params != null) {
			for (String key:params.keySet()) {
				sb.append(key).append("=").append(params.get(key)).append("&");
			}
		}
		System.out.println("连接:"+ url);
		System.out.println("发送:"+ sb.toString());
		try {

			u = new URL(url);
			if(null!=proxyUrl && !proxyUrl.equals("")){
				Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyUrl, proxyPort)); 
				con = (HttpURLConnection) u.openConnection(proxy);
			}else {
				con = (HttpURLConnection) u.openConnection();
			}
			con.setConnectTimeout(30000);
			con.setReadTimeout(20000);
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setUseCaches(false);
			con.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			con.setRequestProperty("Charset", "UTF-8");
			osw = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
			osw.write(sb.toString());
			osw.flush();
		} catch (SocketTimeoutException e) {
			throw new Exception();
		} catch (Exception e) {
			throw new Exception();
		}finally {
			try {
				osw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		StringBuffer buffer = new StringBuffer();
		try {
			br = new BufferedReader(new InputStreamReader(con.getInputStream(),
					"UTF-8"));
			String temp;
			while ((temp = br.readLine()) != null) {
				buffer.append(temp);
				buffer.append("\n");
			}
		} catch (SocketTimeoutException e) {
			throw new Exception();
		} catch (FileNotFoundException e) {
			throw new Exception();
		} catch (Exception e) {
			throw new Exception();
		} finally {
			try {
				if (osw != null) {
					osw.close();
				}
				if (br != null) {
					br.close();
				}
				if (con != null) {
					con.disconnect();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return buffer.toString();
	}


	public static String get(String url, String proxyUrl, int proxyPort,
			Map<String, String> params) throws Exception {

		URL u = null;

		HttpURLConnection con = null;

		// 构建请求参数
		StringBuffer sb = new StringBuffer();
		if (params != null) {
			for (Entry<String, String> e : params.entrySet()) {
				sb.append("&");
				sb.append(e.getKey());
				if (e.getValue() != null && !e.getValue().equals("")) {
					sb.append("=");
					sb.append(e.getValue());
				}
			}
			sb.deleteCharAt(0);
			sb.insert(0, "?");
			sb.insert(0, url);
			url = sb.toString();
		}
		System.out.println("发送："+sb.toString());
		// 尝试发送请求
		try {
			u = new URL(sb.toString());
			if (null != proxyUrl && !proxyUrl.equals("")) {
				Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(
						proxyUrl, proxyPort));
				con = (HttpURLConnection) u.openConnection(proxy);
			} else {
				con = (HttpURLConnection) u.openConnection();
			}
			con.setConnectTimeout(5000);
			con.setReadTimeout(14000);
			con.setRequestMethod("GET");
		} finally {
			if (con != null) {
				con.disconnect();
			}
		}
		// 读取返回内容
		StringBuffer buffer = new StringBuffer();
		BufferedReader br = new BufferedReader(new InputStreamReader(
				con.getInputStream(), "UTF-8"));
		String temp;
		while ((temp = br.readLine()) != null) {
			buffer.append(temp);
			buffer.append("\n");
		}
		if (buffer.length() > 1) {
			buffer.deleteCharAt(buffer.length() - 1);
		}
		return buffer.toString();
	}
	public static void main(String[] args) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("context", "{\"mer_id\":\"d2819d37bce94c84a5db6a4bbd7edcb4\",\"totalbatchno\":\"100115\",\"totalcount\":\"2\",\"totalamount\":\"100\",\"mac\":\"11111\",\"detailinfo\":[\"20180402|100105|622202123123123123|test|test|test|test|test|111|test1|00\",\"20180402|100105|622202123123123123|test|test|test|test|test|111|test1|00\"]}");
		try {
			String ret=HttpPost.http("http://144.131.254.54:24111/queryService/dfonline","",8080,map,"");
			System.out.println(ret);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
