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
        default:
            return state;
    }
};
