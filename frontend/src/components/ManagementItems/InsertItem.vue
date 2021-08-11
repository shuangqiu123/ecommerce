<template>
  <div>
    <div class="top-header">
      <el-breadcrumb separator-class="el-icon-arrow-right" class="breadcrumb">
        <el-breadcrumb-item>Front Page</el-breadcrumb-item>
        <el-breadcrumb-item>Item</el-breadcrumb-item>
        <el-breadcrumb-item>Insert Item</el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <div style="width:700px;margin:50px auto;">
      <el-form :label-position="labelPosition" label-width="80px" :model="form">
        <el-form-item label="id">
          <el-input v-model="form.id"></el-input>
        </el-form-item>
        <el-form-item label="name">
          <el-input v-model="form.title"></el-input>
        </el-form-item>
        <el-form-item label="description">
          <el-input v-model="form.sellpoint"></el-input>
        </el-form-item>
        <el-form-item label="price">
          <el-input v-model="form.price"></el-input>
        </el-form-item>
        <el-form-item label="stock">
          <el-input v-model="form.num"></el-input>
        </el-form-item>
        <el-form-item label="imagel link">
          <el-input v-model="form.image"></el-input>
        </el-form-item>
      </el-form>
    </div>
    <el-button @click="submit">Submit</el-button>
  </div>
</template>

<script>
export default {
  name: "OrderList",
  components: {},
  data() {
    return {
      host: "https://data.demostore.top",
      form: {
        id: "",
        title: "",
        sellPoint: "",
        price: "",
        num: "",
        image: "",
      },
    };
  },
  created() {
    this.admin = JSON.parse(localStorage.getItem("admin"));

    this.loadOrders();
  },
  methods: {
    submit() {
      return this.axios
        .post( "/management/item/insertItem", this.form, {
          headers: {
            Authorization : this.admin.authToken
          }
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
