package io.github.dododcdc.entity;


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
        private static final WbParam WbParam2 = new WbParam();

        public Builder() {
        }

        public  Builder host(String host) {
            WbParam2.host = host;
            return this;
        }
        public  Builder port(int port) {
            WbParam2.port = port;
            return this;
        }

        public  Builder applicationContext(ApplicationContext applicationContext) {
            WbParam2.applicationContext = applicationContext;
            return this;
        }

        public Builder env(Environment env) {
            WbParam2.env = env;
            return this;
        }

        public WbParam build() {
            return WbParam2;
        }
    }
}
