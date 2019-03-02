(ns pt.styles.colors)

(def ^{:private true} theme-blue-gray
  "https://www.materialpalette.com/colors"
  {"50" "#eceff1"
   "100" "#cfd8dc"
   "200" "#b0bec5"
   "300" "#90a4ae"
   "400" "#78909c"
   "500" "#607d8b"
   "600" "#546e7a"
   "700" "#455a64"
   "800" "#37474f"
   "900" "#263238"})

(def ^{:private true} theme-red
  "https://www.materialpalette.com/colors"
  {"50"  "#ffebee"
   "100" "#ffcdd2"
   "200" "#ef9a9a"
   "300" "#e57373"
   "400" "#ef5350"
   "500" "#f44336"
   "600" "#e53935"
   "700" "#d32f2f"
   "800" "#c62828"
   "900" "#b71c1c"})

(def ^{:private true} theme-orange
  "https://www.materialpalette.com/colors"
  {"50"  "#fff3e0"
   "100" "#ffe0b2"
   "200" "#ffcc80"
   "300" "#ffb74d"
   "400" "#ffa726"
   "500" "#ff9800"
   "600" "#fb8c00"
   "700" "#f57c00"
   "800" "#ef6c00"
   "900" "#e65100"})

(def ^{:private true} theme-blue
  "https://www.materialpalette.com/colors"
  {"50"  "#e3f2fd"
   "100" "#bbdefb"
   "200" "#90caf9"
   "300" "#64b5f6"
   "400" "#42a5f5"
   "500" "#2196f3"
   "600" "#1e88e5"
   "700" "#1976d2"
   "800" "#1565c0"
   "900" "#0d47a1"})

(def ^{:private true} theme-cyan
  "https://www.materialpalette.com/colors"
  {"50"  "#e0f7fa"
   "100" "#b2ebf2"
   "200" "#80deea"
   "300" "#4dd0e1"
   "400" "#26c6da"
   "500" "#00bcd4"
   "600" "#00acc1"
   "700" "#0097a7"
   "800" "#00838f"
   "900" "#006064"})

(def ^{:private true} theme-green
  "https://www.materialpalette.com/colors"
  {"50" "#e8f5e9"
   "100" "#c8e6c9"
   "200" "#a5d6a7"
   "300" "#81c784"
   "400" "#66bb6a"
   "500" "#4caf50"
   "600" "#43a047"
   "700" "#388e3c"
   "800" "#2e7d32"
   "900" "#1b5e20"})

(def ^{:private true} theme-yellow
  "https://www.materialpalette.com/colors"
  {"50" "#fffde7"
   "100" "#fff9c4"
   "200" "#fff59d"
   "300" "#fff176"
   "400" "#ffee58"
   "500" "#ffeb3b"
   "600" "#fdd835"
   "700" "#fbc02d"
   "800" "#f9a825"
   "900" "#f57f17"})

(def ^{:private true} gray-scale
  {"10"  "#F7F7F7"
   "25"  "#EBEBEB"
   "50"  "#D7D7D7"
   "100" "#C2C2C2"
   "200" "#AEAEAE"
   "300" "#999999"
   "400" "#858585"
   "500" "#717171"
   "600" "#5C5C5C"
   "700" "#484848"
   "800" "#333333"
   "900" "#1F1F1F"})

(def ^{:private true} true-color
  {"black" "#000000"
   "white" "#ffffff"})

(def ^{:private true} set-colors
  {:error "#FF0300"
   :warning "#FF6600"
   :success "#00E062"
   :link-100 "#ccebff"
   :link-200 "#99d6ff"
   :link-300 "#66c2ff"
   :link-400 "#33adff"
   :link-500 "#009bff"
   :link-600 "#007acc"
   :link-700 "#005c99"})

(def colors
  (->> [[theme-blue-gray "primary"]
        [gray-scale "gray-scale"]
        [true-color "true"]]
       (reduce
        (fn [acc [color-map color-name]]
          (let [updated-map
                (reduce (fn [acc [color-level color-value]]
                          (assoc acc
                                 (keyword (str color-name "-" color-level))
                                 color-value))
                        {}
                        color-map)]
            (merge acc updated-map)))
        {})
       (merge set-colors)))
