var csColor = "#c9c9c9";	// 提示文本字体颜色
var blColor = "#575757";	// 还原字体颜色

//! 正则表达式
var RegExp0 = null;                                                            //! 不加任何验证                         (0)
var RegExp1 = /^(?![a-zA-Z0-9]+$)(?![^a-zA-Z/D]+$)(?![^0-9/D]+$).{10,20}$/;                                                 //! 只能是字母和数字                     (1)
var RegExp2 = /^[a-zA-Z0-9]*$/;                                                //! 只能填汉字,字母,数字及括号、空格     (2)
var RegExp3 = /^[\u4e00-\u9fa5]+$/;                                            //! 纯汉字                               (3)
var RegExp4 = /^\d+$/;                                                         //! 纯数字                               (4)
var RegExp5 = /^((13)|(18)|(15)|(17)|(00886))\d{9}$/;                                       //! 手机                                 (5)
var RegExp6 = /^\w+@[\w-]+\.\w{2,4}(\.\w{2,4})?$/;                             //! 邮箱                                 (6)
var RegExp7 = /^(\w+@[\w-]+\.\w{2,4}(\.\w{2,4})?)|(((13)|(18)|(15)|(0086))\d{9})$/;   //! 手机+邮箱                            (7)
//其他
//直接输入ID可判断与相应ID的内容是否一致（用于两次输入密码确认）

var aNotNull = new Array({ proper: null, obj: null });
//所有映射对象的属性
var csInPut = new Array();
var textInPut = new Array();
var bInput = new Array();


//! 常规提示框 CLASS = jsInPut   就可以实现文本框内提示功能
//! 必填提示框 CLASS = jsInPut jsNotNull
$(document).ready(function () {

    //! 一般提示框
    $(".jsInPut").each(function () {
        inputEach($(this));
    });
    $(".jsInPut").focus(function () {
        inputFocus($(this));
    });
    $(".jsInPut").blur(function () {
        inputblur($(this));
    });

    aNotNull.splice(0, aNotNull.length);
    $(".jsInPut").each(function () {
        var stPos = { proper: false, obj: this};
        aNotNull.push(stPos);
    });

    //! 方法
    function inputEach(Obj) {
        csInPut.push(Obj.css("color"));
        bInput.push(0);
        textInPut.push(Obj.val())
        Obj.css("color", csColor);
    };
    function inputFocus(Obj) {
        $(Obj).css('color', blColor);
        if (bInput[Obj.index(".jsInPut")] == 0) {
            carInPut = Obj.val();
            Obj.val('');

            //! 下次点击不再进行清除
            bInput[Obj.index(".jsInPut")] = 1;
            //! 还原样式
            //Obj.css("color", csInPut[Obj.index(".jsInPut")]);
            //alert(csInPut[Obj.index(".jsInPut")]);

            Obj.css("color", blColor);
        }
    };
    function inputblur(Obj) {

        if (Obj.val() == '') {
            //! 还原提示文本和CSS
            Obj.val(textInPut[Obj.index(".jsInPut")]);
            Obj.css("color", csColor);

            //! 下次点击继续清除
            bInput[Obj.index(".jsInPut")] = 0;
        }
    };
});


//! 判断是否所有的都通过验证了
function jsIfAll() {
    var proper = true;
    for (var i = 0 ; i < aNotNull.length; i++) {
        if (!aNotNull[i].proper) {
            $(aNotNull[i].obj).css('color', '#ec3c3c');
            proper = false;
        }
    }
    return proper;
};

//! 实现验证输入位数和正则表达式的功能
function jsNotNull(Obj, Min, Max, Ref) {
    //! span 对象
    var ObjSpan = $(Obj).next("span");

    //! 验证数据字段位数
    if (Min > 0) {
        if (Obj.value.length == 0)
            return;
        if (Obj.value.length < Min || Obj.value.length >= Max) {
            aNotNull[$(Obj).index(".jsInPut")].proper = false;
            $(Obj).css('color', '#ec3c3c');
            return jsShowOne(ObjSpan);
        }
    }
    else {
        if (Obj.value.length >= Max && Max != 0) {
            aNotNull[$(Obj).index(".jsInPut")].proper = false;
            $(Obj).css('color', '#ec3c3c');
            return jsShowOne(ObjSpan);
        }
    }

    var ifchar = true;
    switch (Ref) {
        case 0:
        {
            aNotNull[$(Obj).index(".jsInPut")].proper = true;
            return true;
        }
        case 1:
            Ref = RegExp1;
            break;
        case 2:
            Ref = RegExp2;
            break;
        case 3:
            Ref = RegExp3;
            break;
        case 4:
            Ref = RegExp4;
            break;
        case 5:
            Ref = RegExp5;
            break;
        case 6:
            Ref = RegExp6;
            break;
        case 7:
            Ref = RegExp7;
            break;
        default:
            ifchar = false;
            break;
    }


    if (!ifchar) {

        if ($(Ref).val() != Obj.value) {
            var dasdasd = $('#password1').value;
            jsMessagebox($(ObjSpan).next("span").html());
            $(Obj).css('color', '#ec3c3c');
            aNotNull[$(Obj).index(".jsInPut")].proper = false;
            return false;
        }
        else {
            $(Obj).css('color', blColor);
        }
    }
    else {
        //! 正则表达式验证
        if (!Ref.test(Obj.value)) {
            jsMessagebox($(ObjSpan).next("span").html());
            $(Obj).css('color', '#ec3c3c');
            aNotNull[$(Obj).index(".jsInPut")].proper = false;
            return false;
        }
        else {
            $(Obj).css('color', blColor);
        }
    }
    aNotNull[$(Obj).index(".jsInPut")].proper = true;

    return jsShowOver(ObjSpan);
};

/////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////
// 显示第二个提示隐藏其他的
function jsShowOne(obj) {
    //弹出错误提示
    jsMessagebox($(obj).html());
    return false;
};

function jsMessagebox(msg) {
    $('.messagebox').remove();
    var messagebox = '<div class=\"messagebox\"><div class=\"content\"></div></div>';
    $('body').append(messagebox);
    $('.messagebox .content').append(msg);
    $('.messagebox').click(function () {
        $(this).remove();
    });
    setTimeout(function () { $('.messagebox').remove(); }, 6000);
};

// 显示成功
function jsShowOver(Obj) {
    //Obj.css("display", 'none');
    //$(Obj).next("span").css("display", 'none');
    //$(Obj).next("span").next("span").css("display", 'none');
    //$(Obj).next("span").next("span").next("span").attr("style", { "display": "none" });
    return true;
};

