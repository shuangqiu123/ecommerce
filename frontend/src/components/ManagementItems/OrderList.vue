<template>
  <div>
    <div class="top-header">
      <el-breadcrumb separator-class="el-icon-arrow-right" class="breadcrumb">
        <el-breadcrumb-item>Front Page</el-breadcrumb-item>
        <el-breadcrumb-item>Order</el-breadcrumb-item>
        <el-breadcrumb-item>Order List</el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <div style="width:1000px;margin:0px auto;">
      <el-table :data="tableData" style="width: 100%">
        <el-table-column prop="name" label="ORDER" width="180">
        </el-table-column>
        <el-table-column prop="date" label="DATE" width="180">
        </el-table-column>
        <el-table-column prop="user" label="USER" width="100">
        </el-table-column>
        <el-table-column
          prop="price"
          label="PRICE"
          width="100"
        ></el-table-column>
        <el-table-column
          prop="status"
          label="STATUS"
          width="110"
        ></el-table-column>
        <el-table-column label="MANAGE" width="180">
          <template slot-scope="scope">
            <el-button
              @click="handleCancel(scope.row)"
              type="text"
              size="small"
              v-if="scope.row.status == 'UNPAID'"
              >CANCEL</el-button
            >
            <el-button
              @click="handleShip(scope.row)"
              type="text"
              size="small"
              v-if="scope.row.status == 'PAID'"
              >SHIP</el-button
            >
          </template>
        </el-table-column>
      </el-table>
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
      tableData: [],
      orderList: [],
    };
  },
  created() {
    this.admin = JSON.parse(localStorage.getItem("admin"));
    this.loadOrders();
  },
  methods: {
    loadOrders() {
      return this.axios
        .get(this.host + "/management/order/getAllOrders", {
          headers: {
            Authorization: this.admin.authToken,
          },
        })
        .then((res) => {
          let i = 0;
          this.orderList = res.data.object;
          for (let row of res.data.object) {
            let price = 0;
            for (let item of row.order.orderItemList) {
              price += item.price * item.num;
            }

            let obj = {
              name: row.order.orderId.split("-")[0],
              date: row.order.createTime.substring(0, 10),
              price: "$ " + price,
              user: row.member.username,
            };

            if (row.order.status == "0") {
              obj.status = "UNPAID";
            } else if (row.order.status == "1") {
              obj.status = "PAID";
            } else if (row.order.status == "2") {
              obj.status = "COMPLETED";
            } else {
              obj.status = "CANCELED";
            }
            obj.index = i++;
            this.tableData.push(obj);
          }
        });
    },
    handleCancel(row) {
      let index = row.index;
      let orderId = this.orderList[index].order.orderId;
      let status = 3;

      return this.axios
        .get(this.host + "/management/order/changeOrderStatus", {
          params: {
            orderId: orderId,
            status: status,
          },
          headers: {
            Authorization: this.admin.authToken,
          },
        })
        .then((res) => {
          location.reload();
        });
    },
    handleShip(row) {
      let index = row.index;
      let orderId = this.orderList[index].order.orderId;
      let status = 2;

      return this.axios
        .get(this.host + "/management/order/changeOrderStatus", {
          params: {
            orderId: orderId,
            status: status,
          },
          headers: {
            Authorization: this.admin.authToken,
          },
        })
        .then((res) => {
          location.reload();
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
.el-button--default {
  padding:10px 15px;
}
</style>
