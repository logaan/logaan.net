;; TODO: Get all of the content from my resume into this guy. It's small enough
;; that it doesn't need a table of contents. And I can just do the division into
;; pages by hand.
(ns logan-cam.core
  (:require [hiccup.core :as c]
            [hiccup.page :as p]
            clojure.pprint
            [clojure.string :as str]))

;; Content constants
(def self-url  "https://logan.cam/")
(def month-and-year "October 2023")

;; Format constants

(def max-width 72)
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

(defn h1 [text]
  [:span {:class "h2"}
   (str text "\n\n")])

(defn h2 [text]
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

    ;; TODO: Should grow with max width
    (spaces 22)]

   [:br]
   [:span {:class "pre docinfo"}
    (spaces max-width)]

   [:br]
   [:span {:class "pre docinfo"}
    ;; TODO: Should grow with max width
    (spaces 56)
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

(def skills
  (list
   (h1 "1. Skills")

   (h1 "1.1. Languages")
   (paragraph
    "Clojure, ClojureScript, Javascript, Ruby, Python, Java, Golang, Erlang,
    Haskell, Scala, Rust, Groovy, Lua, Cold Fusion, PHP, ASP Classic")

   (h1 "1.2. Tools")
   (paragraph
    "Liberator, Reagent, React, Angular, Rails, Spring, Dropwizard, Netty,
    Prototype, YUI, Nitrogen, Alfresco Surf")

   (h1 "1.2. Frameworks")
   (paragraph
    "Kubernetes, Kafka, Docker, AWS, Heroku, Postgres, Cassandra, Datomic,
    RabbitMQ")

   (h1 "1.2. Practices")
   (paragraph
    "Scrum, Kanban, Lean, SAFe, TDD, Pair Programming, CI, CD")))

(defn lines [& items]
  (-> (mapcat (fn [line]
                [(spaces left-margin)
                 line
                 "\n"])
            items)
      (concat ["\n"])))

(defn bullets [& items]
  (-> (mapcat (fn [line]
                [" o "
                 line
                 "\n"])
              items)
      (concat ["\n"])))

;; TODO: Do something like `lines` but for lists that will insert the bullets
;; and wrap with the correct indentation.

(defn end-of-page [page-number]
  (list
   "\n\n\n"

   [:span {:class "grey"} (left-and-right "Campbell"
                                          (str "[Page " page-number "]"))]
   [:hr]))

(def new-page
  (list
   [:pre {:class "newpage"}
    (page-header self-url "PW 1337" "Website of Logan Campbell")]

   "\n\n"))

(defn page-break [page-number]
  (list
   (end-of-page page-number)
   new-page))

(def experience
  (list
   (h1 "2. Experience")

   (h1 "2.1. Zendesk")

   (paragraph "Melbourne, Victoria.")

   (lines
    "Senior Software Engineer ..................... August 2017 - May 2019"
    "Technical Lead for Apps ..................... October 2018 - May 2021"
    "Staff Software Engineer ....................... May 2019 - March 2022"
    "Group Technical Lead ...................... May 2021 - September 2023"
    "Senior Staff Software Engineer .......... March 2022 - September 2023")

   (bullets
    "Evaluation of API Gateways and tender process"
    "Lead the design of tools, standards, and strategory for REST API
   versioning across all of Zendesk (Ruby, Golang, REST)"
    "Design of a request rate monitor and limiter (AWS)"
    "Design and prototypes of a distributed transaction manager (SQS,
   Ruby, Java)"
    "Development of App market (Ruby, Javascript, MySql)")

   (page-break 1)

   (h1 "2.2. Silverpond")
   (paragraph "Melbourne, Victoria.")

   (lines
    "Software Engineer ....................... November 2012 - August 2017")

   (bullets
    "Physical fault detection model from smart meter readings, API and UI
   for fault analysis and remedy tracking, Power network simulation, and
   Data processing pipeline for Powercor (Python, Clojure, ClojureScript,
   React)"
    "Training, architecture proposals, and prototyping for Aus Post
   (Clojure)"
    "Record management interface embedded in a medical device for
   Bluechiip (JS)"
    "Franchisee relationship management UI for 7-Eleven (Clojurescript,
   React)")

   (h1 "2.3. Thoughtworks")
   (paragraph "Melbourne, Victoria.")

   (lines
    "Consultant Developer ...................... July 2011 - November 2012")

   (bullets
    "Three real time financial web services for IOOF (Java)"
    "Payment consolidation service for NBN (Java)"
    "Charity campaign platform for Live Below The Line (Rails)"
    "Domain registry, and performance test suite for Aus Registry (Java,
   Scala)"
    "Rapidly developed MVP for My Red Alert (Rails)"
    "US political candidate information site for New Organising Institute
   (Rails)"
    "Automated content deployment network for Hitnet (Bash)")

   (h1 "2.4. Hard Hat Digital")
   (paragraph "South Yarra, Victoria.")

   (lines
    "Senior Programmer .............................. June 2010 - May 2011")

   (bullets
    "Implementing HHD's CMS on client websites (Rails)"
    "Publishing open source projects (Rails, Clojure)"
    "Extending HHD's CMS (Rails)"
    "Building custom web applications (Rails)"
    "Maintaining legacy websites (PHP)")

   (h1 "2.5. Curtin University of Technology")

   (paragraph "Bentley, Western Australia.")

   (lines
    "Web Programmer ............................. July 2009 - January 2010")

   (bullets
    "Developing an Enterprise Document Management System (JS, Alfresco)")

   (page-break 2)

   (h1 "2.6. Webfirm")

   (paragraph "East Perth, Western Australia..")

   (lines
    "Programmer .............................. September 2007 to July 2008"
    "Senior Programmer ............................ July 2008 to July 2009")

   (bullets
    "Developed software that automated most of the process of adding our
   content management system to client sites"
    "Developed an automated deployment tool for all internal Rails sites"
    "Introduced Rails, trained staff and implemented a new CMS to use
   with Rails"
    "Introduced SVN and then Git, trained staff and set up servers"
    "Rapidly implemented client sites on tight budgets and deadlines")))

(def talks
  (list
   (h1 "3. Talks")

   (h2 "EuroClojure 2014")
   (paragraph "Clojure at a Post Office")

   (h2 "Melbourne FP User Group")
   (paragraph
    "Intro to Erlang, A Monad is not a Burrito, Clojure Workshop, Promise
   Streams, OO values in FP languages, Clojure at a Post Office, Intro to
   Elixir, Full Stack development with FP (Panel)")

   (h2 "Melbourne Clojure User Group")
   (paragraph
    "Intro to Datomic, Promise Streams, Async IO Coordination, Nonaga, Squid")

   (h2 "Australia Post Digital Mailbox")
   (paragraph
    "Async IO Coordination, Clojure Workshop, CQRS with Cassandra")

   (h2 "ThoughtWorks Internal Conferences")
   (paragraph
    "Why Functional Programming matters, Introduction to Erlang")

   (h2 "Webjam9 2008")
   (paragraph
    "Automated CMS back end website generation")))

(def education
  (list

   (h1 "4. Education")

   (lines
    "Monash University, Victoria."
    "Faculty of Information Technology."
    "Master of Computer Science. 2022 - Present.")

   (lines
    "Curtin University of Technology, Western Australia."
    "School of Science, Mathematics and"
    "Engineering. Department of Computing."
    "BsC Computer Science. 2005 - 2007. Did not complete.")

   (end-of-page 3)))

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

   skills
   experience
   talks
   education])

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
;;;
