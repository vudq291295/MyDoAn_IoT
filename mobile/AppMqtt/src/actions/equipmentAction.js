// import moment from 'moment';
import types from '../ultils/constants/actionType';
import * as _service from '../services/equipmentService';

//Get all
const getAllEquipmentSuccess = item => ({type: types.GET_EQUIPMENT_SUCCESS, payload: item});
const getAllEquipmentError = err => ({type: types.GETROOM_ERROR, payload: err});
const getAllEquipment = ()=> async dispatch=>{
  const result = await _service.getAllEquipment();
  if (result) {
    if(result!==undefined|| result.status == 200){
      dispatch(getAllEquipmentSuccess(result));
    }else{
      dispatch(getAllEquipmentError(result));
    }
  } else {
    dispatch(getAllEquipmentError("Danh sách phòng rỗng"));
  }
}

//Get by room
const getAllEquipmentByRoomSuccess = item => ({type: types.GET_EQUIPMENT_BY_ROOM_SUCCESS, payload: item});
const getAllEquipmentByRoomError = err => ({type: types.GET_EQUIPMENT_BY_ROOM_ERROR, payload: err});
const getEquipmentsByRoom = (id)=> async dispatch=>{
  const result = await _service.getEquipmentsByRoom(id);
  if (result) {
    if(result!==undefined|| result.status == 200){
      dispatch(getAllEquipmentByRoomSuccess(result));
    }else{
      dispatch(getAllEquipmentByRoomError(result));
    }
  } else {
    dispatch(getAllEquipmentByRoomError("Danh sách phòng rỗng"));
  }
}

//insert
const insertEquipmentSuccess = item => ({type: types.INSERT_EQUIPMENT_SUCCESS, payload: item});
const insertEquipmentError = err => ({type: types.INSERT_EQUIPMENT_ERROR, payload: err});
const insertEquipment = (param)=> async dispatch=>{
  const result = await _service.insertEquipment(param);
  if (result) {
    if(result!==undefined|| result.status == 200){
      dispatch(insertEquipmentSuccess(result));
    }else{
      dispatch(insertEquipmentError(result));
    }
  } else {
    dispatch(insertEquipmentError("Thêm mới không thành công!"));
  }
}

//update
const updateEquipmentSuccess = item => ({type: types.UPDATE_ROOM_SUCCESS, payload: item});
const updateEquipmentError = err => ({type: types.UPDATE_ROOM_ERROR, payload: err});
const updateEquipment = (param)=> async dispatch=>{
  const result = await _service.updateEquipment(param);
  if (result) {
    if(result!==undefined|| result.status == 200){
      dispatch(updateEquipmentSuccess(result));
    }else{
      dispatch(updateEquipmentError(result));
    }
  } else {
    dispatch(updateEquipmentError("Sửa không thành công!"));
  }
}

//delete
const deleteEquipmentSuccess = item => ({type: types.DELETE_ROOM_SUCCESS, payload: item});
const deleteEquipmentError = err => ({type: types.DELETE_ROOM_ERROR, payload: err});
const deleteEquipment = (param)=> async dispatch=>{
  const result = await _service.deleteEquipment(param);
  if (result) {
    if(result!==undefined|| result.status == 200){
      dispatch(deleteEquipmentSuccess(result));
    }else{
      dispatch(deleteEquipmentError(result));
    }
  } else {
    dispatch(deleteEquipmentError("Thêm mới không thành công!"));
  }
}

export { 
  getAllEquipment, 
  getEquipmentsByRoom,
  insertEquipment, 
  updateEquipment, 
  deleteEquipment 
};
