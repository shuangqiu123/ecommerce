<template>
  <div>
    <Header></Header>
    <el-dialog
      style="width:800px;margin:0px auto;"
      title=""
      :visible.sync="dialogFormVisible"
    >
      <!-- login form-->
      <div id="loginForm">
        <el-form :rules="rules" :model="ruleForm">
          <h1 style="font-size:30px;color:#00B38A">Demo Store</h1>
          <el-form-item label="Username" prop="username">
            <el-input v-model="ruleForm.username"></el-input>
          </el-form-item>
          <el-form-item label="Password" prop="password">
            <el-input v-model="ruleForm.password" show-password></el-input>
          </el-form-item>
        </el-form>
        <el-button
          style="width:100%;margin:0px auto;background-color: #00B38A;font-size:20px"
          type="primary"
          @click="login"
          >Sign In
        </el-button>
        <el-link
          href="/"
          :underline="false"
          style="position:relative;right:100px;top:8px;font-size: 14px;line-height: 20px;"
          disabled>Forgot Password?</el-link
        >
        <el-link
          href="/register"
          :underline="false"
          style="position:relative;left:90px;top:8px;font-size: 14px;line-height: 20px;"
          >Sign Up</el-link
        >
        <!-- <div style="color:#bdbdbd; margin-top:20px;">
          <h2 style="font-size:14px;color:#bdbdbd;">or you can sign in with</h2>
          <svg
            style="fill:currentColor;"
            viewBox="0 0 24 24"
            width="32px"
            height="32px"
            class="icon__3Su4 icon__3F7K"
          >
            <path
              fill-rule="evenodd"
              d="M12 22C6.477 22 2 17.523 2 12S6.477 2 12 2s10 4.477 10 10-4.477 10-10 10zm.044-5.213c2.445 0 4.267-1.551 4.556-3.781v-1.891h-4.519v1.89h2.602a2.893 2.893 0 0 1-2.724 1.93c-1.194 0-2.677-1.1-2.677-2.843 0-1.621 1.161-2.876 2.677-2.876.739 0 1.413.279 1.922.736l1.399-1.376a4.744 4.744 0 1 0-3.236 8.212z"
            ></path>
          </svg>
        </div> -->
      </div>
    </el-dialog>
    <div>
      <div style="width: 850PX;display: inline-block; margin:0px auto;">
        <div class="cart-container" v-if="!isLogin">
          <h1
            style="font-weight: 500;line-height: 1.2;font-family:Gotham Bold;margin-bottom:25px;"
          >
            Your Cart
          </h1>
          <h2
            style="color:#69727b;font-family:Gotham Book;font-size:16px;margin-bottom:25px;"
          >
            Please Log in first to see you cart!
          </h2>
          <el-button @click="directToLogin">Log In</el-button>
        </div>

        <div
          class="cart-container"
          v-if="isLogin && cart.orderItemList.length == 0"
        >
          <h1
            style="font-weight: 500;line-height: 1.2;font-family:Gotham Bold;margin-bottom:25px;"
          >
            Your Cart
          </h1>
          <h2
            style="color:#69727b;font-family:Gotham Book;font-size:16px;margin-bottom:25px;"
          >
            Opps, Your Cart is Empty.
          </h2>
          <el-button @click="returnToHome">Continue Shopping</el-button>
        </div>

        <div
          class="cart-container"
          v-if="isLogin && cart.orderItemList.length > 0"
        >
          <h1
            style="font-weight: 500;line-height: 1.2;font-family:Gotham Bold;margin-bottom:25px;"
          >
            Your Cart
          </h1>
          <table style="background:#f8f8f8;width:100%;">
            <thead style="border-bottom:1px solid #e8e9eb;color:#3d4246;">
              <tr>
                <th scope="col" style="text-align:left;">Product</th>
                <th class="text-right" scope="col">Price</th>
                <th class="text-right" scope="col">Quantity</th>
                <th class="text-right" scope="col">Total</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(item, index) in cart.orderItemList" :key="index">
                <td>
                  <div class="cart-product-info">
                    <div class="cart-image-wrapper">
                      <img
                        class="cart-image"
                        :src="item.picPath.split(',')[0]"
                        style="vertical-align: middle;"
                      />
                    </div>
                    <div style="display:flex;flex-direction:column;">
                      <div class=".item-title" style="width:100%;">
                        <el-link>{{ item.title }}</el-link>
                      </div>
                      <div style="margin-top:30px;">
                        <el-link @click="removeItem(index)">Remove</el-link>
                      </div>
                    </div>
                  </div>
                </td>
                <td class="td-display">$ {{ item.price }}</td>
                <td class="td-display">
                  <el-input-number
                    v-model="cart.orderItemList[index].num"
                    controls-position="right"
                    @change="handleChange(index)"
                    :min="1"
                    :max="10"
                    size="small"
                  ></el-input-number>
                </td>
                <td class="td-display money">${{ item.totalFee }}</td>
              </tr>
            </tbody>
          </table>
          <div style="margin-top:15px;"><p class="money subtotal">Subtotal: $ {{totalPrice}}</p></div>
          <div style="margin-top:15px;">
            <el-button style="float:right;" @click="goToCheckout">Check Out</el-button>
          </div>
        </div>
      </div>
    </div>

    <Footer />
  </div>
</template>

<script>
import Header from "./Header/Header";
import Footer from "./Footer/index";
export default {
  name: "Cart",
  components: {
    Header,
    Footer,
  },
  data() {
    return {
      isLogin: false,
      cart: {
        orderItemList: [],
      },
      dialogFormVisible: false,
      host: "https://data.demostore.top",
      cartLoading: true,
      ruleForm: {
        username: "",
        password: "",
      },
      totalPrice: 0,
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
    this.user = JSON.parse(localStorage.getItem("user"));
    if (this.user != null) {
      this.isLogin = true;
      this.loadCart();
    } else {
      this.cartLoading = false;
    }
  },
  methods: {

    loadCart() {
      return this.axios
        .get( "/order/user/getCartByUserId", {
          headers: {
            Authorization: this.user.authToken,
          },
        })
        .then((res) => {
          console.log(res.data);
          this.cart = res.data.object;

          for (let item of this.cart.orderItemList) {
            this.totalPrice += item.totalFee;
          }
        })
    },

    returnToHome() {
      this.$router.push("/");
    },

    directToLogin() {
      this.dialogFormVisible = true;
    },
    removeItem(index) {
      let orderItem = this.cart.orderItemList[index];
      return this.axios.post( '/order/user/removeItem', orderItem, {
          headers: {
            Authorization: this.user.authToken,
          }})
          .then(res=>{
            location.reload();
          })
    },

    handleChange(index) {
      let orderItem = this.cart.orderItemList[index];
      return this.axios.post( '/order/user/updateItem', orderItem, {
          headers: {
            Authorization: this.user.authToken,
          }})
          .then(res=>{
            location.reload();
          })
    },
    goToCheckout() {
      this.$router.push("/checkout");
    },
    login() {
      return this.axios
        .post( "/user/login/normal", {
          username: this.ruleForm.username,
          password: this.ruleForm.password,
        })
        .then((result) => {
          this.userDTO = result.data.object;
          if (this.userDTO) {
            this.dialogFormVisible = false;
            this.isLogin = true;
            this.userDTO.authToken = "Bearer " + this.userDTO.authToken;
            localStorage.setItem("user", JSON.stringify(this.userDTO));
          } else {
            this.$message.error("Username and password unmatched");
          }
        })
        .catch((error) => {
          this.$message.error("login error!");
        });
    },
  },
};
</script>

<style lang="less" scoped>
@import "../assets/less/common.less";
.money {
  color: #003cff;
  font-weight: 600;
}

.subtotal {
  font-size: 25px;
  text-align: right;
}
.td-display {
  text-align: right;
}

.item-title {
  color: #3d4246;
  font-size: 1.125em;
  min-width: 100px;
}

.cart-product-info {
  display: flex;
}
.cart-image-wrapper {
  padding-right: 10px;
  flex: 7rem 0 0;
}
.cart-image {
  max-height: 95px;
  margin: 0 auto;
}
td {
  padding: 22px 22px;
  vertical-align: middle;
  text-align: left;
}

tr th {
  border-bottom: 1px solid #e8e9eb;
  padding: 10px 20px;
}
.cart-container {
  margin-top: 100px;
}
.text-right {
  text-align: right !important;
}
.el-button--primary{
  padding:10px 15px;
}
.el-button--default {
  padding:10px 15px;
}
</style>
