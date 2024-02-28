import { combineReducers } from "@reduxjs/toolkit";
import backofficeSearchTableReducer from "./backofficeSearchTable";

const viewDataReducers = combineReducers({
  backofficeSearchTable: backofficeSearchTableReducer,
});

export default viewDataReducers;
