export {};
// import * as React from "react";
// import { Fragment, useState } from "react";
// import TableBody from "@mui/material/TableBody";
// import TableCell from "@mui/material/TableCell";
// import TableRow from "@mui/material/TableRow";
// import {
//   AlertSpg,
//   AsyncCallArgs,
//   ConfirmSubmitDialog,
//   OptionTableCell,
//   SecurityGuard,
//   SecurityRole,
//   TableButton,
//   HtmlParseText,
// } from "@duosoftbg/bpo-components";
// import { useTranslation } from "react-i18next";
// import { useFormContext } from "react-hook-form";
// import { BackofficeSearchTable } from "@duosoftbg/bpo-components";
// import useAppDispatch from "../../../../hooks/redux/base/useAppDispatch";
// import { ErrorLogControlActions } from "../../../../store/redux/slice/ComponentsControl/errorLogControl";
// import { automaticallyResolveErrorLog } from "../../../../axios/api/services";
//
// const headCells = [
//   {
//     id: "number",
//     label: "l.table.head.number",
//     sortable: false,
//   },
//   {
//     id: "errorMessage",
//     label: "l.errorLog.errorMessage",
//   },
//   {
//     id: "createdDate",
//     label: "l.createdDate",
//   },
//   {
//     id: "resolvedDate",
//     label: "l.resolvedDate",
//   },
//   {
//     id: "resolvedUser",
//     label: "l.user",
//   },
//   {
//     id: "status",
//     label: "l.status",
//     sortable: false,
//   },
//   {
//     id: "options",
//     label: "",
//     sortable: false,
//   },
// ];
//
// const ErrorLogListTable = ({ total, records, blockTable, onPageOrOrderChange, group }) => {
//   const dispatch = useAppDispatch();
//   const { t } = useTranslation();
//   const { getValues } = useFormContext();
//
//   const handleOpenModal = (id, modalType) => {
//     const payload = { id, modalType };
//     dispatch(ErrorLogControlActions.openModal(payload));
//   };
//
//   return (
//     <Fragment>
//       {total > 0 && (
//         <BackofficeSearchTable
//           group={group}
//           headCells={headCells}
//           total={total}
//           blockTable={blockTable}
//           onPageOrOrderChange={onPageOrOrderChange}
//         >
//           <TableBody>
//             {records.map((row, index) => (
//               <TableRow hover key={row.id}>
//                 <TableCell>{index + 1 + getValues().page * getValues().pageSize}</TableCell>
//                 <TableCell>
//                   <HtmlParseText text={row.errorMessage} styleArray={{ pointerEvents: "none" }} />
//                 </TableCell>
//                 <TableCell>{row.createdDate}</TableCell>
//                 <TableCell>{row.resolvedDate}</TableCell>
//                 <TableCell>{row.resolvedUser}</TableCell>
//                 <TableCell>
//                   {row.resolvedDate ? "разрешен" : <span style={{ color: "#c80000" }}>неразрешен</span>}
//                 </TableCell>
//                 <OptionTableCell>
//                   <SecurityGuard checkForRoles={[SecurityRole.BoErrorLogEdit]}>
//                     <TableButton type={"view"} onClick={() => handleOpenModal(row.id, "view")} />
//                     {!row.resolvedDate && (
//                       <TableButton type={"edit"} onClick={() => handleOpenModal(row.id, "resolve")} />
//                     )}
//                     {!row.resolvedDate && <AutoResolveButton id={row.id} filterFn={onPageOrOrderChange} />}
//                   </SecurityGuard>
//                 </OptionTableCell>
//               </TableRow>
//             ))}
//           </TableBody>
//         </BackofficeSearchTable>
//       )}
//       {!(total > 0) && (
//         <AlertSpg mt={10} mb={10} severity="info">
//           {t("m.empty.list")}
//         </AlertSpg>
//       )}
//     </Fragment>
//   );
// };
//
// const AutoResolveButton = ({ id, filterFn }) => {
//   const [confirmModalState, setConfirmModalState] = useState({ open: false, submitFn: null });
//
//   const handleClick = () => {
//     const asyncCallArgs: AsyncCallArgs = {
//       withGlobalBackdrop: true,
//       promise: automaticallyResolveErrorLog(id),
//       successMessage: { show: true },
//       processResponseErrors: true,
//       onSuccess: (response) => {
//         filterFn();
//       },
//     };
//
//     setConfirmModalState({ open: true, submitFn: asyncCallArgs });
//   };
//
//   return (
//     <>
//       <TableButton title={"Автоматично разрешаване"} type={"signature"} onClick={handleClick} />
//       <ConfirmSubmitDialog
//         dialogTitleText={"Автоматично разрешаване"}
//         alertText={"Сигурни ли сте, че искате да направите опит за автоматично разрешаване на проблема ?"}
//         modalState={confirmModalState}
//         setModalState={setConfirmModalState}
//       />
//     </>
//   );
// };
//
// export default ErrorLogListTable;
