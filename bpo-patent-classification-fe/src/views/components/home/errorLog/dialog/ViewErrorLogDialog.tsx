export {};
// import {
//   AsyncCallArgs,
//   BlockText,
//   CircularLoader,
//   GridContainer,
//   HtmlParseText,
//   useAsyncCall,
//   ViewDialog,
// } from "@duosoftbg/bpo-components";
// import React, { useEffect, useState } from "react";
// import { getErrorLog } from "../../../../../axios/api/services";
// import { ErrorLogControlActions } from "../../../../../store/redux/slice/ComponentsControl/errorLogControl";
// import useAppSelector from "../../../../../hooks/redux/base/useAppSelector";
// import useAppDispatch from "../../../../../hooks/redux/base/useAppDispatch";
// import { useTranslation } from "react-i18next";
// import { Alert } from "@mui/material";
//
// const ViewErrorLogDialog = () => {
//   const dispatch = useAppDispatch();
//   const { asyncCall } = useAsyncCall();
//   const [loading, setLoading] = useState(true);
//   const [error, setError] = useState(false);
//   const [errorLog, setErrorLog] = useState(null);
//
//   const { open, id } = useAppSelector((state) => {
//     return state.ComponentsControl.errorLogControl.modals.view;
//   });
//
//   const handleCloseDialog = () => {
//     dispatch(ErrorLogControlActions.closeModal({ modalType: "view" }));
//   };
//
//   useEffect(() => {
//     if (open) {
//       const asyncCallArgs: AsyncCallArgs = {
//         promise: getErrorLog(id),
//         onSuccess: (response) => {
//           setErrorLog(response);
//           setLoading(false);
//         },
//         onError: () => {
//           setError(true);
//           setLoading(false);
//         },
//       };
//       asyncCall(asyncCallArgs);
//     }
//
//     return () => {
//       setError(null);
//       setLoading(true);
//       setErrorLog(null);
//     };
//     // eslint-disable-next-line
//   }, [open]);
//
//   return (
//     <ViewDialog open={open} onClose={handleCloseDialog} title={"t.view.edu.level.to.app.type.dialog"}>
//       <Content errorLog={errorLog} loading={loading} error={error} />
//     </ViewDialog>
//   );
// };
//
// const Content = ({ loading, error, errorLog }) => {
//   const { t } = useTranslation();
//
//   if (loading) {
//     return <CircularLoader />;
//   }
//
//   if (error) {
//     return <Alert severity="error">{t("m.error.serverFetchingError")}</Alert>;
//   }
//
//   return (
//     <>
//       <GridContainer spacing={4}>
//         {errorLog?.createdDate && <BlockText withGrid label={"l.createdDate"} text={errorLog?.createdDate} />}
//         {errorLog?.resolvedDate && <BlockText withGrid label={"l.resolvedDate"} text={errorLog?.resolvedDate} />}
//         {errorLog?.resolvedUser && (
//           <BlockText withGrid label={"l.errorLog.resolvedUser"} text={errorLog?.resolvedUser} />
//         )}
//         {errorLog?.errorMessage && (
//           <HtmlParseText
//             propsGrid={{ xs: 12, md: 12, sm: 12, lg: 12 }}
//             withGrid
//             label={"l.errorLog.errorMessage"}
//             text={errorLog?.errorMessage}
//             styleArray={{ pointerEvents: "none" }}
//           />
//         )}
//         {errorLog?.resolvedComment && (
//           <BlockText
//             propsGrid={{ xs: 12, md: 12, sm: 12, lg: 12 }}
//             withGrid
//             label={"l.errorLog.resolvedComment"}
//             text={errorLog?.resolvedComment}
//           />
//         )}
//         {errorLog?.dataJson && (
//           <BlockText
//             propsGrid={{ xs: 12, md: 12, sm: 12, lg: 12 }}
//             withGrid
//             label={"l.errorLog.dataJson"}
//             text={errorLog?.dataJson}
//           />
//         )}
//       </GridContainer>
//     </>
//   );
// };
//
// export default ViewErrorLogDialog;
