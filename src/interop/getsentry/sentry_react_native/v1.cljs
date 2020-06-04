(ns interop.getsentry.sentry-react-native.v1
  (:require ["@sentry/react-native" :as module]
            [goog.object :as gobject]))

(assert module)

;https://github.com/getsentry/sentry-javascript/blob/master/packages/minimal/src/index.ts
(defn with-scope [cb] (module/withScope cb))
(defn configure-scope [cb] (module/configureScope cb))
(defn add-breadcrumb
  ([breadcrumb] (module/addBreadcrumb (clj->js breadcrumb)))
  ([scope breadcrumb] (.addBreadcrumb scope (clj->js breadcrumb))))
(defn set-context
  ([name context] (module/setContext name context))
  ([scope name context] (.setContext scope name context)))
(defn set-extras
  ([extras] (module/setExtras (clj->js extras)))
  ([scope extras] (.setExtras scope (clj->js extras))))
(defn set-tags
  ([tags] (module/setTags (clj->js tags)))
  ([scope tags] (.setTags scope (clj->js tags))))
(defn set-extra
  ([k v] (module/setExtra k (pr-str v)))
  ([scope k v] (.setExtra scope k (pr-str v))))
(defn set-tag
  ([k v] (module/setTag k v))
  ([scope k v] (.setTag scope k v)))
(defn set-user
  ([m] (module/setUser (clj->js m)))
  ([scope m] (.setUser scope (clj->js m))))

(defn capture-exception [err]
  (with-scope
    (fn [scope]
      (some->> (ex-data err) pr-str (set-extra scope "ex-data"))
      (module/captureException err))))

(defn capture-message
  ([message] (module/captureMessage message))
  ([message level] (module/captureMessage message level)))

(defn capture-event [event]
  (module/captureEvent (clj->js event)))

;https://github.com/getsentry/sentry-react-native/blob/master/src/js/sdk.ts
(defn init [options] (module/init (clj->js options)))
(defn set-release [release] (module/setRelease release))
(defn set-dist [dist] (module/setDist dist))
(defn native-crash [] (module/nativeCrash))

(comment
  (init {:dsn "https://<key>@<organization>.ingest.sentry.io/<project>"})
  (native-crash)
  (capture-exception (ex-info "My first Sentry error!" {:a 1})))
