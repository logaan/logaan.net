;; TODO: Write a wrap function that counts based on html content and excludes
;; html tags. Maybe also renders the html?
(ns logan-cam.core
  (:require [hiccup.core :as c]
            [hiccup.page :as p]
            clojure.pprint))

;; Content constants
(def self-url  "https://logan.cam/")
(def month-and-year "October 2023")

;; Format constants

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

(def header
  (list
   [:span {:class "pre docinfo"}
    "[" [:a {:href self-url} "Home"]
    "] "

    "[" [:a {:href  "https://logaan.github.io/"} "Blog"]
    "|" [:a {:href  "https://twitter.com/logaan"} "Twitter"]
    "] "

    "[" [:a {:href  "https://github.com/logaan"} "Github"]
    "] "

    "[" [:a {:href  "https://www.instagram.com/logancampbell/"} "Instagram"]
    "|" [:a {:href  "https://flickr.com/photos/colinlogan"} "Flickr"]
    "] "

    (spaces 21)]

   [:br]
   [:span {:class "pre docinfo"}
    (spaces max-width)]

   [:br]
   [:span {:class "pre docinfo"}
    (spaces 55)
    "PERSONAL WEBSITE"]))

(def abstract-and-status
  (list))

(def table-of-contents

      ;; Table of contents html follows
      ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
      ;; Table of Contents
      ;; 
      ;; <a href="#section-1">1</a>
      ;; . Introduction ....................................................
      ;; <a href="#page-6">6</a>
      ;; <a href="#section-1.1">1.1</a>
      ;; . Conformance and Error Handling .............................
      ;; <a href="#page-6">6</a>
      ;; <a href="#section-1.2">1.2</a>
      ;; . Syntax Notation ............................................
      ;; <a href="#page-6">6</a>
      ;; <a href="#section-2">2</a>
      ;; . Resources .......................................................
      ;; <a href="#page-7">7</a>
      ;; <a href="#section-3">3</a>
      ;; . Representations .................................................
      ;; <a href="#page-7">7</a>
      ;; <a href="#section-3.1">3.1</a>
      ;; . Representation Metadata ....................................
      ;; <a href="#page-8">8</a>
      ;; <a href="#section-3.1.1">3.1.1</a>
      ;; . Processing Representation Data ......................
      ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
  (list))

(defn page-header [link-url link-text title]
  (let [content-length (count (str link-text title month-and-year))
        total-spacing  (- max-width content-length)
        left-spacing   (int (/ total-spacing 2))
        right-spacing  (- total-spacing left-spacing)]

    (list
     [:span {:id "page-2"}]
     "\n"
     [:span {:class "grey"}
      [:a {:href link-url} link-text]
      (spaces left-spacing)
      title
      (spaces right-spacing)
      month-and-year])))

;; TODO: Make is so that each of these formatting functions return an object
;; that tracks how many lines long they are when rendered as text (ignoring html
;; tags). Might even need a protocol that lets them split themselves across a
;; page break. That splitting function should return content for before the
;; break and after. We should not assume that the before and after content will
;; have the same length as the original content, something like a multi line
;; heading might refuse to split the heading and instead emit padding to the
;; previous page and start the heading on a new page.
;;
;; Renderer can track which page each of the headings ends up on and
;; automatically report back to the table of contents function.
;;
;; All of this is in aid of being able to add new content without having to
;; manually reformat things into pages and update the TOC.
(def content
  [:pre
   (left-and-right "Public Communication Working Group" "L Campbell")
   (left-and-right "Request For Comments: 0" "Curtin and Monash")
   (left-and-right "STD: 42" month-and-year)
   (title "PERSONAL WEBSITE OF LOGAN CAMPBELL (REVISION 2)")
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
        error mechanism was suggested by PARC's EFTP abort message.")

   "1
    2
    3
    4
    5
    6
    7
    8
    9
    0
    1
    2
    3
    4
    5
    6
    7
    8
    9
    0
"

   "


"


   [:span {:class "grey"} (left-and-right "Campbell" "[Page 1]")]
   [:hr]
   [:pre {:class "newpage"}
    (page-header self-url "RFC 1350" "TFTP Revision 2")]

   ])

(def pages
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
      header
      abstract-and-status
      table-of-contents
      content]))))

;; Output

(spit "docs/output.html" pages)

;;;
;; 1 line of header
;; 2 blank lines between header span and start of content
;; 48 lines of content
;; 3 blank lines before footer
;; 1 line of footer
;;
;; ~ 56 lines total between each page HR
;;
;;;
;; <span class="grey"><a href="./rfc1350">RFC 1350</a>                    TFTP Revision 2                    July 1992</span>
;; 
;; 
;;    so it may be used to move files between machines on different
;;    networks implementing UDP.  (This should not exclude the possibility
;;    of implementing TFTP on top of other datagram protocols.)  It is
;;    designed to be small and easy to implement.  Therefore, it lacks most
;;    of the features of a regular FTP.  The only thing it can do is read
;;    and write files (or mail) from/to a remote server.  It cannot list
;;    directories, and currently has no provisions for user authentication.
;;    In common with other Internet protocols, it passes 8 bit bytes of
;;    data.
;; 
;;    Three modes of transfer are currently supported: netascii (This is
;;    ascii as defined in "USA Standard Code for Information Interchange"
;;    [<a href="#ref-1">1</a>] with the modifications specified in "Telnet Protocol
;;    Specification" [<a href="#ref-3" title="&quot;Telnet Protocol Specification,&quot;">3</a>].)  Note that it is 8 bit ascii.  The term
;;    "netascii" will be used throughout this document to mean this
;;    particular version of ascii.); octet (This replaces the "binary" mode
;;    of previous versions of this document.) raw 8 bit bytes; mail,
;;    netascii characters sent to a user rather than a file.  (The mail
;;    mode is obsolete and should not be implemented or used.)  Additional
;;    modes can be defined by pairs of cooperating hosts.
;; 
;;    Reference [<a href="#ref-4" title="&quot;Requirements for Internet Hosts -- Application and Support&quot;">4</a>] (<a href="#section-4.2">section 4.2</a>) should be consulted for further valuable
;;    directives and suggestions on TFTP.
;; 
;; <span class="h2"><a class="selflink" id="section-2" href="#section-2">2</a>. Overview of the Protocol</span>
;; 
;;    Any transfer begins with a request to read or write a file, which
;;    also serves to request a connection.  If the server grants the
;;    request, the connection is opened and the file is sent in fixed
;;    length blocks of 512 bytes.  Each data packet contains one block of
;;    data, and must be acknowledged by an acknowledgment packet before the
;;    next packet can be sent.  A data packet of less than 512 bytes
;;    signals termination of a transfer.  If a packet gets lost in the
;;    network, the intended recipient will timeout and may retransmit his
;;    last packet (which may be data or an acknowledgment), thus causing
;;    the sender of the lost packet to retransmit that lost packet.  The
;;    sender has to keep just one packet on hand for retransmission, since
;;    the lock step acknowledgment guarantees that all older packets have
;;    been received.  Notice that both machines involved in a transfer are
;;    considered senders and receivers.  One sends data and receives
;;    acknowledgments, the other sends acknowledgments and receives data.
;; 
;;    Most errors cause termination of the connection.  An error is
;;    signalled by sending an error packet.  This packet is not
;;    acknowledged, and not retransmitted (i.e., a TFTP server or user may
;;    terminate after sending an error message), so the other end of the
;;    connection may not get it.  Therefore timeouts are used to detect
;;    such a termination when the error packet has been lost.  Errors are
;; 
;; 
;; 
;; <span class="grey">Sollins                                                         [Page 2]</span></pre>
;;;
