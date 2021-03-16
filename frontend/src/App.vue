<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
import Header from "./components/Header/Header";
import Footer from "./components/Footer/index";
export default {
  name: "app",
  components: {
    Header,
    Footer
  },
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  height:100%;
}
html, body {
    height:100%;
}
#nav {
  padding: 30px;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}
body {
  margin:0;
}

@font-face {
  font-family: Simply Sans Bold;
  src: url('./assets/theme/fonts/SimplySans-Bold.ttf');
}
@font-face {
  font-family: Simply Sans Book;
  src: url('./assets/theme/fonts/SimplySans-Book.ttf');
}
@font-face {
  font-family: Simply Sans Bold-Italic;
  src: url('./assets/theme/fonts/SimplySans-BoldItalic.ttf');
}
@font-face {
  font-family: Simply Sans Book-Italic;
  src: url('./assets/theme/fonts/SimplySans-BookItalic.ttf');
}

@font-face {
  font-family: Gotham Black;
  src: url('./assets/theme/fonts/GOTHAM-BLACK.TTF');
}
@font-face {
  font-family: Gotham Black-Italic;
  src: url('./assets/theme/fonts/GOTHAM-BLACKITALIC.TTF');
}
@font-face {
  font-family: Gotham Bold;
  src: url('./assets/theme/fonts/GOTHAM-BOLD.TTF');
}
@font-face {
  font-family: Gotham Bold-Italic;
  src: url('./assets/theme/fonts/GOTHAM-BOLDITALIC.TTF');
}
@font-face {
  font-family: Gotham Book;
  src: url('./assets/theme/fonts/GOTHAM-BOOK.TTF');
}
@font-face {
  font-family: Gotham Book-Italic;
  src: url('./assets/theme/fonts/GOTHAM-BOLDITALIC.TTF');
}
@font-face {
  font-family: Gotham Light;
  src: url('./assets/theme/fonts/GOTHAM-LIGHT.TTF');
}
@font-face {
  font-family: Gotham Light-Italic;
  src: url('./assets/theme/fonts/GOTHAM-LIGHTITALIC.TTF');
}
@font-face {
  font-family: Gotham Medium;
  src: url('./assets/theme/fonts/GOTHAM-MEDIUM.TTF');
}
@font-face {
  font-family: Gotham Medium-Italic;
  src: url('./assets/theme/fonts/GOTHAM-MEDIUMITALIC.TTF');
}
@font-face {
  font-family: Gotham Thin;
  src: url('./assets/theme/fonts/GOTHAM-THIN.TTF');
}
@font-face {
  font-family: Gotham Thin-Italic;
  src: url('./assets/theme/fonts/GOTHAM-THINITALIC.TTF');
}
@font-face {
  font-family: Gotham Ultra;
  src: url('./assets/theme/fonts/GOTHAM-ULTRA.TTF');
}
@font-face {
  font-family: Gotham Ultra-Italic;
  src: url('./assets/theme/fonts/GOTHAM-ULTRAITALIC.TTF');
}
@font-face {
  font-family: Gotham Xlight;
  src: url('./assets/theme/fonts/GOTHAM-XLIGHT.TTF');
}
@font-face {
  font-family: Gotham Xlight-Italic;
  src: url('./assets/theme/fonts/GOTHAM-XLIGHTITALIC.TTF');
}
</style>
