<template>
  <div>
    <Header />
    <div style="width: 900PX;display: inline-block; margin:50px auto;">
      <h1>Welcome {{ userDTO.username }}</h1>
      <el-tabs
        v-model="activeName"
        @tab-click="handleClick"
        :row-class-name="tableRowClassName"
      >
        <el-tab-pane label="Order History" name="order">
          <el-table :data="tableData" style="width: 100%">
            <el-table-column prop="name" label="ORDER" width="180">
            </el-table-column>
            <el-table-column prop="date" label="DATE" width="180">
            </el-table-column>
            <el-table-column prop="price" label="ORDER TOTAL"></el-table-column>
            <el-table-column prop="status" label="STATUS"></el-table-column>
            <el-table-column label="MANAGE" width="100">
              <template slot-scope="scope">
                <el-button
                  @click="handleClick(scope.row)"
                  type="text"
                  size="small"
                  disabled
                  >CANCEL</el-button
                >
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>
        <!-- <el-tab-pane label="Password" name="password">
          <div style="width:600px;">
            <el-form
              :label-position="labelPosition"
              label-width="130px"
              :model="form"
              :rules="rules"
              ref="form"
              style="text-align:left;font-family:Gotham Book; font-size:20px;line-height:20px;margin:20px auto;"
            >
              <el-form-item label="Old Password" prop="oldpassword">
                <el-input
                  v-model="form.oldpassword"
                  autocomplete="off"
                  type="password"
                ></el-input>
              </el-form-item>
              <el-form-item label="New Password" prop="newpassword">
                <el-input
                  type="password"
                  v-model="form.newpassword"
                  autocomplete="off"
                  show-password
                ></el-input>
              </el-form-item>
              <el-form-item>
                <el-button
                  type="primary"
                  @click="passwordChange('form')"
                  style="margin-left: auto;margin-right: auto;"
                  >Submit</el-button
                >
              </el-form-item>
            </el-form>
          </div>
        </el-tab-pane> -->
      </el-tabs>
    </div>

    <Footer />
  </div>
</template>

<script>
import Header from "./Header/Header";
import Footer from "./Footer/index";
export default {
  name: "Setting",
  components: {
    Header,
    Footer,
  },
  data() {
    return {
      activeName: "order",
      userDTO: null,
      host: "https://data.demostore.top",
      tableData: [],
      form: {
        oldpassword: "",
        newpassword: "",
      },
      rules: {
        oldpassword: [
          {
            required: true,
            message: "Please enter a password",
            trigger: "blur",
          },
          {
            min: 6,
            max: 16,
            message: "length between 3 to 16",
            trigger: "blur",
          },
        ],
        newpassword: [
          {
            required: true,
            message: "Please enter a password",
            trigger: "blur",
          },
          {
            min: 6,
            max: 16,
            message: "length between 3 to 16",
            trigger: "blur",
          },
        ],
      },
    };
  },
  created() {
    this.userDTO = JSON.parse(localStorage.getItem("user"));
    console.log(this.userDTO);
    if (this.userDTO == null) {
      this.$router.push("/");
    }
    this.loadOrders();
  },
  methods: {
    tableRowClassName({ row, rowIndex }) {
      if (rowIndex % 2 != 0) {
        return "success-row";
      }
      return "";
    },
    passwordChange() {
      //check password
    },
    handleClick(tab, event) {
      // console.log(tab, event);
      // this.activeName = "order";
    },

    loadOrders() {
      return this.axios
        .get(this.host + "/order/user/getOrderListByUserId", {
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
.el-table .success-row {
  background: #f0f9eb;
}
.el-button--primary{
  padding:10px 15px;
}
.el-button--primary{
  padding:10px 15px;
}
.el-button--default {
  padding:10px 15px;
}
</style>
