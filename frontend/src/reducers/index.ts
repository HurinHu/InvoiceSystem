import { createStore, applyMiddleware, combineReducers } from 'redux';
import { persistStore, persistReducer } from 'redux-persist';
import { composeWithDevTools } from 'redux-devtools-extension';
import storage from 'redux-persist/lib/storage';
import { globalReducer } from './globalReducer';
import { userReducer } from './userReducer';
import thunk from 'redux-thunk';

const rootReducers = combineReducers({
  global: globalReducer,
  user: userReducer
});

const persistConfig = {
  key: 'root',
  storage,
};

const persistedReducer = persistReducer(persistConfig, rootReducers);

const store = createStore(
    persistedReducer,
    composeWithDevTools(applyMiddleware(thunk))
)

const  persistor = persistStore(store);

export { store, persistor };