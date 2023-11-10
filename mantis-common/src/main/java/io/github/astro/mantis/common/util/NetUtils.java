package io.github.astro.mantis.common.util;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

public interface NetUtils {

    static int ipToInt(String ipAddress) {
        String[] ipArr = ipAddress.split("\\.");
        byte[] ipByteArr = new byte[4];
        for (int i = 0; i < 4; i++) {
            ipByteArr[i] = (byte) (Integer.parseInt(ipArr[i]) & 0xff);
        }
        int ipInt = 0;
        for (byte b : ipByteArr) {
            ipInt <<= 8;
            ipInt |= b & 0xff;
        }
        return ipInt;
    }

    static String getAddress(InetSocketAddress address) {
        return address.getHostString() + ":" + address.getPort();
    }

    static InetSocketAddress toInetSocketAddress(String address) {
        String[] parts = address.split(":");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid address format: " + address);
        }
        String ip = parts[0];
        int port = Integer.parseInt(parts[1]);
        return new InetSocketAddress(ip, port);
    }

    static String getAddress(String host, int port) {
        return host + ":" + port;
    }

    static String getLocalHost() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

}
