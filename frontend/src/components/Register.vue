<template>
  <div>
    <Header></Header>
    <div style="width: 600px;display: inline-block; margin:40px auto;">
      <h1 style="font-family:Gotham Bold; font-size:50px;">Create Account</h1>

      <el-form
        :label-position="labelPosition"
        label-width="80px"
        :model="form"
        :rules="rules"
        ref="form"
        style="text-align:left;font-family:Gotham Book; font-size:20px;line-height:20px;margin-top:20px;"
      >
        <el-form-item label="UserName" prop="username">
          <el-input v-model="form.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item
          label="Email"
          prop="email"
          :rules="[
            {
              required: true,
              message: 'please enter an email',
              trigger: 'blur',
            },
            {
              type: 'email',
              message: 'please enter email in correct format',
              trigger: ['blur', 'change'],
            },
          ]"
        >
          <el-input v-model="form.email" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="Password" prop="password">
          <el-input
            type="password"
            v-model="form.password"
            autocomplete="off"
            show-password
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            @click="submitForm('form')"
            style="margin-left: auto;margin-right: auto;"
            >Submit</el-button
          >
        </el-form-item>
      </el-form>
    </div>
    <Footer />
  </div>
</template>

<script>
import Header from "./Header/Header";
import Footer from "./Footer/index";

export default {
  name: "Register",
  components: {
    Header,
    Footer,
  },
  data() {
    return {
      isLogin: false, //login status
      form: {
        username: "",
        email: "",
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
      labelPosition: "top",
      host: "https://data.demostore.top",
      userDTO: null,
    };
  },
  created() {
    this.user = JSON.parse(localStorage.getItem("user"));
    if (this.user != null) {
      this.isLogin = true;
    }
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          return this.axios
            .post( "/user/register/normal", this.form)
            .then((result) => {
              console.log(result.data);

              this.userDTO = result.data.object;
              if (this.userDTO) {
                this.isLogin = true;
                this.userDTO.authToken = "Bearer " + this.userDTO.authToken;
                localStorage.setItem("user", JSON.stringify(this.userDTO));
                this.$router.push('/');
              } else {
                this.$message.error("Registration error");
              }
            })
            .catch((error) => {
              this.$message.error("Registration error");
            });
        } else {
          return false;
        }
      });
    },
  },
};
</script>

<style lang="less" scoped>
.el-button--primary{
  padding:10px 15px;
}
.el-button--default {
  padding:10px 15px;
}
</style>
