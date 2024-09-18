import {createRouter, createWebHistory} from 'vue-router';
import TheHome from "@/pages/TheHome.vue";
import TheLogin from "@/pages/TheLogin.vue";
import TheSignUp from "@/pages/TheSignUp.vue";
import TheEmailCheck from "@/pages/TheEmailCheck.vue";


const router =  createRouter({
    history: createWebHistory(),
    routes: [
        { path: '/', redirect: '/home'},
        { path: '/home', component: TheHome },
        { path: '/login', component: TheLogin },
        { path: '/signup', component: TheSignUp },
        { path: '/email-check', component: TheEmailCheck}
    ]
});

export default router;