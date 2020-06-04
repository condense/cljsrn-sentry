(ns interop.getsentry.sentry-react-native.v1
  (:require ["@sentry/react-native" :as module]
            [goog.object :as gobject]))

(assert module)

;https://github.com/getsentry/sentry-javascript/blob/master/packages/minimal/src/index.ts
(defn add-breadcrumb ([breadcrumb] (module/addBreadcrumb (clj->js breadcrumb))))
(defn set-context ([name context] (module/setContext name context)))
(defn set-extras ([extras] (module/setExtras (clj->js extras))))
(defn set-tags ([tags] (module/setTags (clj->js tags))))
(defn set-extra ([k v] (module/setExtra k (pr-str v))))
(defn set-tag ([k v] (module/setTag k v)))
(defn set-user ([m] (module/setUser (clj->js m))))

(defn capture-exception [err]
  (let [data (ex-data err)]
    (module/withScope
      (fn [scope]
        (when-data (.setExtra scope "ex-data" (pr-str data)))
        (module/captureException err)))))

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
