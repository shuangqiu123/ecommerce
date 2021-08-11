<template>
  <div>
    <Header />
    <div
      style="width: 900PX;display: inline-block; margin:50px auto;"
      v-loading="loading"
    >
      <el-col :span="14" style="padding-right: 40px;">
        <div>
          <h1
            style="text-align:left;position:relative;left:80px;font-size:20px;margin: 10px 0;"
          >
            Shipping Address
          </h1>
          <el-form ref="form" :model="form" label-width="80px" :rules="rules">
            <el-form-item prop="firstname">
              <el-input
                v-model="form.firstname"
                placeholder="First Name"
              ></el-input>
            </el-form-item>
            <el-form-item prop="secondname">
              <el-input
                v-model="form.secondname"
                placeholder="Second Name"
              ></el-input>
            </el-form-item>
            <el-form-item prop="address">
              <el-input v-model="form.address" placeholder="Address"></el-input>
            </el-form-item>
          </el-form>
        </div>

        <div>
          <h1
            style="text-align:left;position:relative;left:80px;font-size:20px;margin: 10px 0;"
          >
            Payment
          </h1>
          <el-button
            style="float:left;position:relative;left:80px;"
            @click="pay"
          >
            <div>
              <img
                src="data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iMjQiIGhlaWdodD0iMzIiIHZpZXdCb3g9IjAgMCAyNCAzMiIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiBwcmVzZXJ2ZUFzcGVjdFJhdGlvPSJ4TWluWU1pbiBtZWV0Ij4KICAgIDxwYXRoIGZpbGw9IiMwMDljZGUiIGQ9Ik0gMjAuOTA1IDkuNSBDIDIxLjE4NSA3LjQgMjAuOTA1IDYgMTkuNzgyIDQuNyBDIDE4LjU2NCAzLjMgMTYuNDExIDIuNiAxMy42OTcgMi42IEwgNS43MzkgMi42IEMgNS4yNzEgMi42IDQuNzEgMy4xIDQuNjE1IDMuNiBMIDEuMzM5IDI1LjggQyAxLjMzOSAyNi4yIDEuNjIgMjYuNyAyLjA4OCAyNi43IEwgNi45NTYgMjYuNyBMIDYuNjc1IDI4LjkgQyA2LjU4MSAyOS4zIDYuODYyIDI5LjYgNy4yMzYgMjkuNiBMIDExLjM1NiAyOS42IEMgMTEuODI1IDI5LjYgMTIuMjkyIDI5LjMgMTIuMzg2IDI4LjggTCAxMi4zODYgMjguNSBMIDEzLjIyOCAyMy4zIEwgMTMuMjI4IDIzLjEgQyAxMy4zMjIgMjIuNiAxMy43OSAyMi4yIDE0LjI1OCAyMi4yIEwgMTQuODIxIDIyLjIgQyAxOC44NDUgMjIuMiAyMS45MzUgMjAuNSAyMi44NzEgMTUuNSBDIDIzLjMzOSAxMy40IDIzLjE1MyAxMS43IDIyLjAyOSAxMC41IEMgMjEuNzQ4IDEwLjEgMjEuMjc5IDkuOCAyMC45MDUgOS41IEwgMjAuOTA1IDkuNSI+PC9wYXRoPgogICAgPHBhdGggZmlsbD0iIzAxMjE2OSIgZD0iTSAyMC45MDUgOS41IEMgMjEuMTg1IDcuNCAyMC45MDUgNiAxOS43ODIgNC43IEMgMTguNTY0IDMuMyAxNi40MTEgMi42IDEzLjY5NyAyLjYgTCA1LjczOSAyLjYgQyA1LjI3MSAyLjYgNC43MSAzLjEgNC42MTUgMy42IEwgMS4zMzkgMjUuOCBDIDEuMzM5IDI2LjIgMS42MiAyNi43IDIuMDg4IDI2LjcgTCA2Ljk1NiAyNi43IEwgOC4yNjcgMTguNCBMIDguMTczIDE4LjcgQyA4LjI2NyAxOC4xIDguNzM1IDE3LjcgOS4yOTYgMTcuNyBMIDExLjYzNiAxNy43IEMgMTYuMjI0IDE3LjcgMTkuNzgyIDE1LjcgMjAuOTA1IDEwLjEgQyAyMC44MTIgOS44IDIwLjkwNSA5LjcgMjAuOTA1IDkuNSI+PC9wYXRoPgogICAgPHBhdGggZmlsbD0iIzAwMzA4NyIgZD0iTSA5LjQ4NSA5LjUgQyA5LjU3NyA5LjIgOS43NjUgOC45IDEwLjA0NiA4LjcgQyAxMC4yMzIgOC43IDEwLjMyNiA4LjYgMTAuNTEzIDguNiBMIDE2LjY5MiA4LjYgQyAxNy40NDIgOC42IDE4LjE4OSA4LjcgMTguNzUzIDguOCBDIDE4LjkzOSA4LjggMTkuMTI3IDguOCAxOS4zMTQgOC45IEMgMTkuNTAxIDkgMTkuNjg4IDkgMTkuNzgyIDkuMSBDIDE5Ljg3NSA5LjEgMTkuOTY4IDkuMSAyMC4wNjMgOS4xIEMgMjAuMzQzIDkuMiAyMC42MjQgOS40IDIwLjkwNSA5LjUgQyAyMS4xODUgNy40IDIwLjkwNSA2IDE5Ljc4MiA0LjYgQyAxOC42NTggMy4yIDE2LjUwNiAyLjYgMTMuNzkgMi42IEwgNS43MzkgMi42IEMgNS4yNzEgMi42IDQuNzEgMyA0LjYxNSAzLjYgTCAxLjMzOSAyNS44IEMgMS4zMzkgMjYuMiAxLjYyIDI2LjcgMi4wODggMjYuNyBMIDYuOTU2IDI2LjcgTCA4LjI2NyAxOC40IEwgOS40ODUgOS41IFoiPjwvcGF0aD4KPC9zdmc+Cg=="
                aria-label="pp"
                height="24px"
                width="18"
              />
              <img
                src="data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iMTAwIiBoZWlnaHQ9IjMyIiB2aWV3Qm94PSIwIDAgMTAwIDMyIiB4bWxucz0iaHR0cDomI3gyRjsmI3gyRjt3d3cudzMub3JnJiN4MkY7MjAwMCYjeDJGO3N2ZyIgcHJlc2VydmVBc3BlY3RSYXRpbz0ieE1pbllNaW4gbWVldCI+PHBhdGggZmlsbD0iIzAwMzA4NyIgZD0iTSAxMiA0LjkxNyBMIDQuMiA0LjkxNyBDIDMuNyA0LjkxNyAzLjIgNS4zMTcgMy4xIDUuODE3IEwgMCAyNS44MTcgQyAtMC4xIDI2LjIxNyAwLjIgMjYuNTE3IDAuNiAyNi41MTcgTCA0LjMgMjYuNTE3IEMgNC44IDI2LjUxNyA1LjMgMjYuMTE3IDUuNCAyNS42MTcgTCA2LjIgMjAuMjE3IEMgNi4zIDE5LjcxNyA2LjcgMTkuMzE3IDcuMyAxOS4zMTcgTCA5LjggMTkuMzE3IEMgMTQuOSAxOS4zMTcgMTcuOSAxNi44MTcgMTguNyAxMS45MTcgQyAxOSA5LjgxNyAxOC43IDguMTE3IDE3LjcgNi45MTcgQyAxNi42IDUuNjE3IDE0LjYgNC45MTcgMTIgNC45MTcgWiBNIDEyLjkgMTIuMjE3IEMgMTIuNSAxNS4wMTcgMTAuMyAxNS4wMTcgOC4zIDE1LjAxNyBMIDcuMSAxNS4wMTcgTCA3LjkgOS44MTcgQyA3LjkgOS41MTcgOC4yIDkuMzE3IDguNSA5LjMxNyBMIDkgOS4zMTcgQyAxMC40IDkuMzE3IDExLjcgOS4zMTcgMTIuNCAxMC4xMTcgQyAxMi45IDEwLjUxNyAxMy4xIDExLjIxNyAxMi45IDEyLjIxNyBaIj48L3BhdGg+PHBhdGggZmlsbD0iIzAwMzA4NyIgZD0iTSAzNS4yIDEyLjExNyBMIDMxLjUgMTIuMTE3IEMgMzEuMiAxMi4xMTcgMzAuOSAxMi4zMTcgMzAuOSAxMi42MTcgTCAzMC43IDEzLjYxNyBMIDMwLjQgMTMuMjE3IEMgMjkuNiAxMi4wMTcgMjcuOCAxMS42MTcgMjYgMTEuNjE3IEMgMjEuOSAxMS42MTcgMTguNCAxNC43MTcgMTcuNyAxOS4xMTcgQyAxNy4zIDIxLjMxNyAxNy44IDIzLjQxNyAxOS4xIDI0LjgxNyBDIDIwLjIgMjYuMTE3IDIxLjkgMjYuNzE3IDIzLjggMjYuNzE3IEMgMjcuMSAyNi43MTcgMjkgMjQuNjE3IDI5IDI0LjYxNyBMIDI4LjggMjUuNjE3IEMgMjguNyAyNi4wMTcgMjkgMjYuNDE3IDI5LjQgMjYuNDE3IEwgMzIuOCAyNi40MTcgQyAzMy4zIDI2LjQxNyAzMy44IDI2LjAxNyAzMy45IDI1LjUxNyBMIDM1LjkgMTIuNzE3IEMgMzYgMTIuNTE3IDM1LjYgMTIuMTE3IDM1LjIgMTIuMTE3IFogTSAzMC4xIDE5LjMxNyBDIDI5LjcgMjEuNDE3IDI4LjEgMjIuOTE3IDI1LjkgMjIuOTE3IEMgMjQuOCAyMi45MTcgMjQgMjIuNjE3IDIzLjQgMjEuOTE3IEMgMjIuOCAyMS4yMTcgMjIuNiAyMC4zMTcgMjIuOCAxOS4zMTcgQyAyMy4xIDE3LjIxNyAyNC45IDE1LjcxNyAyNyAxNS43MTcgQyAyOC4xIDE1LjcxNyAyOC45IDE2LjExNyAyOS41IDE2LjcxNyBDIDMwIDE3LjQxNyAzMC4yIDE4LjMxNyAzMC4xIDE5LjMxNyBaIj48L3BhdGg+PHBhdGggZmlsbD0iIzAwMzA4NyIgZD0iTSA1NS4xIDEyLjExNyBMIDUxLjQgMTIuMTE3IEMgNTEgMTIuMTE3IDUwLjcgMTIuMzE3IDUwLjUgMTIuNjE3IEwgNDUuMyAyMC4yMTcgTCA0My4xIDEyLjkxNyBDIDQzIDEyLjQxNyA0Mi41IDEyLjExNyA0Mi4xIDEyLjExNyBMIDM4LjQgMTIuMTE3IEMgMzggMTIuMTE3IDM3LjYgMTIuNTE3IDM3LjggMTMuMDE3IEwgNDEuOSAyNS4xMTcgTCAzOCAzMC41MTcgQyAzNy43IDMwLjkxNyAzOCAzMS41MTcgMzguNSAzMS41MTcgTCA0Mi4yIDMxLjUxNyBDIDQyLjYgMzEuNTE3IDQyLjkgMzEuMzE3IDQzLjEgMzEuMDE3IEwgNTUuNiAxMy4wMTcgQyA1NS45IDEyLjcxNyA1NS42IDEyLjExNyA1NS4xIDEyLjExNyBaIj48L3BhdGg+PHBhdGggZmlsbD0iIzAwOWNkZSIgZD0iTSA2Ny41IDQuOTE3IEwgNTkuNyA0LjkxNyBDIDU5LjIgNC45MTcgNTguNyA1LjMxNyA1OC42IDUuODE3IEwgNTUuNSAyNS43MTcgQyA1NS40IDI2LjExNyA1NS43IDI2LjQxNyA1Ni4xIDI2LjQxNyBMIDYwLjEgMjYuNDE3IEMgNjAuNSAyNi40MTcgNjAuOCAyNi4xMTcgNjAuOCAyNS44MTcgTCA2MS43IDIwLjExNyBDIDYxLjggMTkuNjE3IDYyLjIgMTkuMjE3IDYyLjggMTkuMjE3IEwgNjUuMyAxOS4yMTcgQyA3MC40IDE5LjIxNyA3My40IDE2LjcxNyA3NC4yIDExLjgxNyBDIDc0LjUgOS43MTcgNzQuMiA4LjAxNyA3My4yIDYuODE3IEMgNzIgNS42MTcgNzAuMSA0LjkxNyA2Ny41IDQuOTE3IFogTSA2OC40IDEyLjIxNyBDIDY4IDE1LjAxNyA2NS44IDE1LjAxNyA2My44IDE1LjAxNyBMIDYyLjYgMTUuMDE3IEwgNjMuNCA5LjgxNyBDIDYzLjQgOS41MTcgNjMuNyA5LjMxNyA2NCA5LjMxNyBMIDY0LjUgOS4zMTcgQyA2NS45IDkuMzE3IDY3LjIgOS4zMTcgNjcuOSAxMC4xMTcgQyA2OC40IDEwLjUxNyA2OC41IDExLjIxNyA2OC40IDEyLjIxNyBaIj48L3BhdGg+PHBhdGggZmlsbD0iIzAwOWNkZSIgZD0iTSA5MC43IDEyLjExNyBMIDg3IDEyLjExNyBDIDg2LjcgMTIuMTE3IDg2LjQgMTIuMzE3IDg2LjQgMTIuNjE3IEwgODYuMiAxMy42MTcgTCA4NS45IDEzLjIxNyBDIDg1LjEgMTIuMDE3IDgzLjMgMTEuNjE3IDgxLjUgMTEuNjE3IEMgNzcuNCAxMS42MTcgNzMuOSAxNC43MTcgNzMuMiAxOS4xMTcgQyA3Mi44IDIxLjMxNyA3My4zIDIzLjQxNyA3NC42IDI0LjgxNyBDIDc1LjcgMjYuMTE3IDc3LjQgMjYuNzE3IDc5LjMgMjYuNzE3IEMgODIuNiAyNi43MTcgODQuNSAyNC42MTcgODQuNSAyNC42MTcgTCA4NC4zIDI1LjYxNyBDIDg0LjIgMjYuMDE3IDg0LjUgMjYuNDE3IDg0LjkgMjYuNDE3IEwgODguMyAyNi40MTcgQyA4OC44IDI2LjQxNyA4OS4zIDI2LjAxNyA4OS40IDI1LjUxNyBMIDkxLjQgMTIuNzE3IEMgOTEuNCAxMi41MTcgOTEuMSAxMi4xMTcgOTAuNyAxMi4xMTcgWiBNIDg1LjUgMTkuMzE3IEMgODUuMSAyMS40MTcgODMuNSAyMi45MTcgODEuMyAyMi45MTcgQyA4MC4yIDIyLjkxNyA3OS40IDIyLjYxNyA3OC44IDIxLjkxNyBDIDc4LjIgMjEuMjE3IDc4IDIwLjMxNyA3OC4yIDE5LjMxNyBDIDc4LjUgMTcuMjE3IDgwLjMgMTUuNzE3IDgyLjQgMTUuNzE3IEMgODMuNSAxNS43MTcgODQuMyAxNi4xMTcgODQuOSAxNi43MTcgQyA4NS41IDE3LjQxNyA4NS43IDE4LjMxNyA4NS41IDE5LjMxNyBaIj48L3BhdGg+PHBhdGggZmlsbD0iIzAwOWNkZSIgZD0iTSA5NS4xIDUuNDE3IEwgOTEuOSAyNS43MTcgQyA5MS44IDI2LjExNyA5Mi4xIDI2LjQxNyA5Mi41IDI2LjQxNyBMIDk1LjcgMjYuNDE3IEMgOTYuMiAyNi40MTcgOTYuNyAyNi4wMTcgOTYuOCAyNS41MTcgTCAxMDAgNS42MTcgQyAxMDAuMSA1LjIxNyA5OS44IDQuOTE3IDk5LjQgNC45MTcgTCA5NS44IDQuOTE3IEMgOTUuNCA0LjkxNyA5NS4yIDUuMTE3IDk1LjEgNS40MTcgWiI+PC9wYXRoPjwvc3ZnPg=="
                aria-label="paypal"
                height="24px"
                width="75px"
              />
            </div>
          </el-button>
          <p style="position:relative;left:15px;">Username: sample@123.com</p>
          <p>Password: 123456789</p>
        </div>
      </el-col>

      <el-col :span="10" style="border-left:1px solid #dee2e6;">
        <div style="padding-left:30px;">
          <div class="item-display">
            <table
              style="width:100%;border-bottom:1px solid #dee2e6 !important;"
            >
              <thead style="border-bottom:1px solid #e8e9eb;color:#3d4246;">
                <tr>
                  <th scope="col" style="text-align:left;" hidden>Product</th>
                  <th class="text-right" scope="col" hidden>Price</th>
                  <th class="text-right" scope="col" hidden>Quantity</th>
                  <th class="text-right" scope="col" hidden>Total</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="(item, index) in cart.orderItemList" :key="index">
                  <td>
                    <div class="cart-product-info">
                      <div class="cart-image-wrapper">
                        <el-badge :value="item.num" type="primary">
                          <img
                            class="cart-image"
                            :src="item.picPath.split(',')[0]"
                            style="vertical-align: middle;"
                          />
                        </el-badge>
                      </div>
                      <div style="display:flex;flex-direction:column;">
                        <div class=".item-title" style="width:100%;">
                          <el-link :underline=false>{{ item.title }}</el-link>
                        </div>
                      </div>
                    </div>
                  </td>
                  <td class="td-display money">${{ item.totalFee }}</td>
                </tr>
              </tbody>
            </table>

            <div style="margin-top:15px;">
              <span style="float:left;color:#323232;">Total</span>
              <span
                style="float:right;font-size:25px;font-weight:bold;letter-spacing: -0.04em;line-height: 1em;font-family:Gotham Book;"
                >$ {{ totalPrice }}</span
              >
            </div>
          </div>
        </div>
      </el-col>
    </div>
    <Footer />
  </div>
</template>

<script>
import Header from "./Header/Header";
import Footer from "./Footer/index";
export default {
  name: "Checkout",
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
      form: {
        firstname: "",
        secondname: "",
        address: "",
      },
      totalPrice: 0,
      loading: false,
      rules: {
        firstname: [
          {
            required: true,
            message: "required",
            trigger: "blur",
          },
        ],
        secondname: [
          {
            required: true,
            message: "required",
            trigger: "blur",
          },
        ],
        address: [
          {
            required: true,
            message: "required",
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
        .catch((error) => {
          this.$message.error("cannot load cart");
        });
    },

    returnToHome() {
      this.$router.push("/");
    },

    directToLogin() {
      this.dialogFormVisible = true;
    },

    pay() {
      this.$refs["form"].validate((valid) => {
        if (!valid) {
          return;
        }

        return this.axios
          .get( "/order/user/addAddress", {
            headers: {
              Authorization: this.user.authToken,
            },
            params: {
              orderId: this.cart.orderId,
              name: this.form.firstname + " " + this.form.secondname,
              address: this.form.address,
            },
          })
          .then((_) => {
            return this.axios
              .post(
                 "/payment/paypal/init",
                {
                  payerAmount: this.totalPrice,
                  orderId: this.cart.orderId,
                  payerUid: this.cart.userId,
                },
                {
                  headers: {
                    Authorization: this.user.authToken,
                  },
                }
              )
              .then((res) => {
                this.loading = true;
                console.log(res.data);
                this.openWindow(res.data.object);
                setTimeout(()=>{
                  this.repeatRequest();
                }, 10000);
              })
              .catch((error) => {
                this.$message.error("cannot link to paypal");
              });
          })
          .catch((error) => {
            this.$message.error("cannot add shipping address");
          });
      });
    },

    openWindow(url) {
      const iHeight = 600;
      const iWidth = 500;
      const iTop = (window.screen.height - 30 - iHeight) / 2;
      const iLeft = (window.screen.width - 10 - iWidth) / 2;
      window.open(
        url,
        "123",
        "height=" +
          iHeight +
          ",innerHeight=" +
          iHeight +
          ",width=" +
          iWidth +
          ",innerWidth=" +
          iWidth +
          ",top=" +
          iTop +
          ",left=" +
          iLeft +
          ",toolbar=no,menubar=no,scrollbars=auto,resizeable=no,location=no,status=no"
      );
    },

    repeatRequest() {
      let timer = setInterval(() => {
        this.sendRequest(timer);
      }, 5000);
    },
    sendRequest(timer) {
      setTimeout(() => {
        this.axios
          .get( "/payment/checkPayment", {
            headers: {
              Authorization: this.user.authToken,
            },
            params: {
              orderId: this.cart.orderId,
            },
          })
          .then((res) => {
            console.log(res.data);
            if (res.data.message == "success") {
              clearInterval(timer);
              this.$router.push("/setting");
            }
          });
      }, 0);
    },
  },
};
</script>

<style lang="less" scoped>
@import "../assets/less/common.less";
.money {
  font-weight: 400;
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
  flex: 3rem 0 0;
}
.cart-image {
  max-height: 40px;
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
