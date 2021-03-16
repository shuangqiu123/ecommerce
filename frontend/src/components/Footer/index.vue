<template>
  <div class="footer-pc">
    <p><em>&copy;</em>This Website is written by Shuang Qiu.</p>
    <div>
      <a href="https://github.com/shuangqiu123/ecommerce" target="_blank"><img :src="require('./static/github.png')"/></a>
    </div>
  </div>
</template>

<script>
export default {
  name: "Footer",
};
</script>

<style lang="less" scoped>
.footer-pc {
  line-height: 20px;
  margin-top: 60px;
  margin-bottom: 20px;
  font-size: 14px;
  color: #999;
  position: relative;
  border-top: 1px solid #ededed;
}
</style>
