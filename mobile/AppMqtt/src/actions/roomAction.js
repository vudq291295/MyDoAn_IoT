// import moment from 'moment';
import types from '../ultils/constants/actionType';
import * as _service from '../services/roomService';

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

export { getListRoom };
