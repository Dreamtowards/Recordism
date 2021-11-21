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
// import "./assets/semantic/dist/semantic.css";

// import "./assets/css/primer.css";
import "./assets/css/bootstrap.css";
import "./assets/css/bootstrap-utilities.css";
// import "./assets/css/bootstrap.rtl.css";
import "./assets/css/style.css";


// User Control
var user = {
    token: Cookies.get("token"),
    uid: undefined,
    username: undefined,
    avatar_url: "",
    sites: []
};
function userLogin(token) {
    Cookies.set("token", token, {expires: 10});
    user.token = token;
}
function userUpdate() {
    request("/user_profile", {}, resp => {

        user.uid = resp.uid;
        user.username = resp.username;
        user.sites = resp.sites;
    }, err => {
        user.token = undefined;
    });
}
function userLogout() {

    user.token = undefined;
    Cookies.remove("token");
}

// API Networking Requestion
function request(path, data, respf, errf, lcall = ()=>{}) {
    data.token = user.token;
    // request.console.log("request. \npath:"+path+", data:"+JSON.stringify(data));
    return axios
        .post(true ? "http://localhost:8010/api"+path : "https://dalao.world/api"+path, data)
        .then(resp => {
            // console.log("request(responsed). \npath:"+path+", data:"+JSON.stringify(data)+", \nresp:"+JSON.stringify(resp.data));
            respf(resp.data);
        }).catch(ex => {
            if (ex.response) {
                if (errf == null) {
                   errf = err=>showToast("Error: "+err.message)
                }
                errf(ex.response.data);
            }
        }).then(() => {
            lcall();
        });
}



import Overview from "./components/Overview.vue";
import Profile from "./components/UserProfile.vue";

import UserRegister from "./components/UserRegister.vue";
import UserLogin from "./components/UserLogin.vue";

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
        {path: "/register", component: UserRegister},
        {path: "/login", component: UserLogin},
        {path: "/site/:site_id", component: Overview, props: (route) => {
                return { site_id: Number.parseInt(route.params.site_id) }
            }},
        {path: "/profile", component: Profile}
        // {path: "*", component: ErrorPage404}
    ]
});

import AppDefine from "./main.vue";
let AppType = Vue.extend(AppDefine);
let vm = new AppType({
    router: router,
}).$mount("#root");


function showToast(msg) {
    vm.pollToast(msg);
}

export {user, request, showToast, userLogin, userUpdate, userLogout};