<template>
  <div>
    <Header></Header>
    <el-dialog
      style="width:800px;margin:0px auto;"
      title=""
      :visible.sync="dialogFormVisible"
    >
      <!-- login form-->
      <div>
        <el-form :rules="rules" :model="ruleForm">
          <h1 style="font-size:30px;color:#00B38A">Demo Store</h1>
          <el-form-item label="Username" prop="username">
            <el-input v-model="ruleForm.username" placeholder="user"></el-input>
          </el-form-item>
          <el-form-item label="Password" prop="password">
            <el-input v-model="ruleForm.password" show-password placeholder="123456"></el-input>
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
          disabled >Forgot Password?</el-link
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
    <div style="width: 1000PX;display: inline-block; margin:100px auto;">
      <el-row>
        <el-col :span="12">
          <div class="block">
            <el-carousel trigger="click">
              <el-carousel-item
                v-for="(key, src) in item.image.split(',')"
                :key="key"
              >
                <img v-bind:src="key" width="400px" height="400px" />
              </el-carousel-item>
            </el-carousel>
          </div>
        </el-col>
        <el-col :span="12">
          <div style="width:500px;">
            <h1 style="font-family:Gotham-Bold;text-align:left;">
              {{ item.title }}
            </h1>
            <p
              style="text-align:left;color:#e41818;font-family:Gotham-Bold;font-size:25px;margin: 5px 0;font-weight:600;"
            >
              ${{ item.price }}
            </p>

            <p
              style="font-family:Gotham-Book;font-size:20px;margin-top:15px;text-align:left;"
            >
              {{ item.sellPoint }}
            </p>
            <p v-if="item.num > 0" class="stock-span">In Stock</p>
            <p v-if="item.num == 0" class="stock-span">Out Of Stock</p>
          </div>

          <div style="margin-top:35px;">
            <el-form
              :inline="true"
              class="demo-form-inline"
            >
              <el-form-item>
                <el-input-number
                  v-model="num"
                  :min="1"
                  :max="10"
                  v-if="item.num > 0"
                ></el-input-number>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="onSubmit" v-if="item.num > 0"
                  >Add to Cart</el-button
                >
              </el-form-item>
            </el-form>
          </div>
        </el-col>
      </el-row>
    </div>
    <Footer />
  </div>
</template>

<script>
import Header from "./Header/Header";
import Footer from "./Footer/index";
export default {
  name: "Item",
  components: {
    Header,
    Footer,
  },
  data() {
    return {
      isLogin: false,
      cart: null,
      dialogFormVisible: false,
      host: "https://data.demostore.top",
      item: null,
      ruleForm: {
        username: "",
        password: "",
      },
      num: 1,
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
    }
    this.loadItem();
  },
  methods: {
    returnToHome() {
      this.$router.push("/");
    },

    directToLogin() {
      this.dialogFormVisible = true;
    },

    login() {
      return this.axios
        .post(this.host + "/user/login/normal", {
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

    loadItem() {
      let split = window.location.href.split("/");
      return this.axios
        .get(this.host + "/item/getItemById/" + split[split.length - 1])
        .then((res) => {
          console.log(res.data);
          this.item = res.data.object;
        })
        .catch((error) => {
          this.$message.error("cannot load item");
        });
    },
    onSubmit() {
        if (!this.isLogin) {
            this.directToLogin();
            return;
        }

        return this.axios.post(this.host + "/order/user/addItem",{
            itemId: this.item.id,
            num: this.num,
        },{headers: {
                Authorization: this.user.authToken,
            }})
        .then(res => {
            console.log(res.data);
        })
        .catch( error => {
            this.$message.error("cannot add item");
        })
    }
  },
};
</script>

<style lang="less" scoped>
@import "../assets/less/common.less";

.stock-span {
  font-family: Gotham-Bold;
  font-size: 25px;
  font-weight: bold;
  position: relative;
  top: 20px;
  text-align: left;
}
.demo-form-inline {
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
    justify-content: flex-start;
    margin-top: 10px;
}
.el-button--primary{
  padding:10px 15px;
}
.el-button--default {
  padding:10px 15px;
}
</style>
