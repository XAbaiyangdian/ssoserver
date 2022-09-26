// sa 
var sa = {};

sa.loading = function (msg) {
    layer.closeAll();	// 开始前先把所有弹窗关了
    return layer.msg(msg, {icon: 16, shade: 0.3, time: 1000 * 20, skin: 'ajax-layer-load'});
};

// 隐藏loading
sa.hideLoading = function () {
    layer.closeAll();
};

$('.login-btn').click(function () {
    // 开始登录
    setTimeout(function () {
        $.ajax({
            url: "sso/doLogin",
            type: "post",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify({
                "loginName": $('[name=loginName]').val(),
                "password": $('[name=password]').val(),
                "uscc": $('[name=uscc]').val()
            }),
            dataType: 'json',
            success: function (res) {
                sa.hideLoading();
                if (res.status == 1) {
                    layer.msg('登录成功', {anim: 0, icon: 6});
                    setTimeout(function () {
                        location.reload();
                    }, 800)
                } else {
                    layer.msg(res.message, {anim: 6, icon: 2});
                }
            },
            error: function (xhr, type, errorThrown) {
                sa.hideLoading();
                if (xhr.status == 0) {
                    return layer.alert('无法连接到服务器，请检查网络');
                }
                return layer.alert("异常：" + JSON.stringify(xhr));
            }
        });
    }, 400);
})