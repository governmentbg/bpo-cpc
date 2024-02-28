import { configureStore } from "@reduxjs/toolkit";
// import appDataReducers from "./slice/AppData";
import searchDataReducers from "./slice/SearchData";
// import componentsControlReducers from "./slice/ComponentsControl";
import { defaultReduxReducers } from "@duosoftbg/bpo-components";

export const store = configureStore({
  reducer: {
    ...defaultReduxReducers,
    SearchData: searchDataReducers,
    // AppData: appDataReducers,
    // ComponentsControl: componentsControlReducers,
  },
  devTools: process.env.REACT_APP_PROFILE !== "production",
});

export type AppDispatch = typeof store.dispatch;
export type RootState = ReturnType<typeof store.getState>;
