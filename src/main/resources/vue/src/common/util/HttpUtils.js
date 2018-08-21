
import { message } from 'antd';
import CookieUtil from 'util/CookieUtil'

export default class Http {
  static get(url, params, timeout) {
    if (params) {
      var urlParam = ''
      for (let p in params) {
        if (!params[p])
          delete params[p]
      }
      for (let p in params) {
        // urlParam += `${p}=${params[p]}&`
        urlParam += p + '=' + params[p] + '&'
      }
      urlParam = urlParam.substr(0, urlParam.length - 1)
      url += '?' + urlParam
    }
    return this.do(url, timeout)
  }

  static post(url, body, timeout) {
    return this.do(url, 'POST', body, timeout)
  }

  static put(url, body, timeout) {
    return this.do(url, 'PUT', body, timeout)
  }

  static delete(url, body, timeout) {
    return this.do(url, 'DELETE', body, timeout)
  }

  /**
   * @param  {[type]} url
   * @param  {[type]} method
   * @param  {[type]} body
   * @param  {[type]} timeout 单位毫秒
   * @return {[type]}
   */
  static do(url, method, body, timeout) {
    method = method || 'GET'
    body = body || ''
    let config = {
      method: method,
      credentials: 'include'
    }
    if(method !== 'GET') {
      config.headers = new Headers({'Content-Type': 'application/json'})
      config.body = JSON.stringify(body)
    }

    return this._fetch(fetch(url, config), timeout).then(response => {
      return response.json().then(json => {
        if (response.ok) {
          return json
        } else {
          let error = Object.assign({}, json, {
            status: response.status,
            statusText: response.statusText
          })
          throw error
        }
      }, (error) => {
        //没有返回值时response.json()报错
        if (response.ok) {
          return '';
        } else {
          throw {status: response.status, statusText: response.statusText}
        }
      })
    }, error => {
      throw error
    }).catch(error => {
      console.log('url ' + url)
      console.log(error)
      if(error && (error.code === 401 || error.status === 302)) {
        CookieUtil.clearCookie('beibei_token')
        location = '/page/login'
      }else if(error.code === 403) {
        location.replace('/page/noPriv')
      }else if(error && error.message) {
          message.error(error.message)
      }else {
          message.error('服务器内部错误')
      }
      throw error
    })

  }

  static _fetch(fetch_promise, timeout) {
    if (timeout === 0)
      return fetch_promise

    if (!timeout)
      timeout = 1000 * 30; // 默认超时20s

    var abort_fn = null;

    //这是一个可以被reject的promise
    var abort_promise = new Promise(function(resolve, reject) {
      abort_fn = function() {
        reject(new Error('请求超时')); //超时
      };
    });

    //这里使用Promise.race，以最快 resolve 或 reject 的结果来传入后续绑定的回调
    var abortable_promise = Promise.race([fetch_promise, abort_promise]);

    setTimeout(function() {
      abort_fn();
    }, timeout);

    return abortable_promise;
  }


}
