package io.github.astro.mantis.configuration;

import io.github.astro.mantis.common.exception.SourceException;
import io.github.astro.mantis.common.util.NetUtils;
import io.github.astro.mantis.common.util.StringUtils;
import lombok.Getter;
import lombok.Setter;

import java.net.InetSocketAddress;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Basic for:
 * 1、Rpc Request: {@link RemoteUrl}
 * 2、Connect to Third Party Middleware
 */
public class URL {

    private final List<String> paths;

    @Getter
    private final Map<String, String> parameters;

    @Getter
    @Setter
    private String host;

    @Getter
    @Setter
    private int port;

    @Getter
    @Setter
    private String protocol;

    public URL() {
        this.paths = new LinkedList<>();
        this.parameters = new HashMap<>();
    }

    public URL(String protocol, InetSocketAddress address, Map<String, String> params) {
        this(protocol, address);
        this.parameters.putAll(params);
    }

    public URL(String protocol, InetSocketAddress address) {
        this();
        this.protocol = protocol;
        this.host = address.getHostString();
        this.port = address.getPort();
    }

    public URL(String protocol, String address) {
        this(protocol, NetUtils.toInetSocketAddress(address));
    }

    public URL(String protocol, String ip, int port) {
        this(protocol, new InetSocketAddress(ip, port));
    }

    public URL(String protocol, String address, Map<String, String> params) {
        this(protocol, NetUtils.toInetSocketAddress(address), params);
    }

    public URL(String protocol, String ip, int port, Map<String, String> params) {
        this(protocol, new InetSocketAddress(ip, port), params);
    }

    public static URL valueOf(String url) {
        if (url == null || url.trim().isEmpty()) {
            throw new SourceException("url is null");
        }
        String[] strings = url.split("\\?");
        String fixed = strings[0];
        int protocolStartIndex = fixed.lastIndexOf("://");
        String protocol = fixed.substring(0, protocolStartIndex);
        fixed = fixed.substring(protocolStartIndex + 3);
        String[] addressPath = fixed.split("/");
        String[] address = addressPath[0].split(":");
        String ip = address[0];
        int port = Integer.parseInt(address[1]);
        URL urlObj = new URL(protocol, ip, port);
        if (addressPath.length > 1) {
            for (int i = 1; i < addressPath.length; i++) {
                urlObj.addPath(addressPath[i]);
            }
        }
        if (strings.length > 1) {
            String params = strings[1];
            urlObj.addParameters(urlStringToMap(params));
        }
        return urlObj;
    }

    public static String mapToUrlString(Map<String, String> parameters) {
        return parameters.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("&"));
    }

    public static Map<String, String> urlStringToMap(String params) {
        return Arrays.stream(params.split("&"))
                .map(param -> param.split("="))
                .collect(Collectors.toMap(
                        split -> split[0],
                        split -> split[1]
                ));
    }

    public String getAddress() {
        if (StringUtils.isBlank(host)) {
            return null;
        }
        return host + ":" + port;
    }

    public void setAddress(String address) {
        InetSocketAddress inetSocketAddress = NetUtils.toInetSocketAddress(address);
        this.host = inetSocketAddress.getHostString();
        this.port = inetSocketAddress.getPort();
    }

    public void addPath(String path) {
        paths.add(path);
    }

    public void addPath(int index, String path) {
        paths.add(index, path);
    }

    public void removePath(int index) {
        if (paths.size() > index) {
            paths.remove(index);
        }
    }

    public void addParameter(String key, String value) {
        parameters.put(key, value);
    }

    public void addParameters(Map<String, String> parameters) {
        this.parameters.putAll(parameters);
    }

    public String getParameter(String key, String defaultValue) {
        return parameters.getOrDefault(key, defaultValue);
    }

    public String getParameter(String key) {
        return parameters.get(key);
    }

    public boolean getBooleanParameter(String key) {
        return Boolean.parseBoolean(getParameter(key));
    }

    public boolean getBooleanParameter(String key, boolean defaultValue) {
        String value = getParameter(key);
        return StringUtils.isBlank(value) ? defaultValue : Boolean.parseBoolean(value);
    }

    public int getIntParameter(String key) {
        return Integer.parseInt(getParameter(key));
    }

    public int getIntParameter(String key, int defaultValue) {
        String value = getParameter(key);
        return StringUtils.isBlank(value) ? defaultValue : Integer.parseInt(value);
    }

    public long getLongParameter(String key) {
        return Long.parseLong(getParameter(key));
    }

    public void removeParameter(String key) {
        parameters.remove(key);
    }

    public String getAuthority() {
        return protocol + "://" + host + ":" + port;
    }

    public String pathsToString() {
        StringBuilder builder = new StringBuilder();
        for (String path : paths) {
            builder.append("/");
            builder.append(path);
        }
        return builder.toString();

    }

    @Override
    public String toString() {
        String authority = getAuthority();
        String paths = pathsToString();
        String params = mapToUrlString(parameters);
        return authority + paths + (StringUtils.isBlank(params) ? "" : "?" + params);
    }

}
