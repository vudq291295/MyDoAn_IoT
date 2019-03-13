import RootNavigator from '../RootNavigator';

const nav = (state, action) => {
    const newState = RootNavigator.router.getStateForAction(action, state);
    
    return newState || state;
};

export default ({
    nav
});
