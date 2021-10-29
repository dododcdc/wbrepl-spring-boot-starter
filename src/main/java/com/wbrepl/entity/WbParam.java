package com.wbrepl.entity;


import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

public class WbParam {

    private String host;
    private int port;
    private ApplicationContext applicationContext;
    private Environment env;


    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public Environment getEnv() {
        return env;
    }

    public void setEnv(Environment env) {
        this.env = env;
    }

    public static class  Builder {
        private static final WbParam wbParam = new WbParam();

        public Builder() {
        }

        public  Builder host(String host) {
            wbParam.host = host;
            return this;
        }
        public  Builder port(int port) {
            wbParam.port = port;
            return this;
        }

        public  Builder applicationContext(ApplicationContext applicationContext) {
            wbParam.applicationContext = applicationContext;
            return this;
        }

        public Builder env(Environment env) {
            wbParam.env = env;
            return this;
        }

        public  WbParam build() {
            return wbParam;
        }
    }
}
