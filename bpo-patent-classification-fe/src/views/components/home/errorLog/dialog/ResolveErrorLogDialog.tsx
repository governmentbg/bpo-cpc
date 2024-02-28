export {};
// import {
//   GridContainer,
//   useAsyncCall,
//   ViewDialog,
//   useReactHookForm,
//   BlockFormBackdrop,
//   handleSubmitValidationFail,
//   GridItem,
//   SubmitFormButton,
//   BoxSpg,
//   TextareaFormField,
// } from "@duosoftbg/bpo-components";
// import React from "react";
// import useAppSelector from "../../../../../hooks/redux/base/useAppSelector";
// import useAppDispatch from "../../../../../hooks/redux/base/useAppDispatch";
// import { useTranslation } from "react-i18next";
// import { ErrorLogControlActions } from "../../../../../store/redux/slice/ComponentsControl/errorLogControl";
// import { initializeYup } from "@duosoftbg/bpo-components";
// import * as yup from "yup";
// import { resolveErrorLog } from "../../../../../axios/api/services";
// import { FormProvider } from "react-hook-form";
//
// const ResolveErrorLogDialog = ({ filterFn }) => {
//   const dispatch = useAppDispatch();
//
//   const { open, id } = useAppSelector((state) => {
//     return state.ComponentsControl.errorLogControl.modals.resolve;
//   });
//
//   const handleCloseDialog = () => {
//     dispatch(ErrorLogControlActions.closeModal({ modalType: "resolve" }));
//   };
//
//   if (!open) {
//     return null;
//   }
//
//   return (
//     <ViewDialog
//       open={open}
//       onClose={handleCloseDialog}
//       title={"t.resolveErrorLogDialog"}
//       disableEnforceFocus
//       dialogActionsSpacing={{ pr: 3 }}
//     >
//       <Content id={id} handleCloseDialog={handleCloseDialog} filterFn={filterFn} />
//     </ViewDialog>
//   );
// };
//
// const Content = ({ id, handleCloseDialog, filterFn }) => {
//   const { t } = useTranslation();
//   const { asyncCall } = useAsyncCall();
//
//   const { methods } = useReactHookForm({
//     defaultValues: { comment: "" },
//     validationSchema: () => {
//       initializeYup(yup);
//       return yup.object({
//         comment: yup.string().required(),
//       });
//     },
//   });
//
//   const onSubmit = (formData) => {
//     asyncCall({
//       withGlobalBackdrop: true,
//       processResponseErrors: true,
//       successMessage: { show: true },
//       reactHooksForm: { methods },
//       promise: resolveErrorLog(id, formData),
//       onSuccess: (response) => {
//         handleCloseDialog();
//         filterFn();
//       },
//     });
//   };
//
//   return (
//     <FormProvider {...methods}>
//       <form onSubmit={methods.handleSubmit(onSubmit, () => handleSubmitValidationFail(methods.formState, t))}>
//         <BlockFormBackdrop />
//
//         <GridContainer spacing={4} mt={0}>
//           <GridItem sm={12} md={12}>
//             <TextareaFormField rows={6} fieldName={"comment"} labelCode={"l.comment"} />
//           </GridItem>
//         </GridContainer>
//
//         <BoxSpg ml={0} mt={3}>
//           <SubmitFormButton withLoader withLoadingText label={"l.btn.save.data"} color="primary" />
//         </BoxSpg>
//       </form>
//     </FormProvider>
//   );
// };
//
// export default ResolveErrorLogDialog;
