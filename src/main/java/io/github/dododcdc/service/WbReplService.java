package io.github.dododcdc.service;

import clojure.java.api.Clojure;
import clojure.lang.Agent;
import clojure.lang.IFn;

import io.github.dododcdc.entity.WbParam;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.InputStream;
import java.io.InputStreamReader;

public class WbReplService {




    private WbParam wbParam ;

    private IFn symbol = Clojure.var("clojure.core", "symbol");
    private IFn intern = Clojure.var("clojure.core", "intern");
    private IFn stopReplServer = Clojure.var("user", "stop-repl-server");
    private Object symUser = symbol.invoke("user");

    public WbReplService(WbParam wbParam) {
        this.wbParam = wbParam;
    }

    /**
     * 启动repl服务
     */
    @PostConstruct
    void start() {
        intern.invoke(symUser, symbol.invoke("_injected-spring-ctx"), this.wbParam.getApplicationContext());
        intern.invoke(symUser, symbol.invoke("_injected-port"), this.wbParam.getPort());
        intern.invoke(symUser, symbol.invoke("_injected-host"), this.wbParam.getHost());
        intern.invoke(symUser, symbol.invoke("_injected-wbReplService"), this);

        // 通关加载字符串执行clojure代码启动nrepl服务
        IFn loadString = Clojure.var("clojure.core", "load-reader");
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("wbrepl.clj");
        InputStreamReader reader = new InputStreamReader(resourceAsStream);
        loadString.invoke(reader);


    }

    @PreDestroy
    void stop() {
        stopReplServer.invoke();
        Agent.soloExecutor.shutdown();
    }
}
