import Vue from 'vue'
import Router from 'vue-router'
import login from '../components/login'
import buyer from "../components/Buyer/buyer";
import buyerHouses from "../components/Buyer/houses";
import conversations from "../components/shared/conversations";
import receiveRequests from "../components/Buyer/sendRequests";
import sendRequests from "../components/Buyer/sendRequests";
import myHouses from "../components/Seller/myHouses";
import seller from "../components/Seller/seller";

Vue.use(Router);

let router = new Router({
    routes: [
        {
            path: '/',
            name: 'login',
            component: login
        },
        {
            path: '/buyer',
            name: 'buyer',
            redirect: '/buyer/houses',
            component: buyer,
            meta: {
                radio: 'buyer'
            },
            children: [
                {
                    path: 'houses',
                    name: 'houses',
                    component: buyerHouses,
                    meta: {
                        radio: 'buyer'
                    },
                },
                {
                    path: 'conversations',
                    name: 'conversations',
                    component: conversations,
                    meta: {
                        radio: 'buyer'
                    },
                },
                {
                    path: 'requests',
                    name: 'requests',
                    component: sendRequests,
                    meta: {
                        radio: 'buyer'
                    },
                }]
        },
        {
            path: '/seller',
            name: 'seller',
            redirect: '/seller/myHouses',
            component: seller,
            meta: {
                radio: 'seller'
            },
            children: [
                {
                    path: 'myHouses',
                    name: 'myHouses',
                    component: myHouses,
                    meta: {
                        radio: 'seller',

                    },
                },
                {
                    path: 'conversations',
                    name: 'conversations',
                    component: conversations,
                    meta: {
                        radio: 'seller'
                    },
                },
                {
                    path: 'requests',
                    name: 'requests',
                    component: receiveRequests,
                    meta: {
                        radio: 'seller'
                    },
                }]
        }
    ]
});

router.beforeEach((to, from, next) => {
    if (to.path !== '/') {
        //是否需要登录权限
        if (to.meta.radio === window.localStorage["radio"]) {
            next()
        } else {
            next({
                path: '/',
            })
        }
    } else {
        window.localStorage["primaryKey"] = '';
        window.localStorage["radio"] = '';
        next()
    }
});

export default router;
