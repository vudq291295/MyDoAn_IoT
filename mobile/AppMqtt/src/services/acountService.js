import axios from 'axios';
import url, { HOST } from '../ultils/constants/api';
import { AsyncStorage } from "react-native";

const _service = axios.create({
  baseURL: HOST,
  timeout: 5000,
  headers: {
    'Content-Type': 'application/x-www-form-urlencoded'
  }
});

const _retrieveData = async (name) => {
  try {
    const value = await AsyncStorage.getItem(name);
    if (value !== null) {
      return value;
    }
   } catch (error) {
      return {
        error : 'false'
      }
   }
}

const setDefaultTokenHeader = (token)=>{
  _service.defaults.headers.Authorization = "Bearer " + token;
}

const login = async (username, password) => {
  try {
    var infoCurrentUser ={
      token: {},
      user: {},
      success: false,
      message: "Đăng nhập thất bại"
    }
    var data = {
      username : username,
      password : password,
      grant_type: 'password',
      client_id: 'test',
      client_secret: 'test',
    };
    const result = await _service.post(url.LOGIN, toParams(data));
    if(result){
      var token = result.data.access_token;
      setDefaultTokenHeader(token);
      const infoUser = await _service.get(url.USERINFO);
      try {
        var infoCurrentUser = {
          token: token,
          user: infoUser.data,
          success: true
        }
        AsyncStorage.setItem('appmqtt', infoCurrentUser);
      } catch (error) {
        
      }
      return infoCurrentUser;
    }
  } catch (error) {
    console.log('error', error);
    return error;
  }
};

const toParams = function ObjectsToParams(obj) {
  var p = [];
  for (var key in obj) {
      p.push(key + '=' + encodeURIComponent(obj[key]));
  }
  return p.join('&');
}

export {
  login,
  _service,
  setDefaultTokenHeader
};
