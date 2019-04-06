// import moment from 'moment';
import types from '../ultils/constants/actionType';
import * as _service from '../services/acountService';

const loginSuccess = item => ({ type: types.LOGIN, payload: item });

const getRoomSuccess = item => ({type: types.GETROOM_SUCCESS, payload: item});

// const getTokenSuccess = item => ({ type: types.TOKEN, payload: item });

const loginError = err => {
  return { type: types.LOGIN_ERROR, payload: err };
};
const logout = () => {
  return { type: types.LOGOUT, payload: null };
};
const login = (username, password) => async dispatch => {
  const result = await _service.login(username, password);
  if (result) {
    if(result!==undefined|| result.success){
      dispatch(loginSuccess(result));
    }else{
      dispatch(loginError(result.message));
    }
  } else {
    dispatch(loginError("Đăng nhập thất bại"));
  }
};

const setToken = (token)=>dispatch=>{
  _service.setDefaultTokenHeader(token);
}

const room = ()=> async dispatch=>{
  const result = await _service.getAllRoom();
  if (result) {
    if(result!==undefined|| result.success){
      dispatch(getRoomSuccess(result));
    }else{
      dispatch(loginError(result.message));
    }
  } else {
    dispatch(loginError("Đăng nhập thất bại"));
  }
}

export { login, logout , setToken, room};
