(defproject spot "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :profiles {:dev {:dependencies [[speclj "2.5.0"]]}}
  :dependencies [[org.clojure/clojure "1.5.1"]]
  :plugins [[speclj "2.5.0"]]
  :dev-dependencies [[com.speclj/speclj "2.5.0"]]
  :test-path "spec/")
