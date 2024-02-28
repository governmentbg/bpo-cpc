export {};
// import { useTranslation } from "react-i18next";
// import { useStore } from "react-redux";
// import { SEARCH_FILTERS_GROUP } from "../../../../config/search/filters/groupsConfig";
// import {
//   BaseInputFieldFilter,
//   BorderGreyBox,
//   CardSpg,
//   DateFromToFilter,
//   handleSubmitValidationFail,
//   isNotEmpty,
//   SearchFilterGrid,
//   SelectFormField,
//   useReactHookForm,
// } from "@duosoftbg/bpo-components";
// import { errorLogFilterInitialValues } from "../../../../init/errorLog/errorLogInitialValues";
// import { createErrorLogFilterValidationSchema } from "../../../../yup/schema/nomenclature/errorLog/createErrorLogFilterValidationSchema";
// import React, { useEffect } from "react";
// import useSearchTableControl from "../../../../hooks/backoffice/search/useSearchTableControl";
// import { searchErrorLogs } from "../../../../axios/api/services";
// import { FormProvider } from "react-hook-form";
// import { Box, CardContent } from "@mui/material";
// import SearchFilters from "../../common/search/filters/SearchFilters";
// import ErrorLogList from "./ErrorLogList";
// import ViewErrorLogDialog from "./dialog/ViewErrorLogDialog";
// import ResolveErrorLogDialog from "./dialog/ResolveErrorLogDialog";
//
// const ErrorLog = () => {
//   const { t } = useTranslation();
//   const store = useStore();
//   const group = SEARCH_FILTERS_GROUP.ERROR_LOG;
//
//   const { methods } = useReactHookForm({
//     defaultValues: errorLogFilterInitialValues,
//     validationSchema: createErrorLogFilterValidationSchema,
//   });
//
//   useEffect(() => {
//     const filtersData = store.getState().SearchData.backofficeSearchTable[group].filtersData;
//     if (isNotEmpty(filtersData)) {
//       methods.reset(filtersData);
//     }
//     // eslint-disable-next-line react-hooks/exhaustive-deps
//   }, []);
//
//   const control = useSearchTableControl({
//     group,
//     methods,
//     initialValues: errorLogFilterInitialValues,
//     filterData: searchErrorLogs,
//     callAfterResetFilters: true,
//   });
//
//   return (
//     <>
//       <ViewErrorLogDialog />
//       <ResolveErrorLogDialog filterFn={control.fetch} />
//       <FormProvider {...methods}>
//         <form
//           onSubmit={methods.handleSubmit(
//             () => control.handleSubmitFilters(),
//             () => handleSubmitValidationFail(methods.formState, t)
//           )}
//         >
//           <CardSpg my={4} style={{ overflow: "visible" }}>
//             <CardContent style={{ position: "relative" }}>
//               <Box>
//                 <BorderGreyBox>
//                   <SearchFilters
//                     isSubmitBtnDisabled={control.isSubmitBtnDisabled}
//                     isResetBtnDisabled={control.isResetBtnDisabled}
//                     handleResetFilters={control.handleResetFilters}
//                   >
//                     <DateFromToFilter from={"createdDateFrom"} to={"createdDateTo"} label={"l.createdDate"} />
//                     <DateFromToFilter from={"resolvedDateFrom"} to={"resolvedDateTo"} label={"l.resolvedDate"} />
//                     <SearchFilterGrid xs={12} sm={12} md={6} lg={6}>
//                       <SelectFormField
//                         fieldName={"onlyUnresolved"}
//                         labelCode={"Статус"}
//                         addEmptyOption={true}
//                         emptyOptionLabel={"Всички"}
//                         selectOptions={[
//                           { value: true, text: "Неразрешен" },
//                           { value: false, text: "Разрешен" },
//                         ]}
//                       />
//                     </SearchFilterGrid>
//                     <BaseInputFieldFilter md={6} lg={6} fieldName={"resolvedUser"} label={"l.errorLog.resolvedUser"} />
//                     <BaseInputFieldFilter md={6} lg={6} fieldName={"dataJson"} label={"l.errorLog.dataJson"} />
//                     <BaseInputFieldFilter
//                       md={6}
//                       lg={6}
//                       fieldName={"resolvedComment"}
//                       label={"l.errorLog.resolvedComment"}
//                     />
//                     <BaseInputFieldFilter md={12} fieldName={"errorMessage"} label={"l.errorLog.errorMessage"} />
//                   </SearchFilters>
//                 </BorderGreyBox>
//                 {control.showList && (
//                   <ErrorLogList
//                     records={control.records}
//                     total={control.total}
//                     isLoading={control.isLoading}
//                     onPageOrOrderChange={control.handlePageOrOrderChange}
//                     blockTable={control.blockTable}
//                     group={group}
//                     filterFn={control.fetch}
//                   />
//                 )}
//               </Box>
//             </CardContent>
//           </CardSpg>
//         </form>
//       </FormProvider>
//     </>
//   );
// };
//
// export default ErrorLog;
