$(document).ready(function () {
    //音乐人切换
    $("#EM").hide();
    $("#RH").hide();
    $("#CN_tab").click(function () {
        $("#CN").show();
        $("#EM").hide();
        $("#RH").hide();
    });
    $("#EM_tab").click(function () {
        $("#EM").show();
        $("#CN").hide();
        $("#RH").hide();
    });
    $("#RH_tab").click(function () {
        $("#RH").show();
        $("#CN").hide();
        $("#EM").hide();
    });

    //排行榜动画
    $(".Ranking_container .container").hover(function () {
        $(this).hover(function () {
            let str = this.children[2].children[0];
            let yinbo = this.children[2].children[1];
            $(str).hide(300);
            $(yinbo).show(300);
        }, function () {
            let str = this.children[2].children[0];
            let yinbo = this.children[2].children[1];
            $(yinbo).hide(300);
            $(str).show(300);
        });
    });

    //轮播图箭头
    $(".LB").hover(function () {
        $(this).hover(function () {
            let con = this.children[0].children[2];
            let cons = this.children[0].children[3];
            $(con).show(300);
            $(cons).show(300);
        }, function () {
            let con = this.children[0].children[2];
            let cons = this.children[0].children[3];
            $(con).hide(300);
            $(cons).hide(300);
        })
    });
});

$('.icon-like').on('click', function () {
    $(this).css('color', '#e52222');
});


//头部
window.onload = function () {
    var mh_a1 = document.querySelectorAll(".modal_header a")[0];
    var mh_a2 = document.querySelectorAll(".modal_header a")[1];
    var login = document.getElementsByClassName("login")[0];
    var registered = document.getElementsByClassName("registered")[0];
    mh_a1.onclick = function () {
        login.style.display = "block";
        registered.style.display = "none";
    }
    mh_a2.onclick = function () {
        login.style.display = "none";
        registered.style.display = "block";
    }
    var httpurl = ""//请求路径

    // $("#button4").on("click", function () {
    //     var registerUserName = $("#registerUserName").val();
    //     $.ajax({
    //         // contentType: "application/x-www-form-urlencoded",
    //         type: "get",
    //         url: "registerVerificationCode?mailbox=" + registerUserName,
    //         // data:{"name":$("#input").val()},
    //         dataType: "json",
    //         success: function (data, status) {
    //             alert(data.state);
    //             alert(data.registerUserName);
    //             if (data.state) {
    //                 alert("我调用了");
    //             }
    //             alert("数据: \n" + data + "----" + status);
    //             $("#wc").text(data);
    //             document.getElementsByClassName('reg_hed_right')[0].children[1].innerHTML = data.information;
    //         }
    //     });
    // })
    // 用于发送验证码
    $("#button4").on("click", function () {
        var registerMail = $("#registerMail").val();
        $.ajax({
            // contentType: "application/x-www-form-urlencoded",
            type: "get",
            url: "registerVerificationCode?mailbox=" + registerMail,
            // data:{"name":$("#input").val()},
            dataType: "json",
            success: function (data, status) {
                alert(data.state);
                alert(data.registerMail);
                if (data.state) {
                    alert("我调用了");
                }
                $("#wc").text(data);
                document.getElementsByClassName('reg_hed_right')[0].children[1].innerHTML = data.information;
            }
        });
    });

    // 用于注册账号
    $("#registerUser").on("click", function () {
        alert(222);
        var registerUserName = $("#registerUserName").val();
        var registerMail = $("#registerMail").val();
        var registerPassword = $("#registerPassword").val();
        var registerPasswordAgain = $("#registerPasswordAgain").val();
        var verificationCode = $("#verificationCode").val();
        var agreement = $("#agreement").is(":checked");
        alert(agreement);
        $.ajax({
            contentType: "application/x-www-form-urlencoded;charset=UTF-8",
            type: "post",
            url: "register",
            data: {
                "userName": registerUserName,
                "sendMail": registerMail,
                "password": registerPassword,
                "passwordAgain": registerPasswordAgain,
                "verificationCode": verificationCode,
                "agreement": agreement
            },
            dataType: "json",
            success: function (data, status) {
                alert(data.state);
                alert(data.information);
                if (data.state) {
                    alert("我调用了");
                }
                $("#wc").text(data);
                if (data.state == 0) {
                    document.getElementsByClassName('reg_hed_right')[0].children[1].innerHTML = data.information;
                }else{
                    alert("注册成功了");
                }
            }
        });
    });
};
