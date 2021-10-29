

(in-ns 'user)
(require
  '[clojure.reflect :refer [reflect]]
  '[clojure.repl :refer [doc]]
  '[clojure.string :as s]
  '[nrepl.server :as n])
(intern 'user (with-meta 'jb    (meta #'clojure.core/bean)) clojure.core/bean)
(intern 'user (with-meta 'jbean (meta #'clojure.core/bean)) clojure.core/bean)

(defn help
      "List our helper functions (and vars)"
      []
      (println "Helper functions available in the user namespace:")
      (->> (vals (ns-publics 'user))
           (filter #(fn? (deref %)))
           (map #(let [{:keys [name doc]} (meta %)]
                      (str "* " name (if doc (str " :- " doc) ""))))
           (sort)
           (s/join "\n")
           (println))
      (println "\nYou can also use `(doc a-fn)` and `(reflect an-object)`.")
      (println "Remember that *1 holds the result of the last call and *e the last error."))


(defn list-beans
      "ex: `(list-beans)`"
      []
      (seq (.getBeanDefinitionNames user/_injected-spring-ctx)))

(defn find-bean
      "ex: (find-bean \"abc\")"
      [substring]
      (filter
        #(re-matches
           (re-pattern
             (str "(?i).*" substring ".*")) %)
        (list-beans)))

(defn bean
      "ex: `(bean \"userServiceImpl\")`"
      [name]
      (.getBean user/_injected-spring-ctx name))

(defn members
      "Show public methods, fields of a bean; ex: `(members aBean)` <br />
      查看某个类的所有公共的方法，属性 例如 (members String) 也可是自己写的类 例如 (members (bean \"userServiceImpl\"))
      "
      [bean]
      (->> bean clojure.reflect/reflect :members (filter (comp :public :flags)) (map :name)))

;;  部署在哪台服务器就配置该服务器的ip，这样可以远程连接进去，不然只能在部署的那台机器连接
(defonce server (n/start-server :port user/_injected-port :bind user/_injected-host))

;(defonce server (n/start-server :port user/_injected-port :bind "172.16.20.42"))

(defn stop-repl-server
      "Called from ClojureReplServer upon exit; don't use directly"
      []
      (n/stop-server server))

(defn reset
      "Reset the pre-defined functions and vars in the case that you messed up with them. Does not remove vars you made (we'd need clojure.tools/refresh for that)."
      []
      (.reset user/_injected-wbReplService))
