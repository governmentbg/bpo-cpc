export {};
// import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
// import { CoreApiServicesBase } from "@duosoftbg/bpo-components";
// import {
//   fulfilledThunkState,
//   initialThunkState,
//   pendingThunkState,
//   rejectedThunkState,
//   commonThunkCondition,
//   ReferenceDataDomain,
// } from "@duosoftbg/bpo-components";
//
// const sliceName = "appData/applicationStatuses";
// const applicationStatusesSlice = createSlice({
//   name: sliceName,
//   initialState: initialThunkState([]),
//   reducers: {},
//   extraReducers: (builder) => {
//     builder.addCase(applicationStatusesThunk.pending, (state, action) => {
//       return pendingThunkState(state);
//     });
//     builder.addCase(applicationStatusesThunk.fulfilled, (state, action) => {
//       const { data } = action.payload;
//       return fulfilledThunkState(data);
//     });
//     builder.addCase(applicationStatusesThunk.rejected, (state, action) => {
//       return rejectedThunkState([]);
//     });
//   },
// });
//
// export const applicationStatusesThunk = createAsyncThunk(
//   `${sliceName}/applicationStatusesThunk`,
//   async () => {
//     const response = await CoreApiServicesBase.getReferenceDataOptions(ReferenceDataDomain.APPLICATION_STATUS)();
//     return { data: response };
//   },
//   {
//     condition: (_, { getState, extra }) => {
//       return commonThunkCondition(getState()["AppData"]?.applicationStatuses);
//     },
//   }
// );
//
// export default applicationStatusesSlice.reducer;
