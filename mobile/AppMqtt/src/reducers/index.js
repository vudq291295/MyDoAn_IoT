import RootNavigator from '../RootNavigator';
import LoginReducer from './LoginReducer';

const nav = (state, action) => {
    const newState = RootNavigator.router.getStateForAction(action, state);
    
    return newState || state;
};

export default ({
    nav,
    LoginReducer
});
