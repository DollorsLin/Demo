package com.yun.utils.pay;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/***
 * @author 李启明
 * @Description: TODO(标准版xml格式的异步回调) 
 * 声明：以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己需要，按照技术文档编写。
 * 		该代码仅供参考，不提供编码，性能，规范性等方面的保障
 */
public class XmlNotifyServlet  extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	

	protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String context=request.getParameter("context");
		if(context==null || "".equals(context)){
			response.getWriter().write("An empty message was received.");
			System.out.println("收到了空的报文");
			return ;//有可能存在通知报文没有参数的情况，例如验证商户系统是否正常
		}
		System.out.println("收到大华的异步通知内容是："+context);
		Map<String,Object> map=XmlUtils.readXml(context);
		String reponseStr="";
		String response_code="00";//应答嘛，默认是成功的
		if(map.get("error")==null){
			System.out.println("*******************************收到大华通知的参数开始*******************************");
			for(String key:map.keySet()){
				System.out.println(key+":"+map.get(key));
			}
			System.out.println("*******************************收到大华通知的参数结束*******************************");
			//解析xml未出现问题，根据交易类型和参数，进行相应的业务处理
			System.out.println("*******************************响应大华通知的参数开始*******************************");
			map.put("response_code", response_code);
			map.put("response_msg", "交易成功！");
			String transtype=(String) map.get("transtype");
			if("P033".equals(transtype)){
				try {
					//支付通知，商户自行处理,所需要的参数在map中取，参数key在repeustP003BodyNodes里面
					//此处更新数据库的动作省略....
					//组装响应的报文
					reponseStr=XmlUtils.assembleXmlDataNoBody(map,XmlUtils.responseP033BodyNodes);
				} catch (Exception e) {
					//如果操作数据库异常
					map.put("response_code", "01");
					map.put("response_msg", "系统内部异常:"+e.getMessage());
					reponseStr=XmlUtils.assembleXmlDataNoBody(map,XmlUtils.responseErrorBodyNodes);
				}
			}else if("P036".equals(transtype)){//撤销交易通知
				try {
					//退款通知
					//此处更新数据库的动作省略....
					//组织响应的报文
					reponseStr=XmlUtils.assembleXmlDataNoBody(map,XmlUtils.responseP036BodyNodes);
				} catch (Exception e) {
					//如果操作数据库异常
					map.put("response_code", "01");
					map.put("response_msg", "系统内部异常:"+e.getMessage());
					reponseStr=XmlUtils.assembleXmlDataNoBody(map,XmlUtils.responseErrorBodyNodes);
				}
			}else{
				////更多交易类型省略
			}
		}else{
			map.put("response_code", "01");
			map.put("response_msg", map.get("error"));
			reponseStr=XmlUtils.assembleXmlDataNoBody(map,XmlUtils.responseErrorBodyNodes);
		}
		System.out.println("响应给大华的报文是："+reponseStr);
		response.getWriter().write(reponseStr);
		response.getWriter().close();
	}
}
