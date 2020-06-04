(ns interop.getsentry.sentry-react-native.scope-v1
  (:require ["@sentry/react-native" :as module]
            [goog.object :as gobject]))

(assert module)

(defn with-scope [cb] (module/withScope cb))
(defn configure-scope [cb] (module/configureScope cb))
(defn add-breadcrumb [scope breadcrumb] (.addBreadcrumb scope (clj->js breadcrumb)))
(defn set-context [scope name context] (.setContext scope name context))
(defn set-extras [scope extras] (.setExtras scope (clj->js extras)))
(defn set-tags [scope tags] (.setTags scope (clj->js tags)))
(defn set-extra [scope k v] (.setExtra scope k (pr-str v)))
(defn set-tag [scope k v] (.setTag scope k v))
(defn set-user [scope m] (.setUser scope (clj->js m)))

