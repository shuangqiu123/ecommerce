import Vue from 'vue'
import VueRouter from 'vue-router'
import Vuex from 'vuex'
import Index from '../components/Index.vue'
import Register from '../components/Register.vue'
import Cart from '../components/Cart.vue'
import Item from '../components/Item.vue'
import Setting from '../components/setting.vue'
import Checkout from '../components/Checkout.vue'
import Management from '../components/Management.vue'
import OrderList from '../components/ManagementItems/OrderList.vue'
import ItemList from '../components/ManagementItems/ItemList.vue'
import InsertItem from '../components/ManagementItems/InsertItem.vue'
import Advertisement from '../components/ManagementItems/Advertisement.vue'


Vue.use(VueRouter)
Vue.use(Vuex)


const routes = [
  {
    path: '/',
    name: 'Index',
    component: Index,
    meta: {
      title: 'front'
    }
  },
  {
    path: '/register',
    name: 'Register',
    component: Register,
    meta: {
      title: 'register'
    }
  },
  {
    path: '/cart',
    name: 'Cart',
    component: Cart,
    meta: {
      title: 'cart'
    }
  },
  {
    path: '/item/*',
    name: 'Item',
    component: Item,
    meta: {
      title: 'item'
    }
  },
  {
    path: '/checkout',
    name: 'Checkout',
    component: Checkout,
    meta: {
      title: 'Checkout'
    }
  },
  {
    path: '/setting',
    name: 'setting',
    component: Setting,
    meta: {
      title: 'setting'
    }
  },
  {
    path: '/management',
    name: 'management',
    component: Management,
    children: [{
      path: '/orderlist',
      name: 'OrderList',
      component: OrderList,
      meta: {
        title: 'OrderList'
      }
    },
    {
      path: '/itemlist',
      name: 'ItemList',
      component: ItemList,
      meta: {
        title: 'ItemList'
      }
    },
    {
      path: '/insertItem',
      name: 'InsertItem',
      component: InsertItem,
      meta: {
        title: 'InsertItem'
      }
    },
    {
      path: '/advertisement',
      name: 'Advertisement',
      component: Advertisement,
      meta: {
        title: 'Advertisement'
      }
    }
    ],
    meta: {
      title: 'management'
    }
  }]

const router = new VueRouter({
  mode: 'history',
  routes
})

export default router

