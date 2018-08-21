export default class DateUtils {
    static getDayBegin(date) {
        if (!date) date = new Date();
        if (date && date._isAMomentObject) date = date.toDate();
        var result = new Date(date);
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
        date.setMilliseconds(0);
        return date;
    }

    static getDayEnd(date) {
        if (!date) date = new Date();
        if (date && date._isAMomentObject) date = date.toDate();
        var result = new Date(date);
        result.setHours(23);
        result.setMinutes(59);
        result.setSeconds(59);
        result.setMilliseconds(999);
        return result;
    }

    static getDateDiff(startDate, endDate) {
        if (!startDate || !endDate) return '';
        var startTime = startDate.getTime();
        var endTime = endDate.getTime();
        var dayDiff = (endTime - startTime) / (1000 * 60 * 60 * 24);
        return Math.ceil(dayDiff);
    }

    /*
     * yyyy-MM-dd
     */
    static parseToDate(DateStr) {
        var array =  DateStr.split("-");
        var dt = new Date(array[0], array[1] - 1, array[2]);
        return dt;
    }

    static addDate(date, days) {
        if (days == undefined || days == '') {
            days = 1;
        }
        var result = new Date(date);
        result.setDate(date.getDate() + days);
        return result;
    }

}