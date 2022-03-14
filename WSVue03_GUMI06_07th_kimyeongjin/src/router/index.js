import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/HomeView.vue'
import BookView from '../views/BookView.vue'
import AboutView from '../views/AboutView.vue'
import BookCreate from '../components/book/BookCreate'
import BookDetail from '../components/book/BookDetail'
import BookModify from '../components/book/BookModify'
import BookList from '../components/book/BookList'
Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/create',
    name: 'create',
    component: BookCreate
  },
  {
    path: '/',
    name: 'detail',
    component: BookDetail
  },
  {
    path: '/',
    name: 'modify',
    component: BookModify
  },
  {
    path: '/about',
    name: 'about',
    component: AboutView
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    // component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  },
  {
    name: "/book",
    path: "/book",
    component: BookView,
    //중첩 route 설정
    children: [
      {
        path: "/list",
        name: "/book-list",
        // component: () => import("@/components/book/BookList.vue")
        component: BookList
      },
    ]
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
