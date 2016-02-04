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

    html = ['<nav class="navbar navbar-default navbar-fixed-top">',
        '<div class="container-fluid">',
        '<!-- Brand and toggle get grouped for better mobile display -->',
        '<div class="navbar-header">',
        '<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">',
        '<span class="sr-only">Toggle navigation</span>',
        '<span class="icon-bar"></span>',
        '<span class="icon-bar"></span>',
        '<span class="icon-bar"></span>',
        '</button>',
        '<a href="#">',
        '<img src="imgs/smalllogo.jpg">',
        '</a>',
        '</div>',
        '<!-- Collect the nav links, forms, and other content for toggling -->',
        '<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">',
        '<div class="paito-user y-hide">',
        '<li><span class="sr-only">欢迎您!</span></li>',
        '<li><a class="user-uid" target="" href="#"></a></li>',
        '<li><a  id="lnk_Logout" href="https://pai.to/logout/logout.htm">退出</a></li>',
        '</div>',
        '<div class="paito-login">',
        '<ul class="nav navbar-nav navbar-right">',
        '<li><a class="lnk-login" target="_self" id="lnk-login">登录</a></li>',
        '<li class="divider"></li>',
        '<li><a class="lnk-reg" target="_self" id="lnk-reg">注册</a></li>',
        '<li class="divider"></li>',
        '<li><a href="https://paito.to/home/forgetpsd.shtml">忘记密码?</a></li>',
        '</ul>',
        '</div>',
        '</div><!-- /.navbar-collapse -->',
        '</div><!-- /.container-fluid -->',
        '</nav>'].join('');


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

        var showUnLogin = function(){
            jQuery('.paito-login').removeClass('y-hide');
            jQuery('.paito-user').addClass('y-hide');
            jQuery('.lnk-login').attr('href', 'http://pai.to/home/login.shtml' + callbackUrl);
            if(window.location.search.indexOf('lang=en') >= 0 ){
                jQuery('.lnk-reg').attr('href',  'http://pai.to/home/register.shtml?lang=en');
                jQuery('#lnk_login').attr('href','http://pai.to/login/login.htm');
            }else{
                jQuery('.lnk-reg').attr('href', 'http://pai.to/home/register.shtml' + callbackUrl);
            }
        };

        var uid = CookieHelper.get('login_userid');

        if (uid) {
            uid = decodeURIComponent(uid.replace(/"/g, ''));
        }

        /**
         * 逻辑进行修改，
         * 1. 先判断是否需要接口判断，通过 window.__TOPBAR_EXCEPT_PROMOTION__20150104 属性判断
         * 2. 在通过接口判断是否登录，
         */

        // 替换登录状态
        var _changeLoginBtn = function () {
            jQuery('.user-uid').text(uid);
            jQuery('.paito-user').removeClass('y-hide');
            jQuery('.paito-login').addClass('y-hide');
            jQuery('.user-uid').attr({'href': 'http://account.aliyun/common/header.htm?username=' + uid});
            jQuery('#lnk_Logout').attr('href', 'http://pai.to/logout/logout.htm' + callbackUrl);
        }

        var _ajaxLoadUserInfo = function (callback) {
            jQuery.ajax({
                url: '//pai.to:8080/user/accountInfo.json',
                type: 'post',
                cache: false,
                dataType: 'json',
                success: function () {
                    if (result.info.ok) {
                        callback();
                        var data = result.data;
                        // todo
                        /*if (data.messageCount != undefined && parseInt(data.messageCount, 10) != 0) {
                         jQuery('#mMessageCount').text('(' + data.messageCount + ')');
                         } else {
                         jQuery('#mMessageCount').hide();
                         }*/
                        jQuery('#lnk_index').css('margin-top', '3px');
                    } else {
                        showUnLogin();
                    }
                },
                error: function () {
                    showUnLogin();
                }
            });
        }



        if (uid) {
            _changeLoginBtn();
            _ajaxLoadUserInfo(function () {
            });
        } else{
            _ajaxLoadUserInfo(function () {
            });
        }

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
        window.Paito.loadScript('js/jquery.min.js',_rander);
    }else{
        _rander();
    }


}).call(this);
document.write('<style>@media screen and (max-width: 1000px){#LnkNewUserTip{display: none !important;}}.ay-global-topbar{background:#008eb7!important;}#lnk_search{display:none;}#lnk_index{display:none;}#lnk_index_spacing{display:none;}</style>'
);