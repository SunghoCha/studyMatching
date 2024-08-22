import {createRouter, createWebHistory} from 'vue-router';
import TheHome from "@/pages/TheHome.vue";
import TheLogin from "@/pages/TheLogin.vue";



const router =  createRouter({
    history: createWebHistory(),
    routes: [
        { path: '/', redirect: '/home'},
        { path: '/home', component: TheHome },
        { path: '/login', component: TheLogin },

    ]
});

export default router;