/*!
 * Distpicker v1.0.2
 * https://github.com/tshi0912/city-picker
 *
 * Copyright (c) 2014-2016 Tao Shi
 * Released under the MIT license
 *
 * Date: 2016-02-29T12:11:36.473Z
 */

(function (factory) {
    if (typeof define === 'function' && define.amd) {
        // AMD. Register as anonymous module.
        define('ChineseDistricts', [], factory);
    } else {
        // Browser globals.
        factory();
    }
})(function () {

    var ChineseDistricts = {
            //86: {
            //    110000: '北京',
            //    120000: '天津',
            //    130000: '河北',
            //    140000: '山西',
            //    150000: '内蒙古',
            //    210000: '辽宁',
            //    220000: '吉林',
            //    230000: '黑龙江',
            //    310000: '上海',
            //    320000: '江苏',
            //    330000: '浙江',
            //    340000: '安徽',
            //    350000: '福建',
            //    360000: '江西',
            //    370000: '山东',
            //    410000: '河南',
            //    420000: '湖北',
            //    430000: '湖南',
            //    440000: '广东',
            //    450000: '广西',
            //    460000: '海南',
            //    500000: '重庆',
            //    510000: '四川',
            //    520000: '贵州',
            //    530000: '云南',
            //    540000: '西藏',
            //    610000: '陕西',
            //    620000: '甘肃',
            //    630000: '青海',
            //    640000: '宁夏',
            //    650000: '新疆',
            //    710000: '台湾',
            //    810000: '香港',
            //    820000: '澳门'
            //},
            86: {
                'A-G': [
                    {code: '110000', address: '川'},
                    {code: '110000', address: '鄂'},
                    {code: '110000', address: '赣'},
                    {code: '110000', address: '桂'},
                    {code: '110000', address: '贵'},
                    {code: '110000', address: '甘'}],
                'H-K': [
                    {code: '110000', address: '沪'},
                    {code: '110000', address: '黑'},
                    {code: '110000', address: '京'},
                    {code: '110000', address: '津'},
                    {code: '110000', address: '冀'},
                    {code: '110000', address: '晋'},
                    {code: '110000', address: '吉'}],
                'L-S': [
                    {code: '110000', address: '鲁'},
                    {code: '110000', address: '辽'},
                    {code: '110000', address: '闽'},
                    {code: '110000', address: '蒙'},
                    {code: '110000', address: '宁'},
                    {code: '110000', address: '琼'},
                    {code: '110000', address: '青'}],
                'T-Z': [
                    {code: '110000', address: '皖'},
                    {code: '110000', address: '湘'},
                    {code: '110000', address: '新'},
                    {code: '110000', address: '渝'},
                    {code: '110000', address: '豫'},
                    {code: '110000', address: '粤'},
                    {code: '110000', address: '云'},
                    {code: '110000', address: '浙'},
                    {code: '110000', address: '藏'}]
            },
            110000: {
                110101: 'A',
                110102: 'B',
                110103: 'C',
                110104: 'D',
                110105: 'E',
                110106: 'F',
                110107: 'G',
                110108: 'H',
                110109: 'I',
                110110: 'J',
                110111: 'K',
                110112: 'L',
                110113: 'M',
                110114: 'N',
                110115: 'O',
                110116: 'P',
                110117: 'Q',
                110118: 'R',
                110119: 'S',
                110120: 'T',
                110121: 'U',
                110122: 'V',
                110123: 'W',
                110124: 'X',
                110125: 'Y',
                110126: 'Z',
                110127: '1',
                110128: '2',
                110129: '3',
                110130: '4',
                110131: '5',
                110132: '6',
                110133: '7',
                110134: '8',
                110135: '9',
                110136: '0'
            }
        }
        ;

    if (typeof window !== 'undefined') {
        window.ChineseDistricts = ChineseDistricts;
    }

    return ChineseDistricts;

});
