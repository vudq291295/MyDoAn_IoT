import { createStore, applyMiddleware, compose } from 'redux';
import { AsyncStorage } from 'react-native';
import storage from 'redux-persist/lib/storage';
import { persistCombineReducers } from 'redux-persist';
import thunkMiddleWare from 'redux-thunk';
import reducers from './reducers';

const config = {
    key: 'root',
    storage,
    blacklist: ['nav']
};

const persistReducer = persistCombineReducers(config, reducers);

const middleWare = [thunkMiddleWare];

const store = createStore(
    persistReducer,
    undefined, //initial state
    compose(
        applyMiddleware(...middleWare)
    )
);

export default store;
