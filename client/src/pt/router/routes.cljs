(ns pt.router.routes)

(def routes
  "Routing is handled by bidi. See https://github.com/juxt/bidi for details"
  ["/"
   {"" :home
    ["raid/" :raid-id] :raid}])
