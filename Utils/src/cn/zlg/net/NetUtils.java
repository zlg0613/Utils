package cn.zlg.net;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class NetUtils {

	public static String getLocalIp(){
		InetAddress addr;
		try {
			addr = InetAddress.getLocalHost();
			String ip=addr.getHostAddress();//获得本机IP
			return ip;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static String getMachineName(){
		InetAddress addr;
		try {
			addr = InetAddress.getLocalHost();
			String hostName=addr.getHostName();//获得本机IP
			return hostName;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getRealIp()  {
		String localip = null;// 本地IP，如果没有配置外网IP则返回它
		String netip = null;// 外网IP

		Enumeration<NetworkInterface> netInterfaces = null;
		try {
			netInterfaces = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		InetAddress ip = null;
		boolean finded = false;// 是否找到外网IP
		while (netInterfaces.hasMoreElements() && !finded) {
			NetworkInterface ni = netInterfaces.nextElement();
			Enumeration<InetAddress> address = ni.getInetAddresses();
			while (address.hasMoreElements()) {
				ip = address.nextElement();
				if (!ip.isSiteLocalAddress() 
						&& !ip.isLoopbackAddress() 
						&& ip.getHostAddress().indexOf(":") == -1) {// 外网IP
					netip = ip.getHostAddress();
					finded = true;
					break;
				} else if (ip.isSiteLocalAddress() 
						&& !ip.isLoopbackAddress() 
						&& ip.getHostAddress().indexOf(":") == -1) {// 内网IP
					localip = ip.getHostAddress();
				}
			}
		}
	
		if (netip != null && !"".equals(netip)) {
			return netip;
		} else {
			return localip;
		}
	}
}
