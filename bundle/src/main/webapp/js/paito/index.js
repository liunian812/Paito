(function () {

    var UA = function(){
        var Sys = {};
        var ua = navigator.userAgent.toLowerCase();
        var s;
        (s = ua.match(/rv:([\d.]+)\) like gecko/)) ? Sys.ie = s[1] :
            (s = ua.match(/msie ([\d.]+)/)) ? Sys.ie = s[1] :
                (s = ua.match(/firefox\/([\d.]+)/)) ? Sys.firefox = s[1] :
                    (s = ua.match(/chrome\/([\d.]+)/)) ? Sys.chrome = s[1] :
                        (s = ua.match(/opera.([\d.]+)/)) ? Sys.opera = s[1] :
                            (s = ua.match(/version\/([\d.]+).*safari/)) ? Sys.safari = s[1] : 0;
        return Sys.ie == '8.0';
    }

    // IE8 补丁
    if(UA()){
        window.console = {};
        window.console.log=function(){};
        window.console.error=function(){};
        window.console.warn=function(){};
        String.prototype.trim = function() {
            return this.replace(/^\s+|\s+$/g, '');
        };
    }

    var callbackUrl, html;

    html = [
    '<div id="J_slider" data-spm="3">',
    '<img class="banner" src="imgs/banner.jpg">',
    '<div class="aligncenter banner-float-div">',
    '<img src="imgs/index-float-text.png" class="banner-float">',
    '</div>',
    /*'<div class="nav">',
    '<i num="0" class="action"></i>',
    '</div>',*/
    '</div>',
    '<!--<div class="ali-mian-notice" id="J_notice">',
    '<div class="y-row">',
    '<div class="y-span1" style="text-align: right;">',
    '<img src="imgs/horn.jpg">',
    '</div>',
    '<div class="y-span3">',
    '<span>·&nbsp;</span>',
    '</div>',
    '<div class="y-span3">',
    '<span>·&nbsp;</span>',
    '</div>',
    '<div class="y-span3">',
    '<span>·&nbsp;</span>',
    '</div>',
    '<div class="y-span2 y-last">',
    '<a href="http://pai.to/noticelist.html" target="_blank">',
    '更多&gt;&gt;',
    '</a>',
    '</div>',
    '</div>',
    '</div>-->',
    '<div id="J_liucheng" data-spm="4">',
    '<img src="imgs/index-liucheng.jpg" class="img-liucheng aligncenter">',
    '</div>',
    '<div style="width: 100%;background-color: #f6f6f6; height: 500px">',
        '<div id="J_yuming" data-spm="5" class="J_yuming aligncenter">',
            '<div>',
            '<span style="font-size: larger;font-weight: bold; ">本期热门域名竞猜</span>',
            '<span style="position: relative;float: right;padding-right: 500px; color: red">每周二、周六22:00开拍</span>',
            '</div>',
            '<div id="J_nodata-notic">',
            '<img src="imgs/bg-white.png" style="float: left;">',
            '<span class="nodata_span_text">暂无数据，敬请期待!</span>',
            '</div>',
            '<div class="table-responsive paito-table y-hide" id="J_domaintable">',
            '</div>',
        '</div>',
        '<div id="J_contract" class="contract">',
            '<img src="imgs/index-contract-icon.jpg" class="kefu" onfocus="showKefuQCode();">',
            '<img src="imgs/index-weixin-icon.jpg" class="weixin" onfocus="showWeixinQCode();">',
        '</div>',
    '</div>'
    ].join('');


    document.write(html);
    if(typeof Paito == "undefined"){
        window.Paito = {};
    }

    Paito.topbar = {};

    var getSuffix = function(){
        var env = "dev";
        var staticSuffix = {
            'dev': '.test',
            'test': '.test',
            'prod': '.com'
        };
        if(window.location.host.indexOf('127.0.0.1') != -1 || window.location.host.indexOf('localhost') != -1){
            env = 'dev';
        } else if(window.location.host.indexOf('.test') != -1){
            env = 'test';
        } else {
            env = 'prod';
        }
        return staticSuffix[env];
    };


    function Redirect (url) {
        var ua        = navigator.userAgent.toLowerCase(),
            isIE      = ua.indexOf('msie') !== -1,
            version   = parseInt(ua.substr(4, 2), 10);

        // Internet Explorer 8 and lower
        if (isIE && version < 9) {
            var link = document.createElement('a');
            link.href = url;
            document.body.appendChild(link);
            link.click();
        }

        // All other browsers
        else { window.location.href = url; }
    }

    function getParameterByName(name) {
        name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
        var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
            results = regex.exec(location.search);
        return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
    }

    var pluses = /\+/g;
    function parseCookieValue(s) {
        if (s.indexOf('"') === 0) {
            // This is a quoted cookie as according to RFC2068, unescape...
            s = s.slice(1, -1).replace(/\\"/g, '"').replace(/\\\\/g, '\\');
        }

        try {
            // Replace server-side written pluses with spaces.
            // If we can't decode the cookie, ignore it, it's unusable.
            // If we can't parse the cookie, ignore it, it's unusable.
            return decodeURIComponent(s.replace(pluses, ' '));
        } catch(e) {}
    }


    var CookieHelper = {
        set:function(key, value, options){
            if (typeof options.expires === 'number') {
                var d = new Date();
                d.setTime(d.getTime() + (options.expires*24*60*60*1000));
                options.expires = d.toUTCString();
            }

            return (document.cookie = [
                encodeURIComponent(key), '=', encodeURIComponent(value),
                options.expires ? '; expires=' + options.expires : '', // use expires attribute, max-age is not supported by IE
                options.path    ? '; path=' + options.path : '',
                options.domain  ? '; domain=' + options.domain : '',
                options.secure  ? '; secure' : ''
            ].join(''));
        },
        get:function(key){
            var cookies = document.cookie ? document.cookie.split('; ') : [],
                i = 0,
                l = cookies.length,
                result;

            for (; i < l; i++) {
                var parts = cookies[i].split('='),
                    name = decodeURIComponent(parts.shift()),
                    cookie = parts.join('=');

                if (key === name) {
                    // If second argument (value) is a function it's a converter...
                    result = parseCookieValue(cookie);
                    break;
                }
            }
            return result;

        }
    };


    function setCookie(name, value){
        var argv = setCookie.arguments;
        var argc = setCookie.arguments.length;
        var expires = (argc > 2) ? argv[2] : null;
        if(expires!=null){
            var LargeExpDate = new Date ();
            LargeExpDate.setTime(LargeExpDate.getTime() + (expires*1000*3600*24));
        }
        document.cookie = name + "=" + escape (value)+((expires == null) ? "" : ("; expires=" +LargeExpDate.toGMTString()));
    }

    if(getParameterByName('lang') == 'zh-hk'){
        setCookie("ft"+window.location.hostname.toString().replace(/\./g,""),'1');
        CookieHelper.set('paito_lang','zh',{domain:'.pai.to',path:'/',expires:30});
    }else{
        if(getParameterByName('lang') == 'en'){
            CookieHelper.set('paito_lang','en',{domain:'.pai.to',path:'/',expires:30});
        }else{
            CookieHelper.set('paito_lang','zh',{domain:'.pai.to',path:'/',expires:30});
        }
        setCookie("ft"+window.location.hostname.toString().replace(/\./g,""),'0');
    }

    var _rander = function(){


        var fnTimer;

        jQuery('body').bind('mouseenter', function(e){
            clearTimeout(fnTimer);
            var lnk, menu, newuser, newuserMenu;
            lnk = jQuery('.lnk-user');
            menu = jQuery('.user-list');
            newuser = jQuery('.lnk-newuser-t');
            newuserMenu = jQuery('.newuser-panel');
            lnk.removeClass('lnk-user-selected lnk-user-rotate');
            newuser.removeClass('aliyun-newuser-selected aliyun-newuser-rotate');

            menu.css({
                'left': -500
            });
            newuserMenu.css({
                'left': -500
            });
            if (UA()) {
                newuser.removeClass('aliyun-newuser-selected-ie');
                lnk.removeClass('lnk-user-selected-ie');
            }
        });


        jQuery('.ay-global-topbar .aliyun-user').bind('mouseenter',function(){
            fnTimer = setTimeout(function(){
                var lnk, menu, lnkPos;

                lnk = jQuery('.lnk-user');
                menu = jQuery('.user-list');

                lnk.addClass('lnk-user-selected lnk-user-rotate');
                lnkPos = lnk.offset();

                menu.css({
                    'left': lnkPos.left,
                    'width': Math.max( menu.outerWidth(), lnk.outerWidth(), jQuery('#J-user-rights-popup').width() ) ,
                    'height': (jQuery('.user-list li').length * 26 + 92)
                });
                if (UA()) {
                    lnk.addClass('lnk-user-selected-ie');
                }
            },250);
        })

        jQuery('.ay-global-topbar .aliyun-user').bind('mouseleave', function () {
            clearTimeout(fnTimer);
            var lnk, menu;
            lnk = jQuery('.lnk-user');
            menu = jQuery('.user-list');
            lnk.removeClass('lnk-user-selected lnk-user-rotate');

            menu.css({
                'left': -500,
                'height': 0
            });

            if (UA()) {
                lnk.removeClass('lnk-user-selected-ie');
            }

        });


        /*judge log info*/
        callbackUrl = location.href.indexOf('account.pai.to') < 0 ? '?oauth_callback=' + encodeURIComponent(window.location.href) : '';
        jQuery('#lnk_login').attr('href','http://pai.to/home/login.shtml' + callbackUrl);

        if(window.location.search.indexOf('lang=en') >= 0 ){
            jQuery('.lnk-reg').attr('href',  'http://pai.to/home/register.shtml?lang=en');
            jQuery('#lnk_index').attr('href','//www.pai.to?lang=en');
        }else{
            jQuery('.lnk-reg').attr('href', 'http://pai.to/home/register.shtml' + callbackUrl);
        }

        var showNoAuction = function(){
            jQuery('.J_nodata-notic').removeClass('y-hide');
            jQuery('.J_domaintable').addClass('y-hide');
        };

        var uid = CookieHelper.get('_nk_');

        if (uid) {
            uid = decodeURIComponent(uid.replace(/"/g, ''));
        }

        /**
         * 逻辑进行修改，
         * 1. 先判断是否需要接口判断，通过 window.__TOPBAR_EXCEPT_PROMOTION__20150104 属性判断
         * 2. 在通过接口判断是否登录，
         */

        var _ajaxLoadAuctionList = function(callback){
            jQuery.ajax({
                url: '/auction/findAuctionList.action',
                type: 'post',
                cache: false,
                dataType: 'json',
                success: function (result) {
                    if (result.info.ok) {
                        var data = result.data;
                        var auctionList = data.auctionList;
                        if(auctionList){
                            var str = "<table>";
                            str += '<table class="table table-bordered">';
                            str += '<thead class="thread-border">';
                            str += '<tr>';
                            str += '<th class="col-md-3 col-1">热门域名</th>';
                            str += '<th class="col-md-3 col-2">含义</th>';
                            str += '<th class="col-md-3 col-1">预计成交价</th>';
                            str += '<th class="col-md-3 col-2">竞猜成交价</th>';
                            str += '</tr>';
                            str += '</thead>';
                            str += '<tbody>';
                            for (var i = 0, l = auctionList.length; i < l; i++) {
                                var one = auctionList[i];
                                str += "<tr>";
                                str += "<td>"+one.domain +"."+one.domainSuffix+"</td>";
                                str += "<td>"+one.description+"</td>";
                                str += "<td>"+one.basePrice+"</td>";
                                str += "<td></td>";
                                str += "</tr>";
                            };
                            str += "</tbody>";
                            str += "</table>";
                            document.getElementById("J_domaintable").innerHTML = str;
                            jQuery('#J_domaintable').removeClass('y-hide');
                            jQuery('#J_nodata-notic').addClass('y-hide');
                        }
                    } else {
                        showNoAuction();
                    }
                },
                error: function () {
                    showNoAuction();
                }
            });
        }

        _ajaxLoadAuctionList(function(){

        });

    }

    window.Paito.loadScript = function(url, callback) {
        var script = document.createElement("script")
        script.type = "text/javascript";
        if (script.readyState) {  //IE
            script.onreadystatechange = function () {
                if (script.readyState == "loaded" ||
                    script.readyState == "complete") {
                    script.onreadystatechange = null;
                    callback();
                }
            };
        } else {  //Others
            script.onload = function () {
                callback();
            };
        }
        script.src = url;
        document.getElementsByTagName('head')[0].appendChild(script);
    }


    if(!window.jQuery){  // 如果没有加载jquery的话，先加载jquery
        window.Paito.loadScript('../js/jquery.min.js',_rander);
    }else{
        _rander();
    }


}).call(this);

function showKefuQCode(){
    var a="2";
    alert("2");
}

function showWeixinQCode(){
    var b="3";
    alert("3");
}

document.write('<style>@media screen and (max-width: 1000px){#LnkNewUserTip{display: none !important;}}.ay-global-topbar{background:#008eb7!important;}#lnk_search{display:none;}#lnk_index{display:none;}#lnk_index_spacing{display:none;}</style>'
);