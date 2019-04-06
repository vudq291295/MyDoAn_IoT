import types from '../ultils/constants/actionType';

const initialState = {
    success : false,
    message:'',
    payload: {
        
    }
}
export default (state = initialState, action) => {
    switch (action.type) {
        case types.GETROOM_SUCCESS: {
            return {
               listData: action.payload
            };
        }
        case types.GETROOM_ERROR:{
            return{
                error: action
            }
        }
        case types.INSERT_ROOM_SUCCESS: {
            return {
               insert: action.payload
            };
        }
        case types.INSERT_ROOM_ERROR:{
            return{
                insertErr: action
            }
        }
        case types.UPDATE_ROOM_SUCCESS: {
            return {
               update: action.payload
            };
        }
        case types.UPDATE_ROOM_ERROR:{
            return{
                updateErr: action
            }
        }
        case types.DELETE_ROOM_SUCCESS: {
            return {
               delete: action.payload
            };
        }
        case types.DELETE_ROOM_ERROR:{
            return{
                deleteErr: action
            }
        }
        default:
            return state;
    }
};
