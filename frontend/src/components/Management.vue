<template>
  <div v-if="login">
    <el-menu
      mode="horizontal"
      :show-timeout="200"
      :collapse="isCollapse"
      background-color="#304156"
      text-color="#bfcbd9"
      active-text-color="#409EFF"
      router
    >
      <el-menu-item index="/">
        <i class="el-icon-s-home"></i>Front Page</el-menu-item
      >
      <el-submenu index="2">
        <template slot="title">
          <i class="el-icon-s-order"></i>
          Order Management
        </template>
        <el-menu-item index="/orderList">
          <i class="el-icon-menu"></i>Order List</el-menu-item
        >
        <!-- <el-menu-item index="2-2">
          <i class="el-icon-setting"></i>Order Setting</el-menu-item
        >
        <el-menu-item index="2-3">
          <i class="el-icon-close"></i>Order Cancel</el-menu-item
        > -->
      </el-submenu>
      <el-submenu index="3">
        <template slot="title"
          ><i class="el-icon-tickets"></i>Item Management</template
        >
        <el-menu-item index="/itemList">
          <i class="el-icon-menu"></i>Item List</el-menu-item
        >
        <el-menu-item index="/insertItem">
          <i class="el-icon-plus"></i>Insert Item</el-menu-item
        >
      </el-submenu>
      <el-submenu index="4">
        <template slot="title"
          ><i class="el-icon-shopping-bag-1"></i>Sale</template
        >
        <el-menu-item index="/advertisement" disabled>
          <i class="el-icon-reading"></i>Advertisement</el-menu-item
        >
      </el-submenu>
    </el-menu>
    <el-dialog
      style="width:800px;margin:0px auto;"
      :visible.sync="dialogFormVisible"
      :show-close=false
      :close-on-click-modal=false
      :close-on-press-escape=false
    >
      <div id="loginForm">
        <el-form :rules="rules" :model="ruleForm" >
          <h1 style="font-size:20px;color:#00B38A">
            Demo Store Management System
          </h1>
          <el-form-item label="Admin name" prop="username">
            <el-input v-model="ruleForm.username" placeholder="user"></el-input>
          </el-form-item>
          <el-form-item label="Password" prop="password">
            <el-input
              v-model="ruleForm.password"
              show-password
              placeholder="123456"
            ></el-input>
          </el-form-item>
        </el-form>
        <el-button
          style="width:100%;margin:0px auto;background-color: #00B38A;font-size:20px"
          type="primary"
          @click="adminlogin"
          >Sign In
        </el-button>
      </div>
    </el-dialog>
    <router-view></router-view>
    <Footer />
  </div>
</template>

<script>
import Header from "./Header/Header";
import Footer from "./Footer/index";
export default {
  name: "Management",
  components: {
    Header,
    Footer,
  },
  data() {
    return {
      activeName: "order",
      admin: null,
      host: "https://data.demostore.top",
      tableData: [],
      map: {
        "2": "Order",
        "3": "Item",
        "4": "Sale",
        "2-1": "Order List",
        "2-2": "Order Setting",
        "2-3": "Order Cancel",
        "3-1": "Item List",
        "3-2": "Insert Item",
        "4-1": "Advertisement",
      },
      login: true,
      dialogFormVisible: false,
      ruleForm: {
        username: "",
        password: "",
      },
      rules: {
        username: [
          {
            required: true,
            message: "Please enter an username",
            trigger: "blur",
          },
        ],
        password: [
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
    this.admin = JSON.parse(localStorage.getItem("admin"));

    if (this.admin == null) {
      this.dialogFormVisible = true;
    }
  },
  methods: {
    handleSelect(key, keyPath) {
      console.log(this.map[key]);
      this.pos = [this.map[key], this.map[keyPath]];
      console.log(key, keyPath);
    },
    adminlogin() {
      return this.axios
        .post(this.host + "/user/login/normal", {
          username: this.ruleForm.username,
          password: this.ruleForm.password,
        })
        .then((result) => {
          if (!result.data.object || result.data.object.id != 5) {
            this.$message.error("invalid account");
            return;
          } 
          this.admin = result.data.object;
          if (this.admin) {
            this.dialogFormVisible = false;
            this.admin.authToken = "Bearer " + this.admin.authToken;
            localStorage.setItem("admin", JSON.stringify(this.admin));
          } else {
            this.$message.error("Username and password unmatched");
          }
        })
        .catch((error) => {
          this.$message.error("login error!");
        });
    }
  },
};
</script>

<style scoped>
#top-header {
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
