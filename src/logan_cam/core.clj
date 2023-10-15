;; TODO: Write a wrap function that counts based on html content and excludes
;; html tags. Maybe also renders the html?
(ns logan-cam.core
  (:require [hiccup.core :as c]
            [hiccup.page :as p]
            clojure.pprint))

;; Constants

(def max-width 71)
(def left-margin 3)
(def p-content-width 69)
(def lines-per-page 55)

;; String manipulation

(defn spaces [n]
  (apply str (repeat n \space)))

(defn center [s len]
  (let [slen (count s)
        lpad (int (/ (- len slen) 2))
        rpad (- len slen lpad)]
    (str (spaces lpad) s (spaces rpad))))

;; High level page elements

(defn wrap-and-indent [size text]
  (let [indent (spaces left-margin)]
    (clojure.pprint/cl-format
     nil
     (str indent "~{~<~%" indent "~1," size ":;~A~> ~}")
     (clojure.string/split text #"[ \n]+"))))

(defn left-and-right [left right]
  (let [char-count (+ (count left) (count right))
        middle-count (- max-width char-count)]
    (str left (spaces middle-count) right "\n")))

(defn title [text]
  (list
   "\n\n"
   [:span {:class "h1"} (center text max-width)]
   "\n\n"))

(defn heading [text]
  (str text "\n\n"))

(defn paragraph [text]
  (str (wrap-and-indent max-width text) "\n\n"))

;; Actual page contents

(defn foo []
  (c/html
   (list
    (p/doctype :xhtml-transitional)
    (p/xhtml-tag
     "en"
     [:head
      [:title "Logan Campbell"]
      [:style {:type "text/css"}
       (slurp "src/styles.css")]]
     [:body
      [:pre
       (left-and-right "Network Working Group" "K. Sollins")
       (left-and-right "Request For Comments: 1350" "MIT")
       (left-and-right "STD: 33" "July 1992")
       (title "THE TFTP PROTOCOL (REVISION 2)")
       (heading "Status of this Memo")
       (paragraph
        "This RFC specifies an IAB standards track protocol for the Internet
       community, and requests discussion and suggestions for improvements.
       Please refer to the current edition of the \"IAB Official Protocol
       Standards\" for the standardization state and status of this protocol.
       Distribution of this memo is unlimited.")
       (heading "Summary")
       (paragraph
        "TFTP is a very simple protocol used to transfer files. It is from this
        that its name comes, Trivial File Transfer Protocol or TFTP. Each
        nonterminal packet is acknowledged separately. This document describes
        the protocol and its types of packets. The document also explains the
        reasons behind some of the design decisions.")
       (heading "Acknowlegements")
       (paragraph
        "The protocol was originally designed by Noel Chiappa, and was
        redesigned by him, Bob Baldwin and Dave Clark, with comments from Steve
        Szymanski. The current revision of the document includes modifications
        stemming from discussions with and suggestions from Larry Allen, Noel
        Chiappa, Dave Clark, Geoff Cooper, Mike Greenwald, Liza Martin, David
        Reed, Craig Milo Rogers (of USC-ISI), Kathy Yellick, and the author. The
        acknowledgement and retransmission scheme was inspired by TCP, and the
        error mechanism was suggested by PARC's EFTP abort message.")]]))))

;; Output

(defn render-and-write [path]
  (spit path (foo)))

(render-and-write "docs/output.html")
