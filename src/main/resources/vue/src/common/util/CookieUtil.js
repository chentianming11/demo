export default class CookieUtil {

  static setCookie(cname, cvalue, exdays) {
      var d = new Date();
      d.setTime(d.getTime() + (exdays*24*60*60*1000));
      var expires = "expires="+d.toUTCString();
      document.cookie = cname + "=" + escape(cvalue) + "; "+"path=/" + "; "+ expires;
  }
  //获取cookie
  static getCookie(cname) {
      var name = cname + "=";
      var ca = document.cookie.split(';');
      for(var i=0; i<ca.length; i++) {
          var c = ca[i];
          while (c.charAt(0)==' ') c = c.substring(1);
          if (c.indexOf(name) != -1) return unescape(c.substring(name.length, c.length));
      }
      return "";
  }
  //清除cookie
  static clearCookie(name) {
      this.setCookie(name, "", -1);
  }

  static clearLoginCookie() {
      let cname = "access_token";
      let domain = "";
      try {
          var hostnames = location.hostname.split('.');
          if (hostnames.length >= 2 && isNaN(hostnames[hostnames.length - 1])) {
              domain = "; domain=." + hostnames[hostnames.length - 2] + "." + hostnames[hostnames.length - 1];
          }
      } catch (error) {
          console.error(error);
      }
      var date=new Date();
      date.setTime(date.getTime()-10000);
      document.cookie = cname + "=" + escape("") + "; path=/" + domain + "; expires=" + date.toGMTString();
    }

}
