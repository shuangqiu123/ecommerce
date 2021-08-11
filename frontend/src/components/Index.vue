<template>
  <div>
    <Header></Header>
    <div style="width: 850PX;display: inline-block; margin:0px auto;">
      <div class="block" style="margin-top:20px;margin-bottom:20px;">
      </div>
      <el-divider></el-divider>
      <el-form class="demo-form-inline" :model="searchForm" :inline="true">
        <el-form-item label="Name Search">
          <el-input v-model="searchForm.name" @change="nameSearch"></el-input>
        </el-form-item>
        <el-form-item>
          <el-select
            v-model="searchForm.price"
            placeholder="Sort By"
            @change="priceSort"
          >
            <el-option label="Price (low to high)" value="1"></el-option>
            <el-option label="Price (high to low)" value="0"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item >
          <el-checkbox v-model="searchForm.inStock" @change="inStockFilter">In Stock</el-checkbox>
        </el-form-item>
      </el-form>

      <div>
        <ul class="item-ul-pc" v-loading="itemLoading" v-if="reLoad">
          <!-- item display start -->
          <li class="item-li" v-for="(item, index) in itemList" :key="index">
            <!-- front image -->
            <img
              :src="item.image.split(',')[0]"
              class="img-portrait hover-pointer"
            />
            <!-- text -->
            <div class="content-main">
              <!-- title -->
              <div
                class="content-title hover-pointer"
                @click="handleClick(item)"
              >
                <div class="p-title" style="text-align:left;">
                  <span>
                    {{ item.title }}
                  </span>
                </div>
                <p></p>
                <!-- description -->
                <p class="p-describe" style="text-align:left;">
                  {{ item.sellPoint }}
                </p>
              </div>
              <!-- price -->
              <div class="content-price" style="text-align:left;">
                <p class="content-price-p">
                  <span class="content-price-orange-sm">$</span>
                  <span class="content-price-orange">{{ item.price }}</span>
                </p>
              </div>
            </div>
          </li>
          <!-- item display end -->
        </ul>
      </div>
    </div>
    <Footer />
  </div>
</template>

<script>
import Header from "./Header/Header";
import Footer from "./Footer/index";
export default {
  name: "Index",
  components: {
    Header,
    Footer,
  },
  data() {
    return {
      itemList: [], // item list
      itemListCopy: [],
      isLogin: false, //login status
      user: null, // user info
      searchForm: {
        name: "",
        category: "",
        price: "",
        inStock: false,
      },
      reLoad: true,
      itemLoading: true,
      demopics: ["../src/assets/demoPic/demo1.jpg"],
      host: "https://data.demostore.top",
    };
  },
  created() {
    this.user = JSON.parse(localStorage.getItem("user"));
    if (this.user != null) {
      this.isLogin = true;
    }
    this.getItemList();
  },
  methods: {
    changeCourseTab(tabName) {
      this.classSelect = tabName;
      sessionStorage && sessionStorage.setItem("courseTab", tabName);
    },
    getItemList() {
      return this.axios
        .get("/item/getAllItems", {
          params: {
            pageNum: 1,
            pageSize: 100,
          },
        })
        .then((result) => {
          console.log(result);
          this.itemList = result.data.object.list;
          this.itemListCopy = JSON.parse(JSON.stringify(this.itemList));
          this.itemLoading = false;
        })
        .catch((error) => {
          this.$message.error("Cannot load items");
        });
    },
    handleCurrentChange() {},
    currentPage3() {},
    handleClick(item) {
      this.$router.push({ path: "/item/" + item.id, params: { item: item } });
    },
    priceSort() {
      if (this.searchForm.price == "1") {
        this.itemList = this.itemList.sort((a, b) => {
          return a.price - b.price;
        });
      } else {
        this.itemList = this.itemList.sort((a, b) => {
          return b.price - a.price;
        });
      }
      this.reloadItem();
    },

    inStockFilter() {
      console.log(this.searchForm.inStock)
      if (this.searchForm.inStock) {
        this.itemList = this.itemList.filter(a=>a.num>0);
      }
      this.reloadItem();
    },

    reloadItem() {
      this.reLoad = false;
      this.$nextTick(() => {
        this.reLoad = true;
      });
    },

    nameSearch() {
      this.itemList = JSON.parse(JSON.stringify(this.itemListCopy));
      this.itemList = this.itemList.filter(a=>a.title.includes(this.searchForm.name));
      this.reloadItem();
    }
  },
};
</script>

<style lang="less" scoped>
@import "../assets/less/common.less";
.hover-pointer {
  cursor: pointer;
}
.item-ul-pc .item-li {
  padding: 30px 0 40px 0;
  border-bottom: 1px solid #ededed;
  position: relative;
  &:last-child {
    border-bottom: 0;
  }
}

.img-portrait {
  width: 121px;
  height: 159px;
  border-radius: 4px;
  float: left;
}
.content-main {
  line-height: 1.4;
  margin-left: 141px;
  position: relative;
  height: 150px;
}
.content-main .p-title {
  font-size: 20px;
  color: #333;
  font-family: PingFangSC-Medium, PingFang SC;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  width: 350px;
}

p {
  margin-top: 0;
}

.item-ul-pc .content-main .p-describe {
  font-size: 14px;
  margin-top: 5px;
  color: #333;
  word-wrap: normal;
  text-overflow: ellipsis;
  white-space: nowrap;
  overflow: hidden;
}

ol,
ul {
  margin: 0;
  padding: 0;
  list-style: none;
}
* {
  -webkit-tap-highlight-color: transparent;
}



.item-ul-pc .content-main .content-price {
  height: 28px;
  line-height: 28px;
  position: absolute;
  width: 100%;
  bottom: -4px;
}
.item-ul-pc .content-main .content-price-p {
  font-size: 0;
}
.item-ul-pc .content-main .content-price-orange-sm {
  color: #ff7452;
  font-size: 14px;
}


.content-price-p {
  font-size: 0;
}
.content-price-orange {
  color: #ff7452;
  font-size: 20px;
}
.content-price {
  height: 28px;
  line-height: 28px;
  position: absolute;
  width: 100%;
  bottom: -4px;
}

.p-title {
  font-size: 20px;
  color: #333;
  font-family: PingFangSC-Medium, PingFang SC;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  width: 350px;
}
.p-describe {
  // .font(14px);
  font-size: 14px;
  margin-top: 5px;
  color: #333;
  // .text-overflow;
}


.item-ul-pc {
  width: 790px;
  background-color: #fff;
  padding: 0 30px;
  // .fl;
}
.tag-icon {
  width: 57px;
  height: 18px;
  position: absolute;
  top: 30px;
  left: 0;
}

.more-icon {
  width: 10px;
  height: 10px;
  vertical-align: middle;
}

.no-data-title {
  font-size: 20px;
  color: #333;
  font-weight: 500;
}
.el-button--primary{
  padding:10px 15px;
}
.el-button--default {
  padding:10px 15px;
}
</style>
