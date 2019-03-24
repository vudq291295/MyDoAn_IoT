import types from '../ultils/constants/actionType';
// import { initialState } from '../store';

const initialState = {
    user : {
        "user": {
            "id": 0,
            "userName": "",
            "passWord": ""
        },
        "enabled": false,
        "username": "",
        "password": "",
        "authorities": null,
        "accountNonExpired": false,
        "accountNonLocked": false,
        "credentialsNonExpired": false
    },
    token: {},
    success : false,
    message:''
}
export default (state = initialState, action) => {
    console.log('state', state);
    switch (action.type) {
        case types.LOGIN: {
            return {
                state
            };
        }
        case types.LOGIN_ERROR: {
            return {
                message: action.payload
            };
        }
        default:
            return state;
    }
};
