export {};
// import * as React from "react";
// import useAppSelector from "../../../../../../../hooks/redux/base/useAppSelector";
// import { store } from "../../../../../../../store/redux/store";
// import { NomenclatureAutocompleteFilter } from "@duosoftbg/bpo-components";
// import { applicationStatusesThunk } from "../../../../../../../store/redux/slice/AppData/applicationStatuses";
//
// const ApplicationStatusFilter = ({ xs = 3, sm = 3, md = 3, group }) => {
//   const thunkState = useAppSelector((state) => {
//     return state.AppData.applicationStatuses;
//   });
//
//   const getPreviousStateOptionFn = () => {
//     const applicationStatus = store.getState().SearchData.backofficeSearchTable[group].filtersData.applicationStatus;
//     const options = store.getState().AppData.applicationStatuses.data;
//     return options.find((element) => element.id === applicationStatus);
//   };
//
//   return (
//     <NomenclatureAutocompleteFilter
//       filter={"applicationStatus"}
//       thunkFn={applicationStatusesThunk}
//       thunkState={thunkState}
//       getPreviousStateOptionFn={getPreviousStateOptionFn}
//       xs={xs}
//       sm={sm}
//       md={md}
//     />
//   );
// };
//
// export default ApplicationStatusFilter;
