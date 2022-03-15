import Vue from "vue";
import VueRouter from "vue-router";
import HomeView from "../views/HomeView.vue";
import BookView from "../views/BookView.vue";

Vue.use(VueRouter);

const routes = [
    {
        path: "/",
        name: "home",
        component: HomeView,
    },
    {
        path: "/about",
        name: "about",
        component: () => import("@/views/AboutView.vue"),
    },
    {
        path: "/book",
        name: "book",
        component: BookView,
        redirect: "/book",
        children: [
            {
                path: "",
                name: "book-list",
                component: () => import("@/components/book/BookList.vue"),
            },
            {
                path: "create",
                name: "book-create",
                component: () => import("@/components/book/BookCreate.vue"),
            },
            {
                path: "view/:isbn",
                name: "book-detail",
                component: () => import("@/components/book/BookDetail.vue"),
            },
            {
                path: "modify/:isbn",
                name: "book-modify",
                component: () => import("@/components/book/BookModify.vue"),
            },
        ],
    },
];

const router = new VueRouter({
    mode: "history",
    base: process.env.BASE_URL,
    routes,
});

export default router;
