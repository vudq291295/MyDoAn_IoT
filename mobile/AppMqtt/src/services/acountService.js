import url, { HOST } from '../ultils/constants/api';
import { AsyncStorage } from "react-native";
import { _service } from './commonService';
import axios from 'axios';

const setDefaultTokenHeader = (token)=>{
  _service.defaults.headers.Authorization = "Bearer " + token;
}

var service = axios.create({
  baseURL: HOST,
  timeout: 5000,
  headers: {
    'Content-Type': 'application/x-www-form-urlencoded'
  }
});

const login = async (username, password) => {
  try {
    var result = {
      success: false,
      message: 'Thất bại!'
    }
    var data = {
      username : username,
      password : password,
      grant_type: 'password',
      client_id: 'test',
      client_secret: 'test',
    };
    const result = await service.post(url.LOGIN, toParams(data));
    if(result){
      var token = result.data.access_token;
      setDefaultTokenHeader(token);
      try {
        AsyncStorage.setItem('token-appmqtt', token);
        result.success = true;
        result.message = "Thành công!";
        return result;
      } catch (error) {
      }
      return result;
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
