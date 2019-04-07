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

const getAllEquipment = async ()=>{
  const result = await _service.get(url.GETALLROOM);
  if(result){
    return result;
  }else{
      return null;
  }
}

const getEquipmentsByRoom = async (id)=>{
  const result = await _service.get(url.GETEQUIPMENTBYROOM+"/"+id);
  if(result){
    return result;
  }else{
      return null;
  }
}


const insertEquipment = async (param)=>{
  const result = await _service.post(url.INSERTEQUIPMENT, param);
  if(result){
    return result;
  }else{
      return null;
  }
}

const updateEquipment = async (param)=>{
  const result = await _service.post(url.UPDATEEQUIPMENT, param);
  if(result){
    return result;
  }else{
      return null;
  }
}

const deleteEquipment = async (param)=>{
  const result = await _service.post(url.DELETEEQUIPMENT, param);
  if(result){
    return result;
  }else{
      return null;
  }
}

export {
  getAllEquipment,
  getEquipmentsByRoom,
  insertEquipment,
  updateEquipment,
  deleteEquipment
};
