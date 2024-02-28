import { useTranslation } from "react-i18next";
// import React from "react";
// import { ConfirmDialog } from "@duosoftbg/bpo-components";
// import useAppDispatch from "../../../../hooks/redux/base/useAppDispatch";
// import useAppSelector from "../../../../hooks/redux/base/useAppSelector";
// import { toast } from "react-toastify";
//
// type ToggleActivationDialogProps = {
//   filterFn: Function;
//   reduxControlName: string;
//   serviceFn: Function;
//   closeModalFn: Function;
//   isControlExternal?: boolean;
// };
//
// const ToggleActivationDialog = ({
//   filterFn,
//   reduxControlName,
//   serviceFn,
//   closeModalFn,
//   isControlExternal = false,
// }: ToggleActivationDialogProps) => {
//   const dispatch = useAppDispatch();
//   const { t } = useTranslation();
//
//   const { open, id } = useAppSelector((state) => {
//     if (isControlExternal) {
//       return state.LibraryComponentsControl[reduxControlName].modals.toggleActivation;
//     } else {
//       return state.ComponentsControl[reduxControlName].modals.toggleActivation;
//     }
//   });
//
//   const handleCloseDialog = () => {
//     dispatch(closeModalFn({ modalType: "toggleActivation" }));
//   };
//
//   const onSuccess = () => {
//     filterFn();
//     dispatch(closeModalFn({ modalType: "toggleActivation" }));
//     toast.success(t("m.save.data.success"));
//   };
//
//   return (
//     <ConfirmDialog
//       open={open}
//       onClose={handleCloseDialog}
//       promiseFunction={() => serviceFn(id)}
//       onSuccess={onSuccess}
//       title={"m.toggle.activation.modal.title"}
//       message={"m.toggle.activation.modal.message"}
//     />
//   );
// };
//
// export default ToggleActivationDialog;
