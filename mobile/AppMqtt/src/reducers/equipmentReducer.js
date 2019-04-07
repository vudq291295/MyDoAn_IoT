import types from '../ultils/constants/actionType';

const initialState = {
    success : false,
    message:'',
    payload: {
        
    }
}
export default (state = initialState, action) => {
    switch (action.type) {
        case types.GET_EQUIPMENT_SUCCESS: {
            return {
               listData: action.payload
            };
        }
        case types.GET_EQUIPMENT_ERROR:{
            return{
                error: action
            }
        }
        case types.GET_EQUIPMENT_BY_ROOM_SUCCESS: {
            return {
               listData: action.payload
            };
        }
        case types.GET_EQUIPMENT_BY_ROOM_ERROR:{
            return{
                error: action
            }
        }
        case types.INSERT_EQUIPMENT_SUCCESS: {
            return {
               insert: action.payload
            };
        }
        case types.INSERT_EQUIPMENT_ERROR:{
            return{
                insertErr: action
            }
        }
        case types.UPDATE_EQUIPMENT_SUCCESS: {
            return {
               update: action.payload
            };
        }
        case types.UPDATE_EQUIPMENT_ERROR:{
            return{
                updateErr: action
            }
        }
        case types.DELETE_EQUIPMENT_SUCCESS: {
            return {
               delete: action.payload
            };
        }
        case types.DELETE_EQUIPMENT_ERROR:{
            return{
                deleteErr: action
            }
        }
        default:
            return state;
    }
};
