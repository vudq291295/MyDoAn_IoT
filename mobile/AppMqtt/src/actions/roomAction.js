// import moment from 'moment';
import types from '../ultils/constants/actionType';
import * as _service from '../services/roomService';

//Get
const getRoomSuccess = item => ({type: types.GETROOM_SUCCESS, payload: item});
const getRoomError = err => ({type: types.GETROOM_ERROR, payload: err});
const getListRoom = ()=> async dispatch=>{
  const result = await _service.getAllRoom();
  if (result) {
    if(result!==undefined|| result.status == 200){
      dispatch(getRoomSuccess(result));
    }else{
      dispatch(getRoomError(result));
    }
  } else {
    dispatch(getRoomError("Danh sách phòng rỗng"));
  }
}

//insert
const insertRoomSuccess = item => ({type: types.INSERT_ROOM_SUCCESS, payload: item});
const insertRoomError = err => ({type: types.INSERT_ROOM_ERROR, payload: err});
const insertRoom = (param)=> async dispatch=>{
  const result = await _service.insertRoom(param);
  if (result) {
    if(result!==undefined|| result.status == 200){
      dispatch(insertRoomSuccess(result));
    }else{
      dispatch(insertRoomError(result));
    }
  } else {
    dispatch(insertRoomError("Thêm mới không thành công!"));
  }
}

//update
const updateRoomSuccess = item => ({type: types.UPDATE_ROOM_SUCCESS, payload: item});
const updateRoomError = err => ({type: types.UPDATE_ROOM_ERROR, payload: err});
const updateRoom = (param)=> async dispatch=>{
  const result = await _service.updateRoom(param);
  if (result) {
    if(result!==undefined|| result.status == 200){
      dispatch(updateRoomSuccess(result));
    }else{
      dispatch(updateRoomError(result));
    }
  } else {
    dispatch(updateRoomError("Thêm mới không thành công!"));
  }
}

//delete
const deleteRoomSuccess = item => ({type: types.DELETE_ROOM_SUCCESS, payload: item});
const deleteRoomError = err => ({type: types.DELETE_ROOM_ERROR, payload: err});
const deleteRoom = (param)=> async dispatch=>{
  const result = await _service.deleteRoom(param);
  if (result) {
    if(result!==undefined|| result.status == 200){
      dispatch(deleteRoomSuccess(result));
    }else{
      dispatch(deleteRoomError(result));
    }
  } else {
    dispatch(deleteRoomError("Thêm mới không thành công!"));
  }
}

export { 
  getListRoom, 
  insertRoom, 
  updateRoom, 
  deleteRoom 
};
