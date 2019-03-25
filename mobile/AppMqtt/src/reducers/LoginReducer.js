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
