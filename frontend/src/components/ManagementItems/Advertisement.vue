<template>
  <div>
    <div class="top-header">
      <el-breadcrumb separator-class="el-icon-arrow-right" class="breadcrumb">
        <el-breadcrumb-item>Front Page</el-breadcrumb-item>
        <el-breadcrumb-item>Sale</el-breadcrumb-item>
        <el-breadcrumb-item>Advertisement</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
  </div>
</template>

<script>
export default {
  name: "Advertisement",
  components: {
  },
  data() {
    return {
      host: "https://data.demostore.top",
    };
  },
  created() {
    this.loadOrders();
  },
  methods: {
    loadOrders() {
      return this.axios
        .get( "/order/user/getOrderListByUserId", {
          headers: {
            Authorization: this.userDTO.authToken,
          },
        })
        .then((res) => {
          for (let row of res.data.object) {
            if (row.status == 0) {
              continue;
            }
            let price = 0;
            for (let item of row.orderItemList) {
              price += item.price * item.num;
            }

            let obj = {
              name: row.orderId.split("-")[0],
              date: row.createTime.substring(0, 10),
              price: "$ " + price,
              status: row.status == 1 ? "PAID" : "COMPLETED",
            };
            this.tableData.push(obj);
          }
        });
    },
  },
};
</script>

<style scoped>
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
</style>
