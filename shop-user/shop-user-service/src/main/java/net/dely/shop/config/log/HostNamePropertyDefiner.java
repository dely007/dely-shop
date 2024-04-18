package net.dely.shop.config.log;

import ch.qos.logback.core.PropertyDefinerBase;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class HostNamePropertyDefiner extends PropertyDefinerBase {
    @Override
    public String getPropertyValue() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }
}

