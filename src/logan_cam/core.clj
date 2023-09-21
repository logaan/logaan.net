(ns logan-cam.core
  (:require [hiccup.core :as h]))

(defn foo []
  (h/html [:h1 "Hello, World!"]))

(defn render-and-write [path]
  (spit path (foo)))
