;; 55 lines per page
;; 3 char left margin. 69 chars content.
(ns logan-cam.core
  (:require [hiccup.core :as c]
            [hiccup.page :as p]))

(defn foo []
  (c/html
   (list
    (p/doctype :xhtml-transitional)
    (p/xhtml-tag "en"
                 [:h1 "Hello, World!"]))))

(defn render-and-write [path]
  (spit path (foo)))
