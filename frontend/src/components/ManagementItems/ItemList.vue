<template>
  <div>
    <div class="top-header">
      <el-breadcrumb separator-class="el-icon-arrow-right" class="breadcrumb">
        <el-breadcrumb-item>Front Page</el-breadcrumb-item>
        <el-breadcrumb-item>Item</el-breadcrumb-item>
        <el-breadcrumb-item>Item List</el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <div style="width:1000px;margin:0px auto;">
      <ul class="item-ul-pc" v-loading="itemLoading">
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
            <div class="content-title hover-pointer" @click="handleClick(item)">
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
            <el-button @click="deleteItem(item)">Delete</el-button>
          </div>
        </li>
        <!-- item display end -->
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  name: "OrderList",
  components: {},
  data() {
    return {
      host: "https://data.demostore.top",
      itemList: [],
    };
  },
  created() {
    this.admin = JSON.parse(localStorage.getItem("admin"));
    this.getItemList();
  },
  methods: {
    getItemList() {
      return this.axios
        .get( "/item/getAllItems", {
          params: {
            pageNum: 1,
            pageSize: 100,
          },
        })
        .then((result) => {
          console.log(result);
          this.itemList = result.data.object.list;
          this.itemLoading = false;
        })
        .catch((error) => {
          this.$message.error("Cannot load items");
        });
    },

    deleteItem(item) {
      return this.axios
        .get( "/management/item/deleteItem", {
          params: {
            itemId: item.id,
          },
          headers: {
            Authorization: this.admin.authToken
          },
        })
        .then((res) => {
          location.reload();
        });
    },
  },
};
</script>

<style lang="less" scoped>
@import "../../assets/less/common.less";
.top-header {
  border-bottom: 1px solid #dee2e6 !important;
  min-height: 50px;
  display: flex;
  flex-direction: column;
  justify-content: center;
}
.breadcrumb {
  font-size: 18px;
  font-family: Gotham Book;
  margin: auto 15px;
}
.el-button--primary{
  padding:10px 15px;
}
.el-button--default {
  padding:10px 15px;
}
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
</style>
