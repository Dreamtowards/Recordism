import Vue from "vue";
Vue.config.productionTip = false;

// import {library, dom} from "@fortawesome/fontawesome-svg-core";
// import {fas} from "@fortawesome/free-solid-svg-icons";
// import {far} from "@fortawesome/free-regular-svg-icons";
// import {fab} from "@fortawesome/free-brands-svg-icons";
// library.add(fas, far, fab);
// dom.watch();

import axios from "axios";
import Cookies from "js-cookie";

import VueRouter from "vue-router";
Vue.use(VueRouter);

// import "./assets/js/bootstrap.js";
// import "./assets/css/bootstrap.min.css";

import "./assets/css/primer.css"
import "./assets/css/style.css";

// User Control
// function setupLogin(uid, accessToken) {
//     Cookies.set("uid", uid, {expires: 10});
//     Cookies.set("accessToken", accessToken, {expires: 10});
// }
// var user = {
//     uid: parseInt(Cookies.get("uid")),
//     accessToken: Cookies.get("accessToken"),
//
//     email: "",
//     username: "",
//     avatar_url: "",
//     savelists: {
//         HISTORY:    {id: -1},
//         READLATER:  {id: -2},
//         LIKEDPOSTS: {id: -3},
//         created: []
//     }
// };
// function updateUserinfo() {
//     if (isFinite(user.uid)) {  // refresh user_info
//         request("/api/user_info", {}, resp => {
//             user.username = resp.username;
//             user.avatar_url = resp.avatar_url;
//             user.email = resp.email;
//             user.savelists = resp.savelists;
//         });
//     }
// }
// updateUserinfo();

// API Networking Requestion
function request(path, data, respf, errf, lcall = ()=>{}) {
    // setup AuthIdentify (tho may sometimes unnecessary).
    data.uid = user.uid;
    data.accessToken = user.accessToken;
    // request.
            console.log("request. \npath:"+path+", data:"+JSON.stringify(data));
    return axios
        .post("http://1.116.211.233:8003"+path, data)
        .then(resp => {
            console.log("request(responsed). \npath:"+path+", data:"+JSON.stringify(data)+", \nresp:"+JSON.stringify(resp.data));
            respf(resp.data);
        }).catch(ex => {
            if (ex.response) {
                if (errf == null) {
                   errf = err=>showToast("UncaughtedNetException: "+err.message)
                }
                errf(ex.response.data);
            }
        }).then(() => {
            lcall();
        });
}



import Console from "./components/Console.vue";
import Overview from "./components/Overview.vue";

// import SignRegister from "./components/auth/SignRegister.vue";
// import SignLogin from "./components/auth/SignLogin.vue";
// import ErrorPage404 from "./components/error/ErrorPage404.vue";
//
// import HomePage from "./components/home/HomePage.vue";
// import HomeStreaming from "./components/home/homestreaming/HomeStreaming.vue";
//
// import SavelistPage from "./components/SavelistPage.vue";


let router = new VueRouter({
    routes: [
        // {path: "/register", component: SignRegister},
        // {path: "/login", component: SignLogin},
        {
            path: "/",
            component: Overview,
        },
        {
            path: "/console",
            component: Console,
            // children: [
            //     {path: "", component: HomeStreaming},
            //     {path: "/feed/trending", component: ErrorPage404},
            //     {path: "/feed/subscriptions", component: ErrorPage404},
            //     {path: "/feed/library", component: ErrorPage404},
            //     {path: "/savelist", component: SavelistPage, props: route => ({savelist_id: parseInt(route.query.list)})},
            // ]
        },
        // {path: "*", component: ErrorPage404}
    ]
});



import AppDefine from "./main.vue";
let AppType = Vue.extend(AppDefine);
let vm = new AppType({
    router: router
}).$mount("#root");


function showToast(msg) {
    vm.pollToast(msg);
}

export {request, showToast};