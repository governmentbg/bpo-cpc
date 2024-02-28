import { createSlice } from "@reduxjs/toolkit";

import { convertArrayToObject } from "@duosoftbg/bpo-components";
import { SEARCH_TABLE_CONFIG } from "../../../../config/search/filters/groupsConfig";

const createInitialState = () => {
  let result = [];
  for (const item in SEARCH_TABLE_CONFIG) {
    result.push(createInitialTableGroupValue(item));
  }

  return convertArrayToObject(result, "id");
};

function createInitialTableGroupValue(group) {
  let initialTableGroupValue = {
    id: null,
    hasSearchStarted: true,
    filtersData: SEARCH_TABLE_CONFIG[group].initValue,
  };

  initialTableGroupValue.id = group;
  return initialTableGroupValue;
}

const backofficeSearchTableSlice = createSlice({
  name: "backofficeSearchTable",
  initialState: createInitialState(),
  reducers: {
    overrideFiltersData: (state, action) => {
      const { group, data } = action.payload;
      state[group].filtersData = data;
    },
    updateHasSearchStarted: (state, action) => {
      const { group, start } = action.payload;
      state[group].hasSearchStarted = start;
    },
  },
});

export const { overrideFiltersData, updateHasSearchStarted } = backofficeSearchTableSlice.actions;

export default backofficeSearchTableSlice.reducer;
