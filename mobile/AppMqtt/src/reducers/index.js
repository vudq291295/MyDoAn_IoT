import RootNavigator from '../RootNavigator';
import LoginReducer from './LoginReducer';
import roomReducer from './roomReducer';
import equipmentReducer from './equipmentReducer';

const nav = (state, action) => {
    const newState = RootNavigator.router.getStateForAction(action, state);
    
    return newState || state;
};

export default ({
    nav,
    LoginReducer,
    roomReducer,
    equipmentReducer
});
