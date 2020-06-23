/** kitadmin-v2.1.0 MIT License By http://kit.zhengjinfan.cn Author Van Zheng */
;
"use strict";
var mods = ["element", "sidebar", "mockjs", "select", "tabs", "menu", "route", "utils", "component", "kit"];
layui.define(mods,
    function(e) {
        layui.element;
        var t = layui.utils,
            a = layui.jquery,
            n = (layui.lodash, layui.route),
            i = layui.tabs,
            l = layui.layer,
            o = layui.menu,
            m = layui.component,
            s = layui.kit,
            p = function() {
                this.config = {
                    elem: "#app",
                    loadType: "SPA"
                },
                    this.version = "1.0.0"
            };
        p.prototype.ready = function(e) {
            var i = this.config,
                o = (0, t.localStorage.getItem)("KITADMIN_SETTING_LOADTYPE");
            null !== o && void 0 !== o.loadType && (i.loadType = o.loadType),
                s.set({
                    type: i.loadType
                }).init(),
                u.routeInit(i),
                u.menuInit(i),
            "TABS" === i.loadType && u.tabsInit(),
            "" === location.hash && t.setUrlState("主页", "#/"),
                // layui.sidebar.render({
                //     elem: "#ccleft",
                //     title: "",
                //     shade: !0,
                //     direction: "left",
                //     dynamicRender: !0,
                //     url: "dist/views/isleview/currentDragonInfo.html",
                //     width: "50%"
                // }),
                a("#cc").on("click",
                    function() {
                        layui.sidebar.render({
                            elem: this,
                            title: "",
                            shade: !0,
                            dynamicRender: !0,
                            url: "dist/views/isleview/edit_user.html",
                            width: "50%"
                        })
                    }),
                m.on("nav(header_right)",
                    function(e) {
                        var t = e.elem.attr("kit-target");
                        "setting" === t && layui.sidebar.render({
                            elem: e.elem,
                            title: "设置",
                            shade: !0,
                            dynamicRender: !0,
                            url: "dist/views/setting.html"
                        }),
                        "help" === t && l.alert("公会YY:716090;<br>技术YY:3225014")
                    }),
                layui.mockjs.inject(APIs),
            "SPA" === i.loadType && n.render(),
            "function" == typeof e && e()
        };
        var u = {
            routeInit: function(e) {
                var t = this,
                    a = {
                        r: [
                        {
                            path: "/user/index",
                            component: "dist/views/user/index.html",
                            name: "用户列表",
                            children: [
                            {
                                path: "/user/create",
                                component: "dist/views/user/create.html",
                                name: "新增用户"
                            },
                            {
                                path: "/user/edit1",
                                component: "dist/views/user/edit.html",
                                name: "编辑用户"
                            }]
                        }],
                        routes: [{
                            path: "/layui/fly",
                            component: "https://fly.layui.com/",
                            name: "Fly",
                            iframe: !0
                        },
                            {
                                path: "/user/edit",
                                component: "dist/views/isleview/edit_user.html",
                                name: "信息修改"
                            },
                            {
                                path: "/",
                                component: "dist/views/isleview/user.html",
                                name: "主页"
                            },
                            {
                                path: "/user/my",
                                component: "dist/views/isleview/user.html",
                                name: "用户中心"
                            },
                            {
                                path: "/user/make_up",
                                component: "dist/views/isleview/make_up.html",
                                name: "我的补偿"
                            },
                            {
                                path: "/user/currendDargonInfo",
                                component: "dist/views/isleview/currentDragonInfo.html",
                                name: "当前龙的信息"
                            },
                            {
                                path: "/user/kucun",
                                component: "dist/views/isleview/kucun.html",
                                name: "我的库存"
                            },
                            {
                                path: "/user/pointlogs",
                                component: "dist/views/isleview/pointlog.html",
                                name: "我的积分明细"
                            },
                            {
                                path: "/user/cundanglog",
                                component: "dist/views/isleview/cundanglog.html",
                                name: "我的存档记录"
                            },
                            {
                                path: "/user/store",
                                component: "dist/views/isleview/store.html",
                                name: "龙的商店"
                            },
                            {
                                path: "/user/colors",
                                component: "dist/views/isleview/colors.html",
                                name: "我的肤色"
                            },
                            {
                                path: "/exception/403",
                                component: "dist/views/exception/403.html",
                                name: "403"
                            },
                            {
                                path: "/exception/404",
                                component: "dist/views/exception/404.html",
                                name: "404"
                            },
                            {
                                path: "/exception/500",
                                component: "dist/views/exception/500.html",
                                name: "500"
                            }]
                    };
                return "TABS" === e.loadType && (a.onChanged = function() {
                    i.existsByPath(location.hash) ? i.switchByPath(location.hash) : t.addTab(location.hash, (new Date).getTime())
                }),
                    n.setRoutes(a),
                    this
            },
            addTab: function(e, t) {
                var a = n.getRoute(e);
                a && i.add({
                    id: t,
                    title: a.name,
                    path: e,
                    component: a.component,
                    rendered: !1,
                    icon: "&#xe62e;"
                })
            },
            menuInit: function(e) {
                var t = this;
                return o.set({
                    dynamicRender: !1,
                    isJump: "SPA" === e.loadType,
                    onClicked: function(a) {
                        if ("TABS" === e.loadType && !a.hasChild) {
                            var n = a.data,
                                i = n.href,
                                l = n.layid;
                            t.addTab(i, l)
                        }
                    },
                    elem: "#menu-box",
                    remote: {
                        url: "/api/getmenus",
                        method: "post"
                    },
                    cached: !1
                }).render(),
                    t
            },
            tabsInit: function() {
                i.set({
                    onChanged: function(e) {}
                }).render(function(e) {
                    e.isIndex && n.render("#/")
                })
            }
        }; (new p).ready(function() {
            console.log("Init successed.")
        }),
            e("admin", {})
    });
//# sourceMappingURL=admin.js.map
