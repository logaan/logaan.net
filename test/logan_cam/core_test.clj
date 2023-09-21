(ns logan-cam.core-test
  (:require [clojure.test :refer :all]
            [logan-cam.core :refer :all]))

(deftest a-test
  (testing "Render to a html file"
    (render-and-write "docs/output.html")))
