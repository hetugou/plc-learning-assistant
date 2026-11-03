/**
 * PLC 学习助手 — 前端入口脚本
 *
 * 约束：完全离线，不发起任何网络请求（无 fetch / XHR / WebSocket），
 * 不引用 CDN，所有资源均来自本地 assets。
 */
(function () {
    'use strict';

    var APP_VERSION = '0.2.0';

    function renderVersion() {
        var el = document.getElementById('app-version');
        if (el) {
            el.textContent = APP_VERSION;
        }
    }

    function markReady() {
        document.documentElement.setAttribute('data-app-ready', 'true');
    }

    function init() {
        renderVersion();
        markReady();
    }

    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', init, { once: true });
    } else {
        init();
    }
})();
