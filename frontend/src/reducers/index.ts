import { createStore, applyMiddleware, combineReducers } from 'redux';
import { persistStore, persistReducer } from 'redux-persist';
import { composeWithDevTools } from 'redux-devtools-extension';
import storage from 'redux-persist/lib/storage';
import { userReducer } from './userReducers';

const rootReducers = combineReducers({
  user: userReducer
});

const persistConfig = {
  key: 'root',
  storage,
};

const persistedReducer = persistReducer(persistConfig, rootReducers);

const store = createStore(
    persistedReducer,
    composeWithDevTools(applyMiddleware())
)

const  persistor = persistStore(store);

export { store, persistor };