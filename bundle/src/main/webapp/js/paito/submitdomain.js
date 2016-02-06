function submitDomain(){

    var domain = $("#domain").val();
    var description = $("#description").val();
    var platform = $("#platform").val();
    var expireTime = $("#expireTime").val();
    var basePrice = $("#basePrice").val();
    var mobileno = $("#mobileno").val();
    var weixin = $("#weixin").val();
    var verifycode = $("#verifycode").val();
    var domainSuffix = $("#domainSuffix").val();

    jQuery.ajax({
        url: '//pai.to/auction/submitdomain.json',
        data:{
            domain : domain,
            description : description,
            platform : platform,
            expireTime : expireTime,
            basePrice : basePrice,
            mobileno : mobileno,
            weixin : weixin,
            verifycode : verifycode,
            domainSuffix : domainSuffix
        },
        type: 'post',
        cache: false,
        dataType: 'json',
        success: function (result) {
            if (result.info.ok) {
                alert("提交域名成功！");
                $("#status2").show();
                $("#status1").hide();
                $("#domain").attr("disabled", true);
                $("#description").attr("disabled", true);
                $("#platform").attr("disabled", true);
                $("#expireTime").attr("disabled", true);
                $("#basePrice").attr("disabled", true);
                $("#mobileno").attr("disabled", true);
                $("#weixin").attr("disabled", true);
                $("#verifycode").attr("disabled", true);
                $("#domainSuffix").attr("disabled", true);
                $("#J_BtnGetVerifyCode").attr("disabled", true);
                $("#J_BtnInfoForm").attr("disabled", true);
            } else {
                alert("提交域名失败，请重新提交");
            }
        }, error: function () {
            alert("提交域名失败，请重新提交");
        }
    });

};

function checkUserPhone(){
    var userPhone = $("#mobileno").val();
    var userPhone_pattern = new RegExp("^[0-9]{11,11}$");  //...

    if(!userPhone_pattern.test(userPhone)){
        $("#userPhone_err").show();
        $("#userPhone_suc").hide();
        return;
    }else{
        $("#userPhone_err").hide();
        $("#userPhone_suc").show();
        return;
    }
};


function getVerifyCode(){

    var userPhone = $("#mobileno").val();
    $.post('/user/sendSms.action?mobileno='+userPhone, null, function(data){
        if(data.info.ok){
            $("#verifyMsg_sendOk").show();
            return;
        }else{
            $("#verifyMsg_sendOk").hide();
            return;
        }
    });

}

function checkVerifyCode(){

    var userPhone = $("#mobileno").val();
    var verifyMsg = $("#verifycode").val();
    var verifyMsg =
        $.post('/validate/checkVerifyMsg.action?mobileno='+userPhone+'&verifyMsg='+verifyMsg, null, function(data){
            if(data.info.ok){
                $("#verifyMsg_checkerror").hide();
                return;
            }else{
                $("#verifyMsg_checkerror").show();
                return;
            }
        });

};