import { Message } from "element-ui";

export default class Http {
  // GET请求
  static get(url, params, option) {
    return this.do(url, {
      params,
      method: "get",
      ...option
    });
  }

  // json格式post请求
  static post(url, data, option) {
    return this.do(url, {
      method: "post",
      body:data,
      contentType:'json',
      ...option
    });
  }

  // formData格式post请求
  static postWithFormData(url, data, option) {
    return this.do(url, {
      method: "post",
      body:data,
      contentType:'formData',
      ...option
    });
  }

  

  // formData格式put请求
  static put(url, data, option) {
    return this.do(url, {
      method: "put",
      body:data,
      contentType:'json',
      ...option
    });
  }

  // json格式put请求
  static putWithFormData(url, data, option) {
    return this.do(url, {
      method: "put",
      body:data,
      contentType:'formData',
      ...option
    });
  }

  // 执行请求
  static do(url, option) {
    let optionHreaders = {};
      if (option && option.headers) {
        optionHreaders = option.headers;
      }
      let headers = {};
      if (option.contentType === "json") {
        // json方式
         headers = Object.assign(
          { "content-type": "application/json;charset=utf-8" },
          optionHreaders
        );
      } else if (option.contentType === "formData") {
        // 表单方式
         headers = Object.assign(
          { "content-type": "application/x-www-form-urlencoded;charset=utf-8" },
          optionHreaders
        );
      }
    option.headers = headers; 
    return this.myFetch(url,option);
  }

  // fetch支持的属性： method  headers referrer mode  credentials   redirect body  cache
  // 自己扩展的属性：timeout， params
  static myFetch(url, option) {
    let json = option.params;
    if (option.method === "get" && json) {
      let paramsStr = Object.keys(json)
        .map(function(key) {
          return key + "=" + json[key];
        })
        .join("&");
      url = url + "?" + paramsStr;
    }

    let config = {
      method: option.method,
      credentials: "include",
      headers: Object.assign({}, option.headers),
      body: JSON.stringify(option.body),
    };
    return this._fetch(fetch(url, config), option.timeout)
      .then(resp => {
        return resp.json().then(result => {
          if (resp.status >= 200 && resp.status < 300 && result.code === 0) {
            // 业务码 === 0，正常返回
            console.log("正常返回");
            return result.data;
          } else {
            // 异常返回
            let error = Object.assign(
              {
                status: resp.status,
                statusText: resp.statusText
              },
              result
            );
            throw error;
          }
        });
      })
      .catch(err => {
        console.log(err);
        Message.error(err.msg);
        throw err;
      });
  }

  static _fetch(requestPromise, timeout = 3 * 1000) {
    let timeoutAction = null;
    const timerPromise = new Promise((resolve, reject) => {
      timeoutAction = () => {

        reject(new Error("请求超时"));
      };
    });
    setTimeout(() => {
      timeoutAction();
    }, timeout);
    return Promise.race([requestPromise, timerPromise]);
  }
}
