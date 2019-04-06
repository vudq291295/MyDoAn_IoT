import url, { HOST } from '../ultils/constants/api';
import { AsyncStorage } from "react-native";
import { _service } from "./commonService";

const toParams = function ObjectsToParams(obj) {
  var p = [];
  for (var key in obj) {
      p.push(key + '=' + encodeURIComponent(obj[key]));
  }
  return p.join('&');
}

const getAllRoom = async ()=>{
  const result = await _service.get(url.GETROOM);
  if(result){
    return result;
  }else{
      return null;
  }
}

export {
  getAllRoom
};
