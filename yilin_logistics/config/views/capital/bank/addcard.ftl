<div class="modal inmodal" id="userModal" tabindex="-1" role="dialog"
aria-hidden="true" style="display: block; padding-left: 6px;">
    <div class="modal-dialog">
        <div class="modal-content animated bounceInRight">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" style="margin-top: -20px;">
                    <span aria-hidden="true" id="close-x">
                        &times;
                    </span>
                    <span class="sr-only">
                        关闭
                    </span>
                </button>
                <h6 class="modal-title">
                    添加银行卡
                </h6>
                </br>
                <form method="post" class="form-horizontal validate big" id="form-card" action="${WEB_PATH }/capital/bankcard/directOpenCard.do" target="_blank"> 
                <!--<form class="form-horizontal validate big" id="form-card" >-->
                    <input required="" aria-required="true" id="cnName" name="cnName" type="hidden" >
                    <div class="form-group">
                        <label class="col-sm-3 control-label">
                            <span style="color: red;">
                                *&nbsp;&nbsp;
                            </span>
                            银行卡号：
                        </label>
                        <div class="col-sm-8 card-number-content">
                            <input type="text" id="card-number" class="form-control" required="" aria-required="true"
                            name="bankCard" maxlength="32" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">
                            <span style="color: red;">
                                *&nbsp;&nbsp;
                            </span>
                            银行预留手机号码：
                        </label>
                        <div class="col-md-8">
                            <input type="text" required="" aria-required="true" class="form-control"
                            id="phone-number" name="phoneNo" maxlength="11" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">
                            <span style="color: red;">
                                *&nbsp;&nbsp;
                            </span>
                            真实姓名：
                        </label>
                        <div class="col-md-8">
                            <input type="text" required="" aria-required="true" class="form-control"
                            id="user-name" name="userName" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">
                            <span style="color: red;">
                                *&nbsp;&nbsp;
                            </span>
                            身份证号：
                        </label>
                        <div class="col-md-8">
                            <input type="text" required="" aria-required="true" class="form-control"
                            id="user-id" name="userId" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-4">
                        </label>
                        <div class="col-md-7">
                            <button type="button" class="btn btn-white" data-dismiss="modal" id="close-but">
                                关闭
                            </button>
                            <button type="button" class="btn btn-primary" onclick="onOpenCard()" id="saveBut">
                                <span class="glyphicon glyphicon-ok">
                                </span>
                                跳转开通
                            </button>
                        </div>
                    </div>
                </form>
                <div class="alert alert-info big">
                    <div class="col-md-1">
                    </div>
                    <h4>
                        温馨提示
                    </h4>
                    <ol class="padding-left-20">
                        <li>
                            请您输入正确的开户行以及银行卡号。
                        </li>
                        <li>
                            系统会根据输入的卡号信息智能识别银行以及卡种。
                        </li>
                        <li>
                            如果您填写的银行信息不正确可能会导致提现失败。
                        </li>
                        <li>
                            如果您的银行卡信息系统不能识别，请及时联系客服。
                        </li>
                    </ol>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="${WEB_PATH}/resources/js/capital/bankinput.js">
</script>
<script src="${WEB_PATH}/resources/js/plugins/layer/layer.js">
</script>
<script>
    $('#saveBut').attr("disabled", false);
    $.validator.setDefaults({
        highlight: function(e) {
            $(e).closest(".form-group").removeClass("has-success").addClass("has-error")
        },
        success: function(e) {
            e.closest(".form-group").removeClass("has-error").addClass("has-success")
        },
        errorElement: "span",
        errorPlacement: function(e, r) {
            e.appendTo(r.is(":radio") || r.is(":checkbox") ? r.parent().parent().parent() : r.parent())
        },
        errorClass: "help-block m-b-none",
        validClass: "help-block m-b-none"
    });
    function onOpenCard() {
        if ($("#form-card").valid()) {
        	$("#form-card").submit();
            $('#saveBut').attr("disabled", true);
            setTimeout(function() {
                layer.confirm('正在执行绑定银行卡操作', {
                    btn: ['已完成银行卡绑定', '银行卡绑定遇见问题？']
                },
                function(index, layero) {
                    window.location.reload();
                    layer.closeAll();
                },
                function(index) {
                    $('#saveBut').attr("disabled", false);
                    layer.closeAll();
                });
            },
            3000);
        }
    }
    $(function() {
        $("#card-number").bankInput(); //银行卡格式化
        var e = "<i class='fa fa-times-circle'></i>";
        $("#form-card").validate({
            rules: {
                bankCard: {
                    required: !0,
                },
                openBank: {
                    required: !0,
                }
            },
            messages: {
                bankCard: {
                    required: e + "请输入银行卡号。",
                },
                userName: {
                    required: e + "请输入姓名。",
                },
                userId: {
                    required: e + "请输入身份证号。",
                }
            }
        });

        //关闭层
        $('#close-but').click(function() {
            $('#userModal').remove();
        });
        $('#close-x').click(function() {
            $('#userModal').remove();
        });

        $('#card-number').blur(function() {
            var number = $("#card-number").val();
            var html = '<span id="alert">正在检测....</span>';
            $("#card-number").after(html);
            $.ajax({
                type: 'POST',
                url: '${WEB_PATH }/capital/bankcard/verify.do',
                data: {
                    "number": number
                },
                dataType: "json",
                success: function(data) {
                    $("#alert").remove();
                    $(".card-number-content").empty();
                    var charname = new Array();
                    var number = data.bankCard;
                    $("#bankCard").val(number);
                    var newNumber = '';
                    for (var i = 0; i < number.length; i++) {
                        if (i > 3 && i < (number.length - 3)) {
                            charname.push('*');
                        } else {
                            charname.push(number[i]);
                        }
                    }
                    for (var i = 0; i < charname.length; i++) {
                        newNumber = newNumber + charname[i];
                    }
                    $("#cnName").val(data.cnName);
                    var a = $("#cnName").val();
                    var result = '<div class="form-control-static">' + newNumber + '(' + data.cnName + ')</div>';
                    result += '<input type="hidden" class="form-control" id="bankCard" name="bankCard" value="' + number + '"/>';
                    $(".card-number-content").append(result);
                },
                error: function(data) {
                    $("#alert").remove();
                    var data = $.parseJSON(data.responseText);
                    swal("", data.msg, "error");
                }
            });
        });
    });
</script>