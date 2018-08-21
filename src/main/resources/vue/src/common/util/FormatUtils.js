export default class FormatUtils {

  /**
   * 格式化货币
   */
  static formatCurrency(num) {
    if (!num)
      return '0';
    num = num.toString().replace(/\$|\,/g, '');
    if (isNaN(num))
      num = "0";
    let sign = (num == (num = Math.abs(num)));
    num = Math.floor(num * 100 + 0.50000000001);
    let cents = num % 100;
    num = Math.floor(num / 100).toString();
    if (cents < 10)
      cents = "0" + cents;
    for (let i = 0; i < Math.floor((num.length - (1 + i)) / 3); i++)
      num = num.substring(0, num.length - (4 * i + 3)) + ',' + num.substring(num.length - (4 * i + 3));
    return (((sign)
      ? ''
      : '-') + num + (cents !== '00'
      ? ('.' + cents)
      : ''));
  }

  /**
   * 格式化日期
   */
  static formatDate(date, pattern) {
    if(!date) return ''
    date = new Date(date)
    let o = {
      "M+": date.getMonth() + 1, //month
      "d+": date.getDate(), //day
      "h+": date.getHours(), //hour
      "m+": date.getMinutes(), //minute
      "s+": date.getSeconds(), //second
      "q+": Math.floor((date.getMonth() + 3) / 3), //quarter
      "S": date.getMilliseconds() //millisecond
    }
    if (/(y+)/.test(pattern))
      pattern = pattern.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (let k in o)
      if (new RegExp("(" + k + ")").test(pattern))
        pattern = pattern.replace(RegExp.$1, RegExp.$1.length == 1
          ? o[k]
          : ("00" + o[k]).substr(("" + o[k]).length));
    return pattern;
  }

  /**
   * 格式化日期
   */
  static formatStringDate(date) {
    if(!date) return '';
    return date.substring(0,4) + '-' + date.substring(4,6) + '-' + date.substring(6,8);
  }


    static formatStringTime(time) {
        if(!time) return '';
        let timeString = '';
        if(time.length >= 4){
            timeString += time.substring(0,4);
            if(time.length >= 6){
                timeString += '-' + time.substring(4,6);
                if(time.length >= 8) {
                    timeString += '-' + time.substring(6, 8);
                    if (time.length >= 10) {
                        timeString += ' ' + time.substring(8, 10);
                        if (time.length >= 12) {
                            timeString += ':' + time.substring(10, 12);
                            if (time.length >= 14) {
                                timeString += ':' + time.substring(12, 14);
                            } else {
                                timeString += ':00'
                            }
                        } else {
                            timeString += ':00'
                        }
                    }
                }
            }
        }
        return timeString;
    }

  /**
   * 获取日期间隔
   * */
  static calDateInterval(startDate, endDate) {
      if(!startDate || !endDate) return '';
      startDate = new Date(startDate);
      endDate = new Date(endDate);
      startDate.setHours(0,0,0,0);
      endDate.setHours(0,0,0,0);
      return (endDate.getTime() - startDate.getTime())/(1000*60*60*24)
  }
}