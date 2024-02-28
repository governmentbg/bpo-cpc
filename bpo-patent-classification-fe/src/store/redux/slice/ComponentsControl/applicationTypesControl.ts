export {};
// import { createSlice } from "@reduxjs/toolkit";
//
// const initialState = {
//   modals: {
//     edit: {
//       open: false,
//       id: null,
//     },
//     toggleActivation: {
//       open: false,
//       id: null,
//     },
//   },
// };
//
// const applicationTypesControlSlice = createSlice({
//   name: "applicationTypesControl",
//   initialState: initialState,
//   reducers: {
//     openModal: (state, action) => {
//       const { id, modalType } = action.payload;
//       state.modals[modalType] = { open: true, id };
//     },
//     closeModal: (state, action) => {
//       const { modalType } = action.payload;
//       state.modals[modalType] = { open: false, id: null };
//     },
//   },
// });
//
// export const { openModal, closeModal } = applicationTypesControlSlice.actions;
// export default applicationTypesControlSlice.reducer;
